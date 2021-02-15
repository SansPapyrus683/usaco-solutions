"""
ID: kevinsh4
TASK: subset
LANG: PYTHON3

ok so this algo needs some 'splaining to do
so here's the amount of ways for a subset of N to sum up to each of the natural numbers:
if N was 1, it can sum up to 0 in 1 way, and 1 in another way
if N was 2, it can sum up to 0 in 1 way, 1 in 1 way, 2 in 1 way, and 3 in 1 way
here's a treeTable:
N   0   1   2   3   4   5   6   7   8   9   10
1|   1   1
2|   1   1   1   1
3|   1   1   1   2   1   1   1  
4|   1   1   1   2   2   2   2   1   1   1
where's the transition from N to N+1?
if we shift N's line o' numbers N+1 units to the right, and add the two like so (ill take 2 transitioning to 3):
1   1   1   1
            1   1   1   1, we get N+1's line of numbers, and we keep doing that so yea
"""
with open('subset.in') as read:
    up_to_num = int(read.read().rstrip())

actual_set = {i for i in range(1, up_to_num + 1)}

sum_numbers = {1: [1, 1], 2: [1, 1, 1, 1]}  # 0, 1, 2, 3...
need_to_add = 3
while up_to_num not in sum_numbers:
    old_list = sum_numbers[need_to_add - 1].copy()
    add_to_old = [0 for x in range(need_to_add)] + old_list
    for v, i in enumerate(old_list):
        add_to_old[v] += i
    sum_numbers[need_to_add] = add_to_old
    need_to_add += 1

with open('subset.out', 'w') as written:
    if sum(actual_set) % 2:
        written.write('0\n')
    else:
        written.write(str(sum_numbers[up_to_num][sum(actual_set) // 2] // 2) + '\n')
