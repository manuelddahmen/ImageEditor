package one.empty3.library.shader;

/*
import java.io.*;
import com.jogamp.common.nio.Buffers;
import com.jogamp.opengl.GL3;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Vector;
import java.util.stream.Stream;
import javax.swing.JFrame;
 

 *
 * @author memmanuel

class WindowC extends JFrame implements GLEventListener {
 
    /*__
     * @return the glcanvas
     
    public GLCanvas getGlcanvas() {
        return glcanvas;
    }
 
    //getting the capabilities object of GL2 profile
    final private GLProfile profile;
    final private GLCapabilities capabilities;
    final private GLCanvas glcanvas;
    
    // GL PROGAM
    private int glProgram;
    
    // VAO AND VBOs
    private int vao[] = new int[1];    // VAO GROUPS VBOs, ONLY ONE USED
    private int vbo[] = new int[2];    // 2 VBOs FOR 2 SQUARES
 
    public WindowC() {
 
        // OpenGL CAPABILITIES
        profile = GLProfile.get(GLProfile.GL3);
        capabilities = new GLCapabilities(profile);
 
        // CANVAS
        glcanvas = new GLCanvas(capabilities);
        glcanvas.addGLEventListener(this);
        glcanvas.setSize(400, 400);
 
        // JFRAME
        this.getContentPane().add(glcanvas);
        this.setSize(this.getContentPane().getPreferredSize());
 
    }
 
    @Override
    public void display(GLAutoDrawable drawable) {
 
        GL3 gl = drawable.getGL().getGL3();
                
        // USE PROGRAM
        gl.glUseProgram(glProgram);
        
        // VBO 0
        gl.glBindBuffer(gl.GL_ARRAY_BUFFER, vbo[0]); 
        gl.glVertexAttribPointer(0, 2, gl.GL_FLOAT, false, 0, 0); // associate 0th vertex attribute with active buffer
        gl.glEnableVertexAttribArray(0); // enable the 0th vertex attribute
        
        // DRAW BUFFER
        gl.glDrawArrays(GL3.GL_POINTS, 0, 1);
 
        // VBO 1
        gl.glBindBuffer(gl.GL_ARRAY_BUFFER, vbo[1]); 
        gl.glVertexAttribPointer(0, 2, gl.GL_FLOAT, false, 0, 0); // associate 0th vertex attribute with active buffer
        gl.glEnableVertexAttribArray(0); // enable the 0th vertex attribute
 
        // DRAW BUFFER
        gl.glDrawArrays(GL3.GL_POINTS, 0, 1);        
 
        // GET LARGER POINTS SO IT IS EASY TO SEE THEM
        gl.glPointSize(3.0f);   
                
    }
 
    @Override
    public void init(GLAutoDrawable drawable) {
        
        GL3 gl = drawable.getGL().getGL3();
        
        // CREATE PROGRAM
        glProgram = gl.glCreateProgram();
        
        // CREATE VERTEX SHADER
        int vertexShader = gl.glCreateShader(GL3.GL_VERTEX_SHADER);
        int geometryShader = gl.glCreateShader(GL3.GL_GEOMETRY_SHADER);
        int fragmentShader = gl.glCreateShader(GL3.GL_FRAGMENT_SHADER);
                
        // LOAD SOURCE CODE
        String[] vertexShaderSource = readShader("VertexShader1.glsl");
        gl.glShaderSource(vertexShader, vertexShaderSource.length, vertexShaderSource, null, 0); 
        gl.glCompileShader(vertexShader);
        
        String[] geometryShaderSource = readShader("GeometryShader1.glsl");
        gl.glShaderSource(geometryShader, geometryShaderSource.length, geometryShaderSource, null, 0); 
        gl.glCompileShader(geometryShader);
        
        String[] fragmentShaderSource = readShader("FragmentShader1.glsl");
        gl.glShaderSource(fragmentShader, fragmentShaderSource.length, fragmentShaderSource, null, 0); 
        gl.glCompileShader(fragmentShader);
        
        // ATTACH VERTEX SHADER TO PROGRAM, LINK AND DELETE SHADERS
        gl.glAttachShader(glProgram, vertexShader);
        gl.glAttachShader(glProgram, geometryShader);
        gl.glAttachShader(glProgram, fragmentShader);
        gl.glLinkProgram(glProgram);
        gl.glDeleteShader(vertexShader);
        gl.glDeleteShader(geometryShader);
        gl.glDeleteShader(fragmentShader);
        
        // CREATE VAO
        gl.glGenVertexArrays(1, vao, 0);
        gl.glBindVertexArray(vao[0]);
        
        // COORDINATES SQUARES
        float[] coordinatesSquare1 = new float[]{0.5f, 0.5f};
        float[] coordinatesSquare2 = new float[]{-0.5f, -0.5f};
        
        // CREATE VBOs
        gl.glGenBuffers(2, vbo, 0);
        
        // POPULATE VBO 1 FOR SQUARE 1
        gl.glBindBuffer(gl.GL_ARRAY_BUFFER, vbo[0]);
        FloatBuffer bufferSquare1 = Buffers.newDirectFloatBuffer(coordinatesSquare1);
        gl.glBufferData(gl.GL_ARRAY_BUFFER, bufferSquare1.limit()*4, bufferSquare1, gl.GL_STATIC_DRAW);
        
        // POPULATE VBO 2 FOR SQUARE 2
        gl.glBindBuffer(gl.GL_ARRAY_BUFFER, vbo[1]);
        FloatBuffer bufferSquare2 = Buffers.newDirectFloatBuffer(coordinatesSquare2);
        gl.glBufferData(gl.GL_ARRAY_BUFFER, bufferSquare2.limit()*4, bufferSquare2, gl.GL_STATIC_DRAW);
        
 
    }
 
    @Override
    public void dispose(GLAutoDrawable drawable) {
    }
 
    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        System.out.println("reshape");
        this.display(drawable);
        
    }
        
    private String[] readShader(String filename) {  
        
        Vector<String> lines = new Vector<String>();
        
        try (Stream<String> stream = Files.getLines()(Paths.get(filename))) {
            stream.forEach(x -> lines.add(x  + "\n"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // CONVERT VECTOR TO ARRAY
        Object[] objArray = lines.toArray();
        String[] array = Arrays.copyOf(objArray, objArray.length, String[].class);         
        
        return array;
    } 
    
}   
 

public class GlslProgram {

private String loadStringFromAssetFile(Context myContext, String filePath){
    StringBuilder shaderSource = new StringBuilder();
    try {
        BufferedReader reader = new BufferedReader(new InputStreamReader(myContext.getAssets().open(filePath)));
        String line;
        while((line = reader.readLine()) != null){
            shaderSource.append(line).append("\n");
        }
        reader.close();
        return shaderSource.toString();
    } catch (IOException e) {
        e.printStackTrace();
        Log.e(TAG, "Could not load shader file");
        return null;
    }
}

private int compileShader(int shader_type, String shaderString){
    
    // This compiles the shader from the string
    int shader = glCreateShader(shader_type);
    glShaderSource(shader, shaderString); 
    glCompileShader(shader);

    // This checks for for compilation errors
    int[] compiled = new int[1];
    glGetShaderiv(shader, GL_COMPILE_STATUS, compiled, 0);
    if (compiled[0] == 0) {
        String log = glGetShaderInfoLog(shader);

        Log.e(TAG, "Shader compilation error: ");
        Log.e(TAG, log);
    }
    return shader;
}
public static void main (String [] args) {
    
// Load shaders from file
String vertexShaderString = loadStringFromAssetFile(context, "your_vertex_shader.glsl");
String fragmentShaderString = loadStringFromAssetFile(context, "your_fragment_shader.glsl");

// Compile shaders
int vertexShader = compileShader(GL_VERTEX_SHADER, vertexShaderString);
int fragmentShader = compileShader(GL_FRAGMENT_SHADER, fragmentShaderString);

// Link shaders and create shader program
int shaderProgram = glCreateProgram();
glAttachShader(shaderProgram , vertexShader);
glAttachShader(shaderProgram , fragmentShader);
glLinkProgram(shaderProgram);

// Check for linking errors:
int linkStatus[] = new int[1];
glGetProgramiv(shaderProgram, GL_LINK_STATUS, linkStatus, 0);
if (linkStatus[0] != GL_TRUE) {
    String log = glGetProgramInfoLog(shaderProgram);

    Log.e(TAG,"Could not link shader program: ");
    Log.e(TAG, log);
}

glUseProgram(shaderProgram);
} 

} 
*/
