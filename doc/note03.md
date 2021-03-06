
# 矩阵变换

前面两节,我们只是在`2维`平面上绘制了一个矩形,又给这个矩形填充上了`纹理`.
但是我们不不清楚,`OpenGL`是如何完成这个的,甚至有很多人单纯的写出了效果,
却没有搞清楚`OpenGL`到底是干什么的.

本节在前两节的基础之上,对这些问题和疑惑进行深入的讨论一下.

#### `OpenGL`到底是用来干什么的

结合我的学习认知,我给出的结论是:**`OpenGL`就是在计算机里`构建`出和现实
世界一样的`虚拟世界场景`,并将这个场景渲染显示或者输出出来的一套图形API**.

也就是说,`OpenGL`其实可以理解成一个操作流程,按照这个流程,用计算机就可以虚拟出
和真实世界场景一样的`虚拟空间`,然后通过`屏幕`或者`其的方式`输出给人类可以观察到.

说一个大家都不陌生的例子:在游戏世界里的各种场景,树木,房子,以及我们的人物,等等的一些
模型,就可以用`OpenGL`来完成.当然,除了`OpenGL`还有其他的`图形API(DirectX)`也可以完成这些功能.

知道了`OpenGL`的功能之后,我们就来看看,`OpenGL`是如何实现这些功能的.

既然是`虚拟的世界场景`,那么这些物体都是虚拟出来的,也就是我们程序员按着需求自己
组织出来的.我们设想一下,我们想要显示一个广告牌,该怎么做呢,我们分两步:
首先我们构建一个矩形,为这个矩形涂抹上广告的内容.

我们来分部完成:

>* 构建矩形

我们在数学上是如何定义矩形的,很简单,我们在笛卡尔坐标系下,选取四个点,然后依次连接
这四个点,于是就完成了矩形的定义了.

在`OpenGL`中其实也差不多,我们也需要定义四个点,之前我们已经接触过,我们称之为`顶点`
然后依次连接四个点?我们可以尝试一下这样做,看看是否可以达到我们的预期效果.我们可以
用`GL_LINES`来构造图元.

如果你代码正确运行的话,显然,你构建出了一个矩形,但是,这个矩形,并不是我们想要的,因为
它是`空心`的,准确的来说,我们定义的是一个矩形框,我们需要的是一个面,因此需要换一个图元
绘制方式`(GL_TRIANGLES)`,图元绘制方面相关的知识,我们暂时先不用着急,这里我们只需要
知道`GL_TRIANGLES`绘制组成的就是面图元就行了,我们前两节也用的是这个基本图元完成的效果.

总结一下:构建一个物体模型我们需要告诉`OpenGL`两样要素:构成这个模型的`顶点`,
这些顶点之间的`组织关系`.这也是我们人类可以理解的方式.

>* 为矩形表面涂上色彩

现在我们的图元已经构建出来了,但是他是白色的,`OpenGL`默认显示白色的,我们下一步就是
为这个图元的表面抹上色彩,还记得我们第一节的效果吗,我们给我们的顶点增加了颜色属性,然后
我们在`fragmentShader`里,将差值的颜色值,赋值给了`fragColor`变量.这样我们的矩形就完成了
表面涂色,如果,将我们的矩形涂上类似于广告牌的画面,我们就需要使用`纹理映射`来完成.这也是我们
第二节的内容.相信大家都不陌生.

上面的内容也是我们前两节的知识,这里我提出几个问题:这个矩形是构建出来了,表面也有了颜色.
我们的屏幕为什么就正好看到这个样子的矩形呢?我们定义的坐标和我们的屏幕坐标是一样的吗?
为什么前两节感觉差不多呢?为什么我们定义的顶点都是在位置属性坐标都是在`[-1.0,1.0]`呢?
在现实世界中的坐标不是随便定义的吗?等等一系列的疑问?


下面就进入我们今天的正题:

#### `OpenGL`观察流水线(观察管线)

在讲述`OpenGL`管线之前,我们要明确一个知识,就是`OpengL`中模型都是对`3维`空间而言的,
即使是平面也是一样.

我们定义一个`模型`时,我们一般总是以这个`模型`的自身某一个位置为`参考点`,例如,我们要定义一个`立方体`
我们采用的是`立方体`的中心为`坐标原点`,我们定义一个矩形平面,我们以矩形的平面的中心为`坐标原点`.
在`OpenGL`中,采用的是`右手笛卡尔坐标系`,我们以物体自身的某一个位置作为参考点建立坐标系,这个坐标系,我们
称之为`局部坐标空间`. 也就是物体顶点坐标都是以自身的这原点作为参考的. 在世界空间中,组成一个场景需要很多
模型物体,我们可以一样构建这些物体,`OpenGL`初始情况下,`局部坐标空间`原点和`世界坐标空间`原点是重合在一起的.
这就使我们刚定义出的物体,都集中在`世界空间的原点`,我们想要将这些物体放到世界空间中的不同位置,就需要对这些`模型物体`
移动,说移动可能不太严密,这里我们用`变换`来描述.因为可能我们还需要对物体做`旋转`,`缩放`,`错切`,`镜像`等操作,
经过一系列的变换之后,我们的物体模型就放到了世界空间中的指定位置了.

世界空间场景布置好后,然后怎么办呢?我们都见过一个场景,就是打一些第一视角的`3D`游戏,这时,我们的显示屏目上
就出现了这些物体画面,其实第一视角,就相当于我们作为游戏中的一个人物,在观察这个构建出来的世界.一般我们观察世界的
时候,我们总是以自身所在位置作为参考点,比如说,我们经常使用的就是`我的几点钟方向`,或者`你的几点钟方向`我们总是以自身
作为参考点,同样`OpenGL`中想要显示世界空间,我们也需要一个类似于人眼功能的物体模型,来观察这个世界空间的场景.在`OpenGL`中
我们将观察的世界空间的模型定义为`摄像机`,实际上,`OpenGL`中并没有摄像机的概念,这里我们在这里进行详细的讨论,我们的目标
是为大家建立一个整体的概念,具体知识细节,大家可以参考很多教程来学习,我们在这里就不再赘述了.

有了`摄像机`之后我们就要将三维空间的物体投影到`2维`的平面中,为了让我们感觉到远小近大的效果,我们必须经过投影变换,裁剪,将我们定义
需要的空间投影到我们的`2维`平面中,在经过视口变换,将`2维`平面映射到我们的屏幕坐标空间,这样我们就看到了,世界中的物体了.

初始状态下:`OpenGL`的`局部坐标空间`,`世界坐标空间`,`观察坐标空间`,的原点都是在一起的,都位于世界的`原点`.

将模型坐标`左乘`,变换矩阵,就是在对模型进行`移动`,`缩放`等变换操作,就是我们的模型,从`局部坐标空间`,变换到`世界坐标空间`了,然后我们
在左乘一个`观察矩阵`之后,我们的参考范围就定义在了`观察坐标空间`,当我再左乘`投影矩阵`之后,就将我们的三维模型,投影到了`2维`平面中了