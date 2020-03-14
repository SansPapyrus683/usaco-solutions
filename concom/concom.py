"""
ID: kevinsh4
TASK: concom
LANG: PYTHON3
"""
from collections import defaultdict
from itertools import permutations

with open('concom.in') as read:
    owned = defaultdict(lambda: defaultdict(lambda: int()))  # owned[a][b] will give how much a owns of b
    companies = set()
    for v, line in enumerate(read):
        if v != 0:
            line = line.rstrip().split()
            owned[int(line[0])][int(line[1])] = int(line[2])
            companies.add(int(line[0]))
            companies.add(int(line[1]))
    owned = {i: dict(owned[i]) for i in owned}

prevFound = {}  # do some caching bc why not


def sharesOwned(controller: int, subject: int, comeFrom: set) -> int:  # more like check control but you get the idea
    print('checking for %i and %i' % (controller, subject))
    print({i: dict(owned[i]) for i in owned})
    if (controller, subject) in prevFound:
        return prevFound[(controller, subject)]

    ownAmt = 0
    comeFrom.add(subject)
    if controller in owned and subject in owned[controller]:  # directly own some?
        ownAmt += owned[controller][subject]
    elif controller in owned:  # lets see if a controls b indirectly or smth
        for inHand in owned[controller]:
            if inHand not in comeFrom and sharesOwned(controller, inHand, comeFrom) > 50:
                ownAmt += sharesOwned(inHand, subject, comeFrom)

    prevFound[(controller, subject)] = ownAmt
    return ownAmt


controlledPairs = []
for stockEx in permutations(companies, 2):
    print(stockEx)
    if sharesOwned(stockEx[0], stockEx[1], set()) > 50:
        controlledPairs.append(stockEx)

with open('concom.out', 'w') as written:
    for pair in controlledPairs:
        written.write(str(pair[0]) + ' ' + str(pair[1]) + '\n')
