 
* ffmpeg-3.1.3.tar.gz
* lame-3.99.5.tar.xz
* x264-snapshot-20150906-2245.zip
* yasm-1.3.0.tar.gz
 
 
1. lame安装
```
    [root@localhost test]# xz -d lame-3.99.5.tar.xz
    [root@localhost test]# tar -xvf lame-3.99.5.tar
    [root@localhost test]# cd lame-3.99.5
        ./configure                        //配置
        make                               //编译
        make install                       //安装
```
2. yasm安装
```
    [root@localhost test]# tar -zxvf yasm-1.3.0.tar.gz
    [root@localhost test]# cd yasm-1.3.0
    [root@localhost test]# ./configure
    ./configure --prefix=/usr/yasm
    [root@localhost test]# make && make install
```
3. libx264安装 
```
    [root@localhost test]# unzip x264-snapshot-20150906-2245.zip
    [root@localhost test]# cd x264-snapshot-20150906-2245
    [root@localhost test]# chmod +x *
    [root@localhost test]# chmod +x tools/*
    [root@localhost test]# ./configure --enable-shared --enable-static
    [root@localhost test]# make && make install
```            
	
4. ffmpeg安装 
```
    [root@localhost test]# tar -zxvf ffmpeg-3.1.3.tar.gz
    [root@localhost test]# cd ffmpeg-3.1.3
    [root@localhost test]# ./configure --enable-shared --prefix=/usr/local/ffmpeg  --enable-gpl --enable-libx264
    [root@localhost test]# make && make install
    #大要要执行10几分钟
    #./configure --enable-shared --prefix=/usr/local/ffmpeg  --enable-gpl --enable-libx264
```
5. ffmpeg 加入环境变量
```
    [root@localhost ffmpeg]# vim /etc/ld.so.conf
        include ld.so.conf.d/*.conf
        /usr/local/ffmpeg/lib       ///usr/local/ffmpeg 目录是我ffmpeg安装目录，根据你的安装目录改吧
        /usr/local/lib
 
    #配置生效
    [root@localhost]# ldconfig
    #查看so文件是否加载
    [root@localhost]# ldconfig  -p  | grep ffmpeg  
    [root@localhost ffmpeg]# vim /etc/profile            
        export PATH=$PATH:/usr/local/ffmpeg/bin
    [root@localhost ffmpeg]# source /etc/profile          
```
6. 验证
```
    [root@localhost ffmpeg]# ffmpeg -version
        ffmpeg version 3.1.3 Copyright (c) 2000-2016 the FFmpeg developers
        built with gcc 4.8.5 (GCC) 20150623 (Red Hat 4.8.5-28)
        configuration: --enable-shared --prefix=/usr/local/ffmpeg
        libavutil      55. 28.100 / 55. 28.100
        libavcodec     57. 48.101 / 57. 48.101
        libavformat    57. 41.100 / 57. 41.100
        libavdevice    57.  0.101 / 57.  0.101
        libavfilter     6. 47.100 /  6. 47.100
        libswscale      4.  1.100 /  4.  1.100
        libswresample   2.  1.100 /  2.  1.100

    [root@localhost ffmpeg]# ffmpeg -i 100.mp4   101.mp4
```
### error
```
    [root@localhost x264-snapshot-20150906-2245]# ./configure --enable-shared --enable-static
    Found no assembler
    Minimum version is yasm-1.2.0
    If you really want to compile without asm, configure with --disable-asm.
    意思是说你有一个老版本的yasm（似乎是编译器？）装在你的系统里面，所以该升级他了。快速解决方法：
    解决：安装yasm安装


        
    Unknown encoder 'libx264'
    解决：chmod +x *  子目录也要加权限
    ./configure --enable-shared --enable-static

        
    [root@localhost ffmpeg-3.1.3]# /usr/local/ffmpeg/bin/ffmpeg -version
    ffmpeg: error while loading shared libraries: libpostproc.so.54: cannot open shared object file: No such file or directory
    解决：vim /etc/ld.so.conf /usr/local/ffmpeg/lib       ///usr/local/ffmpeg 目录是我ffmpeg安装目录，根据你的安装目录改吧


    /usr/local/ffmpeg/bin/ffmpeg: error while loading shared libraries: libx264.so.148: cannot open shared object file: No such file or directory
    解决：确保安装好libx264
     vim /etc/ld.so.conf
    /usr/local/lib
```    