"""
ID: kevinsh4
TASK: comehome
LANG: PYTHON3
"""
from heapq import heappush, heappop

END = 'Z'

neighbors = {}
with open('she_got_milk.txt') as read:
    for _ in range(int(read.readline())):
        start, end, weight = read.readline().split()
        if start not in neighbors:
            neighbors[start] = {}
        if end not in neighbors:
            neighbors[end] = {}
        neighbors[start][end] = min(neighbors[start].get(end, float('inf')), int(weight))
        neighbors[end][start] = min(neighbors[end].get(start, float('inf')), int(weight))

shortest_time = float('inf')
first_cow = None
for p in neighbors:
    if p.isupper() and p != END:
        frontier = [[0, p]]
        min_cost = {p: 0}
        while frontier:
            this_cost, n = heappop(frontier)
            if this_cost != min_cost[n]:
                continue
            if n == 'Z':
                break
            for n, nc in neighbors[n].items():
                if this_cost + nc < min_cost.get(n, float('inf')):
                    min_cost[n] = this_cost + nc
                    heappush(frontier, [this_cost + nc, n])

        if min_cost.get(END, float('inf')) < shortest_time:
            shortest_time = min_cost[END]
            first_cow = p


print(first_cow, shortest_time, file=open('outputs.txt', 'w'))
print(first_cow, shortest_time)
