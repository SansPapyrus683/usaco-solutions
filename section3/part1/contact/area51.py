"""
ID: kevinsh4
TASK: contact
LANG: PYTHON3
"""
from collections import defaultdict

def chunks(lst, n):
    for i in range(0, len(lst), n):
        yield lst[i:i + n]

detected = ''
with open('cantStopAll.txt') as read:
    for v, line in enumerate(read):
        if v == 0:
            minLen, maxLen, outputNumber = [int(i) for i in line.rstrip().split()]
        else:
            detected += line.rstrip()

freqRecord = defaultdict(lambda: 0)
for v, c in enumerate(detected):  # go through each character and process the trailing ones
    for i in range(minLen, maxLen + 1):
        if v >= i-1:  # prevent it from going wack and trying to access negative indices
            if detected[v-i+1: v+1] == '': print(v)  # i know negatives work, but it'll gimme the wrong ans
            freqRecord[detected[v-i+1: v+1]] += 1

newRecord = defaultdict(lambda: [])
for f in freqRecord.items():
    newRecord[f[1]].append(f[0])
freqRecord = newRecord

with open('outputs.txt', 'w') as written:
    for _ in range(outputNumber):
        try:
            frequency = max(freqRecord)
        except ValueError:  # if there aren't anymore just stop...
            break
        written.write(str(frequency) + '\n')

        valid = freqRecord[frequency]
        valid.sort(key=lambda a: len(a))  # sort it by length first
        lengthBuckets = []
        buildUp = [valid[0]]
        for v, f in enumerate(valid[1:]):
            v += 1
            if len(f) != len(valid[v - 1]):  # putting the sequences of each length in their buckets
                lengthBuckets.append(buildUp)
                buildUp = []
            buildUp.append(f)
        if buildUp:
            lengthBuckets.append(buildUp)

        newValid = []
        for b in lengthBuckets:
            newValid.extend(sorted(b))  # add the sorted (bitwise this time) to the official thing

        for c in chunks(newValid, 6):
            written.write(' '.join(c) + '\n')

        del freqRecord[frequency]
