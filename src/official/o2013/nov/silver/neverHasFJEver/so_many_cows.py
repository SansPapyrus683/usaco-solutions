from functools import reduce
from operator import mul

missing = []
with open('nocow.in') as read:
    adj_num, cow_to_find = [int(i) for i in read.readline().split()]
    for _ in range(adj_num):
        missing.append(read.readline().lower().replace('farmer john has no ', '').replace(' cow.', '').split())
assert len(set(len(c) for c in missing)) == 1, 'all the cows should have the same # of adjectives'

adjectives = []
for col in zip(*missing):
    adjectives.append(sorted(set(col)))

missing_ids = []  # like, if they were all there, what would the IDs be? (the IDs start from 1)
# missing.append(['small', 'spotted', 'noisy'])
adj_amts = [len(a) for a in adjectives]
total_amt = reduce(mul, adj_amts)
for c in missing:
    hypothetical_id = 1
    product = total_amt
    for i in range(len(adjectives)):
        product //= adj_amts[i]
        hypothetical_id += adjectives[i].index(c[i]) * product
    missing_ids.append(hypothetical_id)
missing_ids.sort()

cow_missing = 0
intervals = [0] + missing_ids
kth_cow_no = -1
for v, m in enumerate(intervals):
    hypothetical = cow_to_find + cow_missing
    if v == len(intervals) - 1 or hypothetical < intervals[v + 1]:
        kth_cow_no = hypothetical
        break
    cow_missing += 1

product = total_amt // adj_amts[0]
adj_ind = []
cow_no_left = kth_cow_no - 1  # turning it to 0-based so the modulus, division, and other stuff work correctly
for i in range(len(adjectives)):
    adj_ind.append(cow_no_left // product)
    cow_no_left %= product
    if i < len(adjectives) - 1:
        product //= adj_amts[i + 1]

found_cow = ' '.join(adjectives[v][i] for v, i in enumerate(adj_ind))
print(found_cow)
print(found_cow, file=open('nocow.out', 'w'))
