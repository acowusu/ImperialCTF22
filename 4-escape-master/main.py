#%%

with open("main.txt") as f:
  file = f.read()
  file = file.replace(" " , "")
  lines = file.split('\n')



lines = list(filter(None, lines))

# file.spl
#%%
lines

#%%
res = ""
current = {"x":0, "y":0}
while lines[current["y"]][current["x"]] != "H":
  if current["x"] + 1 < len(lines[current["y"]]) and lines[current["y"]][current["x"]+ 1 ] != "#":
    current["x"] += 1
    res = res + "R"
  elif  current["y"] < len(lines) and lines[current["y"] + 1][current["x"]] != "#":
    current["y"] += 1
    res = res + "D"
  elif  current["x"] -1 > 0 and lines[current["y"]][current["x"]-1] != "#":
    current["y"] -= 1
    res = res + "L"
  elif  current["y"] -1 < 0 and lines[current["y"]-1][current["x"]] != "#":
    current["y"] -= 1
    res = res + "U"
  print(res)

 #H#
 #H#
  #





# %%
