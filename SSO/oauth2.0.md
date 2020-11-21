
OAuth2.0是用于授权的行业标准协议，致力于简化客户端开发人员的工作，同时为web应用程序，桌面应用程序提供特定的授权流程。

OAuth2.0有四种授权模式，分别是：
1. 授权码模式(authorization code)
2. 简化模式(implicit)
3. 密码模式(resource owner)
4. 客户端模式(client credentials)


#### 授权码模式(authorization code)
授权码模式是功能最完整，流程最严密的授权模式，它的特点是通过客户端后台服务器与认证服务器进行交互。
1. 用户访问客户端，客户端将导向认证服务器
2. 用户选择是否给予客户端授权
3. 授权通过后，认证有务器将用户导向客户端的“重定向URI“，同时附上一个授权码。
4. 客户端收到授权码，并附附上申请令牌，认证服务器核对授权码和重定向URI，确认无误后向客户端发送和更新令牌。(后端实现)

客户端申请认证参数
* reponse_type：授权类型
* client_id：客户端id
* client_secret：客户端密码
* redirect_uri：重定向URI
* scope：申请权限范围
* state：客户端当前状态，可以指定任意值，认证服务器会直接返回

认证服务器返回参数
* access_token：表示令牌
* token_type：表示令牌类型，不区分大小写
* expires_in：表示过期时间，单位秒
* refresh_token：表示更新令牌，用来获取下一次该问令牌
* scope：表示权限范围

#### 简化模式(implicit)
简化模式不通过第三方应用程序的服务器，直接在浏览器中向认证服务器申请令牌，跳过了授权码这个过程。
1. 客户端将用户导向认证服务器
2. 用户决定是否给予客户端授权
3. 授权通过，认证服务器将用户导向客户端指定的重定向URI，并在URI的hash部份包含了访问令牌
4. 浏览器向资源服务器发出请求，其中不包括上一步收到的hash值
5. 资源服务器返回一个网页，其中包含了代码可以获取hash值中的令牌
6. 浏览器执行上一步获得的脚本，取出令牌
7. 浏览器将令牌发给客户端

客户发送HTTP请求的参数
* response_type：表示授权类型
* client_id：表示客户端id
* redirect_uri：表示重定向uri
* scope：表示权限范围
* state：表示客户端当前状态

#### 密码模式(resource owner)
密码模式中，用户向客户端提供自已的用户名和密码，客户端使用这些信息向服务提供商索要授权，这种模式下用户必须把密码给客户端，但客户端不存储密码，这通常在用户对客户端高度信任的情况下。
1. 用户向客户端提供用户名和密码
2. 客户端将用户名和密码发送给认证服务器，向后者请求令牌

#### 客户端模式(client credentials)
客户端模式指客户端以自已的名义，向服务提供商进行认证
1. 客户端向认证服务器进行身份认证，并要求一个访问令牌
2. 认证服务器确认无误后，向客户端提供访问令牌


### Spring security

#### HttpSecurity常用方法
* openLogin()：用于基于OpenId的验证
* headers()：将安全标头添加到响应
* cors()：配置跨域资源共享
* sessionManagement()：允许配置会话管理
* portMapper()：允许配置一个PortMapper，https重定向http
* jee()：配置容器的预认证，在这种情况下，认证由Servlet容器管理
* rememberMe()：允许配置“记住我”验证
* authorizeRequests()：允许使用HttpServletRequest限制访问
* requestCache()：允许配置请求缓存
* exceptionHandling()：允许配置错误处理
* securityContext()：在HttpServletRequests之间的SecurityContextHolder上设置SecurityContext的管理
* servletApi()：将HttpServletRequest方法与其找到的值集成到SecurityContext中
* csrf()：添加CSRF支持，使用WebSecurityConfigureAdapter时，默认启用
* logou()：添加退出登录支持
* anonymous()：允许配置匿名用户的表示方法
* formLogin()：指定支持基于表单的身份验证
* oauth2Login()：根据外部Oauth2.0或OpenID Connect1.0提供程序配置身份验证
* requiresChannel()：配置通道安全
* httpBasic()：配置HttpBasic验证
* addFilterAt()：在指定的Filter类的位置添加过滤器

#### WebSecurityConfig
* @EnableWebSecurity：开启Security服务
* @EnableGlobalMethodSecurity：开启全局Security

#### 认证模式
* Http Basic认证头
* Http Digest认证头
* Http X.509客户端证书交换
* LADP一个常见的方法来跨平台认证需要
* Form-based authentication用于简单的用户界面
* OpenId认证

#### spring security核心组件
* SecurityContext
    安全上下文，用户通过Spring Security的检验后，验证信息存储在SecurityContext中，SecurityContext的接口定义如下，实际上主要作用就是获取Authentication对象。
    ```
    public interface SecurityContext extends Serializable {
	    Authentication getAuthentication();
        void setAuthentication(Authentication authentication);
    }
    ```
* SecurityContextHolder

* Authentication
    用来表示当前用户是谁，一般可理解为authentication是一组用户密码信息。
    * Authorities：填充用户角色信息
    * Credentials：证书，填充用户的密码信息。
    * Details：用户信息
    * Principal：填充用户名

    * getAuthorities：获取用户权限，一般情况下获取到的是用户角色信息
    * getCredentials：获取用户认证的信息，密码
    * getDetail：获取用户身份信息
    * isAuthenticated：获取当前Authentication是否已认证
    * setA

* UserDetails
    * getAuthorities：获取用户权限，一般情况下获取到的用户的角色信息
    * getPassword：获取密码
    * getUserName：获取用户名
    * isAccountNonExpired：账户是否过期
    * isAccountNonLocked：账号是否锁定
    * isCredentialsNonExpired：密码是否过期
    * isEnabled：账号是否可用
* AuthenticationManager