"""
ID: kevinsh4
TASK: beads
LANG: PYTHON3
"""
with open('newOrleans.txt') as read:
    bead_len = int(read.readline())
    beads = read.readline().rstrip()
    assert bead_len == len(beads), 'you thought you could get away with bad input? think again'

beads = beads * 2
max_len = 0
for i in range(bead_len // 2, bead_len + bead_len // 2):  # test all the possible split indices
    left_index = i
    left_color = beads[left_index]  # go left and right until we can't anymore
    while left_index > 0 and (beads[left_index - 1] == left_color or 'w' in [left_color, beads[left_index - 1]]):
        left_index -= 1
        if beads[left_index] != 'w':
            left_color = beads[left_index]

    right_ind = i + 1
    right_color = beads[right_ind]
    while right_ind < 2 * bead_len - 1 and \
            (beads[right_ind + 1] == right_color or 'w' in [right_color, beads[right_ind + 1]]):
        right_ind += 1
        if beads[right_ind] != 'w':
            right_color = beads[right_ind]

    max_len = max(max_len, min(bead_len, right_ind - left_index + 1))

print(max_len)
print(max_len, file=open('outputs.txt', 'w'))
