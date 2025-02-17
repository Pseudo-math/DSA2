
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.*;

public class BSTAdditionalTasksTest {

    private static final int RANDOM_TEST_COUNT = 10;

    @Test
    public void testTreeEquals_EmptyTrees() {
        BST<Integer> tree1 = new BST<>(null);
        BST<Integer> tree2 = new BST<>(null);
        assertTrue(tree1.TreeEquals(tree2), "Пустые деревья должны быть равны");
        assertTrue(tree1.TreeEquals(tree1), "Пустое дерево должно быть равно само себе");
    }

    @Test
    public void testTreeEquals_Singletons() {
        BST<Integer> tree1 = new BST<>(new BSTNode<>(10, 10, null));
        BST<Integer> tree2 = new BST<>(new BSTNode<>(10, 10, null));
        assertTrue(tree1.TreeEquals(tree2), "Синглетоны с одинаковыми ключами должны быть равны");
        assertTrue(tree1.TreeEquals(tree1), "Дерево синглетон должно быть равно само себе");

        tree2 = new BST<>(new BSTNode<>(20, 20, null));
        assertFalse(tree1.TreeEquals(tree2), "Синглетоны с разными ключами должны быть не равны");
    }


    @Test
    public void testTreeEquals_AllCombinations_5Nodes() {
        BST<Integer> tree1 = new BST<>(new BSTNode<>(10, 10, null));
        tree1.AddKeyValue(5, 5);
        tree1.AddKeyValue(15, 15);
        tree1.AddKeyValue(3, 3);
        tree1.AddKeyValue(7, 7);

        BST<Integer> tree2 = new BST<>(new BSTNode<>(10, 10, null));
        tree2.AddKeyValue(5, 5);
        tree2.AddKeyValue(15, 15);
        tree2.AddKeyValue(3, 3);
        tree2.AddKeyValue(7, 7);

        assertTrue(tree1.TreeEquals(tree2), "Деревья из 5 узлов должны быть равны");

        tree2.DeleteNodeByKey(7);
        assertFalse(tree1.TreeEquals(tree2), "Деревья должны быть не равны после удаления узла");
    }

    @Test
    public void testTreeEquals_Randomized() {
        Random random = new Random();
        for (int i = 0; i < RANDOM_TEST_COUNT; i++) {
            BST<Integer> tree1 = new BST<>(null);
            BST<Integer> tree2 = new BST<>(null);
            int nodes = 10;
            for (int j = 0; j < nodes; j++) {
                int key = random.nextInt(100);
                tree1.AddKeyValue(key, key);
                tree2.AddKeyValue(key, key);
            }
            assertTrue(tree1.TreeEquals(tree2), "Рандомизированные деревья должны быть равны, если их узлы совпадают");
        }
    }
    @Test
    public void testTreeEquals_Combinations_3Nodes() {
        BST<Integer> tree1 = buildTree(2, 1, 3);
        BST<Integer> tree2 = buildTree(2, 3, 1);
        BST<Integer> tree3 = buildTree(1, 2, 3);
        BST<Integer> tree4 = buildTree(3, 2, 1);
        BST<Integer> tree5 = buildTree(1, 3, 2);

        assertTrue(tree1.TreeEquals(buildTree(2, 1, 3)), "tree1 должно совпадать с аналогичным");
        assertTrue(tree1.TreeEquals(tree1), "tree1 должно совпадать с самим собой");
        assertTrue(tree1.TreeEquals(tree2), "tree1 совпадает с tree2");
        assertTrue(tree2.TreeEquals(tree1), "tree2 совпадает с tree1");
        assertFalse(tree1.TreeEquals(tree3), "tree1 не совпадает с tree3");
        assertFalse(tree3.TreeEquals(tree1), "tree3 не совпадает с tree1");
        assertFalse(tree1.TreeEquals(tree4), "tree1 не совпадает с tree4");
        assertFalse(tree4.TreeEquals(tree1), "tree4 не совпадает с tree1");
        assertFalse(tree1.TreeEquals(tree5), "tree1 не совпадает с tree5");
        assertFalse(tree5.TreeEquals(tree1), "tree5 не совпадает с tree1");

        assertTrue(tree3.TreeEquals(buildTree(1, 2, 3)), "tree3 должно совпадать с самим собой");
        assertFalse(tree3.TreeEquals(tree4), "tree3 не совпадает с tree4");
    }

    private BST<Integer> buildTree(int... values) {
        BST<Integer> tree = new BST<>(new BSTNode<>(values[0], values[0], null));
        for (int i = 1; i < values.length; i++) {
            tree.AddKeyValue(values[i], values[i]);
        }
        return tree;
    }

    @Test
    void testWaysWithFixedLength_EmptyTree() {
        BST<Integer> tree = new BST<>(null);
        ArrayList<LinkedList<BSTNode<Integer>>> result = tree.WaysWithFixedLength(3);
        assertTrue(result.isEmpty(), "Для пустого дерева список путей должен быть пустым");
    }

    @Test
    void testWaysWithFixedLength_NoMatchingPaths() {
        BST<Integer> tree = new BST<>(new BSTNode<>(10, 10, null));
        tree.AddKeyValue(5, 5);
        tree.AddKeyValue(15, 15);

        ArrayList<LinkedList<BSTNode<Integer>>> result = tree.WaysWithFixedLength(5);
        assertTrue(result.isEmpty(), "Не должно быть путей длины 5, если таковых нет");
    }

    @Test
    void testWaysWithFixedLength_SinglePath() {
        BST<Integer> tree = new BST<>(new BSTNode<>(10, 10, null));
        tree.AddKeyValue(5, 5);
        tree.AddKeyValue(2, 2);
        tree.AddKeyValue(1, 1);

        ArrayList<LinkedList<BSTNode<Integer>>> result = tree.WaysWithFixedLength(4);

        assertEquals(1, result.size(), "Должен быть один путь длины 4");

        LinkedList<BSTNode<Integer>> path = result.getFirst();
        assertEquals(List.of(10, 5, 2, 1), path.stream().map(n -> n.NodeKey).toList(), "Путь должен соответствовать ожидаемому");
    }

    @Test
    void testWaysWithFixedLength_MultiplePaths() {
        BST<Integer> tree = new BST<>(new BSTNode<>(10, 10, null));
        tree.AddKeyValue(5, 5);
        tree.AddKeyValue(15, 15);
        tree.AddKeyValue(2, 2);
        tree.AddKeyValue(20, 20);

        ArrayList<LinkedList<BSTNode<Integer>>> result = tree.WaysWithFixedLength(3);
        assertEquals(2, result.size(), "Должно быть два пути длины 3");

        List<List<Integer>> expectedPaths = List.of(
                List.of(10, 5, 2),
                List.of(10, 15, 20)
        );

        List<List<Integer>> actualPaths = result.stream()
                .map(path -> path.stream().map(n -> n.NodeKey).toList())
                .toList();

        assertTrue(actualPaths.containsAll(expectedPaths), "Ожидаемые пути должны присутствовать в результате");
    }

    @Test
    void testEmptyTree() {
        BST<Integer> tree = new BST<>(null);
        ArrayList<LinkedList<BSTNode<Integer>>> result = BST.MaxSumWay(tree);
        assertTrue(result.isEmpty(), "Пустое дерево должно возвращать пустой список");
    }

    @Test
    void testSingleNodeTree() {
        BST<Integer> tree = new BST<>(new BSTNode<>(10, 10, null));
        ArrayList<LinkedList<BSTNode<Integer>>> result = BST.MaxSumWay(tree);

        assertEquals(1, result.size(), "Должен быть один путь");
        assertEquals(1, result.getFirst().size(), "Путь должен содержать один узел");
        assertEquals(10, result.getFirst().getFirst().NodeKey, "Единственный узел должен быть корнем");
    }

    @Test
    void testBalancedTree() {
        BST<Integer> tree = new BST<>(new BSTNode<>(10, 10, null));
        tree.AddKeyValue(5, 5);
        tree.AddKeyValue(20, 20);
        tree.AddKeyValue(3, 3);
        tree.AddKeyValue(25, 25);

        ArrayList<LinkedList<BSTNode<Integer>>> result = BST.MaxSumWay(tree);

        assertEquals(1, result.size(), "Должен быть один путь с максимальной суммой");
        assertArrayEquals(new int[]{10, 20, 25}, result.getFirst().stream().mapToInt(n -> n.NodeKey).toArray(), "Путь с максимальной суммой должен быть 10 -> 20 -> 25");
    }

    @Test
    void testMultipleMaxSumPaths() {
        BST<Integer> tree = new BST<>(new BSTNode<>(11, 11, null));
        tree.AddKeyValue(1, 1);
        tree.AddKeyValue(20, 20);
        tree.AddKeyValue(7, 7);
        tree.AddKeyValue(15, 15);
        tree.AddKeyValue(10, 10);
        tree.AddKeyValue(9, 9);
        tree.AddKeyValue(8, 8);

        ArrayList<LinkedList<BSTNode<Integer>>> result = BST.MaxSumWay(tree);

        assertEquals(2, result.size(), "Должно быть два пути с максимальной суммой");

        Set<List<Integer>> expectedPaths = new HashSet<>();
        expectedPaths.add(Arrays.asList(11, 1, 7, 10, 9, 8));
        expectedPaths.add(Arrays.asList(11, 20, 15));

        Set<List<Integer>> actualPaths = new HashSet<>();
        for (LinkedList<BSTNode<Integer>> path : result) {
            actualPaths.add(path.stream().map(n -> n.NodeKey).toList());
        }

        assertEquals(expectedPaths, actualPaths, "Ожидаемые пути с максимальной суммой должны совпадать");
    }

    @Test
    void testDeleteNode() {
        BST<Integer> tree = new BST<>(new BSTNode<>(10, 10, null));
        tree.AddKeyValue(5, 5);
        tree.AddKeyValue(20, 20);
        tree.DeleteNodeByKey(5);

        ArrayList<LinkedList<BSTNode<Integer>>> result = BST.MaxSumWay(tree);

        assertEquals(1, result.size(), "Должен быть один путь после удаления");
        assertArrayEquals(new int[]{10, 20}, result.getFirst().stream().mapToInt(n -> n.NodeKey).toArray(), "Путь должен быть 10 -> 20");
    }
}



