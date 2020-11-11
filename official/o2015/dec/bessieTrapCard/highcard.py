from bisect import bisect_right

with open('highcard.in') as read:
    elsies_cards = sorted([int(read.readline()) for _ in range(int(read.readline()))])
bessies_cards = list({i for i in range(1, 2 * len(elsies_cards) + 1)}.difference(set(elsies_cards)))
bessies_cards.sort()

points = 0
for c in elsies_cards:
    supposed_card_loc = bisect_right(bessies_cards, c)
    if supposed_card_loc >= len(bessies_cards):
        continue
    card_to_play = bessies_cards[supposed_card_loc]
    points += 1
    if supposed_card_loc == len(bessies_cards) - 1:
        break
    del bessies_cards[supposed_card_loc]

with open('highcard.out', 'w') as written:
    written.write(str(points) + '\n')
