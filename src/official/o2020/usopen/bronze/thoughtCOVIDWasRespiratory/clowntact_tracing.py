# 2020 usopen bronze
def spread_sim(cow_num: int, p_zero: int, spread_times: int, handshakes):
    cows = [False for _ in range(cow_num)]
    cows[p_zero] = True
    shake_after_infected = {c: 0 for c in range(cow_num)}
    for h in handshakes:
        just_infected = False
        if cows[h[0]]:
            shake_after_infected[h[0]] += 1
            if shake_after_infected[h[0]] <= spread_times:
                just_infected = not cows[h[1]]  # only just infected if we switched from a False to a True
                cows[h[1]] = True

        if not just_infected and cows[h[1]]:
            shake_after_infected[h[1]] += 1
            if shake_after_infected[h[1]] <= spread_times:
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

poss_zeroes = 0
lowest_time = float('inf')
highest_time = 0
# brute force all possible patient zeros and K values (how many handshakes after that spread)
for v, c in enumerate(infected):
    if not c:  # bruh there's no possible way this cow could be pat. 0 (it isn't infected at the end i mean)
        continue
    possible = []
    for k in range(0, len(shakes) + 1):
        possible.append(spread_sim(len(infected), v, k, shakes) == infected)
    # also do a check to see if there is no upper bound
    if spread_sim(len(infected), v, float('inf'), shakes) == infected:
        highest_time = float('inf')

    if any(possible):
        poss_zeroes += 1
        firstPossible = possible.index(True)
        lowest_time = min(lowest_time, firstPossible)
        while firstPossible + 1 < len(possible) and possible[firstPossible + 1]:  # go until the last True
            firstPossible += 1
        highest_time = max(highest_time, firstPossible)

print(poss_zeroes, lowest_time, highest_time)
with open('tracing.out', 'w') as written:
    written.write(str(poss_zeroes) + " " + str(lowest_time) + " ")
    if highest_time == float('inf'):
        written.write('Infinity\n')
    else:
        written.write(str(highest_time) + '\n')
