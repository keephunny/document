
Modbus是一种串行通信协议，是Modicon公司（现在的施耐德电气Schneider Electric）于1979年为使用可编程逻辑控制器（PLC）通信而发表。Modbus已经成为工业领域通信协议的业界标准（De facto），并且现在是工业电子设备之间常用的连接方式。
一般来说，ModbusRtu和ModbusAscii是运行在串口上的协议，ModbusTcp是运行是以太网上的协议，但是这并非绝对的，我们也可以将ModbusRtu、ModbusAscii运行在以太网或光纤上使用，同样的，在串口网络里，我们也可以使用ModbusTcp的协议，因为协议只是一种规范，并不限制通信介质。

1. ModbusRtu的报文格式如下：
    * 第一部分：从站地址，占1个字节
    * 第二部分：功能码，占1个字节
    * 第三部分：数据部分，占N个字节
    * 第四部分：校验部分，CRC校验，占2个字节

2. ModbusAscii的报文格式如下：
    * 第一部分：开始字符（:）
    * 第二部分：从站地址，占2个字节
    * 第三部分：功能码，占2个字节
    * 第四部分：数据部分，占N个字节
    * 第五部分：校验部分，LRC校验，占2个字节
    * 第六部分：结束字符（CR LF）

3. ModbusTcp的报文格式如下：
    * 第一部分：事务处理标识符，占2个字节
    * 第二部分：协议标识符，占2个字节
    * 第三部分：长度，占2个字节
    * 第四部分：单元标识符，占1个字节
    * 第五部分：功能码，占1个字节
    * 第六部分：数据部分，占N个字节

#### modbus模拟软件
* ModbusPoll 软件主要用于仿真 Modbus主站或 Modbus 客户端
* ModbusSlave 软件主要用于仿真 Modbus 从站或 Modbus 服务器
* VSPD 全称 Configure Virtual Serial Port Driver，是用来给电脑创建虚拟串口使用的。