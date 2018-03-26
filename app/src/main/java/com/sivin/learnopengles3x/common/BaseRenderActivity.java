package com.sivin.learnopengles3x.common;

import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sivin.learnopengles3x.R;

public abstract class BaseRenderActivity extends AppCompatActivity {

    private GLSurfaceView mRenderView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_render);
        mRenderView = findViewById(R.id.render_view);
        mRenderView.setEGLContextClientVersion(3);
        mRenderView.setRenderer(getRender(mRenderView));
        mRenderView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }
    public abstract GLSurfaceView.Renderer getRender(GLSurfaceView renderView);
}
