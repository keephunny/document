
####  自定义插件
pom.xml配置
```
    <groupId>org.example</groupId>
    <artifactId>untitled</artifactId>
    <version>1.0-SNAPSHOT</version>
    <packaging>maven-plugin</packaging>

    <dependency>
        <groupId>org.apache.maven</groupId>
        <artifactId>maven-plugin-api</artifactId>
        <version>3.5.0</version>
    </dependency>
    <dependency>
        <groupId>org.apache.maven.plugin-tools</groupId>
        <artifactId>maven-plugin-annotations</artifactId>
        <version>3.6.0</version>
        <scope>provided</scope>
    </dependency>

    <build>
        <plugins>

            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.1</version>
                <configuration>
                    <verbose>true</verbose>
                    <fork>true</fork>
                    <executable>javac</executable>
                </configuration>
            </plugin>
        </plugins>
    </build>
```
MvnPlugin.jsva
```
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

@Mojo(name = "mvnPlugin", defaultPhase = LifecyclePhase.PACKAGE)
public class MvnPlugin extends AbstractMojo {
    private final Logger logger = LoggerFactory.getLogger(MvnPlugin.class);
    @Parameter
    private String msg;

    @Parameter
    private List<String> options;

    @Parameter(property = "args")
    private String args;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        logger.info("hello msg {}", msg);
        logger.info("hello my parameter {}", options);
        logger.info("hello my args {}", args);
    }
}
```
mvn install

#### 自定义插件使用
```
    <plugin>
        <groupId>org.example</groupId>
        <artifactId>untitled</artifactId>
        <version>1.0-SNAPSHOT</version>
        <configuration>
            <msg>This is my message</msg>
            <options>
                <option>参数1</option>
                <option>参数2</option>
            </options>
            <args>hello world</args>
        </configuration>
        <executions>
            <execution>
                <phase>package</phase>
                <goals>
                    <goal>mvnPlugin</goal>
                </goals>
            </execution>
        </executions>
    </plugin>
```
```
mvn package
[INFO] hello msg This is my message
[INFO] hello my parameter [参数1, 参数2]
[INFO] hello my args hello world
```


#### 插件安装

@Mojo(name = mvnPlugin)

安装插件要在后续使用插件，就必须至少将插件安装到本地仓库，settings.xml配置
```
    <pluginGroups>
        <!-- pluginGroup
            | Specifies a further group identifier to use for plugin lookup.
        <pluginGroup>com.your.plugins</pluginGroup>
        -->
        <pluginGroup>org.example.mvnplugin</pluginGroup>
    </pluginGroups>
```

Maven根据插件的artifactId来识别插件前缀。
mvn hello:mvnPlugin