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

result = 'GO' if reduce(mul, comet) % MOD == reduce(mul, group) % MOD else 'STAY'
print(result)
print(result, file=open('outputs.txt', 'w'))
