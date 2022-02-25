### 安装node

brew install node

npm是随着nodejs安装一并安装的、更新npm，也可以使用npm命令行

sudo npm install npm -g



### npm相关命令行介绍

npm install -g xx      # 全局安装，安装在/usr/local/lib

npm list -g        # 查看所有全局安装的模块

npm list grunt  # 查看某个模块的版本号

npm uninstall xxx # 卸载模块

npm update xxx # 更新模块

npm init # 创建模块

npm public #发布模块

package.json 文件说明

package.json 类似于cocopods中的podspec文件,是对某指定包、模块做的描述。