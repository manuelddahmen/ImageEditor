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

/*__
 Global license :

 Microsoft Public Licence

 author Manuel Dahmen _manuel.dahmen@gmx.com_

 ***/


package one.empty3.apps.opad;

import one.empty3.library.Point3D;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Properties;

/*__
 * @author Manuel Dahmen _manuel.dahmen@gmx.com_
 */
public class Player {

    private static Iterable<? extends Object> localPlayers;
    private int playerId;
    private int score;
    private Color color;
    private String name;

    public Player(String name, Color color, int score)

    {
        this.name = name;
        this.color = color;
        this.score = score;
    }

    public Player(Properties propertiesPlayer) {
        String name = propertiesPlayer.getProperty("name");
        String[] color = propertiesPlayer.getProperty("color").split(",");
        score = Integer.parseInt(propertiesPlayer.getProperty("score"));
        playerId = Integer.parseInt(propertiesPlayer.getProperty("playerId"));

        this.name = name;
        this.color = new Color(Float.parseFloat(color[0]),
                Float.parseFloat(color[1]),
                Float.parseFloat(color[2]));
    }

    public static Player getByName(String s) throws IOException {
        Player p;
        ArrayList<Player> localPlayers = Game.getLocalPlayers();
        for (Player player : localPlayers) {
            if (player.name.equals(s)) return player;
        }
        return null;
    }

    public Point3D forceMotrice() {

        // TODO
        return null;
    }

    public double score() {
        return score;
    }

    public String getName() {
        return name;
    }

    public void save() {
        localPlayers = new ArrayList<>();

        ArrayList<Properties> properties = new ArrayList<>();

        String pathname = "gameData/players";

        String filename = pathname + "/" + name;

        File file = new File(filename);
        // New or update
        if (!file.exists() && (file.exists()&&check())) {
            try {
                PrintWriter printWriter = new PrintWriter(file);
                printWriter.println("color=" + (float)color.getRed()/255f + ","
                        + (float)color.getGreen()/255f + ","
                        + (float)color.getBlue()/255f);
                printWriter.println("name=" + name);
                printWriter.println("playerId=" + playerId);
                printWriter.println("score=" + score);
                printWriter.println("0");

                printWriter.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }


        // TODO Load player from files in dir gameData

    }

    private boolean check() {
        return true;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setName(String name) {
        this.name = name;
    }

}
