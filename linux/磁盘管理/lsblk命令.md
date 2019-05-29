lsblk 默认是树形方式显示：

$lsblk

NAME   MAJ:MIN RM   SIZE RO TYPE MOUNTPOINT
sda      8:0    0   2.7T  0 disk 
├─sda1   8:1    0   1.3M  0 part 
├─sda2   8:2    0   400M  0 part /boot
├─sda3   8:3    0 390.6G  0 part /
├─sda4   8:4    0  97.7G  0 part /home
├─sda5   8:5    0   7.8G  0 part [SWAP]
└─sda6   8:6    0   2.2T  0 part /data0

* NAME : 这是块设备名。
* MAJ:MIN : 本栏显示主要和次要设备号。
* RM : 本栏显示设备是否可移动设备。注意，在本例中设备sdb和sr0的RM值等于1，这说明他们是可移动设备。
* SIZE : 本栏列出设备的容量大小信息。例如298.1G表明该设备大小为298.1GB，而1K表明该设备大小为1KB。
* RO : 该项表明设备是否为只读。在本案例中，所有设备的RO值为0，表明他们不是只读的。
* TYPE :本栏显示块设备是否是磁盘或磁盘上的一个分区。在本例中，sda和sdb是磁盘，而sr0是只读存储（rom）。
* MOUNTPOINT : 本栏指出设备挂载的挂载点。