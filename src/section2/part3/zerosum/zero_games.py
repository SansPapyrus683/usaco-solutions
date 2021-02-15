"""
ID: kevinsh4
TASK: zerosum
LANG: PYTHON3
"""
with open('game_theory.txt') as read:
    up_to_num = int(read.read().rstrip())  # don't question the name, zerosum just sounds kinda futuristic

numbers = []
for i in range(1, up_to_num + 1):
    numbers.extend([str(i), ''])

del numbers[-1]  # don't need the last '' after the 7
frontier = [numbers]
curr_index = 1  # the index to replace with
for i in range(up_to_num - 1):  # generate all possible combinatiosn of +, -, and abstain
    in_line = []
    for arrangement in frontier:
        one_poss = arrangement.copy()
        two_poss = arrangement.copy()
        one_poss[curr_index] = '+'
        two_poss[curr_index] = '-'
        in_line.extend([one_poss, two_poss, arrangement.copy()])
    frontier = in_line
    curr_index += 2

good_ones = []
for poss in frontier:
    if eval(''.join(poss)) == 0:  # eval breaks the game lol
        for v, s in enumerate(poss):
            if s == '':
                poss[v] = ' '
        good_ones.append(''.join(poss))

good_ones.sort()
with open('outputs.txt', 'w') as written:
    for good in good_ones:
        written.write(good + '\n')
