## 什么是DI依赖注入
spring动态的向某个对象提供它所需要的其它对象，这个点是通过DI(dependency injection）)依赖注入实现。DI是如何实现的？java1.3之后有重要的特征是反射(reflection)，它允许程序在运行的时候动态生成对象，执行对象的方法，改变对象的属情。spring就是通过反射来实现注入的。简单的说依赖注入就是给属性赋值（包括基本数据类型和引用数据类型）

http://www.cnblogs.com/ysocean/p/7471451.html