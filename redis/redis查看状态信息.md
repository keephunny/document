# redis查看状态信息
info   all|default
Info 指定项

## server服务器信息
	redis_version : Redis 服务器版本
	redis_git_sha1 : Git SHA1
	redis_git_dirty : Git dirty flag
	os : Redis 服务器的宿主操作系统
	arch_bits : 架构（32 或 64 位）
	multiplexing_api : Redis 所使用的事件处理机制
	gcc_version : 编译 Redis 时所使用的 GCC 版本
	process_id : 服务器进程的 PID
	run_id : Redis 服务器的随机标识符（用于 Sentinel 和集群）
	tcp_port : TCP/IP 监听端口
	uptime_in_seconds : 自 Redis 服务器启动以来，经过的秒数
	uptime_in_days : 自 Redis 服务器启动以来，经过的天数
	lru_clock : 以分钟为单位进行自增的时钟，用于 LRU 管理