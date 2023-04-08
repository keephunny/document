* FixedLengthFrameDecoder：固定长度解码器
* LineBaseFrameDecoder：行分割解码器
* DelimiterBasedFrameDecoder：自定义分隔符解码器
* LengthFieldBasedFrameDecoder：自定义长度解码器
    ```
    public LengthFieldBasedFrameDecoder(
        int maxFrameLength, // 发送的数据包最大长度
        int lengthFieldOffset,	// 长度字段偏移量
        int lengthFieldLength,	// 长度子段自己占用的字节数
        int lengthAdjustment, 	// 长度字段的偏移量矫正
        int initialBytesToStrip	// 丢弃的起始字节数
    )
    ```