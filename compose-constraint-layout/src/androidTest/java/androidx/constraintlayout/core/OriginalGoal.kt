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

import java.util.ArrayList

/**
 * Represents a goal to minimize
 */
open class OriginalGoal {
    inner class GoalElement {
        var strengths = FloatArray(MAX)
        var variable: SolverVariable? = null
        fun clearStrengths() {
            for (i in 0 until MAX) {
                strengths[i] = 0f
            }
        }

        override fun toString(): String {
            var representation = variable.toString() + "["
            for (j in strengths.indices) {
                representation += strengths[j]
                representation += if (j < strengths.size - 1) {
                    ", "
                } else {
                    "] "
                }
            }
            return representation
        }
    }

    var variables =
        ArrayList<GoalElement>()//                    System.out.println("-> reset, k: " + k + " strength: " + strength + " v: " + value + " candidate " + candidate);//                    System.out.println("-> k: " + k + " strength: " + strength + " v: " + value + " candidate " + candidate);

    //            System.out.println("get pivot, looking at " + element);
    open val pivotCandidate: SolverVariable?
        get() {
            val count = variables.size
            var candidate: SolverVariable? = null
            var strength = 0
            for (i in 0 until count) {
                val element = variables[i]
                //            System.out.println("get pivot, looking at " + element);
                for (k in MAX - 1 downTo 0) {
                    val value = element.strengths[k]
                    if (candidate == null && value < 0 && k >= strength) {
                        strength = k
                        candidate = element.variable
                        //                    System.out.println("-> k: " + k + " strength: " + strength + " v: " + value + " candidate " + candidate);
                    }
                    if (value > 0 && k > strength) {
//                    System.out.println("-> reset, k: " + k + " strength: " + strength + " v: " + value + " candidate " + candidate);
                        strength = k
                        candidate = null
                    }
                }
            }
            return candidate
        }

    open fun updateFromSystemErrors(system: LinearSystem) {
        for (i in 1 until system.mNumColumns) {
            val variable = system.cache.mIndexedVariables[i]!!
            if (variable.mType !== SolverVariable.Type.ERROR) {
                continue
            }
            val element = GoalElement()
            element.variable = variable
            element.strengths[variable.strength] = 1f
            variables.add(element)
        }
    }

    open fun updateFromSystem(system: LinearSystem) {
        variables.clear()
        updateFromSystemErrors(system)
        val count = variables.size
        for (i in 0 until count) {
            val element = variables[i]
            if (element.variable!!.definitionId != -1) {
                val definition = system.getRow(element.variable!!.definitionId)
                val variables = definition!!.variables as ArrayLinkedVariables?
                val size = variables!!.currentSize
                for (j in 0 until size) {
                    val `var` = variables.getVariable(j)
                    val value = variables.getVariableValue(j)
                    add(element, `var`, value)
                }
                element.clearStrengths()
            }
        }
    }

    fun getElement(variable: SolverVariable?): GoalElement {
        val count = variables.size
        for (i in 0 until count) {
            val element = variables[i]
            if (element.variable == variable) {
                return element
            }
        }
        val element = GoalElement()
        element.variable = variable
        element.strengths[variable!!.strength] = 1f
        variables.add(element)
        return element
    }

    fun add(element: GoalElement, variable: SolverVariable?, value: Float) {
        val addition = getElement(variable)
        for (i in 0 until MAX) {
            addition.strengths[i] += element.strengths[i] * value
        }
    }

    override fun toString(): String {
        var representation = "OriginalGoal: "
        val count = variables.size
        for (i in 0 until count) {
            val element = variables[i]
            representation += element.variable.toString() + "["
            for (j in element.strengths.indices) {
                representation += element.strengths[j]
                representation += if (j < element.strengths.size - 1) {
                    ", "
                } else {
                    "], "
                }
            }
        }
        return representation
    }

    companion object {
        var MAX = 6
    }
}