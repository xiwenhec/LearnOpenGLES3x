#version 300 es

//定义顶点位置属性,
//这里我们定义4个分量的数据,是为了提高数据从内存到GPU的传递效率
in vec4 aPosition;

//定义顶点纹理属性
in vec4 aTextureCoords;

//定义输出到片元作色器的变量
out vec2 textureCoordinate;

void main(){

gl_Position = vec4(aPosition.xyz,1.0);
textureCoordinate = vec2(aTextureCoords.st);

}