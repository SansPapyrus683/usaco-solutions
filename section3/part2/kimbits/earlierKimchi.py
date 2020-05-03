"""
ID: kevinsh4
TASK: kimbits
LANG: PYTHON3
"""
# (documentation on other file)
import operator as op  # this file is if for you're running earlier versions of python (i mean pre 3.8)
from functools import reduce  # THE FUTURE IS NOW, OLD MAN.

with open('notThatIveEatenIt.txt') as read:
    length, oneBitNum, nthBitThing = [int(i) for i in read.read().split()]
    nthBitThing -= 1

def ncr(n, r):
    r = min(r, n-r)
    numer = reduce(op.mul, range(n, n-r, -1), 1)
    denom = reduce(op.mul, range(1, r+1), 1)
    return numer // denom

def allStrings(maxStringLen, mostBits):
    mostBits = maxStringLen if mostBits > maxStringLen else mostBits
    return sum([ncr(maxStringLen, i) for i in range(mostBits + 1)])

def findIndex(stringLen, mostBits, theIndex, history=''):
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
