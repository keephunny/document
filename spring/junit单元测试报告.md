  <!--test surefire-report:report-->

  ## pom引用
    <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-surefire-plugin</artifactId>
        <version>2.22.0</version>
        <configuration>
            <forkMode>always</forkMode>
            <parallel>methods</parallel>
            <threadCount>10</threadCount>

            <skip>false</skip>
            <includes>
                <include>**/*Test.java</include>
            </includes>
            <excludes>
                <exclude>**/BaseTest.java</exclude>
            </excludes>
        </configuration>
    </plugin>
## 生成单元报告
    test surefire-report:report

    site/surefire-report.html
    surefire-reports/