
### 特殊字符自动转换

## 区块元素
### 段落和换行
> 一个 Markdown 段落是由一个或多个连续的文本行组成，它的前后要有一个以上的空行（空行的定义是显示上看起来像是空的，便会被视为空行。比方说，若某一行只包含空格和制表符，则该行也会被视为空行）。普通段落不该用空格或制表符来缩进。


### 代码码
    可以使用 4 空格缩进或Tab，实现代码的效果
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
    这个链接用 1 作为网址变量 [Google][1].
    这个链接用 yahoo 作为网址变量 [Yahoo!][yahoo].
    然后在文档的结尾为变量赋值（网址）
    [1]: http://www.google.com/
    [yahoo]: http://www.yahoo.com/
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
### 换行
    如果另起一行，只需在当前行结尾加 2 个空格，如果是要起一个新段落，只需要空出一行即可。

### 分隔符
    如果你有写分割线的习惯，可以新起一行输入三个减号-。当前后都有段落时，请空出一行：
    前面的段落

    ---

    后面的段落

## 高级技巧
### 行内 HTML 元素
### 符号转义