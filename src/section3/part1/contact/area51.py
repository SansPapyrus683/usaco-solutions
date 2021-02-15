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
            min_len, max_len, output_number = [int(i) for i in line.rstrip().split()]
        else:
            detected += line.rstrip()

freq_record = defaultdict(lambda: 0)
for v, c in enumerate(detected):  # go through each character and process the trailing ones
    for i in range(min_len, max_len + 1):
        if v >= i - 1:  # prevent it from going wack and trying to access negative indices
            if detected[v - i + 1: v + 1] == '': print(v)  # i know negatives work, but it'll gimme the wrong ans
            freq_record[detected[v - i + 1: v + 1]] += 1

new_record = defaultdict(lambda: [])
for f in freq_record.items():
    new_record[f[1]].append(f[0])
freq_record = new_record

with open('outputs.txt', 'w') as written:
    for _ in range(output_number):
        try:
            frequency = max(freq_record)
        except ValueError:  # if there aren't anymore just stop...
            break
        written.write(str(frequency) + '\n')

        valid = freq_record[frequency]
        valid.sort(key=lambda a: len(a))  # sort it by length first
        length_buckets = []
        build_up = [valid[0]]
        for v, f in enumerate(valid[1:]):
            v += 1
            if len(f) != len(valid[v - 1]):  # putting the sequences of each length in their buckets
                length_buckets.append(build_up)
                build_up = []
            build_up.append(f)
        if build_up:
            length_buckets.append(build_up)

        new_valid = []
        for b in length_buckets:
            new_valid.extend(sorted(b))  # add the sorted (bitwise this time) to the official thing

        for c in chunks(new_valid, 6):
            written.write(' '.join(c) + '\n')

        del freq_record[frequency]
