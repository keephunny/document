
### 下载安装包

sudo yum install -y https://download.postgresql.org/pub/repos/yum/reporpms/EL-7-x86_64/pgdg-redhat-repo-latest.noarch.rpm
sudo yum install -y postgresql14-server
sudo /usr/pgsql-14/bin/postgresql-14-setup initdb
sudo systemctl enable postgresql-14
sudo systemctl start postgresql-14

```
#查看是否已安装过
[root@localhost ]# 
[root@localhost ]# 
[root@localhost ]# 
[root@localhost ]# 
[root@localhost ]# 



```

### 前置配置

```
[root@localhost ]# 
[root@localhost ]# 
[root@localhost ]# 
```

### 安装程序
```
[root@localhost ]#  


```

### 卸载程序
```
#源码安装卸载
[root@localhost ]#  
[root@localhost ]# 
```


### 配置调试 
命令行工具
```
[root@localhost src]# taos
  CREATE DATABASE demo;
  USE demo;
  CREATE TABLE t (ts TIMESTAMP, speed INT);
  INSERT INTO t VALUES ('2019-07-15 00:00:00', 10);
  INSERT INTO t VALUES ('2019-07-15 01:00:00', 20);
  SELECT * FROM t;
```


集群配置
 130节点、131节点
```
#130
[root@localhost ]# vim conf/cassandra.yaml 
  #种子节点
  -seeds: "192.168.1.130"
  listen_address: 192.168.1.130
  start_rpc: true
  rpc_address: 192.168.1.130
[root@localhost ]# 
#131
[root@localhost ]# vim conf/cassandra.yaml 
  #种子节点
  -seeds: "192.168.1.130"
  listen_address: 192.168.1.131
  start_rpc: true
  rpc_address: 192.168.1.131
[root@localhost ]# 
# 查询环状
[root@localhost ]# ./nodetool ring
# 查看集群物理机状态
[root@localhost ]# ./nodetool status 
  Datacenter: datacenter1
  =======================
  Status=Up/Down
  |/ State=Normal/Leaving/Joining/Moving
  --  Address          Load       Tokens       Owns (effective)  Host ID                               Rack
  UN  192.168.121.130  314.99 KB  256          100.0%            56b7e15d-faac-4622-b6bc-497070ffd798  rack1
  UN  192.168.121.131  184.22 KB  256          100.0%            deca7291-cc61-44c7-8ec8-666dffb7dffb  rack1



```

#### 文件目录

```
[root@localhost ]# cd /usr/local/t
```



连接数据库
```
[root@localhost ]#  bin/cqlsh 192.168.121.131
  cqlsh> describe keyspaces;
    system_schema  iot_spaces  system_distributed
    system_auth    system      system_traces
[root@localhost ]# 

```


### 开机自启
启动服务进程：systemctl start   


### 目录文件

```
```



### 常见错误

```

```



### 安装参数

* --prefix=PREFIX 将所有文件安装在 PREFIX 目录而不是 /usr/local/pgsql 下。实际文件将安装在各种子目录中；任何文件都不会直接安装在 PREFIX 目录中。
* --exec-prefix=EXEC-PREFIX 您可以将依赖于体系结构的文件的安装目录放在与 PREFIX 不同的前缀 EXEC-PREFIX 下。这对于在主机之间共享依赖于体系结构的独立文件很有用。如果您省略此项，则 EXEC-PREFIX 将等于 PREFIX，并且依赖于体系结构和独立的文件都将安装在同一个树下，这可能就是您想要的。
* --bindir=DIRECTORY 指定可执行程序的目录。默认值为 EXEC-PREFIX/bin，通常表示 /usr/local/pgsql/bin。
* --sysconfdir=DIRECTORY 设置各种配置文件所在的目录，默认值为 PREFIX/etc。
* --libdir=DIRECTORY 设置安装库和动态加载模块的位置。默认值为 EXEC-PREFIX/lib。
* --includedir=DIRECTORY 设置安装 C 和 C++ 头文件的目录。默认值为 PREFIX/include。
* --datarootdir=DIRECTORY 设置各种只读 数据文件的根目录。这仅设置以下一些选项的默认值。默认值为 PREFIX/share。管理企业数据
* --datadir=DIRECTORY 设置已安装程序使用的只读数据文件的目录。默认值为 DATAROOTDIR。请注意，这与数据库文件将放置的位置无关。
* --localedir=DIRECTORY 设置安装区域数据（特别是消息翻译目录文件）的目录。默认值为 DATAROOTDIR/locale。
* --mandir=DIRECTORY 随 PostgreSQL 一起提供的 man 页将安装在此目录下的相应 manx 子目录中。默认值为 DATAROOTDIR/man。
* --docdir=DIRECTORY 设置安装文档文件（man 页除外）的根目录。这仅设置以下选项的默认值。此选项的默认值是 DATAROOTDIR/doc/postgresql。
* --htmldir=DIRECTORY PostgreSQL 的 HTML 格式文档将安装在此目录下。默认值为 DATAROOTDIR。

--------------

* --enable-nls[=LANGUAGES] 
	启用本地语言支持（NLS），即能够以非英语语言显示程序的邮件。 LANGUAGES 是可选的空格分隔列表，包含您想要支持的语言代码，例如 --enable-nls='de fr'。（您的列表与实际提供的翻译集之间的交集将自动计算。）如果您不指定列表，则安装所有可用的翻译。
	要使用此选项，您需要 Gettext API 的实现。

* --with-perl 
	构建 PL/Perl 服务器端语言。

* --with-python 
	构建 PL/Python 服务器端语言。

* --with-tcl 
	构建 PL/Tcl 服务器端语言。

* --with-tclconfig=DIRECTORY 
	Tcl 安装 tclConfig.sh 文件，其中包含构建与 Tcl 接口的模块所需的配置信息。此文件通常在已知位置自动找到，但如果您想使用不同版本的 Tcl，则可以指定要查找 tclConfig.sh 的目录。

* --with-llvm 
	构建时支持基于 LLVM 的JIT编译（请参阅第 30 章）。这需要安装 LLVM 库。当前所需的最低 LLVM 版本是 14。
	llvm-config 将用于查找所需的编译选项。 llvm-config 将在您的 PATH 中搜索。如果找不到所需的程序，请使用 LLVM_CONFIG 指定正确 llvm-config 的路径。例如：
	./configure ... --with-llvm LLVM_CONFIG='/path/to/llvm/bin/llvm-config'
	LLVM 支持需要兼容的 clang 编译器（如果需要，通过 CLANG 环境变量指定），以及一个工作的 C++ 编译器（如果需要，通过 CXX 环境变量指定）。

* --with-lz4 
	构建时支持 LZ4 压缩。

* --with-zstd 
	构建时支持 Zstandard 压缩。

* --with-ssl=LIBRARY 
	构建时支持SSL（加密）连接。唯一支持的 LIBRARY 是 openssl，它同时用于 OpenSSL 和 LibreSSL。这需要安装 OpenSSL 包。 configure 将检查所需的头文件和库，以确保您的 OpenSSL 安装足够，然后再继续。

* --with-openssl 
	过时的 --with-ssl=openssl 等效项。

* --with-gssapi 
	构建时支持 GSSAPI 身份验证。GSSAPI 需要安装 MIT Kerberos。在许多系统上，GSSAPI 系统（MIT Kerberos 安装的一部分）未安装在默认搜索的目录中（例如 /usr/include、/usr/lib），因此除了此选项外，您还必须使用 --with-includes 和 --with-libraries 选项。 configure 将检查所需的头文件和库，以确保您的 GSSAPI 安装足够，然后再继续。

* --with-ldap 
	构建时支持LDAP 身份验证和连接参数查找（有关更多信息，请参阅第 32.18 节 和 第 20.10 节）。在 Unix 上，这需要安装 OpenLDAP 包。在 Windows 上，使用默认的 WinLDAP 库。 configure 将检查所需的头文件和库，以确保您的 OpenLDAP 安装足够，然后再继续。

* --with-pam 
	构建时支持PAM（可插拔认证模块）支持。

* --with-bsd-auth 
	构建时支持 BSD 身份验证。（BSD 身份验证框架目前仅在 OpenBSD 上可用。）

* --with-systemd 
	构建时支持 systemd 服务通知。这会改善与 systemd 启动服务器的集成，但否则没有影响；有关更多信息，请参阅第 18.3 节。使用此选项需要安装 libsystemd 和相关的头文件。

* --with-bonjour 
	构建时支持 Bonjour 自动服务发现。这需要在您的操作系统中具有 Bonjour 支持。在 macOS 上推荐。

* --with-uuid=LIBRARY 
	构建 uuid-ossp 模块（提供生成 UUID 的函数），使用指定的 UUID 库。 LIBRARY 必须是以下之一：
	bsd 使用 FreeBSD 和其他一些 BSD 派生系统中找到的 UUID 函数
	e2fs 使用 e2fsprogs 项目创建的 UUID 库；此库存在于大多数 Linux 系统和 macOS 中，并且也可以在其他平台上获得
	ossp 使用 OSSP UUID 库

* --with-ossp-uuid 
	过时的 --with-uuid=ossp 等效项。

* --with-libcurl 
	构建时支持 libcurl 以实现 OAuth 2.0 客户端流程。此功能需要 libcurl 版本 7.61.0 或更高版本。使用此选项构建将检查所需的头文件和库，以确保您的 curl 安装足够，然后再继续。

* --with-libnuma 
	构建时支持 libnuma 以实现基本的 NUMA 支持。仅在实现了 libnuma 库的平台上受支持。

* --with-liburing 
	构建时支持 liburing，启用 io_uring 支持以实现异步 I/O。
	为了检测所需的编译器和链接器选项，PostgreSQL 将查询 pkg-config。管理企业数据
	要使用位于非标准位置的 liburing 安装，您可以设置与 pkg-config 相关的环境变量（请参阅其文档）。

* --with-libxml 
	构建时支持 libxml2，启用 SQL/XML 支持。此功能需要 libxml2 版本 2.6.23 或更高版本。
	为了检测所需的编译器和链接器选项，PostgreSQL 将查询 pkg-config（如果已安装并知道 libxml2）。否则，如果找到，将使用 libxml2 安装的程序 xml2-config。首选使用 pkg-config，因为它能更好地处理多架构安装。
	要使用位于非标准位置的 libxml2 安装，您可以设置与 pkg-config 相关的环境变量（请参阅其文档），或者将环境变量 XML2_CONFIG 设置为指向 libxml2 安装附带的 xml2-config 程序，或者设置变量 XML2_CFLAGS 和 XML2_LIBS。（如果安装了 pkg-config，那么要覆盖其对 libxml2 位置的理解，您必须设置 XML2_CONFIG 或将 XML2_CFLAGS 和 XML2_LIBS 都设置为非空字符串。）

* --with-libxslt 构建时支持 libxslt，启用 xml2 模块来执行 XML 的 XSL 转换。还必须指定 --with-libxml。

* --with-selinux 构建时支持 SElinux，启用 sepgsql 扩展。


### configure环境变量


### 数据库目录文件详解
```
PGDATA/
├── base/               # 默认表空间存储区
├── global/             # 全局元数据
├── pg_wal/             # 预写式日志（WAL）
├── pg_xact/            # 事务状态文件
├── pg_stat/            # 统计信息
├── pg_log/             # 日志文件（需配置）
└── postgresql.conf     # 主配置文件
```

#### base目录：表空间数据存储核心
base目录是默认表空间（pg_default）的物理存储位置，采用”数据库OID+表OID”的二级目录结构：
```
base/
├── 1/                  # 系统数据库template1
│   ├── 1247           # 表文件（按OID命名）
│   └── 1247_fsm       # 空闲空间映射文件
└── 16384/             # 用户数据库（示例OID）
    ├── 12345          # 用户表文件
    └── 12345_vm       # 可见性映射文件

```
每个数据库对应一个以OID命名的子目录，表文件命名格式为<relfilenode>，索引文件使用相同规则，辅助文件（_fsm、_vm）分别记录空闲空间和页面可见性


#### pg_wal目录：事务日志管理
pg_wal目录（PostgreSQL 10+版本，旧版为pg_xlog）存储预写式日志（WAL），采用固定大小文件轮转机制：
```
pg_wal/
├── 000000010000000000000001  # WAL段文件
└── 000000010000000000000002
```
每个WAL段文件固定16MB（可通过wal_segment_size调整），文件名格式为<timelineID><logID><segmentID>，通过checkpoint机制触发段文件回收

#### global目录：集群元数据仓库
global目录存储跨数据库的全局对象，包括：表空间定义（pg_tablespace）、角色信息（pg_authid）、数据库属性（pg_database）
```
pg_control：集群状态核心文件
PG_VERSION：数据库版本标识
pg_filenode.map：表OID与文件节点映射

```


#### data目录

* pg_hba.conf：认证配置文件，配置了允许哪些IP访问数据库，及认证方式等信息。
* pg_ident.conf："ident"认证方式的用户映射文件。
* PG_VERSION：记录了数据库版本号信息。
* postgresql.auto.conf：作用同 postgresql.conf ，优先级高于 postgresql.conf，在数据库中通过alter命令更改的参数记录在此文件中。
* postgresql.conf：数据库实例主配置文件，基本上所有的数据库参数配置都在此文件中。
* postmaster.opts：记录数据库启动命令。
* postmaster.pid：数据库进程文件，数据库启动时被创建，关闭时消失。
* base：该目录包含数据库用户所创建的各个数据库，同时也包括postgres、template0和template1的pg_defaulttablespace。
* global：该目录包含集群范围的各个表和相关视图。(pg_database、pg_tablespace）
* pg_dynshmem：该目录包含动态共享内存子系统使用的文件。
* pg_commit_ts：该目录包含已提交事务的时间。
* pg_logical：该目录包含逻辑解码的状态数据。
* pg_multixact：该目录包含多事务状态数据。（等待锁定的并发事务）
* pg_notify：该目录包含LISTEN/NOTIFY状态数据。
* pg_replslot：该目录包含复制槽数据。
* pg_snapshots：该目录包含导出的快照。
* pg_stat：该目录包含统计子系统的永久文件。
* pg_stat_tmp：该目录包含统计子系统的临时文件。
* pg_subtrans：该目录包含子事务状态数据。
* pg_tblspc：该目录包含表空间的符号链接。
* pg_twophase：该目录包含预备事务的状态文件。
* pg_wal：该目录包含wal日志。
* pg_xact：该目录包含事务提交状态数据。