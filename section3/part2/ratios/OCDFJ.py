"""
ID: kevinsh4
TASK: ratios
LANG: PYTHON3
"""
import numpy as np
from math import gcd
import numpy.linalg as linalg
import fractions

# this is someone's explanation:
# 1. make a 3x3 matrix A where the columns of A are the input ratios
# 2. Solve Ax = y where y is the target ratio. x should be a vector of rational numbers.
#    You can do this by finding the inverse of the matrix A, or by performing gaussian elimination
# 3. Multiply the vector x by the lcm of the denominators of x so that they are all integers
# that all sounds fine and dandy until you realize that USACO's python doesn't have numpy
# oof

def lcm(x, y):
    return (x * y) // gcd(x, y)

feedMatrix = [[None, None, None], [None, None, None], [None, None, None]]
feedList = []
with open('theCowsDontCare.txt') as read:
    for v, line in enumerate(read):
        line = [int(i) for i in line.split()]
        if v == 0:
            goalRatios = np.array(line)
        else:
            for v1, c in enumerate(feedMatrix):
                c[v - 1] = line[v1]
            feedList.append(np.array(line))

bottomDeterminant = int(round(linalg.det(feedMatrix)))
firstUpDet = int(round(linalg.det(np.rot90([goalRatios, feedList[1], feedList[2]]))))
secondUpDet = int(round(linalg.det(np.rot90([feedList[0], goalRatios, feedList[2]]))))
thirdUpDet = int(round(linalg.det(np.rot90([feedList[0], feedList[1], goalRatios]))))

first, second, third = -1 * fractions.Fraction(firstUpDet, bottomDeterminant), \
                       -1 * fractions.Fraction(secondUpDet, bottomDeterminant), \
                       -1 * fractions.Fraction(thirdUpDet, bottomDeterminant)

with open('outputs.txt', 'w') as written:
    theLCM = lcm(lcm(first.denominator, second.denominator), third.denominator)
    endUpWith = first * theLCM * feedList[0] + second * theLCM * feedList[1] + third * theLCM * feedList[2]
    for v, i in enumerate(endUpWith):
        try:
            multiple = i // goalRatios[v]
            break
        except ZeroDivisionError:
            pass

    written.write(f'{first * theLCM} {second * theLCM} {third * theLCM} {multiple}')
