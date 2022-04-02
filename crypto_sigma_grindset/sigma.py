from linalg import *
from math import floor, sqrt
from base64 import b64decode, b64encode
from secrets import flag, mask

P = 227

import sys
import socket
import signal
import threading

### Server code
IP = '0.0.0.0'
PORT = 9009
BUFF = 1024

server = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
server.bind((IP, PORT))
server.listen()

def signal_handler(signal, frame):
	print("\nShutting down server")
	server.close()
	sys.exit(0)

signal.signal(signal.SIGINT, signal_handler)

print("[*] Listening on %s:%d" % (IP, PORT))
### Server code

# legends never die
# ... sigmas get UPGRADED
class Schnorr:
    def __init__(self, s, o, g, P):
        self.s, self.o, self.g, self.P = s, o, g, P
        self.l = int(floor(sqrt(len(s))))
        assert pow(self.l,2) == len(s)
        self.makeSecret()
        
    def makeSecret(self):
        self.S = Matrix(
            [[(ord(c) + self.o) for c in self.s[i:i + self.l]] for i in range(0, len(self.s), self.l)],
            P)

    def startSession(self):
        self.R = random_matrix(self.l, self.l, self.P)

    def answerChallenge(self, C):
        assert C.invertible()
        return self.R + C * self.S
    
    def commitment(self):
        return self.R * self.g
    
    def pubkey(self):
        return self.S * self.g
        

# the trillionaire mindset i'm on 24/7
g = make_vector((72, 85, 83, 84, 76, 69), P)

def prove(client):
    SCHEME = Schnorr(flag, mask, g, P)
    SCHEME.startSession()
    client.send(f"Proving that I know the matrix which turns: {g} into: {SCHEME.pubkey()}\n".encode())
    client.send(f"My commitment is: {SCHEME.commitment()}\n".encode())
    
    client.send(f"Enter your {SCHEME.l}x{SCHEME.l} challenge matrix:\n> ".encode())
    chal = client.recv(BUFF).decode('utf-8')
    C = Matrix([[0] * SCHEME.l for _ in range(SCHEME.l)], P)
    C.decode(chal)
    
    client.send(b"My challenge response is:\n")
    M = SCHEME.answerChallenge(C)
    client.send(M.encode() + b"\n")
    
    client.send(b"Hope you believe me now ;)\n")
    client.close()

while True:
	client, addr = server.accept()
	print("[*] Incoming connection from %s:%d" % (addr[0], addr[1]))

	client_handler = threading.Thread(target = prove, args = (client,))
	client_handler.start()

    