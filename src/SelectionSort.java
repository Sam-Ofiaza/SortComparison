public class SelectionSort extends Sorter{
    public void sort(int[] list) {
        for (int i = 0; i < list.length -1; i++) {
            // Find the minimum in the list[i..list.length-1]
            int currentMin = list[i];
            int currentMinIndex = i;

            for (int j = i+1; j < list.length; j++) {
                if (currentMin > list[j]) {
                    currentMin = list[j];
                    currentMinIndex = j;
                }
                incComparisons(1);
            }

            //  Swap list[i] wiht list[currentMinIndex[ if necessary
            if (currentMinIndex != i) {
                list[currentMinIndex] = list[i];
                list[i] = currentMin;
                incMovements(1);
            }
        }
        //System.out.println("After selection sort: " + ComparisonRunner.printArray(list));
    }
}