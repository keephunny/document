
### 删除OpenJdk
```
rpm -qa|grep java

rpm -e --nodeps 上面的结果

```
### jdk配置
```
    #解压
    [root@localhost src]# tar -zvxf jdk-8u191-linux-x64.tar.gz 
    #移动到/usr/local目录
    [root@localhost src]# mv jdk1.8.0_191/ /usr/local/
    #重命名 java
    [root@localhost local]# mv /usr/local/jdk1.8.0_191/ /usr/local/java
    #修改环境变量
    [root@localhost local]# vim /etc/profile
        JAVA_HOME=/usr/local/java
        CLASSPATH=.:$JAVA_HOME/lib/tools.jar:$JAVA_HOME/lib/dt.jar
        PATH=$JAVA_HOME/bin:$PATH
    :wq  #保存
    [root@localhost local]# source /etc/profile
    [root@localhost local]# java -version
        java version "1.8.0_191"
        Java(TM) SE Runtime Environment (build 1.8.0_191-b12)
        Java HotSpot(TM) 64-Bit Server VM (build 25.191-b12, mixed mode)
```

### maven配置
```
    [root@localhost src]# tar -zxvf apache-maven-3.6.1-bin.tar.gz 
    [root@localhost src]# mkdir /usr/local/maven
    [root@localhost src]# mv apache-maven-3.6.1/* /usr/local/maven/

    [root@localhost bin]# vim /etc/profile
        export MAVEN_HOME=/usr/local/maven
        export PATH=$MAVEN_HOME/bin:$PATH
    :wq #保存
    [root@localhost bin]# source /etc/profile
    [root@localhost bin]# mvn -v
        Apache Maven 3.6.1 (d66c9c0b3152b2e69ee9bac180bb8fcc8e6af555; 2019-04-05T03:00:2908:00)
        Maven home: /usr/local/maven
        Java version: 1.8.0_191, vendor: Oracle Corporation, runtime: /usr/local/java/jre
        Default locale: zh_CN, platform encoding: UTF-8
        OS name: "linux", version: "3.10.0-229.el7.x86_64", arch: "amd64", family: "unix"

```

