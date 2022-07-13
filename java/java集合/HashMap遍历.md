HashMap遍历可以分为以下4类
* 迭代器(Iterator)
* Foreach遍历
* Lambda表达式
* Streams API

#### 迭代器 EntrySet
```
    Iterator<Map.Entry<String,String>> iterator=map.entrySet().iterator();
    while(iterator.hashNext()){
        Map.Entry<String,String> entry=iterator.next();
        entry.getKey();
        entry.getValue();
    }
```

#### 迭代器 KeySet
```
    Iterator<String> iterator=map.keySet().iterator();
    while(iterator.hashNext()){
        String key=iterator.next();
        String value=map.get(key);
    }
```

#### ForEach EntrySet
```
    for(Map.Entry<String,String> entry:map.entrySet()){
        entry.getKey();
        entry.getValue();
    }
```

#### ForEach KeySet
```
    for(String key:map.keySet()){
        key;
        map.get(key);
    }
```

#### Lambda表达式
```
    map.forEach(key,value)->{
        key;
        value;
    }

```

#### Streams API
```
    map.entrySet().stream().forEach((entry)->{
        entry.getKey();
        entry.getValue();
    });

    //多线程
    map.entrySet().parallelStream().forEach();
```