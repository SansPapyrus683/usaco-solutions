"""
ID: kevinsh4
TASK: barn1
LANG: PYTHON3
"""


def remove_segment(all_stalls, occupied_ones):  # removes the longest unoccupied segment
    unoccupied = sorted([s for s in all_stalls if s not in occupied_ones])
    
    longest_unoccupied = []
    so_far = []
    for s in unoccupied:
        if not so_far or s == so_far[-1] + 1:
            so_far.append(s)
        else:
            if len(so_far) > len(longest_unoccupied):
                longest_unoccupied = so_far
            so_far = [s]
    if len(so_far) > len(longest_unoccupied):
        longest_unoccupied = so_far
    
    all_stalls = set(all_stalls)
    for s in longest_unoccupied:
        all_stalls.remove(s)
    return sorted(all_stalls)


with open('repair_costs.txt') as read:
    board_num, stall_num, occupied_num = [int(i) for i in read.readline().split()]
    occupied = [int(read.readline()) for _ in range(occupied_num)]
occupied.sort()

stalls = [i for i in range(occupied[0], occupied[-1] + 1)]
for _ in range(board_num - 1):
    stalls = remove_segment(stalls, occupied)

print(len(stalls))
print(len(stalls), file=open('outputs.txt', 'w'))
