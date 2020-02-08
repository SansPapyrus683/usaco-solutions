"""
ID: kevinsh4
TASK: pprime
LANG: PYTHON3
"""
from math import sqrt
from itertools import count, islice

with open('inputtupin.txt') as inp:
    minVal, maxVal = [int(i) for i in inp.read().rstrip().split()]
    palLengths = [i for i in range(len(str(minVal)), len(str(maxVal)) + 1)]


def primeCheck(n: int) -> bool:  # same one i used for sprime
    return n > 1 and all(n % i for i in islice(count(2), int(sqrt(n) - 1)))


def getPalindrome():
    yield 0
    for digits in count(1):
        first = 10 ** ((digits - 1) // 2)
        for s in map(str, range(first, 10 * first)):
            yield int(s + s[-(digits % 2) - 1::-1])


def allPalindromes(minP, maxP):
    palindromeGenerator = getPalindrome()
    palindromeList = []
    for palindrome in palindromeGenerator:
        if palindrome > maxP:
            break
        if palindrome < minP:
            continue
        palindromeList.append(palindrome)
    return palindromeList


written = open('outputs.txt', 'w')
for pal in allPalindromes(minVal, maxVal):
    if primeCheck(pal):
        written.write(str(pal) + '\n')
