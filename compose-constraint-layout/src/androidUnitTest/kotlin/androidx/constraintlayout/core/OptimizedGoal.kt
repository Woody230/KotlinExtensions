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

/**
 * Represents a goal to minimize
 */
class OptimizedGoal(  //                    System.out.println("-> reset, k: " + k + " strength: " + strength + " v: " + value + " candidate " + candidate);
    val mSystem: LinearSystem
) : OriginalGoal() {
    //                    System.out.println("-> k: " + k + " strength: " + strength + " v: " + value + " candidate " + candidate);
    override val pivotCandidate: SolverVariable?
        get() {
            val count = mSystem.mNumColumns
            var candidate: SolverVariable? = null
            var strength = 0
            for (i in 1 until count) {
                val element: SolverVariable = mSystem.cache.mIndexedVariables.get(i)!!
                if (element.mType !== SolverVariable.Type.ERROR) {
                    continue
                }
                for (k in MAX - 1 downTo 0) {
                    val value = element.strengthVector[k]
                    if (candidate == null && value < 0 && k >= strength) {
                        strength = k
                        candidate = element
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

    override fun updateFromSystemErrors(system: LinearSystem) {
        for (i in 1 until system.mNumColumns) {
            val variable: SolverVariable = system.cache.mIndexedVariables.get(i)!!
            if (variable.mType !== SolverVariable.Type.ERROR) {
                continue
            }
            for (j in 0 until MAX) {
                variable.strengthVector[j] = 0f
            }
            variable.strengthVector[variable.strength] = 1f
        }
    }

    override fun updateFromSystem(system: LinearSystem) {
        updateFromSystemErrors(system)
        val count = system.mNumColumns
        for (i in 1 until count) {
            val element: SolverVariable = system.cache.mIndexedVariables.get(i)!!
            if (element.definitionId != -1) {
                val definition = system.getRow(element.definitionId)
                val variables = definition!!.variables as ArrayLinkedVariables?
                val size = variables!!.currentSize
                for (j in 0 until size) {
                    val `var` = variables.getVariable(j)
                    val value = variables.getVariableValue(j)
                    //                    add(element, var, value);
                    for (k in 0 until MAX) {
                        `var`!!.strengthVector[k] += element.strengthVector[k] * value
                    }
                }
            }
        }
        //        variables.clear();
//        initFromSystemErrors(system);
//        final int count = variables.size();
//        for (int i = 0; i < count; i++) {
//            GoalElement element = variables.get(i);
//            if (element.variable.definitionId != -1) {
//                ArrayRow definition = system.getRow(element.variable.definitionId);
//                ArrayLinkedVariables variables = definition.variables;
//                int size = variables.currentSize;
//                for (int j = 0; j < size; j++) {
//                    SolverVariable var = variables.getVariable(j);
//                    float value = variables.getVariableValue(j);
//                    add(element, var, value);
//                }
//                element.clearStrengths();
//            }
//        }
    }

    override fun toString(): String {
        var representation = "OriginalGoal: "
        for (i in 1 until mSystem.mNumColumns) {
            val variable: SolverVariable = mSystem.cache.mIndexedVariables.get(i)!!
            if (variable.mType !== SolverVariable.Type.ERROR) {
                continue
            }
            representation += "$variable["
            for (j in 0 until variable.strengthVector.count()) {
                representation += variable.strengthVector[j]
                representation += if (j < variable.strengthVector.count() - 1) {
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