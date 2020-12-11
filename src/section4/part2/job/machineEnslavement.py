"""
ID: kevinsh4
TASK: job
LANG: PYTHON3
"""
from typing import List


# used the typing module for compatibility and whatnot
def machineFinTimes(machines: List[int], jobNum: int) -> List[int]:
    """
    note to self:
    you can't just have all the machines run constantly
    because if the rates are like 60 and 1, and there's only 3 tasks
    it's better for the 60 machine to like not run at all
    but anyways, this func calculates the minimum ending times for each job
    """
    finishTimes = []
    alreadySpent = [0 for _ in range(len(machines))]
    for _ in range(jobNum):
        assignmentTimes = [alreadySpent[i] + machines[i] for i in range(len(machines))]
        bestTime = min(assignmentTimes)
        finishTimes.append(bestTime)
        alreadySpent[assignmentTimes.index(bestTime)] = bestTime
    return finishTimes


with open('terminators.txt') as read:
    jobs, aNum, bNum = [int(i) for i in read.readline().split()]
    machines = []
    for line in read.readlines():
        machines.extend([int(i) for i in line.split()])
    aMachines = machines[:aNum]
    bMachines = machines[aNum: aNum + bNum]
    assert len(aMachines) == aNum and len(bMachines) == bNum, 'you better give me the right numbers bro'

aFinTimes = machineFinTimes(aMachines, jobs)
bFinTimes = machineFinTimes(bMachines, jobs)

print(aFinTimes)
print(bFinTimes)

totalTime = max(a + b for a, b in zip(aFinTimes, reversed(bFinTimes)))
print(aFinTimes[-1], totalTime)
print(aFinTimes[-1], totalTime, file=open('outputs.txt', 'w'))  # wow this is so concise though
