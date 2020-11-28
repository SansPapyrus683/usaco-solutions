# 2017 jan silver
with open('cowcode.in') as read:
    theString, charNum = [i for i in read.readline().split()]
    charNum = int(charNum)


def findChar(position: int, findIn: str) -> str:
    if position <= len(findIn):
        return findIn[position - 1]  # -1 bc 0-based

    currTwoPower = 0  # this will be how many times we'll have to apply it (given that the original string's there alr)
    while True:
        currTwoPower += 1
        if position <= 2 ** currTwoPower * len(findIn):
            break
    lowerBound = len(findIn) * 2 ** (currTwoPower - 1)

    if position == lowerBound + 1:
        return findChar(lowerBound, findIn)  # bc of the rotation
    return findChar(position - lowerBound - 1, findIn)


with open('cowcode.out', 'w') as written:
    answer = findChar(charNum, theString)
    print(answer)
    written.write(str(answer) + '\n')
