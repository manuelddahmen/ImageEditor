/*
 *  This file is part of Empty3.
 *
 *     Empty3 is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Empty3 is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Empty3.  If not, see <https://www.gnu.org/licenses/>. 2
 */

/*
 * This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with this program.  If not, see <https://www.gnu.org/licenses/>
 */

/*

 Vous êtes libre de :

 */
package one.empty3.library.core.script;

import java.util.ArrayList;

/*__
 * *
 * AUTHOR DAHMEN MANUEL DATE 18 AVRIL 2012
 */
public class Pile implements Interprete, Definition {

    private ArrayList<Object> objects = new ArrayList<Object>();
    private ArrayList<Type> types;
    private ArrayList<Variable> variables;
    private ArrayList<Operateur> operateurs;
    private int pos;

    public void addOperateurs(ArrayList<Operateur> o) {
        //this.operateurs.merge(o);
    }

    public void addTypes(ArrayList<Type> types) {
        //this.types.merge(types);
    }

    public void addVariable(ArrayList<Variable> v) {
        //this.variables.merge(v);
    }

    public InterpreteConstants constant() {
        return null;
    }

    public Double evaluer(int posO)
            throws Exception {
        posO = objects.size() - 1;
        ArrayList<Double> vals;
        vals = new ArrayList<Double>();
        while (posO >= 0) {
            Object o = objects.get(posO);
            if (o instanceof Operateur) {
                Operateur op = (Operateur) o;
                String id = op.id;
                if (op.nbrOperandes > 0) {
                    for (int j = 0; j < op.nbrOperandes; j++) {
                        posO--;
                        vals.add(evaluer(posO));
                    }
                }
                if (id.equals("+")) {
                    return vals.get(0) + vals.get(1);
                }
                if (id.equals("-")) {
                    return vals.get(0) - vals.get(1);
                }
                if (id.equals("*")) {
                    return vals.get(0) * vals.get(1);
                }
                if (id.equals("/")) {
                    return vals.get(0) / vals.get(1);
                }
                if (id.equals("**")) {
                    return Math.exp(Math.log(vals.get(0)) * vals.get(1));
                }
                if (id.equals("sin")) {
                    return Math.sin(vals.get(0));
                }
                if (id.equals("cos")) {
                    return Math.cos(vals.get(0));
                }

            } else if (o instanceof Variable) {
                vals.add(((Variable) o).evaluer());
            }

        }
        throw new Exception("Erreur dans la pile post-fixée");
    }

    public Operateur getOperateur(String s) {
        for (int i = 0; i < operateurs.size(); i++) {
            if (operateurs.get(i).id.toUpperCase().equals(s.toUpperCase())) {
                return operateurs.get(i);
            }
        }
        return null;
    }

    public int getPosition() {
        return pos;
    }

    public Type getType(String s) {
        for (int i = 0; i < operateurs.size(); i++) {
            if (types.get(i).nom.toUpperCase().equals(s.toUpperCase())) {
                return types.get(i);
            }
        }
        return null;
    }

    public Variable getVariable(String s) {
        for (int i = 0; i < variables.size(); i++) {
            if (variables.get(i).id.toUpperCase().equals(s.toUpperCase())) {
                return variables.get(i);
            }
        }
        return null;
    }

    public Object interprete(String text, int pos)
            throws InterpreteException {
        loadOperateurs();
        InterpretesBase ib = new InterpretesBase();
        ArrayList<Integer> pattern = new ArrayList<Integer>();
        pattern.add(ib.BLANK);
        pattern.add(ib.LEFTPARENTHESIS);
        pattern.add(ib.BLANK);
        ib.compile(pattern);
        ib.read(text, pos);
        pos = this.pos;

        boolean cont = true;
        while (cont) {
            try {
                ib = new InterpretesBase();
                pattern = new ArrayList<Integer>();
                pattern.add(ib.BLANK);
                pattern.add(ib.LEFTPARENTHESIS);
                pattern.add(ib.BLANK);
                ib.compile(pattern);
                ib.read(text, pos);
                pos = this.pos;
                cont = false;
            } catch (InterpreteException ex) {
                cont = true;
            }

            boolean pass = false;
            Double d = 0.0;
            try {
                d = interpreteDouble(text, pos);
                pass = true;
            } catch (InterpreteException ex) {
                pass = false;
            }
            if (pass) {
                objects.add(d);
            }
            String s = null;
            pass = false;
            try {
                s = interpreteIdentifier(text, pos);
                Operateur o = getOperateur(s);
                Variable v = getVariable(s);
                if (o != null) {
                    objects.add(o);
                    pass = true;
                } else if (v != null) {
                    objects.add(v);
                    pass = true;
                }
            } catch (InterpreteException ex) {
                pass = false;
            }
            if (pass) {
                objects.add(d);
            }
            pos = this.pos;
        }
        return null;
    }

    public Double interpreteDouble(String text, int pos)
            throws InterpreteException {
        Double ret = 0.0;
        InterpretesBase ib = new InterpretesBase();
        ArrayList<Integer> pattern = new ArrayList<Integer>();
        pattern.add(ib.BLANK);
        pattern.add(ib.DECIMAL);
        pattern.add(ib.BLANK);
        ib.compile(pattern);
        ret = (Double) ib.read(text, pos).get(1);
        this.pos = pos;
        return ret;
    }

    public String interpreteIdentifier(String text, int pos)
            throws InterpreteException {
        String ret = "";
        InterpreteIdentifier ii = new InterpreteIdentifier();
        ret = (String) ii.interprete(text, pos);
        this.pos = pos;
        return ret;
    }

    public String interpreteOperateur(String text, int pos)
            throws InterpreteException {
        String ret = "";
        InterpreteIdentifier ii = new InterpreteIdentifier();
        ret = (String) ii.interprete(text, pos);
        this.pos = pos;
        return ret;
    }

    public void loadDefVarCst(LoaderDef ld) {
        ld.load(this);
    }

    public void loadOperateurs() {
        types = new ArrayList<Type>();
        Type t = new Type();
        t.nom = "Double";
        t.type = Double.class;
        types.add(t);
        t = new Type();
        t.nom = "variable";
        t.type = Double.class;
    }

    @Override
    public void setConstant(InterpreteConstants c) {
    }

    @Override
    public void setRepertoire(String r) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public interface LoaderDef {

        void load(Definition d);
    }

    public class Operateur {

        public String id;
        public int nbrOperandes;
        public ArrayList<Integer> types;
    }

    public class Type {

        public String nom;
        public Class type;
    }

    public class Variable {

        public String id;
        public Type type;
        public Object value;

        public Double evaluer() {
            return (Double) value;
        }
    }
}
