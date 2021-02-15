"""
ID: kevinsh4
TASK: nuggets
LANG: PYTHON3
"""
MAX_SIZE = 256

with open('nuggets.in') as read:
    nuggets = [int(read.readline()) for _ in range(int(read.readline()))]
    assert all([n <= MAX_SIZE for n in nuggets])

# so given 2 relatively prime #'s the largest # that can't be formed is 1st * 2nd - (1st + 2nd)
# the + MAX_SIZE is just some leeway for my horrid coding
largest_impossible = MAX_SIZE * MAX_SIZE - (MAX_SIZE + MAX_SIZE)
possible = [False for _ in range(largest_impossible + MAX_SIZE)]
possible[0] = True
for i in range(len(possible)):
    if not possible[i]:
        continue
    for n in nuggets:
        if i + n < len(possible):
            possible[i + n] = True

max_impossible = 0
for v, p in enumerate(possible):
    if not p:
        max_impossible = v
if max_impossible > largest_impossible:
    max_impossible = 0

print(max_impossible)
with open('nuggets.out', 'w') as written:
    written.write(str(max_impossible) + '\n')
