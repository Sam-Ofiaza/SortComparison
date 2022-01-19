// getInOrderList/getReverseOrderList - 10^8 max length w/ 1 gb for ~1s
// getAlmostOrderList/getRandomOrderList - 10^5 max length w/ 1 gb for ~1s

public class ComparisonRunner {
    public static void main(String[] args) {
        // Experiment: Modify each sort to output movements and comparisons, then test for time and store in a 2-D array
        // I had to hardcode each sort with an associated index b/c I do not know how to change which sort object I am calling within a loop

        String[] names = {"InsertionSort", "SelectionSort", "QuickSort", "MergeSort", "HeapSort", "RadixSort"};

        ComparisonRunnerGUI gui = new ComparisonRunnerGUI(names);
        
        /*
        int trials = 50;
        int listLength = 10000;
        double ratio = 0.8;
        boolean[] selectedLists = {true, true, true, true, true, false}; // Corresponds with String[] names
        
        long[][] inOrderData = groupSortTester(0, listLength, trials, ratio, selectedLists);
        System.out.println("InOrder data for list length 10000 and 50 trials:\n" + dataPrinter(inOrderData));
        
        long[][] reverseOrderData = groupSortTester(1, listLength, trials, ratio, selectedLists);
        System.out.println("ReverseOrder data for list length 10000 and 50 trials:\n" + dataPrinter(reverseOrderData));
        
        long[][] almostOrderData = groupSortTester(2, listLength, trials, ratio, selectedLists);
        System.out.println("AlmostOrder data for list length 10000, 80% sorted list, and 50 trials:\n" + dataPrinter(almostOrderData));
        
        long[][] randomOrderData = groupSortTester(3, listLength, trials, ratio, selectedLists);
        System.out.println("RandomOrder data for list length 10000 and 50 trials:\n" + dataPrinter(randomOrderData));
        
        // Winning algorithms with percent difference
        
        double[] inOrderWinnerData = getWinningSort(inOrderData);
        long inOrderWinnerTime = inOrderData[(int)inOrderWinnerData[6]][0];
        System.out.println("InOrder " + winnerDataPrinter(inOrderWinnerData, inOrderWinnerTime));
        
        double[] reverseOrderWinnerData = getWinningSort(reverseOrderData);
        long reverseOrderWinnerTime = reverseOrderData[(int)reverseOrderWinnerData[6]][0];
        System.out.println("ReverseOrder " + winnerDataPrinter(reverseOrderWinnerData, reverseOrderWinnerTime));
        
        double[] almostOrderWinnerData = getWinningSort(almostOrderData);
        long almostOrderWinnerTime = almostOrderData[(int)almostOrderWinnerData[6]][0];
        System.out.println("AlmostOrder " + winnerDataPrinter(almostOrderWinnerData, almostOrderWinnerTime));
        
        double[] randomOrderWinnerData = getWinningSort(randomOrderData);
        long randomOrderWinnerTime = randomOrderData[(int)randomOrderWinnerData[6]][0];
        System.out.println("RandomOrder " + winnerDataPrinter(randomOrderWinnerData, randomOrderWinnerTime));*/
    }

    public static long[][] groupSortTester(int listType, int testListLength, int trials, double ratio, boolean[] selectedLists) {
        // Ratio only used for getAlmostOrderList
        long[][] data = new long[6][3]; // 6 rows for each sort, 3 columns for time, comparisons, and movements
        for(int i = 0; i < trials; i++) {
            // Add times for each sort per iteration, then divide for average at the end

            int[] list = getNewList(listType, testListLength, ratio);
            long[] insertionData = selectedLists[0] ? individualSortTester(0, list) : new long[3];

            list = getNewList(listType, testListLength, ratio);
            long[] selectionData = selectedLists[1] ? individualSortTester(1, list) : new long[3];

            list = getNewList(listType, testListLength, ratio);
            long[] quickData = selectedLists[2] ? individualSortTester(2, list) : new long[3];

            list = getNewList(listType, testListLength, ratio);
            long[] mergeData = selectedLists[3] ? individualSortTester(3, list) : new long[3];

            list = getNewList(listType, testListLength, ratio);
            long[] heapData = selectedLists[4] ? individualSortTester(4, list) : new long[3];

            list = getNewList(listType, testListLength, ratio);
            long[] radixData = selectedLists[5] ? individualSortTester(5, list) : new long[3];

            for(int j = 0; j < 3; j++) {
                data[0][j] += insertionData[j];
                data[1][j] += selectionData[j];
                data[2][j] += quickData[j];
                data[3][j] += mergeData[j];
                data[4][j] += heapData[j];
                data[5][j] += radixData[j];
            }
        }
        // Find average over all trials
        for(int i = 0; i < data.length; i++) {
            for(int j = 1; j < data[i].length; j++) {
                data[i][j] /= trials;
            }
        }

        return data;
    }

    public static long[] individualSortTester(int type, int[] list) { // Sort used is given by int type - an index in String[] names
        Sorter sorter;
        long[] output = new long[3];

        switch(type) {
            case 0: sorter = new InsertionSort(); break;
            case 1: sorter = new SelectionSort(); break;
            case 2: sorter = new QuickSort(); break;
            case 3: sorter = new MergeSort(); break;
            case 4: sorter = new HeapSort(); break;
            case 5: sorter = new RadixSort(); break;
            default: return output;
        }

        long start = System.nanoTime();
        sorter.sort(list);
        long end = System.nanoTime();

        output[0] = end-start;
        output[1] = sorter.getComparisons();
        output[2] = sorter.getMovements();

        return output;
    }

    public static int[] getNewList(int type, int testListLength, double ratio) { // Type: 0 = inOrder, 1 = reverse, 2 = almost, 3 = random
        int[] list = {};
        switch(type) {
            case 0 : list = ArrayMaker.getInOrderList(testListLength); break;
            case 1 : list = ArrayMaker.getReverseOrderList(testListLength); break;
            case 2 : list = ArrayMaker.getAlmostOrderList(testListLength, ratio); break;
            case 3 : list = ArrayMaker.getRandomOrderList(testListLength); break;
        }
        return list;
    }

    public static String dataPrinter(long[][] data) {
        String[] names = {"InsertionSort", "SelectionSort", "QuickSort", "MergeSort", "HeapSort", "RadixSort"};
        String output = "";
        for(int i = 0; i < data.length; i++) {
            output += names[i] + " data: " + data[i][0] + " ns, " + data[i][1] + " comparisons, " + data[i][2] + " movements\n";
        }
        return output + "\n";
    }

    public static double[] getWinningSort(long[][] data) {
        // Returns percent difference between winning sort and other sorts

        long minTime = Integer.MAX_VALUE;
        int winner = 0;
        for(int i = 0; i < data.length; i++)
            if(data[i][0] != 0 && data[i][0] < minTime) {
                minTime = data[i][0];
                winner = i;
            }

        double[] output = new double[data.length + 1]; // Last index for index of winner in listData
        for(int i = 0; i < data.length; i++) {
            double x = (double)data[winner][0];
            double y = (double)data[i][0];
            double percentDiff = ((y-x) / ((y+x)/2)) * 100;
            double roundedDiff = (double)Math.round(percentDiff * 10)/10;
            output[i] = roundedDiff;
        }
        output[6] = (double)winner;

        return output;
    }

    public static String winnerDataPrinter(double[] data, long winnerTime) {
        String[] names = {"InsertionSort", "SelectionSort", "QuickSort", "MergeSort", "HeapSort", "RadixSort"};
        String output = "";

        int winner = 0;
        for(int i = 0; i < data.length-1; i++) {
            if(data[i] == 0.0)
                winner = i;
        }
        double winnerTimeMS = ((double)(winnerTime))/1000000;
        double roundedTimeMS = (double)Math.round(winnerTimeMS * 10)/10;
        output += "Winning algorithm: " + names[winner] + " (" + roundedTimeMS + " ms)\n";
        for(int i = 0; i < data.length-1; i++) {
            if(data[i] == -200.0)
                output += names[i] + ": N/A\n";
            else
                output += names[i] + ": " +  data[i] + "%\n";
        }

        return output;
    }

    public static String printArray(int[] list) {
        String output = "[";
        for(int i = 0; i < list.length; i++)
            if(i == list.length-1)
                output += list[i] + "]";
            else
                output += list[i] + ", ";

        return output;
    }
}