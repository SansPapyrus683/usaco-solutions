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
    leftTree = inorder[:inorder.index(root)]
    rightTree = inorder[inorder.index(root) + 1:]

    cutoff = len(preorder)  # if the if never evaluates to true, there is not right tree lol
    for i in range(1, len(preorder)):
        if preorder[i] not in leftTree:
            cutoff = i
            break
    return postorder(leftTree, preorder[1:cutoff]) + postorder(rightTree, preorder[cutoff:]) + root


ans = postorder(inorder, preorder)
with open('heritage.out', 'w') as written:
    written.write(str(ans) + "\n")
    print(ans)
