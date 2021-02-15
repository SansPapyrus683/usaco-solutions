"""
ID: kevinsh4
TASK: shopping
LANG: PYTHON3
"""
from copy import deepcopy

with open('shopping.in') as read:
    offer_list = []
    to_buy = []
    for v, l in enumerate(read):
        if v == 0:
            offer_num = int(l)

        elif 1 <= v <= offer_num:
            offer = [int(i) for i in l.split()[1:]]
            new_offer = []
            for i in range(0, len(offer), 2):  # [[code, amt], [code, amt]... price]
                c = offer[i:i + 2]
                if len(c) == 1:
                    new_offer.append(c[0])
                else:
                    new_offer.append(c)
            offer_list.append(new_offer)

        elif v > offer_num + 1:  # buy {1} of product {0}, each costing {2}
            to_buy.append([int(i) for i in l.split()])

all_offers = {}
prices = []
encoding = {}
for v, p in enumerate(to_buy):
    single_offer = [0 for _ in range(len(to_buy))]
    single_offer[v] = 1
    all_offers[tuple(single_offer)] = p[2]  # a singular item is basically just a horrid offer
    encoding[p[0]] = v
    to_buy[v] = p[1]  # again, positions are the key

for v, o in enumerate(offer_list):
    new_offer = [0 for _ in range(len(encoding))]  # more like edited
    for p in o[:-1]:
        new_offer[encoding[p[0]]] = p[1]
    all_offers[tuple(new_offer)] = o[-1]
cached = deepcopy(all_offers)


def subtract(rn: [int], offer_: [int]) -> [int]:
    return tuple([o1 - o2 for o1, o2 in zip(rn, offer_)])


def valid(shopping_list: (int,)) -> bool:
    for i in shopping_list:
        if i < 0:
            return False
    return True


def empty(shopping_list: (int,)) -> bool:
    for i in shopping_list:
        if i != 0:
            return False
    return True


def find_lowest_cost(shopping_list: (int,)) -> int:
    if shopping_list in cached:
        return cached[shopping_list]
    if empty(shopping_list):
        return 0

    best = float('inf')
    for o in all_offers:
        after = subtract(shopping_list, o)
        if valid(after):
            gotten_output = find_lowest_cost(after)
            cached[after] = gotten_output
            best = min(best, gotten_output + all_offers[o])

    if best == float('inf'):
        return 0
    return best


with open('shopping.out', 'w') as written:
    output = find_lowest_cost(tuple(to_buy))
    print(output)
    written.write(f'{output}\n')
