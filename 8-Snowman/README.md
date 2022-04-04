# 8 - Snowman
CTF name: Imperial CTF 22 Qualifiers
Challenge name: Snowman
Challenge description: What does snow have to do with Attack on Titan?! 
Challenge category: Stego
Challenge points: 50 (Easy)

We see that we are given a text file.
After reading the text file we realised it has a lot of strange whitespace which suggests the message is hidden in the whitespace.

Snowman steganography suggests Stegsnow?

Stegsnow requires a password to decode - maybe AoT
(From the hint?)

So we tried to decode it using different passwords using stegsnow
`stegsnow -C -p 'AoT' AoT_trash_talk.txt`

After around 50 attempts at passwords and the hints being released we realise origins=atuthor

Author of Attack on Titan is Hajime Isayama

xyz000th attempt:

!(https://user-images.githubusercontent.com/51130635/161524933-ef4f72ef-3247-48dc-80c9-fdebfe2bd1bc.jpeg)

Letsssgoooo
