from itertools import combinations

subpopulations = []
with open('evolution.in') as read:
    for _ in range(int(read.readline())):
        subpopulations.append(set(read.readline().strip().split()[1:]))

allFeatures = set().union(*subpopulations)
for first, second in combinations(allFeatures, 2):
    containsFirst = {v for v, i in enumerate(subpopulations) if first in i}
    containsSecond = {v for v, i in enumerate(subpopulations) if second in i}
    """
    so all the cows that have the first feature and all the cows have the second feature can't be like
    A A AB ABC B B
    they share some elements in common, but not ALL of them
    i think it's kinda easy to deduce why an arrangement like this isn't possible
    any other one is possible (they can be subsets or disjoint), just not this one
    """
    if not (containsFirst.issubset(containsSecond) or containsSecond.issubset(containsFirst) or
            containsSecond.isdisjoint(containsFirst)):
        print('no')
        print('no', file=open('evolution.out', 'w'))
        break
else:
    print('yes')
    print('yes', file=open('evolution.out', 'w'))
