import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * TODO: 反射工具类
 * 定义工具方法使用反射创建对象，调用方法。好处：一次编写多次调用(一劳永逸)
 * @author caojie
 * @version 1.0
 * @date 2021/9/5 11:01
 */
public class ReflectUtils {
    /**
     * 定义静态工具方法，使用反射创建对象
     * 步骤：
     * 1 调用Class.forName(className)方法，获取Class对象。反射入口
     * 2 调用Class的getDeclaredConstructor方法获取构造器
     * 3 调用构造器的newInstance方法创建对象
     * @param className 以字符串表示的类名称，通常是包名+类名
     * @param <T> 该方法支持泛型
     * @return 泛型(不确定的类型)
     */
    public static <T> T createObject(String className) throws Exception {
        Class<?> clazz = Class.forName(className);
        Constructor<?> structor = clazz.getDeclaredConstructor();
        return (T)structor.newInstance();
    }

    /**
     * 使用反射创建带有参数对象
     * @param className 以字符串表示的类名称，通常是包名+类名
     * @param params 参数类型列表
     * @param values 参数实际值列表
     * @param <T> 方法支持泛型
     * @return 返回结果是泛型(不确定的类型)
     */
    public static <T> T createObject(String className,Class<?>[] params,Object... values) throws Exception{
        Class<?> clazz = Class.forName(className);
        Constructor<?> struct = clazz.getDeclaredConstructor(params);
        return (T)struct.newInstance(values);
    }

    /**
     *
     * 使用反射完成方法调用(无参)
     * 步骤：
     * 1 根据方法名称获取方法签名(信息)
     * 2 使用反射调用方法
     * @param  type 对象的实例
     * @param  methodName 方法名称
     * 备注：该方法支持两个泛型
     *  T 参数是一个泛型类型
     *  R 返回值是一个泛型
     */
    public static <T,R>  R invokeMethod(T type,String methodName) throws Exception {
        Class<?> clazz = type.getClass();
        // 根据方法名称获取方法签名
        Method method = clazz.getDeclaredMethod(methodName);
        return (R)method.invoke(type);
    }

    /**
     * 使用反射调用带有参数的方法
     * 步骤：
     * 1 获取Class对象
     * 2 根据方法名称获取方法签名
     * 3 调用Method的invoke方法，完成方法调用，并填写参数类型和参数名称
     * 4 返回方法调用的结果
     * @param obj 反射创建的对象
     * @param methodName 方法名称
     * @param params 参数类型列表
     * @param value 参数值列表
     * @param <T> 参数支持泛型
     * @param <R> 返回值支持泛型
     * @return 返回方法调用的结果
     */
    public static <T,R> R invokeMethod(T obj,String methodName,Class[] params,Object...value) throws Exception{
        Class<?> clazz = obj.getClass();
        Method method = clazz.getDeclaredMethod(methodName, params);
        // 编译错误：invoke方法返回Object类型，但是我定义的方法返回R，需要将返回类型强制转换为R类型
        // return method.invoke(type,value);
        return (R) method.invoke(obj,value);
    }

    /**
     * 使用反射访问私有属性(暴力破解)
     * 步骤：
     * 1 获取Class对象
     * 2 根据属性名称获取属性签名Field
     * 3 设置属性签名的可访问性为true
     * 4 调用set方法访问私有属性(为私有属性赋值)
     * @param obj 对象
     * @param fieldName 属性名称
     * @param value 属性值
     * @param <T> 参数支持泛型，此时表示参数类型不确定
     * @throws Exception
     */
    public static <T> void accessField(T obj,String fieldName,String value) throws Exception {
        Class<?> clazz = obj.getClass();
        Field stuNameField = clazz.getDeclaredField(fieldName);
        // 设置属性可访问性为true，就能够访问私有属性
        stuNameField.setAccessible(true);
        // 为私有属性赋值
        stuNameField.set(obj,value);
    }
}