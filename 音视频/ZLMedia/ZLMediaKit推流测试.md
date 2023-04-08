ZLMediaKit支持rtsp/rtmp/rtp推流，一般通常使用obs/ffmpeg推流测试，其中FFmpeg推流命令支持以下：

1、使用rtsp方式推流
# h264推流
ffmpeg -re -i "/path/to/test.mp4" -vcodec h264 -acodec aac -f rtsp -rtsp_transport tcp rtsp://127.0.0.1/live/test
# h265推流
ffmpeg -re -i "/path/to/test.mp4" -vcodec h265 -acodec aac -f rtsp -rtsp_transport tcp rtsp://127.0.0.1/live/test
2、使用rtmp方式推流
#如果未安装FFmpeg，你也可以用obs推流
ffmpeg -re -i "/path/to/test.mp4" -vcodec h264 -acodec aac -f flv rtmp://127.0.0.1/live/test
# RTMP标准不支持H265,但是国内有自行扩展的，如果你想让FFmpeg支持RTMP-H265,请按照此文章编译：https://github.com/ksvc/FFmpeg/wiki/hevcpush
3、使用rtp方式推流
# h264推流
ffmpeg -re -i "/path/to/test.mp4" -vcodec h264 -acodec aac -f rtp_mpegts rtp://127.0.0.1:10000
# h265推流
ffmpeg -re -i "/path/to/test.mp4" -vcodec h265 -acodec aac -f rtp_mpegts rtp://127.0.0.1:10000



2023-04-04 11:34:47.225 D [MediaServer] [106480-event poller 0] RtspSession.cpp:61 ~RtspSession | 4-18(192.168.121.100:3575) 
2023-04-04 11:34:47.231 D [MediaServer] [106480-event poller 0] RtspSession.cpp:55 RtspSession | 5-18(192.168.121.100:3576) 
2023-04-04 11:34:47.233 W [MediaServer] [106480-event poller 0] RtspSession.cpp:67 onError | 5-18(192.168.121.100:3576) RTSP播放器(__defaultVhost__//)断开:end of file,耗时(s):0
2023-04-04 11:34:47.233 D [MediaServer] [106480-event poller 0] RtspSession.cpp:61 ~RtspSession | 5-18(192.168.121.100:3576) 
2023-04-04 11:34:47.685 I [MediaServer] [106480-event poller 0] MediaSource.cpp:525 emitEvent | 媒体注册:hls://__defaultVhost__/live/test
2023-04-04 11:34:47.685 I [MediaServer] [106480-event poller 0] MediaSource.cpp:525 emitEvent | 媒体注销:hls://__defaultVhost__/live/test
2023-04-04 11:34:47.685 I [MediaServer] [106480-event poller 0] MediaSource.cpp:525 emitEvent | 媒体注销:ts://__defaultVhost__/live/test
2023-04-04 11:34:47.686 I [MediaServer] [106480-event poller 0] MediaSource.cpp:525 emitEvent | 媒体注销:rtmp://__defaultVhost__/live/test
2023-04-04 11:34:47.686 I [MediaServer] [106480-event poller 0] MediaSource.cpp:525 emitEvent | 媒体注销:fmp4://__defaultVhost__/live/test
2023-04-04 11:34:47.686 I [MediaServer] [106480-event poller 0] MediaSource.cpp:525 emitEvent | 媒体注销:rtsp://__defaultVhost__/live/test
2023-04-04 11:34:47.686 W [MediaServer] [106480-event poller 0] RtspSession.cpp:67 onError | 2-17(192.168.121.100:3573) RTSP推流器(__defaultVhost__/live/test)断开:recv teardown request,耗时(s):6
2023-04-04 11:34:47.686 D [MediaServer] [106480-event poller 0] RtspSession.cpp:61 ~RtspSession | 2-17(192.168.121.100:3573) 
2023-04-04 11:35:03.087 D [MediaServer] [106480-event poller 0] RtspSession.cpp:55 RtspSession | 6-17(192.168.121.100:3583) 
2023-04-04 11:35:04.786 W [MediaServer] [106480-event poller 0] RtspSession.cpp:67 onError | 6-17(192.168.121.100:3583) RTSP播放器(//)断开:end of file,耗时(s):1
2023-04-04 11:35:04.786 D [MediaServer] [106480-event poller 0] RtspSession.cpp:61 ~RtspSession | 6-17(192.168.121.100:3583) 
