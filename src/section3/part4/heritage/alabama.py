"""
ID: kevinsh4
TASK: heritage
LANG: PYTHON3
"""
with open('heritage.in') as read:
    inorder = list(read.readline().strip())  # the left, root, right
    preorder = list(read.readline().strip())  # the root, left, right


# postorder is left, right, root
def postorder(inorder: [str], preorder: [str]) -> str:
    assert len(inorder) == len(preorder)
    if len(inorder) <= 2:
        return ''.join(reversed(preorder))
    root = preorder[0]
    left_tree = inorder[:inorder.index(root)]
    right_tree = inorder[inorder.index(root) + 1:]

    cutoff = len(preorder)  # if the if never evaluates to true, there is not right tree lol
    for i in range(1, len(preorder)):
        if preorder[i] not in left_tree:
            cutoff = i
            break
    return postorder(left_tree, preorder[1:cutoff]) + postorder(right_tree, preorder[cutoff:]) + root


ans = postorder(inorder, preorder)
with open('heritage.out', 'w') as written:
    written.write(str(ans) + "\n")
    print(ans)
