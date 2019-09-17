Shiro缓存使用Redis、Ehcache、自带的MpCache实现的三种方式实例
https://www.cnblogs.com/zfding/p/8536480.html

Spring Boot Shiro中使用缓存
https://mrbird.cc/Spring-Boot-Shiro%20cache.html

shiro-ehcache.xml：
<?xml version="1.0" encoding="UTF-8"?>
<ehcache xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:noNamespaceSchemaLocation="http://ehcache.org/ehcache.xsd"
    updateCheck="false">
    <diskStore path="java.io.tmpdir/Tmp_EhCache" />
    <defaultCache
        maxElementsInMemory="10000"
        eternal="false"
        timeToIdleSeconds="120"
        timeToLiveSeconds="120"
        overflowToDisk="false"
        diskPersistent="false"
        diskExpiryThreadIntervalSeconds="120" />
    
    <!-- 登录记录缓存锁定1小时 -->
    <cache 
        name="passwordRetryCache"
        maxEntriesLocalHeap="2000"
        eternal="false"
        timeToIdleSeconds="3600"
        timeToLiveSeconds="0"
        overflowToDisk="false"
        statistics="true" />
</ehcache>


* name：缓存名称
* maxElementsInMemory：缓存最大个数。
* eternal：对象是否永久有效，一但设置了timeout将不起作用。
* timeToIdleSeconds：设置对象在失效前的允许闲置时间(秒)。仅当eternal=false对象不是永久有效时使用，可选属性，默认值是0，也就是可闲置时间无穷大。
* timeToLiveSeconds：设置对象在失效前允许存活时间（单位：秒）。最大时间介于创建时间和失效时间之间。仅当eternal=false对象不是永久有效时使用，默认是0.，也就是对象存活时间无穷大。
* overflowToDisk：当内存中对象数量达到maxElementsInMemory时，Ehcache将会对象写到磁盘中。
* diskSpoolBufferSizeMB：这个参数设置DiskStore（磁盘缓存）的缓存区大小。默认是30MB。每个Cache都应该有自己的一个缓冲区。
* maxElementsOnDisk：硬盘最大缓存个数。
* diskPersistent：是否缓存虚拟机重启期数据 Whether the disk store persists between restarts of the Virtual Machine. The default value is false.
* diskExpiryThreadIntervalSeconds：磁盘失效线程运行时间间隔，默认是120秒。
* memoryStoreEvictionPolicy：当达到maxElementsInMemory限制时，Ehcache将会根据指定的策略去清理内存。默认策略是LRU（最近最少使用）。你可以设置为FIFO（先进先出）或是LFU（较少使用）。
* clearOnFlush：内存数量最大时是否清除。



ShiroConfig
@Bean
public EhCacheManager getEhCacheManager() {
    EhCacheManager em = new EhCacheManager();
    em.setCacheManagerConfigFile("classpath:config/shiro-ehcache.xml");
    return em;
}

@Bean  
public SecurityManager securityManager(){  
    DefaultWebSecurityManager securityManager =  new DefaultWebSecurityManager();
    securityManager.setRealm(shiroRealm());
    securityManager.setRememberMeManager(rememberMeManager());
    securityManager.setCacheManager(getEhCacheManager());
    return securityManager;  
}




<?xml version="1.0" encoding="UTF-8"?>
<ehcache updateCheck="false" dynamicConfig="false">
 <diskStore path="java.io.tmpdir"/>
 <!--授权信息缓存-->
 <cache name="authorizationCache"
		maxEntriesLocalHeap="2000"
		eternal="false"
		timeToIdleSeconds="1800"
		timeToLiveSeconds="1800"
		overflowToDisk="false"
		statistics="true">
 </cache>
<!--身份信息缓存-->
 <cache name="authenticationCache"
		maxEntriesLocalHeap="2000"
		eternal="false"
		timeToIdleSeconds="1800"
		timeToLiveSeconds="1800"
		overflowToDisk="false"
		statistics="true">
 </cache>
<!--session缓存-->
 <cache name="activeSessionCache"
		maxEntriesLocalHeap="2000"
		eternal="false"
		timeToIdleSeconds="1800"
		timeToLiveSeconds="1800"
		overflowToDisk="false"
		statistics="true">
 </cache>

 <!-- 缓存半小时 -->
 <cache name="halfHour"
		maxElementsInMemory="10000"
		maxElementsOnDisk="100000"
		eternal="false"
		timeToIdleSeconds="1800"
		timeToLiveSeconds="1800"
		overflowToDisk="false"
		diskPersistent="false" />

 <!-- 缓存一小时 -->
 <cache name="hour"
		maxElementsInMemory="10000"
		maxElementsOnDisk="100000"
		eternal="false"
		timeToIdleSeconds="3600"
		timeToLiveSeconds="3600"
		overflowToDisk="false"
		diskPersistent="false" />

 <!-- 缓存一天 -->
 <cache name="oneDay"
		maxElementsInMemory="10000"
		maxElementsOnDisk="100000"
		eternal="false"
		timeToIdleSeconds="86400"
		timeToLiveSeconds="86400"
		overflowToDisk="false"
		diskPersistent="false" />

 <defaultCache name="defaultCache"
			   maxElementsInMemory="10000"
			   eternal="false"
			   timeToIdleSeconds="600"
			   timeToLiveSeconds="600"
			   overflowToDisk="false"
			   maxElementsOnDisk="100000"
			   diskPersistent="false"
			   diskExpiryThreadIntervalSeconds="120"
			   memoryStoreEvictionPolicy="LRU"/>

</ehcache>


缓冲清空:
当用户权限修改后，用户再次登陆shiro会自动调用realm从数据库获取权限数据，如果在修改权限后想立即清除缓存则可以调用realm的clearCache方法清除缓存。
realm中定义clearCached方法：
// 清除缓存
public void clearCached() {
    PrincipalCollection principals = SecurityUtils.getSubject()
            .getPrincipals();
    super.clearCache(principals);
}


