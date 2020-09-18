"""
ID: kevinsh4
TASK: shopping
LANG: PYTHON3
"""
import sys
import heapq

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


def add(rn: [int], offer_: [int]) -> [int]:
    return [o1 + o2 for o1, o2 in zip(rn, offer_)]

def valid(shoppingList: [int]):
    for t, tb in zip(shoppingList, toBuy):
        if t < 0 or t > tb:
            return False
    return True


costs = {tuple([0 for _ in range(len(toBuy))]): 0}
frontier = [[0, [0 for _ in range(len(toBuy))]]]
upperBound = float('inf')
while frontier:
    # print(frontier)
    current = heapq.heappop(frontier)
    if current[0] == toBuy:
        upperBound = current[1]
    if current[0] > upperBound:
        continue

    for o in allOffers:  # go through offers
        after = add(current[1], o)
        cost = current[0] + allOffers[o]
        if valid(after) and (tuple(after) not in costs or costs[tuple(after)] > cost) and cost <= upperBound:
            costs[tuple(after)] = cost
            heapq.heappush(frontier, [cost, after])

with open('shopping.out', 'w') as written:
    print(costs[tuple(toBuy)])
    written.write(f'{costs[tuple(toBuy)]}\n')
    sys.exit(0)
