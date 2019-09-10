在程序中，不存在所谓的“异常”，只存在错误，错误会导致我们的程序在运行期无法继续运行，如除数为零、尝试打开不存在的文件、试图使用指向为空的对象引用……
如果是上述任意一个情形发生，那么我们的程序都将无法继续运行，当然这些错误都是可以通过编码避免，但如果某一个方法的功能就是实现除法的功能，这个方法无法控制不接受获取除数为零的意外，也无法处理当接收到除数为零时的情况，那么这个时候，你需要从当前环境中抛出一个异常，通知其他环境，这里发生了一个问题，我无法处理，我将它交给你处理（不然程序就中止了）。到这里，大家大概知道了Java中的异常和问题的关系了，异常是Java为了解决在出现问题时发出的一个通知，这个通知到达它该到达的地方，然后在这个地方得到解决，然后使程序继续向错误发生时的情况运行。


### 同时捕获多个异常
 catch (NumberFormatException | RuntimeException e) {}  
 JDK1.7新特性补充


### 栈轨迹printStackTrace
printStackTrace()提供的信息可以通过getStackTrace()方法获取到更详细的内容，该方法将返回一个数组，由栈轨迹元素(StackTraceElement)构成，每个元素表示栈中的一桢，元素0是栈顶元素，是方法调用序列中最后一个方法调用，也就是在这个方法中创建或抛出了异常。      

```
    catch (Exception e) {
        for (StackTraceElement stackTraceElement : e.getStackTrace()) {
            System.out.println(stackTraceElement.getMethodName());
        }
    }
```

### getLocalizedMessage和getMessage方法的区别
Java Exception从Throwable接口继承它们的getMessage和getLocalizedMessage方法。两者的区别是继承类应该override(覆盖)getLocalizedMessage方法来提供一个针对地区方言的错误信息。
举个例子，假设你把美式英语的代码改写成英式英语的代码。你想创建自定义Exception类，使用你这些代码的用户和开发者可能会遇到拼写和语法错误，那么你应该在这个类中override getLocalizedMessage方法来纠正这些语言上的错误。
这样的处理也可以用于Exception类信息的翻译