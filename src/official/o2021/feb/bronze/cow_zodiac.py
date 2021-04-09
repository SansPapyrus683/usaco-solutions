"""
2021 feb bronze
4
Mildred born in previous Dragon year from Bessie
Gretta born in previous Monkey year from Mildred
Elsie born in next Ox year from Gretta
Paulina born in next Dog year from Bessie should output 12
"""
from collections import namedtuple

ZODIAC = ['OX', 'TIGER', 'RABBIT', 'DRAGON', 'SNAKE', 'HORSE', 'GOAT', 'MONKEY', 'ROOSTER', 'DOG', 'PIG', 'RAT']
Cow = namedtuple('Cow', ['name', 'previous', 'year', 'relative'])

names = {'BESSIE'}
relations = []
for _ in range(int(input())):
    relation = input().upper().split()
    cow = Cow(relation[0], relation[3] == 'PREVIOUS', ZODIAC.index(relation[4]), relation[7])
    assert cow.relative in names, 'the base cow has to be mentioned alr'
    assert cow.name not in names, 'and the cow itself has to be new'
    relations.append(cow)
    names.add(cow.name)

birth_years = {'BESSIE': 0}
for r in relations:
    change = -1 if r.previous else 1
    # +change because it has to be at least 1 year off
    this_year = birth_years[r.relative] + change
    while this_year % len(ZODIAC) != r.year:
        this_year += change
    birth_years[r.name] = this_year

print(abs(birth_years['BESSIE'] - birth_years['ELSIE']))
