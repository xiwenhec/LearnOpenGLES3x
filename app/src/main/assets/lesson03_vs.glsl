#version 300 es

in vec3 aPosition;
in vec2 aTextureCoord;

out vec2 textureCoods;


void main(){

gl_Position = vec4(aPosition,1.0);
textureCoods = aTextureCoord;

}