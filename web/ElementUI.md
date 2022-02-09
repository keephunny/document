官网 https://element.eleme.cn/



#### 安装Element UI

```
npm install -g vue 
npm install -dg vue-cli #-dg开发环境和运行环境
vue init webpack projectname #projectName不能有大写字母

#进入项目目录 启动
cd projectname
npm run dev

src/main.js

npm start

npm install webpack -g
npm i element-ui -S
npm install -g vue-cli   

npm install sass-loader --save-dev
```





build/webpack.base.config.js中添加：

```
rules:[
{
     test: /\.scss$/,
     loaders: ['style', 'css', 'sass']
 }
 ]
```



#### 目录结构

```
-lib #打包后的文件
-packages #存放组件相关的源代码
-src #指令、混入、工具方法等源码
-types #声明文件

```





#### Button

```
  <el-row>
    <el-button @click="test">默认按钮</el-button>
    <el-button type="primary">主要按钮</el-button>
    <el-button type="success">成功按钮</el-button>
    <el-button type="info" disabled>信息按钮</el-button>
    <el-button type="warning" disabled>警告按钮</el-button>
    <el-button type="danger" disabled>危险按钮</el-button>
  </el-row>
  
  <el-button-group>
  
    <el-button type="primary">主要按钮</el-button>
    <el-button type="primary">主要按钮</el-button>
  </el-button-group>
```





```


```

