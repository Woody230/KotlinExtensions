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

import org.junit.Assert
import kotlin.test.Test

class CLParserBenchmarkTest {
    var simpleFromWiki2 = """{
  firstName: 'John',
  lastName: 'Smith',
  isAlive: true,
  age: 27,
  address: {
    streetAddress: '21 2nd Street',
    city: 'New York',
    state: 'NY',
    postalCode: '10021-3100'
  },
  phoneNumbers: [
    {
      type: 'home',
      number: '212 555-1234'
    },
    {
      type: 'office',
      number: '646 555-4567'
    }
  ],
  children: [],
  spouse: null
}"""

    @Test
    fun parseAndCheck1000x() {
        try {
            for (i in 0..999) {
                parseAndeCheck()
            }
        } catch (e: CLParsingException) {
            System.err.println("Exception " + e.reason())
            e.printStackTrace()
            Assert.assertTrue(false)
        }
    }

    @Test
    fun parse1000x() {
        try {
            for (i in 0..999) {
                parseOnce()
            }
            parseAndeCheck()
        } catch (e: CLParsingException) {
            System.err.println("Exception " + e.reason())
            e.printStackTrace()
            Assert.assertTrue(false)
        }
    }

    @Throws(CLParsingException::class)
    private fun parseOnce() {
        val test = simpleFromWiki2
        val parsedContent = CLParser.parse(test)
        var o: CLObject
        Assert.assertEquals("John", parsedContent.getString("firstName"))
    }

    @Throws(CLParsingException::class)
    private fun parseAndeCheck() {
        val test = simpleFromWiki2
        val parsedContent = CLParser.parse(test)
        var o: CLObject
        Assert.assertEquals("John", parsedContent.getString("firstName"))
        Assert.assertEquals("Smith", parsedContent.getString("lastName"))
        Assert.assertEquals(true, parsedContent.getBoolean("isAlive"))
        Assert.assertEquals(27, parsedContent.getInt("age").toLong())
        Assert.assertEquals(
            "{ streetAddress: '21 2nd Street', city: 'New York', state: 'NY', postalCode: '10021-3100' }", parsedContent.getObject("address").also { o = it }.toJSON()
        )
        Assert.assertEquals("21 2nd Street", o.getString("streetAddress"))
        Assert.assertEquals("New York", o.getString("city"))
        Assert.assertEquals("NY", o.getString("state"))
        Assert.assertEquals("NY", o.getString("state"))
        Assert.assertEquals("NY", o.getString("state"))
        Assert.assertEquals("NY", o.getString("state"))
        Assert.assertEquals("10021-3100", o.getString("postalCode"))
        Assert.assertEquals("{ type: 'home', number: '212 555-1234' }", parsedContent.getArray("phoneNumbers").getObject(0).also { o = it }.toJSON())
        Assert.assertEquals("home", o.getString("type"))
        Assert.assertEquals("212 555-1234", o.getString("number"))
        Assert.assertEquals("{ type: 'office', number: '646 555-4567' }", parsedContent.getArray("phoneNumbers").getObject(1).also { o = it }.toJSON())
        Assert.assertEquals("office", o.getString("type"))
        Assert.assertEquals("646 555-4567", o.getString("number"))
        Assert.assertEquals(0, parsedContent.getArray("children").mElements.size.toLong())
        val element = parsedContent["spouse"]
        if (element is CLToken) {
            Assert.assertEquals(CLToken.Type.NULL, element.type)
        }
    }
}