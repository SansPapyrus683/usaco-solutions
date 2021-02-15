"""
ID: kevinsh4
TASK: holstein
LANG: PYTHON3
"""
from itertools import combinations
from sys import exit

vitamin_key = 'abcdefghijklmnopqrstuvwxyz'
requirements = dict()
raw_vitamins = dict()  # just a placeholder for how many vitamins there are, probs a better way of doing this
vitamins = dict()  # the scoop assortment
scoop_number = 1
with open('strawberry_good_too.txt') as read:
    for v, line in enumerate(read):
        if v == 1:
            for another_v, n in enumerate([int(i) for i in line.rstrip().split()]):
                requirements[vitamin_key[another_v]] = n
                raw_vitamins[vitamin_key[another_v]] = 0
        elif v > 2:
            scoop = {}
            for another_v, n in enumerate([int(i) for i in line.rstrip().split()]):
                scoop[vitamin_key[another_v]] = n
            vitamins[scoop_number] = scoop
            scoop_number += 1

for vit_comb_num in range(1, scoop_number):
    for scoop_comb in combinations(vitamins, vit_comb_num):  # try for all scoop combs
        curr_scoop = raw_vitamins.copy()
        for scoop in scoop_comb:
            scoop = vitamins[scoop]
            for vit in scoop:
                curr_scoop[vit] += scoop[vit]  # just sees the amt of vitamins that will be made
        for vit in curr_scoop:
            if not curr_scoop[vit] >= requirements[vit]:
                break
        else:
            with open('outputs.txt', 'w') as written:
                written.write(str(vit_comb_num) + ' ' + ' '.join([str(x) for x in sorted(scoop_comb)]) + '\n')
            exit()
