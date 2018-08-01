maven-jar-plugin
org.apache.maven.plugins
maven-assembly-plugin

* maven-jar-plugin：maven默认打包插件，用来创建project.jar
* maven-shade-plugin：用来打可执行包
* maven-assembly-plugin：支持定制化打包方式，例如apache项目的打包方式

### maven-assembly-plugin配置
descriptors：指定打包文件src/assembly/release.xml，在该配置文件内指定打包操作

### maven-assembly-plugin内置了几个可以用的assembly descriptor：
* bin：默认打包，会将bin目录里所有文件打到包中
* jar-with-dependencies：会将所有依赖都解压打包到生成代码中
* src：只将源码目录下的文件打包
* project：将整个project资源打包

### format指定打包类型
### includeBaseDirectory：指定是否包含打包层目录
比如finalName是output，当值为true，所有文件被放在output目录下，否则直接放到包的根目录下 
### fileSets指定要包含的文件集，可以定义多个fileset
### directory：指定要包含的目录
### outputDirectory：指定当前要包含的目录的目的地
    <configuration>  
        <finalName>demo</finalName>  
        <descriptors>  
            <descriptor>assemblies/demo.xml</descriptor>  
        </descriptors>  
        <outputDirectory>output</outputDirectory>  
    </configuration> 



    https://blog.csdn.net/cx1110162/article/details/78647164?locationNum=2&fps=1