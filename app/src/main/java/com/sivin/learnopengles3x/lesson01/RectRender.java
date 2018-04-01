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

}
