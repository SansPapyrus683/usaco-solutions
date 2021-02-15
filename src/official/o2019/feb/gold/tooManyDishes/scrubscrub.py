from collections import deque
from bisect import bisect

with open('dishes.in') as read:
    dishes = [int(read.readline()) for _ in range(int(read.readline()))]

# this goes through the philosophy that all the stacks should be like in increasing order
# something like:
# 1 5
# 2 7 9, so that it can be made into an increasing subsequence
last_finished = -1
stacks = deque([deque([dishes[0]])])
washed = 1
for i in range(1, len(dishes)):
    dish = dishes[i]
    if dish > stacks[-1][-1]:  # make a new stack, it's larger than everything else
        stacks.append(deque([dish]))

    elif dish < stacks[0][0]:
        if dish < last_finished:  # oh crap, we can't insert this dish anywhere
            break
        stacks[0].appendleft(dish)  # i know i could use a LifoQueue or some other stuff but this seems more intuitive

    else:
        # ok, let's do a binsearch to find which stack the dish should go in
        lo = 0
        hi = len(stacks) - 1
        while lo < hi:  # basically from the bisect module, you should go look at it
            mid = (lo + hi) // 2
            if stacks[mid][-1] < dish:
                lo = mid + 1
            else:
                hi = mid

        insert_ind = bisect(stacks[lo], dish)
        if insert_ind == 0:
            stacks[lo].appendleft(dish)
        else:
            last_finished = stacks[lo - 1][-1]
            for _ in range(lo):
                stacks.popleft()
            last_finished = stacks[0][insert_ind - 1]
            for _ in range(insert_ind):
                stacks[0].popleft()
            stacks[0].appendleft(dish)
    washed += 1

print(washed)
print(washed, file=open('dishes.out', 'w'))  # so ez lol
