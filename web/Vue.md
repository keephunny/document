

https://cn.vuejs.org/v2/guide/

chrome调试工具

vue_dev_tools.crx 



#### 目录结构

```
|- build		//构建相关
|- config		//配置相关
|- src			//源代码
|---|- api	//所有请求
|---|- assets	//主题 字体等静态资源
|---|- components	//全局公用组件
|---|- directive	//全局指令
|---|- filtres		//全局filter
|---|- icons			//svg icons
|---|- lang				//国际化
|---|- mock				//mock模拟数据
|---|- router			//路由
|---|- styles			//全局样式
|---|- utils			//全局通用方法
|---|- vendor			//第三方库
|---|- views			//页面视图
|---|- App.vue		//入口页面
|---|- main.js		//入口 加载组件
|---|- permission.js	//权限管理
|- statis							//第三方不打包资源
|---|- Tinymce				//富文本
|- .babelrc					//babel-loader配置
|- eslintrc.js			//eslint配置项
|- index.html				//html模版
|- package.json			//依赖包配置
```



#### 路由

```
npm install vue-router --save
import VueRouter from 'vue-router'
import Vue from 'vue'

Vue.use(VueRouter);
//创建VueRouter对象
const routes=[
	
]

//vue挂载router实例 main.js
import router from './router/index'
new Vue({
	el:"#app",
	router:router
})	


<router-link to="/home">home</router-link>
<router-link to="/table">table</router-link>
<router-view></router-view>

```

