package com.sivin.learnopengles3x.lesson02;

import android.opengl.GLSurfaceView;

import com.sivin.learnopengles3x.common.BaseRenderActivity;
import com.sivin.learnopengles3x.common.Render;

/**
 * @author Sivin 2018/3/26
 * Description:
 */
public class TextureActivity extends BaseRenderActivity{
    @Override
    public Render getRender(GLSurfaceView renderView) {
        return new TextureRender(renderView);
    }
}
