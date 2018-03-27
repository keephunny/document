##Maven的生命周期
	maven把项目的构建划分为不同的生命周期(lifecycle)。粗略一点的话，它这个过程(phase)包括：编译、测试、打包、集成测试、验证、部署。maven中所有的执行动作(goal)都需要指明自己在这个过程中的执行位置，然后maven执行的时候，就依照过程的发展依次调用这些goal进行各种处理。
	这个也是maven的一个基本调度机制。一般来说，位置稍后的过程都会依赖于之前的过程。当然，maven同样提供了配置文件，可以依照用户要求，跳过某些阶段。

##Maven的版本规范
	groudId：团体、组织的标识符。团体标识的约定是，它以创建这个项目的组织名称的逆向域名(reverse domain name)开头。一般对应着JAVA的包的结构。例如org.apache
	artifactId:单独项目的唯一标识符。比如我们的tomcat, commons等。不要在artifactId中包含点号(.)。
	version:一个项目的特定版本。
	packaging：项目的类型，默认是jar，描述了项目打包后的输出。类型为jar的项目产生一个JAR文件，类型为war的项目产生一个web应用。

##maven的安装
	配置环境变量

##settings.xml常用配置
	
	localRepository：本地仓库。该值表示构建系统本地仓库的路径
	proxy：代理元素包含配置代理时需要的信息
		<id>：代理的唯一定义符，用来区分不同的代理元素。                 
		<active>：该代理是否是激活的那个。true则激活代理。
		<protocol>：-代理的协议。 协议://主机名:端口，分隔成离散的元素以方便配置。
		<host>：代理的主机名。
		<port>：代理的端口。
		<username>：代理的用户名
		<password>：代理的密码
		<nonProxyHosts>：不该被代理的主机名列表。该列表的分隔符由代理服务器指定

        <server>服务器元素包含配置服务器时需要的信息 
            <id>server的id,该id与distributionManagement中repository元素的id相匹配。 
            <username>鉴权用户名
            <password>鉴权密码
            <privateKey>鉴权时使用的私钥位置 ${usr.home}/.ssh/id_dsa
            <passphrase>鉴权时使用的私钥密码
            <filePermissions>文件被创建时的权限，其对应了unix文件系统的权限，如664，或者775。
            <directoryPermissions>目录被创建时的权限。
            <configuration>传输层额外的配置项

	<mirror>给定仓库的下载镜像
		<id>该镜像的唯一标识符。id用来区分不同的mirror元素。
		<name>镜像名称
		<url>该镜像的URL
		<mirrorOf>被镜像的服务器的id,这必须和中央仓库的id central完全一致
		
	<mirror>
		<id>nexus-aliyun</id>
		<mirrorOf>central</mirrorOf>
		<name>Nexus aliyun</name>
		<url>http://maven.aliyun.com/nexus/content/groups/public</url>
	</mirror>

#pom.xml配置
##parent
如果项目中没有规定某个元素的值，那么父项目中的对应值即为项目的默认值。坐标包括group ID，artifact ID和 version。

	<parent>父项目的坐标
		<groupId>被继承的父项目的全球唯一标识符
		<artifactId>被继承的父项目的构件标识符
		<version>被继承的父项目的版本
		<relativePath>父项目的pom.xml文件的相对路径，Maven首先在构建当前项目的地方寻找父项目的pom，其次在文件系统的这个位置（relativePath位置），然后在本地仓库，最后在远程仓库寻找父项目的pom。
		
    <modelVersion> 声明项目描述符遵循哪一个POM模型版本
    <groupId>项目的全球唯一标识符
    <artifactId>构件的标识符，它和group ID一起唯一标识一个构件
    <packaging>项目产生的构件类型，例如jar、war、ear、pom。
    <version> 项目当前版本
    <name>项目的名称, Maven产生的文档用
    <url>项目主页的URL, Maven产生的文档用
    <description>项目的详细描述, Maven 产生的文档用。
    <properties>项目开发片定义属性
    	<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    	
    <dependency>项目相关的所有依赖
		<groupId>依赖的group ID
	 	<artifactId> 依赖的artifact ID 
		<version>依赖的版本号 
       <type> 依赖类型，默认类型是jar
       <scope> test </scope> 
    <dependencyManagement>是表示依赖jar包的声明，即你在项目中的dependencyManagement下声明了依赖，maven不会加载该依赖，主要是为了统一管理插件，确保所有子项目使用的插件版本保持一致。
       
#maen命令
	mvn:clean 清除产生的项目 target目录
	mvn:validate 验证项目是否正确，以及所有为了完整构建必要的信息是否可用
	mvn:compile 编译项目的源代码
	mvn:test 使用合适的单元测试框架运行测试。
	mvn:package 将编译好的代码打包成可分发的格式，如JAR，WAR
	mvn:verify 执行所有检查，验证包是有效的，符合质量规范
	mvn:install 安装包至本地仓库，以备本地的其它项目作为依赖使用
	mvn:site 命令支持各种文档信息的发布，包括构建过程的各种输出，javadoc，产品文档等。
	mvn:deploy  复制最终的包至远程仓库，共享给其它开发人员和项目（通常和一次正式的发布相关）