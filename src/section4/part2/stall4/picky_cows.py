"""
ID: kevinsh4
TASK: stall4
LANG: PYTHON3
"""


def found_job(person, match_mat, taken=None, visited=None):
    """
    this function is probably a bad practice lol
    but anyways, stolen from: https://www.geeksforgeeks.org/maximum-bipartite-matching/
    """
    if visited is None:
        visited = set()
    if taken is None:
        taken = [-1 for _ in range(len(match_mat[0]))]

    for j in range(len(match_mat[0])):
        if match_mat[person][j] and j not in visited:
            visited.add(j)
            # if this isn't visited OR the cow we stole the stall from can also find a stall, assign it
            if taken[j] == -1 or found_job(taken[j], match_mat, taken, visited):
                taken[j] = person
                return True
    return False


with open('stall4.in') as read:
    cow_num, stall_num = [int(i) for i in read.readline().split()]
    can_match = [[0 for _ in range(stall_num)] for _ in range(cow_num)]
    for c in range(cow_num):
        for s in [int(i) - 1 for i in read.readline().split()[1:]]:
            can_match[c][s] = 1

max_matched = 0
assigned = [-1 for _ in range(stall_num)]
for c in range(cow_num):
    if found_job(c, can_match, assigned):
        max_matched += 1

print(max_matched)
print(max_matched, file=open('stall4.out', 'w'))
