"""
ID: kevinsh4
TASK: sort3
LANG: PYTHON3
"""
places = []
with open('USACOIGuess.txt') as read:
    for v, line in enumerate(read):
        if v != 0:
            places.append(int(line.rstrip()))

a, b, c = places.count(1), places.count(2), places.count(3)
supposedRanges = [[0, a], [a, a + b], [a + b, a + b + c]]  # last index not inclusive, like slices
buckets = {1: places[0:a], 2: places[a:a+b], 3: places[a+b:a+b+c]}
for key in buckets:
    buckets[key] = list(filter(lambda k: k != key, buckets[key]))

exchange = 0
for b in buckets:
    for otherB in [y for y in buckets if y != b]:
        val, otherVal = b, otherB  # the supposed vals they're supposed to have
        for v, i in enumerate(buckets[b]):
            for stillV, otherI in enumerate(buckets[otherB]):
                if i and otherI and (i == otherVal and otherI == val):  # they have to have something both want
                        exchange += 1
                        buckets[b][v], buckets[otherB][stillV] = False, False  # two sorted elements, mark them so yea
                        break

for key in buckets:
    buckets[key] = list(filter(lambda k: k, buckets[key]))  # get all the False's out

newPlaces = buckets[1] + buckets[2] + buckets[3]
reference = sorted(newPlaces)
for v, i in enumerate(newPlaces):
    if reference[v] == i:
        newPlaces[v] = False
newPlaces = list(filter(lambda k: k, newPlaces))  # again, remove any things that correspond with sorted

for n in [1, 2]:  # now we just brute force sort, starting with ones and moving on to twos
    for v, i in enumerate(newPlaces):
        startIndex = 0 if n == 1 else newPlaces.count(n)
        if i == n and not startIndex <= v < startIndex + newPlaces.count(n):  # if this # is out of place
            for x in range(startIndex, startIndex + newPlaces.count(n)):  # finding valid places to put the n
                if newPlaces[x] != n:
                    newPlaces[v] = newPlaces[x]  # doing the actual exchange
                    newPlaces[x] = n
                    break
            exchange += 1

with open('outputs.txt', 'w') as written:
    written.write(str(exchange) + '\n')
