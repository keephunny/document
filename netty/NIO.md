
#### DatagramSocket
DatagramSocket代表UDP协议的Socket，他本身只有码头，不维护状态，不产生IO流，它唯一的作用就是发送和接收数据报文。java使用DatagramPacket来代表数据报，发送和接收数据都是通过DatagramPacket对象完成的。

构造函数
* DatagramSocket()：创建一个DatagramSocket实例，并将该对象绑定到本机默认的IP和随机的某个端口。
* DatagramSocket(int port)：创建一个DatagramSocket实例，并将该对象绑定到本机默认的IP和指定端口。
* DatagramSocket(int port,InetAddress iaddr)：创建一个DatagramSocket实例，并将该对象绑定到指定IP和指定端口。

发送接收数据    
* receive(DatagramPacket p)：从该DatagramSocket中接收数据报。
* send(DatagramPacket p)：以该DatagramSocket对象向外发送数据报。
使用DatagramSocket发送数据报时，并不知道将该数据发送到哪里，而是由DatagramPacket自身决定数据报的目的地。

DatagramPacket构造器    
* DatagramPacket(byte[] buf,int length)：以一个空数组来创建DatagramPacket对象，该对象的作用是接收DattaGramSocket中的数据。
* DatagramPacket(byte[] buf,int length,InetAddress addr,int port)：以一个包含数据的数组来创建DatagramPacket对象，创建该对象的同时还指定了IP和端口。
* DatagramPacket(byte[] buf,int offset,int length)：以一个空数组来创建DatagramPacket对象，并指定接收到的数据放入buf数组中时从offset开始，最多放length字节。
* DatagramPacket(byte[] buf,int offset,int length,InetAddress address,int port)：创建一个用于发送DatagramPacket对象，指定发送buf数组中从offset开始，总共length个字节。

当client/server程序使用UDP协议时，实际上并没有明显的服务器端和客户端，因为两方都需要建立一个DatagramSocket对象，用来接收或发送数据，然后使用DatagramPacket对象作为传输数据的载体。通常固定IP和端口的DatagramSocket对象所在的程序称为服务器，因为该对象可以主动接收客户端数据。