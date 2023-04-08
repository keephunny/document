请求实时监控码流Rtsp流媒体服务时，应在Url中指明请求的通道号、码流类型，如果需要认证信息，还有提供用户名和密码。

Url格式说明如下：

rtsp://username:password@ip:port/cam/realmonitor?channel=1&subtype=0

其中:

username: 用户名。例如admin。

password: 密码。例如admin。

ip: 为设备IP。例如 10.7.8.122。

port: 端口号默认为554，若为默认可不填写。

channel: 通道号，起始为1。例如通道2，则为channel=2。

subtype: 码流类型，主码流为0（即subtype=0），辅码流为1（即subtype=1）。

例如，请求某设备的通道2的辅码流，Url如下

rtsp://admin:admin@10.12.4.84:554/cam/realmonitor?channel=2&subtype=1

如果不需认证，则用户名和密码无需指定，使用如下格式即可：

rtsp://ip:port/cam/realmonitor?channel=1&subtype=0

0通道RTSP取流方法：

目前部分录像机支持0通道预览功能，可以RTSP取流0通道。格式如下：

rtsp://username:password@ip:port/cam/realmonitor?channel=（实际路数+1）&subtype=0

username: 用户名。例如admin。

password: 密码。例如admin。

ip: 为设备IP。例如 172.22.0.200。

port: 端口号默认为554，若为默认可不填写。

channel: 通道号，此处取流是0通道，在原来总路数上+1，例如32路设备，则填写33

subtype: 码流类型，主码流为0（即subtype=0），取流0通道时候用主码流。

例如：取流某64路设备的0通道流，格式如下:

rtsp://admin:admin123@172.22.0.32:554/cam/realmonitor?channel=65&subtype=0