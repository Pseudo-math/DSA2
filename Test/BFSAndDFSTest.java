import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

public class BFSAndDFSTest {
    private BST<String> bst;

    @BeforeEach
    void setUp() {
        bst = new BST<>(new BSTNode<>(10, "Root", null));
        bst.AddKeyValue(5, "Left");
        bst.AddKeyValue(15, "Right");
        bst.AddKeyValue(3, "Left.Left");
        bst.AddKeyValue(7, "Left.Right");
        bst.AddKeyValue(13, "Right.Left");
        bst.AddKeyValue(18, "Right.Right");
    }

    @Test
    public void testBFSEmptyTree() {
        BST<String> tree = new BST<>(null);  // Пустое дерево
        var result = tree.WideAllNodes();
        assertTrue(result.isEmpty(), "Дерево пустое, результат должен быть пустым списком.");
    }
    @Test
    public void testBFSSingleNode() {
        BST<String> tree = new BST<>(null);  // Пустое дерево
        tree.AddKeyValue(10, "Root");  // Добавляем корень

        var result = tree.WideAllNodes();

        assertEquals(1, result.size(), "Дерево содержит один узел, результат должен содержать один элемент.");
        assertEquals("Root", result.get(0).NodeValue, "Значение единственного узла должно быть 'Root'.");
    }

    @Test
    public void testBFSMultipleNodes() {
        BST<String> tree = new BST<>(null);  // Пустое дерево
        tree.AddKeyValue(10, "Root");  // Добавляем корень
        tree.AddKeyValue(5, "Left");  // Добавляем левый узел
        tree.AddKeyValue(15, "Right");  // Добавляем правый узел

        var result = tree.WideAllNodes();

        assertEquals(3, result.size(), "Дерево содержит три узла, результат должен содержать три элемента.");

        assertEquals("Root", result.get(0).NodeValue, "Первый элемент должен быть корнем со значением 'Root'.");
        assertEquals("Left", result.get(1).NodeValue, "Второй элемент должен быть левым потомком со значением 'Left'.");
        assertEquals("Right", result.get(2).NodeValue, "Третий элемент должен быть правым потомком со значением 'Right'.");
    }


    @Test
    public void testBFSFullBinaryTree() {
        BST<String> tree = new BST<>(null);  // Пустое дерево
        tree.AddKeyValue(10, "Root");  // Добавляем корень
        tree.AddKeyValue(5, "Left");  // Добавляем левый узел
        tree.AddKeyValue(15, "Right");  // Добавляем правый узел
        tree.AddKeyValue(3, "LeftLeft");  // Добавляем левый потомок левого узла
        tree.AddKeyValue(7, "LeftRight");  // Добавляем правый потомок левого узла
        tree.AddKeyValue(13, "RightLeft");  // Добавляем левый потомок правого узла
        tree.AddKeyValue(17, "RightRight");  // Добавляем правый потомок правого узла

        var result = tree.WideAllNodes();

        assertEquals(7, result.size(), "Дерево содержит семь узлов.");

        assertEquals("Root", result.get(0).NodeValue, "Первый элемент должен быть корнем со значением 'Root'.");
        assertEquals("Left", result.get(1).NodeValue, "Второй элемент должен быть левым потомком со значением 'Left'.");
        assertEquals("Right", result.get(2).NodeValue, "Третий элемент должен быть правым потомком со значением 'Right'.");
        assertEquals("LeftLeft", result.get(3).NodeValue, "Четвертый элемент должен быть левым потомком левого узла со значением 'LeftLeft'.");
        assertEquals("LeftRight", result.get(4).NodeValue, "Пятый элемент должен быть правым потомком левого узла со значением 'LeftRight'.");
        assertEquals("RightLeft", result.get(5).NodeValue, "Шестой элемент должен быть левым потомком правого узла со значением 'RightLeft'.");
        assertEquals("RightRight", result.get(6).NodeValue, "Седьмой элемент должен быть правым потомком правого узла со значением 'RightRight'.");
    }

    @Test
    public void testDFSEmptyTree() {
        BST<String> tree = new BST<>(null);  // Пустое дерево
        var resultPreorder = tree.DeepAllNodes(2);
        var resultInorder = tree.DeepAllNodes(0);
        var resultPostorder = tree.DeepAllNodes(1);

        assertTrue(resultPreorder.isEmpty(), "Дерево пустое, результат preorder должен быть пустым.");
        assertTrue(resultInorder.isEmpty(), "Дерево пустое, результат inorder должен быть пустым.");
        assertTrue(resultPostorder.isEmpty(), "Дерево пустое, результат postorder должен быть пустым.");
    }

    @Test
    public void testDFSSingleNode() {
        var tree = new BST<String>(null);  // Пустое дерево
        tree.AddKeyValue(10, "Root");  // Добавляем корень

        var resultPreorder = tree.DeepAllNodes(2);
        var resultInorder = tree.DeepAllNodes(0);
        var resultPostorder = tree.DeepAllNodes(1);

        assertEquals(1, resultPreorder.size(), "Дерево содержит один узел, результат preorder должен содержать один элемент.");
        assertEquals("Root", resultPreorder.get(0).NodeValue, "Значение единственного узла должно быть 'Root'.");

        assertEquals(1, resultInorder.size(), "Дерево содержит один узел, результат inorder должен содержать один элемент.");
        assertEquals("Root", resultInorder.get(0).NodeValue, "Значение единственного узла должно быть 'Root'.");

        assertEquals(1, resultPostorder.size(), "Дерево содержит один узел, результат postorder должен содержать один элемент.");
        assertEquals("Root", resultPostorder.get(0).NodeValue, "Значение единственного узла должно быть 'Root'.");
    }

    @Test
    public void testDFSMultipleNodes() {
        var tree = new BST<String>(null);  // Пустое дерево
        tree.AddKeyValue(10, "Root");  // Добавляем корень
        tree.AddKeyValue(5, "Left");  // Добавляем левый узел
        tree.AddKeyValue(15, "Right");  // Добавляем правый узел

        var resultPreorder = tree.DeepAllNodes(2);
        var resultInorder = tree.DeepAllNodes(0);
        var resultPostorder = tree.DeepAllNodes(1);

        // Preorder: Корень, Левый, Правый
        assertEquals(3, resultPreorder.size(), "Дерево должно содержать 3 узла в preorder.");
        assertEquals("Root", resultPreorder.get(0).NodeValue, "Первый элемент в preorder должен быть 'Root'.");
        assertEquals("Left", resultPreorder.get(1).NodeValue, "Второй элемент в preorder должен быть 'Left'.");
        assertEquals("Right", resultPreorder.get(2).NodeValue, "Третий элемент в preorder должен быть 'Right'.");

        // Inorder: Левый, Корень, Правый
        assertEquals(3, resultInorder.size(), "Дерево должно содержать 3 узла в inorder.");
        assertEquals("Left", resultInorder.get(0).NodeValue, "Первый элемент в inorder должен быть 'Left'.");
        assertEquals("Root", resultInorder.get(1).NodeValue, "Второй элемент в inorder должен быть 'Root'.");
        assertEquals("Right", resultInorder.get(2).NodeValue, "Третий элемент в inorder должен быть 'Right'.");

        // Postorder: Левый, Правый, Корень
        assertEquals(3, resultPostorder.size(), "Дерево должно содержать 3 узла в postorder.");
        assertEquals("Left", resultPostorder.get(0).NodeValue, "Первый элемент в postorder должен быть 'Left'.");
        assertEquals("Right", resultPostorder.get(1).NodeValue, "Второй элемент в postorder должен быть 'Right'.");
        assertEquals("Root", resultPostorder.get(2).NodeValue, "Третий элемент в postorder должен быть 'Root'.");
    }

    @Test
    public void testDFSFullBinaryTree() {
        var tree = new BST<String>(null);  // Пустое дерево
        tree.AddKeyValue(10, "Root");  // Добавляем корень
        tree.AddKeyValue(5, "Left");  // Добавляем левый узел
        tree.AddKeyValue(15, "Right");  // Добавляем правый узел
        tree.AddKeyValue(3, "LeftLeft");  // Добавляем левый потомок левого узла
        tree.AddKeyValue(7, "LeftRight");  // Добавляем правый потомок левого узла
        tree.AddKeyValue(13, "RightLeft");  // Добавляем левый потомок правого узла
        tree.AddKeyValue(17, "RightRight");  // Добавляем правый потомок правого узла

        var resultPreorder = tree.DeepAllNodes(2);
        var resultInorder = tree.DeepAllNodes(0);
        var resultPostorder = tree.DeepAllNodes(1);

        // Preorder: Корень, Левый, Левый-Левый, Левый-Правый, Правый, Правый-Левый, Правый-Правый
        assertEquals(7, resultPreorder.size(), "Дерево должно содержать 7 узлов в preorder.");
        assertEquals("Root", resultPreorder.get(0).NodeValue, "Первый элемент в preorder должен быть 'Root'.");
        assertEquals("Left", resultPreorder.get(1).NodeValue, "Второй элемент в preorder должен быть 'Left'.");
        assertEquals("LeftLeft", resultPreorder.get(2).NodeValue, "Третий элемент в preorder должен быть 'LeftLeft'.");
        assertEquals("LeftRight", resultPreorder.get(3).NodeValue, "Четвертый элемент в preorder должен быть 'LeftRight'.");
        assertEquals("Right", resultPreorder.get(4).NodeValue, "Пятый элемент в preorder должен быть 'Right'.");
        assertEquals("RightLeft", resultPreorder.get(5).NodeValue, "Шестой элемент в preorder должен быть 'RightLeft'.");
        assertEquals("RightRight", resultPreorder.get(6).NodeValue, "Седьмой элемент в preorder должен быть 'RightRight'.");

        // Inorder: Левый-Левый, Левый, Левый-Правый, Корень, Правый-Левый, Правый, Правый-Правый
        assertEquals(7, resultInorder.size(), "Дерево должно содержать 7 узлов в inorder.");
        assertEquals("LeftLeft", resultInorder.get(0).NodeValue, "Первый элемент в inorder должен быть 'LeftLeft'.");
        assertEquals("Left", resultInorder.get(1).NodeValue, "Второй элемент в inorder должен быть 'Left'.");
        assertEquals("LeftRight", resultInorder.get(2).NodeValue, "Третий элемент в inorder должен быть 'LeftRight'.");
        assertEquals("Root", resultInorder.get(3).NodeValue, "Четвертый элемент в inorder должен быть 'Root'.");
        assertEquals("RightLeft", resultInorder.get(4).NodeValue, "Пятый элемент в inorder должен быть 'RightLeft'.");
        assertEquals("Right", resultInorder.get(5).NodeValue, "Шестой элемент в inorder должен быть 'Right'.");
        assertEquals("RightRight", resultInorder.get(6).NodeValue, "Седьмой элемент в inorder должен быть 'RightRight'.");

        // Postorder: Левый-Левый, Левый-Правый, Левый, Правый-Левый, Правый-Правый, Правый, Корень
        assertEquals(7, resultPostorder.size(), "Дерево должно содержать 7 узлов в postorder.");
        assertEquals("LeftLeft", resultPostorder.get(0).NodeValue, "Первый элемент в postorder должен быть 'LeftLeft'.");
        assertEquals("LeftRight", resultPostorder.get(1).NodeValue, "Второй элемент в postorder должен быть 'LeftRight'.");
        assertEquals("Left", resultPostorder.get(2).NodeValue, "Третий элемент в postorder должен быть 'Left'.");
        assertEquals("RightLeft", resultPostorder.get(3).NodeValue, "Четвертый элемент в postorder должен быть 'RightLeft'.");
        assertEquals("RightRight", resultPostorder.get(4).NodeValue, "Пятый элемент в postorder должен быть 'RightRight'.");
        assertEquals("Right", resultPostorder.get(5).NodeValue, "Шестой элемент в postorder должен быть 'Right'.");
        assertEquals("Root", resultPostorder.get(6).NodeValue, "Седьмой элемент в postorder должен быть 'Root'.");
    }

    @Test
    void testInvertTreeEmptyTree() {
        BST<String> emptyTree = new BST<>(null);
        emptyTree.InvertTree();
        assertNull(emptyTree.Root);
    }

    @Test
    void testInvertTreeSingleElement() {
        BST<String> singleNodeTree = new BST<>(new BSTNode<>(1, "Single", null));
        singleNodeTree.InvertTree();
        assertEquals(1, singleNodeTree.Root.NodeKey);
        assertNull(singleNodeTree.Root.LeftChild);
        assertNull(singleNodeTree.Root.RightChild);
    }

    @Test
    void testInvertTree() {
        bst.InvertTree();

        assertEquals(15, bst.Root.LeftChild.NodeKey);
        assertEquals(5, bst.Root.RightChild.NodeKey);
        assertEquals(18, bst.Root.LeftChild.LeftChild.NodeKey);
        assertEquals(13, bst.Root.LeftChild.RightChild.NodeKey);
        assertEquals(7, bst.Root.RightChild.LeftChild.NodeKey);
        assertEquals(3, bst.Root.RightChild.RightChild.NodeKey);
    }

    @Test
    void testInvertTreeUnbalancedTree() {
        BST<String> unbalancedTree = new BST<>(new BSTNode<>(10, "Root", null));
        unbalancedTree.AddKeyValue(5, "Left");
        unbalancedTree.AddKeyValue(1, "Left.Left");
        unbalancedTree.InvertTree();
        assertEquals(5, unbalancedTree.Root.RightChild.NodeKey);
        assertEquals(1, unbalancedTree.Root.RightChild.RightChild.NodeKey);
    }

    @Test
    void testLevelOfMaxSumEmptyTree() {
        BST<String> emptyTree = new BST<>(null);
        assertEquals(0, emptyTree.LevelOfMaxSum());
    }

    @Test
    void testLevelOfMaxSumSingleElement() {
        BST<String> singleNodeTree = new BST<>(new BSTNode<>(42, "Single", null));
        assertEquals(1, singleNodeTree.LevelOfMaxSum());
    }

    @Test
    void testLevelOfMaxSum() {
        assertEquals(3, bst.LevelOfMaxSum());
    }
    @Test
    void testLevelOfMaxSumUnbalancedTree() {
        BST<String> unbalancedTree = new BST<>(new BSTNode<>(10, "Root", null));
        unbalancedTree.AddKeyValue(5, "Left");
        unbalancedTree.AddKeyValue(1, "Left.Left");
        assertEquals(1, unbalancedTree.LevelOfMaxSum());
    }

    @Test
    void testLevelOfMaxSumLargeTree() {
        BST<String> largeTree = new BST<>(new BSTNode<>(50, "Root", null));
        for (int i = 1; i <= 10; i++) {
            largeTree.AddKeyValue(50 - i, "Left" + i);
            largeTree.AddKeyValue(50 + i, "Right" + i);
        }
        assertEquals(2, largeTree.LevelOfMaxSum());
    }

    @Test
    void testRecoveredTree() {
        // Исходные данные
        ArrayList<Integer> preorder = new ArrayList<>(List.of(1, 2, 4, 5, 3, 6, 7));
        ArrayList<Integer> inorder = new ArrayList<>(List.of(4, 2, 5, 1, 6, 3, 7));

        // Восстанавливаем дерево
        BST<Integer> tree = new BST<>(null);
        tree = tree.RecoveredTree(preorder, inorder);

        // Проверка структуры дерева
        assertNotNull(tree.Root, "Корень дерева не должен быть null");
        assertEquals(Integer.valueOf(1), tree.Root.NodeValue, "Корень должен быть с значением 1");

        // Проверка левого поддерева
        assertNotNull(tree.Root.LeftChild, "Левый потомок корня не должен быть null");
        assertEquals(Integer.valueOf(2), tree.Root.LeftChild.NodeValue, "Левый потомок корня должен быть с значением 2");

        // Проверка правого поддерева
        assertNotNull(tree.Root.RightChild, "Правый потомок корня не должен быть null");
        assertEquals(Integer.valueOf(3), tree.Root.RightChild.NodeValue, "Правый потомок корня должен быть с значением 3");

        // Проверка потомков левого узла (2)
        assertNotNull(tree.Root.LeftChild.LeftChild, "Левый потомок узла 2 не должен быть null");
        assertEquals(Integer.valueOf(4), tree.Root.LeftChild.LeftChild.NodeValue, "Левый потомок узла 2 должен быть с значением 4");

        assertNotNull(tree.Root.LeftChild.RightChild, "Правый потомок узла 2 не должен быть null");
        assertEquals(Integer.valueOf(5), tree.Root.LeftChild.RightChild.NodeValue, "Правый потомок узла 2 должен быть с значением 5");

        // Проверка потомков правого узла (3)
        assertNotNull(tree.Root.RightChild.LeftChild, "Левый потомок узла 3 не должен быть null");
        assertEquals(Integer.valueOf(6), tree.Root.RightChild.LeftChild.NodeValue, "Левый потомок узла 3 должен быть с значением 6");

        assertNotNull(tree.Root.RightChild.RightChild, "Правый потомок узла 3 не должен быть null");
        assertEquals(Integer.valueOf(7), tree.Root.RightChild.RightChild.NodeValue, "Правый потомок узла 3 должен быть с значением 7");
    }

    @Test
    void testRecoveredTreeEmpty() {
        // Пустые входные данные
        ArrayList<Integer> preorder = new ArrayList<>();
        ArrayList<Integer> inorder = new ArrayList<>();

        // Восстанавливаем дерево
        BST<Integer> tree = new BST<>(null);
        tree = tree.RecoveredTree(preorder, inorder);

        // Проверяем, что дерево пустое
        assertNull(tree.Root, "Корень дерева должен быть null для пустого ввода");
    }

    @Test
    void testRecoveredTreeSingleNode() {
        // Ввод с одним узлом
        ArrayList<Integer> preorder = new ArrayList<>(List.of(1));
        ArrayList<Integer> inorder = new ArrayList<>(List.of(1));

        // Восстанавливаем дерево
        BST<Integer> tree = new BST<>(null);
        tree = tree.RecoveredTree(preorder, inorder);

        // Проверяем, что дерево содержит только один узел
        assertNotNull(tree.Root, "Корень дерева не должен быть null");
        assertEquals(Integer.valueOf(1), tree.Root.NodeValue, "Корень должен быть с значением 1");
        assertNull(tree.Root.LeftChild, "Левый потомок корня должен быть null");
        assertNull(tree.Root.RightChild, "Правый потомок корня должен быть null");
    }
}
