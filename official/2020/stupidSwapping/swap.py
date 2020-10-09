with open('swap.in') as read:
    for v, line in enumerate(read):
        if v == 0:
            cowNumber, swapTimes = tuple(int(i) for i in line.rstrip().split())
        elif v == 1:
            ASlice = tuple(int(i) for i in line.rstrip().split())
        elif v == 2:
            BSlice = tuple(int(i) for i in line.rstrip().split())

afterPos = [None for _ in range(cowNumber)]
for i in range(1, cowNumber + 1):
    positions = []
    currPos = i  # this position starts at 1, not 0
    while True:
        if ASlice[0] <= currPos <= ASlice[1]:
            currPos = ASlice[1] - (currPos - ASlice[0])
        if BSlice[0] <= currPos <= BSlice[1]:
            currPos = BSlice[1] - (currPos - BSlice[0])
        positions.append(currPos)
        if positions.count(currPos) == 2:  # we've reached a cycle
            # print(positions)
            del positions[-1]
            afterPos[positions[swapTimes % len(positions) - 1] - 1] = i  # the two minus one's are bc of array indexes
            # print(afterPos)
            break

with open('swap.out', 'w') as written:
    for pos in afterPos:
        written.write(str(pos) + '\n')
