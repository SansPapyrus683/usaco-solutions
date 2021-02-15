# 2019 jan bronze
from itertools import combinations

animals = []
with open('guess.in') as read:
    for _ in range(int(read.readline())):
        thing = read.readline().strip().split()
        animals.append(set(thing[2:]))

# if there's 2 cows that have a buncha traits in common, elsie can ask about all those traits
# then she can just ask for the "defining" trait, resulting in the # of common traits + 1 "yeses"
max_common = max(len(a.intersection(b)) for a, b in combinations(animals, 2))
print(max_common + 1)
print(max_common + 1, file=open('guess.out', 'w'))
