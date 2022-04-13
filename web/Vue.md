

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







```
局部安装：
npm install webpack
npm install webpack-cli

全局安装：
npm install global webpack
npm install global webpack-cli（生成目录脚手架）


cnpm install --global vue-cli




Failed to download repo vuejs-templates/webpack-simple: connect ETIMEDOUT 192.30.253.112:443，一直报这个错

解决办法：将本机hosts文件中配置有关GitHub的IP全部删除，保存后，继续打开doc执行命令：
vue init webpack project 便可以成功！


axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';

  axios.post('/api/api/v1.0/aepDevice/queryDeviceList',{
            pageNow: that.currentPage,
            pageSize: that.pageSize,
            productId: 15074737
        },
        {headers: {'Content-Type': 'application/x-www-form-urlencoded'}}
		
		
   axios.get('/api/api/v1.0/aepDevice/queryDeviceList',{
        params: {
          pageNow: that.currentPage,
          pageSize: that.pageSize,
          productId: 15074737
        }}
	queryDeviceList(QueryDeviceListParam queryDeviceListParam) {
	
	
application/x-www-form-urlencoded
最常见的POST编码方式。在nodejs中我们可以使用querystring中间件对参数进行分离。
import axios from 'axios';
import qs from 'qs'
let data={name:'张三',age:18};
axios.post('url',qs.stringify(data))
.then(res=>{
	console.log('返回数据：',res)
})
	
multipart/form-data
import axios from 'axios';
let data=new FormData();
data.append('name','张三');
data.append('age',18);
axios.post('url',data)
.then(res=>{
	console.log('返回数据：',res)
})

application/json
axios默认的提交方式。如果使用这种编码方式，那么传递到后台的将是序列化后的json字符串。
import axios from 'axios';
let data={name:'张三',age:18};
axios.post('url',data)
.then(res=>{
	console.log('返回数据：',res)
})
	
```

