# 2020 feb bronze
with open("breedflip.in") as read:
    cow_num = int(read.readline())
    needed = read.readline().strip()
    have_rn = read.readline().strip()
    assert len(needed) == len(have_rn) == cow_num

is_diff = [n != h for n, h in zip(needed, have_rn)]
consec_num = 0
for i in range(1, cow_num):
    consec_num += not is_diff[i] and is_diff[i - 1]
consec_num += is_diff[-1]

print(consec_num, file=open("breedflip.out", "w"))
