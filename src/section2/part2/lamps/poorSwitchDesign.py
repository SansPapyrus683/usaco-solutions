"""
ID: kevinsh4
TASK: lamps
LANG: PYTHON3
"""
with open('nintendoSwitch.txt') as read:
    for line_num, line in enumerate(read):
        if line_num == 0:
            lamps = tuple(True for _ in range(int(line.rstrip())))  # i just learned about for _ in range() lol
        elif line_num == 1:
            off_on_times = int(line.rstrip())
        elif line_num == 2:
            on_lamps = [int(i) - 1 for i in line.rstrip().split()[:-1]]
        elif line_num == 3:  # minus one because aRrAYS stARt At 0
            offLamps = [int(i) - 1 for i in line.rstrip().split()[:-1]]


good_states = set()
frontier = [lamps]
cached_n = {}  # cached neighbors


def state_neighbors(curr_states):
    global cached_n
    if curr_states in cached_n:  # do some caching to speed up runtime
        return cached_n[curr_states]
    second = list(curr_states)
    third = list(curr_states)
    fourth = list(curr_states)
    for v, l in enumerate(curr_states):  # make all the switch lists in one iteration
        if (v + 1) % 2:
            second[v] = not l
            third[v] = l
        else:
            third[v] = not l
            second[v] = l
        if not v % 3:
            fourth[v] = not l
        else:
            fourth[v] = l
    # below line caches the results because the program goes through many neighbors many times
    cached_n[curr_states] = (tuple(not l for l in curr_states), tuple(second), tuple(third), tuple(fourth))
    return tuple(not l for l in curr_states), tuple(second), tuple(third), tuple(fourth)


for c in range(off_on_times):  # do the actual switching and stuff
    in_line = []
    for state in frontier:
        for n in state_neighbors(state):
            in_line.append(n)
    frontier = list(set(in_line))


for arrangement in frontier:
    for req in on_lamps:  # test for validity of lamp states
        if not arrangement[req]:
            break
    else:
        for req in offLamps:
            if arrangement[req]:
                break
        else:
            good_states.add(arrangement)

good_states = sorted(good_states)
with open('outputs.txt', 'w') as written:
    if not good_states:
        written.write('IMPOSSIBLE\n')
    for state in good_states:
        state = list(state)
        for v, i in enumerate(state):
            state[v] = 1 if i else 0
        written.write(''.join([str(s) for s in state]) + '\n')
