
* pileline：从源端到目标端的整个过程描述，主要由一些同步映射过程组成。
* channel：同步通道，单向同步中由一个pipeline组成，在双向同步中有两个pipeline组成。
* DataMediaPair：根据业务表定义映射关系，比如源表和目标表演，字段映射，字段组等。
* DataMedia：抽象的数据介质概念，可以理解为数据表演/mq队列定义。
* DataMediaSource：抽象的数据介质源信息，补充