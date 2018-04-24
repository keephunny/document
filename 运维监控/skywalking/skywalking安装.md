当企业应用进入分布式微服务时代，应用服务依赖会越来越多，skywalking可以很好的解决服务调用链路追踪问题，而且基于java控针技术。基本对应用零入侵零耦合。  
skywalking是一个应用性能监控系统（APM）,为微服务架构和云原生架构系统设计。它通过控针自动收集所需指标。并进行分布式追踪。通过这些高用链路以及指标，skywalking APM会感知应间关系和服务间关系，进行相应的指标统计。目前支持链路追踪和监控组件。基本涵盖主流框架和容器，如国产PRC Dubbo和motan等，国际化的spring boot，spring cloud都支持了  
APM = Application Performance Management，应用性能管理，对企业系统即时监控以实现对应用程序性能管理和故障管理的系统化的解决方案。应用性能管理是一个比较新的网络管理方向，主要指对企业的关键业务应用进行监测、优化，提高企业应用的可靠性和质量，保证用户得到良好的服务，降低IT总拥有成本(TCO)。一个企业的关键业务应用的性能强大，可以提高竞争力，并取得商业成功，因此，加强应用性能管理（APM）可以产生巨大商业利益。APM的覆盖范围包括五个层次的实现：终端用户体验，应用架构映射，应用事务的分析，深度应用诊断，和数据分析。    
## skywalaking总体架构
* skywalking-collector：链路数据归集器，数据可以落地ElasticSearch，单机也可以落地H2，不推荐，H2仅作为临时演示用
* skywalking-web：web可视化平台，用来展示落地的数据
* skywalking-agent：探针，用来收集和发送数据到归集器

## skywalaking 下载地址
官方地址  https://github.com/OpenSkywalking/skywalking/releases  
分别下载skywalking-collector.zip，skywalking-web.zip，skywalking-agent.zip 
v3.2.6


安装elsearch
wget https://artifacts.elastic.co/downloads/elasticsearch/elasticsearch-5.6.8.tar.gz
groupadd elsearch
useradd elsearch -g elsearch
chown -R elsearch:elsearch elasticsearch-5.6.8
更改该文件夹下所属的用户组的权限

错误：bound or publishing to a non-loopback address,
ulimit -n 65536
https://blog.csdn.net/yiifaa/article/details/70318154

elasticsearch安装方法
https://blog.csdn.net/sinat_28224453/article/details/51134978