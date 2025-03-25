import java.math.BigInteger;
import java.util.*;

public class Tasks {
    private static final Map<String, Runnable> tasks = new LinkedHashMap<>() {{
        put("var  5: Знайти кількість додатних елементів кожного рядка.", Tasks::task1);
        put("var  5: Транспонувати матрицю, лишаючи її в тому самому масиві", Tasks::task2);
        put("var  9: Упорядкувати за неспаданням головну діагональ матриці", Tasks::task3);
        put("var 10: Упорядкувати рядки матриці за неспаданням добутків елементів у цих рядках.", Tasks::task4);
    }};

    private static void task1() {
        List<List<Integer>> matrix = MatrixUtils.requestIntMatrix(false);
        List<Long> result = matrix.stream().map(row -> row.stream().filter(x -> x > 0).count()).toList();
        System.out.println("Result:");
        for (int i = 0; i < matrix.size(); i++) {
            System.out.println("\t" + result.get(i) + " -> " + matrix.get(i));
        }
    }

    private static void task2() {
        List<List<Integer>> matrix = MatrixUtils.toMutableMatrix(MatrixUtils.requestIntMatrix(true));
        for (int i = 0; i < matrix.size(); i++) {
            for (int j = i + 1; j < matrix.get(i).size(); j++) {
                int temp = matrix.get(i).get(j);
                matrix.get(i).set(j, matrix.get(j).get(i));
                matrix.get(j).set(i, temp);
            }
        }
        System.out.println("Result:");
        for (List<Integer> row : matrix) {
            System.out.println("\t" + row);
        }
    }

    private static void task3() {
        List<List<Integer>> matrix = MatrixUtils.toMutableMatrix(MatrixUtils.requestIntMatrix(true));
        boolean swapped;
        do {
            swapped = false;
            for (int i = 1; i < matrix.size(); i++) {
                if (matrix.get(i).get(i) < matrix.get(i - 1).get(i - 1)) {
                    swapped = true;
                    int temp = matrix.get(i).get(i);
                    matrix.get(i).set(i, matrix.get(i - 1).get(i - 1));
                    matrix.get(i - 1).set(i - 1, temp);
                }
            }
        } while (swapped);
        System.out.println("Result:");
        for (List<Integer> row : matrix) {
            System.out.println("\t" + row);
        }
    }

    private static void task4() {
        List<List<Integer>> matrix = MatrixUtils.requestIntMatrix(false);

        List<BigInteger> mulFold = matrix.stream()
                .map(row -> row.stream()
                        .map(BigInteger::valueOf)
                        .reduce(BigInteger.ONE, BigInteger::multiply))
                .toList();

        List<BigInteger[]> indexedRows = new ArrayList<>();
        for (int i = 0; i < matrix.size(); i++) {
            indexedRows.add(new BigInteger[]{mulFold.get(i), BigInteger.valueOf(i)});
        }

        indexedRows.sort(Comparator.comparing(a -> a[0]));

        System.out.println("Result:");
        for (BigInteger[] pair : indexedRows) {
            System.out.println("\t" + pair[0] + " -> " + matrix.get(pair[1].intValue()));
        }
    }


    private static void runTask(int task) {
        tasks.values().stream().skip(task).findFirst().ifPresent(Runnable::run);
    }

    public static void selectAndRunTask() {
        System.out.println("Select task:");
        int index = 1;
        for (String key : tasks.keySet()) {
            System.out.println("\t" + index++ + " -> " + key);
        }
        runTask(MatrixUtils.readInt("", false) - 1);
    }
}
