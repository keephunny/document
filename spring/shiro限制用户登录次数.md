

    //把用户名和密码封装为 UsernamePasswordToken 对象
    //记住用户名
    boolean rememberMe = true;
    UsernamePasswordToken token = new UsernamePasswordToken(name, password,rememberMe);

    try {
        // 执行登录.
        currentUser.login(token);
        msg = "登录成功";
    } catch (IncorrectCredentialsException e) {
        msg = "登录密码错误";
        System.out.println("登录密码错误!!!" + e);
    } catch (ExcessiveAttemptsException e) {
        msg = "登录失败次数过多，请5分钟后再登录!";
        System.out.println("登录失败次数过多!!!" + e);
    } catch (LockedAccountException e) {
        msg = "帐号已被锁定";
        System.out.println("帐号已被锁定!!!" + e);
    } catch (DisabledAccountException e) {
        msg = "帐号已被禁用";
        System.out.println("帐号已被禁用!!!" + e);
    } catch (ExpiredCredentialsException e) {
        msg = "帐号已过期";
        System.out.println("帐号已过期!!!" + e);
    } catch (UnknownAccountException e) {
        msg = "帐号不存在";
        System.out.println("帐号不存在!!!" + e);
    } catch (UnauthorizedException e) {
        msg = "您没有得到相应的授权！";
        System.out.println("您没有得到相应的授权！" + e);
    } catch (Exception e) {
        System.out.println("出错！！！" + e);
    }