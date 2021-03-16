# 2014 dec bronze
SPOTS = 'S'


# number of spotted cows from lo_weight to hi_weight given the surrounding cows
def spotted_num(left: int, right: int, lo_cow=None, hi_cow=None) -> int:
    if lo_cow is None and hi_cow is None:
        raise ValueError("bruh you have to provide at least one cow")
    if lo_cow is None or hi_cow is None:
        return (right - left + 1) * ((hi_cow if lo_cow is None else lo_cow)[1])

    # do some simple sanity checks
    if left > right or lo_cow[0] > hi_cow[0]:
        raise ValueError("one of your lo's is higher than one of your hi's wth")
    if not (lo_cow[0] <= left <= hi_cow[0] and lo_cow[0] <= right <= hi_cow[0]):
        raise ValueError("um the lo weight and hi weight have to be between the cows")

    # this will always be right in the middle or 0.5 greater than the actual middle
    raw_mid = (lo_cow[0] + hi_cow[0] + 1) // 2
    # account for the pesky middle case right here
    total = int(lo_cow[1] or hi_cow[1]) if (hi_cow[0] - lo_cow[0]) % 2 == 0 and left <= raw_mid <= right else 0
    mid_left = raw_mid - 1
    mid_right = mid_left + 1 + ((hi_cow[0] - lo_cow[0]) % 2 == 0)
    # left side stuff
    total += lo_cow[1] * max(min(mid_left, right) - left + 1, 0)
    # right side stuff    
    total += hi_cow[1] * max(right - max(mid_right, left) + 1, 0)
    return total


cows = []
with open('learning.in') as read:
    cow_num, lo, hi = [int(i) for i in read.readline().split()]
    for _ in range(cow_num):
        cow = read.readline().split()
        cows.append([int(cow[1]), cow[0] == SPOTS])
cows.sort()

total_spotted = 0
if lo <= cows[0][0]:
    total_spotted += spotted_num(lo, cows[0][0], None, cows[0])
if hi > cows[-1][0]:
    total_spotted += spotted_num(cows[-1][0] + 1, hi, cows[-1])

for i in range(cow_num - 1):
    if max(lo, cows[i][0]) > min(hi, cows[i + 1][0]):
        continue
    total_spotted += spotted_num(max(lo, cows[i][0] + 1), min(hi, cows[i + 1][0]),
                                 cows[i], cows[i + 1])

print(total_spotted)
print(total_spotted, file=open('learning.out', 'w'))
