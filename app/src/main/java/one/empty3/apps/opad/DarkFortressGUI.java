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

import com.jogamp.newt.event.KeyListener;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLDrawableFactory;
import com.jogamp.opengl.awt.GLCanvas;
import one.empty3.apps.opad.menu.ToggleMenu;
import one.empty3.gui.ModelingInterface;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;

public final class DarkFortressGUI extends JFrame {
    private final Class<? extends Drawer> clazz;
    public PositionUpdateImpl positionUpdate;
    protected PositionUpdate mover;
    //Plotter3D plotter3D;
    Drawer drawer;
    private Class<? extends Drawer> drawerType;
    String Title;
    private DarkFortressGUIKeyListener gameKeyListener;
    private Game game;
    Plotter3D plotter3D;


    /*public Plotter3D getPlotter3D() {
        return plotter3D;
    }*/

    public DarkFortressGUI(Class<? extends Drawer> clazz) {
        super();
        this.clazz = clazz;
        this.drawerType = clazz;
        Title = "Dark Fortress ";
        setTitle(Title);

    }

    public void setLevel(Class<Terrain> sol, Player player) {
        try {
            Terrain t = sol.getConstructor().newInstance();
            mover = new PositionUpdateImpl(t, player);
            new Thread(mover).start();
            gameKeyListener= new DarkFortressGUIKeyListener(mover);
            plotter3D = new Plotter3D(mover);
            mover.setPlotter3D(plotter3D);
            new Thread(mover).start();
            new Thread(gameKeyListener).start();
            new Thread(plotter3D).start();


            Logger.getLogger(DarkFortressGUI.class.getName()).log(Level.INFO, drawerType.getSimpleName());

            if (drawerType.equals(JoglDrawer.class)) {
                Title += "with OpenGL bindings";
                drawer = new JoglDrawer(this);
                drawerType = JoglDrawer.class;


            } else if (drawerType.equals(EcDrawer.class)) {
                Title += "with Empty Canvas rendering";
                drawer = new EcDrawer(this);
                drawerType = JoglDrawer.class;
            }



            drawer.setLogic(mover);
            //drawer.setPlotter3D(plotter3D);
            drawer.setToggleMenu(new ToggleMenu());
            drawer.setLevel(sol);

            //addKeyListener(plotter3D);


            if(drawer instanceof JoglDrawer) {
                ((JoglDrawer) drawer).getGlcanvas().display();
                ((JoglDrawer) drawer).getGlcanvas().getAnimator().start();
                mover.setMain(this);
            }

            setSize(640, 480);

            addKeyListener(gameKeyListener);
           //addKeyListener(plotter3D);



            setVisible(true);

        } catch(InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException ex) {
            Logger.getLogger(DarkFortressGUI.class.getName()).log(Level.SEVERE, null, ex);
        }

    }


    public KeyListener getGameKeyListener() {
        return ( KeyListener) gameKeyListener;
    }

    public void setGame(Game game) {
        this.game = game;
    }
}
