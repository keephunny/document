curl命令可以用来执行下载，发送各种http请求。指定http头部等操作。通过yum install curl安装。curl将下载文件输陨到stdout，将进度信息输出到stderr，不显示进度信息使用--silent选项。

    #将下载文件输出到终端，所有下载的数据都被写入到stdout
    curl URL --silent

    #将下载数据写入到指定名称的文件中，并使用--progress显示进度条
    curl http://www.baidu.com -o filename.txt --progress

## 断点续传
    #当文件下载完成之前结束该进程
    curl -o http://www.baidu.com/index.html
    ######   20%
    #通过添加-C选项继续对该文件进行下载，已下载过的文件不会被重复下载
    curl -C - -o http://www.baidu.com/index.html
    
https://www.cnblogs.com/gbyukg/p/3326825.html