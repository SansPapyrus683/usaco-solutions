"""
6
E 3 5
N 5 3
E 4 6
E 10 4
N 11 2
N 8 1 should output 5, 3, Infinity, Infinity, 2, and 5, each on a newline
"""
cowNum = int(input())
north_ind = []
east_ind = []
cows = []
for c in range(cowNum):
    cow = input().split()
    direction = cow[0].upper()
    if direction == 'N':
        north_ind.append(c)
    elif direction == 'E':
        east_ind.append(c)
    else:
        raise ValueError('what kind of direction is %s, i mean...' % direction)
    cows.append([int(cow[1]), int(cow[2])])

# sorting it like this prevents any unexpected interference when processing
east_ind.sort(key=lambda i: cows[i][1])
north_ind.sort(key=lambda i: cows[i][0])

eaten_amt = [float('inf') for _ in range(cowNum)]
dead_alr = [False for _ in range(cowNum)]  # tbh this is only relevant for the north cows, but does it really matter?
for e in east_ind:
    e_cow = cows[e]
    for n in north_ind:
        nCow = cows[n]
        """
        (comment copied from the java sol in that silver version)
        the arrangement has to be something like this for possibility of a block
        E
               N (i mean tbh it's kinda obvi why this is true)
        """
        if not dead_alr[n] and e_cow[0] < nCow[0] and nCow[1] < e_cow[1]:
            e_time = nCow[0] - e_cow[0]  # the hypothetical times for them to get to the meeting position
            n_time = e_cow[1] - nCow[1]
            if e_time < n_time:  # STOP RIGHT THERE NORTH-GOING SCUM!
                dead_alr[n] = True
                eaten_amt[n] = n_time
            elif e_time > n_time:
                dead_alr[e] = True
                eaten_amt[e] = e_time
                break  # already blocked, no point in processing any more

for a in eaten_amt:
    print('Infinity' if a == float('inf') else a)
