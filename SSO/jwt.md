### json web tokens
随着分布式web项目的增多，通过session管理用户登录状态的成本越来越高。通过token身份校验，然后通过token去redis里取用户信息。
jwt的校验方式更加简单便捷，无需通过redis，而是直接根据token取出保存的用户信息，以及对token可用性校验，单点登录更为简单。


* 体积小、传输速度快。
* 传输方式多样，可以通过URL、POST参数、HTTP头等方式传输。
* 严格的结构化，在payload中就包含了所有与用户相关的验证信息。
* 支持跨域验证，可以应用于单点登录。

### 应用场景
authorization(授权)：一旦用户登录后续的每个请求都将包含jwt，允许用户访问该令牌授权的路由。单点登录是现在广泛使用的jwt的特性。因为他开销较小，并可以实现跨域。
information exchange(信息交换)：对于安全的在各方进行数据传输而言。jwt无疑是一种很好的方式。因为jwts可以被签名。例如，用公钥/私钥对。你可以确定发送人的身份。由于签名是使用头和有效负载计算的，还可以验证内容的篡改。

### jwt结构
jwt由三部份组成，之间用.隔开。
header.payload.signature

#### header头部
header典型的由两部份组成，然后用Base64对这个json进行编码就得到了jwt的header部份。
{
	"alg":"算法名称 HMAC SHA256或者RSA等等",
	"type":"token类型  JWT"
}


#### payload载荷
payload包含声明存放有效信息，是关于实体和其它数据的声明。声明有三种类型registered、public、private
* registered claims：标准中注册的声明，它不是强制的，但是推荐。比如：iss (issuer), exp (expiration time), sub (subject), aud (audience)等。
    iss: jwt签发者
    sub: jwt所面向的用户
    aud: 接收jwt的一方
    exp: jwt的过期时间，这个过期时间必须要大于签发时间
    nbf: 定义在什么时间之前，该jwt都是不可用的.
    iat: jwt的签发时间
    jti: jwt的唯一身份标识，主要用来作为一次性token,从而回避重放攻击。

* public：公共的声明是可以添加任何信息，一般添加用户的相关信息或其它业务需要的必要信息，但不建议添加敏感信息。

* private：私有的声明是提供者和消费者所共同定义的声明。一般不建议存放敏感信息，因为base64是对称解密的，意味着该部份信息可以视作明文信息。

#### signature
为了得到签名部份，必须有编码过的header和payload、一个秘钥，签名算法是header中指定的那个，然后对它们签名即可。
签名是用于验证消息在传递的过程有没有被篡改，并对于使用私钥签名的token它还可以验证jwt的发送方是否是原始发送方。
例如：
HMACSHA256(base64UrlEncode(header) + "." + base64UrlEncode(payload), secret)


### jwt实现过程
1. 用户提交用户名密码给服务端，如果登录成功则使用jwt创建一个token，并返回给用户。
2. 第一段字符串header，内部包含算法和token类型。json转化为字符串，然后做base64Url转码。
3. 第二段字符串payload自定义值，json转化为字符串，然后做base64url转码。由于只做了一层编码，所以不要存放敏感信息。
4. 第三段字符串signature，将前两部份密文拼接起来，对前两部份的密文进行hs256加密+加盐，然后对hs256加密后的密文再做base64url加密。
5. 客户端访问需要携带token，服务器端需要对token进行校验。
    对token进行切割解码，获取payload，检测token是否超时。把前两端拼接再执行hs256加密，把加密后的密文和第三段进行比较。


既然已经获取了token，为什么还要有jwt呢？token生成的其实是一个UUID，和业务没有任何关系，这样就带来最大的问题，就是需要人工持久化处理token，但jwt就不需要，因为jwt自身包含。它定义了一种紧凑且自包含的方式，用于在各方之间作为JSON对象安全地传输信息。由于此信息是经过数字签名的，因此可以被验证和信任。可以使用秘密（使用HMAC算法）或使用RSA或ECDSA的公钥/私钥对对JWT进行签名。



* JWTLoginFilter：认证过过滤器
* JWTAutherticationFilter：鉴权过滤器

#### 客户端配置信息(Client Details)
* clentId：客户端标识ID
* secret：客户端安全码
* scope：客户端访问范围，默认为空则表示全部范围。
* authorizedGrantTypes：客户端使用的授权类型。
* authorities：客户端可使用的权限。


#### 管理令牌(Managing Token)
* ResourceServerTokenService：接口定义了令牌加载，读取方法
* AuthorizationServerTokenService：接口定义了令牌的创建、获取、刷新方法
* ConsumerTokenService：定义令牌的撤销的方法
* DefaultTokenServices：实现了上述三个接口，它包含了一些令牌业务的实现。

TokenStore接口  
* InMemoryTokenStore：默认采用该实现，将令牌信息保存在内存中，易于调试。
* JdbcTokenStore：令牌保存在关系型数据库中，可以实不同服务器间的共享令牌。
* JwtTokenStore：使用jwt方式保存令牌，它不需要进行储，但是它撤销一个已授权令牌会非常困难，所以通常用来处理一个生命周期较短的令牌及撤销刷新令牌。


#### JWT令牌(JWT Tokens)
* 使用jwt令牌需要在授权服务器中使用jwtTokenStore，资源服务器也需要一个解码token令牌的类JwtAccessTokenConverter,JwtTokenStore依赖这个类进行编码及解码，因此授权服务及资源服务都需要这个转换类
* Token令牌默认是有签名，并且资源服务器中需要验证这个签名，因此需一个对称的key值，用来参与签名计算。
* 这个key值存在于授权服务和资源服务之中。或使用非对称加密算法加密token进行签名，public key公布在/oauth/token_key中。

* /oauth/authorize：授权端点
* /oauth/token：令牌端点
* /oauth/confirm_access：用户确认授权提交端点
* /oauth/error：授权服务错识信息端点
* /oauth/check_token：用于资源服务访问令牌解析端点
* /oauth/token_key：当使用jwt令牌，提供公有密钥的端点


#### 资源服务器
* 要访问资源服务器受保护的资源需要携带令牌
* 客户端往往同时也是一个资源服务器，各服务之间通信时需携带访问令牌
* 资源服务器通过@EnableResourceServer注解开启
* 通过继承ResourceServerConfigurerAdapter类来配置资源服务器


资源服务器通在启动类添加以下注解来设置领牌的解析方式
* @EnableAuthJWTTokenStore：使用JWT存储令牌
* @EnableDBClientDetailsService：通过数据库获取客户端详情
* @EnableDBTokenStore：通过数据库存储令牌


### springboot集成jwt


