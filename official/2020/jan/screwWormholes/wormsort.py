import sys

# TODO: slow. probably because it's python tho
# followed this: https://cp-algorithms.com/data_structures/disjoint_set_union.html
sys.setrecursionlimit(10000)
with open('wormsort.in') as read:
    wormholes = []
    for v, l in enumerate(read):
        if v == 1:
            positions = {}
            cows = [int(i) for i in l.split()]
            for v1, c in enumerate(cows):
                positions[c] = v1
            desired = sorted(cows)
        elif v >= 2:
            wormholes.append([int(i) for i in l.split()])

    toSort = []
    for v, c in enumerate(cows):
        if c != v + 1:
            toSort.append(c)

written = open('wormsort.out', 'w')
if cows == desired:
    written.write('-1\n')
    sys.exit(0)
parents = {}
sizes = {}


def start_tree(p):
    parents[p] = p
    sizes[p] = 1


def get_ultimate(p):
    if parents[p] == p:
        return p
    parents[p] = get_ultimate(parents[p])  # caching
    return parents[p]


def merge_trees(p1, p2):
    tree1, tree2 = get_ultimate(p1), get_ultimate(p2)
    if tree1 != tree2:
        if sizes[tree1] >= sizes[tree2]:  # just have tree1 win, doesn't really matter
            parents[tree2] = tree1  # merge the trees through the root nodes
            sizes[tree2] += sizes[tree1]  # oh no the tree's gotten even bigger
        else:
            parents[tree1] = tree2
            sizes[tree1] += sizes[tree2]


bottomBound, topBound = min([w[2] for w in wormholes]), max([w[2] for w in wormholes])

while topBound - bottomBound > 1:
    toSearch = (topBound + bottomBound) // 2

    parents.clear()
    sizes.clear()
    for p in range(1, len(cows) + 1):
        start_tree(p)

    for w in wormholes:
        if w[2] >= toSearch:  # only allow wormholes of a certain width
            merge_trees(w[0], w[1])

    for c in toSort:
        if get_ultimate(positions[c] + 1) != get_ultimate(c):
            topBound = toSearch
            break
    else:
        bottomBound = toSearch
    break

print(topBound - 1)
written.write(f'{topBound - 1}\n')  # -1 because strictly less than
written.close()
