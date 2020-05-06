"""
ID: kevinsh4
TASK: msquare
LANG: PYTHON3
"""
from queue import Queue

with open('wantRefund.txt') as read:
    read = read.read().split()
    currConfig = ((1, 2, 3, 4), (8, 7, 6, 5))
    neededConfig = (tuple([int(i) for i in read[:4]]), tuple(reversed([int(i) for i in read[4:]])))

def rowSwap(config: list) -> tuple:
    return config[1], config[0]

def rowRightShift(config: list) -> tuple:  # nl stands for number list
    nl = config[0] + config[1]
    return (nl[3], nl[0], nl[1], nl[2]), (nl[7], nl[4], nl[5], nl[6])

def middleRotate(config: list) -> tuple:
    nl = config[0] + config[1]
    return (nl[0], nl[5], nl[1], nl[3]), (nl[4], nl[6], nl[2], nl[7])

configFrontier = Queue()
configFrontier.put((currConfig, ''))  # the empty string is the history
gotBefore = {currConfig}
while not configFrontier.empty():
    curr = configFrontier.get()
    if curr[0] == neededConfig:
        minTransformation = curr
        break

    for v, op in enumerate([rowSwap, rowRightShift, middleRotate]):
        if op(curr[0]) in gotBefore:
            continue
        configFrontier.put([op(curr[0]), curr[1] + 'ABC'[v]])
        gotBefore.add(op(curr[0]))

with open('outputs.txt', 'w') as written:
    written.write(f'{len(minTransformation[1])}\n{minTransformation[1]}\n')
