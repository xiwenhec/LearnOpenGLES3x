#version 300 es
precision mediump float;

uniform sampler2D sTexture;

in vec2 textureCoods;

out vec4 fragColor;

void main(){
    fragColor = texture(sTexture,textureCoods);
}
