# 2020 jan bronze
def peakTotal(peak, end):
    return peak * (peak + 1) // 2 + ((peak - 1 - end + 1) * (end + peak - 1)) // 2


def peakHeight(dist, end):  # given dist and end, find the maximum peak so we <= the dist
    # sometimes we actually accelerate to the finish line before we get to the end speed
    # so we binsearch for the least time that still stays below
    validSoFar = end
    lowerBound = 1
    upperBound = end
    while lowerBound <= upperBound:
        toSearch = (lowerBound + upperBound) // 2
        if toSearch * (toSearch + 1) // 2 <= dist:
            lowerBound = toSearch + 1
            validSoFar = toSearch
        else:
            upperBound = toSearch - 1
    end = validSoFar

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
    return height_, end


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

minTimes = []
for q in queries:
    height, endSpeed = peakHeight(distance, q)
    minTimes.append(int((2 * height - endSpeed) +  # initial time (straight to the peak and down to the end)
                        minSumNum(height, distance - peakTotal(height, endSpeed))))  # padding to get to the finish line

print(minTimes)
with open('race.out', 'w') as written:
    for t in minTimes:
        written.write(str(int(t)) + '\n')
