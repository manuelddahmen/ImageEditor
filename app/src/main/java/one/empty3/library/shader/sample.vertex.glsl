attribute vec3 position;// input vertex position from mesh
attribute vec2 texcoord;// input vertex texture coordinate from mesh
attribute vec3 normal;// input vertex normal from mesh

varying vec2 tc;// output texture coordinate of vertex
varying vec3 fn;// output fragment normal of vertex

void main(){
    tc = texcoord;
    fn = normal;
    gl_Position = vec4(0.5 * position, 1.0);
}
