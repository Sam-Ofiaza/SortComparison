public abstract class Sorter
{
    private long comparisons;
    private long movements;

    public Sorter() { comparisons = 0; movements = 0; }
    public void incComparisons(int x) { comparisons += x; }
    public void incMovements(int x) { movements += x; }
    public void setComparisons(long x) { comparisons = x; }
    public void setMovements(long x) { movements = x; }
    public long getComparisons() { return comparisons; }
    public long getMovements() { return movements; }
    public abstract void sort(int[] list);
}
