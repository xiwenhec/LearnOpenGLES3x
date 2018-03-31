#version 300 es

in vec4 aPosition;
in vec4 aTextureCoords;
out vec2 textureCoordinate;

void main() {
    gl_Position = vec4(aPosition.xyz,1.0);
    textureCoordinate = aTextureCoords.st;
}
