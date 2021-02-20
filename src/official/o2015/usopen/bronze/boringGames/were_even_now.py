# 2015 usopen bronze
GAME = 'BESIGOM'

letters = {c: [0, 0] for c in GAME}
with open('geteven.in') as read:
    for _ in range(int(read.readline())):
        letter, num = read.readline().split()
        letters[letter.upper()][int(num) % 2] += 1

possible = 0
for i in range(2 ** len(GAME)):
    parities = {}
    for v, s in enumerate(GAME):
        parities[s] = bool(i & 1 << v)  # true for odd, false for even
    factors = [
        parities['B'] ^ parities['I'],  # e and s aren't necessary bc they appear twice
        parities['G'] ^ parities['O'] ^ parities['E'] ^ parities['S'],
        parities['M']
    ]
    if any(not f for f in factors):
        total = 1
        for c, p in parities.items():
            total *= letters[c][p]
        possible += total

print(possible)
print(possible, file=open('geteven.out', 'w'))
