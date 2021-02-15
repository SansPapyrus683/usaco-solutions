"""
ID: kevinsh4
TASK: nocows
LANG: PYTHON3
iirc i just gave up and copied the official solution
"""
from collections import defaultdict

MOD = 9901

with open('banjos.txt') as read:
    cow_num, gen_num = [int(c) for c in read.read().rstrip().split()]

tree_table = defaultdict(lambda: defaultdict(lambda: 0))  # the treeTable[i][cowN] is # of trees of depth i and cowN cowN
shallower_trees = defaultdict(lambda: defaultdict(lambda: 0))  # the treeTable[i][cowN] still has cowN cowN, but all's
# depth <= i
tree_table[1][1] = 1

for i in range(2, gen_num + 1):  # we'll search the depths of the trees one at a time
    for cow_n in range(1, cow_num + 1, 2):
        other = 1
        while other <= (cow_n - 1 - other):  # calcs all the number of trees smaller
            if other != (cow_n - 1 - other):
                multiplier = 2
            else:
                multiplier = 1
            tree_table[i][cow_n] += multiplier * (shallower_trees[i - 2][other] * tree_table[i - 1][cow_n - 1 - other]
                                                  + tree_table[i - 1][other] * shallower_trees[i - 2][cow_n - 1 - other]
                                                  + tree_table[i - 1][other] * tree_table[i - 1][cow_n - 1 - other])
            tree_table[i][cow_n] %= MOD
            other += 2

    for other in range(cow_num + 1):
        shallower_trees[i - 1][other] += tree_table[i - 1][other] + shallower_trees[i - 2][other]
        shallower_trees[i - 1][other] %= MOD

print(tree_table[gen_num][cow_num], file=open('outputs.txt', 'w'))
