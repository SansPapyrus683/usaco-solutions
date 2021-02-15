sightings = {}
with open('crossroad.in') as read:
    for _ in range(int(read.readline())):
        cow, side = [int(i) for i in read.readline().split()]
        if cow not in sightings:
            sightings[cow] = []
        sightings[cow].append(bool(side))

crossings = 0
for s in sightings.values():
    for v, i in enumerate(s[1:]):
        crossings += i ^ s[v]  # not v - 1 because we started enumerating from the second element
print(crossings)
print(crossings, file=open('crossroad.out', 'w'))
