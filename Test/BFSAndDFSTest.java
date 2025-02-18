import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;

public class BFSAndDFSTest {

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
        var resultPreorder = tree.DeepAllNodes(0);
        var resultInorder = tree.DeepAllNodes(1);
        var resultPostorder = tree.DeepAllNodes(2);

        assertTrue(resultPreorder.isEmpty(), "Дерево пустое, результат preorder должен быть пустым.");
        assertTrue(resultInorder.isEmpty(), "Дерево пустое, результат inorder должен быть пустым.");
        assertTrue(resultPostorder.isEmpty(), "Дерево пустое, результат postorder должен быть пустым.");
    }

    @Test
    public void testDFSSingleNode() {
        var tree = new BST<String>(null);  // Пустое дерево
        tree.AddKeyValue(10, "Root");  // Добавляем корень

        var resultPreorder = tree.DeepAllNodes(0);
        var resultInorder = tree.DeepAllNodes(1);
        var resultPostorder = tree.DeepAllNodes(2);

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

        var resultPreorder = tree.DeepAllNodes(0);
        var resultInorder = tree.DeepAllNodes(1);
        var resultPostorder = tree.DeepAllNodes(2);

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

        var resultPreorder = tree.DeepAllNodes(0);
        var resultInorder = tree.DeepAllNodes(1);
        var resultPostorder = tree.DeepAllNodes(2);

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
}
