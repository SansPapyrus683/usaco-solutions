# 2020 jan silver
with open('berries.in') as read:
    for v, l in enumerate(read):
        if v == 0:
            basket_num = int(l.split()[1])
        else:
            trees = [int(i) for i in l.split()]
            best_tree = max(trees)
            trees.sort(reverse=True)
            trees = trees[:basket_num]  # bessie can only fill so many baskets anyways

max_berries = 0
for b in range(1, best_tree + 1):  # just try to give elsie no more than bessie bc why would we do that
    temp_trees = trees.copy()
    baskets_left = basket_num
    gotten_berries = 0
    for v, t in enumerate(temp_trees):
        if not baskets_left:  # ran out of baskets to fill with just b berries
            break

        for _ in range(t // b):
            if not baskets_left:
                break

            if baskets_left <= basket_num // 2:
                gotten_berries += b
            temp_trees[v] -= b
            baskets_left -= 1
    else:
        if baskets_left:
            temp_trees.sort(reverse=True)
            for t in temp_trees:
                if not baskets_left:
                    break
                if baskets_left <= basket_num // 2:
                    gotten_berries += t
                baskets_left -= 1

    max_berries = max(max_berries, gotten_berries)

print(max_berries)
print(max_berries, file=open('berries.out', 'w'))
