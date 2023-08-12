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
package androidx.constraintlayout.core.parser

import kotlin.jvm.JvmStatic

class CLObject(content: CharArray?) : CLContainer(content!!), Iterable<CLKey?> {
    override fun toJSON(): String {
        val json = StringBuilder("$debugName{ ")
        var first = true
        for (element in mElements) {
            if (!first) {
                json.append(", ")
            } else {
                first = false
            }
            json.append(element.toJSON())
        }
        json.append(" }")
        return json.toString()
    }

    fun toFormattedJSON(): String? {
        return toFormattedJSON(0, 0)
    }

    override fun toFormattedJSON(indent: Int, forceIndent: Int): String {
        val json = StringBuilder(debugName)
        json.append("{\n")
        var first = true
        for (element in mElements) {
            if (!first) {
                json.append(",\n")
            } else {
                first = false
            }
            json.append(element.toFormattedJSON(indent + BASE_INDENT, forceIndent - 1))
        }
        json.append("\n")
        addIndent(json, indent)
        json.append("}")
        return json.toString()
    }

    override fun iterator(): Iterator<CLKey> {
        return CLObjectIterator(this)
    }

    private inner class CLObjectIterator(var myObject: CLObject) : Iterator<CLKey> {
        var index = 0
        override fun hasNext(): Boolean {
            return index < myObject.size()
        }

        override fun next(): CLKey {
            val key = myObject.mElements[index] as CLKey
            index++
            return key
        }
    }

    companion object {
        @JvmStatic
        fun allocate(content: CharArray?): CLObject {
            return CLObject(content)
        }
    }
}