/*
 * Copyright (C) 2016 The Android Open Source Project
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

import androidx.constraintlayout.core.widgets.*
import androidx.constraintlayout.core.widgets.ConstraintWidget.DimensionBehaviour
import org.junit.Assert
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.xml.sax.Attributes
import org.xml.sax.SAXException
import org.xml.sax.helpers.DefaultHandler
import java.io.File
import java.util.*
import javax.xml.parsers.SAXParserFactory
import kotlin.test.Test

/**
 * This test the the ConstraintWidget system buy loading XML that contain tags with there positions.
 * the xml files can be designed in android studio.
 */
@RunWith(Parameterized::class)
class XmlBasedTest(var file: String) {
    var widgetMap: HashMap<String?, ConstraintWidget>? = null
    var boundsMap: HashMap<ConstraintWidget?, String?>? = null
    var container: ConstraintWidgetContainer? = null
    var connectionList: ArrayList<XmlBasedTest.Connection?>? = null

    class Connection {
        var fromWidget: ConstraintWidget? = null
        var fromType: ConstraintAnchor.Type? = null
        var toType: ConstraintAnchor.Type? = null
        var toName: String? = null
        var margin = 0
        var gonMargin = Int.MIN_VALUE
    }

    companion object {
        private const val ALLOWED_POSITION_ERROR = 1
        private val visibilityMap = HashMap<String, Int>()
        private val stringWidthMap: MutableMap<String, Int> = HashMap()
        private val stringHeightMap: MutableMap<String, Int> = HashMap()
        private val buttonWidthMap: MutableMap<String, Int> = HashMap()
        private val buttonHeightMap: MutableMap<String, Int> = HashMap()
        private fun rtl(v: String): String {
            if (v == "START") return "LEFT"
            return if (v == "END") "RIGHT" else v
        }

        //        String dirName = System.getProperty("user.dir") + File.separator+".."+File.separator+".."+File.separator+".."
//                +File.separator+"constraintLayout"+File.separator+"core"+File.separator+"src"+File.separator+"test"+File.separator+"resources"+File.separator;
        private val dir: String
            private get() = System.getProperty("user.dir") + "/src/androidUnitTest/resources/"

        @JvmStatic
        @Parameterized.Parameters
        fun genListOfName(): Array<Array<Any?>> {
            val dirName: String = XmlBasedTest.Companion.dir
            Assert.assertTrue(File(dirName).exists())
            val f = File(dirName).listFiles { pathname: File -> pathname.name.startsWith("check") }
            Assert.assertNotNull(f)
            Arrays.sort(f) { o1: File, o2: File -> o1.name.compareTo(o2.name) }
            val ret = Array(f.size) { arrayOfNulls<Any>(1) }
            for (i in ret.indices) {
                ret[i][0] = f[i].absolutePath
            }
            return ret
        }

        /**
         * Calculate the Factorial of n
         *
         * @param N input number
         * @return Factorial of n
         */
        fun fact(N: Int): Int {
            var N = N
            var ret = 1
            while (N > 0) {
                ret *= N--
            }
            return ret
        }

        /**
         * Simple dimension parser
         * Multiply dp units by 3 because we simulate a screen with 3 pixels per dp
         *
         * @param dim
         * @return
         */
        fun parseDim(dim: String): Int {
            if (dim.endsWith("dp")) {
                return 3 * dim.substring(0, dim.length - 2).toInt()
            }
            return if (dim == "wrap_content") {
                -1
            } else -2
        }

        private fun rightPad(s: String, n: Int): String {
            var s = s
            s = s + String(ByteArray(n)).replace('\u0000', ' ')
            return s.substring(0, n)
        }

        private fun R(s: String): String {
            var s = s
            s = "             $s"
            return s.substring(s.length - 13)
        }

        /**
         * Ordered array (1,2,3...) will be cycled till the order is reversed (9,8,7...)
         *
         * @param array to be carried
         * @return false when the order is reversed
         */
        private fun nextPermutation(array: IntArray): Boolean {
            var i = array.size - 1
            while (i > 0 && array[i - 1] >= array[i]) {
                i--
            }
            if (i <= 0) return false
            var j = array.size - 1
            while (array[j] <= array[i - 1]) {
                j--
            }
            var temp = array[i - 1]
            array[i - 1] = array[j]
            array[j] = temp
            j = array.size - 1
            while (i < j) {
                temp = array[i]
                array[i] = array[j]
                array[j] = temp
                i++
                j--
            }
            return true
        }

        init {
            XmlBasedTest.Companion.visibilityMap.put("gone", ConstraintWidget.GONE)
            XmlBasedTest.Companion.visibilityMap.put("visible", ConstraintWidget.VISIBLE)
            XmlBasedTest.Companion.visibilityMap.put("invisible", ConstraintWidget.INVISIBLE)
            XmlBasedTest.Companion.stringWidthMap.put("TextView", 171)
            XmlBasedTest.Companion.stringWidthMap.put("Button", 107)
            XmlBasedTest.Companion.stringWidthMap.put("Hello World!", 200)
            XmlBasedTest.Companion.stringHeightMap.put("TextView", 57)
            XmlBasedTest.Companion.stringHeightMap.put("Button", 51)
            XmlBasedTest.Companion.stringHeightMap.put("Hello World!", 51)
            val s = "12345678 12345678 12345678 12345678 12345678 12345678 12345678 12345678 12345678 12345678 12345678 12345678 12345678 12345678"
            XmlBasedTest.Companion.stringWidthMap.put(s, 984)
            XmlBasedTest.Companion.stringHeightMap.put(s, 204)
            XmlBasedTest.Companion.buttonWidthMap.put("Button", 264)
            XmlBasedTest.Companion.buttonHeightMap.put("Button", 144)
        }
    }

    @Test
    fun testAccessToResources() {
        val dirName: String = XmlBasedTest.Companion.dir
        Assert.assertTrue(" could not find dir $dirName", File(dirName).exists())
        val names: Array<Array<Any?>> = XmlBasedTest.Companion.genListOfName()
        Assert.assertTrue(" Could not get Path $dirName", names.size > 1)
    }

    fun dim(w: ConstraintWidget?): String {
        if (w is Guideline) {
            return w.left.toString() + "," + w.top + "," + 0 + "," + 0
        }
        return if (w!!.visibility == ConstraintWidget.GONE) {
            0.toString() + "," + 0 + "," + 0 + "," + 0
        } else w.left.toString() + "," + w.top + "," + w.width + "," + w.height
    }

    @Test
    fun testSolverXML() {
        parseXML(file)
        container!!.optimizationLevel = Optimizer.OPTIMIZATION_NONE
        val perm = IntArray(boundsMap!!.size)
        for (i in perm.indices) {
            perm[i] = i
        }
        val total: Int = XmlBasedTest.Companion.fact(perm.size)
        val skip = 1 + total / 1000
        populateContainer(perm)
        makeConnections()
        layout()
        validate()
        var k = 0
        while (XmlBasedTest.Companion.nextPermutation(perm)) {
            k++
            if (k % skip != 0) continue
            populateContainer(perm)
            makeConnections()
            layout()
            validate()
        }
    }

    @Test
    fun testDirectResolutionXML() {
        parseXML(file)
        container!!.optimizationLevel = Optimizer.OPTIMIZATION_STANDARD
        val perm = IntArray(boundsMap!!.size)
        for (i in perm.indices) {
            perm[i] = i
        }
        val total: Int = XmlBasedTest.Companion.fact(perm.size)
        val skip = 1 + total / 1000
        populateContainer(perm)
        makeConnections()
        layout()
        validate()
        var k = 0
        while (XmlBasedTest.Companion.nextPermutation(perm)) {
            k++
            if (k % skip != 0) continue
            populateContainer(perm)
            makeConnections()
            layout()
            validate()
        }
    }

    /**
     * Compare two string containing comer separated integers
     *
     * @param a
     * @param b
     * @return
     */
    private fun isSame(a: String?, b: String?): Boolean {
        if (a == null || b == null) {
            return false
        }
        val a_split = a.split(",".toRegex()).toTypedArray()
        val b_split = b.split(",".toRegex()).toTypedArray()
        if (a_split.size != b_split.size) {
            return false
        }
        for (i in a_split.indices) {
            if (a_split[i].length == 0) {
                return false
            }
            var error: Int = XmlBasedTest.Companion.ALLOWED_POSITION_ERROR
            if (b_split[i].startsWith("+")) {
                error += 10
            }
            val a_value = a_split[i].toInt()
            val b_value = b_split[i].toInt()
            if (Math.abs(a_value - b_value) > error) {
                return false
            }
        }
        return true
    }

    /**
     * parse the XML file
     *
     * @param fileName
     */
    private fun parseXML(fileName: String) {
        println(fileName)
        container = ConstraintWidgetContainer(0, 0, 1080, 1920)
        container!!.debugName = "parent"
        widgetMap = HashMap()
        boundsMap = HashMap()
        connectionList = ArrayList()
        val handler: DefaultHandler = object : DefaultHandler() {
            var parentId: String? = null
            @Throws(SAXException::class)
            override fun startDocument() {
            }

            @Throws(SAXException::class)
            override fun endDocument() {
            }

            @Throws(SAXException::class)
            override fun startElement(
                namespaceURI: String,
                localName: String,
                qName: String,
                attributes: Attributes
            ) {
                if (qName != null) {
                    val androidAttrs: MutableMap<String, String> = HashMap()
                    val appAttrs: MutableMap<String, String> = HashMap()
                    val widgetConstraints: MutableMap<String, String> = HashMap()
                    val widgetGoneMargins: MutableMap<String, String> = HashMap()
                    val widgetMargins: MutableMap<String, String> = HashMap()
                    for (i in 0 until attributes.length) {
                        val attrName = attributes.getLocalName(i)
                        val attrValue = attributes.getValue(i)
                        if (!attrName.contains(":")) {
                            continue
                        }
                        if (attrValue.trim { it <= ' ' }.isEmpty()) {
                            continue
                        }
                        val parts = attrName.split(":".toRegex()).toTypedArray()
                        val scheme = parts[0]
                        val attr = parts[1]
                        if (scheme == "android") {
                            androidAttrs[attr] = attrValue
                            if (attr.startsWith("layout_margin")) {
                                widgetMargins[attr] = attrValue
                            }
                        } else if (scheme == "app") {
                            appAttrs[attr] = attrValue
                            if (attr == "layout_constraintDimensionRatio") {
                            } else if (attr == "layout_constraintGuide_begin") {
                            } else if (attr == "layout_constraintGuide_percent") {
                            } else if (attr == "layout_constraintGuide_end") {
                            } else if (attr == "layout_constraintHorizontal_bias") {
                            } else if (attr == "layout_constraintVertical_bias") {
                            } else if (attr.startsWith("layout_constraint")) {
                                widgetConstraints[attr] = attrValue
                            }
                            if (attr.startsWith("layout_goneMargin")) {
                                widgetGoneMargins[attr] = attrValue
                            }
                        }
                    }
                    val id = androidAttrs["id"]
                    val tag = androidAttrs["tag"]
                    val layoutWidth: Int = XmlBasedTest.Companion.parseDim(androidAttrs["layout_width"] ?: "")
                    val layoutHeight: Int = XmlBasedTest.Companion.parseDim(androidAttrs["layout_height"] ?: "")
                    val text = androidAttrs["text"]
                    val visibility = androidAttrs["visibility"]
                    val orientation = androidAttrs["orientation"]
                    if (qName.endsWith("ConstraintLayout")) {
                        if (id != null) {
                            container!!.debugName = id
                        }
                        widgetMap!![container!!.debugName] = container!!
                        widgetMap!!["parent"] = container!!
                    } else if (qName.endsWith("Guideline")) {
                        val guideline = Guideline()
                        if (id != null) {
                            guideline.debugName = id
                        }
                        widgetMap!![guideline.debugName] = guideline
                        boundsMap!![guideline] = tag
                        val horizontal = "horizontal" == orientation
                        println("Guideline " + id + " " + if (horizontal) "HORIZONTAL" else "VERTICAL")
                        guideline.orientation = if (horizontal) Guideline.HORIZONTAL else Guideline.VERTICAL
                        val constraintGuideBegin = appAttrs["layout_constraintGuide_begin"]
                        val constraintGuidePercent = appAttrs["layout_constraintGuide_percent"]
                        val constraintGuideEnd = appAttrs["layout_constraintGuide_end"]
                        if (constraintGuideBegin != null) {
                            guideline.setGuideBegin(XmlBasedTest.Companion.parseDim(constraintGuideBegin))
                            println("Guideline " + id + " setGuideBegin " + XmlBasedTest.Companion.parseDim(constraintGuideBegin))
                        } else if (constraintGuidePercent != null) {
                            guideline.setGuidePercent(constraintGuidePercent.toFloat())
                            println("Guideline " + id + " setGuidePercent " + constraintGuidePercent.toFloat())
                        } else if (constraintGuideEnd != null) {
                            guideline.setGuideEnd(XmlBasedTest.Companion.parseDim(constraintGuideEnd))
                            println("Guideline " + id + " setGuideBegin " + XmlBasedTest.Companion.parseDim(constraintGuideEnd))
                        }
                        println(">>>>>>>>>>>>  $guideline")
                    } else {
                        val widget = ConstraintWidget(200, 51)
                        widget.baselineDistance = 28
                        val connect = arrayOfNulls<XmlBasedTest.Connection>(5)
                        val widgetLayoutConstraintDimensionRatio = appAttrs["layout_constraintDimensionRatio"]
                        val widgetLayoutConstraintHorizontalBias = appAttrs["layout_constraintHorizontal_bias"]
                        val widgetLayoutConstraintVerticalBias = appAttrs["layout_constraintVertical_bias"]
                        if (id != null) {
                            widget.debugName = id
                        } else {
                            widget.debugName = "widget" + (widgetMap!!.size + 1)
                        }
                        if (tag != null) {
                            boundsMap!![widget] = tag
                        }
                        var hBehaviour = DimensionBehaviour.FIXED
                        if (layoutWidth == 0) {
                            hBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
                            widget.setDimension(layoutWidth, widget.height)
                        } else if (layoutWidth == -1) {
                            hBehaviour = DimensionBehaviour.WRAP_CONTENT
                        } else {
                            widget.setDimension(layoutWidth, widget.height)
                        }
                        widget.horizontalDimensionBehaviour = hBehaviour
                        var vBehaviour = DimensionBehaviour.FIXED
                        if (layoutHeight == 0) {
                            vBehaviour = DimensionBehaviour.MATCH_CONSTRAINT
                            widget.setDimension(widget.width, layoutHeight)
                        } else if (layoutHeight == -1) {
                            vBehaviour = DimensionBehaviour.WRAP_CONTENT
                        } else {
                            widget.setDimension(widget.width, layoutHeight)
                        }
                        widget.verticalDimensionBehaviour = vBehaviour
                        if (text != null) {
                            print("text = \"$text\"")
                            val wmap: Map<String, Int> = if (qName == "Button") XmlBasedTest.Companion.buttonWidthMap else XmlBasedTest.Companion.stringWidthMap
                            val hmap: Map<String, Int> = if (qName == "Button") XmlBasedTest.Companion.buttonHeightMap else XmlBasedTest.Companion.stringHeightMap
                            if (wmap.containsKey(text) && widget.horizontalDimensionBehaviour === DimensionBehaviour.WRAP_CONTENT) {
                                widget.width = wmap[text]!!
                            }
                            if (hmap.containsKey(text) && widget.verticalDimensionBehaviour === DimensionBehaviour.WRAP_CONTENT) {
                                widget.height = hmap[text]!!
                            }
                        }
                        if (visibility != null) {
                            widget.visibility = XmlBasedTest.Companion.visibilityMap.get(visibility) ?: 0
                        }
                        if (widgetLayoutConstraintDimensionRatio != null) {
                            widget.setDimensionRatio(widgetLayoutConstraintDimensionRatio)
                        }
                        if (widgetLayoutConstraintHorizontalBias != null) {
                            println("widgetLayoutConstraintHorizontalBias $widgetLayoutConstraintHorizontalBias")
                            widget.horizontalBiasPercent = widgetLayoutConstraintHorizontalBias.toFloat()
                        }
                        if (widgetLayoutConstraintVerticalBias != null) {
                            println("widgetLayoutConstraintVerticalBias $widgetLayoutConstraintVerticalBias")
                            widget.verticalBiasPercent = widgetLayoutConstraintVerticalBias.toFloat()
                        }
                        val constraintKeySet: Set<String> = widgetConstraints.keys
                        val constraintKeys = constraintKeySet.toTypedArray()
                        for (i in constraintKeys.indices) {
                            val attrName = constraintKeys[i]
                            val attrValue = widgetConstraints[attrName]
                            val sp = attrName.substring("layout_constraint".length).split("_to".toRegex()).toTypedArray()
                            val fromString: String = XmlBasedTest.Companion.rtl(sp[0].toUpperCase())
                            val from = ConstraintAnchor.Type.valueOf(fromString)
                            val toString: String = XmlBasedTest.Companion.rtl(sp[1].substring(0, sp[1].length - 2).toUpperCase())
                            val to = ConstraintAnchor.Type.valueOf(toString)
                            val side = from.ordinal - 1
                            if (connect[side] == null) {
                                connect[side] = XmlBasedTest.Connection()
                            }
                            connect[side]!!.fromWidget = widget
                            connect[side]!!.fromType = from
                            connect[side]!!.toType = to
                            connect[side]!!.toName = attrValue
                        }
                        val goneMarginSet: Set<String> = widgetGoneMargins.keys
                        val goneMargins = goneMarginSet.toTypedArray()
                        for (i in goneMargins.indices) {
                            val attrName = goneMargins[i]
                            val attrValue = widgetGoneMargins[attrName]
                            val marginSide: String = XmlBasedTest.Companion.rtl(attrName.substring("layout_goneMargin".length).toUpperCase())
                            val marginType = ConstraintAnchor.Type.valueOf(marginSide)
                            val side = marginType.ordinal - 1
                            if (connect[side] == null) {
                                connect[side] = XmlBasedTest.Connection()
                            }
                            connect[side]!!.gonMargin = 3 * attrValue!!.substring(0, attrValue.length - 2).toInt()
                        }
                        val marginSet: Set<String> = widgetMargins.keys
                        val margins = marginSet.toTypedArray()
                        for (i in margins.indices) {
                            val attrName = margins[i]
                            val attrValue = widgetMargins[attrName]
                            // System.out.println("margin [" + attrName + "] by [" + attrValue +"]");
                            val marginSide: String = XmlBasedTest.Companion.rtl(attrName.substring("layout_margin".length).toUpperCase())
                            val marginType = ConstraintAnchor.Type.valueOf(marginSide)
                            val side = marginType.ordinal - 1
                            if (connect[side] == null) {
                                connect[side] = XmlBasedTest.Connection()
                            }
                            connect[side]!!.margin = 3 * attrValue!!.substring(0, attrValue.length - 2).toInt()
                        }
                        widgetMap!![widget.debugName] = widget
                        for (i in connect.indices) {
                            if (connect[i] != null) {
                                connectionList!!.add(connect[i])
                            }
                        }
                    }
                }
            }
        }
        val file = File(fileName)
        val spf = SAXParserFactory.newInstance()
        try {
            val saxParser = spf.newSAXParser()
            val xmlReader = saxParser.xmlReader
            xmlReader.contentHandler = handler
            xmlReader.parse(file.toURI().toString())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun populateContainer(order: IntArray) {
        println(Arrays.toString(order))
        val widgetSet: Array<ConstraintWidget?> = boundsMap!!.keys.toTypedArray()
        for (i in widgetSet.indices) {
            val widget = widgetSet[order[i]]
            if (widget!!.debugName == "parent") {
                continue
            }
            val hBehaviour = widget.horizontalDimensionBehaviour
            val vBehaviour = widget.verticalDimensionBehaviour
            if (widget is Guideline) {
                val copy = Guideline()
                copy.copy(widget, HashMap())
                container!!.remove(widget)
                widget.copy(copy, HashMap())
            } else {
                val copy = ConstraintWidget()
                copy.copy(widget, HashMap())
                container!!.remove(widget)
                widget.copy(copy, HashMap())
            }
            widget.horizontalDimensionBehaviour = hBehaviour
            widget.verticalDimensionBehaviour = vBehaviour
            container!!.add(widget)
        }
    }

    private fun makeConnections() {
        for (connection in connectionList!!) {
            var toConnect: ConstraintWidget?
            toConnect = if (connection!!.toName.equals("parent", ignoreCase = true) || connection.toName == container!!.debugName) {
                container
            } else {
                widgetMap!![connection.toName]
            }
            if (toConnect == null) {
                println("   " + connection.toName)
            } else {
                connection.fromWidget!!.connect(connection.fromType!!, toConnect, connection.toType!!, connection.margin)
                connection.fromWidget!!.setGoneMargin(connection.fromType, connection.gonMargin)
            }
        }
    }

    private fun layout() {
        container!!.layout()
    }

    private fun validate() {
        val root = widgetMap!!.remove("parent") as ConstraintWidgetContainer?
        val keys = widgetMap!!.keys.toTypedArray()
        var ok = true
        val layout = StringBuilder("\n")
        for (key in keys) {
            if (key!!.contains("activity_main")) {
                continue
            }
            val widget = widgetMap!![key]
            val bounds = boundsMap!![widget]
            val dim = dim(widget)
            val same = isSame(dim, bounds)
            val compare: String = XmlBasedTest.Companion.rightPad(key, 17) + XmlBasedTest.Companion.rightPad(dim, 15) + "   " + bounds
            ok = ok and same
            layout.append(compare).append("\n")
        }
        Assert.assertTrue(layout.toString(), ok)
    }

    @Test
    fun SimpleTest() {
        val root = ConstraintWidgetContainer(0, 0, 1080, 1920)
        val A = ConstraintWidget(0, 0, 200, 51)
        A.horizontalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        A.verticalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        A.debugName = "A"
        A.connect(ConstraintAnchor.Type.LEFT, root, ConstraintAnchor.Type.LEFT, 0)
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP, 0)
        A.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT, 0)
        A.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM, 0)
        root.add(A)
        root.layout()
        println("f) A: " + A + " " + A.width + "," + A.height)
    }

    @Test
    fun GuideLineTest() {
        val root = ConstraintWidgetContainer(0, 0, 1080, 1920)
        val A = ConstraintWidget(0, 0, 200, 51)
        val guideline = Guideline()
        root.add(guideline)
        guideline.setGuidePercent(0.50f)
        guideline.orientation = Guideline.VERTICAL
        guideline.debugName = "guideline"
        A.horizontalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        A.verticalDimensionBehaviour = DimensionBehaviour.WRAP_CONTENT
        A.debugName = "A"
        A.connect(ConstraintAnchor.Type.LEFT, guideline, ConstraintAnchor.Type.LEFT, 0)
        A.connect(ConstraintAnchor.Type.TOP, root, ConstraintAnchor.Type.TOP, 0)
        A.connect(ConstraintAnchor.Type.RIGHT, root, ConstraintAnchor.Type.RIGHT, 0)
        A.connect(ConstraintAnchor.Type.BOTTOM, root, ConstraintAnchor.Type.BOTTOM, 0)
        root.add(A)
        root.layout()
        println("f) A: " + A + " " + A.width + "," + A.height)
        println("f) A: " + guideline + " " + guideline.width + "," + guideline.height)
    }
}