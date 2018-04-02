package com.sivin.learnopengles3x.lesson03;

import android.content.Context;

import com.sivin.learnopengles3x.common.BaseFilter;

/**
 * @author Sivin 2018/4/1
 * Description:
 */
public class MatrixTransFilter extends BaseFilter {
    private static final String TAG = "MatrixTransFilter";






    public MatrixTransFilter(Context context,String vShderName, String fShaderName) {
        super(context,vShderName, fShaderName);

    }

    @Override
    public boolean onGLPrepare() {
        return false;
    }

    @Override
    protected void onGLStartDraw() {

    }
}
