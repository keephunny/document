StringUtils方法的操作对象是java.lang.String类型的对象，是JDK提供的String类型操作方法的补充。并且是null安全的。
StrungUtils提供了130多个方法，并且都是static
### isEmpty()
判断字符串是否为空，null、str.length=0  
    StringUtils.isEmpty(null);true
    StringUtils.isEmpty(""); true
    StringUtils.isEmpty(" "); false
    StringUtils.isEmpty("b"); false
### isNotEmpty()
判断字符串是否非空，也isEmpty()相反
### isBlank()
判断字符串是否为空或长度为0或空白符构成
    StringUtils.isBlank(null);  true
    StringUtils.isBlank("");    true
    StringUtils.isBlank(" ");   true
    StringUtils.isBlank("\r\n");    true
    StringUtils.isBlank("\b");  false \b为单词边界符
    StringUtils.isBlank("b");   false
### isNotBlank() 
判断字符串是否非空，与isBlank()相反
### trim()
去掉字符串两端的控制符（char<=32)，如果输入null则返回null   
    StringUtils.trim(null) = null
    StringUtils.trim("") = ""
    StringUtils.trim(" ") = ""
    StringUtils.trim(" \b \t \n \f \r ") = ""
    StringUtils.trim(" \n\tss \b") = "ss"
    StringUtils.trim(" d d dd ") = "d d dd"
    StringUtils.trim("dd ") = "dd"
    StringUtils.trim(" dd ") = "dd"
### trimToNull()
去掉字符串两端的控制符(control characters, char <= 32),如果变为null或""，则返回null 
    StringUtils.trimToNull(null) = null
    StringUtils.trimToNull("") = null
    StringUtils.trimToNull(" ") = null
    StringUtils.trimToNull(" \b \t \n \f \r ") = null
    StringUtils.trimToNull(" \n\tss \b") = "ss"
    StringUtils.trimToNull(" d d dd ") = "d d dd"
    StringUtils.trimToNull("dd ") = "dd"
    StringUtils.trimToNull(" dd ") = "dd"
### trimToEmpty()
去掉字符串两端的控制符(control characters, char <= 32),如果变为null或""，则返回""   
    StringUtils.trimToEmpty(null) = ""
    StringUtils.trimToEmpty("") = ""
    StringUtils.trimToEmpty(" ") = ""
    StringUtils.trimToEmpty(" \b \t \n \f \r ") = ""
    StringUtils.trimToEmpty(" \n\tss \b") = "ss"
    StringUtils.trimToEmpty(" d d dd ") = "d d dd"
    StringUtils.trimToEmpty("dd ") = "dd"
    StringUtils.trimToEmpty(" dd ") = "dd"
### strip()
去掉字符串两端的空白符(whitespace)，如果输入为null则返回null
下面是示例(注意和trim()的区别)：        
    StringUtils.strip(null) = null
    StringUtils.strip("") = ""
    StringUtils.strip(" ") = ""
    StringUtils.strip(" \b \t \n \f \r ") = "\b"
    StringUtils.strip(" \n\tss \b") = "ss \b"
    StringUtils.strip(" d d dd ") = "d d dd"
    StringUtils.strip("dd ") = "dd"
    StringUtils.strip(" dd ") = "dd"
### stripToNull()
去掉字符串两端的空白符(whitespace)，如果变为null或""，则返回null        
    StringUtils.stripToNull(null) = null
    StringUtils.stripToNull("") = null
    StringUtils.stripToNull(" ") = null
    StringUtils.stripToNull(" \b \t \n \f \r ") = "\b"
    StringUtils.stripToNull(" \n\tss \b") = "ss \b"
    StringUtils.stripToNull(" d d dd ") = "d d dd"
    StringUtils.stripToNull("dd ") = "dd"
    StringUtils.stripToNull(" dd ") = "dd"
### stripToEmpty()
去掉字符串两端的空白符(whitespace)，如果变为null或""，则返回""  
    StringUtils.stripToNull(null) = ""
    StringUtils.stripToNull("") = ""
    StringUtils.stripToNull(" ") = ""
    StringUtils.stripToNull(" \b \t \n \f \r ") = "\b"
    StringUtils.stripToNull(" \n\tss \b") = "ss \b"
    StringUtils.stripToNull(" d d dd ") = "d d dd"
    StringUtils.stripToNull("dd ") = "dd"
    StringUtils.stripToNull(" dd ") = "dd"

### public static String strip(String str, String stripChars) 
   去掉 str 两端的在 stripChars 中的字符。
   如果 str 为 null 或等于"" ，则返回它本身；
   如果 stripChars 为 null 或"" ，则返回 strip(String str) 。

### public static String stripStart(String str, String stripChars) 
去掉 str 前端的在 stripChars 中的字符。

### public static String stripEnd(String str, String stripChars) 
去掉 str 末端的在 stripChars 中的字符。