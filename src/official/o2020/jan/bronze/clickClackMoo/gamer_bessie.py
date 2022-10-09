# 2020 jan bronze
with open('word.in') as read:
    max_width = int(read.readline().split()[1])
    essay = read.readline().split()

formatted = ""
rn_length = 0
for w in essay:
    # ok, this word exceeds the limit, let's wrap it around
    if rn_length + len(w) > max_width:
        formatted = formatted[:-1] + '\n'
        rn_length = 0
    formatted += w + ' '
    rn_length += len(w)
formatted = formatted[:-1]

with open('word.out', 'w') as written:
    for line in formatted.split('\n'):
        print(line, file=written)
