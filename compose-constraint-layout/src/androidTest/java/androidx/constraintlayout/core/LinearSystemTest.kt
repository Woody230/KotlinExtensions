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
import java.lang.Exception

class LinearSystemTest {
    var s: LinearSystem? = null
    @Before
    fun setUp() {
        s = LinearSystem()
        LinearEquation.Companion.resetNaming()
    }

    fun add(equation: LinearEquation?) {
        val row1: ArrayRow = LinearEquation.Companion.createRowFromEquation(s, equation)
        s!!.addConstraint(row1)
    }

    fun add(equation: LinearEquation?, strength: Int) {
        println("Add equation <$equation>")
        val row1: ArrayRow = LinearEquation.Companion.createRowFromEquation(s, equation)
        println("Add equation row <$row1>")
        row1.addError(s!!, strength)
        s!!.addConstraint(row1)
    }

    @Test
    fun testMinMax() {
        // this shows how basic min/max + wrap works. Need to modify ConstraintWidget to generate this.
//        solver.addConstraint(new ClLinearEquation(Rl, 0));
//        solver.addConstraint(new ClLinearEquation(Al, 0));
//        solver.addConstraint(new ClLinearEquation(Bl, 0));
//        solver.addConstraint(new ClLinearEquation(Br, Plus(Bl, 1000)));
//        solver.addConstraint(new ClLinearEquation(Al, new ClLinearExpression(Rl), ClStrength.weak));
//        solver.addConstraint(new ClLinearEquation(Ar, new ClLinearExpression(Rr), ClStrength.weak));
//        solver.addConstraint(new ClLinearInequality(Ar, GEQ, Plus(Al, 150), ClStrength.medium));
//        solver.addConstraint(new ClLinearInequality(Ar, LEQ, Plus(Al, 200), ClStrength.medium));
//        solver.addConstraint(new ClLinearInequality(Rr, GEQ, new ClLinearExpression(Br)));
//        solver.addConstraint(new ClLinearInequality(Rr, GEQ, new ClLinearExpression(Ar)));
        add(LinearEquation(s).`var`("Rl").equalsTo().`var`(0))
        //        add(new LinearEquation(s).var("Al").equalsTo().var(0));
//        add(new LinearEquation(s).var("Bl").equalsTo().var(0));
        add(LinearEquation(s).`var`("Br").equalsTo().`var`("Bl").plus(300))
        add(LinearEquation(s).`var`("Al").equalsTo().`var`("Rl"), 1)
        add(LinearEquation(s).`var`("Ar").equalsTo().`var`("Rr"), 1)
        add(LinearEquation(s).`var`("Ar").greaterThan().`var`("Al").plus(150), 2)
        add(LinearEquation(s).`var`("Ar").lowerThan().`var`("Al").plus(200), 2)
        add(LinearEquation(s).`var`("Rr").greaterThan().`var`("Ar"))
        add(LinearEquation(s).`var`("Rr").greaterThan().`var`("Br"))
        add(LinearEquation(s).`var`("Al").minus("Rl").equalsTo().`var`("Rr").minus("Ar"))
        add(LinearEquation(s).`var`("Bl").minus("Rl").equalsTo().`var`("Rr").minus("Br"))
        try {
            s!!.minimize()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        println("Result: ")
        s!!.displayReadableRows()
        Assert.assertEquals(s!!.getValueFor("Al"), 50.0f, 0f)
        Assert.assertEquals(s!!.getValueFor("Ar"), 250.0f, 0f)
        Assert.assertEquals(s!!.getValueFor("Bl"), 0.0f, 0f)
        Assert.assertEquals(s!!.getValueFor("Br"), 300.0f, 0f)
        Assert.assertEquals(s!!.getValueFor("Rr"), 300.0f, 0f)
    }

    @Test
    fun testPriorityBasic() {
        add(LinearEquation(s).`var`(2, "Xm").equalsTo().`var`("Xl").plus("Xr"))
        add(LinearEquation(s).`var`("Xl").plus(10).lowerThan().`var`("Xr"))
        //       add(new LinearEquation(s).var("Xl").greaterThan().var(0));
        add(LinearEquation(s).`var`("Xr").lowerThan().`var`(100))
        add(LinearEquation(s).`var`("Xm").equalsTo().`var`(50), 2)
        add(LinearEquation(s).`var`("Xl").equalsTo().`var`(30), 1)
        add(LinearEquation(s).`var`("Xr").equalsTo().`var`(60), 1)
        try {
            s!!.minimize()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        println("Result: ")
        s!!.displayReadableRows()
        Assert.assertEquals(s!!.getValueFor("Xm"), 50.0f, 0f) // 50
        Assert.assertEquals(s!!.getValueFor("Xl"), 40.0f, 0f) // 30
        Assert.assertEquals(s!!.getValueFor("Xr"), 60.0f, 0f) // 70
    }

    @Test
    fun testPriorities() {
        // | <- a -> | b
        // a - zero = c - a
        // 2a = c + zero
        // a = (c + zero ) / 2
        add(LinearEquation(s).`var`("b").equalsTo().`var`(100), 3)
        add(LinearEquation(s).`var`("zero").equalsTo().`var`(0), 3)
        add(LinearEquation(s).`var`("a").equalsTo().`var`(300), 0)
        add(LinearEquation(s).`var`("c").equalsTo().`var`(200), 0)
        add(LinearEquation(s).`var`("c").lowerThan().`var`("b").minus(10), 2)
        add(LinearEquation(s).`var`("a").lowerThan().`var`("c"), 2)
        add(LinearEquation(s).`var`("a").minus("zero").equalsTo().`var`("c").minus("a"), 1)
        try {
            s!!.minimize()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        println("Result: ")
        s!!.displayReadableRows()
        Assert.assertEquals(s!!.getValueFor("zero"), 0.0f, 0f)
        Assert.assertEquals(s!!.getValueFor("a"), 45.0f, 0f)
        Assert.assertEquals(s!!.getValueFor("b"), 100.0f, 0f)
        Assert.assertEquals(s!!.getValueFor("c"), 90.0f, 0f)
    }

    @Test
    fun testOptimizeAndPriority() {
        s!!.reset()
        val eq1 = LinearEquation(s)
        val eq2 = LinearEquation(s)
        val eq3 = LinearEquation(s)
        val eq4 = LinearEquation(s)
        val eq5 = LinearEquation(s)
        val eq6 = LinearEquation(s)
        val eq7 = LinearEquation(s)
        val eq8 = LinearEquation(s)
        val eq9 = LinearEquation(s)
        val eq10 = LinearEquation(s)
        eq1.`var`("Root.left").equalsTo().`var`(0)
        eq2.`var`("Root.right").equalsTo().`var`(600)
        eq3.`var`("A.right").equalsTo().`var`("A.left").plus(100) //*
        eq4.`var`("A.left").greaterThan().`var`("Root.left") //*
        eq10.`var`("A.left").equalsTo().`var`("Root.left") //*
        eq5.`var`("A.right").lowerThan().`var`("B.left")
        eq6.`var`("B.right").greaterThan().`var`("B.left")
        eq7.`var`("B.right").lowerThan().`var`("Root.right")
        eq8.`var`("B.left").equalsTo().`var`("A.right")
        eq9.`var`("B.right").greaterThan().`var`("Root.right")
        val row1: ArrayRow = LinearEquation.Companion.createRowFromEquation(s, eq1)
        s!!.addConstraint(row1)
        val row2: ArrayRow = LinearEquation.Companion.createRowFromEquation(s, eq2)
        s!!.addConstraint(row2)
        val row3: ArrayRow = LinearEquation.Companion.createRowFromEquation(s, eq3)
        s!!.addConstraint(row3)
        val row10: ArrayRow = LinearEquation.Companion.createRowFromEquation(s, eq10)
        s!!.addSingleError(row10, 1, SolverVariable.STRENGTH_MEDIUM)
        s!!.addSingleError(row10, -1, SolverVariable.STRENGTH_MEDIUM)
        s!!.addConstraint(row10)
        val row4: ArrayRow = LinearEquation.Companion.createRowFromEquation(s, eq4)
        s!!.addSingleError(row4, -1, SolverVariable.STRENGTH_HIGH)
        s!!.addConstraint(row4)
        val row5: ArrayRow = LinearEquation.Companion.createRowFromEquation(s, eq5)
        s!!.addSingleError(row5, 1, SolverVariable.STRENGTH_MEDIUM)
        s!!.addConstraint(row5)
        val row6: ArrayRow = LinearEquation.Companion.createRowFromEquation(s, eq6)
        s!!.addSingleError(row6, -1, SolverVariable.STRENGTH_LOW)
        s!!.addConstraint(row6)
        val row7: ArrayRow = LinearEquation.Companion.createRowFromEquation(s, eq7)
        s!!.addSingleError(row7, 1, SolverVariable.STRENGTH_LOW)
        s!!.addConstraint(row7)
        val row8: ArrayRow = LinearEquation.Companion.createRowFromEquation(s, eq8)
        row8.addError(s!!, SolverVariable.STRENGTH_LOW)
        s!!.addConstraint(row8)
        val row9: ArrayRow = LinearEquation.Companion.createRowFromEquation(s, eq9)
        s!!.addSingleError(row9, -1, SolverVariable.STRENGTH_LOW)
        s!!.addConstraint(row9)
        try {
            s!!.minimize()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @Test
    fun testPriority() {
        for (i in 0..2) {
            println("\n*** TEST PRIORITY ***\n")
            s!!.reset()
            val eq1 = LinearEquation(s)
            eq1.`var`("A").equalsTo().`var`(10)
            val row1: ArrayRow = LinearEquation.Companion.createRowFromEquation(s, eq1)
            row1.addError(s!!, i % 3)
            s!!.addConstraint(row1)
            val eq2 = LinearEquation(s)
            eq2.`var`("A").equalsTo().`var`(100)
            val row2: ArrayRow = LinearEquation.Companion.createRowFromEquation(s, eq2)
            row2.addError(s!!, (i + 1) % 3)
            s!!.addConstraint(row2)
            val eq3 = LinearEquation(s)
            eq3.`var`("A").equalsTo().`var`(1000)
            val row3: ArrayRow = LinearEquation.Companion.createRowFromEquation(s, eq3)
            row3.addError(s!!, (i + 2) % 3)
            s!!.addConstraint(row3)
            try {
                s!!.minimize()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            println("Check at iteration $i")
            s!!.displayReadableRows()
            if (i == 0) {
                Assert.assertEquals(s!!.getValueFor("A"), 1000.0f, 0f)
            } else if (i == 1) {
                Assert.assertEquals(s!!.getValueFor("A"), 100.0f, 0f)
            } else if (i == 2) {
                Assert.assertEquals(s!!.getValueFor("A"), 10.0f, 0f)
            }
        }
    }

    @Test
    fun testAddEquation1() {
        val e1 = LinearEquation(s)
        e1.`var`("W3.left").equalsTo().`var`(0)
        s!!.addConstraint(LinearEquation.Companion.createRowFromEquation(s, e1))
        //s.rebuildGoalFromErrors();
        val result = s!!.goal.toString()
        Assert.assertTrue(result == "0 = 0.0" || result == " goal -> (0.0) : ")
        Assert.assertEquals(s!!.getValueFor("W3.left"), 0.0f, 0f)
    }

    @Test
    fun testAddEquation2() {
        val e1 = LinearEquation(s)
        e1.`var`("W3.left").equalsTo().`var`(0)
        val e2 = LinearEquation(s)
        e2.`var`("W3.right").equalsTo().`var`(600)
        s!!.addConstraint(LinearEquation.Companion.createRowFromEquation(s, e1))
        s!!.addConstraint(LinearEquation.Companion.createRowFromEquation(s, e2))
        //s.rebuildGoalFromErrors();
        val result = s!!.goal.toString()
        Assert.assertTrue(result == "0 = 0.0" || result == " goal -> (0.0) : ")
        Assert.assertEquals(s!!.getValueFor("W3.left"), 0.0f, 0f)
        Assert.assertEquals(s!!.getValueFor("W3.right"), 600.0f, 0f)
    }

    @Test
    fun testAddEquation3() {
        val e1 = LinearEquation(s)
        e1.`var`("W3.left").equalsTo().`var`(0)
        val e2 = LinearEquation(s)
        e2.`var`("W3.right").equalsTo().`var`(600)
        val left_constraint = LinearEquation(s)
        left_constraint.`var`("W4.left").equalsTo().`var`("W3.left") // left constraint
        s!!.addConstraint(LinearEquation.Companion.createRowFromEquation(s, e1))
        s!!.addConstraint(LinearEquation.Companion.createRowFromEquation(s, e2))
        s!!.addConstraint(LinearEquation.Companion.createRowFromEquation(s, left_constraint)) // left
        //s.rebuildGoalFromErrors();
        Assert.assertEquals(s!!.getValueFor("W3.left"), 0.0f, 0f)
        Assert.assertEquals(s!!.getValueFor("W3.right"), 600.0f, 0f)
        Assert.assertEquals(s!!.getValueFor("W4.left"), 0.0f, 0f)
    }

    @Test
    fun testAddEquation4() {
        val e1 = LinearEquation(s)
        val e2 = LinearEquation(s)
        val e3 = LinearEquation(s)
        val e4 = LinearEquation(s)
        e1.`var`(2, "Xm").equalsTo().`var`("Xl").plus("Xr")
        val goalRow = s!!.goal
        s!!.addConstraint(LinearEquation.Companion.createRowFromEquation(s, e1)) // 2 Xm = Xl + Xr
        goalRow!!.addError(s!!.getVariable("Xm", SolverVariable.Type.ERROR))
        goalRow.addError(s!!.getVariable("Xl", SolverVariable.Type.ERROR))
        //        assertEquals(s.getRow(0).toReadableString(), "Xm = 0.5 Xl + 0.5 Xr", 0f);
        e2.`var`("Xl").plus(10).lowerThan().`var`("Xr")
        s!!.addConstraint(LinearEquation.Companion.createRowFromEquation(s, e2)) // Xl + 10 <= Xr

//        assertEquals(s.getRow(0).toReadableString(), "Xm = 5.0 + Xl + 0.5 s1", 0f);
//        assertEquals(s.getRow(1).toReadableString(), "Xr = 10.0 + Xl + s1", 0f);
        e3.`var`("Xl").greaterThan().`var`(-10)
        s!!.addConstraint(LinearEquation.Companion.createRowFromEquation(s, e3)) // Xl >= -10
        //        assertEquals(s.getRow(0).toReadableString(), "Xm = -5.0 + 0.5 s1 + s2", 0f);
//        assertEquals(s.getRow(1).toReadableString(), "Xr = s1 + s2", 0f);
//        assertEquals(s.getRow(2).toReadableString(), "Xl = -10.0 + s2", 0f);
        e4.`var`("Xr").lowerThan().`var`(100)
        s!!.addConstraint(LinearEquation.Companion.createRowFromEquation(s, e4)) // Xr <= 100
        //        assertEquals(s.getRow(0).toReadableString(), "Xm = 45.0 + 0.5 s2 - 0.5 s3", 0f);
//        assertEquals(s.getRow(1).toReadableString(), "Xr = 100.0 - s3", 0f);
//        assertEquals(s.getRow(2).toReadableString(), "Xl = -10.0 + s2", 0f);
//        assertEquals(s.getRow(3).toReadableString(), "s1 = 100.0 - s2 - s3", 0f);
        //s.rebuildGoalFromErrors();
//        assertEquals(s.getGoal().toString(), "Goal: ", 0f);
        val goal = LinearEquation(s)
        goal.`var`("Xm").minus("Xl")
        try {
            s!!.minimizeGoal(LinearEquation.Companion.createRowFromEquation(s, goal)) //s.getGoal());
        } catch (e: Exception) {
            e.printStackTrace()
        }
        var xl = s!!.getValueFor("Xl").toInt()
        var xm = s!!.getValueFor("Xm").toInt()
        var xr = s!!.getValueFor("Xr").toInt()
        //        assertEquals(xl, -10, 0f);
//        assertEquals(xm, 45, 0f);
//        assertEquals(xr, 100, 0f);
        val e5 = LinearEquation(s)
        e5.`var`("Xm").equalsTo().`var`(50)
        s!!.addConstraint(LinearEquation.Companion.createRowFromEquation(s, e5))
        try {
//            s.minimizeGoal(s.getGoal());
//            s.minimizeGoal(LinearEquation.createRowFromEquation(s, goal)); //s.getGoal());
            s!!.minimizeGoal(goalRow)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        xl = s!!.getValueFor("Xl").toInt()
        xm = s!!.getValueFor("Xm").toInt()
        xr = s!!.getValueFor("Xr").toInt()
        Assert.assertEquals(xl.toLong(), 0)
        Assert.assertEquals(xm.toLong(), 50)
        Assert.assertEquals(xr.toLong(), 100)
    }
}