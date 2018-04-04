package com.sivin.learnopengles3x.common;

import android.opengl.Matrix;

/**
 * @author Sivin 2018/4/2
 * Description:
 */
public class MatrixUtils {

    private static float[] mModelMatrix = new float[16];
    private static float[] mViewMatrix = new float[16];
    private static float[] mProjectMatrix = new float[16];

    private static float[] sTempM = new float[16];
    //最终的MVP矩阵
    public static float[] mMVPMatrix;


    private static float[][] mStack = new float[10][16];

    private static int stackTop = -1;



    /**
     * 矩阵压栈
     */
    public static void pushMatrix(){
        stackTop++;
        for(int i = 0 ; i < 16 ; i++){
            mStack[stackTop][i]=mModelMatrix[i];
        }

    }

    /**
     * 弹出矩阵
     */
    public static void popMatrix(){
        for(int i=0;i<16;i++) {
            mModelMatrix[i]=mStack[stackTop][i];
        }
        stackTop--;
    }




    /**
     * -----单位矩阵----
     * 由于openGL的矩阵是列主序矩阵,也就是说
     * float[16]数据,每四个数据应该以列优先编码
     *
     * 形如下面的单位矩阵在数组中应该是这样存放的
     *  m[0]:1  m[4]:0  m[8]: 0  m[12]:0
     *  m[1]:0  m[5]:1  m[9]: 0  m[13]:0
     *  m[2]:0  m[6]:0  m[10]:1  m[14]:0
     *  m[3]:0  m[7]:0  m[11]:0  m[15]:1
     *
     * ----------------------------------
     *
     *  1 0 0 0
     *  0 1 0 0
     *  0 0 1 0
     *  0 0 0 1
     *
     */
    public static void initModelMatrix(){
        initMatrix(mModelMatrix);
    }


    /**
     * 将矩阵初始化成单位矩阵
     * @param m 矩阵M
     */
    private static void initMatrix(float[] m) {
        for(int i = 0 ; i < 16 ;i ++){
            m[i] = 0;
        }
        for(int i = 0 ; i < 16 ; i+=5){
            m[i] = 1;
        }
    }


    /**
     * 将向量进行平移预算
     * @param x x轴方向平移量度
     * @param y y轴方向平移量度
     * @param z z轴方向平移量度
     */
    public static void translateM(float x, float y, float z) {





    }

    public static void rotateM(float angle, float x, float y, float z) {

        if(x ==0.0f && y == 0.0f && z == 0.0f){
            initMatrix(sTempM);
        }else if(y == 0.0f && z == 0.0f){
            getRotateXMatrix(sTempM, angle);
        }else if(x == 0.0f && z == 0.0f){
            getRotateYMatrix(sTempM, angle);
        }else if(x== 0.0f && y == 0.0f){
            getRotateZMatrix(sTempM,angle);
        }
        Matrix.multiplyMM(mModelMatrix, 0, sTempM, 0, mModelMatrix, 0);
    }




    public static void scaleM(float x, float y, float z) {
        Matrix.scaleM(mModelMatrix, 0, x, y, z);
    }




    /**
     * 图像在坐标原点:绕X轴旋转的旋转矩阵 angle:
     *
     * 对于向量V(x,y,z)绕X旋转之后的坐标V'(x',y',z')
     *  x' = x
     *  y' = y*cosA + z*sinA
     *  z' = -y*sinA + z*cosA
     *
     * 矩阵表示如下:
     *          V' = R * V
     * 矩阵R:
     *    1      0         0      0
     *    0     cosA      sinA    0
     *    0    -sinA      cosA    0
     *    0      0         0      1
     *
     * @param angle 旋转的角度
     */
    public static void getRotateXMatrix(float[] rotateM, float angle){
        double r_a = angle/180 * Math.PI;
        float cos = (float) Math.cos(r_a);
        float sin = (float) Math.sin(r_a);

        for(int i = 0 ; i < 16 ; i++){
            rotateM[0] = 0;
        }
        rotateM[0] =  1;
        rotateM[5] =  cos;
        rotateM[6] =  -sin;

        rotateM[9] =  sin;
        rotateM[10] = cos;
        rotateM[15] = 1;

    }


    /**
     * 图像在坐标原点:绕Y轴旋转的旋转矩阵 angle:
     *
     * 对于左边V(x,y)旋转之后的坐标V'(x',y')
     *
     *          v' = R * v
     * 矩阵R:
     *
     *  cosA      0       sinA       0
     *    0       1         0        0
     * -sinA      0       cosA       0
     *    0       0         0        1
     * @param angle 旋转的角度
     */
    private static void getRotateYMatrix(float[] rotateM , float angle){
        double r_a = angle/180 * Math.PI;
        float cos = (float) Math.cos(r_a);
        float sin = (float) Math.sin(r_a);

        for(int i = 0 ; i < 16 ; i++){
            rotateM[0] = 0;
        }
        rotateM[0] =  cos;
        rotateM[2] =  -sin;
        rotateM[5] =  1;
        rotateM[8] =  sin;
        rotateM[10] = cos;
        rotateM[15] = 1;
    }



    /**
     * 图像在坐标原点:绕Y轴旋转的旋转矩阵 angle:
     *
     * 对于左边V(x,y)旋转之后的坐标V'(x',y')
     *
     *          v' = R * v
     * 矩阵R:
     *
     *  cosA   -sinA    0     0
     *  sinA    cosA    0     0
     *   0       0      1     0
     *   0       0      0     1
     * @param angle 旋转的角度
     */
    private static void getRotateZMatrix(float[] rotateM , float angle){
        double r_a = angle/180 * Math.PI;
        float cos = (float) Math.cos(r_a);
        float sin = (float) Math.sin(r_a);

        for(int i = 0 ; i < 16 ; i++){
            rotateM[0] = 0;
        }
        rotateM[0] =  cos;
        rotateM[1] =  sin;
        rotateM[4] =  -sin;
        rotateM[5] =  cos;
        rotateM[10] = 1;
        rotateM[15] = 1;
    }








    /**
     * 设置摄像机的位置
     * @param cx camera x
     * @param cy camera y
     * @param cz camera z
     * @param tx target x
     * @param ty target y
     * @param tz target z
     * @param upx up x
     * @param upy up y
     * @param upz up z
     */
    public static void setCamera(
            float cx,    //摄像机位置x
            float cy,   //摄像机位置y
            float cz,   //摄像机位置z
            float tx,   //摄像机目标点x
            float ty,   //摄像机目标点y
            float tz,   //摄像机目标点z
            float upx,  //摄像机UP向量X分量
            float upy,  //摄像机UP向量Y分量
            float upz   //摄像机UP向量Z分量
    ) {
        Matrix.setLookAtM(
                mViewMatrix,
                0,
                cx,
                cy,
                cz,
                tx,
                ty,
                tz,
                upx,
                upy,
                upz
        );
    }



    //设置透视投影参数
    public static void setProjectFrustum(

            float left,        //near面的left
            float right,    //near面的right
            float bottom,   //near面的bottom
            float top,      //near面的top
            float near,        //near面距离
            float far       //far面距离
    ) {

        Matrix.frustumM(mProjectMatrix, 0, left, right, bottom, top, near, far);
    }


    /**
     * 设置正视投影矩阵
     * @param left  left 边的坐标
     * @param right right 边的坐标
     * @param bottom bottom 边的坐标
     * @param top   top  边的坐标
     * @param near  近平面距离摄像机点的距离
     * @param far  远平面距离摄像机的距离
     */
    public static void setOrthoM(

            float left,        //near面的left
            float right,    //near面的right
            float bottom,   //near面的bottom
            float top,      //near面的top
            float near,        //near面距离
            float far       //far面距离

    ) {
        Matrix.orthoM(mProjectMatrix, 0, left, right, bottom, top, near, far);

    }



    //获取具体物体的总变换矩阵
    public static float[] getFinalMatrix() {
        mMVPMatrix = new float[16];
        Matrix.multiplyMM(mMVPMatrix, 0, mViewMatrix, 0, mModelMatrix, 0);
        Matrix.multiplyMM(mMVPMatrix, 0, mProjectMatrix, 0, mMVPMatrix, 0);
        return mMVPMatrix;
    }

}
