package com.sivin.learnopengles3x.base;

import android.opengl.GLES30;

import com.sivin.learnopengles3x.utils.GLESUtils;

/**
 * @author Sivin 2018/3/31
 * Description:
 */
public abstract class BaseFilter {
    //顶点着色器和片段着色器源码
    private String mVertexShader;
    private String mFragmentShader;

    //OpenGLES 着色器程序对象,作色器程序中的一些变量设置,需要通过这个程序来完成
    protected int mGLProgram;

    //判断OpenGL的初始化工作是否全部正常完成
    private boolean mIsInit = false;

    public BaseFilter(String vShader , String fShader) {
        mVertexShader = vShader;
        mFragmentShader = fShader;
    }


    public final void init() {
        mGLProgram = GLESUtils.createGLProgram(mVertexShader, mFragmentShader);
        if (mGLProgram != 0) {
            mIsInit = onInit();
        }
    }

    public final void draw(){
        if(mIsInit){
            onDraw();
        }
    }

    public void onDestroy(){
        if(mGLProgram != 0){
            GLES30.glDeleteProgram(mGLProgram);
            mGLProgram = 0;
        }
    }

    public  void onGLSizeChanged(int width , int height){
        GLES30.glViewport(0,0,width,height);
        GLES30.glClearColor(0.5f,0.5f,0.5f,0.0f);
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT);
        onSizeChanged(width,height);
    }




    public abstract boolean onInit();

    public abstract void onSizeChanged(int width ,int height);

    protected abstract void onDraw();

}
