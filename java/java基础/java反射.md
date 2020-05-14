### 反射机制的相关类
* Class：代表类的实体，在运行的java应用程序中表示类和接口
* Field：类的成员变量
* Method： 类的方法
* Constructor：类的构造方法


```
public static void main(String[] args) throws Exception {

        //反射调用方法
        Class<?> classes = Apptest3.class;
        Object Apptest3 = classes.newInstance();
//        Method[] methods = classes.getMethods();
        Method[] methods = classes.getDeclaredMethods();


        Method method = classes.getMethod("test4");
        Object obj = method.invoke(Apptest3);
        List<UserInfoVo> list2 = converToList(obj);
        System.out.println(list2);
        System.out.println("");

        for (Method m : methods) {
            //System.out.println(m);
            if (m.getName().equals("test2")) {
                m.invoke(Apptest3, "-dfdsfsddsfsdfsdffd");
            }
            if ("test3".equals(m.getName())) {
                String str = (String) m.invoke(Apptest3);
                System.out.println(str);
            }
            if ("test4".equals(m.getName())) {
                Object object = m.invoke(Apptest3);
                List<UserInfoVo> list = converToList(object);

                System.out.println(list);
            }
        }
    }

    public static List<UserInfoVo> converToList(Object object) {
        List<UserInfoVo> result = new ArrayList<>();
        if (object instanceof ArrayList<?>) {
            for (Object o : (List<?>) object) {
                result.add(UserInfoVo.class.cast(o));
            }
        }
        return result;
    }
```