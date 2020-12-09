"""
ID: kevinsh4
TASK: crypt1
LANG: PYTHON3
"""


def possibleComb(initialComb, allCanUse):
    for n in initialComb:
        if n not in allCanUse:  # should never happen, but just a little check
            return False
    topThree = int(''.join([str(i) for i in initialComb[:3]]))
    bottomOnes = initialComb[3]
    bottomTens = initialComb[4]
    intermediateResults = [topThree * bottomOnes, topThree * bottomTens]
    finalResult = intermediateResults[0] + 10 * intermediateResults[1]
    for n in str(intermediateResults[0]) + str(intermediateResults[1]) + str(finalResult):
        if int(n) not in allCanUse:
            return False

    if len(str(intermediateResults[0])) != 3 or len(str(intermediateResults[1])) != 3 or len(str(finalResult)) != 4:
        return False
    return True


with open('cryptic.txt') as read:
    read.readline()
    numbers = set([int(i) for i in read.readline().split()])

possCombinations = {()}
for _ in range(5):
    nextUp = set()
    for c in possCombinations:
        for n in numbers:
            nextUp.add(c + (n,))
    possCombinations = nextUp

total = 0
for c in possCombinations:
    if possibleComb(c, numbers):
        total += 1

print(total)
with open('outputs.txt', 'w') as written:
    written.write(str(total) + '\n')
