import java.util.*;
import java.util.stream.Collectors;

class MatrixUtils
{
    private static final Random rand = new Random();

    public static int nextInt(Random random, int min, int max) {
        return random.nextInt(max - min + 1) + min;
    }

    public static int[] parseIntRange(String input) {
        String[] parts = input.split("\\.\\.");
        if (parts.length == 2) {
            try {
                int start = Integer.parseInt(parts[0]);
                int end = Integer.parseInt(parts[1]);
                if (start <= end) {
                    return new int[]{start, end};
                }
            } catch (NumberFormatException ignored) {}
        }
        return null;
    }

    public static List<List<Integer>> toMutableMatrix(List<List<Integer>> matrix) {
        return matrix.stream().map(ArrayList::new).collect(Collectors.toList());
    }

    public static int[] readIntRange(String message) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            if (!message.isEmpty()) System.out.println(message);
            int[] range = parseIntRange(scanner.nextLine());
            if (range != null) return range;
            System.out.println("Invalid input");
        }
    }

    public static int readInt(String message, boolean inputSign) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            if (!message.isEmpty()) System.out.println(message);
            if (inputSign) System.out.print("> ");
            try {
                return Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input");
            }
        }
    }

    public static List<Integer> generateIntList(int[] lengthRange, int[] elementRange) {
        int length = nextInt(rand, lengthRange[0], lengthRange[1]);
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < length; i++) {
            list.add(nextInt(rand, elementRange[0], elementRange[1]));
        }
        return list;
    }

    public static List<Integer> inputIntList(int size) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            List<Integer> numbers = Arrays.stream(scanner.nextLine().split(" "))
                    .map(String::trim)
                    .map(s -> {
                        try { return Integer.parseInt(s); } catch (NumberFormatException e) { return null; }
                    })
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            if (size <= 0 || numbers.size() == size) return numbers;
            System.out.println("Invalid input");
        }
    }

    public static List<List<Integer>> inputIntMatrix(boolean isSquare) {
        int n = readInt("Enter number of rows:", false);
        System.out.println("Enter " + n + " sub-arrays of matrix" + (isSquare ? " with " + n + " elements in each:" : ":"));
        List<List<Integer>> matrix = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            matrix.add(inputIntList(isSquare ? n : -1));
        }
        return matrix;
    }

    public static List<List<Integer>> generateIntMatrix(boolean isSquare) {
        int n = readInt("Enter number of rows:", false);
        int[] lengthRange = isSquare ? new int[]{n, n} : readIntRange("Enter sub-array random length range. (Example 1..10)");
        int[] elementRange = readIntRange("Enter sub-array elements range. (Example 1..10)");
        List<List<Integer>> matrix = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            matrix.add(generateIntList(lengthRange, elementRange));
        }
        System.out.println("Generated matrix:");
        for (List<Integer> row : matrix) {
            System.out.println("\t" + row);
        }
        return matrix;
    }

    public static List<List<Integer>> requestIntMatrix(boolean isSquare) {
        System.out.println("""
            Input types:
                1 -> line by line
                2 -> random
            """);
        System.out.println("Enter input type:");
        int choice = readInt("", false);
        if (choice == 1) return inputIntMatrix(isSquare);
        if (choice == 2) return generateIntMatrix(isSquare);
        throw new IllegalArgumentException("Invalid input");
    }
}
