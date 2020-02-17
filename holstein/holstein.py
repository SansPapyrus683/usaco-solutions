"""
ID: kevinsh4
TASK: holstein
LANG: PYTHON3
"""
from itertools import combinations
from sys import exit

vitaminkey = 'abcdefghijklmnopqrstuvwxyz'
requirements = dict()
rawVitamins = dict()  # just a placeholder for how many vitamins there are, probs a better way of doing this
vitamins = dict()  # the scoop assortment
scoopNumber = 1
with open('holstein.in') as read:
    for v, line in enumerate(read):
        if v == 1:
            for anotherV, n in enumerate([int(i) for i in line.rstrip().split()]):
                requirements[vitaminkey[anotherV]] = n
                rawVitamins[vitaminkey[anotherV]] = 0
        elif v > 2:
            scoop = {}
            for anotherV, n in enumerate([int(i) for i in line.rstrip().split()]):
                scoop[vitaminkey[anotherV]] = n
            vitamins[scoopNumber] = scoop
            scoopNumber += 1

for vitCombNum in range(1, scoopNumber):
    for scoopComb in combinations(vitamins, vitCombNum):
        currScoop = rawVitamins.copy()
        for scoop in scoopComb:
            scoop = vitamins[scoop]
            for vit in scoop:
                currScoop[vit] += scoop[vit]
        for vit in currScoop:
            if not currScoop[vit] >= requirements[vit]:
                break
        else:
            with open('holstein.out', 'w') as written:
                written.write(str(vitCombNum) + ' ' + ' '.join([str(x) for x in sorted(scoopComb)]) + '\n')
            exit()
