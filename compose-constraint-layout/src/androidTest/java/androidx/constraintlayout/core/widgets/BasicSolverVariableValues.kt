/*
 * Copyright (C) 2021 The Android Open Source Project
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
package androidx.constraintlayout.core.widgets

import androidx.constraintlayout.core.ArrayRow
import androidx.constraintlayout.core.ArrayRow.ArrayRowVariables
import androidx.constraintlayout.core.Cache
import androidx.constraintlayout.core.SolverVariable
import java.util.*

class BasicSolverVariableValues internal constructor(// our owner
    private val mRow: ArrayRow, cache: Cache?
) : ArrayRowVariables {
    inner class Item {
        var variable: SolverVariable? = null
        var value = 0f
    }

    var list = ArrayList<Item>()

    //LinkedList<Item> list = new LinkedList<>();
    var comparator = Comparator<Item> { s1, s2 -> s1.variable!!.id - s2.variable!!.id }
    override val currentSize: Int
        get() = list.size

    override fun getVariable(i: Int): SolverVariable? {
        return list[i].variable
    }

    override fun getVariableValue(i: Int): Float {
        return list[i].value
    }

    override fun contains(variable: SolverVariable?): Boolean {
        for (item in list) {
            if (item.variable!!.id == variable!!.id) {
                return true
            }
        }
        return false
    }

    override fun indexOf(variable: SolverVariable?): Int {
        for (i in 0 until currentSize) {
            val item = list[i]
            if (item.variable!!.id == variable!!.id) {
                return i
            }
        }
        return -1
    }

    override fun get(variable: SolverVariable?): Float {
        return if (contains(variable)) {
            list[indexOf(variable)].value
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

    override fun clear() {
        val count = currentSize
        for (i in 0 until count) {
            val v = getVariable(i)
            v!!.removeFromRow(mRow)
        }
        list.clear()
    }

    override fun put(variable: SolverVariable?, value: Float) {
        if (value > -epsilon && value < epsilon) {
            remove(variable, true)
            return
        }
        //        System.out.println("Put " + variable + " [" + value + "] in " + mRow);
        //list.add(item);
        if (list.size == 0) {
            val item: Item = Item()
            item.variable = variable
            item.value = value
            list.add(item)
            variable!!.addToRow(mRow)
            variable.usageInRowCount++
        } else {
            if (contains(variable)) {
                val currentItem = list[indexOf(variable)]
                currentItem.value = value
                return
            } else {
                val item: Item = Item()
                item.variable = variable
                item.value = value
                list.add(item)
                variable!!.usageInRowCount++
                variable.addToRow(mRow)
                Collections.sort(list, comparator)
            }
            //            if (false) {
//                int previousItem = -1;
//                int n = 0;
//                for (Item currentItem : list) {
//                    if (currentItem.variable.id == variable.id) {
//                        currentItem.value = value;
//                        return;
//                    }
//                    if (currentItem.variable.id < variable.id) {
//                        previousItem = n;
//                    }
//                    n++;
//                }
//                Item item = new Item();
//                item.variable = variable;
//                item.value = value;
//                list.add(previousItem + 1, item);
//                variable.usageInRowCount++;
//                variable.addToRow(mRow);
//            }
        }
    }

    override fun sizeInBytes(): Int {
        return 0
    }

    override fun remove(v: SolverVariable?, removeFromDefinition: Boolean): Float {
        if (!contains(v)) {
            return 0f
        }
        val index = indexOf(v)
        val value = list[indexOf(v)].value
        list.removeAt(index)
        v!!.usageInRowCount--
        if (removeFromDefinition) {
            v.removeFromRow(mRow)
        }
        return value
    }

    override fun add(v: SolverVariable?, value: Float, removeFromDefinition: Boolean) {
        if (value > -epsilon && value < epsilon) {
            return
        }
        if (!contains(v)) {
            put(v, value)
        } else {
            val item = list[indexOf(v)]
            item.value += value
            if (item.value > -epsilon && item.value < epsilon) {
                item.value = 0f
                list.remove(item)
                v!!.usageInRowCount--
                if (removeFromDefinition) {
                    v.removeFromRow(mRow)
                }
            }
        }
    }

    override fun use(definition: ArrayRow?, removeFromDefinition: Boolean): Float {
        return 0f
    }

    override fun invert() {
        for (item in list) {
            item.value *= -1f
        }
    }

    override fun divideByAmount(amount: Float) {
        for (item in list) {
            item.value /= amount
        }
    }

    companion object {
        private const val epsilon = 0.001f
    }
}