"""
ID: kevinsh4
TASK: vans
LANG: PYTHON3

according to https://www.emis.de/journals/SLC/wpapers/s34erlangen.pdf
we can expand 1/(-x^4+2x^3-2x^2-2x+1) as a generaing function,
whatever that is (m=3 since they start from 0)

to actually get the gen func from the rational fraction
i used this: https://stackoverflow.com/a/23093790/12128483
"""

with open("vans.in") as read:
    n = int(read.readline())

left = {0: 1}
divisor = [1, -2, -2, 2, -1]
needed = 0
for gen in range(n - 1):
    needed = left[gen] // divisor[0]
    del left[gen]
    for i in range(1, len(divisor)):
        if gen + i not in left:
            left[gen + i] = 0
        left[gen + i] -= needed * divisor[i]

# *2 since the problem counts direction
print(needed * 2, file=open("vans.out", "w"))
