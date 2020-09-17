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




### springboot集成jwt
