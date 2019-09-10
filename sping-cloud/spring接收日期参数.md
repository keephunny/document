
### 全局配置转换格式
转换异常时，信息不能抛出
```
    @Configuration
    public class WebMvcConfig  implements WebMvcConfigurer {

        /**
        * 日期参数转换
        * @param registry
        */
        @Override
        public void addFormatters(FormatterRegistry registry) {
            registry.addConverter(new StringToDateConverter());
        }
    }


    public class StringToDateConverter implements Converter<String, Date> {
        @Override
        public Date convert(String date){
            Date target = null;
            if (!StringUtils.isEmpty(date)) {
                SimpleDateFormat format = new SimpleDateFormat(DatetimeFormat);
                try {
                    target = format.parse(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
            return target;
        }
    }
```
### 注解指定转换格式

```

    import org.springframework.format.annotation.DateTimeFormat;
    /**
     * 开始时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss" )
    private Date beginDate;
```

### 参数接收
对包装内型和基本类型接收异常数据时处理机制是不一样的。
Integer n：接收到abc时会处理为null
int n：接收到abc时会抛出异常

```
   @Size(min=2, max=30)  
    private String name;  
  
    @NumberFormat(pattern="#,###")  
    private Integer age;  
      
    @DateTimeFormat(pattern="yyyy/MM/dd")  
    private Date birthday;  
```