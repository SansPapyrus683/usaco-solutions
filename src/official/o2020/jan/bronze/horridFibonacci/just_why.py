# 2020 jan bronze
def deduce(initial, first):
    restored = [first]
    had_before = {first}
    for p in initial:
        supposed = p - restored[-1]
        if supposed in had_before or supposed <= 0:
            return [float('inf')]
        restored.append(supposed)
        had_before.add(supposed)
    return restored


with open('photo.in') as read:
    read.readline()
    recorded = [int(i) for i in read.readline().split()]

arrangement = [float('inf')]
for i in range(1, recorded[0]):
    arrangement = deduce(recorded, i)
    if arrangement != [float('inf')]:
        break

print(arrangement)
with open('photo.out', 'w') as written:
    written.write(' '.join([str(i) for i in arrangement]) + '\n')
