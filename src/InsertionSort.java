public class InsertionSort extends Sorter{
    public void sort(int[] list) {
        for (int i = 1; i < list.length; i++) {
            /** Insert list[i] into a sorted sublist list[0..i-1] so that
             *  list[0..i] is sorted
             */
            int currentElement = list[i];
            int k;

            if(list[i-1] <= currentElement) // // Counting possible failed initial comparison
                incComparisons(1);
            for (k = i-1; k>= 0 && list[k] > currentElement; k--) {
                list[k+1] = list[k];
                incComparisons(1);
                incMovements(1);
                if(k < 0 || list[k] <= currentElement) // Counting failed final comparison
                    incComparisons(1);
            }


            //insert the current element into list[k + 1]
            list[k + 1] = currentElement;
            incMovements(1);
        }
        //System.out.println("After insertion sort: " + ComparisonRunner.printArray(list));
    }
}