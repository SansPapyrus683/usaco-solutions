from bisect import bisect_left, bisect_right

# 2015 dec gold
with open('cardgame.in') as read:
    card_num = int(read.readline()) * 2
    assert not card_num % 4, "the card number should be divisble by 4"
    elsie = [int(read.readline()) for _ in range(card_num // 2)]
bessie = list({i for i in range(1, card_num + 1)}.difference(set(elsie)))
bessie.sort()

# probably optimal for bessie to play all her highest cards in the first round
bessie_cards = bessie[card_num // 4:]
elsie_cards = elsie[:card_num // 4]

points = 0
for c in elsie_cards:  # same logic as the silver problem for the first half
    supposed_card_loc = bisect_right(bessie_cards, c)
    if supposed_card_loc >= len(bessie_cards):
        del bessie_cards[0]
        continue
    points += 1
    del bessie_cards[supposed_card_loc]

bessie_cards = bessie[:card_num // 4]
elsie_cards = elsie[card_num // 4:]
for c in elsie_cards:  # the logic gets twisted around a bit now
    supposed_card_loc = bisect_left(bessie_cards, c)
    if supposed_card_loc == 0:  # if bessie can't play any lower, just screw it
        del bessie_cards[-1]  # play off our highest card cuz there's no hope anyways
        continue
    points += 1
    del bessie_cards[supposed_card_loc - 1]  # -1 because it's bisect_left

print(points)
print(points, file=open('cardgame.out', 'w'))
