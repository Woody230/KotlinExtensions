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

import androidx.constraintlayout.core.ArrayRow.ArrayRowVariables

/**
 * Store a set of variables and their values in an array-based linked list coupled
 * with a custom hashmap.
 */
class SolverVariableValues internal constructor(// our owner
    private val mRow: ArrayRow, // pointer to the system-wide cache, allowing access to SolverVariables
    protected val mCache: Cache
) : ArrayRowVariables {
    private val NONE = -1
    private var SIZE = 16
    private val HASH_SIZE = 16
    var keys = IntArray(SIZE)
    var nextKeys = IntArray(SIZE)
    var variables = IntArray(SIZE)
    var values = FloatArray(SIZE)
    var previous = IntArray(SIZE)
    var next = IntArray(SIZE)
    override var currentSize = 0
    var head = -1
    override fun getVariable(index: Int): SolverVariable? {
        val count = currentSize
        if (count == 0) {
            return null
        }
        var j = head
        for (i in 0 until count) {
            if (i == index && j != NONE) {
                return mCache.mIndexedVariables[variables[j]]!!
            }
            j = next[j]
            if (j == NONE) {
                break
            }
        }
        return null
    }

    override fun getVariableValue(index: Int): Float {
        val count = currentSize
        var j = head
        for (i in 0 until count) {
            if (i == index) {
                return values[j]
            }
            j = next[j]
            if (j == NONE) {
                break
            }
        }
        return 0f
    }

    override fun contains(variable: SolverVariable?): Boolean {
        return indexOf(variable) != NONE
    }

    override fun indexOf(variable: SolverVariable?): Int {
        if (currentSize == 0 || variable == null) {
            return NONE
        }
        val id = variable.id
        var key = id % HASH_SIZE
        key = keys[key]
        if (key == NONE) {
            return NONE
        }
        if (variables[key] == id) {
            return key
        }
        while (nextKeys[key] != NONE && variables[nextKeys[key]] != id) {
            key = nextKeys[key]
        }
        if (nextKeys[key] == NONE) {
            return NONE
        }
        return if (variables[nextKeys[key]] == id) {
            nextKeys[key]
        } else NONE
    }

    override fun get(variable: SolverVariable?): Float {
        val index = indexOf(variable)
        return if (index != NONE) {
            values[index]
        } else 0f
    }

    override fun display() {
        val count = currentSize
        print("{ ")
        for (i in 0 until count) {
            val v = getVariable(i) ?: continue
            print(v.toString() + " = " + getVariableValue(i) + " ")
        }
        println(" }")
    }

    override fun toString(): String {
        var str = hashCode().toString() + " { "
        val count = currentSize
        for (i in 0 until count) {
            val v = getVariable(i) ?: continue
            str += v.toString() + " = " + getVariableValue(i) + " "
            val index = indexOf(v)
            str += "[p: "
            if (previous[index] != NONE) {
                str += mCache.mIndexedVariables[variables[previous[index]]]
            } else {
                str += "none"
            }
            str += ", n: "
            if (next[index] != NONE) {
                str += mCache.mIndexedVariables[variables[next[index]]]
            } else {
                str += "none"
            }
            str += "]"
        }
        str += " }"
        return str
    }

    override fun clear() {
        if (DEBUG) {
            println("$this <clear>")
        }
        val count = currentSize
        for (i in 0 until count) {
            val v = getVariable(i)
            if (v != null) {
                v.removeFromRow(mRow)
            }
        }
        for (i in 0 until SIZE) {
            variables[i] = NONE
            nextKeys[i] = NONE
        }
        for (i in 0 until HASH_SIZE) {
            keys[i] = NONE
        }
        currentSize = 0
        head = -1
    }

    private fun increaseSize() {
        val size = SIZE * 2
        variables = variables.copyOf(size)
        values = values.copyOf(size)
        previous = previous.copyOf(size)
        next = next.copyOf(size)
        nextKeys = nextKeys.copyOf(size)
        for (i in SIZE until size) {
            variables[i] = NONE
            nextKeys[i] = NONE
        }
        SIZE = size
    }

    private fun addToHashMap(variable: SolverVariable?, index: Int) {
        if (DEBUG) {
            println(this.hashCode().toString() + " hash add " + variable!!.id + " @ " + index)
        }
        val hash = variable!!.id % HASH_SIZE
        var key = keys[hash]
        if (key == NONE) {
            keys[hash] = index
            if (DEBUG) {
                println(this.hashCode().toString() + " hash add " + variable.id + " @ " + index + " directly on keys " + hash)
            }
        } else {
            while (nextKeys[key] != NONE) {
                key = nextKeys[key]
            }
            nextKeys[key] = index
            if (DEBUG) {
                println(this.hashCode().toString() + " hash add " + variable.id + " @ " + index + " as nextkey of " + key)
            }
        }
        nextKeys[index] = NONE
        if (DEBUG) {
            displayHash()
        }
    }

    private fun displayHash() {
        for (i in 0 until HASH_SIZE) {
            if (keys[i] != NONE) {
                var str = this.hashCode().toString() + " hash [" + i + "] => "
                var key = keys[i]
                var done = false
                while (!done) {
                    str += " " + variables[key]
                    if (nextKeys[key] != NONE) {
                        key = nextKeys[key]
                    } else {
                        done = true
                    }
                }
                println(str)
            }
        }
    }

    private fun removeFromHashMap(variable: SolverVariable?) {
        if (DEBUG) {
            println(this.hashCode().toString() + " hash remove " + variable!!.id)
        }
        val hash = variable!!.id % HASH_SIZE
        var key = keys[hash]
        if (key == NONE) {
            if (DEBUG) {
                displayHash()
            }
            return
        }
        val id = variable.id
        // let's first find it
        if (variables[key] == id) {
            keys[hash] = nextKeys[key]
            nextKeys[key] = NONE
        } else {
            while (nextKeys[key] != NONE && variables[nextKeys[key]] != id) {
                key = nextKeys[key]
            }
            val currentKey = nextKeys[key]
            if (currentKey != NONE && variables[currentKey] == id) {
                nextKeys[key] = nextKeys[currentKey]
                nextKeys[currentKey] = NONE
            }
        }
        if (DEBUG) {
            displayHash()
        }
    }

    private fun addVariable(index: Int, variable: SolverVariable?, value: Float) {
        variables[index] = variable!!.id
        values[index] = value
        previous[index] = NONE
        next[index] = NONE
        variable.addToRow(mRow)
        variable.usageInRowCount++
        currentSize++
    }

    private fun findEmptySlot(): Int {
        for (i in 0 until SIZE) {
            if (variables[i] == NONE) {
                return i
            }
        }
        return -1
    }

    private fun insertVariable(index: Int, variable: SolverVariable?, value: Float) {
        val availableSlot = findEmptySlot()
        addVariable(availableSlot, variable, value)
        if (index != NONE) {
            previous[availableSlot] = index
            next[availableSlot] = next[index]
            next[index] = availableSlot
        } else {
            previous[availableSlot] = NONE
            if (currentSize > 0) {
                next[availableSlot] = head
                head = availableSlot
            } else {
                next[availableSlot] = NONE
            }
        }
        if (next[availableSlot] != NONE) {
            previous[next[availableSlot]] = availableSlot
        }
        addToHashMap(variable, availableSlot)
    }

    override fun put(variable: SolverVariable?, value: Float) {
        if (DEBUG) {
            println(this.toString() + " <put> " + variable!!.id + " = " + value)
        }
        if (value > -epsilon && value < epsilon) {
            remove(variable, true)
            return
        }
        if (currentSize == 0) {
            addVariable(0, variable, value)
            addToHashMap(variable, 0)
            head = 0
        } else {
            val index = indexOf(variable)
            if (index != NONE) {
                values[index] = value
            } else {
                if (currentSize + 1 >= SIZE) {
                    increaseSize()
                }
                val count = currentSize
                var previousItem = -1
                var j = head
                for (i in 0 until count) {
                    if (variables[j] == variable!!.id) {
                        values[j] = value
                        return
                    }
                    if (variables[j] < variable.id) {
                        previousItem = j
                    }
                    j = next[j]
                    if (j == NONE) {
                        break
                    }
                }
                insertVariable(previousItem, variable, value)
            }
        }
    }

    override fun sizeInBytes(): Int {
        return 0
    }

    override fun remove(v: SolverVariable?, removeFromDefinition: Boolean): Float {
        if (DEBUG) {
            println(this.toString() + " <remove> " + v!!.id)
        }
        val index = indexOf(v)
        if (index == NONE) {
            return 0f
        }
        removeFromHashMap(v)
        val value = values[index]
        if (head == index) {
            head = next[index]
        }
        variables[index] = NONE
        if (previous[index] != NONE) {
            next[previous[index]] = next[index]
        }
        if (next[index] != NONE) {
            previous[next[index]] = previous[index]
        }
        currentSize--
        v!!.usageInRowCount--
        if (removeFromDefinition) {
            v.removeFromRow(mRow)
        }
        return value
    }

    override fun add(v: SolverVariable?, value: Float, removeFromDefinition: Boolean) {
        if (DEBUG) {
            println(this.toString() + " <add> " + v!!.id + " = " + value)
        }
        if (value > -epsilon && value < epsilon) {
            return
        }
        val index = indexOf(v)
        if (index == NONE) {
            put(v, value)
        } else {
            values[index] += value
            if (values[index] > -epsilon && values[index] < epsilon) {
                values[index] = 0f
                remove(v, removeFromDefinition)
            }
        }
    }

    override fun use(def: ArrayRow?, removeFromDefinition: Boolean): Float {
        val value = get(def!!.key)
        remove(def.key, removeFromDefinition)
        if (false) {
            val definitionVariables = def.variables
            val definitionSize = definitionVariables!!.currentSize
            for (i in 0 until definitionSize) {
                val definitionVariable = definitionVariables.getVariable(i)
                val definitionValue = definitionVariables[definitionVariable]
                add(definitionVariable, definitionValue * value, removeFromDefinition)
            }
            return value
        }
        val definition = def.variables as SolverVariableValues?
        val definitionSize = definition!!.currentSize
        var j = definition.head
        if (false) {
            for (i in 0 until definitionSize) {
                val definitionValue = definition.values[j]
                val definitionVariable = mCache.mIndexedVariables[definition.variables[j]]!!
                add(definitionVariable, definitionValue * value, removeFromDefinition)
                j = definition.next[j]
                if (j == NONE) {
                    break
                }
            }
        } else {
            j = 0
            var i = 0
            while (j < definitionSize) {
                if (definition.variables[i] != NONE) {
                    val definitionValue = definition.values[i]
                    val definitionVariable = mCache.mIndexedVariables[definition.variables[i]]!!
                    add(definitionVariable, definitionValue * value, removeFromDefinition)
                    j++
                }
                i++
            }
        }
        return value
    }

    override fun invert() {
        val count = currentSize
        var j = head
        for (i in 0 until count) {
            values[j] = values[j] * -1
            j = next[j]
            if (j == NONE) {
                break
            }
        }
    }

    override fun divideByAmount(amount: Float) {
        val count = currentSize
        var j = head
        for (i in 0 until count) {
            values[j] /= amount
            j = next[j]
            if (j == NONE) {
                break
            }
        }
    }

    companion object {
        private const val DEBUG = false
        private const val HASH = true
        private const val epsilon = 0.001f
    }

    init {
        clear()
    }
}