from bisect import bisect_left, bisect_right

# 2015 dec gold
with open('cardgame.in') as read:
    cardNum = int(read.readline()) * 2
    elsieTotalCards = [int(read.readline()) for _ in range(cardNum // 2)]
bessieTotalCards = list({i for i in range(1, cardNum + 1)}.difference(set(elsieTotalCards)))
bessieTotalCards.sort()

# probably optimal for bessie to play all her highest cards in the first round
bessieCards = bessieTotalCards[cardNum // 4:]  # don't worry, it's always divisible by 4
elsieCards = elsieTotalCards[:cardNum // 4]

points = 0
for c in elsieCards:  # same logic as the silver problem for the first half
    supposed_card_loc = bisect_right(bessieCards, c)
    if supposed_card_loc >= len(bessieCards):
        del bessieCards[0]
        continue
    points += 1
    del bessieCards[supposed_card_loc]

bessieCards = bessieTotalCards[:cardNum // 4]
elsieCards = elsieTotalCards[cardNum // 4:]
for c in elsieCards:  # the logic gets twisted around a bit now
    supposed_card_loc = bisect_left(bessieCards, c)
    if supposed_card_loc == 0:  # if bessie can't play any lower, just screw it
        del bessieCards[-1]  # play off our highest card cuz there's no hope anyways
        continue
    points += 1
    del bessieCards[supposed_card_loc - 1]  # -1 because it's bisect_left

print(points)
with open('cardgame.out', 'w') as written:
    written.write(str(points) + '\n')
