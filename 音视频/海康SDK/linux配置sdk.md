* CH-HCNetSDKV6.0.2.2_build20181213_Linux64
* centos7.2

1. 上传lib
拷贝lib到linux服务器/usr/local/hiksdk/
```
    HCNetSDKCom/
        libanalyzedata.so
        libHCAlarm.so
        libHCCoreDevCfg.so
        libHCDisplay.so
        libHCGeneralCfgMgr.so
        libHCIndustry.so
        libHCPlayBack.so
        libHCPreview.so
        libHCVoiceTalk.so
        libiconv2.so
        libStreamTransClient.so
        libSystemTransform.so
    libAudioRender.so
    libcrypto.so
    libcrypto.so.1.0.0
    libHCCore.so
    libhcnetsdk.so
    libhpr.so
    libPlayCtrl.so
    libssl.so
```
2. 配置lib
```
    [root@localhost]# vim /etc/ld.so.conf
        #添加海康sdk
        /usr/local/hiksdk/
        /usr/local/hiksdk/HCNetSDKCom/
    #配置生效
    [root@localhost]# ldconfig
    #查看so文件是否加载
    [root@localhost]# ldconfig  -p  | grep hcnetsdk  
```    
3. 配置环境变量
```
    [root@localhost]# vim /etc/profile
        export LD_LIBRARY=$LD_LIBRARY:/usr/local/hiksdk/HCNetSDKCom:/usr/local/hiksdk/
    [root@localhost]# source /etc/profile
```    