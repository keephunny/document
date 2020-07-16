

### 挂载硬盘
```
#查看当前分区情况
[root@localhost ~]# lsblk
NAME            MAJ:MIN RM  SIZE RO TYPE MOUNTPOINT
sdb               8:16   0    5G  0 disk 
[root@localhost ~]# fdisk -l
磁盘 /dev/sdb：5368 MB, 5368709120 字节，10485760 个扇区
Units = 扇区 of 1 * 512 = 512 bytes
扇区大小(逻辑/物理)：512 字节 / 512 字节
I/O 大小(最小/最佳)：512 字节 / 512 字节
```
### 分区磁盘
通过fdisk -l可以看出挂载了sdb容量为5GB的磁盘
```
[root@localhost ~]# fdisk /dev/sdb
    命令(输入 m 获取帮助)：m
    命令操作
    a   toggle a bootable flag
    b   edit bsd disklabel
    c   toggle the dos compatibility flag
    d   delete a partition
    g   create a new empty GPT partition table
    G   create an IRIX (SGI) partition table
    l   list known partition types
    m   print this menu
    n   add a new partition
    o   create a new empty DOS partition table
    p   print the partition table
    q   quit without saving changes
    s   create a new empty Sun disklabel
    t   change a partition's system id
    u   change display/entry units
    v   verify the partition table
    w   write table to disk and exit
    x   extra functionality (experts only)

    命令(输入 m 获取帮助)：n
    Partition type:
    p   primary (0 primary, 0 extended, 4 free)
    e   extended
    Select (default p): p
    分区号 (1-4，默认 1)：
    起始 扇区 (2048-10485759，默认为 2048)：
    将使用默认值 2048
    Last 扇区, +扇区 or +size{K,M,G} (2048-10485759，默认为 10485759)：
    将使用默认值 10485759
    分区 1 已设置为 Linux 类型，大小设为 5 GiB

    命令(输入 m 获取帮助)：p

    磁盘 /dev/sdb：5368 MB, 5368709120 字节，10485760 个扇区
    Units = 扇区 of 1 * 512 = 512 bytes
    扇区大小(逻辑/物理)：512 字节 / 512 字节
    I/O 大小(最小/最佳)：512 字节 / 512 字节
    磁盘标签类型：dos
    磁盘标识符：0xb79c8045

    设备 Boot      Start         End      Blocks   Id  System
    /dev/sdb1            2048    10485759     5241856   83  Linux

    命令(输入 m 获取帮助)：w
    The partition table has been altered!

    Calling ioctl() to re-read partition table.
    正在同步磁盘。
[root@localhost ~]# lsblk
NAME            MAJ:MIN RM  SIZE RO TYPE MOUNTPOINT
sdb               8:16   0    5G  0 disk 
└─sdb1            8:17   0    5G  0 part 
```
### 格式化文件系统
```
#-j 是ext3
[root@localhost ~]# mkfs.ext3 /dev/sdb1
    mke2fs 1.42.9 (28-Dec-2013)
    文件系统标签=
    OS type: Linux
    块大小=4096 (log=2)
    分块大小=4096 (log=2)
    Stride=0 blocks, Stripe width=0 blocks
    327680 inodes, 1310464 blocks
    65523 blocks (5.00%) reserved for the super user
    第一个数据块=0
    Maximum filesystem blocks=1342177280
    40 block groups
    32768 blocks per group, 32768 fragments per group
    8192 inodes per group
    Superblock backups stored on blocks: 
        32768, 98304, 163840, 229376, 294912, 819200, 884736

    Allocating group tables: 完成                            
    正在写入inode表: 完成                            
    Creating journal (32768 blocks): 完成
    Writing superblocks and filesystem accounting information: 完成 
```

### 创建挂载点
``` 
#创建挂载点
[root@localhost ~]# mkdir /data
[root@localhost /]# mount /dev/sdb1  /data/
[root@localhost /]# lsblk
    NAME            MAJ:MIN RM  SIZE RO TYPE MOUNTPOINT
    sda               8:0    0   20G  0 disk 
    ├─sda1            8:1    0  500M  0 part /boot
    └─sda2            8:2    0   14G  0 part 
    ├─centos-root 253:0    0   10G  0 lvm  /
    ├─centos-swap 253:1    0    2G  0 lvm  [SWAP]
    └─centos-home 253:2    0    2G  0 lvm  /home
    sdb               8:16   0    5G  0 disk 
    └─sdb1            8:17   0    5G  0 part /data
```
### 开机自动挂载
```
[root@localhost ~]# vim /etc/fstab 
在文件中添加如下：/dev/sdb1       /data      ext3   defaults   0 0    
```
### 验证挂载结果
```
[root@localhost ~]# reboot
[root@localhost ~]# df -h
文件系统                 容量  已用  可用 已用% 挂载点
/dev/mapper/centos-root   10G  982M  9.1G   10% /
devtmpfs                 481M     0  481M    0% /dev
tmpfs                    490M     0  490M    0% /dev/shm
tmpfs                    490M  6.7M  484M    2% /run
tmpfs                    490M     0  490M    0% /sys/fs/cgroup
/dev/sr0                 4.1G  4.1G     0  100% /mnt/cdrom
/dev/sdb1                4.8G   11M  4.6G    1% /data

```