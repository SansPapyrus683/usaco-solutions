"""
ID: kevinsh4
TASK: combo
LANG: PYTHON3
"""

from itertools import product

LOCK_NUM = 2

with open('ohBaby.txt') as read:
    lock_amt = int(read.readline())
    combs = [tuple([int(i) for i in read.readline().split()]) for _ in range(LOCK_NUM)]

valid_combs = []
for comb in combs:
    single_poss_vals = []
    for c in comb:
        one_poss_val = []
        for i in range(-2, 3):
            val = c + i
            while val < 1 or val > lock_amt:
                if val > lock_amt:
                    val -= lock_amt
                elif c + i < 1:
                    val += lock_amt
            one_poss_val.append(val)
        single_poss_vals.append(one_poss_val)
    valid_combs.extend(list(product(*single_poss_vals)))

valid = len(set(valid_combs))
print(valid)
print(valid, file=open('outputs.txt', 'w'))
