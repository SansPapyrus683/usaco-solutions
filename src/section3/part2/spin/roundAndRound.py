"""
ID: kevinsh4
TASK: spin
LANG: PYTHON3
"""
from sys import exit

wheels = []
with open('kindaDizzy.txt') as read:
    for l in read.readlines():
        newWheel = {'s': int(l.split()[0]), 'wedges': []}  # s means speed
        currWedge = []
        for i in l.split()[2:]:
            currWedge.append(int(i))
            if len(currWedge) == 2:
                newWheel['wedges'].append([currWedge[0], sum(currWedge) % 360])
                currWedge = []
        wheels.append(newWheel)
written = open('outputs.txt', 'w')

for t in range(360):  # after 360, everything will be back to normal regardless
    for i in range(360):
        for w in wheels:
            for we in w['wedges']:  # if we found a valid wedge for the position, stop searching
                if we[0] < we[1] and we[0] <= i <= we[1]:
                    break
                elif we[0] > we[1] and not (we[1] < i < we[0]):
                    break
                elif we[0] == we[1] and i == we[0]:
                    break
            else:  # we didn't? well, just go to the next one
                break
        else:  # we found one yay
            written.write(str(t) + '\n')
            exit()

    for w in wheels:  # apply the wheel speeds
        for v, we in enumerate(w['wedges']):
            w['wedges'][v] = [(we[0] + w['s']) % 360, (we[1] + w['s']) % 360]
else:
    written.write('none\n')
