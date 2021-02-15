"""
ID: kevinsh4
TASK: prefix
LANG: PYTHON3
"""


def get_valid(the_prefixes, huge_molecule):
    possible = [False for _ in range(len(huge_molecule) + 1)]  # this[i] = whether prefix of len i can be made
    possible[0] = True  # i mean we can always make a prefix of len 0

    for up_to in range(len(huge_molecule) + 1):
        for poss in the_prefixes:
            if up_to - len(poss) < 0:
                continue
            # if the previous one is possible, see if we can just tack poss onto the prev to get another prefix
            if possible[up_to - len(poss)] and huge_molecule[up_to - len(poss): up_to] == poss:
                possible[up_to] = True
                break
    return possible


prefixes = set()
molecule = ''
with open('bio_trash.txt') as read:
    detect_prefix = True
    for v, line in enumerate(read):
        if line.rstrip() == '.':
            detect_prefix = False
            continue
        if detect_prefix:
            for s in line.rstrip().split():
                prefixes.add(s)
        else:
            molecule += line.rstrip()

reachable = get_valid(prefixes, molecule)
longest = 0
for i in range(len(reachable) - 1, -1, -1):
    if reachable[i]:
        longest = i
        break

print(longest)
with open('outputs.txt', 'w') as written:
    written.write(str(longest) + '\n')
