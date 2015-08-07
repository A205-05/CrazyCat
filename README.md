# CrazyCat的基本简介
一个曾经风靡微信朋友圈的小游戏“围住神经猫”，他们是用HTML5来实现的在微信上的运行，这次我们改用写Android版本的“围住神经猫”游戏

![playground](http://i0.hexunimg.cn/2014-07-25/166979326.jpg)

首先，简单为大家介绍一下这款游戏，它就是由许多个可点击的设置路障的点和一个会移动的猫组成，你只需要用路障将这只猫围住，不让它走到点阵的边缘你就获胜了

##Android版Crazycat实现的基本思路
1.想要实现Android版本的“神经猫游戏”首先我们需要绘制场景，我们可以通过绘图工具将其绘制出来
2.基本的界面实现好我们就得实现对该点触摸的点击事件来实现放置路障的效果及开始自动放置路障的效果
3.接下来我们便需要实现神经猫的基本移动，首先实现神经猫的简单基本移动，然后再进一步的优化神经猫的基本移动算法
4.游戏的实现完成后便是对场景，界面的优化、更改

###CrazyCat实现版本的基本效果


