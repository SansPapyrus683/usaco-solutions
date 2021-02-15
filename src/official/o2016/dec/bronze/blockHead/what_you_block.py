from string import ascii_lowercase

# 2016 dec bronze
words = []
with open('blocks.in') as read:
    for i in range(int(read.readline())):
        words.append(read.readline().rstrip().split())

needed = []
for c in ascii_lowercase:  # for each character calculate the "worst case scenario"
    max_count = 0
    for w in words:
        max_count += max(w[0].count(c), w[1].count(c))
    needed.append(max_count)

with open('blocks.out', 'w') as written:
    for i in needed:
        print(i)
        written.write(str(i) + '\n')
