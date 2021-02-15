"""
smh farmer john
you should like force the cows to be in the same photo
why do you appease your cows like this
"""
unfriendly = {}
relevant_cows = set()
with open('photo.in') as read:
    cowNum, unfriendly_num = [int(i) for i in read.readline().split()]
    for _ in range(unfriendly_num):
        first, second = [int(i) - 1 for i in read.readline().split()]
        relevant_cows.add(first)
        relevant_cows.add(second)
        if first not in unfriendly:
            unfriendly[first] = []
        if second not in unfriendly:
            unfriendly[second] = []
        unfriendly[first].append(second)
        unfriendly[second].append(first)
relevant_cows = sorted(relevant_cows)

photos = 0
invalid = set()
# greedily take the photos (aka take as much as possible until we reach an unfriendly pair)
for c in relevant_cows:
    if c in invalid:
        invalid = set()
        photos += 1
    for bad in unfriendly[c]:
        invalid.add(bad)
if invalid:  # if we still were taking a photo, increment it again
    photos += 1

print(photos)
print(photos, file=open('photo.out', 'w'))
