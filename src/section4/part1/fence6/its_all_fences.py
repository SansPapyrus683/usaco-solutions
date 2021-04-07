"""
ID: kevinsh4
TASK: fence6
LANG: PYTHON3
"""
from heapq import heappush, heappop

fences = []
neighbors = {}
lengths = {}
with open('fence6.in') as read:
    for _ in range(int(read.readline())):
        fence_id, length, _, _ = [int(i) for i in read.readline().split()]
        fences.append(fence_id)
        lengths[fence_id] = length
        neighbors[fence_id] = [int(i) for i in read.readline().split()], [int(i) for i in read.readline().split()]

point = 1
points_edges = {}
processed = set()
for n in neighbors:  # convert the edges list to a points list
    edge_meetup = tuple(sorted([n, *neighbors[n][0]]))
    if edge_meetup not in processed:
        points_edges[point] = edge_meetup
        point += 1
        processed.add(edge_meetup)
    edge_meetup = tuple(sorted([n, *neighbors[n][1]]))
    if edge_meetup not in processed:
        points_edges[point] = edge_meetup
        point += 1
        processed.add(edge_meetup)

neighbors = {p: [] for p in range(1, point)}
for p1, edges1 in points_edges.items():
    for e in edges1:
        for p2, edges2 in points_edges.items():
            if p1 != p2 and e in edges2:
                neighbors[p1].append((p2, lengths[e]))

min_perimeter = float('inf')
for p, n_list in neighbors.items():
    # for each of the neighbors run a dijkstra's to find the shortest path back home (if possible)
    for adj, dist in n_list:
        neighbors[adj].remove((p, dist))  # prevent the algorithm from just going back without a cycle
        distances = {adj: dist}
        frontier = [(dist, adj)]
        while frontier:  # simple dijkstra's to find the shortest path back to the start
            current = heappop(frontier)[1]
            if current == p:
                continue
            rnCost = distances[current]
            for n, n_cost in neighbors[current]:
                if n not in distances or rnCost + n_cost < distances[n]:
                    distances[n] = rnCost + n_cost
                    heappush(frontier, (rnCost + n_cost, n))
        if p in distances:  # sometimes it isn't possible to go back to the start in a cycle
            min_perimeter = min(min_perimeter, distances[p])
        neighbors[adj].append((p, dist))  # revert changes

print(min_perimeter)
print(min_perimeter, file=open('fence6.out', 'w'))
