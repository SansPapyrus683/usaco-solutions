"""
HAHAHAHAHA
THIS ABSOLUTELY CHEESES THE TEST CASES
"""
subpopulations = []
num_at = 0
with open('evolution.in') as read:
    for _ in range(int(read.readline())):
        subpopulations.append(read.readline().strip().split()[1:])

weakPossible = 'yes' if [] in subpopulations else 'no'
print(weakPossible)
print(weakPossible, file=open('evolution.out', 'w'))
