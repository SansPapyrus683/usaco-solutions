# 2016 dec bronze
with open('square.in') as read:
    first = [int(i) for i in read.readline().split()]
    second = [int(i) for i in read.readline().split()]

min_area = max(max(first[2], second[2]) - min(first[0], second[0]),
              max(first[3], second[3]) - min(first[1], second[1])) ** 2
print(min_area)
print(min_area, file=open('square.out', 'w'))
