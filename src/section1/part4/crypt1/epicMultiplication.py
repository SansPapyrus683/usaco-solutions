"""
ID: kevinsh4
TASK: crypt1
LANG: PYTHON3
"""


def possibleComb(initialComb, allCanUse):
    for n in initialComb:
        if n not in allCanUse:  # should never happen, but just a little check
            return False
    top_three = int(''.join([str(i) for i in initialComb[:3]]))
    bottom_ones = initialComb[3]
    bottom_tens = initialComb[4]
    intermediate_results = [top_three * bottom_ones, top_three * bottom_tens]
    final_result = intermediate_results[0] + 10 * intermediate_results[1]
    for n in str(intermediate_results[0]) + str(intermediate_results[1]) + str(final_result):
        if int(n) not in allCanUse:
            return False

    if len(str(intermediate_results[0])) != 3 or len(str(intermediate_results[1])) != 3 or len(str(final_result)) != 4:
        return False
    return True


with open('cryptic.txt') as read:
    read.readline()
    numbers = set([int(i) for i in read.readline().split()])

poss_combinations = {()}
for _ in range(5):
    next_up = set()
    for c in poss_combinations:
        for n in numbers:
            next_up.add(c + (n,))
    poss_combinations = next_up

total = 0
for c in poss_combinations:
    if possibleComb(c, numbers):
        total += 1

print(total)
print(total, file=open('outputs.txt', 'w'))
