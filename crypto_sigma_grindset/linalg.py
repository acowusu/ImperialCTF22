# this file provides code for working with matrices/vectors over the
# integers mod P

from copy import deepcopy, copy
from random import randint
from base64 import b64decode, b64encode

class Matrix:
    def __init__(self, rows, P):
        self.rows = rows[::]
        self.m, self.n, self.P = len(rows), len(rows[0]), P
        for r in rows:
            assert len(r) == self.n

        for i in range(self.m):
            for j in range(self.n):
                self.rows[i][j] %= P

    def encode(self):
        assert self.P <= 256 and self.n == self.m
        resp = [0 for _ in range(self.n * self.n)]
        for i in range(self.n):
            for j in range(self.n):
                resp[i * self.n + j] = self[i][j]
        return b64encode(bytes(resp))
    
    def decode(self, s):
        assert self.P <= 256 and self.n == self.m
        b = b64decode(s)
        assert len(b) == self.n *  self.n

        for i in range(self.n):
            for j in range(self.n):
                self.rows[i][j] = b[i * self.n + j]

    def __getitem__(self, i):
        return self.rows[i]
    
    def __add__(self, o):
        assert o.m == self.m and o.n == self.n
        R = [[(self[i][j] + o[i][j]) % self.P for j in range(self.n)] for i in range(self.m)]
        return Matrix(R, self.P)
    
    def __mul__(self, o):
        assert self.n == o.m
        r = []

        for x in range(self.m):
            row = []
            for y in range(o.n):
                row.append(sum([self[x][z] * o[z][y] for z in range(o.m)]) % self.P)
            r.append(row)

        return Matrix(r, self.P)
    
    def __str__(self):
        if self.n == 1:
            return str([r[0] for r in self.rows])
        return str(self.rows)
    
    def inverse(self):
        M = deepcopy(self.rows)

        I = identity_matrix(self.m, self.P)
        for i in range(len(M)):
            M[i] += I[i]
        
        row = 0
        for column in range(self.m):
            if M[row][column] == 0:
                for t_row in range(row + 1, self.m):
                    if M[t_row][column] != 0:
                        temp = copy(M[t_row])
                        M[t_row] = copy(M[row])
                        M[row] = temp

                assert M[row][column] != 0

            for t_row in range(row + 1, self.m):
                if M[t_row][column] == 1:
                    M[t_row] = [(a + b) % self.P for a,b in zip(M[t_row], M[row])]
            for t_row in range(row):
                if M[t_row][column] == 1:
                    M[t_row] = [(a + b) % self.P for a,b in zip(M[t_row], M[row])]
        
        R = []
        for row in M:
            R.append(row[self.m:])
        return Matrix(R, self.P)
    
    def invertible(self):
        try:
            self.inverse()
            return True
        except:
            return False

def identity_matrix(n, P):
    M = [[0] * n for _ in range(n)]
    for i in range(n):
        M[i][i] = 1
    return Matrix(M, P)

def random_matrix(m, n, P):
    M = [[randint(0, P - 1) for _ in range(n)] for _ in range(m)]
    return Matrix(M, P)

def make_vector(v, P):
    return Matrix([[n] for n in v], P)