precision mediump float;
varying vec2 tc;// texture coordinate of pixel (interpolated)
varying vec3 fn;// fragment normal of pixel (interpolated)

void main() {
    gl_FragColor = vec4(tc.x, tc.y, 0.0, 1.0);
}
