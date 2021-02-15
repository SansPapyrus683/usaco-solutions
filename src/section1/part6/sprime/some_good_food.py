"""
ID: kevinsh4
TASK: sprime
LANG: PYTHON3
"""
from math import sqrt
from itertools import count, islice

with open('thordon_ramsay.txt') as food:
    stamp_len = int(food.read().rstrip())


def prime_check(n: int) -> bool:
    return n > 1 and all(n % i for i in islice(count(2), int(sqrt(n) - 1)))


prime_digits = tuple([i for i in range(10) if i % 2 and i != 5])
prime_process = [2, 3, 5, 7]  # start with a bunch of one digit primes (soon to be)
for i in range(stamp_len - 1):  # we already have one digit, so just stampLen - 1
    promising_primes = []  # our "queue"
    for process in prime_process:
        for dig in prime_digits:
            if prime_check(10 * process + dig):  # if adding the digit is still valid for the req
                promising_primes.append(10 * process + dig)
    prime_process = promising_primes


with open('outputs.txt', 'w') as written:
    written.write('\n'.join([str(i) for i in prime_process]) + '\n')
