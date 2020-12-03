"""
ID: kevinsh4
TASK: runround
LANG: PYTHON3
"""
from sys import exit

with open('newLife.txt') as read:
    startFrom = int(read.read().rstrip()) + 1  # we want strictly greater than so +1


def checkRun(n):
    n = [int(x) for x in list(str(n))]
    indexPos = 0
    visited = set()
    for i in range(len(n)):
        indexPos = (n[indexPos] + indexPos) % len(n)
        if n[indexPos] in visited:
            return False
        visited.add(n[indexPos])
    return True


while True:
    if len(set(list(str(startFrom)))) != len(list(str(startFrom))):  # if has repeating digits, don't even bother
        startFrom += 1
        continue
    if checkRun(startFrom):
        with open('outputs.txt', 'w') as written:
            written.write(str(startFrom))
            exit()
    startFrom += 1
