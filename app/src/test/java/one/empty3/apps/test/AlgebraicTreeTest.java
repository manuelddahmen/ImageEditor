/*
 * Copyright (c) 2023.
 *
 *
 *  Copyright 2012-2023 Manuel Daniel Dahmen
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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import one.empty3.library1.tree.AlgebraicFormulaSyntaxException;
import one.empty3.library1.tree.AlgebraicTree;
import one.empty3.library1.tree.TreeNodeEvalException;

/*__
 * Created by Manuel Dahmen on 15-12-16.
 */
public class AlgebraicTreeTest {
    private static final double DELTA = Double.MIN_VALUE;

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void add() throws Exception {

    }

    @Test
    public void addFactors() throws Exception {

    }

    @Test
    public void addTerms() throws Exception {

    }

    @Test
    public void addExponent() throws Exception {

    }

    @Test
    public void addFunction() throws Exception {

    }

    private void testResultVariable(String expr, double expectedResult, Map<String, Double> map, boolean echo) {
        AlgebraicTree AlgebraicTree = null;
        try {
            System.out.println("Expression string : " + expr);
            AlgebraicTree = new AlgebraicTree(expr);
            AlgebraicTree.setParametersValues(map);
            AlgebraicTree.construct();
            if (echo)
                System.out.println(AlgebraicTree);
            try {
                double result;
                result = AlgebraicTree.eval().getElem();
                if (echo)
                    System.out.println("Result : " + result);
                if (echo)
                    System.out.println("Expected : " + expectedResult);
                assertTrue((result < expectedResult + DELTA(expectedResult)
                        && result > expectedResult - DELTA(expectedResult)));
            } catch (TreeNodeEvalException e) {
                e.printStackTrace();
                assertFalse(true);
            }
        } catch (AlgebraicFormulaSyntaxException e) {
            e.printStackTrace();
            assertFalse(true);
        } catch (NullPointerException ex) {
            ex.printStackTrace();
            assertFalse(true);
        }
    }

    private double DELTA(double expectedResult) {
        double abs = Math.abs(Math.max(expectedResult / 10E5, 1E-5));
        return abs;
    }

    protected boolean testResult(String expr, double expectedResult, boolean echo) {
        AlgebraicTree AlgebraicTree = null;
        try {
            System.out.println("Expression string : " + expr);
            AlgebraicTree = new AlgebraicTree(expr);
            AlgebraicTree.construct();
            if (echo)
                System.out.println(AlgebraicTree);
            try {
                double result;
                result = AlgebraicTree.eval().getElem();
                if (echo)
                    System.out.println("Result : " + result);
                if (echo)
                    System.out.println("Expected : " + expectedResult);
                assertTrue((result < expectedResult + DELTA(expectedResult)
                        && result > expectedResult - DELTA(expectedResult)));
                return true;
            } catch (TreeNodeEvalException e) {
                e.printStackTrace();
                fail();
            }
        } catch (AlgebraicFormulaSyntaxException | NullPointerException e) {
            e.printStackTrace();
            fail();
        }
        return false;
    }

    protected boolean testConstructOrEvalFails(String expr, double expectedResult, boolean echo) {
        AlgebraicTree AlgebraicTree = null;
        try {
            System.out.println("Expression string : " + expr);
            AlgebraicTree = new AlgebraicTree(expr);
            AlgebraicTree.construct();
            if (echo)
                System.out.println(AlgebraicTree);
            try {
                Object result;
                result = AlgebraicTree.eval();
                if (echo)
                    System.out.println("Result : " + result);
                if (echo)
                    System.out.println("Expected : " + expectedResult);
                fail();
                return false;
            } catch (TreeNodeEvalException e) {
                assertTrue(true);
                if (echo)
                    e.printStackTrace();
            }
        } catch (AlgebraicFormulaSyntaxException | NullPointerException e) {
            assertTrue(true);
            if (echo)
                e.printStackTrace();
        }
        return false;
    }

    @Test
    public void testSimpleEquation1() {
        testResult("1", 1.0, false);
    }

    @Test
    public void testSimpleEquationAdd() {
        testResult("1+1", 2.0, false);
    }

    @Test
    public void testSimpleEquationAddSubMult() {
        testResult("2*3+1*6-4", 2.0 * 3 + 1. * 6 - 4, false);
    }

    @Test
    public void testSimpleEquationAddSubMult2() {
        testResult("2*3-1*6-4", 2.0 * 3 - 1. * 6 - 4, false);
    }

    @Test
    public void testSimpleEquationAddMult() {
        testResult("2*3+1*6+4", 2 * 3 + 1 * 6 + 4, false);
    }

    @Test
    public void testSimpleEquationMult() {
        testResult("2*3", 6.0, false);
    }

    @Test
    public void testSimpleEquationAddAdd() {
        testResult("1+2+3+4+5+6", 1.0 + 2 + 3 + 4 + 5 + 6, false);
    }

    @Test
    public void testSimpleEquationAddSub() {
        testResult("1-2+3-4+5-6", 1.0 - 2 + 3 - 4 + 5 - 6, false);
    }

    @Test
    public void testSimpleEquationBracedAddAdd() {
        testResult("1+2+3-(4*2/1.5+5)*22+6", 1 + 2 + 3 - (4 * 2 / 1.5 + 5) * 22 + 6, false);
    }

    @Test
    public void testSimpleEquationAddSub2() {

        testResult("4*2/5", 4 * 2.0 / 5, false);
    }

    @Test
    public void testSimpleAddSubMulDiv() {

        testResult("4*2/5+8*9/10-4-4*5/9-2*3+1.2", 4 * 2 / 5. + 8 * 9 / 10. - 4 - 4 * 5 / 9. - 2 * 3 + 1.2, false);
    }

    @Test
    public void testZeroPlusZero() {

        testResult("0+0", 0, false);
    }
    /*@Test
    public void testMultSign() {

        testResult("-10*-10", 100, false);
    }*/

    @Test
    public void testVariable() {

        HashMap<String, Double> vars = new HashMap<>();
        vars.put("u", 4.0);
        vars.put("v", 13.0);
        testResultVariable("u+v", 4.0 + 13.0, vars, true);
    }

    @Test
    public void testVariableZeroPlusZero() {

        HashMap<String, Double> vars = new HashMap<>();
        vars.put("R", 0.0);
        vars.put("u", 0.0);
        testResultVariable("R+u", 0, vars, false);
    }

    @Test
    public void testVariableCircle() {

        HashMap<String, Double> vars = new HashMap<>();
        vars.put("coordArr", 4.0);
        vars.put("y", 13.0);
        vars.put("z", 13.0);
        vars.put("R", 20.0);

        testResultVariable("coordArr*coordArr+y*y+z*z-R*R", 4.0 * 4 + 13.0 * 13 + 13.0 * 13.0 - 20.0 * 20.0, vars, false);
    }

    @Test
    public void testSimpleEquationBracedMultDiv() {
        testResult("1*2*3/4*5*4", 1.0 * 2.0 * 3.0 / 4.0 * 5.0 * 4.0, false);
    }

    @Test
    public void testComplexFunMultiple1() {
        testResult("sin(1)*sin(2)*sin(2)",//2*exp(3/4)+0.5-5*4*cos(2)",
                Math.sin(1) * Math.sin(2) * Math.sin(2), false);
    }

    @Test
    public void testComplexFunFunMultiple2() {
        testResult("sin(1)*sin(2*cos(0.2)*sin(2))+2*exp(3/4)+0.5-5*4*cos(2)",
                Math.sin(1) * Math.sin(2 * Math.cos(0.2) * Math.sin(2)) +
                        +2 * Math.exp(3. / 4) + 0.5 - 5 * 4 * Math.cos(2), false);

    }

    @Test
    public void testComplexFunFunMultiple3() {
        testResult("sin(1)*sin(2*2)+2*exp(3/4)+0.5-5*4*cos(2)",
                Math.sin(1) * Math.sin(2 * 2) + 2 * Math.exp(3 / 4.) + 0.5 - 5 * 4 * Math.cos(2), false);
    }

    @Test
    public void testSimpleFunction() {
        testResult("sin(3.14)*4", Math.sin(3.14) * 4, false);
    }

    @Test
    public void testSimpleFunction1() {
        double u = 10;
        HashMap<String, Double> vars = new HashMap<>();
        vars.put("u", u);
        testResultVariable("10*cos(10*u)", 10 * Math.cos(10 * u), vars, true);
    }

    @Test
    public void testSimpleFunction3() {
        double u = 10;
        HashMap<String, Double> vars = new HashMap<>();
        vars.put("u", u);
        testResultVariable("cos(10*u)+u", Math.cos(10 * u) + u, vars, true);
    }

    @Test
    public void testSimpleFunction2() {
        double u = 10;
        HashMap<String, Double> vars = new HashMap<>();
        vars.put("u", u);


        testResultVariable("cos(5*u)*10", Math.cos(5 * u) * 10, vars, true);
    }

    @Test
    public void testSimple() {
        assertTrue(testResult("1", 1.0, false));
    }

    @Test
    public void testSimple3() {
        assertTrue(testResult("1+1", 2.0, false));
    }

    @Test
    public void testSimple4() {
        assertTrue(testResult("1*8*(-8)", 1 * 8. * -8, false));
    }

    @Test
    public void testSimple5() {
        assertTrue(testResult("6-6*(-12)", 6 - 6 * -12.0, false));
    }

    @Test
    public void testSimple6() {
        assertTrue(testResult("-5/-5*3.0", 3.0, false));
    }

    @Test
    public void testSimple7() {
        assertTrue(testResult("1-1/3*4/5*2", 1 - 1 / 3. * 4 / 5. * 2, false));
    }

    @Test
    public void testSimple8() {
        assertTrue(testResult("1-2-3-4-5-6", 1. - 2 - 3 - 4 - 5 - 6, false));
    }

    @Test
    public void testSimple9() {
        assertTrue(testResult("1/2/3/4/5/6", 1. / 2 / 3 / 4 / 5 / 6, false));
    }

    @Test
    public void testSimple10() {
        assertTrue(testResult("-1", -1, true));
    }

    @Test
    public void testSimpleParentheses() {
        assertTrue(testResult("(2+3)*(4+5)", (2 + 3) * (4 + 5), true));
    }

    @Test
    public void testSimpleParentheses3() {
        assertTrue(testResult("(2+3)*(4+5)*(6+7)", (2 + 3) * (4 + 5) * (6 + 7), true));
    }

    @Test
    public void testSimple2() {
        assertTrue(testResult("1.5", 1.5, false));
    }

    @Test
    public void testExp1() {
        assertTrue(testResult("2^3", Math.pow(2, 3), true));
    }

    @Test
    public void testExp2() {
        assertTrue(testResult("2^(3^4)", Math.pow(2, Math.pow(3, 4)), true));
    }

    @Test
    public void testExp3() {
        assertTrue(testResult("23^34", Math.pow(23, 34), true));
    }

    @Test
    public void testError() {
        testConstructOrEvalFails("2^6^", -2, false);
    }

    @Test
    public void testSimple11() {
        testResult("-1+9", -1 + 9., true);
    }

    @Test
    public void testSimple12() {
        testResult("(-1+9)", (-1 + 9.), true);
    }

    @Test
    public void testSimpleVarMultVar() {
        double x = -2.0;
        HashMap<String, Double> vars = new HashMap<>();
        vars.put("x", x);

        testResultVariable("x*x", x * x, vars, true);
    }

    @Test
    public void testSimpleFunctionDefined() {
        double x = -2.0;
        HashMap<String, Double> vars = new HashMap<>();
        vars.put("x", x);

        testResultVariable("-x+(2*x)", -x + (2 * x), vars, true);
    }

    @Test
    public void testSimpleFunctionSinVar() {
        double r = 12.0;
        HashMap<String, Double> vars = new HashMap<>();
        vars.put("r", r);

        testResultVariable("sin(r*10)", Math.sin(r * 10), vars, true);
    }

}
