with open('circlecross.in') as read:
    circle = read.readline().strip().upper()

crossings = 0
processed = set()
for c in circle:
    if circle.count(c) != 2:
        raise ValueError("a cow should appear exactly twice so i think your input's invalid")
    if c in processed:
        continue

    start = circle.index(c)
    end = circle.rindex(c)
    between = circle[start + 1:end]
    for withinC in between:
        if between.count(withinC) == 1:
            crossings += 1
    processed.add(c)
crossings //= 2

print(crossings)
print(crossings, file=open('circlecross.out', 'w'))
