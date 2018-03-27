## Maven的生命周期
>maven把项目的构建划分为不同的生命周期(lifecycle)。粗略一点的话，它这个过程(phase)包括：编译、测试、打包、集成测试、验证、部署。maven中所有的执行动作(goal)都需要指明自己在这个过程中的执行位置，然后maven执行的时候，就依照过程的发展依次调用这些goal进行各种处理。这个也是maven的一个基本调度机制。一般来说，位置稍后的过程都会依赖于之前的过程。当然，maven同样提供了配置文件，可以依照用户要求，跳过某些阶段。

## Maven的版本规范
* groudId：团体、组织的标识符。团体标识的约定是，它以创建这个项目的组织名称的逆向域名(reverse domain name)开头。一般对应着JAVA的包的结构。例如org.apache
* artifactId:单独项目的唯一标识符。比如我们的tomcat, commons等。不要在artifactId中包含点号(.)。
* version:一个项目的特定版本。
* packaging：项目的类型，默认是jar，描述了项目打包后的输出。类型为jar的项目产生一个JAR文件，类型为war的项目产生一个web应用。

## maven的安装配置
### 安装准备
    下载地址：https://maven.apache.org/download.cgi
    选择版本：apache-maven-3.5.3-bin.tar.gz
    由于maven依赖jdk，所以安装前确保已经安装jdk.
### 安装配置
* 下载解压后放到指定目录，例如D:\install\maven\apache-maven-3.5.0目录下
* 配置Maven环境变量
* 在我的电脑-------属性-------高级系统设置---------环境变量---------系统变量--------新建
* 变量名：M2_HOME
* 变量值：D:\install\maven\apache-maven-3.5.0
* 找到Path在环境变量值尾部加入：;%M2_HOME%\bin; 
* 在cmd 输出mvn -v显示mavne版本说明安装成功
![Alt text](/maven/resource/QQ20180327.png)

## maven全局配置文件settings.xml 
配置文件路径: xxxxx/conf/settings.xml
配置优先级
    需要注意的是：局部配置优先于全局配置。
    配置优先级从高到低：pom.xml> user settings > global settings
    如果这些文件同时存在，在应用配置时，会合并它们的内容，如果有重复的配置，优先级高的配置会覆盖优先级低的。

    localRepository：本地仓库。该值表示构建系统本地仓库的路径
    proxy：代理元素包含配置代理时需要的信息
        id：代理的唯一定义符，用来区分不同的代理元素。                 
        active：该代理是否是激活的那个。true则激活代理。
        protocol：-代理的协议。 协议://主机名:端口，分隔成离散的元素以方便配置。
        host：代理的主机名。
        port：代理的端口。
        username：代理的用户名
        password：代理的密码
        nonProxyHosts：不该被代理的主机名列表。该列表的分隔符由代理服务器指定

    server：服务器元素包含配置服务器时需要的信息，一般，仓库的下载和部署是在pom.xml文件中的repositories和distributionManagement元素中定义的。然而，一般类似用户名、密码（有些仓库访问是需要安全认证的）等信息不应该在pom.xml文件中配置，这些信息可以配置在settings.xml中。
        id：server的id,该id与distributionManagement中repository元素的id相匹配。 
        username：鉴权用户名
        password：鉴权密码
        privateKey：鉴权时使用的私钥位置 ${usr.home}/.ssh/id_dsa
        passphrase：鉴权时使用的私钥密码
        filePermissions：文件被创建时的权限，其对应了unix文件系统的权限，如664，或者775。
        directoryPermissions：目录被创建时的权限。
        configuration：传输层额外的配置项

	mirror给定仓库的下载镜像
        id：该镜像的唯一标识符。id用来区分不同的mirror元素。
        name：镜像名称
        url：该镜像的URL
        mirrorOf：被镜像的服务器的id,这必须和中央仓库的id central完全一致
	国内阿里云镜象
	&lt;mirror>
        &lt;id>nexus-aliyun</id>
        &lt;mirrorOf>central</mirrorOf>
        &lt;name>Nexus aliyun</name>
        &lt;url>http://maven.aliyun.com/nexus/content/groups/public</url>
	&lt;/mirror>


    repository：包含需要连接到远程仓库的信息
        id：远程仓库唯一标识
        name：远程仓库名称
        releases：如何处理远程仓库里发布版本的下载
            enabled：true或者false表示该仓库是否为下载某种类型构件（发布版，快照版）开启。
            updatePolicy：该元素指定更新发生的频率。Maven会比较本地POM和远程POM的时间戳。这里的选项是：always（一直），daily（默认，每日），interval：X（这里X是以分钟为单位的时间间隔），或者never（从不）。
            checksumPolicy：当Maven验证构件校验文件失败时该怎么做-ignore（忽略），fail（失败），或者warn（警告）。
        snapshots：如何处理远程仓库里快照版本的下载。有了releases和snapshots这两组配置，POM就可以在每个单独的仓库中，为每种类型的构件采取不同的策略
            enabled：
            updatePolicy：
            checksumPolicy：
        url：远程仓库URL
        layout：用于定位和排序构件的仓库布局类型-可以是default（默认）或者

    profile：根据环境参数来调整构建配置的列表。
      id：profile的唯一标识
      activation：自动触发profile的条件逻辑
      properties：扩展属性列表
      repositories：远程仓库列表
    activation：自动触发profile的条件逻辑。
    activeProfiles：手动激活profiles的列表，按照profile被应用的顺序定义
        <activeProfiles>
            <!-- 要激活的profile id -->
            <activeProfile>dev</activeProfile>
        </activeProfiles>