package com.sivin.learnopengles3x.lesson03;

import android.opengl.GLSurfaceView;

import com.sivin.learnopengles3x.common.BaseFilter;
import com.sivin.learnopengles3x.common.GLESUtils;
import com.sivin.learnopengles3x.common.Render;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * @author Sivin 2018/4/1
 * Description:
 */
public class MatrixTransRender extends Render {


    public MatrixTransRender(GLSurfaceView renderView) {
        super(renderView);
    }

    @Override
    protected BaseFilter getFilter() {
        String vertexShader = GLESUtils.loadAssetFileContent("lesson03_vs.glsl", mContext.getResources());
        String fragmentShader = GLESUtils.loadAssetFileContent("lesson03_fs.glsl", mContext.getResources());
        return new MatrixTransFilter(mContext,vertexShader,fragmentShader);
    }

}
