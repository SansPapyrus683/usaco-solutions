"""
ID: kevinsh4
TASK: lgame
LANG: PYTHON3
"""
from itertools import combinations, product

MIN_LEN = 3
MAX_LEN = 7
CHAR_VALS = [2, 5, 4, 4, 1, 6, 5, 5, 1, 7, 6, 3, 5, 2, 3, 5, 7, 2, 1, 2, 4, 6, 6, 7, 5, 7]


def allPossWords(word: str, atLeastLen: int = 0, atMostLen: int = -1):
    if atMostLen == -1:
        atMostLen = len(word)
    for len_ in range(atLeastLen, atMostLen + 1):
        for comb in combinations(word, len_):
            comb = ''.join(comb)
            yield comb


def wordVal(word):
    return sum(CHAR_VALS[ord(c) - ord('a')] for c in word)


with open('lgame.in') as read:
    have = read.readline().lower().rstrip()
    assert MIN_LEN <= len(have) <= MAX_LEN

with open('lgame.dict') as read:
    words = [line.lower().strip() for line in read.readlines() if line.strip() != '.']
sortedToOrig = {}
for v, w in enumerate(words):
    words[v] = ''.join(sorted(w))
    if words[v] not in sortedToOrig:  # put in a list because a word can have like multiple permutations
        sortedToOrig[words[v]] = []
    sortedToOrig[words[v]].append(w)
    assert MIN_LEN <= len(w) <= MAX_LEN

bestSingle = 0
bestWords = set()
for w in allPossWords(have, MIN_LEN):
    w = ''.join(sorted(w))
    value = wordVal(w)
    if w in sortedToOrig:
        if value > bestSingle:
            bestSingle = value
            bestWords = {w}
        elif value == bestSingle:
            bestWords.add(w)

bestDouble = 0
bestPairs = set()
for w1 in allPossWords(have, MIN_LEN, len(have) - MIN_LEN):
    w1 = ''.join(sorted(w1))
    leftover = list(have)
    for c in w1:
        del leftover[leftover.index(c)]

    for w2 in allPossWords(''.join(leftover), MIN_LEN, len(leftover)):
        w2 = ''.join(sorted(w2))
        if w1 in sortedToOrig and w2 in sortedToOrig:
            totalVal = wordVal(w1) + wordVal(w2)
            if totalVal > bestDouble:
                bestDouble = totalVal
                bestPairs = {tuple(sorted([w1, w2]))}
            elif totalVal == bestDouble:
                bestPairs.add(tuple(sorted([w1, w2])))

results = []
if bestSingle >= bestDouble:
    for w in bestWords:
        for o in sortedToOrig[w]:
            results.append([o])
if bestDouble >= bestSingle:
    for p in bestPairs:
        o = [sortedToOrig[p[0]], sortedToOrig[p[1]]]
        for c in product(*o):
            results.append(sorted(c))
results.sort()

print(max(bestSingle, bestDouble))
print(results)
with open('lgame.out', 'w') as written:
    written.write(str(max(bestSingle, bestDouble)) + '\n')
    for s in results:
        written.write(' '.join(s) + '\n')
