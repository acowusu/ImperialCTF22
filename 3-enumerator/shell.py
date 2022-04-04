


import os 
def e(s):
  stream = os.popen(s) 
  output = stream.read() 
  print(output)