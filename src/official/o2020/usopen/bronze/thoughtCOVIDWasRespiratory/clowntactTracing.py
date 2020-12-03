# 2020 usopen bronze
def spreadSimulation(cowNum, pZero, spreadTimes, handshakes):
    cows = [False for _ in range(cowNum)]
    cows[pZero] = True
    shakeAfterInfected = {c: 0 for c in range(cowNum)}
    for h in handshakes:
        justInfected = False
        if cows[h[0]]:
            shakeAfterInfected[h[0]] += 1
            if shakeAfterInfected[h[0]] <= spreadTimes:
                justInfected = not cows[h[1]]  # only just infected if we switched from a False to a True
                cows[h[1]] = True

        if not justInfected and cows[h[1]]:
            shakeAfterInfected[h[1]] += 1
            if shakeAfterInfected[h[1]] <= spreadTimes:
                cows[h[0]] = True
    return cows


with open('tracing.in') as read:
    read.readline()
    infected = [c == '1' for c in read.readline().rstrip()]
    shakes = []
    for s in read.readlines():
        shakes.append([int(i) for i in s.split()])
        shakes[-1][1] -= 1  # the cows are initially 1-indexed, let's switch em to 0
        shakes[-1][2] -= 1
shakes = [s[1:] for s in sorted(shakes)]  # after sorting the time is irrelevant

possibleZeroes = 0
lowestSpreadTime = float('inf')
highestSpreadTime = 0
# brute force all possible patient zeros and K values (how many handshakes after that spread)
for v, c in enumerate(infected):
    if not c:  # bruh there's no possible way this cow could be pat. 0 (it isn't infected at the end i mean)
        continue
    possible = []
    for k in range(0, len(shakes) + 1):
        possible.append(spreadSimulation(len(infected), v, k, shakes) == infected)
    # also do a check to see if there is no upper bound
    if spreadSimulation(len(infected), v, float('inf'), shakes) == infected:
        highestSpreadTime = float('inf')

    if any(possible):
        possibleZeroes += 1
        firstPossible = possible.index(True)
        lowestSpreadTime = min(lowestSpreadTime, firstPossible)
        while firstPossible + 1 < len(possible) and possible[firstPossible + 1]:  # go until the last True
            firstPossible += 1
        highestSpreadTime = max(highestSpreadTime, firstPossible)

print(possibleZeroes, lowestSpreadTime, highestSpreadTime)
with open('tracing.out', 'w') as written:
    written.write(str(possibleZeroes) + " " + str(lowestSpreadTime) + " ")
    if highestSpreadTime == float('inf'):
        written.write('Infinity\n')
    else:
        written.write(str(highestSpreadTime) + '\n')
