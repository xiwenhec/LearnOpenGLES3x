package com.sivin.learnopengles3x.common;

import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sivin.learnopengles3x.R;

public abstract class BaseRenderActivity extends AppCompatActivity {

    private GLSurfaceView mRenderView;
    private Render mRender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_render);
        mRenderView = findViewById(R.id.render_view);
        mRenderView.setEGLContextClientVersion(3);
        mRender = getRender(mRenderView);
        mRenderView.setRenderer(mRender);
        mRenderView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }
    public abstract Render getRender(GLSurfaceView renderView);

    @Override
    protected void onDestroy() {
        if(mRender != null){
            mRender.onDestroy();
        }
        super.onDestroy();
    }
}
