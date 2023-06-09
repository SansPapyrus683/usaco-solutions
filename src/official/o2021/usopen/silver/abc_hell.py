# 2021 us open silver (input ommitted bc length)
from itertools import combinations_with_replacement, permutations

for _ in range(int(input())):
    size = int(input())
    arr = [int(i) for i in input().split()]
    assert size == len(arr) and 4 <= size <= 7, "invalid input or smth"

    """
    so like the editorial says
    we can put 0 in the things and then organize the numbers as follows
    0 -> 0 + A, B -> B + A, C -> C + A, and B + C -> A + B + C
    notice that like we can get A if we have any pair
    like B -> B + A -> we get A, yeah yeah
    we can deduce a possibility for the individual variables through pure brute force
    """
    arr = sorted([0] + arr)
    var_poss = set()
    inds = set(range(len(arr)))
    # get all possibilities for the right hand side of the pairs
    for top_ind in permutations(inds, 4):
        # what we have of the left side
        bottom_ind = list(inds - set(top_ind))
        diffs = [arr[top_ind[i]] - arr[bottom_ind[i]] for i in range(len(bottom_ind))]
        # they have to be all the same for the variable to be a possibility
        if len(set(diffs)) == 1 and diffs[0] >= 1:
            var_poss.add(diffs[0])

    arr.remove(0)
    valid = 0
    var_poss = sorted(var_poss)
    # then just brute force all possible variable combinations and see which ones work
    for a, b, c in combinations_with_replacement(var_poss, 3):
        reqs = arr.copy()
        for total in [a, b, c, a + b, b + c, a + c, a + b + c]:
            if total in reqs:
                reqs.remove(total)
        valid += not reqs
    print(valid)
