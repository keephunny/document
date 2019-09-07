maven打jar包，引用第三方jar
maven打war包


### assembly标准化打包


bin/ 启停脚本
conf
lib
logs

#### pom配置

```
    <plugins>
        <!--打包-->
        <!-- 打包命令 clean install -Dmaven.test.skip -Dprofiles.active=test-->
        <plugin>
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-assembly-plugin</artifactId>
            <version>2.3</version>
            <executions>
                <execution>
                    <id>assemble</id>
                    <goals>
                        <goal>single</goal>
                    </goals>
                    <phase>package</phase>
                </execution>
            </executions>
            <configuration>
                <appendAssemblyId>false</appendAssemblyId>
                <attach>false</attach>
                <!-- maven assembly插件需要一个描述文件 来告诉插件包的结构以及打包所需的文件来自哪里 -->
                <descriptors>
                    <descriptor>${basedir}/src/main/resources/assembly/${profiles.active}.xml</descriptor>
                </descriptors>
                <finalName>${project.artifactId}-${project.version}</finalName>
                <outputDirectory>${project.build.directory}</outputDirectory>
            </configuration>
        </plugin>
    </plugins>

    <!--排除resources文件夹-->
    <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-jar-plugin</artifactId>
        <version>3.0.2</version>
        <configuration>
            <excludes>
                <!--注意这玩意从编译结果目录开始算目录结构-->
                <exclude>/**/*.xml</exclude>
                <exclude>/**/*.yml</exclude>
                <exclude>logback/**</exclude>
                <exclude>assembly/**</exclude>
                <exclude>bin/**</exclude>
                <exclude>logback/**</exclude>
                <exclude>static/**</exclude>
                <exclude>templates/**</exclude>
            </excludes>
        </configuration>
    </plugin>
    
    <!--生成java doc-->
    <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-javadoc-plugin</artifactId>
        <version>2.9.1</version>
        <configuration>
            <encoding>UTF-8</encoding>
            <aggregate>true</aggregate>
            <charset>UTF-8</charset>
            <docencoding>UTF-8</docencoding>
            <reportOutputDirectory>./target/javadoc</reportOutputDirectory>
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

    <profiles>
        <profile>
            <id>dev</id>
            <properties>
                <profiles.active>dev</profiles.active>
                <maven.test.skip>true</maven.test.skip>
                <name>env</name>
                <value>dev</value>
            </properties>
            <activation>
                <activeByDefault>true</activeByDefault>
            </activation>
        </profile>
        <profile>
            <id>test</id>
            <properties>
                <profiles.active>test</profiles.active>
                <maven.test.skip>true</maven.test.skip>
                <name>env</name>
                <value>test</value>
            </properties>
        </profile>
    </profiles>
```

#### 打包命令
```
    clean install -Dmaven.test.skip -Dprofiles.active=dev
```