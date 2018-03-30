package com.sivin.learnopengles3x.lesson02;

import android.opengl.GLSurfaceView;

import com.sivin.learnopengles3x.common.BaseRenderActivity;
import com.sivin.learnopengles3x.lesson1.RectRender;

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
