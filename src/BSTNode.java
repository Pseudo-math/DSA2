import java.io.*;
import java.util.*;
import java.util.stream.*;


class BSTNode<T>
{
    public int NodeKey; // ключ узла
    public T NodeValue; // значение в узле
    public BSTNode<T> Parent; // родитель или null для корня
    public BSTNode<T> LeftChild; // левый потомок
    public BSTNode<T> RightChild; // правый потомок	

    public BSTNode(int key, T val, BSTNode<T> parent)
    {
        NodeKey = key;
        NodeValue = val;
        Parent = parent;
        LeftChild = null;
        RightChild = null;
    }

    public boolean NodeEquals(BSTNode<T> otherNode) {
        if (this == otherNode)
            return true;
        return NodeKey == otherNode.NodeKey && this.NodeValue.equals(otherNode.NodeValue);
    }
}

// промежуточный результат поиска
class BSTFind<T>
{
    // null если в дереве вообще нету узлов
    public BSTNode<T> Node;

    // true если узел найден
    public boolean NodeHasKey;

    // true, если родительскому узлу надо добавить новый левым
    public boolean ToLeft;

    public BSTFind() { Node = null; }
}

class BST<T>
{
    BSTNode<T> Root; // корень дерева, или null
    int Count;

    public BST(BSTNode<T> node)
    {
        Root = node;
        Count = (node == null) ? 0 : 1;
    }

    public BSTFind<T> FindNodeByKey(int key)
    {
        if (Root == null)
            return new BSTFind<>();
        if (Root.NodeKey == key) {
            var finded = new BSTFind<T>();
            finded.Node = Root;
            finded.NodeHasKey = true;
            return finded;
        }
        if (key < Root.NodeKey && Root.LeftChild == null) {
            var finded = new BSTFind<T>();
            finded.Node = Root;
            finded.NodeHasKey = false;
            finded.ToLeft = true;
            return finded;
        }
        if (key > Root.NodeKey && Root.RightChild == null) {
            var finded = new BSTFind<T>();
            finded.Node = Root;
            finded.NodeHasKey = false;
            finded.ToLeft = false;
            return finded;
        }
        var rootOfSubtree = key < Root.NodeKey ? Root.LeftChild : Root.RightChild;
        return (new BST<>(rootOfSubtree)).FindNodeByKey(key);
    }

    public boolean AddKeyValue(int key, T val)
    {
        var whereNeedAdd = FindNodeByKey(key);
        if (whereNeedAdd.Node == null) {
            Root = new BSTNode<>(key, val, null);
            Count++;
            return true;
        }
        if (whereNeedAdd.NodeHasKey)
            return false;
        var addingNode = new BSTNode<>(key, val, whereNeedAdd.Node);
        if (whereNeedAdd.ToLeft)
            whereNeedAdd.Node.LeftChild = addingNode;
        else
            whereNeedAdd.Node.RightChild = addingNode;
        Count++;
        return true;
    }

    public BSTNode<T> FinMinMax(BSTNode<T> FromNode, boolean FindMax)
    {
        if (FromNode == null)
            return null;
        if (FindMax && FromNode.RightChild == null || !FindMax && FromNode.LeftChild == null)
            return FromNode;
        if (FindMax)
            return FinMinMax(FromNode.RightChild, FindMax);
        else
            return FinMinMax(FromNode.LeftChild, FindMax);
    }

    public boolean DeleteNodeByKey(int key) {
        BSTFind<T> foundNode = FindNodeByKey(key);
        if (!foundNode.NodeHasKey || foundNode.Node == null)
            return false;

        BSTNode<T> deletingNode = foundNode.Node;

        if (deletingNode.LeftChild != null && deletingNode.RightChild != null) {
            BSTNode<T> heir = FinMinMax(deletingNode.RightChild, false);

            if (heir.RightChild != null) {
                heir.RightChild.Parent = heir.Parent;
                if (heir.Parent.LeftChild == heir) {
                    heir.Parent.LeftChild = heir.RightChild;
                } else {
                    heir.Parent.RightChild = heir.RightChild;
                }
            } else {
                if (heir.Parent.LeftChild == heir) {
                    heir.Parent.LeftChild = null;
                } else {
                    heir.Parent.RightChild = null;
                }
            }

            if (deletingNode.Parent == null) {
                Root = heir;
            } else if (deletingNode.Parent.LeftChild == deletingNode) {
                deletingNode.Parent.LeftChild = heir;
            } else {
                deletingNode.Parent.RightChild = heir;
            }

            heir.Parent = deletingNode.Parent;

            heir.LeftChild = deletingNode.LeftChild;
            if (heir.LeftChild != null)
                heir.LeftChild.Parent = heir;
            heir.RightChild = deletingNode.RightChild;
            if (heir.RightChild != null)
                heir.RightChild.Parent = heir;

            Count--;
            return true;
        }
        BSTNode<T> child = (deletingNode.LeftChild != null) ? deletingNode.LeftChild : deletingNode.RightChild;

        if (deletingNode.Parent == null) {
            Root = child;
        } else {
            if (deletingNode.Parent.LeftChild == deletingNode) {
                deletingNode.Parent.LeftChild = child;
            } else {
                deletingNode.Parent.RightChild = child;
            }
        }

        if (child != null) {
            child.Parent = deletingNode.Parent;
        }

        Count--;
        return true;
    }
    public boolean TreeEquals(BST<T> otherTree)
    {
        if (Root == null && otherTree.Root == null)
            return true;
        if (Root == null || otherTree.Root == null)
            return false;
        if (!(Root.NodeEquals(otherTree.Root)))
            return false;
        var leftSubtreeThis = new BST<>(Root.LeftChild);
        var rightSubtreeThis = new BST<>(Root.RightChild);
        var leftSubtreeOther = new BST<>(otherTree.Root.LeftChild);
        var rightSubtreeOther = new BST<>(otherTree.Root.RightChild);
        return leftSubtreeThis.TreeEquals(leftSubtreeOther) &&
               rightSubtreeThis.TreeEquals(rightSubtreeOther);
    }
    public ArrayList<LinkedList<BSTNode<T>>> WaysWithFixedLength(int length)
    {
        if (Root == null) {
            return new ArrayList<>();
        }
        if (Root.LeftChild == null && Root.RightChild == null && length != 1) {
            return new ArrayList<>();
        }
        var answer = new ArrayList<LinkedList<BSTNode<T>>>();
        if (length == 1) {
            var wayWithEnd = new LinkedList<BSTNode<T>>();
            wayWithEnd.add(Root);
            answer.add(wayWithEnd);
            return answer;
        }

        if (length - 1 >= 1)
        {
            var subtreeLeft = new BST<>(Root.LeftChild);
            var subtreeRight = new BST<>(Root.RightChild);
            answer.addAll(subtreeLeft.WaysWithFixedLength(length - 1));
            answer.addAll(subtreeRight.WaysWithFixedLength(length - 1));
        }
        answer.stream().forEach(list -> list.addFirst(Root));
        return answer;
    }
    public static ArrayList<LinkedList<BSTNode<Integer>>> MaxSumWay(BST<Integer> tree)
    {
        if (tree.Root == null) {
            return new ArrayList<>();
        }
        var answer = new ArrayList<LinkedList<BSTNode<Integer>>>();
        MaxSumWayHelp(answer, tree.Root);
        return answer;
    }
    public static int MaxSumWayHelp(ArrayList<LinkedList<BSTNode<Integer>>> answer, BSTNode<Integer> root)
    {
        if (root.LeftChild == null && root.RightChild == null) {
            var wayWithEnd = new LinkedList<BSTNode<Integer>>();
            wayWithEnd.add(root);
            answer.add(wayWithEnd);
            return root.NodeValue;
        }
        if (root.LeftChild != null && root.RightChild != null) {
            var answerLeft = new ArrayList<LinkedList<BSTNode<Integer>>>();
            var answerRight = new ArrayList<LinkedList<BSTNode<Integer>>>();
            int leftMax = MaxSumWayHelp(answerLeft, root.LeftChild);
            int rightMax = MaxSumWayHelp(answerRight, root.RightChild);
            if (leftMax >= rightMax)
                answer.addAll(answerLeft);
            if (leftMax <= rightMax)
                answer.addAll(answerRight);
            answer.stream().forEach(list -> list.addFirst(root));
            return Math.max(leftMax, rightMax) + root.NodeValue;
        }
        var answerChild = new ArrayList<LinkedList<BSTNode<Integer>>>();
        var rootChild = (root.LeftChild != null) ? root.LeftChild : root.RightChild;
        int childMax = MaxSumWayHelp(answerChild, rootChild);
        answer.addAll(answerChild);
        answer.stream().forEach(list -> list.addFirst(root));
        return childMax + root.NodeValue;
    }
    public boolean IsSymmetrical() {
        return false;
    }
    public ArrayList<BSTNode> WideAllNodes() {
        if (Root == null)
            return new ArrayList<>();

        var answer = new ArrayList<BSTNode>();
        Queue<BSTNode> notVisitedNodes = new LinkedList<>();
        notVisitedNodes.add(Root);

        while (!notVisitedNodes.isEmpty()) {
            var current = notVisitedNodes.poll();
            answer.add(current);

            if (current.LeftChild != null)
                notVisitedNodes.add(current.LeftChild);
            if (current.RightChild != null)
                notVisitedNodes.add(current.RightChild);
        }

        return answer;
    }

    public ArrayList<BSTNode> DeepAllNodes(int order) {
        if (Root == null)
            return new ArrayList<>();
        var answer = new ArrayList<BSTNode>();
        if (order == 0)
            answer.add(Root);
        if (Root.LeftChild != null)
            answer.addAll((new BST<>(Root.LeftChild)).DeepAllNodes(order));
        if (order == 1)
            answer.add(Root);
        if (Root.RightChild != null)
            answer.addAll((new BST<>(Root.RightChild)).DeepAllNodes(order));
        if (order == 2)
            answer.add(Root);
        return answer;
    }
    public int Count()
    {
        return Count; // количество узлов в дереве
    }

}