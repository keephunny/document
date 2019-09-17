
* 容器生命周期管理
    docker [run|start|stop|restart|kill|rm|pause|unpasse]
* 容器操作运维
    docker [ps|inspect|top|attach|events|logs|wati|export|port]
* 容器rootfs命令
    docker [commit|cp|diff]
* 镜象仓库
    docker [login|pull|search]
* 本地镜象管理
    docker [images|rmi|tag|build|history|save|import]
* 其它命令
    docker [info|version]


### 查看日志    
#### 1.docker attach进入容器查看日志

docker attach [options] 容器会连接到正在运行的容器，然后将容器的标准输入、输出和错误流信息附在本地打印出来。
使用ctrl+c可以直接断开连接，但是这样会导致容器退出，而且还stop了。如果想在脱离容器终端时，容器依然运行。就需要使用--sig-proxy这个参数。
```
     docker attach --sig-proxy=false dockerID
```
#### 2.docker logs查看日志

* details		显示提供给日志的额外细节
* follow或-f		按日志输出
* since		从某个时间开始显示
* tail	从日志末尾多少行开始显示
* timestamps或-t		显示时间戳
* until		打印某个时间以前的日志
```
    查看指定id的日志
    docker logs --tail='100' dockerID
```