/*
 * Copyright (C) 2015 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package androidx.constraintlayout.core

import androidx.constraintlayout.core.widgets.Chain
import androidx.constraintlayout.core.widgets.ConstraintAnchor
import androidx.constraintlayout.core.widgets.ConstraintWidget
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.sin

/**
 * Represents and solves a system of linear equations.
 */
class LinearSystem {
    @JvmField
    var hasSimpleDefinition = false

    /*
     * Variable counter
     */
    var numVariables = 0

    /*
     * Store a map between name->SolverVariable and SolverVariable->Float for the resolution.
     */
    private var mVariables: HashMap<String, SolverVariable>? = null

    /**
     * Simple accessor for the current goal. Used when minimizing the system's goal.
     * @return the current goal.
     */
    /*
          * The goal that is used when minimizing the system.
          */
    val goal: Row?
    private var TABLE_SIZE = 32 // default table size for the allocation
    private var mMaxColumns = TABLE_SIZE
    @JvmField
    var mRows: Array<ArrayRow?>? = null

    // if true, will use graph optimizations
    @JvmField
    var graphOptimizer = false
    @JvmField
    var newgraphOptimizer = false

    // Used in optimize()
    private var mAlreadyTestedCandidates = BooleanArray(TABLE_SIZE)
    var mNumColumns = 1
    var numEquations = 0
    private var mMaxRows = TABLE_SIZE
    val cache: Cache
    private var mPoolVariables = arrayOfNulls<SolverVariable>(POOL_SIZE)
    private var mPoolVariablesCount = 0
    private var mTempGoal: Row? = null

    internal inner class ValuesRow(cache: Cache) : ArrayRow() {
        init {
            variables = SolverVariableValues(this, cache)
        }
    }

    fun fillMetrics(metrics: Metrics?) {
        Companion.metrics = metrics
    }

    interface Row {
        fun getPivotCandidate(system: LinearSystem?, avoid: BooleanArray?): SolverVariable?
        fun clear()
        fun initFromRow(row: Row?)
        fun addError(variable: SolverVariable?)
        fun updateFromSystem(system: LinearSystem?)
        val key: SolverVariable?
        val isEmpty: Boolean
        fun updateFromRow(system: LinearSystem?, definition: ArrayRow?, b: Boolean)
        fun updateFromFinalVariable(system: LinearSystem?, variable: SolverVariable?, removeFromDefinition: Boolean)
    }
    /*--------------------------------------------------------------------------------------------*/ // Memory management
    /*--------------------------------------------------------------------------------------------*/
    /**
     * Reallocate memory to accommodate increased amount of variables
     */
    private fun increaseTableSize() {
        if (DEBUG) {
            println("###########################")
            println("### INCREASE TABLE TO " + TABLE_SIZE * 2 + " (num rows: " + numEquations + ", num cols: " + mNumColumns + "/" + mMaxColumns + ")")
            println("###########################")
        }
        TABLE_SIZE *= 2
        mRows = mRows?.copyOf(TABLE_SIZE)
        cache.mIndexedVariables = cache.mIndexedVariables.copyOf(TABLE_SIZE)
        mAlreadyTestedCandidates = BooleanArray(TABLE_SIZE)
        mMaxColumns = TABLE_SIZE
        mMaxRows = TABLE_SIZE
        if (metrics != null) {
            metrics!!.tableSizeIncrease++
            metrics!!.maxTableSize = max(metrics!!.maxTableSize, TABLE_SIZE.toLong())
            metrics!!.lastTableSize = metrics!!.maxTableSize
        }
    }

    /**
     * Release ArrayRows back to their pool
     */
    private fun releaseRows() {
        if (OPTIMIZED_ENGINE) {
            for (i in 0 until numEquations) {
                val row = mRows!![i]
                if (row != null) {
                    cache.optimizedArrayRowPool.release(row)
                }
                mRows!![i] = null
            }
        } else {
            for (i in 0 until numEquations) {
                val row = mRows!![i]
                if (row != null) {
                    cache.arrayRowPool.release(row)
                }
                mRows!![i] = null
            }
        }
    }

    /**
     * Reset the LinearSystem object so that it can be reused.
     */
    fun reset() {
        if (DEBUG) {
            println("##################")
            println("## RESET SYSTEM ##")
            println("##################")
        }
        for (i in cache.mIndexedVariables.indices) {
            val variable = cache.mIndexedVariables[i]
            variable?.reset()
        }
        cache.solverVariablePool.releaseAll(mPoolVariables, mPoolVariablesCount)
        mPoolVariablesCount = 0
        cache.mIndexedVariables.fill(null)
        if (mVariables != null) {
            mVariables!!.clear()
        }
        numVariables = 0
        goal!!.clear()
        mNumColumns = 1
        for (i in 0 until numEquations) {
            if (mRows!![i] != null) {
                mRows!![i]!!.used = false
            }
        }
        releaseRows()
        numEquations = 0
        mTempGoal = if (OPTIMIZED_ENGINE) {
            ValuesRow(cache)
        } else {
            ArrayRow(cache)
        }
    }

    /*--------------------------------------------------------------------------------------------*/ // Creation of rows / variables / errors
    /*--------------------------------------------------------------------------------------------*/
    fun createObjectVariable(anchor: Any?): SolverVariable? {
        if (anchor == null) {
            return null
        }
        if (mNumColumns + 1 >= mMaxColumns) {
            increaseTableSize()
        }
        var variable: SolverVariable? = null
        if (anchor is ConstraintAnchor) {
            variable = anchor.solverVariable
            if (variable == null) {
                anchor.resetSolverVariable(cache)
                variable = anchor.solverVariable
            }
            if (variable!!.id == -1 || variable.id > numVariables || cache.mIndexedVariables[variable.id] == null) {
                if (variable.id != -1) {
                    variable.reset()
                }
                numVariables++
                mNumColumns++
                variable.id = numVariables
                variable.mType = SolverVariable.Type.UNRESTRICTED
                cache.mIndexedVariables[numVariables] = variable
            }
        }
        return variable
    }

    fun createRow(): ArrayRow {
        var row: ArrayRow?
        if (OPTIMIZED_ENGINE) {
            row = cache.optimizedArrayRowPool.acquire()
            if (row == null) {
                row = ValuesRow(cache)
                OPTIMIZED_ARRAY_ROW_CREATION++
            } else {
                row.reset()
            }
        } else {
            row = cache.arrayRowPool.acquire()
            if (row == null) {
                row = ArrayRow(cache)
                ARRAY_ROW_CREATION++
            } else {
                row.reset()
            }
        }
        SolverVariable.increaseErrorId()
        return row
    }

    fun createSlackVariable(): SolverVariable {
        if (metrics != null) {
            metrics!!.slackvariables++
        }
        if (mNumColumns + 1 >= mMaxColumns) {
            increaseTableSize()
        }
        val variable = acquireSolverVariable(SolverVariable.Type.SLACK, null)
        numVariables++
        mNumColumns++
        variable.id = numVariables
        cache.mIndexedVariables[numVariables] = variable
        return variable
    }

    fun createExtraVariable(): SolverVariable {
        if (metrics != null) {
            metrics!!.extravariables++
        }
        if (mNumColumns + 1 >= mMaxColumns) {
            increaseTableSize()
        }
        val variable = acquireSolverVariable(SolverVariable.Type.SLACK, null)
        numVariables++
        mNumColumns++
        variable.id = numVariables
        cache.mIndexedVariables[numVariables] = variable
        return variable
    }

    private fun addError(row: ArrayRow) {
        row.addError(this, SolverVariable.STRENGTH_NONE)
    }

    private fun addSingleError(row: ArrayRow, sign: Int) {
        addSingleError(row, sign, SolverVariable.STRENGTH_NONE)
    }

    fun addSingleError(row: ArrayRow, sign: Int, strength: Int) {
        var prefix: String? = null
        if (DEBUG) {
            prefix = if (sign > 0) {
                "ep"
            } else {
                "em"
            }
            prefix = "em"
        }
        val error = createErrorVariable(strength, prefix)
        row.addSingleError(error, sign)
    }

    private fun createVariable(name: String, type: SolverVariable.Type): SolverVariable {
        if (metrics != null) {
            metrics!!.variables++
        }
        if (mNumColumns + 1 >= mMaxColumns) {
            increaseTableSize()
        }
        val variable = acquireSolverVariable(type, null)
        variable.name = name
        numVariables++
        mNumColumns++
        variable.id = numVariables
        if (mVariables == null) {
            mVariables = HashMap()
        }
        mVariables!![name] = variable
        cache.mIndexedVariables[numVariables] = variable
        return variable
    }

    fun createErrorVariable(strength: Int, prefix: String?): SolverVariable {
        if (metrics != null) {
            metrics!!.errors++
        }
        if (mNumColumns + 1 >= mMaxColumns) {
            increaseTableSize()
        }
        val variable = acquireSolverVariable(SolverVariable.Type.ERROR, prefix)
        numVariables++
        mNumColumns++
        variable.id = numVariables
        variable.strength = strength
        cache.mIndexedVariables[numVariables] = variable
        goal!!.addError(variable)
        return variable
    }

    /**
     * Returns a SolverVariable instance of the given type
     * @param type type of the SolverVariable
     * @return instance of SolverVariable
     */
    private fun acquireSolverVariable(type: SolverVariable.Type, prefix: String?): SolverVariable {
        var variable = cache.solverVariablePool.acquire()
        if (variable == null) {
            variable = SolverVariable(type, prefix)
            variable.setType(type, prefix)
        } else {
            variable.reset()
            variable.setType(type, prefix)
        }
        if (mPoolVariablesCount >= POOL_SIZE) {
            POOL_SIZE *= 2
            mPoolVariables = mPoolVariables.copyOf(POOL_SIZE)
        }
        mPoolVariables[mPoolVariablesCount++] = variable
        return variable
    }

    /*--------------------------------------------------------------------------------------------*/ // Accessors of rows / variables / errors
    /*--------------------------------------------------------------------------------------------*/
    fun getRow(n: Int): ArrayRow? {
        return mRows?.getOrNull(n)
    }

    fun getValueFor(name: String): Float {
        val v = getVariable(name, SolverVariable.Type.UNRESTRICTED) ?: return 0f
        return v.computedValue
    }

    fun getObjectVariableValue(`object`: Any): Int {
        val anchor = `object` as ConstraintAnchor
        if (Chain.USE_CHAIN_OPTIMIZATION) {
            if (anchor.hasFinalValue()) {
                return anchor.finalValue
            }
        }
        val variable = anchor.solverVariable
        return if (variable != null) {
            (variable.computedValue + 0.5f).toInt()
        } else 0
    }

    /**
     * Returns a SolverVariable instance given a name and a type.
     *
     * @param name name of the variable
     * @param type [type][SolverVariable.Type] of the variable
     * @return a SolverVariable instance
     */
    fun getVariable(name: String, type: SolverVariable.Type): SolverVariable? {
        if (mVariables == null) {
            mVariables = HashMap()
        }
        var variable = mVariables!![name]
        if (variable == null) {
            variable = createVariable(name, type)
        }
        return variable
    }
    /*--------------------------------------------------------------------------------------------*/ // System resolution
    /*--------------------------------------------------------------------------------------------*/
    /**
     * Minimize the current goal of the system.
     */
    @Throws(Exception::class)
    fun minimize() {
        if (metrics != null) {
            metrics!!.minimize++
        }
        if (goal!!.isEmpty) {
            if (DEBUG) {
                println("\n*** SKIPPING MINIMIZE! ***\n")
            }
            computeValues()
            return
        }
        if (DEBUG) {
            println("\n*** MINIMIZE ***\n")
        }
        if (graphOptimizer || newgraphOptimizer) {
            if (metrics != null) {
                metrics!!.graphOptimizer++
            }
            var fullySolved = true
            for (i in 0 until numEquations) {
                val r = mRows!![i]
                if (!r!!.isSimpleDefinition) {
                    fullySolved = false
                    break
                }
            }
            if (!fullySolved) {
                minimizeGoal(goal)
            } else {
                if (metrics != null) {
                    metrics!!.fullySolved++
                }
                computeValues()
            }
        } else {
            minimizeGoal(goal)
        }
        if (DEBUG) {
            println("\n*** END MINIMIZE ***\n")
        }
    }

    /**
     * Minimize the given goal with the current system.
     * @param goal the goal to minimize.
     */
    @Throws(Exception::class)
    fun minimizeGoal(goal: Row?) {
        if (metrics != null) {
            metrics!!.minimizeGoal++
            metrics!!.maxVariables = max(metrics!!.maxVariables, mNumColumns.toLong())
            metrics!!.maxRows = max(metrics!!.maxRows, numEquations.toLong())
        }
        // First, let's make sure that the system is in Basic Feasible Solved Form (BFS), i.e.
        // all the constants of the restricted variables should be positive.
        if (DEBUG) {
            println("minimize goal: $goal")
        }
        // we don't need this for now as we incrementally built the system
        // goal.updateFromSystem(this);
        if (DEBUG) {
            displayReadableRows()
        }
        enforceBFS(goal)
        if (DEBUG) {
            println("Goal after enforcing BFS $goal")
            displayReadableRows()
        }
        optimize(goal, false)
        if (DEBUG) {
            println("Goal after optimization $goal")
            displayReadableRows()
        }
        computeValues()
    }

    fun cleanupRows() {
        var i = 0
        while (i < numEquations) {
            val current = mRows!![i]
            if (current!!.variables?.currentSize == 0) {
                current.isSimpleDefinition = true
            }
            if (current.isSimpleDefinition) {
                current.key?.computedValue = current.constantValue
                current.key?.removeFromRow(current)
                for (j in i until numEquations - 1) {
                    mRows!![j] = mRows!![j + 1]
                }
                mRows!![numEquations - 1] = null
                numEquations--
                i--
                if (OPTIMIZED_ENGINE) {
                    cache.optimizedArrayRowPool.release(current)
                } else {
                    cache.arrayRowPool.release(current)
                }
            }
            i++
        }
    }

    /**
     * Add the equation to the system
     * @param row the equation we want to add expressed as a system row.
     */
    fun addConstraint(row: ArrayRow?) {
        if (row == null) {
            return
        }
        if (metrics != null) {
            metrics!!.constraints++
            if (row.isSimpleDefinition) {
                metrics!!.simpleconstraints++
            }
        }
        if (numEquations + 1 >= mMaxRows || mNumColumns + 1 >= mMaxColumns) {
            increaseTableSize()
        }
        if (DEBUG) {
            println("addConstraint <" + row.toReadableString() + ">")
            displayReadableRows()
        }
        var added = false
        if (!row.isSimpleDefinition) {
            // Update the equation with the variables already defined in the system
            row.updateFromSystem(this)
            if (row.isEmpty) {
                return
            }

            // First, ensure that if we have a constant it's positive
            row.ensurePositiveConstant()
            if (DEBUG) {
                println("addConstraint, updated row : " + row.toReadableString())
            }

            // Then pick a good variable to use for the row
            if (row.chooseSubject(this)) {
                // extra variable added... let's try to see if we can remove it
                val extra = createExtraVariable()
                row.key = extra
                val numRows = numEquations
                addRow(row)
                if (numEquations == numRows + 1) {
                    added = true
                    mTempGoal!!.initFromRow(row)
                    optimize(mTempGoal, true)
                    if (extra.definitionId == -1) {
                        if (DEBUG) {
                            println("row added is 0, so get rid of it")
                        }
                        if (row.key === extra) {
                            // move extra to be parametric
                            val pivotCandidate = row.pickPivot(extra)
                            if (pivotCandidate != null) {
                                if (metrics != null) {
                                    metrics!!.pivots++
                                }
                                row.pivot(pivotCandidate)
                            }
                        }
                        if (!row.isSimpleDefinition) {
                            row.key?.updateReferencesWithNewDefinition(this, row)
                        }
                        if (OPTIMIZED_ENGINE) {
                            cache.optimizedArrayRowPool.release(row)
                        } else {
                            cache.arrayRowPool.release(row)
                        }
                        numEquations--
                    }
                }
            }
            if (!row.hasKeyVariable()) {
                // Can happen if row resolves to nil
                if (DEBUG) {
                    println("No variable found to pivot on " + row.toReadableString())
                    displayReadableRows()
                }
                return
            }
        }
        if (!added) {
            addRow(row)
        }
    }

    private fun addRow(row: ArrayRow) {
        row.key?.let { key ->
            if (SIMPLIFY_SYNONYMS && row.isSimpleDefinition) {
                key.setFinalValue(this, row.constantValue)
            } else {
                mRows!![numEquations] = row
                key.definitionId = numEquations
                numEquations++
                key.updateReferencesWithNewDefinition(this, row)
            }
            if (DEBUG) {
                println("Row added: $row")
                println("here is the system:")
                displayReadableRows()
            }
            if (SIMPLIFY_SYNONYMS && hasSimpleDefinition) {
                // compact the rows...
                var i = 0
                while (i < numEquations) {
                    if (mRows!![i] == null) {
                        println("WTF")
                    }
                    if (mRows!![i] != null && mRows!![i]!!.isSimpleDefinition) {
                        val removedRow = mRows!![i]
                        removedRow!!.key?.setFinalValue(this, removedRow.constantValue)
                        if (OPTIMIZED_ENGINE) {
                            cache.optimizedArrayRowPool.release(removedRow)
                        } else {
                            cache.arrayRowPool.release(removedRow)
                        }
                        mRows!![i] = null
                        var lastRow = i + 1
                        for (j in i + 1 until numEquations) {
                            mRows!![j - 1] = mRows!![j]
                            if (mRows!![j - 1]!!.key?.definitionId == j) {
                                mRows!![j - 1]!!.key?.definitionId = j - 1
                            }
                            lastRow = j
                        }
                        if (lastRow < numEquations) {
                            mRows!![lastRow] = null
                        }
                        numEquations--
                        i--
                    }
                    i++
                }
                hasSimpleDefinition = false
            }
        }
    }

    fun removeRow(row: ArrayRow) {
        if (row.isSimpleDefinition && row.key != null) {
            if (row.key?.definitionId != -1) {
                for (i in (row.key?.definitionId ?: 0) until numEquations - 1) {
                    val rowVariable = mRows!![i + 1]!!.key
                    if (rowVariable?.definitionId == i + 1) {
                        rowVariable.definitionId = i
                    }
                    mRows!![i] = mRows!![i + 1]
                }
                numEquations--
            }
            if (row.key?.isFinalValue != true) {
                row.key?.setFinalValue(this, row.constantValue)
            }
            if (OPTIMIZED_ENGINE) {
                cache.optimizedArrayRowPool.release(row)
            } else {
                cache.arrayRowPool.release(row)
            }
        }
    }

    /**
     * Optimize the system given a goal to minimize. The system should be in BFS form.
     * @param goal goal to optimize.
     * @param b
     * @return number of iterations.
     */
    private fun optimize(goal: Row?, b: Boolean): Int {
        if (metrics != null) {
            metrics!!.optimize++
        }
        var done = false
        var tries = 0
        for (i in 0 until mNumColumns) {
            mAlreadyTestedCandidates[i] = false
        }
        if (DEBUG) {
            println("\n****************************")
            println("*       OPTIMIZATION       *")
            println("* mNumColumns: $mNumColumns")
            println("* GOAL: $goal")
            println("****************************\n")
        }
        while (!done) {
            if (metrics != null) {
                metrics!!.iterations++
            }
            tries++
            if (DEBUG) {
                println("\n******************************")
                println("* iteration: $tries")
            }
            if (tries >= 2 * mNumColumns) {
                if (DEBUG) {
                    println("=> Exit optimization because tries " + tries + " >= " + 2 * mNumColumns)
                }
                return tries
            }
            if (goal!!.key != null) {
                mAlreadyTestedCandidates[goal.key!!.id] = true
            }
            val pivotCandidate = goal.getPivotCandidate(this, mAlreadyTestedCandidates)
            if (DEBUG) {
                println("* Pivot candidate: $pivotCandidate")
                println("******************************\n")
            }
            if (pivotCandidate != null) {
                if (mAlreadyTestedCandidates[pivotCandidate.id]) {
                    if (DEBUG) {
                        println("* Pivot candidate $pivotCandidate already tested, let's bail")
                    }
                    return tries
                } else {
                    mAlreadyTestedCandidates[pivotCandidate.id] = true
                }
            }
            if (pivotCandidate != null) {
                if (DEBUG) {
                    println("valid pivot candidate: $pivotCandidate")
                }
                // there's a negative variable in the goal that we can pivot on.
                // We now need to select which equation of the system we should do
                // the pivot on.

                // Let's try to find the equation in the system that we can pivot on.
                // The rules are simple:
                // - only look at restricted variables equations (i.e. Cs)
                // - only look at equations containing the column we are trying to pivot on (duh)
                // - select preferably an equation with strong strength over weak strength
                var min = Float.MAX_VALUE
                var pivotRowIndex = -1
                for (i in 0 until numEquations) {
                    val current = mRows!![i]
                    val variable = current!!.key
                    if (variable?.mType == SolverVariable.Type.UNRESTRICTED) {
                        // skip unrestricted variables equations (to only look at Cs)
                        continue
                    }
                    if (current.isSimpleDefinition) {
                        continue
                    }
                    if (current.hasVariable(pivotCandidate)) {
                        if (DEBUG) {
                            println("equation $i $current contains $pivotCandidate")
                        }
                        // the current row does contains the variable
                        // we want to pivot on
                        val a_j = current.variables?.get(pivotCandidate)
                        if (a_j != null) {
                            if (a_j < 0) {
                                val value = -current.constantValue / a_j
                                if (value < min) {
                                    min = value
                                    pivotRowIndex = i
                                }
                            }
                        }
                    }
                }
                // At this point, we ought to have an equation to pivot on
                if (pivotRowIndex > -1) {
                    // We found an equation to pivot on
                    if (DEBUG) {
                        println("We pivot on $pivotRowIndex")
                    }
                    val pivotEquation = mRows!![pivotRowIndex]
                    pivotEquation!!.key?.definitionId = -1
                    if (metrics != null) {
                        metrics!!.pivots++
                    }
                    pivotEquation.pivot(pivotCandidate)
                    pivotEquation.key?.definitionId = pivotRowIndex
                    pivotEquation.key?.updateReferencesWithNewDefinition(this, pivotEquation)
                    if (DEBUG) {
                        println("new system after pivot:")
                        displayReadableRows()
                        println("optimizing: $goal")
                    }
                    /*
                    try {
                        enforceBFS(goal);
                    } catch (Exception e) {
                        System.out.println("### EXCEPTION " + e);
                        e.printStackTrace();
                    }
                    */
                    // now that we pivoted, we're going to continue looping on the next goal
                    // columns, until we exhaust all the possibilities of improving the system
                } else {
                    if (DEBUG) {
                        println("we couldn't find an equation to pivot upon")
                    }
                }
            } else {
                // There is no candidate goals columns we should try to pivot on,
                // so let's exit the loop.
                if (DEBUG) {
                    println("no more candidate goals to pivot on, let's exit")
                }
                done = true
            }
        }
        return tries
    }

    /**
     * Make sure that the system is in Basic Feasible Solved form (BFS).
     * @param goal the row representing the system goal
     * @return number of iterations
     */
    @Throws(Exception::class)
    private fun enforceBFS(goal: Row?): Int {
        var tries = 0
        var done: Boolean
        if (DEBUG) {
            println("\n#################")
            println("# ENFORCING BFS #")
            println("#################\n")
        }

        // At this point, we might not be in Basic Feasible Solved form (BFS),
        // i.e. one of the restricted equation has a negative constant.
        // Let's check if that's the case or not.
        var infeasibleSystem = false
        for (i in 0 until numEquations) {
            val variable = mRows!![i]!!.key
            if (variable?.mType == SolverVariable.Type.UNRESTRICTED) {
                continue  // C can be either positive or negative.
            }
            if (mRows!![i]!!.constantValue < 0) {
                infeasibleSystem = true
                break
            }
        }

        // The system happens to not be in BFS form, we need to go back to it to properly solve it.
        if (infeasibleSystem) {
            if (DEBUG) {
                println("the current system is infeasible, let's try to fix this.")
            }

            // Going back to BFS form can be done by selecting any equations in Cs containing
            // a negative constant, then selecting a potential pivot variable that would remove
            // this negative constant. Once we have
            done = false
            tries = 0
            while (!done) {
                if (metrics != null) {
                    metrics!!.bfs++
                }
                tries++
                if (DEBUG) {
                    println("iteration on infeasible system $tries")
                }
                var min = Float.MAX_VALUE
                var strength = 0
                var pivotRowIndex = -1
                var pivotColumnIndex = -1
                for (i in 0 until numEquations) {
                    val current = mRows!![i]
                    val variable = current!!.key
                    if (variable?.mType == SolverVariable.Type.UNRESTRICTED) {
                        // skip unrestricted variables equations, as C
                        // can be either positive or negative.
                        continue
                    }
                    if (current.isSimpleDefinition) {
                        continue
                    }
                    if (current.constantValue < 0) {
                        // let's examine this row, see if we can find a good pivot
                        if (DEBUG) {
                            println("looking at pivoting on row $current")
                        }
                        if (SKIP_COLUMNS) {
                            val size = current.variables?.currentSize ?: 0
                            for (j in 0 until size) {
                                val candidate = current.variables?.getVariable(j)
                                val a_j = current.variables?.get(candidate) ?: 0f
                                if (a_j <= 0f) {
                                    continue
                                }
                                if (DEBUG) {
                                    println("candidate for pivot $candidate")
                                }
                                for (k in 0 until SolverVariable.MAX_STRENGTH) {
                                    val value = (candidate?.strengthVector?.get(k) ?: 0f) / a_j
                                    if (value < min && k == strength || k > strength) {
                                        min = value
                                        pivotRowIndex = i
                                        pivotColumnIndex = candidate?.id ?: 0
                                        strength = k
                                    }
                                }
                            }
                        } else {
                            for (j in 1 until mNumColumns) {
                                val candidate = cache.mIndexedVariables[j]
                                val a_j = current.variables?.get(candidate) ?: 0f
                                if (a_j <= 0) {
                                    continue
                                }
                                if (DEBUG) {
                                    println("candidate for pivot $candidate")
                                }
                                for (k in 0 until SolverVariable.MAX_STRENGTH) {
                                    val value = candidate!!.strengthVector[k] / a_j
                                    if (value < min && k == strength || k > strength) {
                                        min = value
                                        pivotRowIndex = i
                                        pivotColumnIndex = j
                                        strength = k
                                    }
                                }
                            }
                        }
                    }
                }
                if (pivotRowIndex != -1) {
                    // We have a pivot!
                    val pivotEquation = mRows!![pivotRowIndex]
                    if (DEBUG) {
                        println(
                            "Pivoting on " + pivotEquation!!.key + " with "
                                    + cache.mIndexedVariables[pivotColumnIndex]
                        )
                    }
                    pivotEquation!!.key?.definitionId = -1
                    if (metrics != null) {
                        metrics!!.pivots++
                    }
                    pivotEquation.pivot(cache.mIndexedVariables[pivotColumnIndex])
                    pivotEquation.key?.definitionId = pivotRowIndex
                    pivotEquation.key?.updateReferencesWithNewDefinition(this, pivotEquation)
                    if (DEBUG) {
                        println("new goal after pivot: $goal")
                        displayRows()
                    }
                } else {
                    done = true
                }
                if (tries > mNumColumns / 2) {
                    // fail safe -- tried too many times
                    done = true
                }
            }
        }
        if (DEBUG) {
            println("the current system should now be feasible [$infeasibleSystem] after $tries iterations")
            displayReadableRows()

            // Let's make sure the system is correct
            infeasibleSystem = false
            for (i in 0 until numEquations) {
                val variable = mRows!![i]!!.key
                if (variable?.mType == SolverVariable.Type.UNRESTRICTED) {
                    continue  // C can be either positive or negative.
                }
                if (mRows!![i]!!.constantValue < 0) {
                    infeasibleSystem = true
                    break
                }
            }
            if (DEBUG && infeasibleSystem) {
                println("IMPOSSIBLE SYSTEM, WTF")
                throw Exception()
            }
            if (infeasibleSystem) {
                return tries
            }
        }
        return tries
    }

    private fun computeValues() {
        for (i in 0 until numEquations) {
            val row = mRows!![i]
            row!!.key?.computedValue = row.constantValue
        }
    }

    /*--------------------------------------------------------------------------------------------*/ // Display utility functions
    /*--------------------------------------------------------------------------------------------*/
    private fun displayRows() {
        displaySolverVariables()
        var s: String? = ""
        for (i in 0 until numEquations) {
            s += mRows!![i]
            s += "\n"
        }
        s += """
             ${goal.toString()}
             
             """.trimIndent()
        println(s)
    }

    fun displayReadableRows() {
        displaySolverVariables()
        var s: String? = """ num vars ${numVariables}
"""
        for (i in 0 until numVariables + 1) {
            val variable = cache.mIndexedVariables[i]
            if (variable != null && variable.isFinalValue) {
                s += """ $[$i] => $variable = ${variable.computedValue}
"""
            }
        }
        s += "\n"
        for (i in 0 until numVariables + 1) {
            val variable = cache.mIndexedVariables[i]
            if (variable != null && variable.isSynonym) {
                val synonym = cache.mIndexedVariables[variable.synonym]
                s += """ ~[$i] => $variable = $synonym + ${variable.synonymDelta}
"""
            }
        }
        s += "\n\n #  "
        for (i in 0 until numEquations) {
            s += mRows!![i]!!.toReadableString()
            s += "\n #  "
        }
        if (goal != null) {
            s += """
                Goal: ${goal}
                
                """.trimIndent()
        }
        println(s)
    }

    fun displayVariablesReadableRows() {
        displaySolverVariables()
        var s: String? = ""
        for (i in 0 until numEquations) {
            if (mRows!![i]!!.key?.mType == SolverVariable.Type.UNRESTRICTED) {
                s += mRows!![i]!!.toReadableString()
                s += "\n"
            }
        }
        s += """
               ${goal.toString()}
               
               """.trimIndent()
        println(s)
    }

    val memoryUsed: Int
        get() {
            var actualRowSize = 0
            for (i in 0 until numEquations) {
                if (mRows!![i] != null) {
                    actualRowSize += mRows!![i]!!.sizeInBytes()
                }
            }
            return actualRowSize
        }

    /**
     * Display current system information
     */
    fun displaySystemInformation() {
        val count = 0
        var rowSize = 0
        for (i in 0 until TABLE_SIZE) {
            if (mRows!![i] != null) {
                rowSize += mRows!![i]!!.sizeInBytes()
            }
        }
        var actualRowSize = 0
        for (i in 0 until numEquations) {
            if (mRows!![i] != null) {
                actualRowSize += mRows!![i]!!.sizeInBytes()
            }
        }
        println(
            "Linear System -> Table size: " + TABLE_SIZE
                    + " (" + getDisplaySize(TABLE_SIZE * TABLE_SIZE)
                    + ") -- row sizes: " + getDisplaySize(rowSize)
                    + ", actual size: " + getDisplaySize(actualRowSize)
                    + " rows: " + numEquations + "/" + mMaxRows
                    + " cols: " + mNumColumns + "/" + mMaxColumns
                    + " " + count + " occupied cells, " + getDisplaySize(count)
        )
    }

    private fun displaySolverVariables() {
        val s = """
            Display Rows (${numEquations}x$mNumColumns)
            
            """.trimIndent()
        /*
        s += ":\n\t | C | ";
        for (int i = 1; i <= mNumColumns; i++) {
            SolverVariable v = mCache.mIndexedVariables[i];
            s += v;
            s += " | ";
        }
        s += "\n";
        */println(s)
    }

    private fun getDisplaySize(n: Int): String {
        val mb = n * 4 / 1024 / 1024
        if (mb > 0) {
            return "$mb Mb"
        }
        val kb = n * 4 / 1024
        return if (kb > 0) {
            "$kb Kb"
        } else "" + n * 4 + " bytes"
    }

    private fun getDisplayStrength(strength: Int): String {
        if (strength == SolverVariable.STRENGTH_LOW) {
            return "LOW"
        }
        if (strength == SolverVariable.STRENGTH_MEDIUM) {
            return "MEDIUM"
        }
        if (strength == SolverVariable.STRENGTH_HIGH) {
            return "HIGH"
        }
        if (strength == SolverVariable.STRENGTH_HIGHEST) {
            return "HIGHEST"
        }
        if (strength == SolverVariable.STRENGTH_EQUALITY) {
            return "EQUALITY"
        }
        if (strength == SolverVariable.STRENGTH_FIXED) {
            return "FIXED"
        }
        return if (strength == SolverVariable.STRENGTH_BARRIER) {
            "BARRIER"
        } else "NONE"
    }
    ////////////////////////////////////////////////////////////////////////////////////////
    // Equations
    ////////////////////////////////////////////////////////////////////////////////////////
    /**
     * Add an equation of the form a >= b + margin
     * @param a variable a
     * @param b variable b
     * @param margin margin
     * @param strength strength used
     */
    fun addGreaterThan(a: SolverVariable?, b: SolverVariable?, margin: Int, strength: Int) {
        if (a == null || b == null)
        {
            return
        }

        if (DEBUG_CONSTRAINTS) {
            println("-> " + a + " >= " + b + (if (margin != 0) " + $margin" else "") + " " + getDisplayStrength(strength))
        }
        val row = createRow()
        val slack = createSlackVariable()
        slack.strength = 0
        row.createRowGreaterThan(a, b, slack, margin)
        if (strength != SolverVariable.STRENGTH_FIXED) {
            val slackValue = row.variables?.get(slack) ?: 0f
            addSingleError(row, (-1 * slackValue).toInt(), strength)
        }
        addConstraint(row)
    }

    fun addGreaterBarrier(a: SolverVariable?, b: SolverVariable?, margin: Int, hasMatchConstraintWidgets: Boolean) {
        if (a == null || b == null)
        {
            return
        }

        if (DEBUG_CONSTRAINTS) {
            println("-> Barrier $a >= $b")
        }
        val row = createRow()
        val slack = createSlackVariable()
        slack.strength = 0
        row.createRowGreaterThan(a, b, slack, margin)
        addConstraint(row)
    }

    /**
     * Add an equation of the form a <= b + margin
     * @param a variable a
     * @param b variable b
     * @param margin margin
     * @param strength strength used
     */
    fun addLowerThan(a: SolverVariable?, b: SolverVariable?, margin: Int, strength: Int) {
        if (a == null || b == null)
        {
            return
        }

        if (DEBUG_CONSTRAINTS) {
            println("-> " + a + " <= " + b + (if (margin != 0) " + $margin" else "") + " " + getDisplayStrength(strength))
        }
        val row = createRow()
        val slack = createSlackVariable()
        slack.strength = 0
        row.createRowLowerThan(a, b, slack, margin)
        if (strength != SolverVariable.STRENGTH_FIXED) {
            val slackValue = row.variables?.get(slack) ?: 0f
            addSingleError(row, (-1 * slackValue).toInt(), strength)
        }
        addConstraint(row)
    }

    fun addLowerBarrier(a: SolverVariable?, b: SolverVariable?, margin: Int, hasMatchConstraintWidgets: Boolean) {
        if (a == null || b == null)
        {
            return
        }

        if (DEBUG_CONSTRAINTS) {
            println("-> Barrier $a <= $b")
        }
        val row = createRow()
        val slack = createSlackVariable()
        slack.strength = 0
        row.createRowLowerThan(a, b, slack, margin)
        addConstraint(row)
    }

    /**
     * Add an equation of the form (1 - bias) * (a - b) = bias * (c - d)
     * @param a variable a
     * @param b variable b
     * @param m1 margin 1
     * @param bias bias between ab - cd
     * @param c variable c
     * @param d variable d
     * @param m2 margin 2
     * @param strength strength used
     */
    fun addCentering(
        a: SolverVariable?, b: SolverVariable?, m1: Int, bias: Float,
        c: SolverVariable?, d: SolverVariable?, m2: Int, strength: Int
    ) {
        if (b == null || c == null)
        {
            return
        }

        if (DEBUG_CONSTRAINTS) {
            println(
                "-> [center bias: " + bias + "] : " + a + " - " + b
                        + " - " + m1
                        + " = " + c + " - " + d + " - " + m2
                        + " " + getDisplayStrength(strength)
            )
        }
        val row = createRow()
        row.createRowCentering(a, b, m1, bias, c, d, m2)
        if (strength != SolverVariable.STRENGTH_FIXED) {
            row.addError(this, strength)
        }
        addConstraint(row)
    }

    fun addRatio(a: SolverVariable?, b: SolverVariable?, c: SolverVariable?, d: SolverVariable?, ratio: Float, strength: Int) {
        if (DEBUG_CONSTRAINTS) {
            println("-> [ratio: " + ratio + "] : " + a + " = " + b + " + (" + c + " - " + d + ") * " + ratio + " " + getDisplayStrength(strength))
        }
        val row = createRow()
        row.createRowDimensionRatio(a, b, c, d, ratio)
        if (strength != SolverVariable.STRENGTH_FIXED) {
            row.addError(this, strength)
        }
        addConstraint(row)
    }

    fun addSynonym(a: SolverVariable, b: SolverVariable, margin: Int) {
        var a = a
        var b = b
        var margin = margin
        if (a.definitionId == -1 && margin == 0) {
            if (DEBUG_CONSTRAINTS) {
                println("(S) -> " + a + " = " + b + if (margin != 0) " + $margin" else "")
            }
            if (b.isSynonym) {
                margin += b.synonymDelta.toInt()
                b = cache.mIndexedVariables[b.synonym]!!
            }
            if (a.isSynonym) {
                margin -= a.synonymDelta.toInt()
                a = cache.mIndexedVariables[a.synonym]!!
            } else {
                a.setSynonym(this, b, 0f)
            }
        } else {
            addEquality(a, b, margin, SolverVariable.STRENGTH_FIXED)
        }
    }

    /**
     * Add an equation of the form a = b + margin
     * @param a variable a
     * @param b variable b
     * @param margin margin used
     * @param strength strength used
     */
    fun addEquality(a: SolverVariable?, b: SolverVariable?, margin: Int, strength: Int): ArrayRow? {
        var a = a ?: return null
        var b = b ?: return null
        var margin = margin
        if (USE_BASIC_SYNONYMS && strength == SolverVariable.STRENGTH_FIXED && b.isFinalValue && a.definitionId == -1) {
            if (DEBUG_CONSTRAINTS) {
                println("=> " + a + " = " + b + (if (margin != 0) " + $margin" else "") + " = " + (b.computedValue + margin) + " (Synonym)")
            }
            a.setFinalValue(this, b.computedValue + margin)
            return null
        }
        if (false && USE_SYNONYMS && strength == SolverVariable.STRENGTH_FIXED && a.definitionId == -1 && margin == 0) {
            if (DEBUG_CONSTRAINTS) {
                println("(S) -> " + a + " = " + b + (if (margin != 0) " + $margin" else "") + " " + getDisplayStrength(strength))
            }
            if (b.isSynonym) {
                margin += b.synonymDelta.toInt()
                b = cache.mIndexedVariables[b.synonym]!!
            }
            if (a.isSynonym) {
                margin -= a.synonymDelta.toInt()
                a = cache.mIndexedVariables[a.synonym]!!
            } else {
                a.setSynonym(this, b, margin.toFloat())
                return null
            }
        }
        if (DEBUG_CONSTRAINTS) {
            println("-> " + a + " = " + b + (if (margin != 0) " + $margin" else "") + " " + getDisplayStrength(strength))
        }
        val row = createRow()
        row.createRowEquals(a, b, margin)
        if (strength != SolverVariable.STRENGTH_FIXED) {
            row.addError(this, strength)
        }
        addConstraint(row)
        return row
    }

    /**
     * Add an equation of the form a = value
     * @param a variable a
     * @param value the value we set
     */
    fun addEquality(a: SolverVariable?, value: Int) {
        if (a == null)
        {
            return
        }

        if (USE_BASIC_SYNONYMS && a.definitionId == -1) {
            if (DEBUG_CONSTRAINTS) {
                println("=> $a = $value (Synonym)")
            }
            a.setFinalValue(this, value.toFloat())
            for (i in 0 until numVariables + 1) {
                val variable = cache.mIndexedVariables[i]
                if (variable != null && variable.isSynonym && variable.synonym == a.id) {
                    variable.setFinalValue(this, value + variable.synonymDelta)
                }
            }
            return
        }
        if (DEBUG_CONSTRAINTS) {
            println("-> $a = $value")
        }
        val idx = a.definitionId
        if (a.definitionId != -1) {
            val row = mRows!![idx]
            if (row!!.isSimpleDefinition) {
                row.constantValue = value.toFloat()
            } else {
                if (row.variables?.currentSize == 0) {
                    row.isSimpleDefinition = true
                    row.constantValue = value.toFloat()
                } else {
                    val newRow = createRow()
                    newRow.createRowEquals(a, value)
                    addConstraint(newRow)
                }
            }
        } else {
            val row = createRow()
            row.createRowDefinition(a, value)
            addConstraint(row)
        }
    }

    /**
     * Add the equations constraining a widget center to another widget center, positioned
     * on a circle, following an angle and radius
     *
     * @param widget
     * @param target
     * @param angle from 0 to 360
     * @param radius the distance between the two centers
     */
    fun addCenterPoint(widget: ConstraintWidget, target: ConstraintWidget, angle: Float, radius: Int) {
        val Al = createObjectVariable(widget.getAnchor(ConstraintAnchor.Type.LEFT))
        val At = createObjectVariable(widget.getAnchor(ConstraintAnchor.Type.TOP))
        val Ar = createObjectVariable(widget.getAnchor(ConstraintAnchor.Type.RIGHT))
        val Ab = createObjectVariable(widget.getAnchor(ConstraintAnchor.Type.BOTTOM))
        val Bl = createObjectVariable(target.getAnchor(ConstraintAnchor.Type.LEFT))
        val Bt = createObjectVariable(target.getAnchor(ConstraintAnchor.Type.TOP))
        val Br = createObjectVariable(target.getAnchor(ConstraintAnchor.Type.RIGHT))
        val Bb = createObjectVariable(target.getAnchor(ConstraintAnchor.Type.BOTTOM))
        var row = createRow()
        var angleComponent = (sin(angle.toDouble()) * radius).toFloat()
        row.createRowWithAngle(At, Ab, Bt, Bb, angleComponent)
        addConstraint(row)
        row = createRow()
        angleComponent = (cos(angle.toDouble()) * radius).toFloat()
        row.createRowWithAngle(Al, Ar, Bl, Br, angleComponent)
        addConstraint(row)
    }

    companion object {
        const val FULL_DEBUG = false
        const val DEBUG = false
        const val MEASURE = false
        private const val DEBUG_CONSTRAINTS = FULL_DEBUG
        @JvmField
        var USE_DEPENDENCY_ORDERING = false
        var USE_BASIC_SYNONYMS = true
        @JvmField
        var SIMPLIFY_SYNONYMS = true
        var USE_SYNONYMS = true
        var SKIP_COLUMNS = true
        var OPTIMIZED_ENGINE = false

        /*
     * Default size for the object pools
     */
        private var POOL_SIZE = 1000
        var metrics: Metrics? = null
        var ARRAY_ROW_CREATION: Long = 0
        var OPTIMIZED_ARRAY_ROW_CREATION: Long = 0

        /**
         * Create a constraint to express A = C * percent
         * @param linearSystem the system we create the row on
         * @param variableA variable a
         * @param variableC variable c
         * @param percent the percent used
         * @return the created row
         */
        @JvmStatic
        fun createRowDimensionPercent(
            linearSystem: LinearSystem,
            variableA: SolverVariable,
            variableC: SolverVariable,
            percent: Float
        ): ArrayRow {
            if (DEBUG_CONSTRAINTS) {
                println("-> $variableA = $variableC * $percent")
            }
            val row = linearSystem.createRow()
            return row.createRowDimensionPercent(variableA, variableC, percent)
        }
    }

    init {
        mRows = arrayOfNulls(TABLE_SIZE)
        releaseRows()
        cache = Cache()
        goal = PriorityGoalRow(cache)
        mTempGoal = if (OPTIMIZED_ENGINE) {
            ValuesRow(cache)
        } else {
            ArrayRow(cache)
        }
    }
}