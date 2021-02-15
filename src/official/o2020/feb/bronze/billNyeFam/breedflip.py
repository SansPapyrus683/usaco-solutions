# 2020 feb bronze
with open('breedflip.in') as read:
    for v, line in enumerate(read):
        if v == 1:
            needed = tuple(c == 'H' for c in line.rstrip())  # holsteins be false, guernseys be true
        elif v == 2:
            have_rn = tuple(c == 'H' for c in line.rstrip())

diff_list = [v for v, c in enumerate(have_rn) if needed[v] != c]  # true if different, false it not

consec = []
curr = []
firstTime = True
for v, b in enumerate(diff_list):
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
