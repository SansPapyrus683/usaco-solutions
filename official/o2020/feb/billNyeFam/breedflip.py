# 2020 feb bronze
with open('breedflip.in') as read:
    for v, line in enumerate(read):
        if v == 1:
            needed = tuple(c == 'H' for c in line.rstrip())  # holsteins be false, guernseys be true
        elif v == 2:
            haveRN = tuple(c == 'H' for c in line.rstrip())

differenceList = [v for v, c in enumerate(haveRN) if needed[v] != c]  # true if different, false it not
print(differenceList)
consec = []
curr = []
firstTime = True
for v, b in enumerate(differenceList):
    if firstTime:
        curr.append(b)
        firstTime = False
    else:
        if b - 1 != curr[-1]:
            consec.append(curr)
            curr = [b]
        else:
            curr.append(b)

if curr:
    consec.append(curr)

with open('breedflip.out', 'w') as written:
    written.write(str(len(consec)) + '\n')
