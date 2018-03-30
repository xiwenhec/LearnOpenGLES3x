

### 什么是`Filter`

    `Filter`将`Render`类的`OpenGL ES`初始化代码, 渲染绘制代码, 资源释放代码,分离出去.
    这样,在同一个`OpenGL ES`渲染线程中,通过动态切换`Filter`子类对象,可以实现渲染效果的动态切换.

#### `Filter`类的上下文环境

    `filter`是负责`OpenGL ES`相关的工作, 因此,凡是调用`OpenGL ES`API的代码, 必须要要保证代码运行的环境是在
    `OpenGL ES`线程中,这也是`OpenGL ES`代码环境的要求

#### 相关API及流程介绍

```
  /**
  * 设置OpenGL视口,即:OpenGL ES渲染的窗口,这个视口的设置,是以显示容器为参考的
  * startX ,startY:表示视口起始点的位置,在android上(0,0)点在容器的左下角
  */
  GLES30.glViewport(startX, startY, width, height);
```

`GLSurfaceView`已经帮我们准备好了`OpenGL ES`渲染环境,这里`Filter`的第一步是获取`OpenGLES`着色器程序
和`shader`相关的变量句柄.
