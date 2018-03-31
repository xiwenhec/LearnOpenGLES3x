package com.sivin.learnopengles3x.lesson01;

import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.util.Log;

import com.sivin.learnopengles3x.common.Render;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * @author Sivin 2018/3/26
 * Description:
 */
public class RectRender extends Render {
    private RectFilter mFilter;
    public RectRender(GLSurfaceView renderView) {
        super(renderView);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        Log.e("RectRender", "onSurfaceCreated: " );
        mFilter = new RectFilter(mContext);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES30.glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        GLES30.glClearColor(0.0f,0.0f,0.0f,0.0f);
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT|GLES30.GL_DEPTH_BUFFER_BIT);
        mFilter.onDrawFragment();
    }
}
