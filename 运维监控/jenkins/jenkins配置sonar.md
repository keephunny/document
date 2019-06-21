
### 依赖插件
SonarQube Scanner	 
Sonar Quality Gates	 
Quality Gates	
怎么安装略过……

### 安装sonar-scanner
[sonar-scanner-cli-3.3.0.1492-linux.zip](./resources/sonar-scanner-cli-3.3.0.1492-linux.zip)
上传并安装到/usr/local/sonar-scanner
```
[root@localhost src]# unzip sonar-scanner-cli-3.3.0.1492-linux.zip 
[root@localhost src]# mv sonar-scanner-3.3.0.1492-linux/ /usr/local/
[root@localhost src]# mv sonar-scanner-3.3.0.1492-linux/ sonar-scanner
```
### 配置sonar-scanner
[系统管理]-[全局工具配置]-[SonarQube Scanner]   
SONAR_RUNNBER_HOME：/usr/local/sonar-scanner (安装的路径径) 

![](./resources/20190621112327.png)

### 配置sonarqube-server
[系统管理]-[系统设置]-[SonarQube Servers]   

![](./resources/20190621111853.png)


### 配置项目源码地址
![](./resources/20190621140942.png)


### Post Steps
[Post Steps]-add[Execute SonarQube Scanner]
![](./resources/20190621142914.png)

sonar-scanner配置属性二选一。
* Path to project properties：sonar.properties的相对路径
* Analysis properties：
    sonar.projectKey=com.test.project:1.0-SNAPSHOT
    #sonar.projectName=测试项目  汉字乱码未解决，只能先用unicode编码
    sonar.projectName=\u6d4b\u8bd5\u9879\u76ee
    sonar.sources=.
    sonar.java.binaries=.
    sonar.sourceEncoding=UTF-8
    sonar.language=java
![](./resources/20190621112425.png)

sonar.properties常用属性

* sonar.projectKey=项目key
* sonar.projectName=项目名称
* sonar.projectVersion=${VER} 项目版本，可以写死，也可以引用变量
* sonar.sourceEncoding=UTF-8 源文件编码
* sonar.language=java 源文件语言
* sonar.sources=src/main 源代码目录，如果多个使用","分割 例如：mode1/src/main,mode2/src/main
* sonar.tests=src/test 单元测试目录，如果多个使用","分割 例如：mode1/src/test,mode2/src/test
* sonar.exclusions=*/src/test/**/*  忽略的目录
* sonar.junit.reportsPath=target/surefire-reports  单元测试报告目录
* sonar.java.coveragePlugin=jacoco  代码覆盖率插件
* sonar.jacoco.reportPath=target/coverage-reports/jacoco.exec   jacoco.exec文件路径

### sonar扫描不通过不允许发布
Quality Gate Plugin



classes是源代码编译生成的字节码目录
coverage-reports是单元测试覆盖率报告生成目录
surefire-reports是单元测试报告生成目录
test-classes是单元测试代码编译生成的字节码目录
jacoco.exec是用于生成单元测试可执行文件


单元测试输出信息乱码，这个可以通过maven-surefire-plugin插件的configuration来配置字符编码，如下配置：

<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-surefire-plugin</artifactId>
    <version>2.5</version>
    <configuration>
        <skipTests>false</skipTests>
        <argLine>${argLine} -Dfile.encoding=UTF-8</argLine>
    </configuration>
</plugin>