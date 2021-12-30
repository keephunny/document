```
ClassPathResource resource = new ClassPathResource("application.yml");
try {
	String bastPath = resource.getFile().getPath();
	String content = StreamUtils.copyToString(resource.getInputStream(), StandardCharsets.UTF_8);
	logger.warn("-- -- -- -- content:{}", content.replace(" ", "").replace("\n", ""));
	logger.warn("-- -- -- -- bastPath:{}", bastPath);
} catch (IOException e) {
	logger.error("", e);
}


ClassPathResource resource = new ClassPathResource("bin");
也支持目录

开发：resource/application.yml
生产：con/application.yml

start.sh
        CLASSPATH="$base/conf:$CLASSPATH";
        CLASSPATH="$base/libs/*:$CLASSPATH";
        echo "CLASSPATH"$CLASSPATH
java $JAVA_OPTS  -classpath .:$CLASSPATH 
```







```
老方法
basePath = System.getProperty("user.dir") + File.separator + ".." + File.separator + "conf" + File.separator + "aep2id";
logger.info("user.dir={}", basePath);

File file = new File(basePath);
if (!file.exists()) {
    try {
        basePath = ResourceUtils.getFile("classpath:conf/aep2id").getPath();
    } catch (FileNotFoundException e) {
        logger.error("", e);
    }
}
```

