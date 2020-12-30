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
northCowIndices = []
eastCowIndices = []
cows = []
for c in range(cowNum):
    cow = input().split()
    direction = cow[0].upper()
    if direction == 'N':
        northCowIndices.append(c)
    elif direction == 'E':
        eastCowIndices.append(c)
    else:
        raise ValueError('what kind of direction is %s, i mean...' % direction)
    cows.append([int(cow[1]), int(cow[2])])

# sorting it like this prevents any unexpected interference when processing
eastCowIndices.sort(key=lambda i: cows[i][1])
northCowIndices.sort(key=lambda i: cows[i][0])

eatenAmt = [float('inf') for _ in range(cowNum)]
deadAlr = [False for _ in range(cowNum)]  # tbh this is only relevant for the north cows, but does it really matter?
for e in eastCowIndices:
    eCow = cows[e]
    for n in northCowIndices:
        nCow = cows[n]
        """
        (comment copied from the java sol)
        the arrangement has to be something like this for possibility of a block
        E
               N (i mean tbh it's kinda obvi why this is true)
        """
        if not deadAlr[n] and eCow[0] < nCow[0] and nCow[1] < eCow[1]:
            eCowTime = nCow[0] - eCow[0]  # the hypothetical times for them to get to the meeting position
            nCowTime = eCow[1] - nCow[1]
            if eCowTime < nCowTime:  # STOP RIGHT THERE NORTH-GOING SCUM!
                deadAlr[n] = True
                eatenAmt[n] = nCowTime
            elif eCowTime > nCowTime:
                deadAlr[e] = True
                eatenAmt[e] = eCowTime
                break  # already blocked, no point in processing any more

for a in eatenAmt:
    print('Infinity' if a == float('inf') else a)
