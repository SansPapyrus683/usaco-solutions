# 2020 jan bronze
def peakTotal(peak, end):
    return peak * (peak + 1) // 2 + ((peak - 1 - end + 1) * (end + peak - 1)) // 2


def peakHeight(dist, end):  # given dist and end, find the maximum peak so we <= the dist
    lowerBound = 1
    upperBound = dist
    height_ = -1
    while lowerBound <= upperBound:  # binsearch for the peak
        toSearch = (lowerBound + upperBound) // 2
        if peakTotal(toSearch, end) <= dist:
            height_ = toSearch
            lowerBound = toSearch + 1
        else:
            upperBound = toSearch - 1
    return height_


# the minimum amt of numbers we have to use (numbers can range from 1 to maxNum) to get to sumTo
def minSumNum(maxNum, sumTo):
    if sumTo == 0:
        return 0
    if maxNum >= sumTo:
        return 1
    return sumTo // maxNum + 1


with open('race.in') as read:
    distance, queryNum = [int(i) for i in read.readline().split()]
    queries = [int(read.readline()) for _ in range(queryNum)]

maxSpeedUp = 0
while True:  # we can only speed up so much before we hit the finish line, let's see what that bound is
    maxSpeedUp += 1
    if maxSpeedUp * (maxSpeedUp + 1) // 2 > distance:
        maxSpeedUp -= 1
        break

minTimes = []
for q in queries:
    q = min(q, maxSpeedUp)
    height = peakHeight(distance, q)
    minTimes.append(int((2 * height - q) +  # initial time (straight to the peak and down to the end)
                        minSumNum(height, distance - peakTotal(height, q))))  # padding to get to the finish line

print(minTimes)
with open('race.out', 'w') as written:
    for t in minTimes:
        written.write(str(int(t)) + '\n')
