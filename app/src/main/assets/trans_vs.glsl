#version 300 es

in vec3 aPosition;
in vec2 aTextureCoord;


uniform mat4 mvpMatrix;


out vec2 textureCoods;
void main(){

gl_Position = mvpMatrix * vec4(aPosition,1.0);

textureCoods = aTextureCoord;

}