/*
 * Copyright (C) 2020 The Android Open Source Project
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

import kotlin.math.abs

/**
 * Implements a row containing goals taking in account priorities.
 */
class PriorityGoalRow(var mCache: Cache) : ArrayRow(mCache) {
    private val TABLE_SIZE = 128
    private var arrayGoals = arrayOfNulls<SolverVariable>(TABLE_SIZE)
    private var sortArray = arrayOfNulls<SolverVariable>(TABLE_SIZE)
    private var numGoals = 0
    var accessor = GoalVariableAccessor(this)

    inner class GoalVariableAccessor(var row: PriorityGoalRow) {
        var variable: SolverVariable? = null
        fun init(variable: SolverVariable?) {
            this.variable = variable
        }

        fun addToGoal(other: SolverVariable, value: Float): Boolean {
            if (variable!!.inGoal) {
                var empty = true
                for (i in 0 until SolverVariable.MAX_STRENGTH) {
                    variable!!.goalStrengthVector[i] += other.goalStrengthVector[i] * value
                    val v = variable!!.goalStrengthVector[i]
                    if (abs(v) < epsilon) {
                        variable!!.goalStrengthVector[i] = 0f
                    } else {
                        empty = false
                    }
                }
                if (empty) {
                    removeGoal(variable)
                }
            } else {
                for (i in 0 until SolverVariable.MAX_STRENGTH) {
                    val strength = other.goalStrengthVector[i]
                    if (strength != 0f) {
                        var v = value * strength
                        if (abs(v) < epsilon) {
                            v = 0f
                        }
                        variable!!.goalStrengthVector[i] = v
                    } else {
                        variable!!.goalStrengthVector[i] = 0f
                    }
                }
                return true
            }
            return false
        }

        fun add(other: SolverVariable) {
            for (i in 0 until SolverVariable.MAX_STRENGTH) {
                variable!!.goalStrengthVector[i] += other.goalStrengthVector[i]
                val value = variable!!.goalStrengthVector[i]
                if (abs(value) < epsilon) {
                    variable!!.goalStrengthVector[i] = 0f
                }
            }
        }

        val isNegative: Boolean
            get() {
                for (i in SolverVariable.MAX_STRENGTH - 1 downTo 0) {
                    val value = variable!!.goalStrengthVector[i]
                    if (value > 0) {
                        return false
                    }
                    if (value < 0) {
                        return true
                    }
                }
                return false
            }

        fun isSmallerThan(other: SolverVariable?): Boolean {
            for (i in SolverVariable.MAX_STRENGTH - 1 downTo 0) {
                val comparedValue = other!!.goalStrengthVector[i]
                val value = variable!!.goalStrengthVector[i]
                if (value == comparedValue) {
                    continue
                }
                return if (value < comparedValue) {
                    true
                } else {
                    false
                }
            }
            return false
        }

        val isNull: Boolean
            get() {
                for (i in 0 until SolverVariable.MAX_STRENGTH) {
                    if (variable!!.goalStrengthVector[i] != 0f) {
                        return false
                    }
                }
                return true
            }

        fun reset() {
            variable?.goalStrengthVector?.fill(0f)
        }

        override fun toString(): String {
            var result = "[ "
            if (variable != null) {
                for (i in 0 until SolverVariable.MAX_STRENGTH) {
                    result += variable!!.goalStrengthVector[i].toString() + " "
                }
            }
            result += "] $variable"
            return result
        }
    }

    override fun clear() {
        numGoals = 0
        constantValue = 0f
    }

    override val isEmpty: Boolean
        get() = numGoals == 0

    override fun getPivotCandidate(system: LinearSystem?, avoid: BooleanArray?): SolverVariable? {
        var pivot = NOT_FOUND
        for (i in 0 until numGoals) {
            val variable = arrayGoals[i]
            if (avoid!![variable!!.id]) {
                continue
            }
            accessor.init(variable)
            if (pivot == NOT_FOUND) {
                if (accessor.isNegative) {
                    pivot = i
                }
            } else if (accessor.isSmallerThan(arrayGoals[pivot])) {
                pivot = i
            }
        }
        return if (pivot == NOT_FOUND) {
            null
        } else arrayGoals[pivot]
    }

    override fun addError(error: SolverVariable?) {
        accessor.init(error)
        accessor.reset()
        error!!.goalStrengthVector[error.strength] = 1f
        addToGoal(error)
    }

    private fun addToGoal(variable: SolverVariable?) {
        if (numGoals + 1 > arrayGoals.size) {
            arrayGoals = arrayGoals.copyOf(arrayGoals.size * 2)
            sortArray = arrayGoals.copyOf(arrayGoals.size * 2)
        }
        arrayGoals[numGoals] = variable
        numGoals++
        if (numGoals > 1 && arrayGoals[numGoals - 1]!!.id > variable!!.id) {
            for (i in 0 until numGoals) {
                sortArray[i] = arrayGoals[i]
            }
            // TODO sorting all and not just to numGoals
            sortArray = sortArray.filterNotNull().sorted().toTypedArray()
            for (i in 0 until numGoals) {
                arrayGoals[i] = sortArray[i]
            }
        }
        variable!!.inGoal = true
        variable.addToRow(this)
    }

    private fun removeGoal(variable: SolverVariable?) {
        for (i in 0 until numGoals) {
            if (arrayGoals[i] === variable) {
                for (j in i until numGoals - 1) {
                    arrayGoals[j] = arrayGoals[j + 1]
                }
                numGoals--
                variable!!.inGoal = false
                return
            }
        }
    }

    override fun updateFromRow(system: LinearSystem?, definition: ArrayRow?, removeFromDefinition: Boolean) {
        val goalVariable = definition!!.key ?: return
        val rowVariables = definition.variables
        val currentSize = rowVariables!!.currentSize
        for (i in 0 until currentSize) {
            val solverVariable = rowVariables.getVariable(i)
            val value = rowVariables.getVariableValue(i)
            accessor.init(solverVariable)
            if (accessor.addToGoal(goalVariable, value)) {
                addToGoal(solverVariable)
            }
            constantValue += definition.constantValue * value
        }
        removeGoal(goalVariable)
    }

    override fun toString(): String {
        var result = ""
        result += " goal -> ($constantValue) : "
        for (i in 0 until numGoals) {
            val v = arrayGoals[i]
            accessor.init(v)
            result += "$accessor "
        }
        return result
    }

    companion object {
        private const val epsilon = 0.0001f
        private const val DEBUG = false
        const val NOT_FOUND = -1
    }
}