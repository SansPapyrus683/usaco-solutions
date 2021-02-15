"""
ID: kevinsh4
TASK: sort3
LANG: PYTHON3
good lord looking back at this i was so young and reckless
"""
places = []
with open('USACOIGuess.txt') as read:
    for v, line in enumerate(read):
        if v != 0:
            places.append(int(line.rstrip()))

a, b, c = places.count(1), places.count(2), places.count(3)
supposed_ranges = [[0, a], [a, a + b], [a + b, a + b + c]]  # last index not inclusive, like slices
buckets = {1: places[0:a], 2: places[a:a + b], 3: places[a + b:a + b + c]}
for key in buckets:
    buckets[key] = list(filter(lambda k: k != key, buckets[key]))

exchange = 0
for b in buckets:
    for other_b in [y for y in buckets if y != b]:
        val, other_val = b, other_b  # the supposed vals they're supposed to have
        for v, i in enumerate(buckets[b]):
            for still_v, other_i in enumerate(buckets[other_b]):
                if i and other_i and (i == other_val and other_i == val):  # they have to have something both want
                    exchange += 1
                    buckets[b][v], buckets[other_b][still_v] = False, False  # two sorted elements, mark them so yea
                    break

for key in buckets:
    buckets[key] = list(filter(lambda k: k, buckets[key]))  # get all the False's out

new_places = buckets[1] + buckets[2] + buckets[3]
reference = sorted(new_places)
for v, i in enumerate(new_places):
    if reference[v] == i:
        new_places[v] = False
new_places = list(filter(lambda k: k, new_places))  # again, remove any things that correspond with sorted

for n in [1, 2]:  # now we just brute force sort, starting with ones and moving on to twos
    for v, i in enumerate(new_places):
        start_index = 0 if n == 1 else new_places.count(n)
        if i == n and not start_index <= v < start_index + new_places.count(n):  # if this # is out of place
            for x in range(start_index, start_index + new_places.count(n)):  # finding valid places to put the n
                if new_places[x] != n:
                    new_places[v] = new_places[x]  # doing the actual exchange
                    new_places[x] = n
                    break
            exchange += 1

print(exchange)
print(exchange, file=open('outputs.txt', 'w'))
