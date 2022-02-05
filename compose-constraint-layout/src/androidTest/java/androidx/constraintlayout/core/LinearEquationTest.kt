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
package androidx.constraintlayout.core

import org.junit.Assert
import org.junit.Before
import kotlin.test.Test
import java.lang.Exception

class LinearEquationTest {
    var s: LinearSystem? = null
    var e: LinearEquation? = null
    @Before
    fun setUp() {
        s = LinearSystem()
        e = LinearEquation()
        e!!.setSystem(s)
        LinearEquation.Companion.resetNaming()
    }

    @Test
    fun testDisplay1() {
        e!!.`var`("A").equalsTo().`var`(100)
        Assert.assertEquals(e.toString(), "A = 100")
    }

    @Test
    fun testDisplay2() {
        e!!.`var`("A").equalsTo().`var`("B")
        Assert.assertEquals(e.toString(), "A = B")
    }

    @Test
    fun testDisplay3() {
        e!!.`var`("A").greaterThan().`var`("B")
        Assert.assertEquals(e.toString(), "A >= B")
    }

    @Test
    fun testDisplay4() {
        e!!.`var`("A").lowerThan().`var`("B")
        Assert.assertEquals(e.toString(), "A <= B")
    }

    @Test
    fun testDisplay5() {
        e!!.`var`("A").greaterThan().`var`("B").plus(100)
        Assert.assertEquals(e.toString(), "A >= B + 100")
    }

    @Test
    fun testDisplay6() {
        e!!.`var`("A").plus("B").minus("C").plus(50).greaterThan().`var`("B").plus("C").minus(100)
        Assert.assertEquals(e.toString(), "A + B - C + 50 >= B + C - 100")
    }

    @Test
    fun testDisplay7() {
        e!!.`var`("A").lowerThan().`var`("B")
        e!!.normalize()
        Assert.assertEquals(e.toString(), "A + s1 = B")
    }

    @Test
    fun testDisplay8() {
        e!!.`var`("A").greaterThan().`var`("B")
        e!!.normalize()
        Assert.assertEquals(e.toString(), "A - s1 = B")
    }

    @Test
    fun testDisplay9() {
        e!!.`var`("A").greaterThan().`var`("B").withError()
        e!!.normalize()
        Assert.assertEquals(e.toString(), "A - s1 = B + e1+ - e1-")
    }

    @Test
    fun testDisplaySimplify() {
        e!!.`var`("A").plus(5).minus(2).plus(2, "B").minus(3, "B").greaterThan().`var`("C").minus(3, "C").withError()
        Assert.assertEquals(e.toString(), "A + 5 - 2 + 2 B - 3 B >= C - 3 C + e1+ - e1-")
        e!!.normalize()
        Assert.assertEquals(e.toString(), "A + 5 - 2 + 2 B - 3 B - s1 = C - 3 C + e1+ - e1-")
        e!!.simplify()
        Assert.assertEquals(e.toString(), "3 + A - B - s1 = - 2 C + e1+ - e1-")
    }

    @Test
    fun testDisplayBalance1() {
        e!!.`var`("A").plus(5).minus(2).plus(2, "B").minus(3, "B").greaterThan().`var`("C").minus(3, "C").withError()
        e!!.normalize()
        try {
            e!!.balance()
        } catch (e: Exception) {
            System.err.println("Exception raised: $e")
        }
        Assert.assertEquals(e.toString(), "A = - 3 + B - 2 C + e1+ - e1- + s1")
    }

    @Test
    fun testDisplayBalance2() {
        e!!.plus(5).minus(2).minus(2, "A").minus(3, "B").equalsTo().`var`(5, "C")
        try {
            e!!.balance()
        } catch (e: Exception) {
            System.err.println("Exception raised: $e")
        }
        Assert.assertEquals(e.toString(), "A = 3/2 - 3/2 B - 5/2 C")
    }

    @Test
    fun testDisplayBalance3() {
        e!!.plus(5).equalsTo().`var`(3)
        try {
            e!!.balance()
        } catch (e: Exception) {
            Assert.assertTrue(true)
        }
        Assert.assertFalse(false)
    }

    @Test
    fun testDisplayBalance4() {
        // s1 = - 200 - e1- + 236 + e1- + e2+ - e2-
        e!!.withSlack().equalsTo().`var`(-200).withError("e1-", -1).plus(236)
        e!!.withError("e1-", 1).withError("e2+", 1).withError("e2-", -1)
        try {
            e!!.balance()
        } catch (e: Exception) {
            System.err.println("Exception raised: $e")
        }
        Assert.assertEquals(e.toString(), "s1 = 36 + e2+ - e2-")
    }

    @Test
    fun testDisplayBalance5() {
        // 236 + e1- + e2+ - e2- = e1- - e2+ + e2-
        e!!.`var`(236).withError("e1-", 1).withError("e2+", 1).withError("e2-", -1)
        e!!.equalsTo().withError("e1-", 1).withError("e2+", -1).withError("e2-", 1)
        try {
            e!!.balance()
        } catch (e: Exception) {
            System.err.println("Exception raised: $e")
        }
        // 236 + e1- + e2+ - e2- = e1- - e2+ + e2-
        // 0 = e1- - e2+ + e2- -236 -e1- - e2+ + e2-
        // 0 =     - e2+ + e2- -236      - e2+ + e2-
        // 0 = -236 - 2 e2+ + 2 e2-
        // 2 e2+ = -236 + 2 e2-
        // e2+ = -118 + e2-
        Assert.assertEquals(e.toString(), "e2+ = - 118 + e2-")
    }
}