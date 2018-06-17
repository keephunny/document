新建maven project项目时，需要选择archetype  
archtype是模版原型，原型是一个maven项目模块工具包，一个原型被定义为从其中相同类型的所有其它事情是由一个原始图案或模型。名称配合，因为我们正在努力提一种系统，该系统提供了一种生成maven项目的一致的手段。

## 常见archtype类型
### cocoon-22-archetype-webapp
该模版包含 applicationContext.xml、log4j.xml、web.xml
### maven-archetype-quickstart
在这个项目里，除了pom.xml外，没有其他的xml了，但是有main、test两个包，包里放了一个App、AppTest类
### maven-archetype-webapp
在这个项目里，有WEB-INF目录，并且有web.xml和一个index.jsp

## maven提供的41个骨架原型
1. appfuse-basic-jsf (创建一个基于Hibernate，Spring和JSF的Web应用程序的原型) 
2. appfuse-basic-spring(创建一个基于Hibernate，Spring和Spring MVC的Web应用程序的原型) 
3. appfuse-basic-struts(创建一个基于Hibernate，Spring和Struts 2的Web应用程序的原型) 
4. appfuse-basic-tapestry(创建一个基于Hibernate，Spring 和 Tapestry 4的Web应用程序的原型) 
5. appfuse-core(创建一个基于Hibernate，Spring 和 XFire的jar应用程序的原型) 
6. appfuse-modular-jsf(创建一个基于Hibernate，Spring和JSF的模块化应用原型) 
7. appfuse-modular-spring(创建一个基于Hibernate, Spring 和 Spring MVC 的模块化应用原型) 
8. appfuse-modular-struts(创建一个基于Hibernate, Spring 和 Struts 2 的模块化应用原型) 
9. appfuse-modular-tapestry (创建一个基于 Hibernate, Spring 和 Tapestry 4 的模块化应用原型) 
10. maven-archetype-j2ee-simple(一个简单的J2EE的Java应用程序) 
11. maven-archetype-marmalade-mojo(一个Maven的 插件开发项目 using marmalade) 
12. maven-archetype-mojo(一个Maven的Java插件开发项目) 
13. maven-archetype-portlet(一个简单的portlet应用程序) 
14. maven-archetype-profiles() 
15. maven-archetype-quickstart() 
16. maven-archetype-site-simple(简单的网站生成项目) 
17. maven-archetype-site(更复杂的网站项目) 
18. maven-archetype-webapp(一个简单的Java Web应用程序) 
19. jini-service-archetype(Archetype for Jini service project creation) 
20. softeu-archetype-seam(JSF+Facelets+Seam Archetype) 
21. softeu-archetype-seam-simple(JSF+Facelets+Seam (无残留) 原型) 
22. softeu-archetype-jsf(JSF+Facelets 原型) 
23. jpa-maven-archetype(JPA 应用程序) 
24. spring-osgi-bundle-archetype(Spring-OSGi 原型) 
25. confluence-plugin-archetype(Atlassian 聚合插件原型) 
26. jira-plugin-archetype(Atlassian JIRA 插件原型) 
27. maven-archetype-har(Hibernate 存档) 
28. maven-archetype-sar(JBoss 服务存档) 
29. wicket-archetype-quickstart(一个简单的Apache Wicket的项目) 
30. scala-archetype-simple(一个简单的scala的项目) 
31. lift-archetype-blank(一个 blank/empty liftweb 项目) 
32. lift-archetype-basic(基本（liftweb）项目) 
33. cocoon-22-archetype-block-plain([http://cocoapacorg2/maven-plugins/]) 
34. cocoon-22-archetype-block([http://cocoapacorg2/maven-plugins/]) 
35. cocoon-22-archetype-webapp([http://cocoapacorg2/maven-plugins/]) 
36. myfaces-archetype-helloworld(使用MyFaces的一个简单的原型) 
37. myfaces-archetype-helloworld-facelets(一个使用MyFaces和Facelets的简单原型) 
38. myfaces-archetype-trinidad(一个使用MyFaces和Trinidad的简单原型) 
39. myfaces-archetype-jsfcomponents(一种使用MyFaces创建定制JSF组件的简单的原型) 
40. gmaven-archetype-basic(Groovy的基本原型) 
41. gmaven-archetype-mojo(Groovy mojo 原型)