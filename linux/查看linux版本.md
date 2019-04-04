
　　1、# uname －a   （Linux查看版本当前操作系统内核信息）
 
　　Linux localhost.localdomain 2.4.20-8 #1 Thu Mar 13 17:54:28 EST 2003 i686 athlon i386 GNU/Linux
 
　　2、# cat /proc/version （Linux查看当前操作系统版本信息）
 
      Linux version 2.4.20-8 (bhcompile@porky.devel.redhat.com)
      (gcc version 3.2.2 20030222 (Red Hat Linux 3.2.2-5)) #1 Thu Mar 13 17:54:28 EST 2003
 
　　3、# cat /etc/issue  或cat /etc/redhat-release（Linux查看版本当前操作系统发行版信息）
 
　　Red Hat Linux release 9 (Shrike)

　　4、# cat /proc/cpuinfo （Linux查看cpu相关信息，包括型号、主频、内核信息等）
 
　　processor        : 0
     vendor_id         : AuthenticAMD
　　cpu family        : 15
　　model             : 1
　　model name      : AMD A4-3300M APU with Radeon(tm) HD Graphics
　　stepping         : 0
　　cpu MHz          : 1896.236
　　cache size       : 1024 KB
　　fdiv_bug         : no
　　hlt_bug          : no
　　f00f_bug        : no
　　coma_bug      : no
　　fpu                : yes
　　fpu_exception   : yes
　　cpuid level      : 6
　　wp                : yes
　　flags             : fpu vme de pse tsc msr pae mce cx8 apic sep mtrr pge mca cmov pat pse36 clflush mmx fxsr
                           sse sse2 syscall mmxext lm 3dnowext 3dnow
　　bogomips      : 3774.87
 
　　5、# getconf LONG_BIT  （Linux查看版本说明当前CPU运行在32bit模式下， 但不代表CPU不支持64bit）
 
　　32
 
　　6、# lsb_release -a

7. cat /etc/redhat-release