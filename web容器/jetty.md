jetty是一个servlet引擎，它的架构比较简单，也是一个可扩展性和非常灵活的应用服务器，它有一个基本数据模型，这个数据模型就是handler，所有可以被扩展的组件都可以作为一个Handler添加到Server中。jetty就是帮你管理这些Handler。

## jetty基本架构
整个jetty的核心组件由Server和Connector两个组件构成，整个Server组件是基于Handeler容器工作的。它类似于tomcat的Container容器。jetty另外一个重要组件是Connector，它负责接受客户端连接请求，并将请求要配给一个处理队列去执行。

## 接受请求
jetty作为一个独立的servlet引擎可以独立提供web服务，但是它也可以与其它web应用服务器集成，所以它可以提供其于两个协议工作，一个是http、ajp协议。
### 基于http协议工作
如果前端没有其它web服务器，那么jetty应该是其于http协议工作，也就是当jetty接到一个请求时，必须按照http协议请求和封装返回的数据，jetty创建接受连接环境需要三个步骤。
1. 创建 一个列列线程池，用于处理每个建立连接产生的任务，这个线程池可以由用户来指定，这个和tomcat类似。
2. 创建ServerSocket用于准备接受客户端的socket请求，以及客户端用来包装这个socket的一些辅助类。
3. 创建 一个或多个监听线程，用来监听访问端口是否有连接进来。 
###  基于AJP协议工作
通常一个web服务站点的后端服务器不是将java的应用服务器直接暴露给服务访问者，而是在应用服务器，如jboss的前面加一个web服务器，如apache、nginx，这样做日志分析、负载均衡、权限控制、防止恶意请求以及静态资源预加载等。
### 基于NIO方式工作
前端所描述的jetty建立客户端连接到处理客户端的连接都是基于BIO的方式，它也支持另外一个NIO处理方式，其中jetty默认的connetor就是NIO方式。

## 与 Tomcat 的比较
单纯比较 Tomcat 与 Jetty 的性能意义不是很大，只能说在某种使用场景下，它表现的各有差异。因为它们面向的使用场景不尽相同。从架构上来看 Tomcat 在处理少数非常繁忙的连接上更有优势，也就是说连接的生命周期如果短的话，Tomcat 的总体性能更高。

而 Jetty 刚好相反，Jetty 可以同时处理大量连接而且可以长时间保持这些连接。例如像一些 web 聊天应用非常适合用 Jetty 做服务器，像淘宝的 web 旺旺就是用 Jetty 作为 Servlet 引擎。

另外由于 Jetty 的架构非常简单，作为服务器它可以按需加载组件，这样不需要的组件可以去掉，这样无形可以减少服务器本身的内存开销，处理一次请求也是可以减少产生的临时对象，这样性能也会提高。另外 Jetty 默认使用的是 NIO 技术在处理 I/O 请求上更占优势，Tomcat 默认使用的是 BIO，在处理静态资源时，Tomcat 的性能不如 Jetty。


https://www.ibm.com/developerworks/cn/java/j-lo-jetty/