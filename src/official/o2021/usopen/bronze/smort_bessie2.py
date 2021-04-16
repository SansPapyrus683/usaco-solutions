"""
2021 usopen bronze
2 3
elsie mildred dean
elsie mildred dean
elsie dean mildred should output
B00
1B0
11B
"""
paper_num, people_num = [int(i) for i in input().split()]
people = {p: i for i, p in enumerate(input().strip().split())}

papers = [input().strip().split() for _ in range(paper_num)]
assert all(len(set(p)) == people_num for p in papers), f'all papers should have {people_num} people writing them'

relation = [['?' for _ in range(people_num)] for _ in range(people_num)]
for p in range(people_num):
    relation[p][p] = 'B'
for p in papers:
    for i in range(people_num):
        in_order = True
        for j in range(i + 1, people_num):
            if p[j] < p[j - 1]:
                in_order = False
            if not in_order:
                relation[people[p[i]]][people[p[j]]] = '0'
                relation[people[p[j]]][people[p[i]]] = '1'

for r in relation:
    print(''.join(r))
