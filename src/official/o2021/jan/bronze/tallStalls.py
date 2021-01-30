"""
2021 jan gold
4
1 2 3 4
2 4 3 4 should output 8
"""
input()
cows = sorted([int(i) for i in input().split()], reverse=True)
stalls = sorted([int(i) for i in input().split()], reverse=True)
assert len(cows) <= len(stalls), 'bruh i have to be able to fit all the cows in the stalls'

total = 1
stallAt = 0
for v, c in enumerate(cows):
    if c > stalls[0]:
        total = 0
        break
    # get the amt of stalls that we can assign this cow to
    while stallAt + 1 < len(stalls) and stalls[stallAt + 1] >= c:
        stallAt += 1
    # mul by the total amt of stalls we can assign this one to - the # of stalls that are occupied alr
    total *= stallAt + 1 - v
print(total)
