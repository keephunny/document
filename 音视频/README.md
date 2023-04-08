### FPS 帧率（每秒传输帧数(Frames Per Second)）
<p>FPS是图像领域中的定义，是指画面每秒传输帧数，通俗来讲就是指动画或视频的画面数。FPS是测量用于保存、显示动态视频的信息数量。每秒钟帧数愈多，所显示的动作就会越流畅。通常，要避免动作不流畅的最低是30。某些计算机视频格式，每秒只能提供15帧。 </p> 
<p>电影以每秒24张画面的速度播放，也就是一秒钟内在屏幕上连续投射出24张静止画面。有关动画播放速度的单位是fps，其中的f就是英文单词Frame（画面、帧），p就是Per（每），s就是Second（秒）。用中文表达就是多少帧每秒，或每秒多少帧。电影是24fps，通常简称为24帧。 </p> 

### 码率（Bitrate）
码率：影响体积，与体积成正比：码率越大，体积越大；码率越小，体积越小。

码率就是数据传输时单位时间传送的数据位数,一般我们用的单位是kbps即千位每秒。也就是取样率（并不等同与采样率，采样率的单位是Hz，表示每秒采样的次数），单位时间内取样率越大，精度就越高，处理出来的文件就越接近原始文件，但是文件体积与取样率是成正比的，所以几乎所有的编码格式重视的都是如何用最低的码率达到最少的失真，围绕这个核心衍生出来cbr（固定码率）与vbr（可变码率）， “码率”就是失真度，码率越高越清晰，反之则画面粗糙而多马赛克。
首先要了解的是，ps指的是/s，即每秒。Kbps指的是网络速度，也就是每秒钟传送多少个千位的信息（K表示千位，Kb表示的是多少千个位），为了在直观上显得网络的传输速度较快，一般公司都使用kb（千位）来表示。1KB/S=8Kbps。ADSL上网时的网速是512Kbps,如果转换成字节，就是512/8=64KB/S(即64千字节每秒）。




### 分辨率
影响图像大小，与图像大小成正比：分辨率越高，图像越大；分辨率越低，图像越小。

## 清晰度
在码率一定的情况下，分辨率与清晰度成反比关系：分辨率越高，图像越不清晰，分辨率越低，图像越清晰。
是衡量图像细节表现力的技术参数。分辨率高是保证彩色显示器清晰度的重要前提。分辨率是体现屏幕图像的精密度，是指显示器所能显示的点数的多少。通常，“分辨率”被表示成每一个方向上的像素数量，分辨率越高，可显示的点数越多，画面就越精细。


### 流媒体视频格式H.264
是国际标准化组织（ISO）和国际电信联盟（ITU）共同提出的继MPEG4之后的新一代数字视频压缩格式H.264最具价值的部分无疑是更高的数据压缩比。在同等的图像质量条件下，H.264的数据压缩比能比当前DVD系统中使用的 MPEG-2高2-3倍，比MPEG-4高1.5-2倍。正因为如此，经过H.264压缩的视频数据，在网络传输过程中所需要的带宽更少，也更加经济。在 MPEG-2需要6Mbps的传输速率匹配时，H.264只需要1Mbps-2Mbps的传输速率。 
是ISO/IEC的MPEG-4高级视频编码（Advanced Video Coding，AVC）的第10 部分。因此，不论是MPEG-4 AVC、MPEG-4 Part 10，还是ISO/IEC 14496-10，都是指H.264。   
H.264是在MPEG-4技术的基础之上建立起来的，其编解码流程主要包括5个部分：帧间和帧内预测（Estimation）、变换（Transform）和反变换、量化（Quantization）和反量化、环路滤波（Loop Filter）、熵编码（Entropy Coding）。

### 采样率
采样频率，也称为采样速度或者采样率，定义了每秒从连续信号中提取并组成离散信号的采样个数，它用赫兹（Hz）来表示。采样频率的倒数是采样周期或者叫作采样时间，它是采样之间的时间间隔。通俗的讲采样频率是指计算机每秒钟采集多少个信号样本。  
在当今的主流采集卡上，采样频率一般共分为22.05KHz、44.1KHz、48KHz三个等级，22.05KHz只能达到FM广播的声音品 质，44.1KHz则是理论上的CD音质界限，48KHz则更加精确一些。对于高于48KHz的采样频率人耳已无法辨别出来了，所以在电脑上没有多少使用 价值。


### 文件格式
* mp3
* mp4
    MP4是一套用于音频、视频信息的压缩编码标准，由国际标准化组织（ISO）和国际电工委员会（IEC）下属的“动态图像专家组”（Moving Picture Experts Group，即MPEG）制定，第一版在1998年10月通过，第二版在1999年12月通过。MPEG-4格式的主要用途在于网上流、光盘、语音发送（视频电话），以及电视广播。
* acc
* flv
* avi
* rmvb
    RMVB视频文件为例，RMVB中的VB，指的是VBR，即Variable BitRate的缩写，中文含义是可变比特率，它表示RMVB采用的是动态编码的方式，把较高的采样率用于复杂的动态画面(歌舞、飞车、战争、动作等)，而把较低的采样率用于静态画面，合理利用资源，达到画质与体积可兼得的效果。

数据速率
总比特率


### 名词解释
* OSD（on-screen display）即屏幕菜单式调节方式
* PTZ 在安防监控应用中是 Pan/Tilt/Zoom 的简写，代表云台全方位（左右/上下）移动及镜头变倍、变焦控制。
* SIP：（Session initialization Protocol，会话初始协议）是由IETF制定的多媒体通信协议。是一个基于文本的应用层控制协议，用于创建、修改和释放一个或多个参与者的会话。SIP 是一种源于互联网的IP 语音会话控制协议，具有灵活、易于实现、便于扩展等特点。
* IPC：IP Camera网络摄像机，它是在前一代模拟摄像机的基础上，集成了编码模块后的摄像机。
* 信令：信令是控制电路的信号，是终端和终端、终端和网络之间传递的一种消息，专门用来控制电路，建立、管理、删除连接，以使用户能够正常通过这些连接进行通信。
* flv
* rtmp
* hls
* m3u8
* DVR：Digital Video Recorder 硬盘录像机
* NVR：Network Video Recorder，即网络视频录像机
* GB28181
* RSVP（ Resource ReServation Protocol ）用于预约网络资源，
* RTP（ Real-time Transmit Protocol ）用于传输实时数据并提供服务质量（ QoS ）反馈，
* RTSP （ Real-Time Stream Protocol ）用于控制实时媒体流的传输，
* SAP（ Session Announcement Protocol ）用于通过组播发布多媒体会话，
* SDP（ Session Description Protocol ）用于描述多媒体会话。
* IPSec:因特网安全协议(InternetProtocolSecurity)
* ISUP是海康部分新版NVR主动连接协议的新名称


ehome
isup

青柿视频流媒体服务解决方案
https://www.liveqing.com/docs/products/LiveGBS.html
https://www.liveqing.com/docs/manuals/LiveGBS.html

demo
https://gbs.liveqing.com:10010/login.html

 安徽旭帆信息科技有限公司
http://www.tsingsee.com/
https://github.com/tsingsee/EasyIPCamera


安徽
http://www.easydarwin.org/


https://github.com/swwheihei/wvp-GB28181
https://doc.wvp-pro.cn/#/./_content/introduction/config


海康萤石平台
https://open.ys7.com/help/399

一个基于C++11的高性能运营级流媒体服务框架
https://github.com/ZLMediaKit/ZLMediaKit



国产开源流媒体SRS4.0对视频监控GB28181的支持
https://github.com/ossrs/srs
https://ossrs.net/lts/zh-cn/docs/v5/doc/introduction
https://zhuanlan.zhihu.com/p/141113179?utm_source=wechat_session

5.0安装配置 海康 players/srs_gb28181.html
https://blog.csdn.net/adkada1/article/details/121121901

https://www.bilibili.com/video/BV1Mv4y1d7Vy/?spm_id_from=333.337.search-card.all.click&vd_source=47a8ecfae6ac799392c4199544055284
从零开发国标GB28181流媒体服务器，并实现大华摄像头国标协议推流。代码开源地址：https://gitee.com/Vanishi/BXC_SipServer。GB28181流媒体服务器的sip信令服务是我自己开发，流媒体传输和流媒体分发则采用开源项目ZLMediaKit。最终可以将摄像头的视频流通过国标协议传输到流媒体服务器，并实现RTSP/RTMP/HTTP-FLV/HLS等协议进行分发。另外如果没有支持GB28181的摄像头用来调试，可以看我视频合集里面另外一个模拟GB28181摄像头的项目。



实现一个支持国标GB28181协议的摄像头模拟软件
https://www.bilibili.com/video/BV1cK411z73C/?spm_id_from=333.788&vd_source=47a8ecfae6ac799392c4199544055284




GBSipDevice客户端测试 模拟软件
https://gitee.com/DLGCY_GB28181/GB28181_ServerPlatform
https://www.dianjilingqu.com/541592.html


海康
tsingeye/FreeEhome
https://github.com/tsingeye/FreeEhome



各终端实时推流
https://github.com/EasyDarwin/EasyPusher

开放演示平台
https://gbs.realgbs.com:8280/monitor/catalogs
https://gbs.liveqing.com:10010/login.html


cd cmake-3.12.4
./bootstrap
gmake
./configure
make && make install
vi /etc/profile
export PATH=/usr/loacl/bin/cmake:$PATH
source /etc/profile



No SOURCES given to target: flv
CMake Error at CMakeLists.txt:111 (add_library):
  No SOURCES given to target: mpeg
CMake Error at CMakeLists.txt:132 (add_library):
  No SOURCES given to target: mov
CMake Error at CMakeLists.txt:170 (add_library):
  No SOURCES given to target: zltoolkit
CMake Error at CMakeLists.txt:148 (add_library):
  No SOURCES given to target: rtp
