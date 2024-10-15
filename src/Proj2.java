import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;

public class Proj2 {
    public static void main(String[] args) throws IOException {
        // Use command line arguments to specify the input file
        if (args.length != 2) {
            System.err.println("Usage: java TestAvl <input file> <number of lines>");
            System.exit(1);
        }

        String inputFileName = args[0];
        int numLines = Integer.parseInt(args[1]);

        // For file input
        FileInputStream inputFileNameStream = null;
        Scanner inputFileNameScanner = null;

        // Open the input file
        inputFileNameStream = new FileInputStream(inputFileName);
        inputFileNameScanner = new Scanner(inputFileNameStream);

        // ignore first line
        inputFileNameScanner.nextLine();

        // store dataset in an ArrayList
        ArrayList<Integer> dataList = new ArrayList<>();

        // read up to specified number of lines
        while (inputFileNameScanner.hasNextLine() && dataList.size() < numLines) {
            dataList.add(Integer.parseInt(inputFileNameScanner.nextLine().trim()));
        }

        // close input streams
        inputFileNameScanner.close();
        inputFileNameStream.close();

        // create copies of data for sorted and shuffled versions
        ArrayList<Integer> sortedData = new ArrayList<>(dataList);
        ArrayList<Integer> shuffledData = new ArrayList<>(dataList);

        // sort and shuffle data
        Collections.sort(sortedData);
        Collections.shuffle(shuffledData);

        // create BST and AVL trees
        BST<Integer> bstSorted = new BST<>();
        BST<Integer> bstShuffled = new BST<>();
        AvlTree<Integer> avlSorted = new AvlTree<>();
        AvlTree<Integer> avlShuffled = new AvlTree<>();

        // measure insertion times
        long bstSortedInsertTime = measureInsertionTime(bstSorted, sortedData);
        long bstShuffledInsertTime = measureInsertionTime(bstShuffled, shuffledData);
        long avlSortedInsertTime = measureInsertionTime(avlSorted, sortedData);
        long avlShuffledInsertTime = measureInsertionTime(avlShuffled, shuffledData);

        // measure search times
        long bstSearchTime = measureSearchTime(bstSorted, dataList);
        long avlSearchTime = measureSearchTime(avlSorted, dataList);

        // print results to console
        System.out.printf("BST Insert (Sorted): %d ns%n", bstSortedInsertTime);
        System.out.printf("BST Insert (Shuffled): %d ns%n", bstShuffledInsertTime);
        System.out.printf("AVL Insert (Sorted): %d ns%n", avlSortedInsertTime);
        System.out.printf("AVL Insert (Shuffled): %d ns%n", avlShuffledInsertTime);
        System.out.printf("BST Search: %d ns%n", bstSearchTime);
        System.out.printf("AVL Search: %d ns%n", avlSearchTime);

        // write results to output.txt
        try (FileOutputStream out = new FileOutputStream("output.txt", true)) {
            String result = String.format("%d,%d,%d,%d,%d,%d,%d%n",
                    numLines, bstSortedInsertTime, bstShuffledInsertTime,
                    avlSortedInsertTime, avlShuffledInsertTime,
                    bstSearchTime, avlSearchTime);
            out.write(result.getBytes());
        }

    }

    private static <T extends Comparable<T>> long measureInsertionTime(BST<T> tree, ArrayList<T> data) {
        long start = System.nanoTime();
        for (T value : data) {
            tree.insert(value);
        }
        return System.nanoTime() - start;
    }

    private static <T extends Comparable<T>> long measureInsertionTime(AvlTree<T> tree, ArrayList<T> data) {
        long start = System.nanoTime();
        for (T value : data) {
            tree.insert(value);
        }
        return System.nanoTime() - start;
    }

    private static <T extends Comparable<T>> long measureSearchTime(BST<T> tree, ArrayList<T> data) {
        long start = System.nanoTime();
        for (T value : data) {
            tree.search(value);
        }
        return System.nanoTime() - start;
    }

    private static <T extends Comparable<T>> long measureSearchTime(AvlTree<T> tree, ArrayList<T> data) {
        long start = System.nanoTime();
        for (T value : data) {
            tree.contains(value);
        }
        return System.nanoTime() - start;
    }
}
