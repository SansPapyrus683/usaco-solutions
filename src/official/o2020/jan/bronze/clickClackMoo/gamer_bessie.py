# 2020 jan bronze
with open('word.in') as read:
    max_width = int(read.readline().split()[1])
    essay = read.readline().split()

formatted = ""
rn_length = 0
for v, w in enumerate(essay):
    if rn_length + len(w) > max_width:  # ok, this word exceeds the limit, let's wrap it around
        formatted += "\n"
        rn_length = 0
    formatted += w + ' '
    rn_length += len(w)

with open('word.out', 'w') as written:
    for line in formatted.split("\n"):
        print(line.strip())
        written.write(str(line.strip()) + '\n')
