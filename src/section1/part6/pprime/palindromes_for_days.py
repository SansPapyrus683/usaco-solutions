"""
ID: kevinsh4
TASK: pprime
LANG: PYTHON3
"""
from math import sqrt
from itertools import count, islice

with open('inputtupin.txt') as inp:
    min_val, max_val = [int(i) for i in inp.read().rstrip().split()]
    palLengths = [i for i in range(len(str(min_val)), len(str(max_val)) + 1)]


def prime_check(n: int) -> bool:  # same one y used for sprime
    return n > 1 and all(n % i for i in islice(count(2), int(sqrt(n) - 1)))


def get_palindrome():
    yield 0
    for digits in count(1):
        first = 10 ** ((digits - 1) // 2)
        for s in map(str, range(first, 10 * first)):
            yield int(s + s[-(digits % 2) - 1::-1])


def all_palindromes(min_p, max_p):
    palindrome_generator = get_palindrome()
    palindrome_list = []
    for palindrome in palindrome_generator:
        if palindrome > max_p:
            break
        if palindrome < min_p:
            continue
        palindrome_list.append(palindrome)
    return palindrome_list


written = open('outputs.txt', 'w')
for pal in all_palindromes(min_val, max_val):
    if prime_check(pal):
        written.write(str(pal) + '\n')
