### 实现ApplicationRunner接口

```
    import org.springframework.boot.ApplicationArguments;
    import org.springframework.boot.ApplicationRunner;
    import org.springframework.core.annotation.Order;
    import org.springframework.stereotype.Component;

    @Component
    public class ApplicationRunner implements ApplicationRunner {
        @Override
        public void run(ApplicationArguments args) throws Exception {
            System.out.println("xxxxxxxxxx");
        }
    }
```

### 实现CommandLineRunner接口
```
    import org.springframework.boot.CommandLineRunner;
    import org.springframework.stereotype.Component;

    @Component
    public class CommandLineRunner implements CommandLineRunner {
        @Override
        public void run(String... args) throws Exception {
            System.out.println("CommandLineRunner");
        }
    }

```