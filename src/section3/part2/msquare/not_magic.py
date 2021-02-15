"""
ID: kevinsh4
TASK: msquare
LANG: PYTHON3
"""
from queue import Queue

with open('want_refund.txt') as read:
    read = read.read().split()
    curr_config = ((1, 2, 3, 4), (8, 7, 6, 5))
    needed_config = (tuple([int(i) for i in read[:4]]), tuple(reversed([int(i) for i in read[4:]])))


def row_swap(config: list) -> tuple:
    return config[1], config[0]


def row_right_shift(config: list) -> tuple:  # nl stands for number list
    nl = config[0] + config[1]
    return (nl[3], nl[0], nl[1], nl[2]), (nl[7], nl[4], nl[5], nl[6])


def middle_rotate(config: list) -> tuple:
    nl = config[0] + config[1]
    return (nl[0], nl[5], nl[1], nl[3]), (nl[4], nl[6], nl[2], nl[7])


config_frontier = Queue()
config_frontier.put((curr_config, ''))  # the empty string is the history
got_before = {curr_config}
while not config_frontier.empty():
    curr = config_frontier.get()
    if curr[0] == needed_config:
        min_transformation = curr
        break

    for v, op in enumerate([row_swap, row_right_shift, middle_rotate]):
        if op(curr[0]) in got_before:
            continue
        config_frontier.put([op(curr[0]), curr[1] + 'ABC'[v]])
        got_before.add(op(curr[0]))

with open('outputs.txt', 'w') as written:
    written.write(f'{len(min_transformation[1])}\n{min_transformation[1]}\n')
