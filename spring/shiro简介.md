


### 获取当前登录用户信息
```
    //controller

    //设置用户信息
    UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(userInfo.getUserName(), userInfo.getUserPwd());
    Subject subject = SecurityUtils.getSubject();

    Session session = subject.getSession();
    subject.getSession().setAttribute("userInfo", userInfo);

    //获取用户信息
    UserInfo user = (UserInfo) SecurityUtils.getSubject().getSession().getAttribute("userInfo");
    System.out.println("当前登录用户信息：");
```