with open('berries.in') as read:
    for v, l in enumerate(read):
        if v == 0:
            basketNum = int(l.split()[1])
        else:
            trees = [int(i) for i in l.split()]
            bestTree = max(trees)
            trees.sort(reverse=True)
            trees = trees[:basketNum]  # bessie can only fill so many baskets anyways

maxBerries = 0
for b in range(1, bestTree + 1):  # just try to give elsie no more than bessie
    tempTrees = trees.copy()
    basketsLeft = basketNum
    gottenBerries = 0
    for v, t in enumerate(tempTrees):
        if not basketsLeft:  # ran out of baskets to fill with just b berries
            break

        for _ in range(t // b):
            if not basketsLeft:
                break

            if basketsLeft <= basketNum // 2:
                gottenBerries += b
            tempTrees[v] -= b
            basketsLeft -= 1
    else:
        if basketsLeft:
            tempTrees.sort(reverse=True)
            for t in tempTrees:
                if not basketsLeft:
                    break
                if basketsLeft <= basketNum // 2:
                    gottenBerries += t
                basketsLeft -= 1

    maxBerries = max(maxBerries, gottenBerries)

with open('berries.out', 'w') as written:
    print(maxBerries)
    written.write(f'{maxBerries}\n')
