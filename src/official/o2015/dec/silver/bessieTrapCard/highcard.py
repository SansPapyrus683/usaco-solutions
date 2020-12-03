from bisect import bisect_right

# 2015 dec silver
with open('highcard.in') as read:
    elsiesCards = sorted([int(read.readline()) for _ in range(int(read.readline()))])
bessiesCards = list({i for i in range(1, 2 * len(elsiesCards) + 1)}.difference(set(elsiesCards)))
bessiesCards.sort()

points = 0
for c in elsiesCards:
    # binsearch for the lowest card that bessie can play that's larger than elsie's
    supposed_card_loc = bisect_right(bessiesCards, c)
    if supposed_card_loc >= len(bessiesCards):  # larger than all cards? screw this
        del bessiesCards[0]  # ok well might as well get rid of our lowest card (code still works w/o this line lol)
        continue
    points += 1
    del bessiesCards[supposed_card_loc]

with open('highcard.out', 'w') as written:
    written.write(str(points) + '\n')
