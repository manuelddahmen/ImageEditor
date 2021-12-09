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

package one.empty3.library.core.raytracer;

import one.empty3.library.*;

import java.util.ArrayList;
import java.util.List;

public class RtScene {

    private int mNumNodes;
    private List<RtNode> mNodes = new ArrayList<RtNode>();
    private List<Representable> mNodesR = new ArrayList<Representable>();
    private List<RtCamera> mCameras = new ArrayList<RtCamera>();
    private List<RtLight> mLights = new ArrayList<RtLight>();
    private List<RtMatiere> mMaterials = new ArrayList<RtMatiere>();
    private RtCamera mActiveCamera;


    // constructeurs et destructeurs
    public RtScene() {
        mNumNodes = 0;
        mActiveCamera = null;


    }

    // get
    public int getNumNodes() {
        return mNodes.size();
    }

    public RtNode getNode(int i) {
        assert (i < mNodes.size());
        return mNodes.get(i);
    }

    public RtLight getLight(int i) {
        assert (i < mLights.size());
        return mLights.get(i);
    }

    public int getNumLights() {
        return mLights.size();
    }

    public RtCamera getActiveCamera() {
        return mActiveCamera;
    }

    // set
    public void setActiveCamera(int i) {
        assert (i < mCameras.size());
        mActiveCamera = mCameras.get(i);
    }

    public void setActiveCamera(RtCamera cam) {
        assert (cam != null);
        mActiveCamera = cam;
    }

    public RtMatiere getMaterial(int i) {
        assert (i < mMaterials.size());
        return mMaterials.get(i);
    }

    // methodes
    public boolean addObject(RtObject object) {
        assert (object != null);
        addNode(object);
        return true;
    }

    public boolean addCamera(RtCamera camera) {
        assert (camera != null);
        mCameras.add(camera);
        addNode(camera);
        setActiveCamera(camera);
        return true;
    }

    public boolean addNode(RtNode node) {
        assert (node != null);
        mNodes.add(node);
        mNumNodes++;
        return true;
    }

    public boolean addLight(RtLight light) {
        assert (light != null);
        mLights.add(light);
        addNode(light);
        return true;
    }

    public boolean addMaterial(RtMatiere material) {
        assert (material != null);
        mMaterials.add(material);
        return true;
    }

    public void addObject(Representable myRep) {
        mNodesR.add(myRep);
    }

    public List<Representable> getRepresentables() {
        return mNodesR;
    }
}
