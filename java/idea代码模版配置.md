### Class
```
    /*
    * Copyright (c) ${YEAR}.
    */
    #if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME};#end
    #parse("File Header.java")
    /**
    * //TODO 添加说明
    * @author ${USER}
    * 创建时间 ${YEAR}-${MONTH}-${DAY} ${TIME}
    */
    public class ${NAME} {
    }

```

### Interface
```
    /*
    * Copyright (c) ${YEAR}.
    */
    #if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME};#end
    #parse("File Header.java")
    /**
    * //TODO 添加说明
    * @author ${USER}
    * 创建时间 ${YEAR}-${MONTH}-${DAY} ${TIME}
    */
    public interface ${NAME} {
    }

```

### Enum
```
    /*
    * Copyright (c) ${YEAR}.
    */
    #if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME};#end
    #parse("File Header.java")
    /**
    * //TODO 添加说明
    * @author ${USER}
    * 创建时间 ${YEAR}-${MONTH}-${DAY} ${TIME}
    */
    public enum ${NAME} {
    }

```
### AnnotationType
```
    /*
    * Copyright (c) ${YEAR}.
    */
    #if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME};#end
    #parse("File Header.java")
    /**
    * //TODO 添加说明
    * @author ${USER}
    * 创建时间 ${YEAR}-${MONTH}-${DAY} ${TIME}
    */
    public @interface ${NAME} {
    }

```

### package-info
```
    /**
    * //TODO 添加说明
    * @author ${USER}
    * 创建时间 ${YEAR}-${MONTH}-${DAY} ${TIME}
    */
    #parse("File Header.java")
    #if (${PACKAGE_NAME} && ${PACKAGE_NAME} != "")package ${PACKAGE_NAME};#end
```

### module-info
```
    /**
    * //TODO 添加说明
    * @author ${USER}
    * 创建时间 ${YEAR}-${MONTH}-${DAY} ${TIME}
    */
    #parse("File Header.java")
    module #[[$MODULE_NAME$]]# {
    }
```