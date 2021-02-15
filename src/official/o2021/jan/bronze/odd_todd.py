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
even_amt = cows.count(False)
odd_amt = len(cows) - even_amt

# merge enough odds into evens so that we can distribute the cows as needed
while even_amt < odd_amt:
    odd_amt -= 2
    even_amt += 1
even_amt = min(even_amt, odd_amt + 1)  # i'm p sure this is needed
print(even_amt + odd_amt)
