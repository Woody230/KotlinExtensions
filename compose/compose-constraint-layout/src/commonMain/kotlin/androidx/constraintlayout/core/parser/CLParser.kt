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

class CLParser(private val mContent: String) {
    private var hasComment = false
    private var lineNumber = 0

    internal enum class TYPE {
        UNKNOWN, OBJECT, ARRAY, NUMBER, STRING, KEY, TOKEN
    }

    @Throws(CLParsingException::class)
    fun parse(): CLObject {
        var root: CLObject? = null
        val content = mContent.toCharArray()
        var currentElement: CLElement? = null
        val length = content.size

        // First, let's find the root element start
        lineNumber = 1
        var startIndex = -1
        for (i in 0 until length) {
            val c = content[i]
            if (c == '{') {
                startIndex = i
                break
            }
            if (c == '\n') {
                lineNumber++
            }
        }
        if (startIndex == -1) {
            throw CLParsingException("invalid json content", null)
        }

        // We have a root object, let's start
        root = CLObject.allocate(content)
        root.line = lineNumber
        root.start = startIndex.toLong()
        currentElement = root
        for (i in startIndex + 1 until length) {
            val c = content[i]
            if (c == '\n') {
                lineNumber++
            }
            if (hasComment) {
                hasComment = if (c == '\n') {
                    false
                } else {
                    continue
                }
            }
            if (false) {
                println("Looking at $i : <$c>")
            }
            if (currentElement == null) {
                break
            }
            if (currentElement.isDone) {
                currentElement = getNextJsonElement(i, c, currentElement, content)
            } else if (currentElement is CLObject) {
                if (c == '}') {
                    currentElement.setEnd((i - 1).toLong())
                } else {
                    currentElement = getNextJsonElement(i, c, currentElement, content)
                }
            } else if (currentElement is CLArray) {
                if (c == ']') {
                    currentElement.setEnd((i - 1).toLong())
                } else {
                    currentElement = getNextJsonElement(i, c, currentElement, content)
                }
            } else if (currentElement is CLString) {
                val ck = content[currentElement.start.toInt()]
                if (ck == c) {
                    currentElement.start = currentElement.start + 1
                    currentElement.setEnd((i - 1).toLong())
                }
            } else {
                if (currentElement is CLToken) {
                    val token = currentElement
                    if (!token.validate(c, i.toLong())) {
                        throw CLParsingException(
                            "parsing incorrect token " + token.content() +
                                    " at line " + lineNumber, token
                        )
                    }
                }
                if (currentElement is CLKey || currentElement is CLString) {
                    val ck = content[currentElement.start.toInt()]
                    if ((ck == '\'' || ck == '"') && ck == c) {
                        currentElement.start = currentElement.start + 1
                        currentElement.setEnd((i - 1).toLong())
                    }
                }
                if (!currentElement.isDone) {
                    if (c == '}' || c == ']' || c == ',' || c == ' ' || c == '\t' || c == '\r' || c == '\n' || c == ':') {
                        currentElement.setEnd((i - 1).toLong())
                        if (c == '}' || c == ']') {
                            currentElement = currentElement.container
                            currentElement!!.setEnd((i - 1).toLong())
                            if (currentElement is CLKey) {
                                currentElement = currentElement.container
                                currentElement!!.setEnd((i - 1).toLong())
                            }
                        }
                    }
                }
            }
            if (currentElement.isDone && (currentElement !is CLKey || currentElement.mElements.size > 0)) {
                currentElement = currentElement.container
            }
        }

        // Close all open elements -- allow us to be more resistant to invalid json, useful during editing.
        while (currentElement != null && !currentElement.isDone) {
            (currentElement as? CLString)?.start = currentElement.start.toInt() + 1L
            currentElement.setEnd((length - 1).toLong())
            currentElement = currentElement.container
        }
        if (DEBUG) {
            println("Root: " + root.toJSON())
        }
        return root
    }

    @Throws(CLParsingException::class)
    private fun getNextJsonElement(
        position: Int, c: Char, currentElement: CLElement,
        content: CharArray
    ): CLElement {
        var currentElement: CLElement? = currentElement
        when (c) {
            ' ', ':', ',', '\t', '\r', '\n' -> {}
            '{' -> {
                currentElement = createElement(currentElement, position, TYPE.OBJECT, true, content)
            }
            '[' -> {
                currentElement = createElement(currentElement, position, TYPE.ARRAY, true, content)
            }
            ']', '}' -> {
                currentElement!!.setEnd((position - 1).toLong())
                currentElement = currentElement.container
                currentElement!!.setEnd(position.toLong())
            }
            '"', '\'' -> {
                currentElement = if (currentElement is CLObject) {
                    createElement(currentElement, position, TYPE.KEY, true, content)
                } else {
                    createElement(currentElement, position, TYPE.STRING, true, content)
                }
            }
            '/' -> {
                if (position + 1 < content.size && content[position + 1] == '/') {
                    hasComment = true
                }
            }
            '-', '+', '.', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' -> {
                currentElement = createElement(currentElement, position, TYPE.NUMBER, true, content)
            }
            else -> {
                if (currentElement is CLContainer && currentElement !is CLObject) {
                    currentElement = createElement(currentElement, position, TYPE.TOKEN, true, content)
                    val token = currentElement as CLToken?
                    if (!token!!.validate(c, position.toLong())) {
                        throw CLParsingException("incorrect token <$c> at line $lineNumber", token)
                    }
                } else {
                    currentElement = createElement(currentElement, position, TYPE.KEY, true, content)
                }
            }
        }
        return currentElement!!
    }

    private fun createElement(
        currentElement: CLElement?, position: Int,
        type: TYPE, applyStart: Boolean, content: CharArray
    ): CLElement? {
        var position = position
        var newElement: CLElement? = null
        if (DEBUG) {
            println("CREATE " + type + " at " + content[position])
        }
        when (type) {
            TYPE.OBJECT -> {
                newElement = CLObject.allocate(content)
                position++
            }
            TYPE.ARRAY -> {
                newElement = CLArray.allocate(content)
                position++
            }
            TYPE.STRING -> {
                newElement = CLString.allocate(content)
            }
            TYPE.NUMBER -> {
                newElement = CLNumber.allocate(content)
            }
            TYPE.KEY -> {
                newElement = CLKey.allocate(content)
            }
            TYPE.TOKEN -> {
                newElement = CLToken.allocate(content)
            }
            else -> {}
        }
        if (newElement == null) {
            return null
        }
        newElement.line = lineNumber
        if (applyStart) {
            newElement.start = position.toLong()
        }
        if (currentElement is CLContainer) {
            newElement.setContainer(currentElement)
        }
        return newElement
    }

    companion object {
        @JvmField
        var DEBUG = false
        @Throws(CLParsingException::class)
        fun parse(string: String): CLObject {
            return CLParser(string).parse()
        }
    }
}