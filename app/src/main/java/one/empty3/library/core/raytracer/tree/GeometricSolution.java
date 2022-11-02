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

package one.empty3.library.core.raytracer.tree;

import one.empty3.library.ECBufferedImage;
import one.empty3.library.Scene;
import one.empty3.library.ZBuffer;
import one.empty3.library.core.raytracer.RtRaytracer;

/*__
 * Created by Manuel Dahmen on 15-12-16.
 */
public abstract class GeometricSolution {
    private ECBufferedImage graph;
    private Scene scene;

    public GeometricSolution(ECBufferedImage graph, Scene scene) {
        this.graph = graph;
        this.scene = scene;
    }

    public void setGraph(ECBufferedImage graph) {
        this.graph = graph;
    }

    public ECBufferedImage getGraph() {
        return graph;
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    public abstract boolean plot();

    class ZBufferGeometricSolution extends GeometricSolution {
        private ZBuffer zbuffer;

        public ZBufferGeometricSolution(ZBuffer z, Scene scene) {
            super(graph, scene);
            zbuffer = z;
        }

        @Override
        public boolean plot() {
            return false;
        }
    }

    class RayTracerGeometricSolution extends GeometricSolution {

        private RtRaytracer raytracer;

        public RayTracerGeometricSolution(RtRaytracer rt, Scene scene) {
            super(graph, scene);
            raytracer = rt;

        }

        @Override
        public boolean plot() {
            return false;
        }
    }
}
