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

package one.empty3.library.core.testing;

import one.empty3.library.Camera;
import one.empty3.library.ITexture;
import one.empty3.library.Scene;
import one.empty3.library.ZBuffer;

import java.io.File;
import java.util.ArrayList;

/*__
 * @author Manuel DAHMEN
 */
public interface Test extends Runnable {

    /*__
     * After the loop extend to add extra info on movie
     */
    void afterRender();

    /*__
     * Prefer use scene().cameraActive()
     *
     * @return
     */
    Camera camera();

    /*__
     * Prefer use scene().cameraActive()
     *
     * @return
     */
    void camera(Camera c);

    /*__
     * Not in use
     *
     * @return
     */
    ArrayList<TestInstance.Parameter> getInitParams();


    /*__
     * boolean for begin loop and making a movie or a image sequence
     *
     * @return isLoop?
     */
    boolean loop();

    /*__
     * boolean for begin loop and making a movie or a image sequence
     *
     * @return isLoop?
     */
    void loop(boolean isLooping);

    /*__
     * Internal use
     *
     * @return
     */
    boolean nextFrame();

    /*__
     * Internal use
     */
    void publishResult();


    /*__
     * Main run test method. Don't call it directly. Called when test starts
     */
    void run();

    /*__
     * Scene to render. Instance Read only
     *
     * @return
     */
    Scene scene();

    /*__
     * Main frame animation method
     *
     * @throws Exception
     */
    void testScene() throws Exception;

    /*__
     * Main frame animation method. Load a file. Deprecated?
     *
     * @throws Exception
     */
    void testScene(File f) throws Exception;

    /*__
     * Use for drawing fast after scene is drawn
     *
     * @return instance of running ZBuffer (ZBufferImpl)
     */
    ZBuffer getZ();


    void onTextureEnds(ITexture texture, int texture_event);

    void onMaxFrame(int maxFramesEvent);

}
