public class ProgressPrinter {

    private int total;
    private Statistics stats;

    public ProgressPrinter(int total, Statistics stats) {
        this.total = total;
        this.stats = stats;
    }

    public void print(int current) {
        if (current % 10_000 == 0) {
            double percent = (current * 100.0) / total;
            System.out.printf(
                "Przetworzono: %d / %d (%.2f%%) Hit ratio: %.2f%%%n",
                current, total, percent, stats.getHitRatio()
            );
        }
    }
}
