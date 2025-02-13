import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;

class BSTTest {
    private BST<Integer> tree;
    private BST<Integer> emptyTree;
    private BST<Integer> singleNodeTree;
    private static final int N = 10;

    @BeforeEach
    void setUp() {
        tree = new BST<>(new BSTNode<>(10, 10, null));
        emptyTree = new BST<>(null);
        singleNodeTree = new BST<>(new BSTNode<>(20, 20, null));
    }

    @Test
    void testFindNodeByKeyAbsentLeft() {
        BSTFind<Integer> result = tree.FindNodeByKey(5);
        assertNotNull(result);
        assertFalse(result.NodeHasKey);
        assertTrue(result.ToLeft);
    }

    @Test
    void testFindNodeByKeyAbsentRight() {
        BSTFind<Integer> result = tree.FindNodeByKey(15);
        assertNotNull(result);
        assertFalse(result.NodeHasKey);
        assertFalse(result.ToLeft);
    }

    @Test
    void testFindNodeByKeyPresent() {
        tree.AddKeyValue(5, 5);
        BSTFind<Integer> result = tree.FindNodeByKey(5);
        assertNotNull(result);
        assertTrue(result.NodeHasKey);
    }

    @Test
    void testFindNodeByKeyInEmptyTree() {
        BSTFind<Integer> result = emptyTree.FindNodeByKey(5);
        assertNotNull(result);
        assertNull(result.Node);
        assertFalse(result.NodeHasKey);
    }

    @Test
    void testAddKeyValueLeft() {
        assertFalse(tree.FindNodeByKey(7).NodeHasKey);
        assertTrue(tree.AddKeyValue(7, 7));
        assertTrue(tree.FindNodeByKey(7).NodeHasKey);
    }

    @Test
    void testAddKeyValueRight() {
        assertFalse(tree.FindNodeByKey(15).NodeHasKey);
        assertTrue(tree.AddKeyValue(15, 15));
        assertTrue(tree.FindNodeByKey(15).NodeHasKey);
    }

    @Test
    void testAddKeyValueExisting() {
        assertFalse(tree.AddKeyValue(10, 10)); // Ключ уже есть
    }

    @Test
    void testFindMinFromRoot() {
        tree.AddKeyValue(5, 5);
        tree.AddKeyValue(15, 15);
        assertEquals(5, tree.FinMinMax(tree.Root, false).NodeKey);
    }

    @Test
    void testFindMaxFromRoot() {
        tree.AddKeyValue(5, 5);
        tree.AddKeyValue(15, 15);
        assertEquals(15, tree.FinMinMax(tree.Root, true).NodeKey);
    }

    @Test
    void testFindMinFromSubtree() {
        tree.AddKeyValue(5, 5);
        tree.AddKeyValue(3, 3);
        assertEquals(3, tree.FinMinMax(tree.FindNodeByKey(5).Node, false).NodeKey);
    }

    @Test
    void testFindMaxFromSubtree() {
        tree.AddKeyValue(5, 5);
        tree.AddKeyValue(7, 7);
        assertEquals(7, tree.FinMinMax(tree.FindNodeByKey(5).Node, true).NodeKey);
    }

    @Test
    void testDeleteNodeByKey() {
        tree.AddKeyValue(5, 5);
        tree.AddKeyValue(15, 15);

        assertTrue(tree.FindNodeByKey(5).NodeHasKey);
        assertTrue(tree.DeleteNodeByKey(5));
        assertFalse(tree.FindNodeByKey(5).NodeHasKey);

        assertFalse(tree.DeleteNodeByKey(99)); // Нет такого узла
    }

    @Test
    void testDeleteNodeByKeyInEmptyTree() {
        assertFalse(emptyTree.DeleteNodeByKey(5));
    }

    @Test
    void testCount() {
        assertEquals(1, tree.Count());
        tree.AddKeyValue(5, 5);
        tree.AddKeyValue(15, 15);
        assertEquals(3, tree.Count());
    }

    @Test
    void testCountInEmptyTree() {
        assertEquals(0, emptyTree.Count());
    }

    // Тесты для дерева с одним узлом
    @Test
    void testFindNodeInSingleNodeTree() {
        assertTrue(singleNodeTree.FindNodeByKey(20).NodeHasKey);
        assertFalse(singleNodeTree.FindNodeByKey(30).NodeHasKey);
    }

    @Test
    void testAddNodeInSingleNodeTree() {
        assertTrue(singleNodeTree.AddKeyValue(25, 25));
        assertFalse(singleNodeTree.AddKeyValue(20, 20)); // Дублирующий ключ
    }

    @Test
    void testFindMinMaxInSingleNodeTree() {
        assertEquals(20, singleNodeTree.FinMinMax(singleNodeTree.Root, true).NodeKey);
        assertEquals(20, singleNodeTree.FinMinMax(singleNodeTree.Root, false).NodeKey);
    }

    @Test
    void testDeleteNodeInSingleNodeTree() {
        assertTrue(singleNodeTree.DeleteNodeByKey(20));
        assertEquals(0, singleNodeTree.Count());
    }

    @Test
    void testCountInSingleNodeTree() {
        assertEquals(1, singleNodeTree.Count());
    }

    // Рандомизированные тесты для каждого метода
    @Test
    void testRandomizedFind() {
        Random random = new Random();
        for (int i = 0; i < N; i++) {
            int key = random.nextInt(100);
            singleNodeTree.AddKeyValue(key, key);
            assertNotNull(singleNodeTree.FindNodeByKey(key));
        }
    }

    @Test
    void testRandomizedAdd() {
        Random random = new Random();
        Set<Integer> keys = new HashSet<>();

        // Генерируем N уникальных случайных ключей
        while (keys.size() < N) {
            keys.add(random.nextInt(100));
        }

        // Добавляем ключи в дерево
        for (int key : keys) {
            assertTrue(singleNodeTree.AddKeyValue(key, key), "Key " + key + " should be added");
        }

        // Проверяем, что все добавленные ключи присутствуют в дереве
        for (int key : keys) {
            assertTrue(singleNodeTree.FindNodeByKey(key).NodeHasKey, "Key " + key + " should be in the tree");
        }
    }


    @Test
    void testRandomizedMinMax() {
        Random random = new Random();
        for (int i = 0; i < N; i++) {
            singleNodeTree.AddKeyValue(random.nextInt(100), 0);
        }
        assertNotNull(singleNodeTree.FinMinMax(singleNodeTree.Root, true));
        assertNotNull(singleNodeTree.FinMinMax(singleNodeTree.Root, false));
    }


    @Test
    void testRandomizedCount() {
        Random random = new Random();
        int expectedCount = 1;
        for (int i = 0; i < N; i++) {
            if (singleNodeTree.AddKeyValue(random.nextInt(100), 0)) {
                expectedCount++;
            }
        }
        assertEquals(expectedCount, singleNodeTree.Count());
    }

    @Test
    public void testDeleteAllNodes() {
        // Создаем дерево из 6 вершин вручную
        BST<Integer> tree = new BST<>(new BSTNode<>(10, 10, null));
        tree.AddKeyValue(5, 5);
        tree.AddKeyValue(15, 15);
        tree.AddKeyValue(3, 3);
        tree.AddKeyValue(7, 7);
        tree.AddKeyValue(20, 20);

        assertEquals(6, tree.Count(), "Изначально должно быть 6 вершин");

        // Удаляем все узлы
        assertTrue(tree.DeleteNodeByKey(3));
        assertTrue(tree.DeleteNodeByKey(5));
        assertTrue(tree.DeleteNodeByKey(7));
        assertTrue(tree.DeleteNodeByKey(10));
        assertTrue(tree.DeleteNodeByKey(15));
        assertTrue(tree.DeleteNodeByKey(20));

        // Проверяем, что дерево пусто
        assertEquals(0, tree.Count(), "После удаления всех вершин дерево должно быть пустым");
        assertNull(tree.Root, "Корень должен быть null после удаления всех вершин");
    }


    @Test
    void testDeleteNodeWithSuccessorHavingRightChild() {
        BST<Integer> tree = new BST<>(new BSTNode<>(40, 40, null));

        // Добавляем узлы
        int[] nodes = {20, 60, 10, 30, 50, 70, 5, 15, 25, 35, 45, 55, 65, 75, 57};
        for (int key : nodes) {
            tree.AddKeyValue(key, key);
        }

        // Удаляем узел 50 (у него есть successor 55, у которого есть правый потомок 57)
        boolean deleted = tree.DeleteNodeByKey(50);
        assertTrue(deleted, "Node 50 should be deleted");

        // Проверяем, что successor (55) занял место 50
        BSTFind<Integer> successorNode = tree.FindNodeByKey(55);
        assertNotNull(successorNode.Node, "Successor 55 should exist in tree");
        assertTrue(successorNode.NodeHasKey, "Successor 55 should be in the tree");

        // Проверяем, что у нового узла 55 родителем теперь является родитель 50
        BSTFind<Integer> parentOf55 = tree.FindNodeByKey(40);
        assertNotNull(parentOf55.Node, "Parent of 55 should exist");
        assertEquals(60, successorNode.Node.Parent.NodeKey, "New parent of 55 should be 40");

        // Проверяем, что у 55 правый ребёнок — 57
        assertNotNull(successorNode.Node.RightChild, "Successor 55 should have a right child");
        assertEquals(57, successorNode.Node.RightChild.NodeKey, "Successor's right child should be 57");

        // Проверяем, что 57 всё ещё в дереве
        BSTFind<Integer> check57 = tree.FindNodeByKey(57);
        assertTrue(check57.NodeHasKey, "Node 57 should still be in the tree");

        // Проверяем, что узла 50 больше нет
        BSTFind<Integer> deletedNode = tree.FindNodeByKey(50);
        assertFalse(deletedNode.NodeHasKey, "Node 50 should not be in the tree anymore");
    }

    @Test
    void testDeleteNodeWhenSuccessorIsDirectRightChildWithRightChild() {
        BST<Integer> tree = new BST<>(new BSTNode<>(50, 50, null));

        // Создаем дерево:
        //        50
        //       /  \
        //     30    60
        //         /    \
        //       55      70
        //         \
        //          57

        tree.AddKeyValue(30, 30);
        tree.AddKeyValue(60, 60);
        tree.AddKeyValue(55, 55);
        tree.AddKeyValue(70, 70);
        tree.AddKeyValue(57, 57);

        // Удаляем 50, successor — это 55, у которого есть правый ребенок (57)
        boolean deleted = tree.DeleteNodeByKey(50);
        assertTrue(deleted, "Node 50 should be deleted");

        // Проверяем, что successor (55) занял место 50
        BSTFind<Integer> successorNode = tree.FindNodeByKey(55);
        assertNotNull(successorNode.Node, "Successor 55 should exist in tree");
        assertTrue(successorNode.NodeHasKey, "Successor 55 should be in the tree");

        // Проверяем, что successor (55) теперь корень
        assertEquals(55, tree.Root.NodeKey, "Successor 55 should be the new root");

        // Проверяем, что у 55 всё еще есть правый потомок 60
        assertNotNull(successorNode.Node.RightChild, "Successor 55 should have a right child");
        assertEquals(60, successorNode.Node.RightChild.NodeKey, "Successor's right child should be 60");

        // Проверяем, что у 60 всё еще есть левый потомок 57
        assertNotNull(successorNode.Node.RightChild.LeftChild, "Node 60 should have a left child");
        assertEquals(57, successorNode.Node.RightChild.LeftChild.NodeKey, "Node 60's left child should be 57");
    }

    @Test
    void testDeleteNodeWhenSuccessorHasRightChildAndIsNotDirectRightChild() {
        BST<Integer> tree = new BST<>(new BSTNode<>(50, 50, null));

        // Дерево перед удалением:
        //         50
        //       /    \
        //     30      80
        //           /
        //         60
        //        /  \
        //      55    70
        //        \
        //         57

        tree.AddKeyValue(30, 30);
        tree.AddKeyValue(80, 80);
        tree.AddKeyValue(60, 60);
        tree.AddKeyValue(55, 55);
        tree.AddKeyValue(70, 70);
        tree.AddKeyValue(57, 57);

        // Удаляем 50, его successor — это 55, у которого есть правый потомок 57
        boolean deleted = tree.DeleteNodeByKey(50);
        assertTrue(deleted, "Node 50 should be deleted");

        // Проверяем, что successor (55) занял место 50
        BSTFind<Integer> successorNode = tree.FindNodeByKey(55);
        assertNotNull(successorNode.Node, "Successor 55 should exist in tree");
        assertTrue(successorNode.NodeHasKey, "Successor 55 should be in the tree");

        // Проверяем, что successor (55) теперь корень
        assertEquals(55, tree.Root.NodeKey, "Successor 55 should be the new root");

        // Проверяем, что у 55 есть правильные потомки
        assertEquals(30, tree.Root.LeftChild.NodeKey, "Left child of new root should be 30");
        assertEquals(80, tree.Root.RightChild.NodeKey, "Right child of new root should be 80");

        // Проверяем, что у 55 остался правый ребенок 60
        assertNotNull(tree.Root.RightChild.LeftChild, "55's right child should be 60");
        assertEquals(60, tree.Root.RightChild.LeftChild.NodeKey, "Successor's right child should be 60");

        // Проверяем, что 60 всё еще имеет левый потомок 57
        assertNotNull(tree.Root.RightChild.LeftChild.LeftChild, "60 should have a left child");
        assertEquals(57, tree.Root.RightChild.LeftChild.LeftChild.NodeKey, "60's left child should be 57");
    }

    private BST<Integer> createBalancedTree() {
        BST<Integer> tree = new BST<>(new BSTNode<>(40, 40, null));
        int[] nodes = {20, 60, 10, 30, 50, 70, 5, 15, 25, 35, 45, 55, 65, 75};
        for (int key : nodes) {
            tree.AddKeyValue(key, key);
        }
        return tree;
    }


    @Test
    void testDeleteNodesInIdenticalTrees() {
        List<BST<Integer>> trees = new ArrayList<>();
        for (int i = 0; i < 15; i++) {
            trees.add(createBalancedTree());
        }

        int[] deleteKeys = {5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55, 60, 65, 70, 75};

        for (int i = 0; i < 15; i++) {
            BST<Integer> tree = trees.get(i);
            int keyToDelete = deleteKeys[i];

            assertTrue(tree.DeleteNodeByKey(keyToDelete), "Node " + keyToDelete + " should be deleted");

            // Проверяем, что удаленный узел больше не найден
            BSTFind<Integer> findDeleted = tree.FindNodeByKey(keyToDelete);
            assertFalse(findDeleted.NodeHasKey, "Deleted node " + keyToDelete + " should not be in the tree");

            // Проверяем, что структура дерева не сломалась
            checkTreeStructure(tree.Root, null);
        }
    }

    private void checkTreeStructure(BSTNode<Integer> node, BSTNode<Integer> expectedParent) {
        if (node == null) return;

        // Проверяем родителя
        assertEquals(expectedParent, node.Parent, "Node " + node.NodeKey + " should have correct parent");

        // Проверяем левый и правый потомок (если есть)
        if (node.LeftChild != null) {
            assertTrue(node.LeftChild.NodeKey < node.NodeKey, "Left child should be smaller than parent");
            checkTreeStructure(node.LeftChild, node);
        }

        if (node.RightChild != null) {
            assertTrue(node.RightChild.NodeKey > node.NodeKey, "Right child should be greater than parent");
            checkTreeStructure(node.RightChild, node);
        }
    }
}

