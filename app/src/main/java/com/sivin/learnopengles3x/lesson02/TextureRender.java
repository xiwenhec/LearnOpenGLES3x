package com.sivin.learnopengles3x.lesson02;

import android.opengl.GLES30;
import android.opengl.GLSurfaceView;
import android.util.Log;

import com.sivin.learnopengles3x.common.BaseFilter;
import com.sivin.learnopengles3x.common.GLESUtils;
import com.sivin.learnopengles3x.common.Render;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * @author Sivin 2018/3/31
 * Description:
 */
public class TextureRender extends Render {


    public TextureRender(GLSurfaceView renderView) {
        super(renderView);
    }

    @Override
    protected BaseFilter getFilter() {
        String mVertexShader = GLESUtils.loadAssetFileContent("lesson02_vs.glsl",renderView.getResources());
        String mFragmentShader = GLESUtils.loadAssetFileContent("lesson02_fs.glsl",renderView.getResources());
        return  new TextureFilter(mVertexShader,mFragmentShader);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        mFilter.onGLInit();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        GLES30.glViewport(0,0,width,height);
        GLES30.glClearColor(0.0f,0.0f,0.0f,0.0f);
        GLES30.glClear(GLES30.GL_COLOR_BUFFER_BIT|GLES30.GL_DEPTH_BUFFER_BIT);

    }

    @Override
    public void onDrawFrame(GL10 gl) {
        mFilter.onDraw();
    }


}
