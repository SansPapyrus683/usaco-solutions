import sys

with open('wormsort.in') as read:
    wormholes = []
    for v, l in enumerate(read):
        if v == 1:
            cows = [int(i) for i in l.split()]
            desired = sorted(cows)
        elif v >= 2:
            wormholes.append([int(i) for i in l.split()])
    wormholes.sort(key=lambda w: w[2])
    toSort = []
    for v, c in enumerate(cows):
        if c != v + 1:
            toSort.append(c)

with open('wormsort.out', 'w') as written:
    if cows == desired:
        written.write('-1\n')
        sys.exit(0)


def connected(toUse, cow) -> bool:  # TODO: this is definitely not optimized
    at = cows.index(cow) + 1

    frontier = []
    for w in toUse:
        if at in w[:-1]:
            frontier.append(at)
            break
    else:
        return False

    visited = set(frontier)
    while frontier:
        print(f'exploring with indexes {frontier} seeing if {cow} is connected')
        if cow in frontier:
            print(f'yup {cow} can be sorted')
            return True

        inLine = []
        for a in frontier:
            for w in toUse:
                if w[1] not in visited and a == w[0]:
                    inLine.append(w[1])
                elif w[0] not in visited and a == w[1]:
                    inLine.append(w[0])
        frontier = inLine
    return False


bottomBound, topBound = min([w[2] for w in wormholes]), max([w[2] for w in wormholes])
while topBound - bottomBound > 1:
    toSearch = (topBound + bottomBound) // 2
    onlyUse = []
    for w in wormholes:
        if w[2] >= toSearch:
            onlyUse.append(w)

    for c in toSort:
        if not connected(onlyUse, c):
            topBound = toSearch
            break
    else:
        bottomBound = toSearch

with open('wormsort.out', 'w') as written:
    written.write(f'{topBound - 1}\n')
