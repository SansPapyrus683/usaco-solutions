"""
ID: kevinsh4
TASK: fracdec
LANG: PYTHON3
"""
n, d = [int(i) for i in open('fractionsBetter.txt').read().rstrip().split()]
intPart = 0
while n >= d:  # get out the whole number part
    intPart += 1
    n -= d

remains = [n * 10]
memberCheck = {n * 10}  # set member checks faster
newRemain = n * 10
decPart = ''
while True:
    decimal = 0
    while newRemain >= d:
        newRemain -= d
        decimal += 1
    newRemain *= 10  # "drop the zero", which is really just mul-ing the inital one by 10
    decPart += str(decimal)
    if newRemain in memberCheck:  # did we reach something already reached?
        for v, c in enumerate(remains):
            if newRemain == c:  # see where to cut the repeat off
                repeatStart = v
        break
    remains.append(newRemain)
    memberCheck.add(newRemain)


def chunks(lst, n):
    for i in range(0, len(lst), n):
        yield lst[i: i + n]


with open('outputs.txt', 'w') as written:
    if decPart[repeatStart:] != '0':
        output = str(intPart) + '.' + decPart[:repeatStart] + '(' + decPart[repeatStart:] + ')'
    else:
        if not decPart[:-1]:  # add a zero to prevent outputs like 3.
            decPart += '0'
        output = str(intPart) + '.' + decPart[:-1]
    for l in chunks(output, 76):
        written.write(l + '\n')
