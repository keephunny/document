* maven-clean-plugin：清理插件，执行清理删除已有target目录
* maven-resources-plugin：资源插件，执行资源文件的处理
* maven-compiler-plugin：编译插件，编译所有源文件生成class文件至target\class目录下 
* maven-surefire-plugin：运行测试用例
* maven-jar-plugin：对编译后生成的文件进行打包，包名默认为artifactid-verion
* maven-install-plugin：安装jar，将创建生成的jar复制到本地仓库
* maven-deploy-plugin：发布jar

### mvn clean install执行过程
    maven-clean-plugin:2.5:clean (default-clean)
    maven-resources-plugin:2.6:resources (default-resources)
    maven-compiler-plugin:3.1:compile (default-compile)
    maven-resources-plugin:2.6:testResources (default-testResources)
    maven-compiler-plugin:3.1:testCompile (default-testCompile)
    maven-surefire-plugin:2.12.4:test (default-test)
    maven-jar-plugin:2.4:jar (default-jar)
    maven-install-plugin:2.4:install (default-install)