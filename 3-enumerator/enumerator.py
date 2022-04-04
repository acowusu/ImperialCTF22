
#  file origionally located at  /enumerator_hjikmrfvyg/enumerator.py
from argon2 import PasswordHasher
from flask import Flask, session, redirect, url_for, request, render_template
from werkzeug.debug import DebuggedApplication
import requests
import random
import os

app = Flask(__name__)
app.secret_key = "qMUUE3lTQqTyMxxIadQ3"

@app.route('/', methods = ['GET', 'POST'])
def index():
  data = '''
        <h1> Continous Automatic Tested Enumerator </h1>
      <h3> Tired of having to browse so many websites in a single day? </h3>
      <p> Our enumeration service allows you to look up any page on the web from the confort of our own app. </p>
      <p> What are you waiting for? Start searching! </p>
      <form action = "" method = "post">
        <p> URL: <input type = text name = url /> </p>
        <p> <input type = submit value = SuperFastSearch /> <p>
      </form>
        '''
        
  if request.method == 'GET':
      return generate_page(data)

  if request.method == 'POST':
    url = request.form['url']
    if "file" in url:
      with open(url[7:]) as f:
        contents = f.readlines()
    else:
      r = requests.get(url)
      contents = r.text

    return generate_page(f"<div> {data} </div> <div> {contents} </div>")


def generate_page(data):
  return f"""
    <style>
      body {{
          background-color: #e0f9f9;
      }}
    </style>

    <div class="content">
      {data}
    </div>
    """

if __name__ == "__main__":
      port = int(os.environ.get('PORT', 5000))
      app.run(debug=True, host='0.0.0.0', port=port)
	

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


bootID

78c9aa53-8acf-414a-ac9c-c9d557d76d33





11:name=systemd:/docker/3e5f58710ee3415ec2a1536107725aff6f8c43702c1c4d6da6ba93144ed0c856
10:blkio:/docker/4f6679306bda216590c0f1c2cb51542bae83c644804ef38ee8cc39210fcaf2e9/docker/3e5f58710ee3415ec2a1536107725aff6f8c43702c1c4d6da6ba93144ed0c856
9:cpuset:/docker/4f6679306bda216590c0f1c2cb51542bae83c644804ef38ee8cc39210fcaf2e9/docker/3e5f58710ee3415ec2a1536107725aff6f8c43702c1c4d6da6ba93144ed0c856
8:pids:/docker/4f6679306bda216590c0f1c2cb51542bae83c644804ef38ee8cc39210fcaf2e9/docker/3e5f58710ee3415ec2a1536107725aff6f8c43702c1c4d6da6ba93144ed0c856
7:freezer:/docker/4f6679306bda216590c0f1c2cb51542bae83c644804ef38ee8cc39210fcaf2e9/docker/3e5f58710ee3415ec2a1536107725aff6f8c43702c1c4d6da6ba93144ed0c856
6:memory:/docker/4f6679306bda216590c0f1c2cb51542bae83c644804ef38ee8cc39210fcaf2e9/docker/3e5f58710ee3415ec2a1536107725aff6f8c43702c1c4d6da6ba93144ed0c856
5:cpu,cpuacct:/docker/4f6679306bda216590c0f1c2cb51542bae83c644804ef38ee8cc39210fcaf2e9/docker/3e5f58710ee3415ec2a1536107725aff6f8c43702c1c4d6da6ba93144ed0c856
4:hugetlb:/docker/4f6679306bda216590c0f1c2cb51542bae83c644804ef38ee8cc39210fcaf2e9/docker/3e5f58710ee3415ec2a1536107725aff6f8c43702c1c4d6da6ba93144ed0c856
3:perf_event:/docker/4f6679306bda216590c0f1c2cb51542bae83c644804ef38ee8cc39210fcaf2e9/docker/3e5f58710ee3415ec2a1536107725aff6f8c43702c1c4d6da6ba93144ed0c856
2:net_cls,net_prio:/docker/4f6679306bda216590c0f1c2cb51542bae83c644804ef38ee8cc39210fcaf2e9/docker/3e5f58710ee3415ec2a1536107725aff6f8c43702c1c4d6da6ba93144ed0c856
1:devices:/docker/4f6679306bda216590c0f1c2cb51542bae83c644804ef38ee8cc39210fcaf2e9/docker/3e5f58710ee3415ec2a1536107725aff6f8c43702c1c4d6da6ba93144ed0c856