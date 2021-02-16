# 2016 feb gold
with open('cbarn.in') as read:
    rooms = [int(read.readline()) for _ in range(int(read.readline()))]

# i could code golf this to hell and back but i decided not to
best = float('inf')
for v, r in enumerate(rooms):
    total = sum(dist * rooms[(v + dist) % len(rooms)] for dist in range(1, len(rooms)))
    best = min(best, total)

print(best)
print(best, file=open('cbarn.out', 'w'))
