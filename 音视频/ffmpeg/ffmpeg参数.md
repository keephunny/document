## 通用选项
* -L license
* -h 帮助
* -fromats 显示可用的格式，编解码的，协议的...
* -f fmt 强迫采用格式fmt
* -I filename 输入文件
* -y 覆盖输出文件
* -t duration 设置纪录时间 hh:mm:ss[.xxx]格式的记录时间也支持
* -ss position 搜索到指定的时间 [-]hh:mm:ss[.xxx]的格式也支持
* -title string 设置标题
* -author string 设置作者
* -copyright string 设置版权
* -comment string 设置评论
* -target type 设置目标文件类型(vcd,svcd,dvd) 所有的格式选项（比特率，编解码以及缓冲区大小）自动设置，只需要输入如下的就可以了：ffmpeg -i myfile.avi -target vcd /tmp/vcd.mpg
* -hq 激活高质量设置
* -itsoffset offset 设置以秒为基准的时间偏移，该选项影响所有后面的输入文件。该偏移被加到输入文件的时戳，定义一个正偏移意味着相应的流被延迟了 offset秒。 [-]hh:mm:ss[.xxx]的格式也支持

## 视频选项
* -b bitrate 设置比特率，缺省200kb/s
* -r fps 设置帧频 缺省25
* -s size 设置帧大小 格式为WXH 缺省160X128.下面的简写也可以直接使用：
* Sqcif 128X96 qcif 176X144 cif 252X288 4cif 704X576
* -aspect aspect 设置横纵比 4:3 16:9 或 1.3333 1.7777
* -croptop size 设置顶部切除带大小 像素单位
* -cropbottom size –cropleft size –cropright size
* -padtop size 设置顶部补齐的大小 像素单位
* -padbottom size –padleft size –padright size –padcolor color 设置补齐条颜色(hex,6个16进制的数，红:绿:兰排列，比如 000000代表黑色)
* -vn 不做视频记录
* -bt tolerance 设置视频码率容忍度kbit/s
* -maxrate bitrate设置最大视频码率容忍度
* -minrate bitreate 设置最小视频码率容忍度
* -bufsize size 设置码率控制缓冲区大小
* -vcodec codec 强制使用codec编解码方式。如果用copy表示原始编解码数据必须被拷贝。
* -sameq 使用同样视频质量作为源（VBR）
* -pass n 选择处理遍数（1或者2）。两遍编码非常有用。第一遍生成统计信息，第二遍生成精确的请求的码率
* -passlogfile file 选择两遍的纪录文件名为file

### 高级视频选项
* -g gop_size 设置图像组大小
* -intra 仅适用帧内编码
* -qscale q 使用固定的视频量化标度(VBR)
* -qmin q 最小视频量化标度(VBR)
* -qmax q 最大视频量化标度(VBR)
* -qdiff q 量化标度间最大偏差 (VBR)
* -qblur blur 视频量化标度柔化(VBR)
* -qcomp compression 视频量化标度压缩(VBR)
* -rc_init_cplx complexity 一遍编码的初始复杂度
* -b_qfactor factor 在p和b帧间的qp因子
* -i_qfactor factor 在p和i帧间的qp因子
* -b_qoffset offset 在p和b帧间的qp偏差
* -i_qoffset offset 在p和i帧间的qp偏差
* -rc_eq equation 设置码率控制方程 默认tex^qComp
* -rc_override override 特定间隔下的速率控制重载
* -me method 设置运动估计的方法 可用方法有 zero phods log x1 epzs(缺省) full
* -dct_algo algo 设置dct的算法 可用的有 0 FF_DCT_AUTO 缺省的DCT 1 FF_DCT_FASTINT 2 FF_DCT_INT 3 FF_DCT_MMX 4 FF_DCT_MLIB 5 FF_DCT_ALTIVEC
* -idct_algo algo 设置idct算法。可用的有 0 FF_IDCT_AUTO 缺省的IDCT 1 FF_IDCT_INT 2 FF_IDCT_SIMPLE 3 FF_IDCT_SIMPLEMMX 4 FF_IDCT_LIBMPEG2MMX 5 FF_IDCT_PS2 6 FF_IDCT_MLIB 7 FF_IDCT_ARM 8 * FF_IDCT_ALTIVEC 9 FF_IDCT_SH4 10 FF_IDCT_SIMPLEARM
* -er n 设置错误残留为n 1 FF_ER_CAREFULL 缺省 2 FF_ER_COMPLIANT 3 FF_ER_AGGRESSIVE 4 FF_ER_VERY_AGGRESSIVE
* -ec bit_mask 设置错误掩蔽为bit_mask,该值为如下值的位掩码 1 FF_EC_GUESS_MVS (default=enabled) 2 FF_EC_DEBLOCK (default=enabled)
* -bf frames 使用frames B 帧，支持mpeg1,mpeg2,mpeg4
* -mbd mode 宏块决策 0 FF_MB_DECISION_SIMPLE 使用mb_cmp 1 FF_MB_DECISION_BITS 2 FF_MB_DECISION_RD
* -4mv 使用4个运动矢量 仅用于mpeg4
* -part 使用数据划分 仅用于mpeg4
* -bug param 绕过没有被自动监测到编码器的问题
* -strict strictness 跟标准的严格性
* -aic 使能高级帧内编码 h263+
* -umv 使能无限运动矢量 h263+
* -deinterlace 不采用交织方法
* -interlace 强迫交织法编码仅对mpeg2和mpeg4有效。当你的输入是交织的并且你想要保持交织以最小图像损失的时候采用该选项。可选的方法是不交织，但是损失更大
* -psnr 计算压缩帧的psnr
* -vstats 输出视频编码统计到vstats_hhmmss.log
* -vhook module 插入视频处理模块 module 包括了模块名和参数，用空格分开

## 音频选项
* -ab bitrate 设置音频码率
* -ar freq 设置音频采样率
* -ac channels 设置通道 缺省为1
* -an 不使能音频纪录
* -acodec codec 使用codec编解码

### 音频/视频捕获选项
* -vd device 设置视频捕获设备。比如/dev/video0
* -vc channel 设置视频捕获通道 DV1394专用
* -tvstd standard 设置电视标准 NTSC PAL(SECAM)
* -dv1394 设置DV1394捕获
* -av device 设置音频设备 比如/dev/dsp

## 高级选项
* -map file:stream 设置输入流映射
* -debug 打印特定调试信息
* -benchmark 为基准测试加入时间
* -hex 倾倒每一个输入包
* -bitexact 仅使用位精确算法 用于编解码测试
* -ps size 设置包大小，以bits为单位
* -re 以本地帧频读数据，主要用于模拟捕获设备
* -loop 循环输入流（只工作于图像流，用于ffserver测试）


### 常用命令
    ```
    C:\Users\wq\Desktop\ffmpeg>ffmpeg  -i 100.mp4  -q  1  -c copy 101.mp4

    #设置输出文件以64千比特/秒的视频比特率:
    ffmpeg -i input.avi -b：V 64K -bufsize 64K output.avi

    #要强制输出文件为24 fps的帧速率：
    ffmpeg -i input.avi -r 24 output.avi

    #要强制输入文件的帧频（仅对原始格式有效），以1 FPS读入文件，以每秒24帧的帧速率输出：
    ffmpeg -r 1 -i input.m2v -r 24 output.avi

    #format 选项可能需要指定，对于原始输入文件。

    ```

1. 分离视频音频流
    ffmpeg -i input_file -vcodec copy -an output_file_video　　//分离视频流
    ffmpeg -i input_file -acodec copy -vn output_file_audio　　//分离音频流
    ffmpeg -i target.mp4 -vcodec libx264 -an target.h264    // 将target.mp4分离出没有声音的h264编码的视频文件
    ffmpeg -i target.mp4 -acodec aac target.aac    //  // 将target.mp4分离出aac编码的音频文件
    ffmpeg -i target.mp4 -c:v libx264 -c:a aac -strict -2 -f hls target.m3u8  // 将mp4文件切为ts视频段，并生成m3u8文件
2. 视频解复用
    ffmpeg –i test.mp4 –vcodec copy –an –f m4v test.264
    ffmpeg –i test.avi –vcodec copy –an –f m4v test.264
3. 视频转码
    ffmpeg –i test.mp4 –vcodec h264 –s 352*278 –an –f m4v test.264              //转码为码流原始文件
    ffmpeg –i test.mp4 –vcodec h264 –bf 0 –g 25 –s 352*278 –an –f m4v test.264  //转码为码流原始文件
    ffmpeg –i test.avi -vcodec mpeg4 –vtag xvid –qsame test_xvid.avi            //转码为封装文件
    //-bf B帧数目控制，-g 关键帧间隔控制，-s 分辨率控制

    ffmpeg -i target.h264 -vcodec h264 -i target.aac -acodec copy target.ts
4. 视频封装

    ffmpeg –i video_file –i audio_file –vcodec copy –acodec copy output_file
5. 视频剪切
    ffmpeg –i test.avi –r 1 –f image2 image-%3d.jpeg        //提取图片
    ffmpeg -ss 0:1:30 -t 0:0:20 -i input.avi -vcodec copy -acodec copy output.avi    //剪切视频
    //-r 提取图像的频率，-ss 开始时间，-t 持续时间
6. 视频录制
    ffmpeg –i rtsp://192.168.3.205:5555/test –vcodec copy out.avi
7. YUV序列播放
    ffplay -f rawvideo -video_size 1920x1080 input.yuv
8. YUV序列转AVI
    ffmpeg –s w*h –pix_fmt yuv420p –i input.yuv –vcodec mpeg4 output.avi
9. 将文件当做直播送至live
    ffmpeg -re -i localFile.mp4 -c copy -f flv rtmp://server/live/streamName
10. 将直播媒体保存至本地文件
    ffmpeg -i rtmp://server/live/streamName -c copy dump.flv
11. 将其中一个直播流，视频改用h264压缩，音频不变，送至另外一个直播服务流
    ffmpeg -i rtmp://server/live/originalStream -c:a copy -c:v libx264 -vpre slow -f flv rtmp://server/live/h264Stream 
12. 将其中一个直播流，视频改用h264压缩，音频改用faac压缩，送至另外一个直播服务流
    ffmpeg -i rtmp://server/live/originalStream -c:a libfaac -ar 44100 -ab 48k -c:v libx264 -vpre slow -vpre baseline -f flv rtmp://server/live/h264Stream
13. 将其中一个直播流，视频不变，音频改用faac压缩，送至另外一个直播服务流
    ffmpeg -i rtmp://server/live/originalStream -acodec libfaac -ar 44100 -ab 48k -vcodec copy -f flv rtmp://server/live/h264_AAC_Stream
14. 将一个高清流，复制为几个不同视频清晰度的流重新发布，其中音频不变
    ffmpeg -re -i rtmp://server/live/high_FMLE_stream -acodec copy -vcodec x264lib -s 640×360 -b 500k -vpre medium -vpre baseline rtmp://server/live/baseline_500k -acodec copy -vcodec x264lib -s 480×272 -b 300k -vpre medium -vpre baseline rtmp://server/live/baseline_300k -acodec copy -vcodec x264lib -s 320×200 -b 150k -vpre medium -vpre baseline rtmp://server/live/baseline_150k -acodec libfaac -vn -ab 48k rtmp://server/live/audio_only_AAC_48k
15. 功能一样，只是采用-x264opts选项
    ffmpeg -re -i rtmp://server/live/high_FMLE_stream -c:a copy -c:v x264lib -s 640×360 -x264opts bitrate=500:profile=baseline:preset=slow rtmp://server/live/baseline_500k -c:a copy -c:v x264lib -s 480×272 -x264opts bitrate=300:profile=baseline:preset=slow rtmp://server/live/baseline_300k -c:a copy -c:v x264lib -s 320×200 -x264opts bitrate=150:profile=baseline:preset=slow rtmp://server/live/baseline_150k -c:a libfaac -vn -b:a 48k rtmp://server/live/audio_only_AAC_48k
16. 将当前摄像头及音频通过DSSHOW采集，视频h264、音频faac压缩后发布
    ffmpeg -r 25 -f dshow -s 640×480 -i video=”video source name”:audio=”audio source name” -vcodec libx264 -b 600k -vpre slow -acodec libfaac -ab 128k -f flv rtmp://server/application/stream_name
17. 将一个JPG图片经过h264压缩循环输出为mp4视频
    ffmpeg.exe -i INPUT.jpg -an -vcodec libx264 -coder 1 -flags +loop -cmp +chroma -subq 10 -qcomp 0.6 -qmin 10 -qmax 51 -qdiff 4 -flags2 +dct8x8 -trellis 2 -partitions +parti8x8+parti4x4 -crf 24 -threads 0 -r 25 -g 25 -y OUTPUT.mp4
18. 将普通流视频改用h264压缩，音频不变，送至高清流服务(新版本FMS live=1)
    ffmpeg -i rtmp://server/live/originalStream -c:a copy -c:v libx264 -vpre slow -f flv “rtmp://server/live/h264Stream live=1〃

### 官方文档

[http://ffmpeg.org/ffmpeg.html](http://ffmpeg.org/ffmpeg.html)