"""
ID: kevinsh4
TASK: ride
LANG: PYTHON3
"""
from operator import mul
from functools import reduce

MOD = 47

with open('rescueET.txt') as read:
    comet = [ord(c) - 64 for c in read.readline().rstrip()]
    group = [ord(c) - 64 for c in read.readline().rstrip()]
print(comet, group)
with open('outputs.txt', 'w') as written:
    if reduce(mul, comet) % MOD == reduce(mul, group) % MOD:
        print('GO')
        written.write('GO\n')
    else:
        print('STAY')
        written.write('STAY\n')
