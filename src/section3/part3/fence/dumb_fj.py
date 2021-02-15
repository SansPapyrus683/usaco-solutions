"""
ID: kevinsh4
TASK: fence
LANG: PYTHON3
"""
import sys
from collections import defaultdict
from copy import deepcopy

sys.setrecursionlimit(10000)
connected_intersections = defaultdict(lambda: list())
with open('lazy_as_frick.txt') as read:
    for v, line in enumerate(read):
        line = [int(i) for i in line.split()]
        if v != 0:
            connected_intersections[line[0]].append(line[1])
            connected_intersections[line[1]].append(line[0])
    connected_intersections = {i: sorted(connected_intersections[i]) for i in connected_intersections}

# ik global variables are a bad practice but hey, it gets the job done
# 2/14/2021- I HATE YOU PAST ME
circuit = {}
circuit_pos = 0
sacrificial_circuit = deepcopy(connected_intersections)  # we'll remove all the edges from this
path = []


def the_tour(position):
    sacrificial_circuit[position].sort()
    if not sacrificial_circuit[position]:
        path.append(position)
    else:
        # while loop bc with a for loop it always iterates through EVERY thing, but this while loop cares if it's
        # still there in the loop
        while sacrificial_circuit[position]:
            n = sacrificial_circuit[position][0]
            sacrificial_circuit[position].remove(n)  # remove that edge
            sacrificial_circuit[n].remove(position)
            the_tour(n)  # recurse on that node
        path.append(position)
    return path


def actual_sol():
    global sacrificial_circuit  # yes ik global vars are bad practice
    global path
    connected = defaultdict(lambda: int())
    for i in connected_intersections:
        for n in connected_intersections[i]:
            connected[i] += 1
            connected[n] += 1

    two_odds = False  # are there just two odd nodes
    the_odds = []
    for i in connected:
        if (connected[i] // 2) % 2 == 1:  # // 2 because i counted each edge twice
            two_odds = True
            the_odds.append(i)
            if len(the_odds) == 2:
                break

    if two_odds:
        for i in connected:
            if i == min(the_odds):
                tour = the_tour(i)
                return tour
    else:
        for i in connected:
            tour = the_tour(i)
            return tour


with open('outputs.txt', 'w') as written:
    for i in reversed(actual_sol()):
        written.write(str(i) + '\n')
