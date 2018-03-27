## pom.xml配置
    <build>
        <finalName>maven-project</finalName>
        <plugins>
            <plugin>
                <groupId>org.codehaus.mojo</groupId>
                <artifactId>tomcat-maven-plugin</artifactId>
                <version>1.1</version>
                <configuration>
                    <url>http://localhost:8080/manager/text</url>
                    <server>tomcat</server>
                    <username>admin</username>
                    <password>admin</password>
                    <ignorePackaging>true</ignorePackaging>
                </configuration>  
            </plugin>
        </plugins>
    </build>

## setting.xml配置
    <servers>
        <server>
            <id>tomcat</id>
            <username>admin</username>
            <password>admin</password>
        </server>
    </servers>

## tomcat-users.xml配置
    <role rolename="admin-gui"/>
    <role rolename="admin-script"/>
    <role rolename="manager-gui"/>
    <role rolename="manager-script"/>
    <role rolename="manager-jmx"/>
    <role rolename="manager-status"/>
    <user username="admin" password="admin" roles="manager-gui,manager-script,manager-jmx,manager-status,admin-script,admin-gui"/>

## mvc发布命令
    mvn clean install
    mvn tomcat:redeploy