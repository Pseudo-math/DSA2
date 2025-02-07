import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

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
}
