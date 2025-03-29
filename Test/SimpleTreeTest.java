import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class SimpleTreeTest {

    @Test
    void testAddChild() {
        SimpleTreeNode<Integer> root = new SimpleTreeNode<>(1, null);
        SimpleTree<Integer> tree = new SimpleTree<>(root);

        SimpleTreeNode<Integer> child = new SimpleTreeNode<>(2, root);
        tree.AddChild(root, child);

        assertEquals(1, root.Children.size());
        assertEquals(child, root.Children.get(0));
        assertEquals(root, child.Parent);
    }

    @Test
    void testDeleteNode() {
        SimpleTreeNode<Integer> root = new SimpleTreeNode<>(1, null);
        SimpleTree<Integer> tree = new SimpleTree<>(root);

        SimpleTreeNode<Integer> child = new SimpleTreeNode<>(2, root);
        root.Children = new ArrayList<>();
        tree.AddChild(root, child);

        tree.DeleteNode(child);

        assertTrue(root.Children.isEmpty());
        assertNull(child.Parent);
    }

    @Test
    void testGetAllNodes() {
        SimpleTreeNode<Integer> root = new SimpleTreeNode<>(1, null);
        SimpleTree<Integer> tree = new SimpleTree<>(root);

        SimpleTreeNode<Integer> child1 = new SimpleTreeNode<>(2, root);
        SimpleTreeNode<Integer> child2 = new SimpleTreeNode<>(3, root);
        root.Children = new ArrayList<>();
        tree.AddChild(root, child1);
        tree.AddChild(root, child2);

        List<SimpleTreeNode<Integer>> allNodes = tree.GetAllNodes();

        assertEquals(3, allNodes.size());
        assertTrue(allNodes.contains(root));
        assertTrue(allNodes.contains(child1));
        assertTrue(allNodes.contains(child2));
    }

    @Test
    void testFindNodesByValue() {
        SimpleTreeNode<Integer> root = new SimpleTreeNode<>(1, null);
        SimpleTree<Integer> tree = new SimpleTree<>(root);

        SimpleTreeNode<Integer> child1 = new SimpleTreeNode<>(2, root);
        SimpleTreeNode<Integer> child2 = new SimpleTreeNode<>(1, root);
        tree.AddChild(root, child1);
        tree.AddChild(root, child2);

        List<SimpleTreeNode<Integer>> nodesWithValue1 = tree.FindNodesByValue(1);

        assertEquals(2, nodesWithValue1.size());
        assertTrue(nodesWithValue1.contains(root));
        assertFalse(nodesWithValue1.contains(child1));
        assertTrue(nodesWithValue1.contains(child2));
    }

    @Test
    void testMoveNode() {
        SimpleTreeNode<Integer> root = new SimpleTreeNode<>(1, null);
        SimpleTree<Integer> tree = new SimpleTree<>(root);

        SimpleTreeNode<Integer> child1 = new SimpleTreeNode<>(2, root);
        SimpleTreeNode<Integer> child2 = new SimpleTreeNode<>(3, root);
        tree.AddChild(root, child1);
        tree.AddChild(root, child2);

        tree.MoveNode(child1, child2);

        assertEquals(1, root.Children.size());
        assertTrue(root.Children.contains(child2));
        assertEquals(1, child2.Children.size());
        assertTrue(child2.Children.contains(child1));
        assertEquals(child2, child1.Parent);
    }

    @Test
    void testCount() {
        SimpleTreeNode<Integer> root = new SimpleTreeNode<>(1, null);
        SimpleTree<Integer> tree = new SimpleTree<>(root);

        SimpleTreeNode<Integer> child1 = new SimpleTreeNode<>(2, root);
        SimpleTreeNode<Integer> child2 = new SimpleTreeNode<>(3, root);
        tree.AddChild(root, child1);
        tree.AddChild(root, child2);

        assertEquals(3, tree.Count());
    }

    @Test
    void testLeafCount() {
        SimpleTreeNode<Integer> root = new SimpleTreeNode<>(1, null);
        SimpleTree<Integer> tree = new SimpleTree<>(root);

        SimpleTreeNode<Integer> child1 = new SimpleTreeNode<>(2, root);
        SimpleTreeNode<Integer> child2 = new SimpleTreeNode<>(3, root);
        tree.AddChild(root, child1);
        tree.AddChild(root, child2);

        assertEquals(2, tree.LeafCount());
    }

    @Test
    void testDeleteRootThrowsException() {
        SimpleTreeNode<Integer> root = new SimpleTreeNode<>(1, null);
        SimpleTree<Integer> tree = new SimpleTree<>(root);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> tree.DeleteNode(root));
        assertEquals("NodeToDelete must not be root", exception.getMessage());
    }
    @Test
    void testDeleteNodeRemovesSubtree() {
        // Создаём дерево
        SimpleTreeNode<Integer> root = new SimpleTreeNode<>(1, null);
        SimpleTree<Integer> tree = new SimpleTree<>(root);

        // Добавляем узлы
        SimpleTreeNode<Integer> child1 = new SimpleTreeNode<>(2, root);
        SimpleTreeNode<Integer> child2 = new SimpleTreeNode<>(3, root);
        SimpleTreeNode<Integer> grandChild1 = new SimpleTreeNode<>(4, child1);
        SimpleTreeNode<Integer> grandChild2 = new SimpleTreeNode<>(5, child1);
        tree.AddChild(root, child1);
        tree.AddChild(root, child2);
        tree.AddChild(child1, grandChild1);
        tree.AddChild(child1, grandChild2);

        // Удаляем узел `child1` (вместе с его потомками)
        tree.DeleteNode(child1);

        // Проверяем, что `child1` больше не существует в дереве
        List<SimpleTreeNode<Integer>> allNodes = tree.GetAllNodes();
        assertFalse(allNodes.contains(child1), "Удалённый узел всё ещё существует в дереве!");

        // Проверяем, что потомки `child1` тоже удалены
        assertFalse(allNodes.contains(grandChild1), "Потомок grandChild1 удалённого узла всё ещё существует!");
        assertFalse(allNodes.contains(grandChild2), "Потомок grandChild2 удалённого узла всё ещё существует!");

        // Убеждаемся, что дерево продолжает корректно работать
        assertEquals(2, tree.Count(), "Количество узлов в дереве некорректно после удаления поддерева!");
        assertEquals(1, tree.LeafCount(), "Количество листьев некорректно после удаления поддерева!");
    }

    @Test
    void testCountInEmptyTree() {
        // Создаём пустое дерево
        SimpleTree<Integer> tree = new SimpleTree<>(null);

        // Проверяем, что количество узлов равно 0
        assertEquals(0, tree.Count(), "Количество узлов в пустом дереве должно быть 0!");
    }

    @Test
    void testGetAllNodesInEmptyTree() {
        // Создаём пустое дерево
        SimpleTree<Integer> tree = new SimpleTree<>(null);

        // Проверяем, что список всех узлов пуст
        List<SimpleTreeNode<Integer>> allNodes = tree.GetAllNodes();
        assertTrue(allNodes.isEmpty(), "Метод GetAllNodes для пустого дерева должен возвращать пустой список!");
    }

    @Test
    public void testAddChild_EmptyTree() {
        // Создаем пустое дерево
        SimpleTree<Integer> tree = new SimpleTree<>(null);

        // Добавляем узел в пустое дерево
        SimpleTreeNode<Integer> newChild = new SimpleTreeNode<>(1, null);
        tree.AddChild(null, newChild);

        // Проверяем, что дерево больше не пустое
        assertNotNull(tree.Root, "Корень должен быть установлен после добавления узла.");
        assertEquals(newChild, tree.Root, "Добавленный узел должен стать корнем.");
        assertEquals(0, tree.Root.Level, "Уровень корня должен быть 0.");
    }

    @Test
    public void testFindNodesByValue_EmptyTree() {
        // Создаем пустое дерево
        SimpleTree<Integer> tree = new SimpleTree<>(null);

        // Пытаемся найти узел со значением 1
        List<SimpleTreeNode<Integer>> foundNodes = tree.FindNodesByValue(1);

        // Проверяем, что ничего не найдено
        assertTrue(foundNodes.isEmpty(), "В пустом дереве не должно быть найдено узлов.");
    }

    @Test
    public void testMoveNode_EmptyTree() {
        // Создаем пустое дерево
        SimpleTree<Integer> tree = new SimpleTree<>(null);

        // Пытаемся переместить узел в пустом дереве
        SimpleTreeNode<Integer> nodeToMove = new SimpleTreeNode<>(1, null);
        SimpleTreeNode<Integer> newParent = new SimpleTreeNode<>(2, null);

        assertThrows(NullPointerException.class, () -> {
            tree.MoveNode(nodeToMove, newParent);
        }, "В пустом дереве операция MoveNode должна выбросить исключение.");
    }

    @Test
    public void testLeafCount_EmptyTree() {
        // Создаем пустое дерево
        SimpleTree<Integer> tree = new SimpleTree<>(null);

        // Проверяем количество листьев
        int leafCount = tree.LeafCount();

        // Ожидаем 0 листьев
        assertEquals(0, leafCount, "В пустом дереве количество листьев должно быть 0.");
    }

    @Test
    public void testUpdateNodesLevels_EmptyTree() {
        SimpleTree<Integer> tree = new SimpleTree<>(null);
        tree.UpdateNodesLevels();
        assertEquals(0, tree.Count(), "Дерево пустое, уровни узлов обновить не требуется.");
    }

    @Test
    public void testUpdateNodesLevels_SingleRootNode() {
        SimpleTreeNode<Integer> root = new SimpleTreeNode<>(1, null);
        SimpleTree<Integer> tree = new SimpleTree<>(root);
        tree.UpdateNodesLevels();
        assertEquals(0, root.Level, "Уровень корня должен быть 0.");
    }

    @Test
    public void testUpdateNodesLevels_MultipleLevels() {
        // Создаем узлы дерева
        SimpleTreeNode<Integer> root = new SimpleTreeNode<>(1, null);
        SimpleTreeNode<Integer> child1 = new SimpleTreeNode<>(2, root);
        SimpleTreeNode<Integer> child2 = new SimpleTreeNode<>(3, root);
        SimpleTreeNode<Integer> child3 = new SimpleTreeNode<>(4, child1);

        // Добавляем узлы
        SimpleTree<Integer> tree = new SimpleTree<>(root);
        tree.AddChild(root, child1);
        tree.AddChild(root, child2);
        tree.AddChild(child1, child3);

        // Обновляем уровни
        tree.UpdateNodesLevels();

        // Проверяем уровни
        assertEquals(0, root.Level, "Уровень корня должен быть 0.");
        assertEquals(1, child1.Level, "Уровень первого потомка должен быть 1.");
        assertEquals(1, child2.Level, "Уровень второго потомка должен быть 1.");
        assertEquals(2, child3.Level, "Уровень дочернего узла второго уровня должен быть 2.");
    }

    @Test
    public void testUpdateNodesLevels_AfterMoveNode() {
        // Создаем узлы дерева
        SimpleTreeNode<Integer> root = new SimpleTreeNode<>(1, null);
        SimpleTreeNode<Integer> child1 = new SimpleTreeNode<>(2, root);
        SimpleTreeNode<Integer> child2 = new SimpleTreeNode<>(3, root);
        SimpleTreeNode<Integer> child3 = new SimpleTreeNode<>(4, child1);

        // Добавляем узлы
        SimpleTree<Integer> tree = new SimpleTree<>(root);
        tree.AddChild(root, child1);
        tree.AddChild(root, child2);
        tree.AddChild(child1, child3);

        // Перемещаем узел child3 под child2
        tree.MoveNode(child3, child2);

        // Проверяем уровни после перемещения
        assertEquals(2, child3.Level, "Уровень перемещенного узла должен быть обновлен.");
        assertEquals(1, child2.Level, "Уровень нового родителя должен остаться прежним.");
    }

    @Test
    public void testUpdateNodesLevels_ComplexTree() {
        // Создаем сложное дерево
        SimpleTreeNode<Integer> root = new SimpleTreeNode<>(1, null);
        SimpleTreeNode<Integer> child1 = new SimpleTreeNode<>(2, root);
        SimpleTreeNode<Integer> child2 = new SimpleTreeNode<>(3, root);
        SimpleTreeNode<Integer> child3 = new SimpleTreeNode<>(4, child1);
        SimpleTreeNode<Integer> child4 = new SimpleTreeNode<>(5, child1);
        SimpleTreeNode<Integer> child5 = new SimpleTreeNode<>(6, child3);

        // Добавляем узлы
        SimpleTree<Integer> tree = new SimpleTree<>(root);
        tree.AddChild(root, child1);
        tree.AddChild(root, child2);
        tree.AddChild(child1, child3);
        tree.AddChild(child1, child4);
        tree.AddChild(child3, child5);

        // Обновляем уровни
        tree.UpdateNodesLevels();

        // Проверяем уровни
        assertEquals(0, root.Level, "Уровень корня должен быть 0.");
        assertEquals(1, child1.Level, "Уровень первого потомка должен быть 1.");
        assertEquals(1, child2.Level, "Уровень второго потомка должен быть 1.");
        assertEquals(2, child3.Level, "Уровень дочернего узла второго уровня должен быть 2.");
        assertEquals(2, child4.Level, "Уровень второго дочернего узла второго уровня должен быть 2.");
        assertEquals(3, child5.Level, "Уровень дочернего узла третьего уровня должен быть 3.");
    }

    @Test
    public void testMoveNode_UpdateLevelsCorrectly() {
        // Создаем дерево
        SimpleTreeNode<Integer> root = new SimpleTreeNode<>(1, null);
        SimpleTreeNode<Integer> child1 = new SimpleTreeNode<>(2, root);
        SimpleTreeNode<Integer> child2 = new SimpleTreeNode<>(3, root);
        SimpleTreeNode<Integer> child3 = new SimpleTreeNode<>(4, child1);

        SimpleTree<Integer> tree = new SimpleTree<>(root);

        tree.AddChild(root, child1);
        tree.AddChild(root, child2);
        tree.AddChild(child1, child3);

        // Проверяем начальные уровни
        assertEquals(0, root.Level, "Уровень корня должен быть 0.");
        assertEquals(1, child1.Level, "Уровень child1 должен быть 1.");
        assertEquals(1, child2.Level, "Уровень child2 должен быть 1.");
        assertEquals(2, child3.Level, "Уровень child3 должен быть 2.");

        // Перемещаем узел child3 под child2
        tree.MoveNode(child3, child2);

        // Проверяем уровни после перемещения
        assertEquals(0, root.Level, "Уровень корня должен быть 0.");
        assertEquals(1, child1.Level, "Уровень child1 должен остаться 1.");
        assertEquals(1, child2.Level, "Уровень child2 должен остаться 1.");
        assertEquals(2, child3.Level, "Уровень child3 должен обновиться до 2.");
        assertEquals(child2, child3.Parent, "Родителем child3 должен стать child2.");
    }

    @Test
    public void testMoveNode_ComplexTree() {
        // Создаем дерево
        SimpleTreeNode<Integer> root = new SimpleTreeNode<>(1, null);
        SimpleTreeNode<Integer> child1 = new SimpleTreeNode<>(2, root);
        SimpleTreeNode<Integer> child2 = new SimpleTreeNode<>(3, root);
        SimpleTreeNode<Integer> child3 = new SimpleTreeNode<>(4, child1);
        SimpleTreeNode<Integer> child4 = new SimpleTreeNode<>(5, child3);

        SimpleTree<Integer> tree = new SimpleTree<>(root);

        tree.AddChild(root, child1);
        tree.AddChild(root, child2);
        tree.AddChild(child1, child3);
        tree.AddChild(child3, child4);

        // Проверяем начальные уровни
        assertEquals(0, root.Level, "Уровень корня должен быть 0.");
        assertEquals(1, child1.Level, "Уровень child1 должен быть 1.");
        assertEquals(1, child2.Level, "Уровень child2 должен быть 1.");
        assertEquals(2, child3.Level, "Уровень child3 должен быть 2.");
        assertEquals(3, child4.Level, "Уровень child4 должен быть 3.");

        // Перемещаем узел child3 под child2
        tree.MoveNode(child3, child2);

        // Проверяем уровни после перемещения
        assertEquals(0, root.Level, "Уровень корня должен быть 0.");
        assertEquals(1, child1.Level, "Уровень child1 должен остаться 1.");
        assertEquals(1, child2.Level, "Уровень child2 должен остаться 1.");
        assertEquals(2, child3.Level, "Уровень child3 должен обновиться до 2.");
        assertEquals(3, child4.Level, "Уровень child4 должен обновиться до 3.");
        assertEquals(child2, child3.Parent, "Родителем child3 должен стать child2.");
    }

    @Test
    public void testComplexTreeOperations() {
        // Создаем узлы дерева
        SimpleTreeNode<Integer> root = new SimpleTreeNode<>(1, null);
        SimpleTreeNode<Integer> child1 = new SimpleTreeNode<>(2, root);
        SimpleTreeNode<Integer> child2 = new SimpleTreeNode<>(3, root);
        SimpleTreeNode<Integer> child3 = new SimpleTreeNode<>(4, child1);
        SimpleTreeNode<Integer> child4 = new SimpleTreeNode<>(5, child1);
        SimpleTreeNode<Integer> child5 = new SimpleTreeNode<>(6, child3);

        // Создаем дерево и добавляем узлы
        SimpleTree<Integer> tree = new SimpleTree<>(root);
        tree.AddChild(root, child1);
        tree.AddChild(root, child2);
        tree.AddChild(child1, child3);
        tree.AddChild(child1, child4);
        tree.AddChild(child3, child5);

        // Проверяем начальные уровни
        assertEquals(0, root.Level, "Уровень корня должен быть 0.");
        assertEquals(1, child1.Level, "Уровень child1 должен быть 1.");
        assertEquals(1, child2.Level, "Уровень child2 должен быть 1.");
        assertEquals(2, child3.Level, "Уровень child3 должен быть 2.");
        assertEquals(2, child4.Level, "Уровень child4 должен быть 2.");
        assertEquals(3, child5.Level, "Уровень child5 должен быть 3.");

        // Перемещаем узел child3 под child2
        tree.MoveNode(child3, child2);
        assertEquals(2, child3.Level, "Уровень child3 после перемещения должен быть 2.");
        assertEquals(3, child5.Level, "Уровень child5 после перемещения должен быть 3.");

        // Удаляем узел child4
        tree.DeleteNode(child4);
        List<SimpleTreeNode<Integer>> allNodesAfterDelete = tree.GetAllNodes();
        assertFalse(allNodesAfterDelete.contains(child4), "child4 должен быть удален из дерева.");

        // Находим узел по значению
        List<SimpleTreeNode<Integer>> foundNodes = tree.FindNodesByValue(3);
        assertEquals(1, foundNodes.size(), "Должен быть найден ровно один узел со значением 3.");
        assertEquals(child2, foundNodes.get(0), "Найденный узел должен быть равен child2.");

        // Проверяем общее количество узлов
        assertEquals(5, tree.Count(), "Общее количество узлов должно быть 5.");

        // Проверяем количество листьев
        assertEquals(2, tree.LeafCount(), "Количество листьев должно быть 2 (child5 и child2).");

        // Добавляем новый узел
        SimpleTreeNode<Integer> child6 = new SimpleTreeNode<>(7, root);
        tree.AddChild(root, child6);
        assertEquals(1, child6.Level, "Уровень нового узла child6 должен быть 1.");

        // Проверяем дерево после всех операций
        List<SimpleTreeNode<Integer>> allNodes = tree.GetAllNodes();
        assertTrue(allNodes.contains(root), "Дерево должно содержать корень.");
        assertTrue(allNodes.contains(child1), "Дерево должно содержать child1.");
        assertTrue(allNodes.contains(child2), "Дерево должно содержать child2.");
        assertTrue(allNodes.contains(child3), "Дерево должно содержать child3.");
        assertTrue(allNodes.contains(child5), "Дерево должно содержать child5.");
        assertTrue(allNodes.contains(child6), "Дерево должно содержать child6.");
    }

    @Test
    void testEvenTrees() {
        // Создание узлов
        SimpleTreeNode<Integer> root = new SimpleTreeNode<>(1, null);
        SimpleTree<Integer> tree = new SimpleTree<>(root);

        SimpleTreeNode<Integer> node2 = new SimpleTreeNode<>(2, root);
        SimpleTreeNode<Integer> node3 = new SimpleTreeNode<>(3, root);
        SimpleTreeNode<Integer> node4 = new SimpleTreeNode<>(4, node2);
        SimpleTreeNode<Integer> node5 = new SimpleTreeNode<>(5, node2);
        SimpleTreeNode<Integer> node6 = new SimpleTreeNode<>(6, node3);
        SimpleTreeNode<Integer> node7 = new SimpleTreeNode<>(7, node3);
        SimpleTreeNode<Integer> node8 = new SimpleTreeNode<>(8, node6);
        SimpleTreeNode<Integer> node9 = new SimpleTreeNode<>(9, node8);
        SimpleTreeNode<Integer> node10 = new SimpleTreeNode<>(10, node8);

        // Добавление узлов в дерево
        tree.AddChild(root, node2);
        tree.AddChild(root, node3);
        tree.AddChild(node2, node4);
        tree.AddChild(node2, node5);
        tree.AddChild(node3, node6);
        tree.AddChild(node3, node7);
        tree.AddChild(node6, node8);
        tree.AddChild(node8, node9);
        tree.AddChild(node8, node10);

        // Проверка результата EvenTrees
        List<Integer> expected = Arrays.asList(1, 3, 3, 6);
        assertEquals(expected, tree.EvenTrees());
    }
}
