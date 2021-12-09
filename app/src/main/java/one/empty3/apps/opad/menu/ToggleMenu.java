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

package one.empty3.apps.opad.menu;


import java.util.HashMap;

/*__
 * Created by manuel on 20-05-17.
 */
public class ToggleMenu {
    private final HashMap<String, Boolean> items = new HashMap<>();
    {
        items.put("displayMenu",false);
        items.put("displaySky",true);
        items.put("displayGround",true);
        items.put("displayBonus",true);
        items.put("displayCharacter",true);
        items.put("displayGroundGrid",false);
        items.put("displayScore",true);
        items.put("displayEnergy",true);
        items.put("displayArcs", false);
    }


    private int index = 0;
    private int maxIndex = items.size();

    public boolean isDisplayMenu() {
        return items.get("displayMenu");
    }

    public void setDisplayMenu(boolean displayMenu) {
        items.put("displayMenu", displayMenu);
    }

    public boolean isDisplaySky() {
        return items.get("displaySky");
    }

    public void setDisplaySky(boolean displaySky) {
        items.put("displaySky", displaySky);
    }

    public boolean isDisplayGround() {
        return items.get("displayGround");
    }

    public void setDisplayGround(boolean displayGround) {
        items.put("displayGround", displayGround);
    }

    public boolean isDisplayBonus() {
        return items.get("displayBonus");
    }

    public void setDisplayBonus(boolean displayBonus) {
        items.put("displayBonus", displayBonus);
    }

    public boolean isDisplayCharacter() {
        return items.get("displayCharacter");
    }

    public void setDisplayCharacter(boolean displayCharacter) {
        items.put("displayCharacter", displayCharacter);
    }

    public boolean isDisplayGroundGrid() {
        return items.get("displayGroundGrid");
    }

    public void setDisplayGroundGrid(boolean displayGroundGrid) {
        items.put("displayGroundGrid", displayGroundGrid);
    }

    public boolean isDisplayScore() {
        return items.get("displayScore");
    }

    public void setDisplayScore(boolean displayScore) {
        items.put("displayScore", displayScore);
    }

    public boolean isDisplayEnergy() {
        return items.get("displayEnergy");
    }

    public void setDisplayEnergy(boolean displayEnergy) {
        items.put("displayEnergy", displayEnergy);
    }

    public boolean isDisplayArcs() {
        return items.get("displayArcs");
    }

    public boolean setDisplayArcs(boolean displayArcs) {
        return items.put("displayArcs", displayArcs);
    }

    @Override
    public String toString() {
        return "Toggle menu" +
                "\nDisplay Sky = " + isDisplaySky() +
                "\nDisplay Ground = " + isDisplayGround() +
                "\nDisplay Bonus = " + isDisplayBonus() +
                "\nDisplay Character = " + isDisplayCharacter() +
                "\nDisplay Ground grid = " + isDisplayGroundGrid() +
                "\nDisplay Score = " + isDisplayScore()+
                "\nDisplay Energy = " + isDisplayEnergy() +
                "\nDisplay Arcs = " + isDisplayEnergy();
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index%maxIndex;
        if (index < 0)
            this.index = maxIndex-1;
        System.out.println(index);
    }

    public boolean getOption(int index) {
        switch (index) {
            case 0:
                return items.get("displaySky");
            case 1:
                return items.get("displayGround");
            case 2:
                return items.get("displayBonus");
            case 3:
                return items.get("displayCharacter");
            case 4:
                return items.get("displayGroundGrid");
            case 5:
                return items.get("displayScore");
            case 6:
                return items.get("displayEnergy");
        }
        throw new IndexOutOfBoundsException("Option incorrecte");
    }

    public void setOption(int index, boolean value) {
        switch (index) {
            case 0:
                setDisplaySky(value);
                break;
            case 1:
                setDisplayGround (value);
                break;
            case 2:
                setDisplayBonus(value);
                break;
            case 3:
                setDisplayCharacter ( value);
                break;
            case 4:
                setDisplayGroundGrid ( value);
                break;
            case 5:
                setDisplayScore ( value);
                break;
            case 6:
                setDisplayEnergy (value);
                break;
        }
    }

}