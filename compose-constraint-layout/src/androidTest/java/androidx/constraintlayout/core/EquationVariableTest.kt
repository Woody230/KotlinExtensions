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
import org.junit.Test

class EquationVariableTest {
    var s: LinearSystem? = null
    var e1: EquationVariable? = null
    var e2: EquationVariable? = null
    @Before
    fun setUp() {
        s = LinearSystem()
        e1 = EquationVariable(s, 200)
        e2 = EquationVariable(s, 200)
    }

    @Test
    fun testEquality() {
        Assert.assertTrue(e1!!.amount == e2!!.amount)
    }

    @Test
    fun testAddition() {
        e1!!.add(e2)
        Assert.assertEquals(e1!!.amount!!.numerator.toLong(), 400)
    }

    @Test
    fun testSubtraction() {
        e1!!.subtract(e2)
        Assert.assertEquals(e1!!.amount!!.numerator.toLong(), 0)
    }

    @Test
    fun testMultiply() {
        e1!!.multiply(e2)
        Assert.assertEquals(e1!!.amount!!.numerator.toLong(), 40000)
    }

    @Test
    fun testDivide() {
        e1!!.divide(e2)
        Assert.assertEquals(e1!!.amount!!.numerator.toLong(), 1)
    }

    @Test
    fun testCompatible() {
        Assert.assertTrue(e1!!.isCompatible(e2))
        e2 = EquationVariable(s, 200, "TEST", SolverVariable.Type.UNRESTRICTED)
        Assert.assertFalse(e1!!.isCompatible(e2))
    }
}