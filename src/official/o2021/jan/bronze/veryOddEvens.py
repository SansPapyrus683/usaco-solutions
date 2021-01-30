"""
2021 jan gold
7
1 3 5 7 9 11 13 should output 3
7
11 2 17 13 1 15 3 should output 5
1st should be even, 2nd should be odd, 3rd should be even...
"""
input()
cows = [bool(int(i) % 2) for i in input().split()]  # false is even, true is odd
evenAmt = cows.count(False)
oddAmt = len(cows) - evenAmt

# merge enough odds into evens so that we can distribute the cows as needed
while evenAmt < oddAmt:
    oddAmt -= 2
    evenAmt += 1
evenAmt = min(evenAmt, oddAmt + 1)  # i'm p sure this is needed
print(evenAmt + oddAmt)
