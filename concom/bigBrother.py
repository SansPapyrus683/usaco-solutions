"""
ID: kevinsh4
TASK: concom
LANG: PYTHON3
"""
from collections import defaultdict
from itertools import permutations
from sys import exit, setrecursionlimit
setrecursionlimit(10)

with open('capitalismBeLike.txt') as read:
    owned = defaultdict(lambda: defaultdict(lambda: 0))  # owned[a][b] will give how much a owns of b
    for v, line in enumerate(read):
        if v != 0:
            line = line.rstrip().split()
            owned[int(line[0])][int(line[1])] = int(line[2])
    companies = list(owned.keys())

def checkOwn(controller, subject):  # more like check control but you get the idea
    print('testing %i and %i' % (controller, subject))
    if owned[controller][subject] > 50:  # directly owned?
        return True
    else:  # lets see if a controls b indirectly or smth
        ownAmt = 0
        for inHand in owned[controller]:
            if (not inHand == subject) and checkOwn(controller, inHand):
                ownAmt += owned[inHand][subject]
        if ownAmt > 50:
            print(controller, subject)
            return True
    return False


print(checkOwn(3, 2))
print(owned)
exit()
for stockEx in permutations(companies, 2):
    if checkOwn(stockEx[0], stockEx[1]):
        print(stockEx)
