with open('cownomics.in') as read:
    type_num, genome_len = [int(i) for i in read.readline().split()]
    spotty = [read.readline().strip().upper() for _ in range(type_num)]
    plain = [read.readline().strip().upper() for _ in range(type_num)]

valid = 0
for i in range(genome_len):
    spotty_letters = {g[i] for g in spotty}
    plain_letters = {g[i] for g in plain}
    if spotty_letters.isdisjoint(plain_letters):
        valid += 1

print(valid)
print(valid, file=open('cownomics.out', 'w'))
