#!/usr/bin/env python
# coding:utf-8

import getopt
import os
import platform
import sys
#from you_get.common import *

# import requests
a = __file__  # __file__全局变量获取当前文件路径
# print(a)
b = os.path.dirname(a)  # 获取文件当前目录
# print(b)
c = "Lib"  # 自定义文件目录名称
c_you_get = "you_get"

d = os.path.join(b, c)  # 将获取文件当前目录，与自定义文件目录名称，拼接成完整的路径
# print(d)
# print("\n")
sys.path.append(d)  # 将拼接好的路径，添加到解释器模块路径中
#print("111",d)

from you_get.version import script_name, __version__
from you_get.util import git, log


def yg_main(**kwargs):
    """Main entry point.
    you-get (legacy)
    """
    #from you_get.common import *
    from you_get.common import main
    main(**kwargs)

# if __name__ == '__main__':
#     sys.exit(yg_main())

yg_main()
