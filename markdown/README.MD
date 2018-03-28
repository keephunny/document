
### 特殊字符自动转换

## 区块元素
### 段落和换行
> 一个 Markdown 段落是由一个或多个连续的文本行组成，它的前后要有一个以上的空行（空行的定义是显示上看起来像是空的，便会被视为空行。比方说，若某一行只包含空格和制表符，则该行也会被视为空行）。普通段落不该用空格或制表符来缩进。


### 代码
可以使用 4 空格缩进或Tab```标识，实现代码的效果
* 行内代码使用 `代码` 标识，可嵌入文字中
    ```
    这里是代码
    ```
* 代码块使用4个空格或```标识
    ``` 语言
    //注意语言前面有空格
    这里是代码
    ```
### 语法着色
    ```javascript
    $(document).ready(function () {
        alert('hello world');
    });
    支持的语言：1c, abnf, accesslog, actionscript, ada, apache, applescript, arduino, armasm, asciidoc, aspectj, autohotkey, autoit, avrasm, awk, axapta, bash, basic, bnf, brainfuck, cal, capnproto, ceylon, clean, clojure, clojure-repl, cmake, coffeescript, coq, cos, cpp, crmsh, crystal, cs, csp, css, d, dart, delphi, diff, django, dns, dockerfile, dos, dsconfig, dts, dust, ebnf, elixir, elm, erb, erlang, erlang-repl, excel, fix, flix, fortran, fsharp, gams, gauss, gcode, gherkin, glsl, go, golo, gradle, groovy, haml, handlebars, haskell, haxe, hsp, htmlbars, http, hy, inform7, ini, irpf90, java, javascript, json, julia, kotlin, lasso, ldif, leaf, less, lisp, livecodeserver, livescript, llvm, lsl, lua, makefile, markdown, mathematica, matlab, maxima, mel, mercury, mipsasm, mizar, mojolicious, monkey, moonscript, n1ql, nginx, nimrod, nix, nsis, objectivec, ocaml, openscad, oxygene, parser3, perl, pf, php, pony, powershell, processing, profile, prolog, protobuf, puppet, purebasic, python, q, qml, r, rib, roboconf, rsl, ruby, ruleslanguage, rust, scala, scheme, scilab, scss, smali, smalltalk, sml, sqf, sql, stan, stata, step21, stylus, subunit, swift, taggerscript, tap, tcl, tex, thrift, tp, twig, typescript, vala, vbnet, vbscript, vbscript-html, verilog, vhdl, vim, x86asm, xl, xml, xquery, yaml, zephir
效果：
```javascript
$(document).ready(function () {
    alert('hello world');
});
如你不需要代码高亮，可以用下面的方法禁用：
```nohighlight
```

### 表格
表格对齐格式

* 居左：:----
* 居中：:----:或-----
* 居右：----:
例子：
    |标题|标题|标题|
    |:---|:---:|---:|
    |居左测试文本|居中测试文本|居右测试文本|
    |居左测试文本1|居中测试文本2|居右测试文本3|
    |居左测试文本11|居中测试文本22|居右测试文本33|
    |居左测试文本111|居中测试文本222|居右测试文本333|


|标题|标题|标题|
|:---|:---:|---:|
|居左测试文本|居中测试文本|居右测试文本|
|居左测试文本1|居中测试文本2|居右测试文本3|
|居左测试文本11|居中测试文本22|居右测试文本33|
|居左测试文本111|居中测试文本222|居右测试文本333|

### 目录TOC
根据标题生成目录，兼容性一般
    [TOC]

### 标题
    标题1
    ======
    标题2
    -----
    ## 大标题 ##
    ### 小标题 ###   
效果：

标题1
======
标题2
-----
## 大标题 ##
### 小标题 ### 

### 粗斜体
    *斜体文本*    _斜体文本_
    **粗体文本**    __粗体文本__
    ***粗斜体文本***    ___粗斜体文本___
效果：

*斜体文本*    _斜体文本_
**粗体文本**    __粗体文本__
***粗斜体文本***    ___粗斜体文本___

### 超链接
    文字链接 [链接名称](http://链接网址)
    网址链接 <http://链接网址>
    高级链接技巧
    [百度1](http://www.baidu.com/" 百度一下"){:target="_blank"}   
    这个链接用 1 作为网址变量 [Google][1].
    这个链接用 yahoo 作为网址变量 [Yahoo!][yahoo].
    然后在文档的结尾为变量赋值（网址）
    [1]: http://www.google.com/
    [yahoo]: http://www.yahoo.com/
###  锚点
只有标题支持锚点， 跳转目录方括号后 保持空格
    [公式标题锚点](#1)
    ### [需要跳转的目录] {#1}    // 方括号后保持空格
### 插入图片带有链接
    [![](./01.png '百度')](http://www.baidu.com){:target="_blank"}        // 内链式
    [![](./01.png '百度')][5]{:target="_blank"}                       // 引用式
    [5]: http://www.baidu.com

### 列表
    普通无序列表
    - 列表文本前使用 [减号+空格]
    + 列表文本前使用 [加号+空格]
    * 列表文本前使用 [星号+空格]

    普通有序列表
    1. 列表前使用 [数字+空格]
    2. 我们会自动帮你添加数字
    7. 不用担心数字不对，显示的时候我们会自动把这行的 7 纠正为 3

    列表嵌套
    1. 列出所有元素：
        - 无序列表元素 A
            1. 元素 A 的有序子列表
        - 前面加四个空格
    2. 列表里的多段换行：
        前面必须加四个空格，
        这样换行，整体的格式不会乱
    3. 列表里引用：

        > 前面空一行
        > 仍然需要在 >  前面加四个空格

    4. 列表里代码段：

        ```
        前面四个空格，之后按代码语法 ``` 书写
        ```

            或者直接空八个，引入代码块
### 任务列表
兼容性一般 要隔开一行
    - [x] 选项一
    - [ ] 选项二  
    - [ ]  [选项3]
### 引用
    普通引用
    > 引用文本前使用 [大于号+空格]
    > 折行可以不加，新起一行都要加上哦

    引用里嵌套引用
    > 最外层引用
    > > 多一个 > 嵌套一层引用
    > > > 可以嵌套很多层

    引用里嵌套代码块
    > 同样的，在前面加四个空格形成代码块
    >  
    > ```
    > 或者使用 ``` 形成代码块
    > ```
### 图片
    跟链接的方法区别在于前面加了个感叹号 ! http地址，或者相对路径
    ![图片名称](http://图片网址)
    这个链接用 1 作为网址变量 [Google][1].然后在文档的结尾位变量赋值（网址）
    [1]: http://www.google.com/logo.png
### 视频插入
注：Markdown 语法是不支持直接插入视频的
普遍的做法是 插入HTML的 iframe 框架，通过网站自带的分享功能获取，如果没有可以尝试第二种方法
第二是伪造播放界面，实质是插入视频图片，然后通过点击跳转到相关页面

代码1
注：多数第三方平台不支持插入<iframe>视频
    <iframe height=498 width=510 src='http://player.youku.com/embed/XMjgzNzM0NTYxNg==' frameborder=0 'allowfullscreen'></iframe>

    [![](./youku2.png)](http://v.youku.com/v_show/id_XMjgzNzM0NTYxNg==.html?spm=a2htv.20009910.contentHolderUnit2.A&from=y1.3-tv-grid-1007-9910.86804.1-2#paction){:target="_blank"}


### 换行
    如果另起一行，只需在当前行结尾加 2 个空格，如果是要起一个新段落，只需要空出一行即可。

### 分隔符
    如果你有写分割线的习惯，可以新起一行输入三个减号-。当前后都有段落时，请空出一行：
    在一行中用三个以上的*、-、_来建立一个分隔线，行内不能有其他东西。也可以在符号间插入空格。
    前面的段落

    ---

    后面的段落
### 脚注(注解)
使用[^]来定义脚注：
    这是一个脚注的例子[^1]
    [^1]: 这里是脚注
## 高级技巧
### 行内 HTML 元素

    使用 <kbd>Ctrl</kbd>+<kbd>Alt</kbd>+<kbd>Del</kbd> 重启电脑
使用 <kbd>Ctrl</kbd>+<kbd>Alt</kbd>+<kbd>Del</kbd> 重启电脑  
使用 Ctrl+Alt+Del 重启电脑  
### 符号转义
如果你的描述中需要用到 markdown 的符号，比如 _ # * 等，但又不想它被转义，这时候可以在这些符号前加反斜杠，如 \_ \# \* 进行避免。  
    \_不想这里的文本变斜体\_
    \*\*不想这里的文本被加粗\*\*
### 扩展
支持 jsfiddle、gist、runjs、优酷视频，直接填写 url，在其之后会自动添加预览点击会展开相关内容。
    http://{url_of_the_fiddle}/embedded/[{tabs}/[{style}]]/
    https://gist.github.com/{gist_id}
    http://runjs.cn/detail/{id}
    http://v.youku.com/v_show/id_{video_id}.html

### 公式
当你需要在编辑器中插入数学公式时，可以使用两个美元符 $$ 包裹 TeX 或 LaTeX 格式的数学公式来实现。提交后，问答和文章页会根据需要加载 Mathjax 对数学公式进行渲染。如：
    $$ x = {-b \pm \sqrt{b^2-4ac} \over 2a}. $$
    $$
    x \href{why-equal.html}{=} y^2 + 1
    $$
同时也支持 HTML 属性，如：  
    $$ (x+1)^2 = \class{hidden}{(x+1)(x+1)} $$

    $$
    (x+1)^2 = \cssId{step1}{\style{visibility:hidden}{(x+1)(x+1)}}
    $$
$$ (x+1)^2 = \class{hidden}{(x+1)(x+1)} $$

$$
(x+1)^2 = \cssId{step1}{\style{visibility:hidden}{(x+1)(x+1)}}
$$

### 常用弥补Markdown的Html标签
#### 字体
    <font face="微软雅黑" color="red" size="6">字体及字体颜色和大小</font>
    <font color="#0000ff">字体颜色</font>
#### 换行
    使用html标签`<br/>`<br/>换行
#### 文本对齐方式
    <p align="left">居左文本</p>
    <p align="center">居中文本</p>
    <p align="right">居右文本</p>
#### 下划线
    <u>下划线文本</u>