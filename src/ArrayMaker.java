import java.util.HashSet;
import java.util.Random;

public class ArrayMaker
{
    public static int[] getInOrderList(int len) {
        int[] list = new int[len];

        for(int i = 0; i < list.length; i++)
            list[i] = i+1;

        return list;
    }

    public static int[] getReverseOrderList(int len) {
        int[] list = new int[len];

        for(int i = 0; i < list.length; i++)
            list[i] = list.length-i;

        return list;
    }

    public static int[] getAlmostOrderList(int len, double sortedRatio) {
        // To make 'fragments' of sorted elements, predetermine sorted indices and leave the rest random

        int[] list = new int[len];

        int numSortedElements = (int)((double)(len * sortedRatio));
        HashSet<Integer> sortedIndices = new HashSet<Integer>();
        int selectedIndex = getRandomNum(len);
        for(int i = 0; i < numSortedElements; i++) {
            while(sortedIndices.contains(selectedIndex))
                selectedIndex = getRandomNum(len);
            sortedIndices.add(selectedIndex);
        }

        HashSet<Integer> usedNums = new HashSet<Integer>();
        int num = getRandomNum(len);
        for(int i = 0; i < list.length; i++) {
            if(sortedIndices.contains(i))
                list[i] = i;
            else {
                while(usedNums.contains(num) || sortedIndices.contains(num))
                    num = getRandomNum(len);
                list[i] = num;
                usedNums.add(num);
                num = getRandomNum(len);
            }
        }

        return list;
    }

    public static int[] getRandomOrderList(int len) {
        int[] list = new int[len];
        HashSet<Integer> usedNums = new HashSet<Integer>();

        int num = getRandomNum(len);
        for(int i = 0; i < list.length; i++) {
            while(usedNums.contains(num))
                num = getRandomNum(len);
            list[i] = num;
            usedNums.add(num);
            num = getRandomNum(len);
        }

        return list;
    }

    public static int getRandomNum(int max) { // 0 to max, excluding max
        Random rand = new Random();
        return rand.nextInt(max);
    }
}