```
//两种传参方式
axios.get('/user?id=12345',{
			params:{
				key:"xxx"
			}
	})
  .then(function(response) {
  	console.log(response);
  }
  .catch(function(error)){
  	console.log(error);
  }
);

axios.post()

response.data：服务器响应的内容
	status：服务器响应的状态码
	statusText：状态码信息
	headers：服务器响应的头信息
	config：
```



#### axios API

```
axios({
	methos:"post",
	url:"/api/user",
	data:{
		key:"xxxx"
	}
});
```

发送请法语

axios.request(config);

axios.get(url,config);

```
{
	url:"/api/xx",	//请求地址
	method:"get",		//请求类型
	baseUrl:"http://xx",
	transformRequest:function(data,headers){
	
	},
	transformResponse:function(data,headers){
	
	},
	headers:{
		"X-Request-With":"XMLHttpRequest"
	},
	params:{
		key:xxx
	},
	//参数序列化
	paramsSerializer:function(params){
	
	},
	//作为请求主体被发送的数据
	data:{
		key:xx
	},
	//请求超时间毫秒
	timeout:1000
	//跨域请求是否需要使用凭证
	withCredentials:false
	//允许自定义处理请求 返回一个promise并应用一个有效的响应
	adapter:function(config){}
	//http基础验证，并提供凭据 设置一个Authorization头
	auth:{
		username:"xx",
		password:"xxx"
	},
	//表示服务器响应的数据类型，可以是 'arraybuffer', 'blob', 'document', 'json', 'text', 'stream'
	responseType:"json"
	responseEncoding:"utf-8"
	//is the name of the http header that carries the xsrf token value
	xsrHeaderName:"X-XSRF-TOKEN",
	//允许上传处理进度事件
	onUploadProgress:function(progressEvent){}
	//允许下载处理进度事件
	onDownloadProgress:function(progressEvent(){}
	//定义允许的响应内容最大尺寸
	maxContentLength:2000
	//定义对于给定的HTTP 响应状态码是 resolve 或 reject  promise 。如果 `validateStatus` 返回 `true` (或者设置为 `null` 或 `undefined`)，promise 将被 resolve; 否则，promise 将被 rejecte
  validateStatus: function (status) {
    return status >= 200 && status < 300; // default
  },
  // `maxRedirects` 定义在 node.js 中 follow 的最大重定向数目
  // 如果设置为0，将不会 follow 任何重定向
  maxRedirects: 5, // default

  // `socketPath` defines a UNIX Socket to be used in node.js.
  socketPath: null, // default

  //分别在node.js 中用于定义在执行 http 和 https 时使用的自定义代理。允许像这样配置选项：
  httpAgent: new http.Agent({ keepAlive: true }),
  httpsAgent: new https.Agent({ keepAlive: true }),

  // 'proxy' 定义代理服务器的主机名称和端口
  // `auth` 表示 HTTP 基础验证应当用于连接代理，并提供凭据
  // 这将会设置一个 `Proxy-Authorization` 头，覆写掉已有的通过使用 `header` 设置的自定义 `Proxy-Authorization` 头。
  proxy: {
    host: '127.0.0.1',
    port: 9000,
    auth: {
      username: 'mikeymike',
      password: 'rapunz3l'
    }
  },
  //指定用于取消请求的 cancel token
  cancelToken: new CancelToken(function (cancel) {
  })
}
```

http://www.axios-js.com/zh-cn/docs/

