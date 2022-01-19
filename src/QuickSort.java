public class QuickSort extends Sorter{
    private int[] list;

    public void sort(int[] list) {
        quickSort(list, 0, list.length-1);
    }

    public void quickSort(int[] list, int first, int last) {
        if (last > first) {
            int pivotIndex = partition(list, first, last);
            quickSort(list, first, pivotIndex -1);
            quickSort(list, pivotIndex + 1, last);
        }
    }

    /** Partition the array list[first..last] */
    public int partition(int[] list, int first, int last) {
        int pivot = list[first]; // Choose the first element as the pivot
        int low = first + 1; // Index for forward search
        int high = last; //Index for backward search

        while (high > low) {
            // Search forward from left
            if(list[high] > pivot)
                incComparisons(1);
            while (low <= high && list[low] <= pivot) {
                low++;
                incComparisons(1);
                if(low > high || list[low] > pivot)
                    incComparisons(1);
            }

            // Search backward from right
            if(list[high] <= pivot)
                incComparisons(1);
            while (low <= high && list[high] > pivot) {
                high--;
                incComparisons(1);
                if(low > high || list[high] <= pivot)
                    incComparisons(1);
            }

            //  Swap two elements in the list
            if (high > low) {
                int temp = list[high];
                list[high] = list[low];
                list[low] = temp;
                incMovements(1);
            }
            incComparisons(1);
        }

        if(list[high] < pivot)
            incComparisons(1);
        while (high > first && list[high] >= pivot) {
            high--;
            incComparisons(1);
            if(high <= first || list[high] < pivot)
                incComparisons(1);
        }

        //  Swap pivot with list[high]
        incComparisons(1);
        if (pivot > list[high]) {
            list[first] = list[high];
            list[high] = pivot;
            incMovements(1);
            return high;
        }
        else {
            return first;
        }
    }
}
