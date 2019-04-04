## 监听器 listener
监听器是是servlet规范中定义的一种特殊类，用于监听servletContext、HttpSession和servletRequest等域对象的创建和销毁事件，监听域对象的属性发生修改的事件，用于在事件发生前、发生后做一些必要的处理。常用于统计在线人数、系统启动初始化、统计访问量、记录用户方问路径。

    package com.example.demo;
    import javax.servlet.http.HttpSessionEvent;
    import javax.servlet.http.HttpSessionListener;
    public class MyHttpSessionListener implements HttpSessionListener {
        public static int online = 0;
        @Override
        public void sessionCreated(HttpSessionEvent se) {
            System.out.println("创建session");
            online ++;
        }

        @Override
        public void sessionDestroyed(HttpSessionEvent se) {
            System.out.println("销毁session");
            
        }
    }

## 过滤器 filter
滤器是servlet技术中最实用的技术，通过filter技术对web服务器的所有web资源例如jsp、servlet、静态资源进行拦载，从而实现一些特殊的功能。例如访问权限，过虑敏感词，压缩响应信息。主要用于对用户请求进行预处理，也可以对httpServletResponse进行后处理。filter对用户请求进行预处理，接着将请求交给servlet进行处理并生成响应。最后filter再对服务器进行后处理。

    public class MyFilter implements Filter {  
        @Override  
        public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {  
            System.out.println(servletRequest.getParameter("name"));
            HttpServletRequest hrequest = (HttpServletRequest)servletRequest;
            HttpServletResponseWrapper wrapper = new HttpServletResponseWrapper((HttpServletResponse) servletResponse);
            if(hrequest.getRequestURI().indexOf("/index") != -1 || 
                    hrequest.getRequestURI().indexOf("/asd") != -1 ||
                    hrequest.getRequestURI().indexOf("/online") != -1 ||
                    hrequest.getRequestURI().indexOf("/login") != -1
                    ) {
                filterChain.doFilter(servletRequest, servletResponse);  
            }else {
                wrapper.sendRedirect("/login");
            }
        }  
        @Override  
        public void destroy() {  
        }
        @Override
        public void init(FilterConfig filterConfig) throws ServletException {
        }    
    } 

## 拦载器 interceptor
拦截器在AOP中用于在某个方法或字段被访问之前，进行拦载后在之前或之后加入某些操作，比如日志，安全等。一般拦截器方法都是通过动态代理的方式实现，可以通过它进行权限验证。或者判断用户是否登录。

    import java.io.PrintWriter;
    import java.util.Map;
    import javax.servlet.http.HttpServletRequest;
    import javax.servlet.http.HttpServletResponse;
    import org.springframework.web.servlet.HandlerInterceptor;
    import org.springframework.web.servlet.HandlerMapping;
    import org.springframework.web.servlet.ModelAndView;

    public class MyInterceptor implements HandlerInterceptor {
        //在请求处理之前进行调用（Controller方法调用之前
        @Override
        public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
            System.out.println("preHandle被调用");
            Map map =(Map)httpServletRequest.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
            System.out.println(map.get("name"));
            System.out.println(httpServletRequest.getParameter("username"));
            if(map.get("name").equals("zhangsan")) {
                return true;    //如果false，停止流程，api被拦截
            }else {
                PrintWriter printWriter = httpServletResponse.getWriter();    
                printWriter.write("please login again!");    
                return false; 
            }
        }
        @Override
        public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
            System.out.println("postHandle被调用");
        }

        @Override
        public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
            System.out.println("afterCompletion被调用");
        }
    }

## springboot配置类

    import org.springframework.boot.web.servlet.FilterRegistrationBean;
    import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
    import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
    import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

    @Configuration
    public class MywebConfig implements WebMvcConfigurer {

        @Override
        public void addViewControllers(ViewControllerRegistry registry) {
            registry.addViewController("/zxc/foo").setViewName("foo");
        }
        @Override
        public void addInterceptors(InterceptorRegistry registry) {
            registry.addInterceptor(new MyInterceptor()) 
                    .addPathPatterns("/asd/**"); 
        }
        @SuppressWarnings({ "rawtypes", "unchecked" })
        @Bean
        public FilterRegistrationBean filterRegist() {
            FilterRegistrationBean frBean = new FilterRegistrationBean();
            frBean.setFilter(new MyFilter());
            frBean.addUrlPatterns("/*");
            System.out.println("filter");
            return frBean;
        }
        @SuppressWarnings({ "rawtypes", "unchecked" })
        @Bean
        public ServletListenerRegistrationBean listenerRegist() {
            ServletListenerRegistrationBean srb = new ServletListenerRegistrationBean();
            srb.setListener(new MyHttpSessionListener());
            System.out.println("listener");
            return srb;
        }
    }





1.后端创建cookie
    Cookie cookName =newCookie("cookName", cookValue);
    cookName.setMaxAge(60*60*24*7);//设置cookie的最大生命周期为7天
    cookName.setPath("/"); //设置路径为全路径（这样写的好处是同项目下的页面都能访问该cookie
    response.addCookie(cookName); //response是HttpServletResponse类型
2.后端创建session
    HttpSession session = request.getSession(); //request是HttpServletRequest类型的
    session.setAttribute("manuse", manuse);//往session中添加字段
    session.setAttribute("rank", rank);
3.后端获取cookie并干掉cookie
    @CookieValue（"cookieName"）Cookie cookieName//@CookieValue是spring提供的注解,用在参数里
    userCookie.setMaxAge(0);
    userCookie.setPath("/"); //路径一定要写上，不然干不掉的
    response.addCookie(userCookie);
4.后端获取session并干掉session
    HttpSession session = request.getSession();
    session.invalidate(); 