//5. Строим сбалансированные двоичные деревья поиска
//3.* Реализуйте метод удаления узла из двоичного дерева, заданного в виде массива. Несмотря на отсутствие пустых мест, метод должен корректно перестраивать дерево, сохраняя балансировку.
//Код: https://github.com/Pseudo-math/DSA2/blob/master/src/AlgorithmsDataStructures2.java#L43
//Тесты: https://github.com/Pseudo-math/DSA2/blob/master/Test/AlgorithmsDataStructures2Test.java#L27
//Метод deleteNode имеет временную сложность O(n), где n — количество элементов в дереве, так как он выполняет in-order обход дерева (O(n)),
// удаление элемента из списка (O(n) в худшем случае) и перестройку сбалансированного бинарного дерева поиска (O(n)).
// Пространственная сложность также O(n), поскольку требуется дополнительный список для хранения элементов дерева и временный массив для отсортированных данных.
//4.* Подумайте, как ответить на вопрос с картинки (реально, периодически на собесах так подкалывают).
// Текст с картинки "Sort B tree in O(1) time"
// На данный момент на курсе ACД-2 Б-деревьев не было, поэтому я думаю про BST.
// Ответ: дерево уже отсортировано, действительно, если представить BST, то чем левее узел, тем он меньше (больше).
//       4
//   2       6
// 1   3   5   7
// Как видно слева на право 1 - 2 - 3 - 4 - 5 - 6 - 7, то есть в дереве левее меньшие вершины,
// а  правее большие, значит дерево отсортировано.

