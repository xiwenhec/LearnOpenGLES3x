package com.sivin.learnopengles3x.filter;

import android.opengl.GLES20;
import android.opengl.GLES30;

import com.sivin.learnopengles3x.base.BaseFilter;
import com.sivin.learnopengles3x.utils.GLESUtils;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

/**
 * @author Sivin 2018/3/26
 * Description:
 */
public class RectFilter extends BaseFilter{

    //矩形顶点数组
    private float[] vertexArray = new float[]{
            -0.5f,  0.5f,  0.0f,
            -0.5f, -0.5f,  0.0f,
            0.5f,  -0.5f,  0.0f,
            0.5f,   0.5f,  0.0f
    };


    private float[] colorArray = new float[]{
            1.0f, 0.0f, 0.0f,
            0.0f, 1.0f, 0.0f,
            0.0f, 0.0f, 1.0f,
            1.0f, 1.0f, 0.0f
    };

    private byte[] indices = new byte[]{
        0,1,2,0,2,3
    };


    //顶点数组缓冲对象
    private FloatBuffer mVertexBuffer;
    private FloatBuffer mColorBuffer;
    private ByteBuffer mIndexBuffer;


    public RectFilter(String vShaderName , String fShaderName) {
        super(vShaderName,fShaderName);
        //初始化的第一步是获取shader对象
        mVertexBuffer = GLESUtils.createFloatBuffer(vertexArray);
        mColorBuffer  = GLESUtils.createFloatBuffer(colorArray);
        mIndexBuffer = GLESUtils.createByteBuffer(indices);
    }

    @Override
    public boolean onInit() {
        int posHandle = GLES30.glGetAttribLocation(mGLProgram, "aPosition");
        int colorHandle = GLES30.glGetAttribLocation(mGLProgram,"aColor");
        GLES30.glVertexAttribPointer(posHandle, 3, GLES20.GL_FLOAT, false, 3*4, mVertexBuffer);
        GLES30.glVertexAttribPointer(colorHandle,3,GLES30.GL_FLOAT,false,3*4, mColorBuffer);
        GLES30.glEnableVertexAttribArray(posHandle);
        GLES30.glEnableVertexAttribArray(colorHandle);
        return true;
    }

    @Override
    public void onSizeChanged(int width, int height) {


    }

    @Override
    protected void onDraw() {
        GLES30.glUseProgram(mGLProgram);
        /*
         * 绘制的对象, index缓冲大小,缓冲类型,缓冲引用
         */
        GLES30.glDrawElements(GLES30.GL_TRIANGLES,mIndexBuffer.capacity(),GLES30.GL_UNSIGNED_BYTE,mIndexBuffer);
    }
}
