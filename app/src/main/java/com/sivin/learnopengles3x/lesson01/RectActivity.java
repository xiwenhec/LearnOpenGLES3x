package com.sivin.learnopengles3x.lesson01;

import android.opengl.GLSurfaceView;

import com.sivin.learnopengles3x.common.BaseRenderActivity;

/**
 * @author Sivin 2018/3/26
 * Description:
 */
public class RectActivity extends BaseRenderActivity{
    @Override
    public GLSurfaceView.Renderer getRender(GLSurfaceView renderView) {
        return new RectRender(renderView);
    }
}
