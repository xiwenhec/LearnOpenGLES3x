package com.sivin.learnopengles3x.common;

import android.opengl.GLES30;

import javax.microedition.khronos.opengles.GL;

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

    public BaseFilter(String vertexShader ,String fragmentShader) {
        mVertexShader = vertexShader;
        mFragmentShader = fragmentShader;
    }


    public final void onGLInit() {
        mGLProgram = GLESUtils.createGLProgram(mVertexShader, mFragmentShader);
        if (mGLProgram != 0) {
            mIsInit = onGLPrepare();
        }
    }

    public final void onDraw(){
        if(mIsInit){
            onGLStartDraw();
        }
    }

    public void onDestroy(){
        if(mGLProgram != 0){
            GLES30.glDeleteProgram(mGLProgram);
            mGLProgram = 0;
        }
    }

    public abstract boolean onGLPrepare();

    protected abstract void onGLStartDraw();

}