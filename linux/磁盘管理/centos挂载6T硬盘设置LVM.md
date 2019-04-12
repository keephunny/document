


### 名称路径说明
    /mnt/storage  12T硬盘挂载点，用于存图片视频
    /dev/sda 999.0 GB
    /dev/sdb：6001.2 GB
    /dev/sdc：6001.2 GB
    

### 1. 格式化硬盘
两块硬盘执行同样的操作  sdb sdc
```
    [root@whzc-slave ~]# parted /dev/sdc 
        mklabel gpt
        mkpart primary 0% 100%

        p
        quit
    [root@whzc-slave ~]# mkfs.ext4 /dev/sdc1
    [root@whzc-slave ~]# blkid
    /dev/sda1: UUID="af501da6-d93d-417a-899f-661b2e55487a" TYPE="xfs" 
    /dev/sda2: UUID="HY5CWP-E1UL-IlSD-pa3q-R0m2-41th-UYTxRT" TYPE="LVM2_member" 
    /dev/mapper/centos-root: UUID="6b0dfc72-a651-4fd8-b247-d56308495514" TYPE="xfs" 
    /dev/mapper/centos-swap: UUID="73507422-dfa3-4570-89fe-724f6f2c414e" TYPE="swap" 
    /dev/sdb1: UUID="kxv34d-yWZA-YH3F-cJn3-mAkK-L5WS-C6c58A" TYPE="LVM2_member" PARTLABEL="primary" PARTUUID="a6d62e29-6d82-4db2-92d3-184bc123724b" 
    /dev/sdc1: UUID="22180138-1c31-4218-af79-005d3e354b3d" TYPE="ext4" PARTLABEL="primary" PARTUUID="cb4b35bf-3471-4210-8efe-9242c6afb910" 
    #TYPE="ext4" 受pvcreat影响

    [root@whzc-slave ~]# lsblk
    NAME            MAJ:MIN RM   SIZE RO TYPE MOUNTPOINT
    sda               8:0    0 930.4G  0 disk 
    ├─sda1            8:1    0   600M  0 part /boot
    └─sda2            8:2    0 929.8G  0 part 
    ├─centos-root 253:0    0 917.8G  0 lvm  /
    └─centos-swap 253:1    0    12G  0 lvm  [SWAP]
    sdb               8:16   0   5.5T  0 disk 
    └─sdb1            8:17   0   5.5T  0 part 
    sdc               8:32   0   5.5T  0 disk 
    └─sdc1            8:33   0   5.5T  0 part 
    sr0              11:0    1  1024M  0 rom  
```

### 2. 创建PV
```
    [root@whzc-slave ~]# pvcreate /dev/sdb1
    [root@whzc-slave ~]# pvcreate /dev/sdc1
    [root@whzc-slave ~]# pvs
    PV         VG      Fmt  Attr PSize   PFree
    /dev/sda2  centos  lvm2 a--  929.80g    0 
    /dev/sdb1  vg_data lvm2 a--    5.46t 5.46t
    /dev/sdc1  vg_data lvm2 a--    5.46t 5.46t
```
  
### 3. 创建VG
```
    [root@whzc-slave ~]# vgcreate vg_data /dev/sd{b1,c1}
    Volume group "vg_data" successfully created
    [root@whzc-slave ~]# vgchange -a y vg_data #激活
    0 logical volume(s) in volume group "vg_data" now active  
    [root@whzc-slave ~]# vgs
    VG      #PV #LV #SN Attr   VSize   VFree 
    centos    1   2   0 wz--n- 929.80g     0 
    vg_data   2   0   0 wz--n-  10.92t 10.92t
```    
  
### 4. 创建LV
12T没有全部用完，预留1T左右 
```
    [root@whzc-slave ~]# lvcreate -L 10T -n storage vg_data
    Logical volume "storage" created.
    设置文件系统
    [root@whzc-slave ~]# mkfs.ext4 /dev/vg_data/storage  
    创建挂载点
    [root@whzc-slave ~]# mkdir /mnt/storage
    挂载到文件夹
    [root@whzc-slave ~]# mount /dev/vg_data/storage /mnt/storage/
    开机自动挂载
    [root@whzc-slave ~]# vim /etc/fstab 
        #190411增加挂载12T硬盘lvm
        /dev/vg_data/storage    /mnt/storage    ext4    defaults 0 0
        :wq 
    [root@whzc-slave ~]# lvs
    LV      VG      Attr       LSize   Pool Origin Data%  Meta%  Move Log Cpy%Sync Convert
    root    centos  -wi-ao---- 917.80g                                                    
    swap    centos  -wi-ao----  12.00g                                                    
    storage vg_data -wi-a-----  10.00t
    [root@whzc-slave ~]# df -h
    文件系统                     容量  已用  可用 已用% 挂载点
    /dev/mapper/centos-root      918G   90G  829G   10% /
    devtmpfs                     7.7G     0  7.7G    0% /dev
    tmpfs                        7.7G     0  7.7G    0% /dev/shm
    tmpfs                        7.7G  8.8M  7.7G    1% /run
    tmpfs                        7.7G     0  7.7G    0% /sys/fs/cgroup
    /dev/sda1                    594M  125M  470M   21% /boot
    /dev/mapper/vg_data-storage   10T   73M  9.5T    1% /mnt/storage  
```    