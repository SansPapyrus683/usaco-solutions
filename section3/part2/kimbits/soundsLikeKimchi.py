"""
ID: kevinsh4
TASK: kimbits
LANG: PYTHON3
"""
from math import comb

with open('notThatIveEatenIt.txt') as read:
    length, oneBitNum, nthBitThing = [int(i) for i in read.read().split()]
    nthBitThing -= 1  # -1 because arrays start at 0 and the problem doesn't have the set start @ 0

def allStrings(maxStringLen, mostBits):
    return sum([comb(maxStringLen, i) for i in range(mostBits + 1)])

def findIndex(stringLen, mostBits, theIndex, history=''):
    print(stringLen, mostBits, theIndex, history)
    if stringLen == 1:
        if mostBits == 0:
            return history + '0'  # i mean, there's only one possibility so why not
        else:
            return [history + '0', history + '1'][theIndex]

    if theIndex < allStrings(stringLen - 1, mostBits):  # the recursive part
        return findIndex(stringLen - 1, mostBits, theIndex, history + '0')
    else:
        return findIndex(stringLen - 1, mostBits - 1, theIndex - allStrings(stringLen - 1, mostBits), history + '1')

with open('outputs.txt', 'w') as written:
    written.write(str(findIndex(length, oneBitNum, nthBitThing)) + '\n')
