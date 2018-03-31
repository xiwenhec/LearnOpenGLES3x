package com.sivin.learnopengles3x.common;

import android.content.res.Resources;
import android.opengl.GLES11Ext;
import android.opengl.GLES20;
import android.opengl.GLES30;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

/**
 * @author Sivin 2018/3/26
 * Description:
 */
public class GLESUtils {

    private static final String ERROR_TAG = "ES30_ERROR";
    //一个float数据类型,占用的字节数
    public static final int FLOAT_SIZE = 4;
    public static final int NO_INIT = -1;
    public static final int NO_TEXTURE = -1;
    public static final int ON_DRAWN = 1;


    public static String loadAssetFileContent(String fileName, Resources res) {
        String result = null;
        try {
            InputStream is = res.getAssets().open(fileName);

            int ch = 0;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            while ((ch = is.read()) != -1) {
                baos.write(ch);
            }
            byte[] buff = baos.toByteArray();
            baos.close();
            is.close();
            result = new String(buff, "UTF-8");
            result = result.replace("\r\n", "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


    public static int loadShader(int shaderType, String shaderRes) {
        int shader = GLES30.glCreateShader(shaderType);
        if (shader != 0) {
            GLES30.glShaderSource(shader, shaderRes);
            GLES30.glCompileShader(shader);
            int[] complied = new int[1];
            GLES30.glGetShaderiv(shader, GLES30.GL_COMPILE_STATUS, complied, 0);
            if (complied[0] == GLES30.GL_FALSE) {
                Log.e("ES30_ERROR", "Could not compile shader " + shaderType + ":");
                Log.e("ES30_ERROR", GLES30.glGetShaderInfoLog(shader));
                GLES30.glDeleteShader(shader);
                shader = 0;
            }
        }
        return shader;
    }

    /**
     * 创建OpenGL ES着色器程序
     * @param vertexSource 顶点着色器源码
     * @param fragmentSource 片段着色器源码
     * @return 如果一切正常,则返回非零的作色器程序引用Id,否则,返回0
     */
    public static int createGLProgram(String vertexSource, String fragmentSource) {
        int vertexShader = loadShader(GLES30.GL_VERTEX_SHADER, vertexSource);
        if (vertexShader == 0) {
            Log.e(ERROR_TAG, "createGLProgram: vertexShader = 0");
            return 0;
        }
        int fragmentShader = loadShader(GLES30.GL_FRAGMENT_SHADER, fragmentSource);
        if (fragmentShader == 0) {
            Log.e(ERROR_TAG, "createGLProgram: fragmentShader = 0");
            return 0;
        }
        int program = GLES30.glCreateProgram();
        if (program != 0) {
            GLES30.glAttachShader(program, vertexShader);
            checkError("attach vertexShader");
            GLES30.glAttachShader(program, fragmentShader);
            checkError("attach fragmentShader");
            GLES30.glLinkProgram(program);
            int[] linkStatus = new int[1];
            GLES30.glGetProgramiv(program, GLES20.GL_LINK_STATUS, linkStatus, 0);
            if (linkStatus[0] != GLES30.GL_TRUE) {
                Log.e(ERROR_TAG, "Could not link program");
                Log.e(ERROR_TAG, GLES30.glGetProgramInfoLog(program));
                program = 0;
            }
        }

        //shader编译成program之后,shader就没有用了,因此可以删除
        GLES30.glDetachShader(program,vertexShader);
        GLES30.glDetachShader(program,fragmentShader);
        GLES30.glDeleteShader(vertexShader);
        GLES30.glDeleteShader(fragmentShader);

        return program;
    }


    public static void checkError(String op) {
        int error;
        if ((error = GLES30.glGetError()) != GLES30.GL_NO_ERROR) {
            Log.e(ERROR_TAG, op + " glError: " + error);
            throw new RuntimeException(op + " glError: " + error);
        }
    }


    public static FloatBuffer createFloatBuffer(float[] coords) {
        ByteBuffer bb = ByteBuffer.allocateDirect(coords.length * FLOAT_SIZE);
        bb.order(ByteOrder.nativeOrder());
        FloatBuffer fb = bb.asFloatBuffer();
        fb.put(coords);
        fb.position(0);
        return fb;
    }

    public static ByteBuffer createByteBuffer(byte[] coords) {
        ByteBuffer bb = ByteBuffer.allocateDirect(coords.length * FLOAT_SIZE);
        bb.order(ByteOrder.nativeOrder());
        bb.put(coords);
        bb.position(0);
        return bb;
    }


    public static IntBuffer createIntBuffer(int[] coords) {
        ByteBuffer bb = ByteBuffer.allocateDirect(coords.length * FLOAT_SIZE);
        bb.order(ByteOrder.nativeOrder());
        IntBuffer ib = bb.asIntBuffer();
        ib.put(coords);
        ib.position(0);
        return ib;
    }

    public static int createTextureIdOES() {
        int[] textures = new int[1];
        //生成纹理对象id,然后绑定设置
        GLES30.glGenTextures(1, textures, 0);
        GLES30.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, textures[0]);

        //设置纹理采样参数
        GLES30.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GLES30.GL_TEXTURE_MIN_FILTER, GLES30.GL_NEAREST);
        GLES30.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GLES30.GL_TEXTURE_MAG_FILTER, GLES30.GL_LINEAR);

        //设置纹理S,T的拉伸方式
        GLES30.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GLES30.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
        GLES30.glTexParameterf(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, GLES30.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);

        //解除绑定,等到使用的时候在绑定就行了
        GLES30.glBindTexture(GLES11Ext.GL_TEXTURE_EXTERNAL_OES, 0);
        return textures[0];
    }


    public static int createTextureId() {
        int[] textures = new int[1];
        //生成纹理对象id,然后绑定设置
        GLES30.glGenTextures(1, textures, 0);
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, textures[0]);

        //设置纹理采样参数
        GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MIN_FILTER, GLES30.GL_NEAREST);
        GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_MAG_FILTER, GLES30.GL_LINEAR);

        //设置纹理S,T的拉伸方式
        GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_S, GLES20.GL_CLAMP_TO_EDGE);
        GLES30.glTexParameterf(GLES30.GL_TEXTURE_2D, GLES30.GL_TEXTURE_WRAP_T, GLES20.GL_CLAMP_TO_EDGE);

        //解除绑定,等到使用的时候在绑定就行了
        GLES30.glBindTexture(GLES30.GL_TEXTURE_2D, 0);
        return textures[0];

    }


}
