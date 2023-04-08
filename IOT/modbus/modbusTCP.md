MODBUS/TCP是简单的、中立厂商的用于管理和控制自动化设备的MODBUS系列通讯协议的派生产品，显而易见，它覆盖了使用TCP/IP协议的“Intranet”和“Internet”环境中MODBUS报文的用途。协议的最通用用途是为诸如PLC’s，I/O模块，以及连接其它简单域总线或I/O模块的网关服务的。

modbus由MODICON公司于1979年开发，是一种工业现场总线协议标准。Modbus协议是一项应用层报文传输协议，包括ASCII、RTU、TCP三种报文类型。标准的Modbus协议物理层接口有RS232、RS422、RS485和以太网接口，采用master/slave方式通信。

MODBUS/TCP 使MODBUS_RTU协议运行于以太网，MODBUS TCP使用TCP/IP和以太网在站点间传送MODBUS报文，MODBUS TCP结合了以太网物理网络和网络标准TCP/IP以及以MODBUS作为应用协议标准的数据表示方法。MODBUS TCP通信报文被封装于以太网TCP/IP数据包中。与传统的串口方式，MODBUS TCP插入一个标准的MODBUS报文到TCP报文中，不再带有数据校验和地址。
缺省协议使用Port 502 通信端口

####  通讯所使用的以太网参考模型
Modbus TCP传输过程中使用了TCP/IP以太网参考模型的5层：
第一层：物理层，提供设备物理接口，与市售介质/网络适配器相兼容
第二层：数据链路层，格式化信号到源/目硬件址数据帧
第三层：网络层，实现带有32位IP址IP报文包
第四层：传输层，实现可靠性连接、传输、查错、重发、端口服务、传输调度
第五层：应用层，Modbus协议报文

#### Modbus TCP数据帧 
Modbus数据在TCP/IP以太网上传输，支持Ethernet II和802.3两种帧格式，Modbus TCP数据帧包含报文头、功能代码和数据3部分，MBAP报文头(MBAP、Modbus Application Protocol、Modbus应用协议)分4个域，共7个字节，