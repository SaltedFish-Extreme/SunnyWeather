# SunnyWeather
## 第一行代码实战项目 ***天气预报app***
初版完成，使用android+kotlin技术，mvvm架构，协程和Retrofit网络框架以及Material Design相关的一些技术  

**minSdkVersion:23(安卓6)**&nbsp;&nbsp;&nbsp;&nbsp;**compileSdkVersion:31(安卓12)**  

> 不同于书中使用~~彩云天气api~~，这里使用了***和风天气api***  

[和风天气功能介绍](https://dev.qweather.com/help/general/)  

[api开发文档](https://dev.qweather.com/docs/api/)  

基本的功能已经完成，包括 *城市信息查询、实时天气查询、未来一周天气查询以及当天空气生活质量查询*  

app图标的适配也好，状态栏的沉浸也好，夜间主题模式适配也罢，能尽力的都尽力了  

第一行代码第三版已经出版了一年半了，有很多东西已经过时或者废弃了，很多需要自己网上找代替的方案  

比如viewModel对象的初始化，Fragment的onActivityCreated生命周期的回调，以及用了最原始的findViewById来获取控件对象  

架构还是那一套，改了一些代码方面的逻辑以及UI方面的效果，将过时废弃的方法属性都换成SDK31可以正常使用并向下兼容的了(经手机及模拟器测试，兼容安卓6-11版本)  

那么就这样吧，这个项目到此告一段落，如果有未发现的BUG或者是新功能的提交欢迎PR