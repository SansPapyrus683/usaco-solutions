# 2020 jan bronze
def peak_total(peak, end):
    return peak * (peak + 1) // 2 + ((peak - 1 - end + 1) * (end + peak - 1)) // 2


def peak_height(dist, end):  # given dist and end, find the maximum peak so we <= the dist
    lo = 1
    hi = dist
    height_ = -1
    while lo <= hi:  # binsearch for the peak
        mid = (lo + hi) // 2
        if peak_total(mid, end) <= dist:
            height_ = mid
            lo = mid + 1
        else:
            hi = mid - 1
    return height_


# the minimum amt of numbers we have to use (numbers can range from 1 to maxNum) to get to sum_to
def min_sum_num(max_num, sum_to):
    if sum_to == 0:
        return 0
    if max_num >= sum_to:
        return 1
    return sum_to // max_num + 1


with open('race.in') as read:
    distance, query_num = [int(i) for i in read.readline().split()]
    queries = [int(read.readline()) for _ in range(query_num)]

max_speed_up = 0
while True:  # we can only speed up so much before we hit the finish line, let's see what that bound is
    max_speed_up += 1
    if max_speed_up * (max_speed_up + 1) // 2 > distance:
        max_speed_up -= 1
        break

min_times = []
for q in queries:
    q = min(q, max_speed_up)
    height = peak_height(distance, q)
    min_times.append(int((2 * height - q) +  # initial time (straight to the peak and down to the end)
                         min_sum_num(height, distance - peak_total(height, q))))  # padding to get to the finish line

print(min_times)
with open('race.out', 'w') as written:
    for t in min_times:
        written.write(str(int(t)) + '\n')
