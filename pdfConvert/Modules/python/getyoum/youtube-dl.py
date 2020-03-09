#!/usr/bin/env python
# coding:utf-8
#from __future__ import unicode_literals

# Execute with
# $ python youtube_dl/__main__.py (2.6+)
# $ python -m youtube_dl          (2.7+)

import sys
import os

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


def ydl_main():
    from youtube_dl import main
    main()

# if __name__ == '__main__':
#     #youtube_dl.main()
#     ydl_main()

ydl_main()