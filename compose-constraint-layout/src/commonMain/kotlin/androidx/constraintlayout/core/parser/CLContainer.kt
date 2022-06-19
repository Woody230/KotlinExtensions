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

import kotlin.jvm.JvmField
import kotlin.jvm.JvmStatic

open class CLContainer(content: CharArray) : CLElement(content) {
    @JvmField
    var mElements = ArrayList<CLElement>()
    fun add(element: CLElement) {
        mElements.add(element)
        if (CLParser.DEBUG) {
            println("added element $element to $this")
        }
    }

    override fun toString(): String {
        val list = StringBuilder()
        for (element in mElements) {
            if (list.length > 0) {
                list.append("; ")
            }
            list.append(element)
        }
        return super.toString() + " = <" + list + " >"
    }

    fun size(): Int {
        return mElements.size
    }

    fun names(): ArrayList<String> {
        val names = ArrayList<String>()
        for (element in mElements) {
            if (element is CLKey) {
                names.add(element.content())
            }
        }
        return names
    }

    fun has(name: String): Boolean {
        for (element in mElements) {
            if (element is CLKey) {
                if (element.content() == name) {
                    return true
                }
            }
        }
        return false
    }

    fun put(name: String, value: CLElement?) {
        for (element in mElements) {
            val key = element as CLKey
            if (key.content() == name) {
                key.set(value)
                return
            }
        }
        val key = CLKey.allocate(name, value) as CLKey
        mElements.add(key)
    }

    fun putNumber(name: String, value: Float) {
        put(name, CLNumber(value))
    }

    fun remove(name: String) {
        val toRemove = ArrayList<CLElement>()
        for (element in mElements) {
            val key = element as CLKey
            if (key.content() == name) {
                toRemove.add(element)
            }
        }
        for (element in toRemove) {
            mElements.remove(element)
        }
    }

    /////////////////////////////////////////////////////////////////////////
    // By name
    /////////////////////////////////////////////////////////////////////////
    @Throws(CLParsingException::class)
    operator fun get(name: String): CLElement {
        for (element in mElements) {
            val key = element as CLKey
            if (key.content() == name) {
                return key.value ?: throw CLParsingException("no element for key <$name>", this)
            }
        }
        throw CLParsingException("no element for key <$name>", this)
    }

    @Throws(CLParsingException::class)
    fun getInt(name: String): Int {
        val element = get(name)
        if (element != null) {
            return element.int
        }
        throw CLParsingException(
            "no int found for key <" + name + ">," +
                    " found [" + element.strClass + "] : " + element, this
        )
    }

    @Throws(CLParsingException::class)
    fun getFloat(name: String): Float {
        val element = get(name)
        if (element != null) {
            return element.float
        }
        throw CLParsingException(
            "no float found for key <" + name + ">," +
                    " found [" + element.strClass + "] : " + element, this
        )
    }

    @Throws(CLParsingException::class)
    fun getArray(name: String): CLArray {
        val element = get(name)
        if (element is CLArray) {
            return element
        }
        throw CLParsingException(
            "no array found for key <" + name + ">," +
                    " found [" + element.strClass + "] : " + element, this
        )
    }

    @Throws(CLParsingException::class)
    fun getObject(name: String): CLObject {
        val element = get(name)
        if (element is CLObject) {
            return element
        }
        throw CLParsingException(
            "no object found for key <" + name + ">," +
                    " found [" + element.strClass + "] : " + element, this
        )
    }

    @Throws(CLParsingException::class)
    fun getString(name: String): String {
        val element = get(name)
        if (element is CLString) {
            return element.content()
        }
        var strClass: String? = null
        if (element != null) {
            strClass = element.strClass
        }
        throw CLParsingException(
            "no string found for key <" + name + ">," +
                    " found [" + strClass + "] : " + element, this
        )
    }

    @Throws(CLParsingException::class)
    fun getBoolean(name: String): Boolean {
        val element = get(name)
        if (element is CLToken) {
            return element.boolean
        }
        throw CLParsingException(
            "no boolean found for key <" + name + ">," +
                    " found [" + element.strClass + "] : " + element, this
        )
    }

    /////////////////////////////////////////////////////////////////////////
    // Optional
    /////////////////////////////////////////////////////////////////////////
    fun getOrNull(name: String): CLElement? {
        for (element in mElements) {
            val key = element as CLKey
            if (key.content() == name) {
                return key.value
            }
        }
        return null
    }

    fun getObjectOrNull(name: String): CLObject? {
        val element = getOrNull(name)
        return if (element is CLObject) {
            element
        } else null
    }

    fun getArrayOrNull(name: String): CLArray? {
        val element = getOrNull(name)
        return if (element is CLArray) {
            element
        } else null
    }

    fun getStringOrNull(name: String): String? {
        val element = getOrNull(name)
        return (element as? CLString)?.content()
    }

    fun getFloatOrNaN(name: String): Float {
        val element = getOrNull(name)
        return (element as? CLNumber)?.float ?: Float.NaN
    }

    /////////////////////////////////////////////////////////////////////////
    // By index
    /////////////////////////////////////////////////////////////////////////
    @Throws(CLParsingException::class)
    operator fun get(index: Int): CLElement {
        if (index >= 0 && index < mElements.size) {
            return mElements[index]
        }
        throw CLParsingException("no element at index $index", this)
    }

    @Throws(CLParsingException::class)
    fun getInt(index: Int): Int {
        val element = get(index)
        if (element != null) {
            return element.int
        }
        throw CLParsingException("no int at index $index", this)
    }

    @Throws(CLParsingException::class)
    fun getFloat(index: Int): Float {
        val element = get(index)
        if (element != null) {
            return element.float
        }
        throw CLParsingException("no float at index $index", this)
    }

    @Throws(CLParsingException::class)
    fun getArray(index: Int): CLArray {
        val element = get(index)
        if (element is CLArray) {
            return element
        }
        throw CLParsingException("no array at index $index", this)
    }

    @Throws(CLParsingException::class)
    fun getObject(index: Int): CLObject {
        val element = get(index)
        if (element is CLObject) {
            return element
        }
        throw CLParsingException("no object at index $index", this)
    }

    @Throws(CLParsingException::class)
    fun getString(index: Int): String {
        val element = get(index)
        if (element is CLString) {
            return element.content()
        }
        throw CLParsingException("no string at index $index", this)
    }

    @Throws(CLParsingException::class)
    fun getBoolean(index: Int): Boolean {
        val element = get(index)
        if (element is CLToken) {
            return element.boolean
        }
        throw CLParsingException("no boolean at index $index", this)
    }

    /////////////////////////////////////////////////////////////////////////
    // Optional
    /////////////////////////////////////////////////////////////////////////
    fun getOrNull(index: Int): CLElement? {
        return if (index >= 0 && index < mElements.size) {
            mElements[index]
        } else null
    }

    fun getStringOrNull(index: Int): String? {
        val element = getOrNull(index)
        return (element as? CLString)?.content()
    }

    companion object {
        @JvmStatic
        fun allocate(content: CharArray): CLElement {
            return CLContainer(content)
        }
    }
}