package com.sivin.learnopengles3x.lesson1;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLES30;

import com.sivin.learnopengles3x.common.GLESUtils;

import java.nio.FloatBuffer;

/**
 * @author Sivin 2018/3/26
 * Description:
 */
public class RectFilter {

    private static final String VERTEX_SHADER = "rect_vs.glsl";
    private static final String FRAGMENT_SHADER = "rect_fs.glsl";


    //矩形顶点数组
    private float[] vertexArray = new float[]{
            -1.0f, 1.0f, 0.0f,
            -1.0f, -1.0f, 0.0f,
            1.0f, -1.0f, 0.0f,
            1.0f, 1.0f, 0.0f
    };

    //纹理数组
    private float[] textureArray = new float[]{
            0.0f, 0.0f,
            0.0f, 1.0f,
            1.0f, 1.0f,
            1.0f, 0.0f,

    };

    //顶点数组缓冲对象
    private FloatBuffer mVertexBuffer;
    private FloatBuffer mTextureBuffer;

    //作色器程序
    private int mGLPro;
    private String mVertexShader;
    private String mFragmentShader;

    private int mPosHandle;
    private int mTextureHandle;


    private boolean mIsInit = false;


    public RectFilter(Context context) {


        mVertexBuffer = GLESUtils.createFloatBuffer(vertexArray);
        mTextureBuffer = GLESUtils.createFloatBuffer(textureArray);

        mVertexShader = GLESUtils.loadAssetFileContent(VERTEX_SHADER, context.getResources());
        mFragmentShader = GLESUtils.loadAssetFileContent(FRAGMENT_SHADER, context.getResources());

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
        GLES30.glDrawArrays(GLES20.GL_TRIANGLES,0,3);
    }
}
