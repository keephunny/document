
### 启动
```
@echo off
set nginx_home=./
 #
echo Starting nginx...
RunHiddenConsole %nginx_home%/nginx.exe -p %nginx_home%

```

### 停止
```
@echo off  
echo Stopping nginx...    
taskkill /F /IM nginx.exe > nul
exit  
```

### 重启
@echo off
echo Stopping nginx……
taskkill /F /IM nginx.exe >nul

@echo off
set nginx_home=./

echo Starting nginx...
RunHiddenConsole %nginx_home%/nginx.exe -p %nginx_home%


[RunHiddenConsole.exe](./resources/RunHiddenConsole.rar) 需要放在当前目前下