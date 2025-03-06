import java.util.Arrays;

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
}