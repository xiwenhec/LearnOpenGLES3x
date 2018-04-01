package com.sivin.learnopengles3x.lesson03;

import android.opengl.GLSurfaceView;

import com.sivin.learnopengles3x.common.BaseRenderActivity;
import com.sivin.learnopengles3x.common.Render;

/**
 * @author Sivin 2018/4/1
 * Description:
 */
public class MatrixTrans extends BaseRenderActivity {
    @Override
    public Render getRender(GLSurfaceView renderView) {
        return new MatrixTransRender(renderView);
    }
}
