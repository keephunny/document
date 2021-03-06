### cron表达式
cron表达式是一个字符串，字符串以5或6个空格隔开，分为6或7个域，每个域代表一个含义。
* 6域表达式
    seconds minutes hours dayOfMonth month dayOfWeek year
* 7域表达式
    seconds minutes hours dayOfMonth month dayOfWeek
#### 每个域可以出的字符如下
* seconds:0-59 　　　　, - * / 
* minutes:0-59　　　　 , - * / 
* hours:0-23 　　　　, - * / 
* dayOfMonth:1-31 　　　　, - * ? / L W C 
* month:1-12 　　　　, - * / 
* dayOfWeek:1-7 　　　　  , - * ? / L C # 
* year:1970-2099 　　, - * /

#### 域字符含义
* 数字：与该域对的数值时间
* *:表演示匹配该域任意值，如果在minutes配置*，则表示每分钟都会触发。
* ?:只能用在dayOfMonth和dayOfWeek两个域，匹配任意值。dayOfMonth和dayOftWeek会相互影响，例如每月20日触发 13 13 15 20 * ? 其中最后一位只能用?，而不能使用*，*表示不管星期几都会触发。
* -:表示范围，例如在minutes域使用5-20，表示从5分钟到20分钟每分钟都触发一次。
* /:表示起始时间开始触发，然后每隔固定时间触发一次，例如在minutes域使用5/20，则表示5分钟触发一次，而25,45等分别触发一次。
* ,:表示列出枚举值，如果在minutes配置5,20，则表示在5分和20分触发一次。
* L:表示最后，只能出现在dayOfWeek和dayOfMonth域，如果在dayOfWeek配置5L，则示每个月最后一个星期四触发。
* W:表示有效工作日(周一至周五)，只能出现在dayOfMonth域，用于指定日期的最近工作日，如果配置15W，则表演示这个月15号最近的工作日，如果15号是周六，则任会在14号触发。如果15号是周日，则任务会在周一16号触发。
* LW:可以联合使用，表示这个月最后一周的工作日。
* #:只允许出现在dayOfMonth域中，6#3表示某月的每三周的星期五。 4#2表示某月的第二个星期三。




### 例子
    每隔5秒执行一次："*/5 * * * * ?"
    每隔1分钟执行一次："0 */1 * * * ?"
    每天23点执行一次："0 0 23 * * ?"
    每天凌晨1点执行一次："0 0 1 * * ?"
    每月1号凌晨1点执行一次："0 0 1 1 * ?"
    每月最后一天23点执行一次："0 0 23 L * ?"
    每周星期天凌晨1点实行一次："0 0 1 ? * L"
    在26分、29分、33分执行一次："0 26,29,33 * * * ?"
    每天的0点、13点、18点、21点都执行一次："0 0 0,13,18,21 * * ?"
    表示在每月的1日的凌晨2点调度任务："0 0 2 1 * ? *"
    表示周一到周五每天上午10：15执行作业："0 15 10 ? * MON-FRI" 
    表示2002-2006年的每个月的最后一个星期五上午10:15执行："0 15 10 ? 6L 2002-2006"
    0 0 10,14,16 * * ? 每天上午10点，下午2点，4点
    0 0/30 9-17 * * ?   朝九晚五工作时间内每半小时
    0 0 12 ? * WED 表示每个星期三中午12点 
    "0 0 12 * * ?" 每天中午12点触发 
    "0 15 10 ? * *" 每天上午10:15触发 
    "0 15 10 * * ?" 每天上午10:15触发 
    "0 15 10 * * ? *" 每天上午10:15触发 
    "0 15 10 * * ? 2005" 2005年的每天上午10:15触发 
    "0 * 14 * * ?" 在每天下午2点到下午2:59期间的每1分钟触发 
    "0 0/5 14 * * ?" 在每天下午2点到下午2:55期间的每5分钟触发 
    "0 0/5 14,18 * * ?" 在每天下午2点到2:55期间和下午6点到6:55期间的每5分钟触发 
    "0 0-5 14 * * ?" 在每天下午2点到下午2:05期间的每1分钟触发 
    "0 10,44 14 ? 3 WED" 每年三月的星期三的下午2:10和2:44触发 
    "0 15 10 ? * MON-FRI" 周一至周五的上午10:15触发 
    "0 15 10 15 * ?" 每月15日上午10:15触发 
    "0 15 10 L * ?" 每月最后一日的上午10:15触发 
    "0 15 10 ? * 6L" 每月的最后一个星期五上午10:15触发 
    "0 15 10 ? * 6L 2002-2005" 2002年至2005年的每月的最后一个星期五上午10:15触发 
    "0 15 10 ? * 6#3" 每月的第三个星期五上午10:15触发
    
