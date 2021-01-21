"""
2019 dec bronze
lol only sue's name starts with an s
also where the frick is my girl elsie
"""
from itertools import permutations

# these cows are sorted by alphabetical order
COWS = {
    'beatrice': 0,
    'belinda': 1,
    'bella': 2,
    'bessie': 3,
    'betsy': 4,
    'blue': 5,
    'buttercup': 6,
    'sue': 7
}
REVERSE = ['beatrice', 'belinda', 'bella', 'bessie', 'betsy', 'blue', 'buttercup', 'sue']

requirements = [[] for _ in range(len(COWS))]
with open('lineup.in') as read:
    for _ in range(int(read.readline())):
        req = read.readline().lower().replace('must be milked beside', '').strip().split()
        requirements[COWS[req[0]]].append(COWS[req[1]])

works = -1
# the bounds are so small we can just brute force all orderings
for order in permutations(range(len(COWS))):
    valid = True
    for c, r in enumerate(requirements):
        for otherC in r:
            if abs(order.index(c) - order.index(otherC)) > 1:
                valid = False
                break
        if not valid:
            break
    if valid:
        works = order
        break

for c in works:
    name = REVERSE[c]
    name = name[0].upper() + name[1:]  # capitalize first letter (frick you brian)
    print(name)
    print(name, file=open('lineup.out', 'a'))  # not w because it clears the entire file
