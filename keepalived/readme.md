Keepalived为linux系统提供了负载均均衡的高可用能力，负载均衡的能力来自于linux内核中的LVS项目模块IP Virtual Server。
keepalived 运行在 Linux 系统中，它会启动内核中的 LVS 服务来创建虚拟服务器。比如我们在两台服务器上都启动了一个 Keepalived 服务，然后 LVS 会虚拟化出来一个 IP（VIP），但是只有一个 Keepalived 会接管这个 VIP，就是说客户端的请求只会到 Master Keepalived 节点上。这样流量就只会到一台 keepalived 上了，然后 keepalived 可以配置几台真实的服务 IP 地址和端口，通过负载调度算法将流量分摊到这些服务上。对于另外一台 Backup Keepalived 节点。
#### LVS负载功能
* 基于DNS域名轮流解析
* 基于客户端调度访问方案
* 基于应用层系统
* 基于IP地址

而效率最高的是基于 IP 地址的调度方案。其实就是将请求转发给对应的 IP 地址 + 端口号，它的效率是非常高的，LVS 的 IP 负载均衡技术是通过 IPVS 模块来实现的，IPVS 是 LVS 集群系统的核心软件。

LVS 负载均衡器会虚拟化一个IP（VIP），对于客户端来说，它事先只知道这个 VIP 的，客户端就将请求发送给 VIP，然后 LVS 负载均衡器会将请求转发给后端服务器中的一个，这些服务器都称为 Real Server（真实服务器）。转发的规则是通过设置 LVS 的负载均衡算法来的，比如随机分配、按照权重分配等。

后端服务器的提供的功能要求是一致的，不论转发到哪台服务器，最终得到的结果是一致的，所以对于客户端来说，它并不关心有多少个后端服务器在提供服务，它只关心访问的 VIP 是多少。

那么后端服务处理完请求后，如何将数据返回给客户端呢？根据 LVS 的不同模式，会选择不同的方式将数据返回给客户端。LVS 的工作模式有三种：NAT 模式、TUN 模式、DR 模式。

#### VRRP协议
VRRP（Virtual Router Readundancy Protoco）虚拟冗余协议，它是一种容错协议，为了解决局域网中单点路由故障的问题。
* 虚拟路由器和虚拟IP
* master广播ARP报文
* backup选举新的master