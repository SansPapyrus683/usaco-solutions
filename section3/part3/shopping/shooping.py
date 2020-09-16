"""
ID: kevinsh4
TASK: shopping
LANG: PYTHON3
"""
import sys

# with open('shopping.in') as read:
with open('coinsThing.txt') as read:
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

prices = []
encoding = {}
for v, p in enumerate(toBuy):
    prices.append([{v: 1}, p[2]])  # a singular item is basically just a horrid offer
    encoding[p[0]] = v
    toBuy[v] = p[1]  # again, positions are the key

killList = []
for v, o in enumerate(offerList):
    newOffer = {}  # more like edited
    for p in o[:-1]:
        if p[0] not in encoding:
            killList.append(v)
            break
        newOffer[encoding[p[0]]] = p[1]
    offerList[v] = [newOffer, o[-1]]

for t in reversed(killList):
    del offer[t]


def add(rn: [int], offer_: {int: int}) -> [int]:
    rn = rn.copy()
    for b_ in offer_:
        rn[b_] += offer_[b_]
    return rn


costs = {tuple([0 for _ in range(len(toBuy))]): 0}
frontier = [[[0 for _ in range(len(toBuy))], 0]]
valid = []
while frontier:
    inLine = []
    for b in frontier:
        if b[0] == toBuy:
            pass

        for o in offerList + prices:  # go through offers
            after = add(b[0], o[0])
            cost = b[1] + o[1]
            for v, t in enumerate(after):
                if toBuy[v] < t:
                    break
            else:
                if tuple(after) not in costs or costs[tuple(after)] > cost:
                    costs[tuple(after)] = cost
                    inLine.append([after, cost])
    frontier = inLine

# with open('shopping.out', 'w') as written:
with open('outputs.txt', 'w') as written:
    print(costs[tuple(toBuy)])
    written.write(f'{costs[tuple(toBuy)]}\n')
    sys.exit(0)
