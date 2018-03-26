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

    public Render(GLSurfaceView renderView) {
        this.renderView = renderView;
        this.mContext = renderView.getContext();
    }

}
