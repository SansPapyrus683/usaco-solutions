"""
ID: kevinsh4
TASK: namenum
LANG: PYTHON3
"""
with open('cowNames.txt') as read:
    cow_id = read.readline().rstrip()
with open('dict.txt') as names:
    names = set(names.read().split('\n'))

numChars = [
    None, None,  # 0 and 1 are invalid apparently
    ['A', 'B', 'C'],
    ['D', 'E', 'F'],
    ['G', 'H', 'I'],
    ['J', 'K', 'L'],
    ['M', 'N', 'O'],
    ['P', 'R', 'S'],
    ['T', 'U', 'V'],
    ['W', 'X', 'Y']
]
poss_names = ['']
for i in cow_id:
    next_name = []
    for c in numChars[int(i)]:
        for n in poss_names:
            next_name.append(n + c)
    poss_names = next_name
valid_names = sorted(names.intersection(set(poss_names)))

print(valid_names)
with open('outputs.txt', 'w') as written:
    if valid_names:
        for n in valid_names:
            written.write(n + '\n')
    else:
        written.write('NONE\n')
