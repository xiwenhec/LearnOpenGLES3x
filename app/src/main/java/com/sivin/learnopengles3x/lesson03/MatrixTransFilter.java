package com.sivin.learnopengles3x.lesson03;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLES30;
import android.opengl.GLUtils;
import android.util.Log;

import com.sivin.learnopengles3x.R;
import com.sivin.learnopengles3x.common.BaseFilter;
import com.sivin.learnopengles3x.common.GLESUtils;
import com.sivin.learnopengles3x.common.MatrixUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

/**
 * @author Sivin 2018/4/1
 * Description:
 */
public class MatrixTransFilter extends BaseFilter {


    private static final String TAG = "MatrixTransFilter";
    //位置坐标顶点数组
    private float[] vertexArray = new float[]{

            -0.5f, 0.5f,  0.0f,
            -0.5f, -0.5f, 0.0f,
            0.5f, -0.5f,  0.0f,
            0.5f, 0.5f,   0.0f,
    };

    //纹理坐标数组
    private float[] textureCoordsArray = new float[]{
            0.0f, 0.0f,
            0.0f, 1.0f,
            1.0f, 1.0f,
            1.0f, 0.0f
    };

    //绘制顺序索引数组
    private byte[] indices = new byte[]{
            0, 1, 2, 2,3,0
    };

    private FloatBuffer mVertexBuffer;
    private FloatBuffer mTextureCoordsBuffer;
    private ByteBuffer mIndicesBuffer;

    private int mMvpMatrixHandle;

    //纹理Id
    private int mTextureID;


    public MatrixTransFilter(Context context, String vShaderName, String fShaderName) {
        super(context,vShaderName, fShaderName);
        filterInit();
    }

    private void filterInit() {
        //将内存中的数据,存放的缓冲当中,这部分放在哪一个线程中其实无所谓,
        //你可以在放在主线程,可以放在OpenGL线程中,上一节,我们放在了OpenGL线程中了,
        //这里为了演示,我们放在主线程中
        mVertexBuffer = GLESUtils.createFloatBuffer(vertexArray);
        mTextureCoordsBuffer = GLESUtils.createFloatBuffer(textureCoordsArray);
        mIndicesBuffer = GLESUtils.createByteBuffer(indices);
    }


    @Override
    public boolean onGLPrepare() {
        //获取着色器程序里的属性索引
        int mPositionHandle = GLES30.glGetAttribLocation(mGLProgram, "aPosition");
        int mTextureCoordHandle = GLES30.glGetAttribLocation(mGLProgram, "aTextureCoord");


        //将缓冲的中的数据,传递到显卡中,同时告诉显卡该如何解释,这些数据,stride我们可以写0,也可以写计算偏移量
        GLES30.glVertexAttribPointer(mPositionHandle, 3, GLES20.GL_FLOAT, false, 3*4, mVertexBuffer);
        GLES30.glVertexAttribPointer(mTextureCoordHandle, 2, GLES20.GL_FLOAT, false, 2*4, mTextureCoordsBuffer);

        //启用顶点属性
        GLES30.glEnableVertexAttribArray(mPositionHandle);
        GLES30.glEnableVertexAttribArray(mTextureCoordHandle);


        //获取矩阵索引
        mMvpMatrixHandle = GLES30.glGetUniformLocation(mGLProgram,"mvpMatrix");


        //获取uniform变量索引,这里我们获取的是纹理采样器对象
        int mTextureSamplerHandle = GLES30.glGetUniformLocation(mGLProgram, "sTexture");
        //下面我们为这个纹理采样器对象和纹理单元绑定,这样我的采样器就可以从改纹理单元从获取对应的文素.
        //由于这个变量是uniform修饰,因此在设置这个值的时候,需要先使用
        GLES30.glUseProgram(mGLProgram);
        GLES30.glUniform1ui(mTextureSamplerHandle, GLES30.GL_TEXTURE0);
        GLES30.glUseProgram(0);

        initTexture();
        return true;
    }

    private void initTexture() {
        mTextureID = GLESUtils.createTextureId();
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, mTextureID);

        InputStream is = mContext.getResources().openRawResource(R.raw.cat);
        Bitmap bm = BitmapFactory.decodeStream(is);
        try {
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (bm != null) {
            GLUtils.texImage2D(GLES30.GL_TEXTURE_2D, 0, bm, 0);
            bm.recycle();
            GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, 0);
        }
    }

    /**
     * 开始渲染
     */
    @Override
    protected void onGLStartDraw() {
        GLES30.glUseProgram(mGLProgram);
        if (mTextureID != 0) {
            GLES30.glActiveTexture(GLES30.GL_TEXTURE0);
            GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, mTextureID);
        }

//        MatrixUtils.pushMatrix();
//        MatrixUtils.scale(0.5f, 0.5f, 0.5f);
//        MatrixUtils.translateM(0.5f,0.5f,0.0f);
//        GLES30.glUniformMatrix4fv(mMvpMatrixHandle, 1, false, MatrixUtils.getFinalMatrix(), 0);
//        GLES30.glDrawElements(GLES30.GL_TRIANGLES, mIndicesBuffer.capacity(), GLES30.GL_UNSIGNED_BYTE, mIndicesBuffer);
//        MatrixUtils.popMatrix();
//        MatrixUtils.pushMatrix();
//       MatrixUtils.translateM(0.0f,0.0f,1.0f);
       MatrixUtils.rotateM(60,0,0,1);
      // MatrixUtils.translateM(0,0,-1.0f);


        GLES30.glUniformMatrix4fv(mMvpMatrixHandle, 1, false, MatrixUtils.getFinalMatrix(), 0);
        GLES30.glDrawElements(GLES30.GL_TRIANGLES, mIndicesBuffer.capacity(), GLES30.GL_UNSIGNED_BYTE, mIndicesBuffer);
      //  MatrixUtils.popMatrix();

    }

    @Override
    public void onGLSizeChanged(int width, int height) {
        super.onGLSizeChanged(width, height);
        float ratio = width/height;
        //初始化模型矩阵
        MatrixUtils.initModelMatrix();

        //设置camera
        MatrixUtils.setCamera(0.0f, 0.0f, 2.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f);

        //设置透视投影矩阵
        MatrixUtils.setProjectFrustum(-ratio, ratio, -1.0f, 1.0f, 1.0f,10.0f );

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.e(TAG, "onDestroy: ");
    }





}
