"""
ID: kevinsh4
TASK: barn1
LANG: PYTHON3
"""


def removeSegment(allStalls, occupiedOnes):  # removes the longest unoccupied segment
    unoccupied = sorted([s for s in allStalls if s not in occupiedOnes])
    
    longestUnoccupied = []
    soFar = []
    for s in unoccupied:
        if not soFar or s == soFar[-1] + 1:
            soFar.append(s)
        else:
            if len(soFar) > len(longestUnoccupied):
                longestUnoccupied = soFar
            soFar = [s]
    if len(soFar) > len(longestUnoccupied):
        longestUnoccupied = soFar
    
    allStalls = set(allStalls)
    for s in longestUnoccupied:
        allStalls.remove(s)
    return sorted(allStalls)


with open('repairCosts.txt') as read:
    boardNum, stallNum, occupiedNum = [int(i) for i in read.readline().split()]
    occupied = [int(read.readline()) for _ in range(occupiedNum)]
occupied.sort()

stalls = [i for i in range(occupied[0], occupied[-1] + 1)]
for _ in range(boardNum - 1):
    stalls = removeSegment(stalls, occupied)

print(len(stalls))
with open('outputs.txt', 'w') as written:
    written.write(str(len(stalls)) + '\n')
