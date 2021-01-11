"""
smh farmer john
you should like force the cows to be in the same photo
why do you appease your cows like this
"""
unfriendly = {}
relevantCows = set()
with open('photo.in') as read:
    cowNum, unfriendlyNum = [int(i) for i in read.readline().split()]
    for _ in range(unfriendlyNum):
        first, second = [int(i) - 1 for i in read.readline().split()]
        relevantCows.add(first)
        relevantCows.add(second)
        if first not in unfriendly:
            unfriendly[first] = []
        if second not in unfriendly:
            unfriendly[second] = []
        unfriendly[first].append(second)
        unfriendly[second].append(first)
relevantCows = sorted(relevantCows)

photos = 0
invalid = set()
# greedily take the photos (aka take as much as possible until we reach an unfriendly pair)
for c in relevantCows:
    if c in invalid:
        invalid = set()
        photos += 1
    for bad in unfriendly[c]:
        invalid.add(bad)
if invalid:  # if we still were taking a photo, increment it again
    photos += 1

print(photos)
print(photos, file=open('photo.out', 'w'))
