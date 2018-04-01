package com.sivin.learnopengles3x.lesson02;

import android.opengl.GLES30;
import android.opengl.GLSurfaceView;

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
        return  new TextureFilter(mContext,mVertexShader,mFragmentShader);
    }
}
