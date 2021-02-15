occurrences = {}
with open('records.in') as read:
    for _ in range(int(read.readline())):
        group = tuple(sorted(read.readline().split()))
        if group not in occurrences:
            occurrences[group] = 0
        occurrences[group] += 1

most_group_occ = max(occurrences.values())
print(most_group_occ)
print(most_group_occ, file=open('records.out', 'w'))
