package com.sivin.learnopengles3x.ui;

import android.content.Context;
import android.opengl.GLSurfaceView;

import com.sivin.learnopengles3x.base.BaseFilter;

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
        mFilter = filter();
    }


    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        mFilter.init();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        mFilter.onGLSizeChanged(width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        mFilter.draw();
    }

    protected abstract BaseFilter filter();

    protected void onDestroy(){
        if(mFilter != null){
            mFilter.onDestroy();
        }
    }



}
