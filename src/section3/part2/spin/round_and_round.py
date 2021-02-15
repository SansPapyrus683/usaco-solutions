"""
ID: kevinsh4
TASK: spin
LANG: PYTHON3
"""
from sys import exit

wheels = []
with open('kindaDizzy.txt') as read:
    for l in read.readlines():
        new_wheel = {'s': int(l.split()[0]), 'wedges': []}  # s means speed
        curr_wedge = []
        for i in l.split()[2:]:
            curr_wedge.append(int(i))
            if len(curr_wedge) == 2:
                new_wheel['wedges'].append([curr_wedge[0], sum(curr_wedge) % 360])
                curr_wedge = []
        wheels.append(new_wheel)
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
