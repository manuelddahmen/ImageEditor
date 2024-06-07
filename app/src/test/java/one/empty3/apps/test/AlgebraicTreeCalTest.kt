/*
 * Copyright (c) 2024.
 *
 *
 *  Copyright 2023 Manuel Daniel Dahmen
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 *
 */
package one.empty3.apps.test;

import one.empty3.library.Point2D
import one.empty3.library.Point3D
import one.empty3.library.StructureMatrix
import one.empty3.library1.shader.Vec
import one.empty3.library1.*
import one.empty3.library1.tree.*
import one.empty3.library1.tree.AlgebraicTree
import one.empty3.library1.tree.TreeNodeEvalException
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/*__
 * Created by Manuel Dahmen on 15-12-16.
 * Updated by Manuel Dahmen on 10-11-23
 */
@RunWith(JUnit4::class)
class AlgebraicTreeCalTest() {

    private fun testResultVariable(
        expr: String,
        expectedResult: Double,
        map: Map<String, Double>,
        echo: Boolean
    ) {
        var AlgebraicTree: AlgebraicTree? = null
        try {
            println("Expression string : $expr")
            AlgebraicTree = AlgebraicTree(expr)
            AlgebraicTree.parametersValues = map
            AlgebraicTree.construct()
            if (echo) println(AlgebraicTree)
            try {
                var result: Double = 0.0
                val eval = AlgebraicTree.eval()
                println("eval dims:" + eval.dim+" ")
                if(eval.dim==1)
                    println("eval vector size : " +eval.data1d.size)
                if(eval.dim==1 && eval.data1d.size>0) {
                    result = AlgebraicTree.eval().getElem(0)
                } else if(eval.dim==0 || eval.data0d!=null) {
                    result = AlgebraicTree.eval().getElem()
                } else {
                    throw AlgebraicFormulaSyntaxException("Cannot evaluate")
                }
                if (echo) println("Result : $result")
                if (echo) println("Expected : $expectedResult")
                Assert.assertTrue(
                    result < expectedResult + DELTA(expectedResult)
                            && result > expectedResult - DELTA(expectedResult)
                )
            } catch (e: TreeNodeEvalException) {
                e.printStackTrace()
                Assert.assertFalse(true)
            }
        } catch (e: AlgebraicFormulaSyntaxException) {
            e.printStackTrace()
            Assert.assertFalse(true)
        } catch (ex: NullPointerException) {
            ex.printStackTrace()
            Assert.assertFalse(true)
        }
    }

    private fun DELTA(expectedResult: Double): Double {
        return Math.abs(Math.max(expectedResult / 10E5, 1E-5))
    }

    protected fun testResult(expr: String, expectedResult: Double, echo: Boolean): Boolean {
        var AlgebraicTree: AlgebraicTree? = null
        try {
            println("Expression string : $expr")
            AlgebraicTree = AlgebraicTree(expr)
            AlgebraicTree.construct()
            if (echo) println(AlgebraicTree)
            try {
                val result: Double
                result = AlgebraicTree.eval().getElem()
                if (echo) println("Result : $result")
                if (echo) println("Expected : $expectedResult")
                Assert.assertTrue(((result < expectedResult + DELTA(expectedResult)
                            && result > expectedResult - DELTA(expectedResult))))
                return true
            } catch (e: TreeNodeEvalException) {
                e.printStackTrace()
                Assert.fail()
            }
        } catch (e: AlgebraicFormulaSyntaxException) {
            e.printStackTrace()
            Assert.fail()
        } catch (e: NullPointerException) {
            e.printStackTrace()
            Assert.fail()
        }
        return false
    }

    protected fun testConstructOrEvalFails(
        expr: String,
        expectedResult: Double,
        echo: Boolean
    ): Boolean {
        var AlgebraicTree: AlgebraicTree? = null
        try {
            println("Expression string : $expr")
            AlgebraicTree = AlgebraicTree(expr)
            AlgebraicTree.construct()
            if (echo) println(AlgebraicTree)
            try {
                val result: Any
                result = AlgebraicTree.eval()
                if (echo) println("Result : $result")
                if (echo) println("Expected : $expectedResult")
                Assert.fail()
                return false
            } catch (e: TreeNodeEvalException) {
                Assert.assertTrue(true)
                if (echo) e.printStackTrace()
            }
        } catch (e: AlgebraicFormulaSyntaxException) {
            Assert.assertTrue(true)
            if (echo) e.printStackTrace()
        } catch (e: NullPointerException) {
            Assert.assertTrue(true)
            if (echo) e.printStackTrace()
        }
        return false
    }

    @Test
    fun testSimpleNumber1() {
        testResult("1", 1.0, true)
    }
    @Test
    fun testSimpleNumber0() {
        testResult("0", 0.0, false)
    }

    @Test
    fun testSimpleEquationAdd() {
        testResult("1+1", 2.0, false)
    }

    @Test
    fun testSimpleEquationAddSubMult() {
        testResult("2*3+1*6-4", 2.0 * 3 + 1.0 * 6 - 4, false)
    }

    @Test
    fun testSimpleEquationAddSubMult2() {
        testResult("2*3-1*6-4", (2.0 * 3) - (1.0 * 6) - 4, false)
    }

    @Test
    fun testSimpleEquationAddMult() {
        testResult("2*3+1*6+4", ((2 * 3) + (1 * 6) + 4).toDouble(), false)
    }

    @Test
    fun testSimpleEquationMult() {
        testResult("2*3", 6.0, false)
    }

    @Test
    fun testSimpleEquationAddAdd() {
        testResult("1+2+3+4+5+6", 1.0 + 2 + 3 + 4 + 5 + 6, false)
    }

    @Test
    fun testSimpleEquationAddSub() {
        testResult("1-2+3-4+5-6", 1.0 - 2 + 3 - 4 + 5 - 6, false)
    }

    @Test
    fun testSimpleEquationBracedAddAdd() {
        testResult("1+2+3-(4*2/1.5+5)*22+6", 1 + 2 + 3 - (4 * 2 / 1.5 + 5) * 22 + 6, false)
    }

    @Test
    fun testSimpleEquationAddSub2() {
        testResult("4*2/5", 4 * 2.0 / 5, false)
    }

    @Test
    fun testSimpleAddSubMulDiv() {
        testResult(
            "4*2/5+8*9/10-4-4*5/9-2*3+1.2",
            (4 * 2 / 5.0 + 8 * 9 / 10.0) - 4 - (4 * 5 / 9.0) - (2 * 3) + 1.2,
            false
        )
    }

    @Test
    fun testZeroPlusZero() {
        testResult("0+0", 0.0, false)
    }

    /*@Test
    public void testMultSign() {

        testResult("-10*-10", 100, false);
    }*/
    @Test
    fun testVariableAdd() {
        val vars = HashMap<String, Double>()
        vars["u"] = 4.0
        vars["v"] = 13.0
        testResultVariable("u+v", 4.0 + 13.0, vars, true)
    }
    @Test
    fun testVariableSub() {
        val vars = HashMap<String, Double>()
        vars["u"] = 4.0
        vars["v"] = 13.0
        testResultVariable("u-v", 4.0 - 13.0, vars, true)
    }
    @Test
    fun testVariableMul() {
        val vars = HashMap<String, Double>()
        vars["u"] = 4.0
        vars["v"] = 13.0
        testResultVariable("u*v", 4.0 * 13.0, vars, true)
    }

    @Test
    fun testVariableZeroPlusZero() {
        val vars = HashMap<String, Double>()
        vars["R"] = 0.0
        vars["u"] = 0.0
        testResultVariable("R+u", 0.0, vars, false)
    }

    @Test
    fun testVariableCircle() {
        val vars = HashMap<String, Double>()
        vars["coordArr"] = 4.0
        vars["y"] = 13.0
        vars["z"] = 13.0
        vars["R"] = 20.0
        testResultVariable(
            "coordArr*coordArr+y*y+z*z-R*R",
            (4.0 * 4) + (13.0 * 13) + (13.0 * 13.0) - 20.0 * 20.0,
            vars,
            false
        )
    }

    @Test
    fun testSimpleEquationBracedMultDiv() {
        testResult("1*2*3/4*5*4", (1.0 * 2.0 * 3.0 / 4.0) * 5.0 * 4.0, false)
    }

    @Test
    fun testComplexFunMultiple1() {
        testResult(
            "sin(1)*sin(2)*sin(2)",  //2*exp(3/4)+0.5-5*4*cos(2)",
            Math.sin(1.0) * Math.sin(2.0) * Math.sin(2.0), false
        )
    }

    @Test
    fun testComplexFunFunMultiple2() {
        testResult(
            "sin(1)*sin(2*cos(0.2)*sin(2))+2*exp(3/4)+0.5-5*4*cos(2)",
            (Math.sin(1.0) * Math.sin(2 * Math.cos(0.2) * Math.sin(2.0))) + (
                    +2 * Math.exp(3.0 / 4)) + 0.5 - 5 * 4 * Math.cos(2.0), false
        )
    }

    @Test
    fun testComplexFunFunMultiple3() {
        testResult(
            "sin(1)*sin(2*2)+2*exp(3/4)+0.5-5*4*cos(2)",
            (Math.sin(1.0) * Math.sin((2 * 2).toDouble())) + (2 * Math.exp(3 / 4.0)) + 0.5 - 5 * 4 * Math.cos(
                2.0
            ), false
        )
    }

    @Test
    fun testSimpleFunction() {
        testResult("sin(3.14)*4", Math.sin(3.14) * 4, false)
    }

    @Test
    fun testSimpleFunction1() {
        val u = 10.0
        val vars = HashMap<String, Double>()
        vars["u"] = u
        testResultVariable("10*cos(10*u)", 10 * Math.cos(10 * u), vars, true)
    }

    @Test
    fun testSimpleFunction3() {
        val u = 10.0
        val vars = HashMap<String, Double>()
        vars["u"] = u
        testResultVariable("cos(10*u)+u", Math.cos(10 * u) + u, vars, true)
    }

    @Test
    fun testSimpleFunction2() {
        val u = 10.0
        val vars = HashMap<String, Double>()
        vars["u"] = u
        testResultVariable("cos(5*u)*10", Math.cos(5 * u) * 10, vars, true)
    }

    @Test
    fun testSimple() {
        Assert.assertTrue(testResult("1", 1.0, false))
    }

    @Test
    fun testSimple3() {
        Assert.assertTrue(testResult("1+1", 2.0, false))
    }

    @Test
    fun testSimple4() {
        Assert.assertTrue(testResult("1*8*(-8)", 1 * 8.0 * -8, false))
    }

    @Test
    fun testSimple5() {
        Assert.assertTrue(testResult("6-6*(-12)", 6 - 6 * -12.0, false))
    }

    @Test
    fun testSimple6() {
        testResultVariable("-5/-5*3.0", -5/-5*3.0,HashMap<String, Double>(), true)
    }

    @Test
    fun testSimple6_1() {
        testResultVariable("(-5)/(-5)*3.0", -5.0/(-5)*3.0, HashMap<String, Double>(),true)
    }
    @Test
    fun testSimple6_2() {
        testResultVariable("(-5)/(-5)*3.0", -5.0/(-5)*3.0, HashMap<String, Double>(),true)
    }
    @Test
    fun testSimple7() {
        Assert.assertTrue(testResult("1-1/3*4/5*2", 1 - 1 / 3.0 * 4 / 5.0 * 2, false))
    }

    @Test
    fun testSimple8() {
        Assert.assertTrue(testResult("1-2-3-4-5-6", 1.0 - 2 - 3 - 4 - 5 - 6, false))
    }

    @Test
    fun testSimple9() {
        Assert.assertTrue(testResult("1/2/3/4/5/6", 1.0 / 2 / 3 / 4 / 5 / 6, false))
    }

    @Test
    fun testSimple10() {
        Assert.assertTrue(testResult("-1", -1.0, true))
    }

    @Test
    fun testSimpleParentheses() {
        Assert.assertTrue(testResult("(2+3)*(4+5)", ((2 + 3) * (4 + 5)).toDouble(), true))
    }

    @Test
    fun testSimpleParentheses3() {
        Assert.assertTrue(
            testResult(
                "(2+3)*(4+5)*(6+7)",
                ((2 + 3) * (4 + 5) * (6 + 7)).toDouble(),
                true
            )
        )
    }

    @Test
    fun testSimple2() {
        Assert.assertTrue(testResult("1.5", 1.5, false))
    }
    @Test
    fun testSimple15() {
        Assert.assertTrue(testResult("-1", -1.0, true))
    }

    @Test
    fun testExp1() {
        Assert.assertTrue(testResult("2^3", Math.pow(2.0, 3.0), true))
    }

    @Test
    fun testExp2() {
        Assert.assertTrue(testResult("2^(3^4)", Math.pow(2.0, Math.pow(3.0, 4.0)), true))
    }

    @Test
    fun testExp3() {
        Assert.assertTrue(testResult("23^34", Math.pow(23.0, 34.0), true))
    }

    @Test
    fun testError() {
        testConstructOrEvalFails("2^6^", -2.0, false)
    }

    @Test
    fun testSimple11() {
        testResult("-1+9", -1 + 9.0, true)
    }

    @Test
    fun testSimple12() {
        testResult("(-1+9)", (-1 + 9.0), true)
    }
    @Test
    fun testSimple13() {
        testResult("6+6-(2*6)", 6.0+6-(2*6), true)
    }
    @Test
    fun testSimple14() {
        testResult("6+6-(6+6)", 6.0+6-(6+6), true)
    }

    @Test
    fun testSimpleVarMultVar() {
        val x = -2.0
        val vars = HashMap<String, Double>()
        vars["x"] = x
        testResultVariable("x*x", x * x, vars, true)
    }

    @Test
    fun testSimpleFunctionDefined() {
        val x = -2.0
        val vars = HashMap<String, Double>()
        vars["x"] = x
        testResultVariable("-x+(2*x)", -x + (2 * x), vars, true)
    }

    @Test
    fun testSimpleFunctionSinVar() {
        val r = 12.0
        val vars = HashMap<String, Double>()
        vars["r"] = r
        testResultVariable("sin(r*10)", Math.sin(r * 10), vars, true)
    }

    companion object {
        private val DELTA = Double.MIN_VALUE
    }

    @Test
    fun testVectorSimple1() {
        val vars = HashMap<String, Double>()
        testResultVariableVec("(0,1,0)", Vec(0.0, 1.0, 0.0), vars, true)
    }
    @Test
    fun testVectorVariable() {
        val vars = HashMap<String, Double>()
        val x =  1.0
        val y =  2.1
        val z = 50.0
        vars["x"] = x
        vars["y"] = y
        vars["z"] = z
        testResultVariableVec("(x,y,z)", Vec(x, y, z),  vars, true)
    }

    private fun testResultVariableVec(
        expr: String,
        expectedResult: Vec,
        map: java.util.HashMap<String, Double>,
        echo: Boolean
    ) {
        var algebraicTree: AlgebraicTree? = null
        try {
            println("Expression string : $expr")
            algebraicTree = AlgebraicTree(expr)
            algebraicTree.parametersValues = map
            algebraicTree.construct()
            if (echo) println(algebraicTree)
            try {
                val result :StructureMatrix<Double> = algebraicTree.eval()

                println("Result : $result")
                if (echo) println("Expected : $expectedResult")

                var assertion = true

                try {
                    if(vecEqualsSM(result,  expectedResult)) {
                        assertion = true
                    } else {
                        assertion = false
                    }
                } catch (ex : NullPointerException) {
                    assertion = false
                }

                Assert.assertTrue(assertion)

                if (echo) println("Result : $result");

            } catch (e: TreeNodeEvalException) {
                e.printStackTrace()
                Assert.assertFalse(true)
            }
        } catch (e: AlgebraicFormulaSyntaxException) {
            e.printStackTrace()
            Assert.assertFalse(true)
        } catch (ex: NullPointerException) {
            ex.printStackTrace()
            Assert.assertFalse(true)
        }
    }

    @Test
    fun testForVectorSimple1() {
        val r = 12.0
        val vars = HashMap<String, Double>()
        vars["r"] = r
        testResultVariableVec("(0,1,0)", Vec(0.0,1.0,0.0), vars, true)
    }

    @Test
    fun testForVectorSum() {
        val r = 12.0
        val vars = HashMap<String, Double>()
        vars["r"] = r
        testResultVariableVec("(2,1,2)+(2,2,3)", Vec(4.0,3.0,5.0), vars, true)
    }
    @Test
    fun testForVectorSubstract() {
        val r = 12.0
        val vars = HashMap<String, Double>()
        vars["r"] = r
        testResultVariableVec("(2,1,2)-(2,2,3)", Vec(0.0,-1.0,-1.0), vars, true)
    }

    @Test
    fun testForVectorDot() {
        val r = 12.0
        val vars = HashMap<String, Double>()
        vars["r"] = r
        testResultVariableVec("(2,1,2)*(2,2,3)", Vec(4.0,2.0,6.0), vars, true)
    }
    @Test
    fun testForVectorMulVec() {
        val vars = HashMap<String, Double>()
        val a : Point3D = Point3D(2.0,1.0,2.0)
        val b : Point3D = Point3D(2.0,2.0,3.0)
        val r : Point3D = a.prodVect(b)
        val s : Vec = Vec(r.x, r.y, r.z)
        testResultVariableVec("(2,1,2)^(2,2,3)", s, vars, true)
    }
    @Test
    fun testForVectorMulVecDim2() {
        val vars = HashMap<String, Double>()
        val a : Point2D = Point2D(2.0,1.0)
        val b : Point2D = Point2D(2.0,2.0)
        val r : Point2D = a.mult(b)
        val s : Vec = Vec(r.x, r.y)
        testResultVariableVec("(2,1)^(2,2)", s, vars, true)
    }

    @Test
    fun testForVectorDotSum() {
        val r = 12.0
        val vars = HashMap<String, Double>()
        vars["r"] = r
        testResultVariableVec("(9,1,3)+(2,1,2)*(2,2,3)+(1,2,3)", Vec(14.0,5.0,12.0), vars, true)
    }

    @Test
    fun testForVectorOfVector() {
        val r = 12.0
        val vars = HashMap<String, Double>()
        vars["r"] = r
        testResultVariableVec("((2,1,2),(2,2,3),(1,2,3))", Vec(2.0,1.0,2.0,2.0,2.0,3.0,1.0,2.0,3.0), vars, true)
    }

    @Test
    fun testTextCalculator3() {
        val listInstructions: ListInstructions = ListInstructions()
        listInstructions.run {
            addInstructions("x=(1,2,3)\n"+ "y=(5,6,7)\n"+ "z=x+y\n")
            runInstructions()
        }
        var assertion = false
        try {
            if (vecEqualsSM(
                    listInstructions.currentParamsValuesVecComputed!!["z"]!!,
                    Vec(1.0 + 5, 2.0 + 6, 3.0 + 7))
            ) {
                assertion = true
            }
        } catch (ex:RuntimeException) {
            ex.printStackTrace()
        }
        Assert.assertTrue(assertion)
    }

    private fun vecEqualsSM(get: StructureMatrix<Double>, vec: Vec): Boolean {
        if(get!=null && get.dim==0) {
            if ((get.data0d != null && vec.vecVal.data0d != null &&
                        vec.vecVal.data0d.equals(get.data0d))
            ) {
                return true
            }else {
                println("get : StructureMatrix<Double> : invalid StructureMatrix or Vec { $get, $vec }")
                return false
            }
        } else if(get!=null && get.dim==1){
            if((get.data1d!=null && vec.vecVal.data1d!=null)) {
                for (i in 0 until get.data1d.size) {
                    println("computed vec : $get")
                    println("computed vec : $vec")
                    if(get.data1d[i]!=vec.vecVal.data1d[i]) {
                        println("equals(StructureMatrix<Double>,Vec) : invalid for number $i")
                        return false
                    }
                }
                return true
            } else {
                println("StructureMatrix<Double>,Vec one or two terms has 1-dim null error")
                return false
            }
            return true
        } else {
            println("StructureMatrix<Double>,Vec sm==null or sm.dim=invalid")
            return false
        }
        return false
    }

}
