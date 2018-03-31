package com.sivin.learnopengles3x.common;

import android.content.Context;
import android.opengl.GLSurfaceView;

/**
 * @author Sivin 2018/3/26
 * Description:
 */
public abstract class Render implements GLSurfaceView.Renderer {

    protected GLSurfaceView renderView;
    protected Context mContext;
    protected BaseFilter mFilter;

    public Render(GLSurfaceView renderView) {
        this.renderView = renderView;
        this.mContext = renderView.getContext();
        mFilter = getFilter();
    }

    protected abstract BaseFilter getFilter();

    protected void onDestroy(){
        if(mFilter != null){
            mFilter.onDestroy();
        }
    }

}
