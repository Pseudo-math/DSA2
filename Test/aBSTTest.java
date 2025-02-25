import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.stream.*;

class aBSTTest {
    aBST tree;

    @BeforeEach
    void setUp() {
        tree = new aBST(2); // Дерево глубины 2 (максимум 7 узлов)
    }

    @Test
    void testEmptyTree() {
        assertEquals(0, tree.FindKeyIndex(10));
    }

    @Test
    void testAddKeyToEmptyTree() {
        int index = tree.AddKey(10);
        assertEquals(0, index);
        assertEquals(10, tree.Tree[0]);
    }

    @Test
    void testFindExistingKey() {
        tree.AddKey(10);
        tree.AddKey(5);
        tree.AddKey(15);
        assertEquals(0, tree.FindKeyIndex(10));
        assertEquals(1, tree.FindKeyIndex(5));
        assertEquals(2, tree.FindKeyIndex(15));
    }

    @Test
    void testFindNonExistingKey() {
        tree.AddKey(10);
        tree.AddKey(5);
        tree.AddKey(15);
        int expectedIndex = -6; // 20 должен вставляться в Tree[6]
        assertEquals(expectedIndex, tree.FindKeyIndex(20));
    }

    @Test
    void testAddDuplicateKey() {
        tree.AddKey(10);
        int index = tree.AddKey(10);
        assertEquals(0, index);
    }

    @Test
    void testFindInsertionIndex() {
        tree.AddKey(10);
        int expectedIndex = -1; // Например, если 7 должен вставляться в Tree[1]
        assertEquals(expectedIndex, tree.FindKeyIndex(7));
    }

    @Test
    void testTreeFull() {
        tree.AddKey(4);
        tree.AddKey(2);
        tree.AddKey(1);
        tree.AddKey(3);
        tree.AddKey(6);
        tree.AddKey(5);
        tree.AddKey(7);

        Integer[] expected = {4, 2, 6, 1, 3, 5, 7};

        assertArrayEquals(tree.Tree, expected);
    }

    @Test
    void testFindInTreeFull() {
        tree.AddKey(4);
        tree.AddKey(2);
        tree.AddKey(1);
        tree.AddKey(3);
        tree.AddKey(6);
        tree.AddKey(5);
        tree.AddKey(7);

        assertNull(tree.FindKeyIndex(9));
    }
    @Test
    void testInsertionIntoMiddleLevel() {
        tree.AddKey(10);
        tree.AddKey(5);
        tree.AddKey(15);
        int expectedIndex = -4; // Например, если 6 должен вставляться в Tree[4]
        assertEquals(expectedIndex, tree.FindKeyIndex(6));
    }

    @Test
    void testInsertionFailsWhenTreeIsFull() {
        tree.AddKey(10);
        tree.AddKey(5);
        tree.AddKey(15);
        tree.AddKey(3);
        tree.AddKey(7);
        tree.AddKey(12);
        tree.AddKey(17);
        assertEquals(-1, tree.AddKey(8)); // Дерево полностью заполнено, вставка невозможна
    }
}
