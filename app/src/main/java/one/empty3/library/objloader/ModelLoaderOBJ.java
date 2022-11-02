///*
// *  This file is part of Empty3.
// *
// *     Empty3 is free software: you can redistribute it and/or modify
// *     it under the terms of the GNU General Public License as published by
// *     the Free Software Foundation, either version 3 of the License, or
// *     (at your option) any later version.
// *
// *     Empty3 is distributed in the hope that it will be useful,
// *     but WITHOUT ANY WARRANTY; without even the implied warranty of
// *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// *     GNU General Public License for more details.
// *
// *     You should have received a copy of the GNU General Public License
// *     along with Empty3.  If not, see <https://www.gnu.org/licenses/>. 2
// */
//
///*
// * This program is free software: you can redistribute it and/or modify
// *     it under the terms of the GNU General Public License as published by
// *     the Free Software Foundation, either version 3 of the License, or
// *     (at your option) any later version.
// *
// *     This program is distributed in the hope that it will be useful,
// *     but WITHOUT ANY WARRANTY; without even the implied warranty of
// *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// *     GNU General Public License for more details.
// *
// *     You should have received a copy of the GNU General Public License
// *     along with this program.  If not, see <https://www.gnu.org/licenses/>
// */
//
//package one.empty3.library.objloader;
//
//import com.jogamp.opengl.GL2;
//
//import java.io.BufferedReader;
//import java.io.FileInputStream;
//import java.io.InputStreamReader;
//
///*__
// * Image loading class that converts BufferedImages into a data structure that
// * can be easily passed to OpenGL.
// *
// * @author Pepijn Van Eeckhoudt Downloaded from:
// *         http://www.felixgers.de/teaching/jogl/
// */
//
//// Uses the class GLModel from JautOGL to load and display obj files.
//public class ModelLoaderOBJ {
//
//	/*
//	public void init(GL2 gl) {
//		String path1 = "W:\\nauka\\msc\\gk\\asemalaituri\\spot.obj";
//		try {
//
//			FileInputStream r_path1 = new FileInputStream(path1);
//			BufferedReader b_read1 = new BufferedReader(new InputStreamReader(
//					r_path1));
//			model1 = new GLModel(b_read1, true,
//					"W:\\nauka\\msc\\gk\\asemalaituri\\spot.mtl", gl);
//			r_path1.close();
//			b_read1.close();
//
//		} catch (Exception e) {
//			System.out.println("LOADING ERROR" + e);
//		}
//
//		System.out.println("ModelLoaderOBJ init() done"); // ddd
//	}
//
//	public void draw(GL2 gl) {
//		gl.glPushMatrix();
//		model1.opengldraw(gl);
//		gl.glPopMatrix();
//
//	}*/
//
//	public static GLModel LoadModel(String objPath, String mtlPath, GL2 gl)
//	{
//		GLModel model = null;
//		try {
//			FileInputStream r_path1 = new FileInputStream(objPath);
//			BufferedReader b_read1 = new BufferedReader(new InputStreamReader(
//					r_path1));
//			model = new GLModel(b_read1, true,
//					mtlPath, gl);
//			r_path1.close();
//			b_read1.close();
//
//		} catch (Exception e) {
//			System.out.println("LOADING ERROR" + e);
//		}
//
//		System.out.println("ModelLoaderOBJ init() done"); // ddd
//		return model;
//	}
//	public static E3Model LoadModelE3(String objPath, String mtlPath)
//	{
//		E3Model model = null;
//		try {
//			FileInputStream r_path1 = new FileInputStream(objPath);
//			BufferedReader b_read1 = new BufferedReader(new InputStreamReader(
//					r_path1));
//			model = new E3Model(b_read1, true,
//					mtlPath);
//			r_path1.close();
//			b_read1.close();
//
//		} catch (Exception e) {
//			System.out.println("LOADING ERROR" + e);
//			e.printStackTrace();
//		}
//		System.out.println("ModelLoaderOBJ init() done"); // ddd
//		return model;
//	}
//}
