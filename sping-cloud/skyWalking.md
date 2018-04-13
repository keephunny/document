Skywalking 是一个APM系统，即应用性能监控系统，为微服务架构和云原生架构系统设计。它通过探针自动收集所需的指标，并进行分布式追踪。通过这些调用链路以及指标，Skywalking APM会感知应用间关系和服务间关系，并进行相应的指标统计。目前支持链路追踪和监控应用组件如下，基本涵盖主流框架和容器，如国产PRC Dubbo和motan等，国际化的spring boot，spring cloud都支持了  

APM = Application Performance Management，应用性能管理，对企业系统即时监控以实现对应用程序性能管理和故障管理的系统化的解决方案。应用性能管理是一个比较新的网络管理方向，主要指对企业的关键业务应用进行监测、优化，提高企业应用的可靠性和质量，保证用户得到良好的服务，降低IT总拥有成本(TCO)。一个企业的关键业务应用的性能强大，可以提高竞争力，并取得商业成功，因此，加强应用性能管理（APM）可以产生巨大商业利益。APM的覆盖范围包括五个层次的实现：终端用户体验，应用架构映射，应用事务的分析，深度应用诊断，和数据分析。  

## skywalaking总体架构
* skywalking-collector：链路数据归集器，数据可以落地ElasticSearch，单机也可以落地H2，不推荐，H2仅作为临时演示用
* skywalking-web：web可视化平台，用来展示落地的数据
* skywalking-agent：探针，用来收集和发送数据到归集器

## skywalaking 下载地址
官方地址  https://github.com/OpenSkywalking/skywalking/releases  
分别下载skywalking-collector.zip，skywalking-web.zip，skywalking-agent.zip 


https://blog.csdn.net/qq_32792363/article/details/79501167
https://blog.csdn.net/zhangkang65/article/details/78991760