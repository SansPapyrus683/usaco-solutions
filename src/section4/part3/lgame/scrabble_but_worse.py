"""
ID: kevinsh4
TASK: lgame
LANG: PYTHON3
"""
from itertools import combinations, product

MIN_LEN = 3
MAX_LEN = 7
CHAR_VALS = [2, 5, 4, 4, 1, 6, 5, 5, 1, 7, 6, 3, 5, 2, 3, 5, 7, 2, 1, 2, 4, 6, 6, 7, 5, 7]


def all_poss_words(word: str, at_least_len: int = 0, at_most_len: int = -1):
    if at_most_len == -1:
        at_most_len = len(word)
    for len_ in range(at_least_len, at_most_len + 1):
        for comb in combinations(word, len_):
            comb = ''.join(comb)
            yield comb


def word_val(word):
    return sum(CHAR_VALS[ord(c) - ord('a')] for c in word)


with open('lgame.in') as read:
    have = read.readline().lower().rstrip()
    assert MIN_LEN <= len(have) <= MAX_LEN

with open('lgame.dict') as read:
    words = [line.lower().strip() for line in read.readlines() if line.strip() != '.']

sorted_to_orig = {}
for v, w in enumerate(words):
    words[v] = ''.join(sorted(w))
    if words[v] not in sorted_to_orig:  # put in a list because a word can have like multiple permutations
        sorted_to_orig[words[v]] = []
    sorted_to_orig[words[v]].append(w)
    assert MIN_LEN <= len(w) <= MAX_LEN

best_single = 0
best_words = set()
for w in all_poss_words(have, MIN_LEN):
    w = ''.join(sorted(w))
    value = word_val(w)
    if w in sorted_to_orig:
        if value > best_single:
            best_single = value
            best_words = {w}
        elif value == best_single:
            best_words.add(w)

best_double = 0
best_pairs = set()
for w1 in all_poss_words(have, MIN_LEN, len(have) - MIN_LEN):
    w1 = ''.join(sorted(w1))
    leftover = list(have)
    for c in w1:
        del leftover[leftover.index(c)]

    for w2 in all_poss_words(''.join(leftover), MIN_LEN, len(leftover)):
        w2 = ''.join(sorted(w2))
        if w1 in sorted_to_orig and w2 in sorted_to_orig:
            total_val = word_val(w1) + word_val(w2)
            if total_val > best_double:
                best_double = total_val
                best_pairs = {tuple(sorted([w1, w2]))}
            elif total_val == best_double:
                best_pairs.add(tuple(sorted([w1, w2])))

results = []
if best_single >= best_double:
    for w in best_words:
        for o in sorted_to_orig[w]:
            results.append([o])
if best_double >= best_single:
    for p in best_pairs:
        o = [sorted_to_orig[p[0]], sorted_to_orig[p[1]]]
        for c in product(*o):
            results.append(sorted(c))
results.sort()

print(max(best_single, best_double))
print(results)
with open('lgame.out', 'w') as written:
    written.write(str(max(best_single, best_double)) + '\n')
    for s in results:
        written.write(' '.join(s) + '\n')
