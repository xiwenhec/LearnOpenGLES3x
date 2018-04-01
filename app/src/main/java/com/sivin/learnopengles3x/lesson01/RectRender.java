package com.sivin.learnopengles3x.lesson01;

import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.util.Log;

import com.sivin.learnopengles3x.common.BaseFilter;
import com.sivin.learnopengles3x.common.GLESUtils;
import com.sivin.learnopengles3x.common.Render;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * @author Sivin 2018/3/26
 * Description:
 */
public class RectRender extends Render {

    private static final String VERTEX_SHADER = "lesson01_vs.glsl";
    private static final String FRAGMENT_SHADER = "lesson01_fs.glsl";

    public RectRender(GLSurfaceView renderView) {
        super(renderView);
    }

    @Override
    protected BaseFilter getFilter() {
        String mVertexShader = GLESUtils.loadAssetFileContent(VERTEX_SHADER,renderView.getResources());
        String mFragmentShader = GLESUtils.loadAssetFileContent(FRAGMENT_SHADER,renderView.getResources());
        return new RectFilter(mVertexShader,mFragmentShader);
    }


    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        Log.e("RectRender", "onSurfaceCreated: " );
        mFilter.onGLInit();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES30.glViewport(0, 0, width, height);
        GLES30.glClearColor(0.5f,0.5f,0.5f,0.0f);
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT|GLES30.GL_DEPTH_BUFFER_BIT);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        mFilter.onDraw();
    }

}
