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

package one.empty3.library.stl_loader;

import java.io.IOException;
import java.io.Reader;
import java.io.StreamTokenizer;

public class StlFileParser extends StreamTokenizer {

    /*__
     * Constructor: object creation and setup
     *
     * @param r The Reader instance
     */
    public StlFileParser(Reader r) {
        super(r);
        setup();
    }

    /*__
     * Gets a number from the stream. Note that we don't recognize numbers in
     * the tokenizer automatically because numbers might be in scientific
     * notation, which isn't processed correctly by StreamTokenizer. The number
     * is returned in nval.
     *
     * @return boolean.
     */
    boolean getNumber() {
        int t;

        try {
            nextToken();
            if (ttype != TT_WORD) {
                throw new IOException("Expected number on line " + lineno());
            }
            nval = (Double.valueOf(sval)).doubleValue();
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return false;
        } catch (NumberFormatException e) {
            System.err.println(e.getMessage());
            return false;
        }
        return true;
    } // end of getNumber

    /*__
     * Method that sets some params of the Tokenizer for reading the file
     * correctly
     */
    public void setup() {
        resetSyntax();
        eolIsSignificant(true);   // The End Of Line is important
        lowerCaseMode(true);

        // All printable ascii characters
        wordChars('!', '~');

        whitespaceChars(' ', ' ');
        whitespaceChars('\n', '\n');
        whitespaceChars('\r', '\r');
        whitespaceChars('\t', '\t');
    }// End setup

}// End of StlFileParser
