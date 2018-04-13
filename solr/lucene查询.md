## 一.精确搜索
## 二.范围查询TermRangeQuery
    //最后两个参数表示起始和结尾是开区间还是闭区间
    Query query=new TermRangeQuery(field,start,end,true,true);
## 三.数字范围查询NumericRangeQuery
    Query query=new NumericRangeQuery.netIntRange(field,star,end,true,true);
## 四.前缀搜索PreFixQuery
    //value的值通过前缀区配
    Query query=new PreFixQuery(new Term(field,value));
## 五.通配符搜索WildcardQuery
    //value java*
    Query query=new WildcardQuery(new Term(field,value))
## 六.多条件查询BooleanQuery
    BooleanQuery query=new BooleanQuery();
    //查询表示名字为tom同时内容有like的索引
    //Occur.MUST参数表示条件必须有
    //MUST_NOT表示必须没有
    //SHOULD表示可有可无
    query.add(new TermQuery(new Term("name","tom")).Occur.MUST);
    query.add(new TermQuery(new Term("content","like")).Occur.MUST);
## 七.短语查询PhraseQuery
    PhraseQuery query=new PhraseQuery();
    //比如I like football，我们查询I football则表示中间有一跳（一空），参数值就表示跳数
    query.setSlop(1);
    query.add(new Term("content","i));
    query.add(new Term("content","football"));
## 八.模糊查询FuzzyQuery
    //这里我们看到名字虽然写错了，但是能够查出来，默认可以匹配一个字符出错的情况，这里设置匹配力（相似度）0.5<=1，距离为2
    FuzzyQuery query=new FuzzyQuery(new Term("name","tome"),0.5f,2);
进行查询，模糊查询和通配符查询不一样，可以理解为容错查询，如果不给出后面两个参数，则默认可以允许一个字符出错，当然这可以使用后面两个参数进行设定。倒数第二个参数是匹配力，此值小于等于1.0，默认为1.0。值越小匹配越小，最后一个参数表示可以出现错误的字符个数。此方法用的不多，了解即可。
## 九.QueryParser
    //创建QueryParser对象，默认搜索域为content
    QueryParser parser=new QueryParser(Version.LUCENE_35, "content", new StandardAnalyzer(Version.LUCENE_35));
    //改变默认空格操作符，但是分开写还是或操作符
    parser.setDefaultOperator(Operator.AND);
    //开启前缀为通配符查询，默认关闭，效率较低
    parser.setAllowLeadingWildcard(true);
    
    //搜索content中包含like的
    //空格和分开默认表示或
    Query query = parser.parse("like");
    //这就是要查有I或者football的
    query = parser.parse("I football");
    //这就是要查有I同时有football的
    query = parser.parse("I AND football");
    
    //改变搜索域为name为tom的
    query = parser.parse("name:like");
    query = parser.parse("name:j*");
    //这种效率较低，默认是关闭的，但是可以打开
    query = parser.parse("email:*@sina.com");
    //这里表示不能有like而必须有football，中间要有空格，加减要放在条件前面
    query = parser.parse("- name:tom + football");
    //注意是闭区间，TO必须是大写
    query = parser.parse("id:[1 TO 3]");
    //注意是开区间，不能是半开半闭
    query = parser.parse("id:{1 TO 3}");
    //完全匹配"I like football"
    query = parser.parse("\"I like football\"");
    //表示I和football之间有一个空白单词的
    query = parser.parse("\"I football\"~1");
    
## 十.分页搜索