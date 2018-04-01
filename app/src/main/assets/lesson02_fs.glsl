#version 300 es

/*
 * 在使用浮点相关的数据类型时,顶点着色器和fragment着色器有所不同
 * 在顶点着色器中直接声明使用即可,
 * 在fragment着色器里,使用浮点相关的变量时,必须指定精度,若不指定精度,可能会引起编译错误
 * lowp float color
 * in mediump vec2 Coord;
 * highp mat4 m;
 * 还可以全局指定:
 * precision mediump float;
 */
precision mediump float;

in vec2 textureCoordinate;

//fragment shader基本数据类型之一,采样器数据类型
//用于采样2d纹理数据
uniform sampler2D textureSampler;

//OpenGLES 3.0之后移除了gl_FragColor这个内建变量,需要开发者手动输出
//定义输出片元颜色
out vec4 fragColor;

void main(){

//使用内建纹理获取函数获取采样器中指定纹理坐标的颜色值.
fragColor =texture(textureSampler,textureCoordinate);;

}