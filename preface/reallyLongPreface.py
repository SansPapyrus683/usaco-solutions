"""
ID: kevinsh4
TASK: preface
LANG: PYTHON3
"""
from collections import defaultdict

with open('ceasarPuns.txt') as read:
    pageNum = int(read.read().rstrip())


def romNum(num):  # converts normal to roman numerals
    val = [1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1]
    syb = ["M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"]
    romVer = ''
    i = 0
    while num > 0:
        for _ in range(num // val[i]):
            romVer += syb[i]
            num -= val[i]
        i += 1
    return romVer


symbols = defaultdict(lambda: 0)
for p in range(pageNum + 1):
    for char in romNum(p):  # goes thru each page, adding the amt of symbols required to a defaultdict
        symbols[char] += 1

with open('outputs.txt', 'w') as written:
    for sym in symbols:
        written.write(sym + ' ' + str(symbols[sym]) + '\n')
