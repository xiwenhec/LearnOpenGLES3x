package com.sivin.learnopengles3x.lesson1;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLES30;

import com.sivin.learnopengles3x.common.GLESUtils;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * @author Sivin 2018/3/26
 * Description:
 */
public class RectFilter {

    private static final String VERTEX_SHADER = "rect_vs.glsl";
    private static final String FRAGMENT_SHADER = "rect_fs.glsl";


    //矩形顶点数组
    private float[] vertexArray = new float[]{

            -0.5f,  0.5f,  0.0f,
            -0.5f, -0.5f,  0.0f,
            0.5f,  -0.5f,  0.0f,
            0.5f,   0.5f,  0.0f
    };

    //纹理数组
    private float[] textureArray = new float[]{
            0.0f, 0.0f,
            0.0f, 1.0f,
            1.0f, 1.0f,
            1.0f, 0.0f,
    };


    private int[] indexs = new int[]{
        0,1,2,0,2,3
    };


    //顶点数组缓冲对象
    private FloatBuffer mVertexBuffer;
    private FloatBuffer mTextureBuffer;
    private IntBuffer mIndexBuffer;

    //作色器程序
    private int mGLPro;
    private String mVertexShader;
    private String mFragmentShader;

    private int mPosHandle;
    private int mTextureHandle;


    private boolean mIsInit = false;


    public RectFilter(Context context) {

        //初始化的第一步是获取shader对象
        mVertexBuffer = GLESUtils.createFloatBuffer(vertexArray);
        mTextureBuffer = GLESUtils.createFloatBuffer(textureArray);
        mIndexBuffer = GLESUtils.createIntBuffer(indexs);
        mVertexShader = GLESUtils.loadAssetFileContent(VERTEX_SHADER, context.getResources());
        mFragmentShader = GLESUtils.loadAssetFileContent(FRAGMENT_SHADER, context.getResources());

        //openGL渲染的很重要的一步就是获取mGLProgram,因为后面的所有设置,都是在这个program对象中完成的
        mGLPro = GLESUtils.createGLProgram(mVertexShader, mFragmentShader);
        if (mGLPro != 0) {
            mPosHandle = GLES30.glGetAttribLocation(mGLPro, "aPosition");
            GLES30.glVertexAttribPointer(mPosHandle, 3, GLES20.GL_FLOAT, false, 3 * GLESUtils.FLOAT_SIZE, mVertexBuffer);
            GLES30.glEnableVertexAttribArray(mPosHandle);
            mIsInit = true;
        }
    }


    public void onDrawFragment() {
        if (!mIsInit) return;
        GLES30.glUseProgram(mGLPro);
        /*
         * 绘制的对象, index缓冲大小,缓冲类型,缓冲引用
         */
        GLES30.glDrawElements(GLES30.GL_TRIANGLES,mIndexBuffer.capacity(),GLES30.GL_UNSIGNED_INT,mIndexBuffer);
    }
}
