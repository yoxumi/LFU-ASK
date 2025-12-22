import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        // Uruchamiamy okno symulatora LFU
        SwingUtilities.invokeLater(() -> {
            InterfejsGraficzny okno = new InterfejsGraficzny();
            okno.setVisible(true);
        });
    }
}