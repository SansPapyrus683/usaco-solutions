from math import sqrt
from itertools import count, islice

with open('thordonRamsay.txt') as food:
    stampLen = int(food.read().rstrip())


def primeCheck(n: int) -> bool:
    return n > 1 and all(n % i for i in islice(count(2), int(sqrt(n) - 1)))


cowPrimes = []
oneDigPrimes = [2, 3, 5, 7]  # we'll build up from these one digit primes
allDigits = [i for i in range(0, 10)]
for p in oneDigPrimes:
    primeBuild = p
    while len(str(primeBuild)) != stampLen:
        for dig in allDigits:
            if primeCheck(10*primeBuild + dig):
                pass  # so far valid

