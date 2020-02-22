"""
ID: kevinsh4
TASK: zerosum
LANG: PYTHON3
"""
with open('zeroSoundsFuturistic.txt') as read:
    upToNum = int(read.read().rstrip())  # don't question the name, zerosum just sounds kinda futuristic

numbers = []
for i in range(1, upToNum + 1):
    numbers.extend([str(i), ''])

del numbers[-1]  # don't need the last '' after the 7
frontier = [numbers]
currIndex = 1
for i in range(upToNum - 1):
    inLine = []
    for arrangement in frontier:
        onePoss = arrangement.copy()
        twoPoss = arrangement.copy()
        onePoss[currIndex] = '+'
        twoPoss[currIndex] = '-'
        inLine.extend([onePoss, twoPoss, arrangement.copy()])
    frontier = inLine
    currIndex += 2

goodOnes = []
for poss in frontier:
    if eval(''.join(poss)) == 0:
        for v, s in enumerate(poss):
            if s == '':
                poss[v] = ' '
        goodOnes.append(''.join(poss))
        print(''.join(poss))

goodOnes.sort()
with open('outputs.txt', 'w') as written:
    for good in goodOnes:
        written.write(good + '\n')
