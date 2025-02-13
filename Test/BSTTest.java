import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class BSTTest {
    private BST<Integer> tree;
    private BST<Integer> emptyTree;

    @BeforeEach
    void setUp() {
        tree = new BST<>(new BSTNode<>(10, 10, null));
        emptyTree = new BST<>(null);
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
}
