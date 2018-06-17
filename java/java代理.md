代理可以隐藏委托类的实现。  
代理可以实现客户与委托类间的解耦，在不修改委托类代码的情况下能够做一些额外的处理。  
## 静太代理
或代理类在程序运行前就已经存在。那么这种代理方式被称为静态代理。这种情况下的代理类通常是我们用java代码定义的。通常情况下，静态代码类与委托类会实现同一接口或派生自相同的父类。

    public interface Sell {
        void sell();
        void ad();
    }

    public class Vendor implements Sell {
        public void sell() {
            System.out.println("In sell method");
        }
        public void ad() {
            System.out.println("ad method")
        }
    }

    //代理类
    public class BusinessAgent implements Sell {
        private Vendor mVendor;

        public BusinessAgent(Vendor vendor) {
            mVendor = vendor;
        }
        public void sell() { mVendor.sell(); }
        public void ad() { mVendor.ad(); }
    }
从上面的代理类定义我们可以了解到，静态代理可以通过聚合实现。让代理类持有一个委托类的引用即可。通过静态代理，我们无需修改Vendor类的代码就可以实现，只需在BusinessAgent类中的sell方法中。可以实现客户与委托类间的解耦，在不修改委托类代码的情况下能够做一些额外的处理。静态代理的局限在于运行前必须编写好代理类，下面我们重点来介绍下运行时生成代理类的动态代理方式。

## 动态代理
代理类在程序运行时创建的代理方式被称为动态代理，在这种情况下，代理类并不是在java代码中定义的。而是运行时候根据我们在java代码中的指示动态生成的。相比于静态代理，动态代理的优势在于可以很方便的对代理类的函数进行统一的处理，而不用修改每个代理类的函数。  
假设我们在执行委托类中的方法之前输出before在执行完之后输出after。先看下静态代理。
    public class BusinessAgent implements Sell {
        private Vendor mVendor;
    
        public BusinessAgent(Vendor vendor) {
            this.mVendor = vendor;
        }
    
        public void sell() {
            System.out.println("before");
            mVendor.sell();
            System.out.println("after");
        }
    
        public void ad() {
            System.out.println("before");
            mVendor.ad();
            System.out.println("after");
        }
    }
## 使用动态代理
### InvocationHandler接口
在使用动态代理时，我们需要定一个位于代理类与委托类之间的中介类。这个中介类被要求实现InvocationHandler接口。
    public interface InvocationHandler{
        Object invoke(Object proxy,Method method,Object[] args);
    }
代理类对象作为proxy参数传入，参数的method标识了我们具体的调用是代理类的哪个方法。args是参数。这样我对代理类中的所有方法的调用都会变成对invoke的调用，这样就可以在invoke方法中统一添加处理逻辑。也可以根据method参数对不同的代理类方法做不同的处理。

### 委托类的定义
动态代理方式下，要求委托类必须实现某个接口。   
    public class Vendor implements Sell {
        public void sell() {
            System.out.println("In sell method");
        }
        public void ad() {
            System,out.println("ad method")
        }
    }
### 中介类
中介类必须实现InvocationHandler接口。作为调用处理器拦截对代理方法的调用。   
    public class DynamicProxy implements InvocationHandler{
        //obj为委托对象
        private Object obj;
        public DynamicProxy(Object obj){
            this.obj=obj;
        }
        @Override
        public Object invoke(Object proxy,Method method,Object[] args)throws Throwable{
            System.out.println("before");
            Object result=method.
        }
    }
中介类持有一个委托对象引用，在invoke方法中调用了委托对象的相应方法.代理类与中介类构成一个静态代理关系，在这个关系中，中介类是委托类，代理类是代理类。动态代理关系由两组静态代理关系组成。  
### 动态生成代理类
public static void main(String[] args){
    //创建中介类实例
    DynamicProxy inter=new DynamicProxy(new Vendor());
    //产生一个$Proxy0.class，这个文件即为动态生成的代理文件
    System.getProperties().put("xxx.xx.Class","true);
    //获取代理类实例sell
    Sell sell = (Sell)(Proxy.newProxyInstance(Sell.class.getClassLoader(),new Class[] (Sell.class),inter);

    //通过代理类对象调用代理类方法，实际上会转到invoke方法调用
    sell.sell();
    sell.ad();
}

http://www.cnblogs.com/guxiong/p/6707193.html

