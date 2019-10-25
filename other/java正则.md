https://blog.csdn.net/mynamepg/article/details/83110538
public class AppTest {
    public static void main(String[] args) {

        //System.out.println(Pattern.matches("^/api/\\S+$","http://api/aa.jpg"));
        System.out.println(Pattern.matches("\\S+", "a**"));
//        String[] regs = {
//                "/api/*",
//                "/api/**",
//                "/ap*",
//                "*.do"
//        };
//        //白名单
//        String[] urls = {
//                "http://api/aa.jpg",
//                "http://asdfdfs/aa.do",
//                "http://asdfdfs/api.jpg",
//                "http://asdfdfs/api",
//        };
//        String[] regsNew = new String[regs.length];
//        for (int i = 0; i < regsNew.length; i++) {
//
//            String str = regs[i];
//            str = str.replace("*", "\\S+");
//            String reg = "^" + str + "$";
//            regsNew[i] = reg;
//            System.out.println(reg);
//        }
//        System.out.println("");
//
//        for (String url : urls) {
//            for (String reg : regsNew) {
//                if (Pattern.matches(reg, url)) {
//                    System.out.println("通过" + url);
//                    break;
//                }
//            }
//        }

    }

    private void test1() {
        //静态资源
        String[] urls = {
                "http://asdfdfs/aa.jpg",
                "http://asdfdfs/aa.gif",
                "http://asdfdfs/aa.GIF",
                "http://asdfdfs/aa.css",
        };
//        Pattern p=Pattern.compile("\\*.jpg$");
//        (?i)abc 表示abc都忽略大小写
//        a(?i)bc 表示bc忽略大小写
//        a((?i)b)c 表示只有b忽略大小写
        String reg = "^\\S+(?i)(.jpg|.gif|.css|.js|.css)$";

        for (String url : urls) {
            System.out.println(Pattern.matches(reg, url));
        }
    }
}
