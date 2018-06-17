
    Throwable
        Error AWTError、IOError、ThreadDeath
        Exception 
            RuntimeException：IndexOutOfBoundsException、NullPointerException、ClassCastException
            IOException
            SQLException

java的异常被分为两大类：checked异常和runtime异常。所有runtimeException类及其子类实例被称为Runtime异常，不是runtimeException类及其子类异常实例则被称为Checked异常。  

只有java语言提供了checked异常，其它语言都没有提供checked异常，java认为checked异常都是可以被处理的。
1. 当前方法明确知道如何处理该异常，程序应该使用try catch块来捕获该异常，然后对应的catch块中修补该异常。
2. 当前方法不知道如何处理这种异常，应该在定义该方法时声明抛出该异常。 
https://blog.csdn.net/z69183787/article/details/41013607
https://www.cnblogs.com/winkey4986/archive/2012/03/19/2406030.html