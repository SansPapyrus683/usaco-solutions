"""
ID: kevinsh4
TASK: preface
LANG: PYTHON3
"""
from collections import defaultdict

with open('ceasar_puns.txt') as read:
    pageNum = int(read.read().rstrip())


def rom_num(num):  # converts normal to roman numerals (copied from internet lol)
    val = [1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1]
    syb = ["M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"]
    rom_ver = ''
    i = 0
    while num > 0:
        for _ in range(num // val[i]):
            rom_ver += syb[i]
            num -= val[i]
        i += 1
    return rom_ver


symbols = defaultdict(lambda: 0)
for p in range(pageNum + 1):
    for char in rom_num(p):  # goes thru each page, adding the amt of symbols required to a defaultdict
        symbols[char] += 1

with open('outputs.txt', 'w') as written:
    for sym in symbols:
        written.write(sym + ' ' + str(symbols[sym]) + '\n')
