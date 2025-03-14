import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class AlgorithmsDataStructures2Test {

    @Test
    public void testGenerateBBSTArrayEmpty() {
        int[] input = {};
        int[] result = AlgorithmsDataStructures2.GenerateBBSTArray(input);
        assertArrayEquals(new int[]{}, result);
    }

    @Test
    public void testGenerateBBSTArraySingleElement() {
        int[] input = {5};
        int[] result = AlgorithmsDataStructures2.GenerateBBSTArray(input);
        assertArrayEquals(new int[]{5}, result);
    }

    @Test
    public void testGenerateBBST() {
        int[] input = {1, 2, 3, 4, 5, 7, 6};
        int[] result = AlgorithmsDataStructures2.GenerateBBSTArray(input);
        assertArrayEquals(new int[]{4, 2, 6, 1, 3, 5, 7}, result);
    }

    @Test
    public void testDeleteExistingElement() {
        Integer[] bbstArray = {4, 2, 6, 1, 3, 5, 7};
        boolean result = AlgorithmsDataStructures2.deleteNode(bbstArray, 2);
        assertTrue(result);
        assertArrayEquals(new Integer[]{5, 3, 7, 1, 4, 6, null}, bbstArray);
    }

    @Test
    public void testDeleteNonExistingElement() {
        Integer[] bbstArray = {4, 2, 6, 1, 3, 5, 7};
        Integer[] originalArray = bbstArray.clone();
        boolean result = AlgorithmsDataStructures2.deleteNode(bbstArray, 8);
        assertFalse(result);
        assertArrayEquals(originalArray, bbstArray);
    }

    @Test
    public void testDeleteFromSingleElementTree() {
        Integer[] bbstArray = {1};
        boolean result = AlgorithmsDataStructures2.deleteNode(bbstArray, 1);
        assertTrue(result);
        assertArrayEquals(new Integer[]{null}, bbstArray);
    }

    @Test
    public void testDeleteFromEmptyTree() {
        Integer[] bbstArray = {};
        boolean result = AlgorithmsDataStructures2.deleteNode(bbstArray, 1);
        assertFalse(result);
        assertArrayEquals(new Integer[]{}, bbstArray);
    }

    @Test
    public void testDeleteRoot() {
        Integer[] bbstArray = {4, 2, 6, 1, 3, 5, 7};
        boolean result = AlgorithmsDataStructures2.deleteNode(bbstArray, 4);
        assertTrue(result);
        assertArrayEquals(new Integer[]{5, 2, 7, 1, 3, 6, null}, bbstArray);
    }
}