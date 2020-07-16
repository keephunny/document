proxy_set_header Host $http_host;
proxy_set_header X-Real-IP $remote_addr;
proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
proxy_set_header X-Forwarded-Proto $scheme;
各参数的含义如下所示。

Host包含客户端真实的域名和端口号；
X-Forwarded-Proto表示客户端真实的协议（http还是https）；
X-Real-IP表示客户端真实的IP；
X-Forwarded-For这个Header和X-Real-IP类似，但它在多层代理时会包含真实客户端及中间每个代理服务器的IP。



public static String getIp(HttpServletRequest request) {
    String ip = request.getHeader("x-forwarded-for");
    if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
        ip = request.getHeader("Proxy-Client-IP");
    }
    if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
        ip = request.getHeader("WL-Proxy-Client-IP");
    }
    if (StringUtils.isEmpty(ip) || "unknown".equalsIgnoreCase(ip)) {
        ip = request.getRemoteAddr();
        if ("127.0.0.1".equals(ip)) {
            //根据网卡取本机配置的IP
            InetAddress inet = null;
            try {
                inet = InetAddress.getLocalHost();
            } catch (Exception e) {
                LOGGER.error(e.getMessage(), e);
            }
            if (inet != null) {
                ip = inet.getHostAddress();
            }
        }
    }
    // 多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
    if (ip != null && ip.length() > 15) {
        if (ip.indexOf(",") > 0) {
            ip = ip.substring(0, ip.indexOf(","));
        }
    }
    return ip;
}
/***
 * 获取客户端IP地址;这里通过了Nginx获取;X-Real-IP
 */
public static String getClientIP(HttpServletRequest request) {
    String fromSource = "X-Real-IP";
    String ip = request.getHeader("X-Real-IP");
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
    	ip = request.getHeader("X-Forwarded-For");
    	fromSource = "X-Forwarded-For";
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
    	ip = request.getHeader("Proxy-Client-IP");
    	fromSource = "Proxy-Client-IP";
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
    	ip = request.getHeader("WL-Proxy-Client-IP");
    	fromSource = "WL-Proxy-Client-IP";
    }
    if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
    	ip = request.getRemoteAddr();
    	fromSource = "request.getRemoteAddr";
    }
    return ip;
}

Servlet API提供了request.getRemoteAddr()