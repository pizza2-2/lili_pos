<div align="center">
<img src="https://img-cdn-tc.dcloud.net.cn/uploads/avatar/000/94/00/35_avatar_max.jpg?v=1755752173?4716" width="120px" height="120px"/>

<p align="center" style="margin-top:20px">
	<a href="https://v3.vuejs.org/" target="_blank">
		<img src="https://img.shields.io/badge/uniapp x-latest-blue" alt="Vite">
	</a>
	<a href="https://v3.vuejs.org/" target="_blank">
		<img src="https://img.shields.io/badge/Vue-3.x-green" alt="Vue">
	</a>
	<a href="https://element-plus.org/#/zh-CN/component/changelog" target="_blank">
		<img src="https://img.shields.io/badge/HBuilderX-4.75-blue" alt="element plus">
	</a>
</p>

<h1>FC程序开发-uniapp x快速开发框架（uniCloud）</h1>
<div>
实现基础必备功能，让你不在为这些琐事浪费太多时间，更专注于业务开发。全面适配 iOS、安卓及鸿蒙系统，持续迭代更新。
</div>
</div>

## 功能介绍

* 隐私政策弹窗

安卓端首次启动应用弹出`《用户协议》`和`《隐私政策》`弹窗。应用上架必备功能。

* App版本更新

使用官方插件`uni-upgrade-center`配合`uniCloud`+`uniAdmin`实现App应用更新。

* 系统权限申请

解决安卓端申请应用弹窗说明，以及用户拒绝权限跳转应用设置。

* 推送通知

使用`uni-push 2.0`配合`uniCloud`实现App推送，适配iOS 安卓 鸿蒙next 三端。

* 全局状态

由于uniapp x未适配pinia，根据官方示例实现全局变量及本地存储token和用户信息（示例）。

* 路由跳转和路由拦截

封装方法使路由跳转传参更方便。配置路由白名单，实现未登录跳转登录页面。（示例）

* 简单封装网络请求

简单封装网络请求使其添加公共请求数据，以及解析返回数据。

* 全局样式以及自定义图标、字体

简单封装些常用的全局样式。自定义iconfont和字体的使用。（示例）

* 加密解密

使用uts语法封装base64,md5,sha1,sha256加密方法。

* 简单封装一些事件处理方法

时间格式化、获取某个时间前后n秒时间戳、两个时间相差描述等。

> 以上功能都是可选配置项，不需要的可以删除或更换。更多功能持续开发中...

## 项目预览

![安卓下载二维码](https://www.pgyer.com/app/qrcode/fcbasexunicloud)
**仅安卓系统安装使用**


## 开发文档

文档整理中...

## 项目截图

![项目截图](https://cdn-app-screenshot.pgyer.com/2/0/1/3/2/20132940b9a3f23fc39341426253b0ca?x-oss-process=image/resize,m_lfit,h_528,w_528/format,jpg)

## 注意事项

* 开发过程中一定要使用自定义基座运行测试。
* 各个功能耦合都不深，可以随意删除/更改。但也请在理解功能实现的情况下操作。
* 因为开发的应用不同，所以在开发过程中，还需要配合官方文档说明。
* iOS和安卓作者已经在真机运行测试过。由于作者没有纯血鸿蒙测试机，鸿蒙next只在模拟器运行测试。
