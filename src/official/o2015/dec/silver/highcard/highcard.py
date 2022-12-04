from bisect import bisect_right

# 2015 dec silver
with open('highcard.in') as read:
    elsie = sorted([int(read.readline()) for _ in range(int(read.readline()))])
bessie = list({i for i in range(1, 2 * len(elsie) + 1)}.difference(set(elsie)))
bessie.sort()

points = 0
for c in elsie:
    # binsearch for the lowest card that bessie can play that's larger than elsie's
    supposed_card_loc = bisect_right(bessie, c)
    if supposed_card_loc >= len(bessie):  # larger than all cards? screw this
        del bessie[0]  # ok well might as well get rid of our lowest card (code still works w/o this line lol)
        continue
    points += 1
    del bessie[supposed_card_loc]

with open('highcard.out', 'w') as written:
    written.write(str(points) + '\n')
