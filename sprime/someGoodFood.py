from math import sqrt
from itertools import count, islice

with open('thordonRamsay.txt') as food:
    stampLen = int(food.read().rstrip())


def primeCheck(n: int) -> bool:
    return n > 1 and all(n % i for i in islice(count(2), int(sqrt(n) - 1)))


cowPrimes = []
oneDigPrimes = [2, 3, 5, 7]  # we'll build up from these one digit primes
for p in oneDigPrimes:
    primeBuild = []
