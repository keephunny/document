我们都知道一个对象只要实现了Serilizable接口，这个对象就可以被序列化，java的这种序列化模式为开发者提供了很多便利，我们可以不必关系具体序列化的过程，只要这个类实现了Serilizable接口，这个类的所有属性和方法都会自动序列化。  

然而在实际开发过程中，我们常常会遇到这样的问题，这个类的有些属性需要序列化，而其他属性不需要被序列化，打个比方，如果一个用户有一些敏感信息（如密码，银行卡号等），为了安全起见，不希望在网络操作（主要涉及到序列化操作，本地序列化缓存也适用）中被传输，这些信息对应的变量就可以加上transient关键字。换句话说，这个字段的生命周期仅存于调用者的内存中而不会写到磁盘里持久化。  

总之，java 的transient关键字为我们提供了便利，你只需要实现Serilizable接口，将不需要序列化的属性前添加关键字transient，序列化对象的时候，这个属性就不会序列化到指定的目的地中。

    package com.maven.project.test;

    import java.io.*;
    import java.util.ArrayList;
    import java.util.Iterator;
    import java.util.List;

    /**
    * description:
    *
    * @outhor wq
    * @create 2018-04-13 11:22
    */
    public class Test1 implements Serializable{
        private static final long serialVersionUID = 1L;

        private String id;
        private transient String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public static void main(String[] args) {
            Test1 test1=new Test1();
            test1.setId("id");
            test1.setName("name");

            System.out.println(test1.getId());
            System.out.println(test1.getName());

            try {
                ObjectOutputStream os=new ObjectOutputStream(new FileOutputStream("/Users/apple/Documents/test.txt"));
                os.writeObject(test1);
                os.flush();
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try{
                ObjectInputStream is=new ObjectInputStream(new FileInputStream("/Users/apple/Documents/test.txt"));
                Test1 test2=(Test1) is.readObject();
                is.close();
                System.out.println("序列化之后读取");
                System.out.println(test2.getId());
                System.out.println(test2.getName());
            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }
