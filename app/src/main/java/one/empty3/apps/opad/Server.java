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

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/*__
 * Created by manuel on 19-05-17.
 */
public class Server {
    //protected ListSearch<Game> games;
    private static java.sql.Connection con;
    @DatabaseField
    protected URL url;


    public static void fetchPlayers()
            throws SQLException {

        Statement stmt = null;
        String query = "select * from game";
        try {
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String gameName = rs.getString("gameName");
                int playerID = rs.getInt("playerID");
                int playerUID = rs.getInt("playerUID");


            }
        } catch (SQLException e) {
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    public static void fetchPlayersByGame(Game game)
            throws SQLException {

        PreparedStatement pstmt = null;
        String query = "select * from game where game.gameName=?";
        pstmt.setNString(0, game.gameName);
        try {
            pstmt = con.prepareCall(query);
            ResultSet rs = pstmt.executeQuery(query);
            while (rs.next()) {
                String gameName = rs.getString("gameName");
                int playerID = rs.getInt("playerID");
                int playerUID = rs.getInt("playerUID");


            }
        } catch (SQLException e) {
        } finally {
            if (pstmt != null) {
                pstmt.close();
            }
        }
    }
}
