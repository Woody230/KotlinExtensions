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

class CLToken(content: CharArray) : CLElement(content) {
    var index = 0
    var type = Type.UNKNOWN

    @get:Throws(CLParsingException::class)
    val boolean: Boolean
        get() {
            if (type == Type.TRUE) {
                return true
            }
            if (type == Type.FALSE) {
                return false
            }
            throw CLParsingException("this token is not a boolean: <" + content() + ">", this)
        }

    @get:Throws(CLParsingException::class)
    val isNull: Boolean
        get() {
            if (type == Type.NULL) {
                return true
            }
            throw CLParsingException("this token is not a null: <" + content() + ">", this)
        }

    enum class Type {
        UNKNOWN, TRUE, FALSE, NULL
    }

    var tokenTrue = "true".toCharArray()
    var tokenFalse = "false".toCharArray()
    var tokenNull = "null".toCharArray()
    override fun toJSON(): String {
        return if (CLParser.DEBUG) {
            "<" + content() + ">"
        } else {
            content()
        }
    }

    override fun toFormattedJSON(indent: Int, forceIndent: Int): String {
        val json = StringBuilder()
        addIndent(json, indent)
        json.append(content())
        return json.toString()
    }

    fun validate(c: Char, position: Long): Boolean {
        var isValid = false
        when (type) {
            Type.TRUE -> {
                isValid = tokenTrue[index] == c
                if (isValid && index + 1 == tokenTrue.size) {
                    setEnd(position)
                }
            }
            Type.FALSE -> {
                isValid = tokenFalse[index] == c
                if (isValid && index + 1 == tokenFalse.size) {
                    setEnd(position)
                }
            }
            Type.NULL -> {
                isValid = tokenNull[index] == c
                if (isValid && index + 1 == tokenNull.size) {
                    setEnd(position)
                }
            }
            Type.UNKNOWN -> {
                if (tokenTrue[index] == c) {
                    type = Type.TRUE
                    isValid = true
                } else if (tokenFalse[index] == c) {
                    type = Type.FALSE
                    isValid = true
                } else if (tokenNull[index] == c) {
                    type = Type.NULL
                    isValid = true
                }
            }
        }
        index++
        return isValid
    }

    companion object {
        fun allocate(content: CharArray): CLElement {
            return CLToken(content)
        }
    }
}