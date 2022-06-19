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

class CLKey(content: CharArray?) : CLContainer(content!!) {
    companion object {
        private val sections = ArrayList<String>()
        fun allocate(content: CharArray?): CLElement {
            return CLKey(content)
        }

        fun allocate(name: String, value: CLElement?): CLElement {
            val key = CLKey(name.toCharArray())
            key.start = 0
            key.setEnd((name.length - 1).toLong())
            key.set(value)
            return key
        }

        init {
            sections.add("ConstraintSets")
            sections.add("Variables")
            sections.add("Generate")
            sections.add("Transitions")
            sections.add("KeyFrames")
            sections.add("KeyAttributes")
            sections.add("KeyPositions")
            sections.add("KeyCycles")
        }
    }

    val name: String
        get() = content()

    override fun toJSON(): String {
        return if (mElements.size > 0) {
            debugName + content() + ": " + mElements[0].toJSON()
        } else debugName + content() + ": <> "
    }

    override fun toFormattedJSON(indent: Int, forceIndent: Int): String {
        var forceIndent = forceIndent
        val json = StringBuilder(debugName)
        addIndent(json, indent)
        val content = content()
        if (mElements.size > 0) {
            json.append(content)
            json.append(": ")
            if (sections.contains(content)) {
                forceIndent = 3
            }
            if (forceIndent > 0) {
                json.append(mElements[0].toFormattedJSON(indent, forceIndent - 1))
            } else {
                val `val` = mElements[0].toJSON()
                if (`val`!!.length + indent < MAX_LINE) {
                    json.append(`val`)
                } else {
                    json.append(mElements[0].toFormattedJSON(indent, forceIndent - 1))
                }
            }
            return json.toString()
        }
        return "$content: <> "
    }

    fun set(value: CLElement?) {
        if (mElements.size > 0) {
            mElements[0] = value!!
        } else {
            mElements.add(value!!)
        }
    }

    val value: CLElement?
        get() = if (mElements.size > 0) {
            mElements[0]
        } else null
}