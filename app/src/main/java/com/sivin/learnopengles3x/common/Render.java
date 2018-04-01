package com.sivin.learnopengles3x.common;

import android.content.Context;
import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * @author Sivin 2018/3/26
 * Description:
 */
public abstract class Render implements GLSurfaceView.Renderer {

    protected GLSurfaceView renderView;
    protected Context mContext;
    protected BaseFilter mFilter;

    public Render(GLSurfaceView renderView) {
        this.renderView = renderView;
        this.mContext = renderView.getContext();
        mFilter = getFilter();
    }


    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        mFilter.onGLInit();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        mFilter.onGLSizeChanged(width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        mFilter.onDraw();
    }




    protected abstract BaseFilter getFilter();

    protected void onDestroy(){
        if(mFilter != null){
            mFilter.onDestroy();
        }
    }



}
