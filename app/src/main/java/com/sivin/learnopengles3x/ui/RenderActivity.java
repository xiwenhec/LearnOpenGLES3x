package com.sivin.learnopengles3x.ui;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sivin.learnopengles3x.R;
import com.sivin.learnopengles3x.base.BaseFilter;
import com.sivin.learnopengles3x.utils.GLESUtils;
import com.sivin.learnopengles3x.filter.RectFilter;
import com.sivin.learnopengles3x.filter.TextureFilter;
import com.sivin.learnopengles3x.filter.MatrixTransFilter;

public class RenderActivity extends AppCompatActivity {

    private Context mContext;
    private Render mRender;
    private int filterType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_render);
        mContext = getApplicationContext();

        filterType = getIntent().getIntExtra("filterType", 0);
        GLSurfaceView mRenderView = findViewById(R.id.render_view);
        mRenderView.setEGLContextClientVersion(3);
        mRender = new Render(mRenderView) {
            @Override
            protected BaseFilter filter() {
                return getFilter();
            }
        };
        mRenderView.setRenderer(mRender);
        mRenderView.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
    }

    private BaseFilter getFilter() {
        BaseFilter filter = null;
        switch (filterType) {
            case 1:
                filter = new RectFilter(
                        getShader("rect_vs.glsl"),
                        getShader("rect_fs.glsl"));
                break;
            case 2:
                filter = new TextureFilter(
                        getShader("texture_vs.glsl"),
                        getShader("texture_fs.glsl"));
                break;

            case 3:
                filter = new MatrixTransFilter(
                        getShader("trans_vs.glsl"),
                        getShader("trans_fs.glsl"));
                break;

            default:
                filter = new RectFilter(
                        getShader("rect_vs.glsl"),
                        getShader("rect_fs.glsl"));
                break;
        }
        return filter;
    }


    public String getShader(String name) {
       return GLESUtils.loadShaderResource(mContext,name);
    }


    @Override
    protected void onDestroy() {
        if (mRender != null) {
            mRender.onDestroy();
        }
        super.onDestroy();
    }
}
