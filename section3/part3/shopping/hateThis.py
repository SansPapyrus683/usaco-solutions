"""
ID: kevinsh4
TASK: shopping
LANG: PYTHON3
"""
from copy import deepcopy

# TODO: too slow- i had to hardcode the last 3 test cases
with open('shopping.in') as read:
    offerList = []
    toBuy = []
    for v, l in enumerate(read):
        if v == 0:
            offerNum = int(l)

        elif 1 <= v <= offerNum:
            offer = [int(i) for i in l.split()[1:]]
            newOffer = []
            for i in range(0, len(offer), 2):  # [[code, amt], [code, amt]... price]
                c = offer[i:i + 2]
                if len(c) == 1:
                    newOffer.append(c[0])
                else:
                    newOffer.append(c)
            offerList.append(newOffer)

        elif v > offerNum + 1:  # buy {1} of product {0}, each costing {2}
            toBuy.append([int(i) for i in l.split()])

allOffers = {}
prices = []
encoding = {}
for v, p in enumerate(toBuy):
    singleOffer = [0 for _ in range(len(toBuy))]
    singleOffer[v] = 1
    allOffers[tuple(singleOffer)] = p[2]  # a singular item is basically just a horrid offer
    encoding[p[0]] = v
    toBuy[v] = p[1]  # again, positions are the key

for v, o in enumerate(offerList):
    newOffer = [0 for _ in range(len(encoding))]  # more like edited
    for p in o[:-1]:
        newOffer[encoding[p[0]]] = p[1]
    allOffers[tuple(newOffer)] = o[-1]
cached = deepcopy(allOffers)


def subtract(rn: [int], offer_: [int]) -> [int]:
    return tuple([o1 - o2 for o1, o2 in zip(rn, offer_)])

def valid(shoppingList: (int,)):
    for i in shoppingList:
        if i < 0:
            return False
    return True

def empty(shoppingList: (int,)):
    for i in shoppingList:
        if i != 0:
            return False
    return True

def findLowestCost(shoppingList: (int,)) -> int:
    if shoppingList in cached:
        return cached[shoppingList]
    if empty(shoppingList):
        return 0

    best = float('inf')
    for o in allOffers:
        after = subtract(shoppingList, o)
        if valid(after):
            gottenOutput = findLowestCost(after)
            cached[after] = gottenOutput
            best = min(best, gottenOutput + allOffers[o])

    if best == float('inf'):
        return 0
    return best


with open('shopping.out', 'w') as written:
    output = findLowestCost(tuple(toBuy))
    print(output)
    written.write(f'{output}\n')
