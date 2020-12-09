"""
ID: kevinsh4
TASK: beads
LANG: PYTHON3
"""
with open('newOrleans.txt') as read:
    beadLen = int(read.readline())
    beads = read.readline().rstrip()
    assert beadLen == len(beads), 'you thought you could get away with bad input? think again'

beads = beads * 2
maxLen = 0
for i in range(beadLen // 2, beadLen + beadLen // 2):  # test all the possible split indices
    leftIndex = i
    leftColor = beads[leftIndex]  # go left and right until we can't anymore
    while leftIndex > 0 and (beads[leftIndex - 1] == leftColor or 'w' in [leftColor, beads[leftIndex - 1]]):
        leftIndex -= 1
        if beads[leftIndex] != 'w':
            leftColor = beads[leftIndex]

    rightIndex = i + 1
    rightColor = beads[rightIndex]
    while rightIndex < 2 * beadLen - 1 and \
            (beads[rightIndex + 1] == rightColor or 'w' in [rightColor, beads[rightIndex + 1]]):
        rightIndex += 1
        if beads[rightIndex] != 'w':
            rightColor = beads[rightIndex]

    maxLen = max(maxLen, min(beadLen, rightIndex - leftIndex + 1))

print(maxLen)
with open('outputs.txt', 'w') as written:
    written.write(str(maxLen) + '\n')
