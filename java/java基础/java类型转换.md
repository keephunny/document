

### object转list

```


    List<UserInfoVo> list = converToList(object);

    public static List<UserInfoVo> converToList(Object object) {
        List<UserInfoVo> result = new ArrayList<>();
        if (object instanceof ArrayList<?>) {
            for (Object o : (List<?>) object) {
                result.add(UserInfoVo.class.cast(o));
            }
        }
        return result;
    }
```