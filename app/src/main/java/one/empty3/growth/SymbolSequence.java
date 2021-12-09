/*
 * This file is part of Plants-Growth-2
 *     Plants-Growth-2 is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Plants-Growth-2 is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Plants-Growth-2.  If not, see <http://www.gnu.org/licenses/>.
 */

/*
 * This file is part of Plants-Growth-2
 *     Plants-Growth-2 is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Plants-Growth-2 is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 *     along with Plants-Growth-2.  If not, see <http://www.gnu.org/licenses/>.
 */

package one.empty3.growth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SymbolSequence {
    private List<SymbolSequence> symbols = new ArrayList<SymbolSequence>();
    /***
     * ssUp here up is the sense from trunk to branches
     */
    SymbolSequence
            ssUp;
    /***
     * ssDown here up is the inverse sense from trunk to branches,
     * So the sense from branches to trunk
     * */
    SymbolSequence
            ssDown;


    public SymbolSequence() {

    }

    public SymbolSequence getSsUp() {
        return ssUp;
    }

    public void setSsUp(SymbolSequence ssUp) {
        this.ssUp = ssUp;
    }

    public SymbolSequence getSsDown() {
        return ssDown;
    }

    public void setSsDown(SymbolSequence ssDown) {
        this.ssDown = ssDown;
    }

    public List<SymbolSequence> upTo(int n) {
        return null;
    }

    public SymbolSequence(SymbolSequence ss) {
        symbols.add(ss);
    }

    public SymbolSequence(Symbol s) {
        symbols.add(s);
    }

    public SymbolSequence(List<Symbol> listOfSymbolsRes) {
        for(int i=0; i<listOfSymbolsRes.size(); i++)
            symbols.add(listOfSymbolsRes.get(i));
    }

    public void add(Symbol s) {
        symbols.add(s);
    }

    public void add(SymbolSequence ss) {
        symbols.add(ss);
    }

    public List<SymbolSequence> getSymbols() {
        return symbols;
    }

    public SymbolSequence parts() {
        SymbolSequence res = new SymbolSequence();
        for (int index = 0; index < getSymbols().size(); index++) {
            SymbolSequence symbolSequence = getSymbols().get(index);
            if (symbolSequence instanceof Symbol) {
                res.add((Symbol)symbolSequence);
            } else {
                res.addAll(symbolSequence);
            }
            {

            }
        }
        return res;
    }

    public List<Symbol> each() {
        List<Symbol> res = new ArrayList<>();
        for (int index = 0; index < getSymbols().size(); index++) {
            SymbolSequence symbolSequence = getSymbols().get(index);
            if (symbolSequence instanceof Symbol) {
                res.add((Symbol)symbolSequence);
            } else {
                res.addAll(symbolSequence.each());
            }
            {

            }
        }
        return res;

    }

    private Collection<? extends Symbol> toCollection() {
        List<Symbol> list = new ArrayList<>();
        for(SymbolSequence ss : symbols) {
            if(ss instanceof Symbol) {
                list.add((Symbol)ss);

            } else {
                list.addAll(ss.toCollection());
            }
        }
        return list;
    }

    public String toString() {
        String s = "seq_:";
        for(SymbolSequence ss : symbols) {
            s += ss.toString() + " &&";
        }
        return s;
    }

    public int size() {
        return symbols.size();
    }

    public SymbolSequence get(int i) {
        return symbols.get(i);
    }

    public void addAll(SymbolSequence ss) {
        symbols.addAll(ss.symbols);
    }

    public void addAll(int i, SymbolSequence ss) {
        symbols.addAll(i, ss.symbols);
    }

    public boolean equals(Object to) {
        if (to instanceof SymbolSequence) {

            SymbolSequence a = this.parts();
            SymbolSequence b = ((SymbolSequence) to).parts();

            if (a.symbols.size() == b.symbols.size()) {
                int ok = 0;
                for (int i = 0; i < a.size(); i++) {
                    if (a.get(i).equals(b.get(i))) {
                        ok++;
                    }
                }
                if (ok == a.size()) {
                    return true;
                }
            }
        }
        return false;
    }

}