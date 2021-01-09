occurrences = {}
with open('records.in') as read:
    for _ in range(int(read.readline())):
        group = tuple(sorted(read.readline().split()))
        if group not in occurrences:
            occurrences[group] = 0
        occurrences[group] += 1

mostGroupOcc = max(occurrences.values())
print(mostGroupOcc)
print(mostGroupOcc, file=open('records.out', 'w'))
