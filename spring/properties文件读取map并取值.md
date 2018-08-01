    //读取properties内容并转换成map形式
    public class PropertiesReader {
    public Map<String, String> getProperties(){
        Properties props = new Properties();
        Map<String, String> map=new HashMap<String, String>();
        try {
            InputStream in= getClass().getResourceAsStream("Type.properties");
            props.load(in);
            Enumeration en=props.propertyNames();
            while (en.hasMoreElements()) {
                String key=(String) en.nextElement();
                String property=props.getProperty(key);
                map.put(key, property);
                System.out.println(key + "."+property);
            }
        } catch (Exception e) {
            // TODO: handle exception
        }
        return map;
    }
    }
