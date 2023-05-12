---
notebook: JUnit5
title: JUnit5问题汇总
tags: Xunit
---
# mvn命令test未执行
#### 问题: `mvn clean test -Dtest=*`
对应的test用例没有执行
#### 解决
```
对应的类名必须以Test结尾或者以Test开头
```
- pom文件添加对应解析规则:
```xml
<build>
    <pluginManagement>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-surefire-plugin</artifactId>
                <version>3.0.0-M5</version>
                <dependencies>
                    <dependency>
                        <groupId>org.junit.jupiter</groupId>
                        <artifactId>junit-jupiter-engine</artifactId>
                        <version>5.8.1</version>
                    </dependency>
                </dependencies>
                <configuration>
                    <!--要执行的标签-->
                    <!--<groups>fast</groups>-->
                    <excludedGroups>slow</excludedGroups>
                    <!--                        <groups>important</groups>-->
                    <!--不要执行的标签-->
                    <!--                        <excludedGroups>hard</excludedGroups>-->
                    <!--                        <includes>-->
                    <!--                            <include>**/*Test.java</include>-->
                    <!--                        </includes>-->
                </configuration>

            </plugin>

            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.8.1</version>
                <configuration>
                    <source>1.8</source>
                    <target>1.8</target>
                </configuration>
            </plugin>
        </plugins>
    </pluginManagement>
</build>
```