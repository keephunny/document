* Callable 和 Future接口 
* Callable是类似于Runnable的接口，实现Callable接口的类和实现Runnable的类都是可被其它线程执行的任务。 
* Callable和Runnable有几点不同： 
* （1）Callable规定的方法是call()，而Runnable规定的方法是run(). 
* （2）Callable的任务执行后可返回值，而Runnable的任务是不能返回值的。 
* （3）call()方法可抛出异常，而run()方法是不能抛出异常的。 
* （4）运行Callable任务可拿到一个Future对象，Future 表示异步计算的结果。它提供了检查计算是否完成的方法，以等待计算的完成，并检索计算的结果。通过Future对象可了解任务执行情况，可取消任务的执行，还可获取任务执行的结果。 

http://bijian1013.iteye.com/blog/2281156