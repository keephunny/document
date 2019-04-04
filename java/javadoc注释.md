## javadoc注释规范

    @author 标明开发该类模块的作者 
    @version 标明该类模块的版本 
    @see 参考转向，也就是相关主题 
    @param 对方法中某参数的说明 
    @return 对方法返回值的说明 
    @exception 对方法可能抛出的异常进行说明 

    @author 作者名 
    @version 版本号 
    其中，@author 可以多次使用，以指明多个作者，生成的文档中每个作者之间使用逗号 (,) 隔开。@version 也可以使用多次，只有第一次有效 

    使用 @param、@return 和 @exception 说明方法 
    这三个标记都是只用于方法的。@param 描述方法的参数，@return 描述方法的返回值，@exception 描述方法可能抛出的异常。它们的句法如下： 
    @param 参数名 参数说明 
    @return 返回值说明 
    @exception 异常类名 说明 

## javadoc生成插件
    mvn javadoc:javadoc

    <!--配置生成Javadoc包-->
    <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-javadoc-plugin</artifactId>
        <version>2.9.1</version>
        <configuration>
            <encoding>UTF-8</encoding>
            <aggregate>true</aggregate>
            <charset>UTF-8</charset>
            <docencoding>UTF-8</docencoding>
            <reportOutputDirectory>./javadoc<reportOutputDirectory>
        </configuration>
        <executions>
            <execution>
                <id>attach-javadocs</id>
                <phase>package</phase>
                <goals>
                    <goal>jar</goal>
                </goals>
            </execution>
        </executions>
    </plugin>

 ## Java Doc控制台乱码
    在IDEA中，打开File | Settings | Build, Execution, Deployment | Build Tools | Maven | Runner在VM Options中添加-Dfile.encoding=GBK，切记一定是GBK。即使用UTF-8的话，依然是乱码，这是因为Maven的默认平台编码是GBK，如果你在命令行中输入mvn -version的话，会得到如下信息，根据Default locale可以看出

    在idea 中，设置文件的编码为utf-8
    Editor-File Encoding-Global Encoding=utf-8
                        Project Encoding=utf-8

    修改环境变量
    JAVA_TOOL_OPTIONS

    -Dfile.encoding=UTF-8
    环境变量里加个这东西  这是JVM 全局属性   