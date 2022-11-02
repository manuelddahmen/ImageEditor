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
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package one.empty3.library;

import android.widget.EditText;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/*__
 * @author manue_001
 */
public class RegisterOutput {

    private Logger logger;
    private OutputStream output;
    private EditText pane;

    public void addOutput(Logger l) {
        this.logger = l;
    }

    public void addOutput(OutputStream o) {
        this.output = o;
    }

    public void addOutput(EditText p) {
        this.pane = p;
    }

    public Logger getLogger() {
        return logger;
    }

    public OutputStream getOutput() {
        return output;
    }

    public EditText getPane() {
        return pane;
    }

    public void println(String s) {
        if (logger != null)
            logger.log(Level.INFO, s);
        if (output != null)
            new PrintWriter(output).println(s);
        if (pane != null)
            pane.setText(pane.getText() + "\n" + s);


    }
}
