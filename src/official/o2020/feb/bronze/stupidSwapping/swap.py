# 2020 jan bronze
with open('swap.in') as read:
    for v, line in enumerate(read):
        if v == 0:
            cowNumber, swapTimes = tuple(int(i) for i in line.rstrip().split())
        elif v == 1:
            a_slice = tuple(int(i) for i in line.rstrip().split())
        elif v == 2:
            b_slice = tuple(int(i) for i in line.rstrip().split())

after_pos = [None for _ in range(cowNumber)]
for i in range(1, cowNumber + 1):
    positions = []
    curr_pos = i  # this position starts at 1, not 0
    while True:
        if a_slice[0] <= curr_pos <= a_slice[1]:
            curr_pos = a_slice[1] - (curr_pos - a_slice[0])
        if b_slice[0] <= curr_pos <= b_slice[1]:
            curr_pos = b_slice[1] - (curr_pos - b_slice[0])
        positions.append(curr_pos)
        if positions.count(curr_pos) == 2:  # we've reached a cycle
            del positions[-1]
            after_pos[positions[swapTimes % len(positions) - 1] - 1] = i  # the two minus one's are bc of array indexes
            break

with open('swap.out', 'w') as written:
    for pos in after_pos:
        written.write(str(pos) + '\n')
