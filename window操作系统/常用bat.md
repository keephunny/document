

### 按端口号杀进程
```
    @echo off
    setlocal enabledelayedexpansion
    for /f "delims= tokens=1" %%i in ('netstat -aon ^| findstr "0.0.0.0:8085"') do (
    set a=%%i
    taskkill /f /pid "!a:~71,5!"
    )
    pause
```

### 按java程序名杀进程
```
    @echo off
    set program=com.spring.project.web.api.Application
    echo program: %program%
    for /f "usebackq tokens=1-2" %%a in (`jps -l ^| findstr %program%`) do (
        set pid=%%a
        set image_name=%%b
        echo %pid%
        echo %image_name%
    )
    if not defined pid (
        echo "process" %program% "does not exists"
    ) else (
        echo prepare to kill %image_name%
        echo "start kill" %pid% ...
        taskkill /f /pid %pid%
    )
```