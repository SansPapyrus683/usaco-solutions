# 2019 jan bronze
SHELL_NUM = 3


def points(start: int, moves):
    shells = [False for _ in range(SHELL_NUM)]
    shells[start] = True
    total = 0
    for m in moves:
        shells[m[0]], shells[m[1]] = shells[m[1]], shells[m[0]]
        total += shells[m[2]]
    return total


swaps = []
with open('shell.in') as read:
    for _ in range(int(read.readline())):
        swaps.append([int(i) - 1 for i in read.readline().split()])
        # 1/20/2021- bruh why they still using python 3.4 lol
        # assert all(0 <= c < SHELL_NUM for c in swaps[-1]), f"there's only {SHELL_NUM} shells ok?"

maxPoints = max(points(s, swaps) for s in range(SHELL_NUM))
print(maxPoints)
print(maxPoints, file=open('shell.out', 'w'))
