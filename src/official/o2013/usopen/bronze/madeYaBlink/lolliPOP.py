with open('blink.in') as read:
    bulbNum, moveAmt = [int(i) for i in read.readline().split()]
    bulbs = [bool(int(read.readline())) for _ in range(bulbNum)]

seen = {}
states = []
toggleTime = 0
while tuple(bulbs) not in seen:
    states.append(bulbs)
    seen[tuple(bulbs)] = toggleTime
    toggleTime += 1
    updated = []
    for v, b in enumerate(bulbs):
        updated.append(not b if bulbs[v - 1] else b)
    bulbs = updated

offset = seen[tuple(bulbs)]
cycleLen = toggleTime - offset
moveAmt = (moveAmt - offset) % cycleLen
finalState = states[moveAmt + offset]

with open('blink.out', 'w') as written:
    for b in finalState:
        print(int(b))
        written.write(str(int(b)) + '\n')
