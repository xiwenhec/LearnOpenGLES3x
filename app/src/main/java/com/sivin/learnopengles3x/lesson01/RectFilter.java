package com.sivin.learnopengles3x.lesson01;

import android.opengl.GLES20;
import android.opengl.GLES30;

import com.sivin.learnopengles3x.common.BaseFilter;
import com.sivin.learnopengles3x.common.GLESUtils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

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

    private int[] indexs = new int[]{
        0,1,2,0,2,3
    };


    //顶点数组缓冲对象
    private FloatBuffer mVertexBuffer;
    private IntBuffer mIndexBuffer;

    private int mPosHandle;


    public RectFilter(String vertexShader ,String fragmentShader) {
        super(vertexShader,fragmentShader);
        //初始化的第一步是获取shader对象
        mVertexBuffer = GLESUtils.createFloatBuffer(vertexArray);
        mIndexBuffer = GLESUtils.createIntBuffer(indexs);
    }

    @Override
    public boolean onGLPrepare() {
        mPosHandle = GLES30.glGetAttribLocation(mGLProgram, "aPosition");
        GLES30.glVertexAttribPointer(mPosHandle, 3, GLES20.GL_FLOAT, false, 0, mVertexBuffer);
        GLES30.glEnableVertexAttribArray(mPosHandle);
        return true;
    }

    @Override
    protected void onGLStartDraw() {
        GLES30.glUseProgram(mGLProgram);
        /*
         * 绘制的对象, index缓冲大小,缓冲类型,缓冲引用
         */
        GLES30.glDrawElements(GLES30.GL_TRIANGLES,mIndexBuffer.capacity(),GLES30.GL_UNSIGNED_INT,mIndexBuffer);
    }
}
