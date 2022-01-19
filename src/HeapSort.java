public class HeapSort extends Sorter{
    public void sort(int[] list) {
        // Copying int[] list to Integer[] list will add O(N) time
        Integer[] list2 = new Integer[list.length];
        int x = 0;
        for(int y : list)
            list2[x++] = Integer.valueOf(y);

        heapSort(list2);
    }

    /** Heap sort method */
    public <E extends Comparable<E>> long[] heapSort(E[] list) {
        //  Create a Heap of integers
        Heap<E> heap = new Heap<>();

        //  Add elements to the heap
        for (int i = 0; i < list.length; i++)
            heap.add(list[i]);

        //  Remove elements from the heap
        for (int i = list.length -1; i >= 0; i--)
            list[i] = heap.remove();

        /*System.out.print("After insertion sort: [");
        for(int i = 0; i < list.length; i++)
            if(i == list.length-1)
                System.out.println(list[i] + "]");
            else
                System.out.print(list[i] + ", ");*/

        long[] data = heap.getData();
        setComparisons(data[0]);
        setMovements(data[1]);

        return heap.getData();
    }

    /**
     /** A test method *
     public static void main(String[] args) {
     Integer[] list = {-44, -5, -3, 3, 3, 1, -4, 0, 1, 2, 4, 5, 53};
     heapSort(list);
     for(int i = 0; i < list.length; i++)
     System.out.println(list[i] + " ");
     }
     */
}