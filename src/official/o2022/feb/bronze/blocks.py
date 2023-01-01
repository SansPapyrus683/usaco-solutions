from itertools import permutations, product

BLOCK_NUM = 4

word_num = int(input())

blocks = [input() for _ in range(BLOCK_NUM)]

poss_words = set()
for b_perm in permutations(blocks):
    for word in product(*b_perm):
        word = ''.join(word)
        for pref in range(1, len(word) + 1):
            poss_words.add(word[:pref])

for _ in range(word_num):
    word = input()
    print('YES' if word in poss_words else 'NO')
