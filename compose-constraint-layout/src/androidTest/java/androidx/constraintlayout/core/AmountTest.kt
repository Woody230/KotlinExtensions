/*
 * Copyright (C) 2015 The Android Open Source Project
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

class AmountTest {
    var a1 = Amount(2, 3)
    var a2 = Amount(3, 5)
    @Before
    fun setUp() {
        a1[2] = 3
        a2[3] = 5
    }

    @Test
    fun testAdd() {
        a1.add(a2)
        Assert.assertEquals(a1.numerator.toLong(), 19)
        Assert.assertEquals(a1.denominator.toLong(), 15)
    }

    @Test
    fun testSubtract() {
        a1.subtract(a2)
        Assert.assertEquals(a1.numerator.toLong(), 1)
        Assert.assertEquals(a1.denominator.toLong(), 15)
    }

    @Test
    fun testMultiply() {
        a1.multiply(a2)
        Assert.assertEquals(a1.numerator.toLong(), 2)
        Assert.assertEquals(a1.denominator.toLong(), 5)
    }

    @Test
    fun testDivide() {
        a1.divide(a2)
        Assert.assertEquals(a1.numerator.toLong(), 10)
        Assert.assertEquals(a1.denominator.toLong(), 9)
    }

    @Test
    fun testSimplify() {
        a1[20] = 30
        Assert.assertEquals(a1.numerator.toLong(), 2)
        Assert.assertEquals(a1.denominator.toLong(), 3)
        a1[77] = 88
        Assert.assertEquals(a1.numerator.toLong(), 7)
        Assert.assertEquals(a1.denominator.toLong(), 8)
    }

    @Test
    fun testEquality() {
        a2[a1.numerator] = a1.denominator
        Assert.assertTrue(a1 == a2)
    }
}