with open('milktemp.in') as read:
    cow_num, cold, comfortable, hot = [int(i) for i in read.readline().split()]
    cows = [[int(i) for i in read.readline().split()] for _ in range(cow_num)]

endpoints = []
for c in cows:
    endpoints.extend([[c[0], 1], [c[1], 2]])  # 1 is start point, 2 is endpoint
endpoints.sort()

# this is kinda llike a line sweep approach
# we go through all the endpoints of all the cows and then move the thermostat up and up
# then we just take the running max
milk_production = len(cows) * cold
max_milk = -1
for p in endpoints:
    if p[1] == 1:
        milk_production += comfortable - cold
    else:
        milk_production -= comfortable - hot
    max_milk = max(max_milk, milk_production)

print(max_milk)
print(max_milk, file=open('milktemp.out', 'w'))
