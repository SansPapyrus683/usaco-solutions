"""
ID: kevinsh4
TASK: namenum
LANG: PYTHON3
"""
with open('cowNames.txt') as read:
    cowID = read.readline().rstrip()
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
possNames = ['']
for i in cowID:
    nextNames = []
    for c in numChars[int(i)]:
        for n in possNames:
            nextNames.append(n + c)
    possNames = nextNames
validNames = sorted(names.intersection(set(possNames)))

print(validNames)
with open('outputs.txt', 'w') as written:
    if validNames:
        for n in validNames:
            written.write(n + '\n')
    else:
        written.write('NONE\n')
