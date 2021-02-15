"""
ID: kevinsh4
TASK: milk3
LANG: PYTHON3
"""
from copy import deepcopy
from itertools import permutations

milk_buckets = {}

with open('malk.txt') as buckets:
    for line in buckets.readlines():
        line = [int(i) for i in line.rstrip().split()]
        milk_buckets['A'] = [line[0], 0]
        milk_buckets['B'] = [line[1], 0]
        milk_buckets['C'] = [line[2], line[2]]


def pour(pouring: 'bucket id', pouree: 'another bucket id', curr_states: dict):
    next_states = deepcopy(curr_states)
    first_bucket = curr_states[pouring].copy()
    second_bucket = curr_states[pouree].copy()
    while first_bucket[-1] != 0:  # pours until a empty or b full
        if second_bucket[0] == second_bucket[-1]:
            break
        first_bucket[-1] -= 1
        second_bucket[-1] += 1
    next_states[pouring] = first_bucket
    next_states[pouree] = second_bucket
    return next_states


poss_values = [pour('C', 'B', milk_buckets)['C'][-1]]  # an easy possible value
states_in_line = [pour('C', 'A', milk_buckets), pour('C', 'B', milk_buckets)]  # sets up the a two nodes for bfs

visited = deepcopy(states_in_line)
found_all_states = False
while not found_all_states:  # does a bfs through all possible states
    found_all_states = True
    in_line = []
    for state in states_in_line:  # one node will be linked to others if it's accessible through a single pour
        for pourComb in permutations(['A', 'B', 'C'], 2):
            poss = pour(pourComb[0], pourComb[1], state)
            if poss not in visited:
                in_line.append(poss)
                visited.append(poss)
                found_all_states = False
            if poss['A'][-1] == 0 and poss['C'][-1] not in poss_values:
                poss_values.append(poss['C'][-1])
    states_in_line = in_line  # pushes all the inLine states into the processing line

with open('outputs.txt', 'w') as written:
    poss_values.sort()
    written.write(' '.join([str(x) for x in poss_values]) + '\n')
