#!/usr/bin/python

import re

h = open("AoT_trash_talk.txt")
c = [ x[:-1] for x in h.readlines() ]
h.close()

for line in c:
  num = (re.findall(r"(\s+)", line))
  if num:
    print(int(''.join(num).replace(' ', '0').replace('\t', '1'), 2))
