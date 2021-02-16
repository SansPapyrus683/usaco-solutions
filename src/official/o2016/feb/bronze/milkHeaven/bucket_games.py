# 2016 feb bronze
first, second, target = [int(i) for i in open('pails.in').read().split()]

possible = [True] + [False for _ in range(target)]
for v, t in enumerate(possible):
    if not t:
        continue
    for reachable in [v + first, v + second]:
        if reachable <= target:
            possible[reachable] = True

for i in range(target, -1, -1):
    if possible[i]:
        print(i)
        print(i, file=open('pails.out', 'w'))
        break
