package com.sivin.learnopengles3x.common;

import android.opengl.GLSurfaceView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sivin.learnopengles3x.R;
import com.sivin.learnopengles3x.lesson01.RectFilter;
import com.sivin.learnopengles3x.lesson02.TextureFilter;

public class RenderActivity extends AppCompatActivity {

    private static final int VERTEXT_SHADER = 0;
    private static final int FRAGMENT_SHADER = 1;
    private String VERTEX_SHADER_FORMAT = "lesson%s_vs.glsl";
    private String FRAGMENT_SHADER_FORMAT = "lesson%s_fs.glsl";
    private Render mRender;
    private int filterType;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_render);
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
                filter = new RectFilter(this,
                        getShaderName(VERTEXT_SHADER, "01"),
                        getShaderName(FRAGMENT_SHADER, "01"));
                break;
            case 2:
                filter = new TextureFilter(this,
                        getShaderName(VERTEXT_SHADER, "02"),
                        getShaderName(FRAGMENT_SHADER, "02"));
                break;

            default:
                filter = new RectFilter(this,
                        getShaderName(VERTEXT_SHADER, "01"),
                        getShaderName(FRAGMENT_SHADER, "01"));
                break;
        }
        return filter;
    }


    public String getShaderName(int type, String index) {

        if (type == VERTEXT_SHADER) {
            return String.format(VERTEX_SHADER_FORMAT, index);
        } else {
            return String.format(FRAGMENT_SHADER_FORMAT, index);
        }
    }


    @Override
    protected void onDestroy() {
        if (mRender != null) {
            mRender.onDestroy();
        }
        super.onDestroy();
    }
}
