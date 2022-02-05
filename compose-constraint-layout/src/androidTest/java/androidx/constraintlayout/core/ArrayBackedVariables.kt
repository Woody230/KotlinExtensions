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
 * Store a set of variables and their values in an array.
 */
internal class ArrayBackedVariables(arrayRow: ArrayRow?, cache: Cache?) {
    private var variables: Array<SolverVariable?>? = null
    private var values: FloatArray? = null
    private var indexes: IntArray? = null
    private val threshold = 16
    private var maxSize = 4
    private var currentSize = 0
    private var currentWriteSize = 0
    private var candidate: SolverVariable? = null
    val pivotCandidate: SolverVariable?
        get() {
            if (candidate == null) {
                for (i in 0 until currentSize) {
                    val idx = indexes!![i]
                    if (values!![idx] < 0) {
                        candidate = variables!![idx]
                        break
                    }
                }
            }
            return candidate
        }

    fun increaseSize() {
        maxSize *= 2
        variables = variables?.copyOf(maxSize)
        values = values?.copyOf(maxSize)
        indexes = indexes?.copyOf(maxSize)
    }

    fun size(): Int {
        return currentSize
    }

    fun getVariable(index: Int): SolverVariable? {
        return variables!![indexes!![index]]
    }

    fun getVariableValue(index: Int): Float {
        return values!![indexes!![index]]
    }

    fun updateArray(target: ArrayBackedVariables, amount: Float) {
        if (amount == 0f) {
            return
        }
        for (i in 0 until currentSize) {
            val idx = indexes!![i]
            val v = variables!![idx]
            val value = values!![idx]
            target.add(v, value * amount)
        }
    }

    fun setVariable(index: Int, value: Float) {
        val idx = indexes!![index]
        values!![idx] = value
        if (value < 0) {
            candidate = variables!![idx]
        }
    }

    operator fun get(v: SolverVariable): Float {
        if (currentSize < threshold) {
            for (i in 0 until currentSize) {
                val idx = indexes!![i]
                if (variables!![idx] == v) {
                    return values!![idx]
                }
            }
        } else {
            var start = 0
            var end = currentSize - 1
            while (start <= end) {
                val index = start + (end - start) / 2
                val idx = indexes!![index]
                val current = variables!![idx]
                if (current == v) {
                    return values!![idx]
                } else if (current!!.id < v.id) {
                    start = index + 1
                } else {
                    end = index - 1
                }
            }
        }
        return 0f
    }

    fun put(variable: SolverVariable, value: Float) {
        if (value == 0f) {
            remove(variable)
            return
        }
        while (true) {
            var firstEmptyIndex = -1
            for (i in 0 until currentWriteSize) {
                if (variables!![i] == variable) {
                    values!![i] = value
                    if (value < 0) {
                        candidate = variable
                    }
                    return
                }
                if (firstEmptyIndex == -1 && variables!![i] == null) {
                    firstEmptyIndex = i
                }
            }
            if (firstEmptyIndex == -1 && currentWriteSize < maxSize) {
                firstEmptyIndex = currentWriteSize
            }
            if (firstEmptyIndex != -1) {
                variables!![firstEmptyIndex] = variable
                values!![firstEmptyIndex] = value
                // insert the position...
                var inserted = false
                for (j in 0 until currentSize) {
                    val index = indexes!![j]
                    if (variables!![index]!!.id > variable.id) {
                        // this is our insertion point
                        System.arraycopy(indexes, j, indexes, j + 1, currentSize - j)
                        indexes!![j] = firstEmptyIndex
                        inserted = true
                        break
                    }
                }
                if (!inserted) {
                    indexes!![currentSize] = firstEmptyIndex
                }
                currentSize++
                if (firstEmptyIndex + 1 > currentWriteSize) {
                    currentWriteSize = firstEmptyIndex + 1
                }
                if (value < 0) {
                    candidate = variable
                }
                return
            } else {
                increaseSize()
            }
        }
    }

    fun add(variable: SolverVariable?, value: Float) {
        if (value == 0f) {
            return
        }
        while (true) {
            var firstEmptyIndex = -1
            for (i in 0 until currentWriteSize) {
                if (variables!![i] == variable) {
                    values!![i] += value
                    if (value < 0) {
                        candidate = variable
                    }
                    if (values!![i] == 0f) {
                        remove(variable)
                    }
                    return
                }
                if (firstEmptyIndex == -1 && variables!![i] == null) {
                    firstEmptyIndex = i
                }
            }
            if (firstEmptyIndex == -1 && currentWriteSize < maxSize) {
                firstEmptyIndex = currentWriteSize
            }
            if (firstEmptyIndex != -1) {
                variables!![firstEmptyIndex] = variable
                values!![firstEmptyIndex] = value
                // insert the position...
                var inserted = false
                for (j in 0 until currentSize) {
                    val index = indexes!![j]
                    if (variables!![index]!!.id > variable!!.id) {
                        // this is our insertion point
                        System.arraycopy(indexes, j, indexes, j + 1, currentSize - j)
                        indexes!![j] = firstEmptyIndex
                        inserted = true
                        break
                    }
                }
                if (!inserted) {
                    indexes!![currentSize] = firstEmptyIndex
                }
                currentSize++
                if (firstEmptyIndex + 1 > currentWriteSize) {
                    currentWriteSize = firstEmptyIndex + 1
                }
                if (value < 0) {
                    candidate = variable
                }
                return
            } else {
                increaseSize()
            }
        }
    }

    fun clear() {
        var i = 0
        val length = variables!!.size
        while (i < length) {
            variables!![i] = null
            i++
        }
        currentSize = 0
        currentWriteSize = 0
    }

    fun containsKey(variable: SolverVariable): Boolean {
        if (currentSize < 8) {
            for (i in 0 until currentSize) {
                if (variables!![indexes!![i]] == variable) {
                    return true
                }
            }
        } else {
            var start = 0
            var end = currentSize - 1
            while (start <= end) {
                val index = start + (end - start) / 2
                val current = variables!![indexes!![index]]
                if (current == variable) {
                    return true
                } else if (current!!.id < variable.id) {
                    start = index + 1
                } else {
                    end = index - 1
                }
            }
        }
        return false
    }

    fun remove(variable: SolverVariable?): Float {
        if (DEBUG) {
            print("BEFORE REMOVE $variable -> ")
            display()
        }
        if (candidate == variable) {
            candidate = null
        }
        for (i in 0 until currentWriteSize) {
            val idx = indexes!![i]
            if (variables!![idx] == variable) {
                val amount = values!![idx]
                variables!![idx] = null
                System.arraycopy(indexes, i + 1, indexes, i, currentWriteSize - i - 1)
                currentSize--
                if (DEBUG) {
                    print("AFTER REMOVE ")
                    display()
                }
                return amount
            }
        }
        return 0f
    }

    fun sizeInBytes(): Int {
        var size = 0
        size += maxSize * 4
        size += maxSize * 4
        size += maxSize * 4
        size += 4 + 4 + 4 + 4
        return size
    }

    fun display() {
        val count = size()
        print("{ ")
        for (i in 0 until count) {
            print(getVariable(i).toString() + " = " + getVariableValue(i) + " ")
        }
        println(" }")
    }

    private val internalArrays: String
        private get() {
            var str = ""
            val count = size()
            str += "idx { "
            for (i in 0 until count) {
                str += indexes!![i].toString() + " "
            }
            str += "}\n"
            str += "obj { "
            for (i in 0 until count) {
                str += variables!![i].toString() + ":" + values!![i] + " "
            }
            str += "}\n"
            return str
        }

    fun displayInternalArrays() {
        val count = size()
        print("idx { ")
        for (i in 0 until count) {
            print(indexes!![i].toString() + " ")
        }
        println("}")
        print("obj { ")
        for (i in 0 until count) {
            print(variables!![i].toString() + ":" + values!![i] + " ")
        }
        println("}")
    }

    fun updateFromRow(arrayRow: ArrayRow?, definition: ArrayRow?) {
        // TODO -- only used when ArrayRow.USE_LINKED_VARIABLES is set to true
    }

    fun pickPivotCandidate(): SolverVariable? {
        // TODO -- only used when ArrayRow.USE_LINKED_VARIABLES is set to true
        return null
    }

    fun updateFromSystem(goal: ArrayRow?, mRows: Array<ArrayRow?>?) {
        // TODO -- only used when ArrayRow.USE_LINKED_VARIABLES is set to true
    }

    fun divideByAmount(amount: Float) {
        // TODO -- only used when ArrayRow.USE_LINKED_VARIABLES is set to true
    }

    fun updateClientEquations(arrayRow: ArrayRow?) {
        // TODO -- only used when ArrayRow.USE_LINKED_VARIABLES is set to true
    }

    fun hasAtLeastOnePositiveVariable(): Boolean {
        // TODO -- only used when ArrayRow.USE_LINKED_VARIABLES is set to true
        return false
    }

    fun invert() {
        // TODO -- only used when ArrayRow.USE_LINKED_VARIABLES is set to true
    }

    companion object {
        private const val DEBUG = false
    }

    init {
        variables = arrayOfNulls(maxSize)
        values = FloatArray(maxSize)
        indexes = IntArray(maxSize)
    }
}