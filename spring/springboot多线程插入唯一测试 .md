    @RequestMapping(value = "/insertTest", method = RequestMethod.GET)
    public ResponseDto insertTest() {
        ResponseDto responseDto = new ResponseDto(ResponseStatus.ERROR);


        ThreadInsert threadInsert = new ThreadInsert();

        Thread thread1 = new Thread(threadInsert);
        Thread thread2 = new Thread(threadInsert);
        thread1.start();
        thread2.start();
        return responseDto;
    }

    @Component("threadInsert")
    @Scope("prototype")
    public class ThreadInsert implements Runnable {
        @Override
        public void run() {
            System.out.println("run");

            for (int i = 0; i < 5; i++) {
                UserInfo userInfo = new UserInfo();
                userInfo.setUserName(UUID.randomUUID().toString().substring(0,12));
                userInfo.setUserPwd("11111");
                userInfo.setRoleId(1);
                userInfo.setCreateTime(new Date());
                userInfo.setCreateUser("admin");
                userInfo.setRealName("张三" + (i % 3));
                int n = userInfoService.insert(userInfo);
                logger.info("插入返回值：{}", n);
            }
        }
    }


    service方法
     @Override
    public synchronized int insert(UserInfo userInfo) {
        boolean flag=isExistsByUserName(userInfo.getRealName());
        if (flag) {
            System.out.println("用户名已经存在");
            return 0;
        }
        return userInfoMapper.insert(userInfo);
    }