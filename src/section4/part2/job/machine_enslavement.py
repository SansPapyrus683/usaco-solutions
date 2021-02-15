"""
ID: kevinsh4
TASK: job
LANG: PYTHON3
"""
from typing import List


# used the typing module for compatibility and whatnot
def machine_fin_times(machines: List[int], job_num: int) -> List[int]:
    """
    note to self:
    you can't just have all the machines run constantly
    because if the rates are like 60 and 1, and there's only 3 tasks
    it's better for the 60 machine to like not run at all
    but anyways, this func calculates the minimum ending times for each job
    """
    finish_times = []
    already_spent = [0 for _ in range(len(machines))]
    for _ in range(job_num):
        assignment_times = [already_spent[i] + machines[i] for i in range(len(machines))]
        best_time = min(assignment_times)
        finish_times.append(best_time)
        already_spent[assignment_times.index(best_time)] = best_time
    return finish_times


with open('job.in') as read:
    jobs, a_num, b_num = [int(i) for i in read.readline().split()]
    machines = []
    for line in read.readlines():
        machines.extend([int(i) for i in line.split()])
    a_machines = machines[:a_num]
    b_machines = machines[a_num: a_num + b_num]
    assert len(a_machines) == a_num and len(b_machines) == b_num, 'you better give me the right numbers bro'

a_fin_times = machine_fin_times(a_machines, jobs)
b_fin_times = machine_fin_times(b_machines, jobs)

print(a_fin_times)
print(b_fin_times)

total_time = max(a + b for a, b in zip(a_fin_times, reversed(b_fin_times)))
print(a_fin_times[-1], total_time)
print(a_fin_times[-1], total_time, file=open('job.out', 'w'))  # wow this is so concise though
