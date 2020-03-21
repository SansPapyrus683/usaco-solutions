"""
ID: kevinsh4
TASK: sprime
LANG: PYTHON3
"""
from math import sqrt
from itertools import count, islice

with open('thordonRamsay.txt') as food:
    stampLen = int(food.read().rstrip())


def primeCheck(n: int) -> bool:
    return n > 1 and all(n % i for i in islice(count(2), int(sqrt(n) - 1)))


primeDigits = tuple([i for i in range(10) if i % 2 and i != 5])
primeProcess = [2, 3, 5, 7]  # start with a bunch of one digit primes (soon to be)
for i in range(stampLen - 1):  # we already have one digit, so just stampLen - 1
    promisingPrimes = []  # our "queue"
    for process in primeProcess:
        for dig in primeDigits:
            if primeCheck(10 * process + dig):  # if adding the digit is still valid for the req
                promisingPrimes.append(10 * process + dig)
    primeProcess = promisingPrimes


with open('outputs.txt', 'w') as written:
    written.write('\n'.join([str(i) for i in primeProcess]) + '\n')
