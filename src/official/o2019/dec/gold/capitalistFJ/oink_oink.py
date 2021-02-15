"""
here have a python implementation
no reason whatsoever i just fel like doing it in python for once
this folllows basically the exact same logic as the java implementation
so all the code comments are over there
(2019 dec gold)
"""
from heapq import heappush


def min_cost_with_flow(flow_req, neighbors, start=0, end=-1):
    end = len(neighbors) - 1 if end == -1 else end

    forward_costs = [float('inf') for _ in range(len(neighbors))]
    forward_costs[0] = 0
    frontier = [[0, start]]
    while frontier:
        curr = frontier.pop(0)[1]
        rn_cost = forward_costs[curr]
        for n in neighbors[curr]:
            if rn_cost + n[1] < forward_costs[n[0]] and n[2] >= flow_req:
                forward_costs[n[0]] = rn_cost + n[1]
                heappush(frontier, [forward_costs[n[0]], n[0]])
    
    backward_cost = [float('inf') for _ in range(len(neighbors))]
    backward_cost[-1] = 0
    frontier = [[0, end]]
    while frontier:
        curr = frontier.pop(0)[1]
        rn_cost = backward_cost[curr]
        for n in neighbors[curr]:
            if rn_cost + n[1] < backward_cost[n[0]] and n[2] >= flow_req:
                backward_cost[n[0]] = rn_cost + n[1]
                heappush(frontier, [backward_cost[n[0]], n[0]])
    
    min_cost = float('inf')
    for j, n_list in enumerate(neighbors):
        for n in n_list:
            if n[2] == flow_req:
                min_cost = min(min_cost, forward_costs[j] + n[1] + backward_cost[n[0]])
    return min_cost


with open('pump.in') as read:
    # junctions are points, pipes are edges
    junction_num, pipe_num = [int(i) for i in read.readline().split()]
    adj_juncs = [[] for _ in range(junction_num)]
    flow_rates = set()
    for _ in range(pipe_num):
        pipe = [int(i) for i in read.readline().split()]
        pipe[0] -= 1
        pipe[1] -= 1
        adj_juncs[pipe[0]].append(pipe[1:])  # note: neighbor, followed by the cost & flow rate
        adj_juncs[pipe[1]].append(pipe[:1] + pipe[2:])
        flow_rates.add(pipe[3])

best_score = 0
for r in flow_rates:
    best_score = max(best_score, r / min_cost_with_flow(r, adj_juncs))
print(best_score)
with open('pump.out', 'w') as written:
    written.write(str(int(best_score * 10 ** 6)) + '\n')
