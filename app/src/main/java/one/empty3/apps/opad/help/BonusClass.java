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
package one.empty3.apps.opad.help;

import java.util.ResourceBundle;

/*__
 *
 * @author Se7en
 */
public class BonusClass {
    protected ResourceBundle bundle;

    public BonusClass() {
        bundle = ResourceBundle.getBundle("one.empty3.apps.opad.Bundle"); // NOI18N
    }

    public ResourceBundle getBundle() {
        return bundle;
    }

    public void setBundle(ResourceBundle bundle) {
        this.bundle = bundle;
    }

    public void setValue(double value) {
        this.value = value;
    }

    protected double value = 0.0;

    public double getValue() {
        return value;
    }

}
