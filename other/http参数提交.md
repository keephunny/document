### Form表单EncType编码方式

* application/x-www-form-urlencoded： 窗体数据被编码为名称/值对。这是标准的编码格式。
* multipart/form-data： 窗体数据被编码为一条消息，页上的每个控件对应消息中的一个部分，这个一般文件上传时用。
* text/plain： 窗体数据以纯文本形式进行编码，其中不含任何控件或格式字符。