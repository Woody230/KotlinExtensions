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

import kotlin.jvm.JvmField

open class ArrayRow : LinearSystem.Row {
    override var key: SolverVariable? = null
    @JvmField
    var constantValue = 0f
    var used = false
    var variablesToUpdate = ArrayList<SolverVariable>()
    @JvmField
    var variables: ArrayRowVariables? = null

    interface ArrayRowVariables {
        val currentSize: Int
        fun getVariable(i: Int): SolverVariable?
        fun getVariableValue(i: Int): Float
        operator fun get(variable: SolverVariable?): Float
        fun indexOf(variable: SolverVariable?): Int
        fun display()
        fun clear()
        operator fun contains(v: SolverVariable?): Boolean
        fun put(variable: SolverVariable?, value: Float)
        fun sizeInBytes(): Int
        fun invert()
        fun remove(v: SolverVariable?, removeFromDefinition: Boolean): Float
        fun divideByAmount(amount: Float)
        fun add(`var`: SolverVariable?, value: Float, removeFromDefinition: Boolean)
        fun use(definition: ArrayRow?, removeFromDefinition: Boolean): Float
    }

    var isSimpleDefinition = false

    constructor() {}
    constructor(cache: Cache) {
        variables = ArrayLinkedVariables(this, cache)
        //variables = new OptimizedSolverVariableValues(this, cache);
    }

    fun hasKeyVariable(): Boolean {
        return !(key == null
                || (key!!.mType != SolverVariable.Type.UNRESTRICTED
                && constantValue < 0))
    }

    override fun toString(): String {
        return toReadableString()
    }

    fun toReadableString(): String {
        var s = ""
        if (key == null) {
            s += "0"
        } else {
            s += key
        }
        s += " = "
        var addedVariable = false
        if (constantValue != 0f) {
            s += constantValue
            addedVariable = true
        }
        val count = variables!!.currentSize
        for (i in 0 until count) {
            val v = variables!!.getVariable(i) ?: continue
            var amount = variables!!.getVariableValue(i)
            if (amount == 0f) {
                continue
            }
            val name = v.toString()
            if (!addedVariable) {
                if (amount < 0) {
                    s += "- "
                    amount *= -1f
                }
            } else {
                if (amount > 0) {
                    s += " + "
                } else {
                    s += " - "
                    amount *= -1f
                }
            }
            s += if (amount == 1f) {
                name
            } else {
                "$amount $name"
            }
            addedVariable = true
        }
        if (!addedVariable) {
            s += "0.0"
        }
        if (DEBUG) {
            variables!!.display()
        }
        return s
    }

    fun reset() {
        key = null
        variables!!.clear()
        constantValue = 0f
        isSimpleDefinition = false
    }

    fun hasVariable(v: SolverVariable?): Boolean {
        return variables!!.contains(v)
    }

    fun createRowDefinition(variable: SolverVariable, value: Int): ArrayRow {
        key = variable
        variable.computedValue = value.toFloat()
        constantValue = value.toFloat()
        isSimpleDefinition = true
        return this
    }

    fun createRowEquals(variable: SolverVariable?, value: Int): ArrayRow {
        if (value < 0) {
            constantValue = (-1 * value).toFloat()
            variables!!.put(variable, 1f)
        } else {
            constantValue = value.toFloat()
            variables!!.put(variable, -1f)
        }
        return this
    }

    fun createRowEquals(variableA: SolverVariable?, variableB: SolverVariable?, margin: Int): ArrayRow {
        var inverse = false
        if (margin != 0) {
            var m = margin
            if (m < 0) {
                m = -1 * m
                inverse = true
            }
            constantValue = m.toFloat()
        }
        if (!inverse) {
            variables!!.put(variableA, -1f)
            variables!!.put(variableB, 1f)
        } else {
            variables!!.put(variableA, 1f)
            variables!!.put(variableB, -1f)
        }
        return this
    }

    fun addSingleError(error: SolverVariable?, sign: Int): ArrayRow {
        variables!!.put(error, sign.toFloat())
        return this
    }

    fun createRowGreaterThan(
        variableA: SolverVariable?,
        variableB: SolverVariable?, slack: SolverVariable?,
        margin: Int
    ): ArrayRow {
        var inverse = false
        if (margin != 0) {
            var m = margin
            if (m < 0) {
                m = -1 * m
                inverse = true
            }
            constantValue = m.toFloat()
        }
        if (!inverse) {
            variables!!.put(variableA, -1f)
            variables!!.put(variableB, 1f)
            variables!!.put(slack, 1f)
        } else {
            variables!!.put(variableA, 1f)
            variables!!.put(variableB, -1f)
            variables!!.put(slack, -1f)
        }
        return this
    }

    fun createRowGreaterThan(a: SolverVariable?, b: Int, slack: SolverVariable?): ArrayRow {
        constantValue = b.toFloat()
        variables!!.put(a, -1f)
        return this
    }

    fun createRowLowerThan(
        variableA: SolverVariable?, variableB: SolverVariable?,
        slack: SolverVariable?, margin: Int
    ): ArrayRow {
        var inverse = false
        if (margin != 0) {
            var m = margin
            if (m < 0) {
                m = -1 * m
                inverse = true
            }
            constantValue = m.toFloat()
        }
        if (!inverse) {
            variables!!.put(variableA, -1f)
            variables!!.put(variableB, 1f)
            variables!!.put(slack, -1f)
        } else {
            variables!!.put(variableA, 1f)
            variables!!.put(variableB, -1f)
            variables!!.put(slack, 1f)
        }
        return this
    }

    fun createRowEqualMatchDimensions(
        currentWeight: Float, totalWeights: Float, nextWeight: Float,
        variableStartA: SolverVariable?,
        variableEndA: SolverVariable?,
        variableStartB: SolverVariable?,
        variableEndB: SolverVariable?
    ): ArrayRow {
        constantValue = 0f
        if (totalWeights == 0f || currentWeight == nextWeight) {
            // endA - startA == endB - startB
            // 0 = startA - endA + endB - startB
            variables!!.put(variableStartA, 1f)
            variables!!.put(variableEndA, -1f)
            variables!!.put(variableEndB, 1f)
            variables!!.put(variableStartB, -1f)
        } else {
            if (currentWeight == 0f) {
                variables!!.put(variableStartA, 1f)
                variables!!.put(variableEndA, -1f)
            } else if (nextWeight == 0f) {
                variables!!.put(variableStartB, 1f)
                variables!!.put(variableEndB, -1f)
            } else {
                val cw = currentWeight / totalWeights
                val nw = nextWeight / totalWeights
                val w = cw / nw

                // endA - startA == w * (endB - startB)
                // 0 = startA - endA + w * (endB - startB)
                variables!!.put(variableStartA, 1f)
                variables!!.put(variableEndA, -1f)
                variables!!.put(variableEndB, w)
                variables!!.put(variableStartB, -w)
            }
        }
        return this
    }

    fun createRowEqualDimension(
        currentWeight: Float, totalWeights: Float, nextWeight: Float,
        variableStartA: SolverVariable?, marginStartA: Int,
        variableEndA: SolverVariable?, marginEndA: Int,
        variableStartB: SolverVariable?, marginStartB: Int,
        variableEndB: SolverVariable?, marginEndB: Int
    ): ArrayRow {
        if (totalWeights == 0f || currentWeight == nextWeight) {
            // endA - startA + marginStartA + marginEndA == endB - startB + marginStartB + marginEndB
            // 0 = startA - endA - marginStartA - marginEndA + endB - startB + marginStartB + marginEndB
            // 0 = (- marginStartA - marginEndA + marginStartB + marginEndB) + startA - endA + endB - startB
            constantValue = (-marginStartA - marginEndA + marginStartB + marginEndB).toFloat()
            variables!!.put(variableStartA, 1f)
            variables!!.put(variableEndA, -1f)
            variables!!.put(variableEndB, 1f)
            variables!!.put(variableStartB, -1f)
        } else {
            val cw = currentWeight / totalWeights
            val nw = nextWeight / totalWeights
            val w = cw / nw
            // (endA - startA + marginStartA + marginEndA) = w * (endB - startB) + marginStartB + marginEndB;
            // 0 = (startA - endA - marginStartA - marginEndA) + w * (endB - startB) + marginStartB + marginEndB
            // 0 = (- marginStartA - marginEndA + marginStartB + marginEndB) + startA - endA + w * endB - w * startB
            constantValue = -marginStartA - marginEndA + w * marginStartB + w * marginEndB
            variables!!.put(variableStartA, 1f)
            variables!!.put(variableEndA, -1f)
            variables!!.put(variableEndB, w)
            variables!!.put(variableStartB, -w)
        }
        return this
    }

    fun createRowCentering(
        variableA: SolverVariable?, variableB: SolverVariable, marginA: Int,
        bias: Float, variableC: SolverVariable, variableD: SolverVariable?, marginB: Int
    ): ArrayRow {
        if (variableB === variableC) {
            // centering on the same position
            // B - A == D - B
            // 0 = A + D - 2 * B
            variables!!.put(variableA, 1f)
            variables!!.put(variableD, 1f)
            variables!!.put(variableB, -2f)
            return this
        }
        if (bias == 0.5f) {
            // don't bother applying the bias, we are centered
            // A - B = C - D
            // 0 = A - B - C + D
            // with margin:
            // A - B - Ma = C - D - Mb
            // 0 = A - B - C + D - Ma + Mb
            variables!!.put(variableA, 1f)
            variables!!.put(variableB, -1f)
            variables!!.put(variableC, -1f)
            variables!!.put(variableD, 1f)
            if (marginA > 0 || marginB > 0) {
                constantValue = (-marginA + marginB).toFloat()
            }
        } else if (bias <= 0) {
            // A = B + m
            variables!!.put(variableA, -1f)
            variables!!.put(variableB, 1f)
            constantValue = marginA.toFloat()
        } else if (bias >= 1) {
            // D = C - m
            variables!!.put(variableD, -1f)
            variables!!.put(variableC, 1f)
            constantValue = -marginB.toFloat()
        } else {
            variables!!.put(variableA, 1 * (1 - bias))
            variables!!.put(variableB, -1 * (1 - bias))
            variables!!.put(variableC, -1 * bias)
            variables!!.put(variableD, 1 * bias)
            if (marginA > 0 || marginB > 0) {
                constantValue = -marginA * (1 - bias) + marginB * bias
            }
        }
        return this
    }

    fun addError(system: LinearSystem, strength: Int): ArrayRow {
        variables!!.put(system.createErrorVariable(strength, "ep"), 1f)
        variables!!.put(system.createErrorVariable(strength, "em"), -1f)
        return this
    }

    fun createRowDimensionPercent(
        variableA: SolverVariable?,
        variableC: SolverVariable?, percent: Float
    ): ArrayRow {
        variables!!.put(variableA, -1f)
        variables!!.put(variableC, percent)
        return this
    }

    /**
     * Create a constraint to express `A = B + (C - D)` * ratio
     * We use this for ratio, where for example `Right = Left + (Bottom - Top) * percent`
     *
     * @param variableA variable A
     * @param variableB variable B
     * @param variableC variable C
     * @param variableD variable D
     * @param ratio ratio between AB and CD
     * @return the row
     */
    fun createRowDimensionRatio(
        variableA: SolverVariable?, variableB: SolverVariable?,
        variableC: SolverVariable?, variableD: SolverVariable?, ratio: Float
    ): ArrayRow {
        // A = B + (C - D) * ratio
        variables!!.put(variableA, -1f)
        variables!!.put(variableB, 1f)
        variables!!.put(variableC, ratio)
        variables!!.put(variableD, -ratio)
        return this
    }

    /**
     * Create a constraint to express At + (Ab-At)/2 = Bt + (Bb-Bt)/2 - angle
     *
     * @param at
     * @param ab
     * @param bt
     * @param bb
     * @param angleComponent
     * @return
     */
    fun createRowWithAngle(at: SolverVariable?, ab: SolverVariable?, bt: SolverVariable?, bb: SolverVariable?, angleComponent: Float): ArrayRow {
        variables!!.put(bt, 0.5f)
        variables!!.put(bb, 0.5f)
        variables!!.put(at, -0.5f)
        variables!!.put(ab, -0.5f)
        constantValue = -angleComponent
        return this
    }

    fun sizeInBytes(): Int {
        var size = 0
        if (key != null) {
            size += 4 // object
        }
        size += 4 // constantValue
        size += 4 // used
        size += variables!!.sizeInBytes()
        return size
    }

    fun ensurePositiveConstant() {
        // Ensure that if we have a constant it's positive
        if (constantValue < 0) {
            // If not, simply multiply the equation by -1
            constantValue *= -1f
            variables!!.invert()
        }
    }

    /**
     * Pick a subject variable out of the existing ones.
     * - if a variable is unrestricted
     * - or if it's a negative new variable (not found elsewhere)
     * - otherwise we have to add a new additional variable
     *
     * @return true if we added an extra variable to the system
     */
    fun chooseSubject(system: LinearSystem): Boolean {
        var addedExtra = false
        val pivotCandidate = chooseSubjectInVariables(system)
        if (pivotCandidate == null) {
            // need to add extra variable
            addedExtra = true
        } else {
            pivot(pivotCandidate)
        }
        if (variables!!.currentSize == 0) {
            isSimpleDefinition = true
        }
        return addedExtra
    }

    /**
     * Pick a subject variable out of the existing ones.
     * - if a variable is unrestricted
     * - or if it's a negative new variable (not found elsewhere)
     * - otherwise we return null
     *
     * @return a candidate variable we can pivot on or null if not found
     */
    fun chooseSubjectInVariables(system: LinearSystem): SolverVariable? {
        // if unrestricted, pick it
        // if restricted, needs to be < 0 and new
        //
        var restrictedCandidate: SolverVariable? = null
        var unrestrictedCandidate: SolverVariable? = null
        var unrestrictedCandidateAmount = 0f
        var restrictedCandidateAmount = 0f
        var unrestrictedCandidateIsNew = false
        var restrictedCandidateIsNew = false
        val currentSize = variables!!.currentSize
        for (i in 0 until currentSize) {
            val amount = variables!!.getVariableValue(i)
            val variable = variables!!.getVariable(i)
            if (variable?.mType == SolverVariable.Type.UNRESTRICTED) {
                if (unrestrictedCandidate == null) {
                    unrestrictedCandidate = variable
                    unrestrictedCandidateAmount = amount
                    unrestrictedCandidateIsNew = isNew(variable, system)
                } else if (unrestrictedCandidateAmount > amount) {
                    unrestrictedCandidate = variable
                    unrestrictedCandidateAmount = amount
                    unrestrictedCandidateIsNew = isNew(variable, system)
                } else if (!unrestrictedCandidateIsNew && isNew(variable, system)) {
                    unrestrictedCandidate = variable
                    unrestrictedCandidateAmount = amount
                    unrestrictedCandidateIsNew = true
                }
            } else if (unrestrictedCandidate == null) {
                if (amount < 0) {
                    if (restrictedCandidate == null) {
                        restrictedCandidate = variable
                        restrictedCandidateAmount = amount
                        restrictedCandidateIsNew = isNew(variable, system)
                    } else if (restrictedCandidateAmount > amount) {
                        restrictedCandidate = variable
                        restrictedCandidateAmount = amount
                        restrictedCandidateIsNew = isNew(variable, system)
                    } else if (!restrictedCandidateIsNew && isNew(variable, system)) {
                        restrictedCandidate = variable
                        restrictedCandidateAmount = amount
                        restrictedCandidateIsNew = true
                    }
                }
            }
        }
        return unrestrictedCandidate ?: restrictedCandidate
    }

    /**
     * Returns true if the variable is new to the system, i.e. is already present
     * in one of the rows. This function is called while choosing the subject of a new row.
     *
     * @param variable the variable to check for
     * @param system the linear system we check
     * @return
     */
    private fun isNew(variable: SolverVariable?, system: LinearSystem): Boolean {
        if (FULL_NEW_CHECK) {
            var isNew = true
            for (i in 0 until system.numEquations) {
                val row = system.mRows!![i]!!
                if (row.hasVariable(variable)) {
                    isNew = false
                }
            }
            if ((variable?.usageInRowCount ?: 0) <= 1 != isNew) {
                println("Problem with usage tracking")
            }
            return isNew
        }
        // We maintain a usage count -- variables are ref counted if they are present
        // in the right side of a row or not. If the count is zero or one, the variable
        // is new (if one, it means it exist in a row, but this is the row we insert)
        return (variable?.usageInRowCount ?: 0) <= 1
    }

    fun pivot(v: SolverVariable?) {
        if (key != null) {
            // first, move back the variable to its column
            variables!!.put(key, -1f)
            key!!.definitionId = -1
            key = null
        }
        val amount = variables!!.remove(v, true) * -1
        key = v
        if (amount == 1f) {
            return
        }
        constantValue = constantValue / amount
        variables!!.divideByAmount(amount)
    }

    // Row compatibility
    override val isEmpty: Boolean
        get() = key == null && constantValue == 0f && variables!!.currentSize == 0

    override fun updateFromRow(system: LinearSystem?, definition: ArrayRow?, removeFromDefinition: Boolean) {
        val value = variables!!.use(definition, removeFromDefinition)
        constantValue += definition!!.constantValue * value
        if (removeFromDefinition) {
            definition.key!!.removeFromRow(this)
        }
        if (LinearSystem.SIMPLIFY_SYNONYMS
            && key != null && variables!!.currentSize == 0
        ) {
            isSimpleDefinition = true
            system!!.hasSimpleDefinition = true
        }
    }

    override fun updateFromFinalVariable(system: LinearSystem?, variable: SolverVariable?, removeFromDefinition: Boolean) {
        if (variable == null || !variable.isFinalValue) {
            return
        }
        val value = variables!![variable]
        constantValue += variable.computedValue * value
        variables!!.remove(variable, removeFromDefinition)
        if (removeFromDefinition) {
            variable.removeFromRow(this)
        }
        if (LinearSystem.SIMPLIFY_SYNONYMS
            && variables!!.currentSize == 0
        ) {
            isSimpleDefinition = true
            system!!.hasSimpleDefinition = true
        }
    }

    fun updateFromSynonymVariable(system: LinearSystem?, variable: SolverVariable?, removeFromDefinition: Boolean) {
        if (variable == null || !variable.isSynonym) {
            return
        }
        val value = variables!![variable]
        constantValue += variable.synonymDelta * value
        variables!!.remove(variable, removeFromDefinition)
        if (removeFromDefinition) {
            variable.removeFromRow(this)
        }
        variables!!.add(system!!.cache.mIndexedVariables[variable.synonym], value, removeFromDefinition)
        if (LinearSystem.SIMPLIFY_SYNONYMS
            && variables!!.currentSize == 0
        ) {
            isSimpleDefinition = true
            system.hasSimpleDefinition = true
        }
    }

    private fun pickPivotInVariables(avoid: BooleanArray?, exclude: SolverVariable?): SolverVariable? {
        val all = true
        var value = 0f
        var pivot: SolverVariable? = null
        var pivotSlack: SolverVariable? = null
        var valueSlack = 0f
        val currentSize = variables!!.currentSize
        for (i in 0 until currentSize) {
            val currentValue = variables!!.getVariableValue(i)
            if (currentValue < 0) {
                // We can return the first negative candidate as in ArrayLinkedVariables
                // they are already sorted by id
                val v = variables!!.getVariable(i) ?: continue
                if (!(avoid != null && avoid[v.id] || v === exclude)) {
                    if (all) {
                        if (v.mType == SolverVariable.Type.SLACK
                            || v.mType == SolverVariable.Type.ERROR
                        ) {
                            if (currentValue < value) {
                                value = currentValue
                                pivot = v
                            }
                        }
                    } else {
                        if (v.mType == SolverVariable.Type.SLACK) {
                            if (currentValue < valueSlack) {
                                valueSlack = currentValue
                                pivotSlack = v
                            }
                        } else if (v?.mType == SolverVariable.Type.ERROR) {
                            if (currentValue < value) {
                                value = currentValue
                                pivot = v
                            }
                        }
                    }
                }
            }
        }
        return if (all) {
            pivot
        } else pivot ?: pivotSlack
    }

    fun pickPivot(exclude: SolverVariable?): SolverVariable? {
        return pickPivotInVariables(null, exclude)
    }

    override fun getPivotCandidate(system: LinearSystem?, avoid: BooleanArray?): SolverVariable? {
        return pickPivotInVariables(avoid, null)
    }

    override fun clear() {
        variables!!.clear()
        key = null
        constantValue = 0f
    }

    /**
     * Used to initiate a goal from a given row (to see if we can remove an extra var)
     * @param row
     */
    override fun initFromRow(row: LinearSystem.Row?) {
        if (row is ArrayRow) {
            val copiedRow = row
            key = null
            variables!!.clear()
            for (i in 0 until copiedRow.variables!!.currentSize) {
                val `var` = copiedRow.variables!!.getVariable(i)
                val `val` = copiedRow.variables!!.getVariableValue(i)
                variables!!.add(`var`, `val`, true)
            }
        }
    }

    override fun addError(error: SolverVariable?) {
        var weight = 1f
        if (error!!.strength == SolverVariable.STRENGTH_LOW) {
            weight = 1f
        } else if (error.strength == SolverVariable.STRENGTH_MEDIUM) {
            weight = 1E3f
        } else if (error.strength == SolverVariable.STRENGTH_HIGH) {
            weight = 1E6f
        } else if (error.strength == SolverVariable.STRENGTH_HIGHEST) {
            weight = 1E9f
        } else if (error.strength == SolverVariable.STRENGTH_EQUALITY) {
            weight = 1E12f
        }
        variables!!.put(error, weight)
    }

    override fun updateFromSystem(system: LinearSystem?) {
        if (system?.mRows?.count() == 0) {
            return
        }
        var done = false
        while (!done) {
            val currentSize = variables!!.currentSize
            for (i in 0 until currentSize) {
                val variable = variables!!.getVariable(i) ?: continue
                if (variable?.definitionId != -1 || variable.isFinalValue || variable.isSynonym) {
                    variablesToUpdate.add(variable)
                }
            }
            val size = variablesToUpdate.size
            if (size > 0) {
                for (i in 0 until size) {
                    val variable = variablesToUpdate[i]
                    if (variable.isFinalValue) {
                        updateFromFinalVariable(system, variable, true)
                    } else if (variable.isSynonym) {
                        updateFromSynonymVariable(system, variable, true)
                    } else {
                        updateFromRow(system, system?.mRows!![variable.definitionId], true)
                    }
                }
                variablesToUpdate.clear()
            } else {
                done = true
            }
        }
        if (LinearSystem.SIMPLIFY_SYNONYMS
            && key != null && variables!!.currentSize == 0
        ) {
            isSimpleDefinition = true
            system?.hasSimpleDefinition = true
        }
    }

    companion object {
        private const val DEBUG = false
        private const val FULL_NEW_CHECK = false // full validation (debug purposes)
    }
}