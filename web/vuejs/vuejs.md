### 事件
```
    1.@click.prevent：阻止默认事件
    2.@click.stop：阻止事件冒
    3.@click.once：事件只触发一次
    4.@click.capture：使用事件的捕获模式
    5.@click.self：只有event.target是当前操作的元素是才触发事件
    6.@click.passive：事件的默认行为立即执行，无需等待事件回调执行完毕
    7.@keyup.enter：捕获enter键 别名
            .delete
            .esc
            .space
            .tab
            .up
            .down
            .left
            .right

    @scoll事件
    @wheel事件
```
### 动态渲染
```
<div v-show="boolean"></div>
<div v-if="boolean"></div>
<div v-else-if="boolean"></div>
<div v-else></div>
```

### 列表渲染
```
可以遍历数组、对象
<div v-for="val in values">{{val}}</div>
<div v-for="(val,index) in values" :key="index">{{val}}</div>

```

### 收集表单数据
```
<input v-model="xxx"/>
<input type="radio" v-model="sex" value="male"/>
<input type="radio" v-model="sex" value="female"/>
v-model.lazy
v-model.trim
v-model.number

```

www.bootcdn.cn
dayjs日期处理库
moment.js日期处理库

v-text
v-html  
v-cloak
v-pre：跳过没有指令语法的节点，提高解析效率
<div v-once>{{name}}</div>



### 自定义指令
```
v-xxxx:value=""
directives:{

    xxxx(element,binding){
        element.innerText=xxx;
    },
    xxxx2:{
        bind(element,binding):{

        }
        inserted(element,binding):{

        }
        update(element,binding):{

        }
    }
}

```