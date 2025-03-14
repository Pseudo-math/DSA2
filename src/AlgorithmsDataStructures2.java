import java.util.*;

public class AlgorithmsDataStructures2 {
    public static int[] GenerateBBSTArray(int[] a) {
        int[] sorted = Arrays.copyOf(a, a.length);
        Arrays.sort(sorted);
        int[] result = new int[a.length];
        generateBBSTArrayRecursive(sorted, result, 0, sorted.length - 1, 0);
        return result;
    }

    private static void generateBBSTArrayRecursive(int[] sorted, int[] result, int start, int end, int currentIndex) {
        if (start > end) {
            return;
        }
        int mid = start + (end - start) / 2;
        if (currentIndex < result.length) {
            result[currentIndex] = sorted[mid];
        }
        generateBBSTArrayRecursive(sorted, result, start, mid - 1, 2 * currentIndex + 1);
        generateBBSTArrayRecursive(sorted, result, mid + 1, end, 2 * currentIndex + 2);
    }
    private static void generateBBSTArrayRecursive(Integer[] sorted, Integer[] result, Integer start, Integer end, Integer currentIndex) {
        if (start > end) {
            return;
        }
        int mid = start + (end - start + 1) / 2;
        if (currentIndex < result.length) {
            result[currentIndex] = sorted[mid];
        }
        generateBBSTArrayRecursive(sorted, result, start, mid - 1, 2 * currentIndex + 1);
        generateBBSTArrayRecursive(sorted, result, mid + 1, end, 2 * currentIndex + 2);
    }

    public static void recoverSortedList(Integer[] bbstArray, int index, List<Integer> result) {
        if (index >= bbstArray.length || bbstArray[index] == null) {
            return;
        }
        recoverSortedList(bbstArray, 2 * index + 1, result);
        result.add(bbstArray[index]);
        recoverSortedList(bbstArray, 2 * index + 2, result);
    }
    // Я создал deleteNode метод для Integer[], но все null при балансировке переносит в конец, поэтому идейно подходит для int[]
    public static boolean deleteNode(Integer[] bbstArray, Integer valueToDelete) {
        List<Integer> elements = new ArrayList<>();
        recoverSortedList(bbstArray, 0, elements);

        boolean removed = elements.remove(valueToDelete);

        if (!removed) {
            return false;
        }

        Arrays.fill(bbstArray, null);

        Integer[] sortedArray = elements.toArray(new Integer[0]);
        generateBBSTArrayRecursive(sortedArray, bbstArray, 0, sortedArray.length - 1, 0);

        return true;
    }
}
