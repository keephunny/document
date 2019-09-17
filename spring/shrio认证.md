### shiro认证流程
```
    //1.构建securityManager
    DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
    defaultSecurityManager.setRealm(simpleAccountRealm);

    // 2.主体提交认证请求
    SecurityUtils.setSecurityManager(defaultSecurityManager);
    Subject subject = SecurityUtils.getSubject();

    // 3.认证
    UsernamePasswordToken token = new UsernamePasswordToken("admin", "admin");
    subject.login(token);
    System.out.println("是否认证：" + subject.isAuthenticated());

    // 4.退出
    subject.logout();
    System.out.println("是否认证：" + subject.isAuthenticated());
```