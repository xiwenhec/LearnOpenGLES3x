package com.sivin.learnopengles3x.lesson03;

import android.content.Context;

import com.sivin.learnopengles3x.common.BaseFilter;

/**
 * @author Sivin 2018/4/1
 * Description:
 */
public class MatrixTransFilter extends BaseFilter {

    private static final String TAG = "MatrixTransFilter";
    private Context mContext;


    public MatrixTransFilter(Context context,String vertexShader, String fragmentShader) {
        super(vertexShader, fragmentShader);
        mContext = context;

    }

    @Override
    public boolean onGLPrepare() {
        return false;
    }

    @Override
    protected void onGLStartDraw() {

    }
}
