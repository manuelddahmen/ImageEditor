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

package one.empty3.apps.opad;

import one.empty3.library.Point3D;
import one.empty3.library.core.physics.Level1;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;

/*__
 * Created by manuel on 19-05-17.
 */
public class Game {


    private static ArrayList<Player> localPlayers = new ArrayList<>();
    protected String gameName;
    //protected ListSearch<one.empty3.apps.opad.Gamer> players;
    private Player activePlayers;
    private Player currentPlayer;

    public Game() {
    }


    public static ArrayList getLocalPlayers() throws IOException {

        localPlayers = new ArrayList<>();

        ArrayList<Properties> properties = new ArrayList<>();

        String pathname = "gameData/players";
        String[] list = new File(pathname).list();
        assert list != null;
        if(list!=null) {
            for (String file : list) {
                properties.add(new Properties());
                Properties propertiesPlayer = properties.get(properties.size() - 1);
                propertiesPlayer.load(new FileReader(pathname + "/" + file));
                Player player = new Player(propertiesPlayer);
                localPlayers.add(player);
            }

        }
        // TODO Load player from files in dir gameData


        return localPlayers;
    }



    public Point3D forceMotrice() {

        return Point3D.O0.mult(getLocalPlayer().score());
    }

    public Player getLocalPlayer() {
        return currentPlayer;
    }

    Level1 getLevel() {
        // TODO
        return null;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }
}
