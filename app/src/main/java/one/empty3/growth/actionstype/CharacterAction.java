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

package one.empty3.growth.actionstype;

import one.empty3.growth.Action;
import one.empty3.growth.Symbol;
import one.empty3.growth.SymbolSequence;

import java.util.ArrayList;
import java.util.List;


public class CharacterAction extends Action {

    private SymbolSequence replaceman = new SymbolSequence();

    public CharacterAction(Character c) {
        this.replaceman = new Symbol(c);
    }

    public CharacterAction(String cs) {
        for(Byte c : cs.getBytes()) {
            replaceman.add(new Symbol((char)c.shortValue()));
        }
    }

    public void replace(SymbolSequence pattern, SymbolSequence toReplacePattern) {
        List<Symbol> stringOfSymbols = new ArrayList<Symbol>();
        SymbolSequence patternParts = pattern.parts();
        SymbolSequence tokenParts = toReplacePattern.parts();
        for (Symbol ss : patternParts.each()) {
            if (ss instanceof Symbol) {
                stringOfSymbols.add((Symbol) ss);

            }

        }
        int id = 0;
        for (int i = 0; i < stringOfSymbols.size(); i++) {
            for(int j=0 ; j<tokenParts.size(); j++) {
                if(i+j>=stringOfSymbols.size())

                    break;

                if(stringOfSymbols.get(i+j).equals(tokenParts.get(j)))

                    id++;


            }

            if(id==tokenParts.size()) {

            }
        }
    }
}
