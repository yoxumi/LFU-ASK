import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class InterfejsGraficzny extends JFrame {
    private JTextField poleSekwencja;
    private JTextField poleRozmiarCache;
    private JButton przyciskSymuluj;
    private JButton przyciskLosowa;
    private JPanel panelWizualizacji;
    private JTextArea poleLog;
    private SymulatorLFU symulator;
    private List<Integer> sekwencja;
    private int aktualnyKrok = 0;
    private Timer timer;

    public InterfejsGraficzny() {
        setTitle("Symulator LFU - Zastępowanie Stron");
        setSize(1000, 700); // większy rozmiar, żeby wszystko się zmieściło
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // === Górny panel z inputem ===
        JPanel panelInput = new JPanel();
        poleSekwencja = new JTextField(30);
        poleRozmiarCache = new JTextField(5);
        przyciskSymuluj = new JButton("Symuluj");
        przyciskLosowa = new JButton("Losowa sekwencja");

        panelInput.add(new JLabel("Sekwencja stron (np. 1,2,3,1,4):"));
        panelInput.add(poleSekwencja);
        panelInput.add(new JLabel("Rozmiar cache:"));
        panelInput.add(poleRozmiarCache);
        panelInput.add(przyciskSymuluj);
        panelInput.add(przyciskLosowa);

        add(panelInput, BorderLayout.NORTH);

        // === Środkowy panel – wizualizacja ===
        panelWizualizacji = new PanelWizualizacji();
        add(panelWizualizacji, BorderLayout.CENTER);

        // === Dolny panel – log tekstowy ===
        poleLog = new JTextArea(10, 30);
        poleLog.setEditable(false);
        add(new JScrollPane(poleLog), BorderLayout.SOUTH);

        // Akcje przycisków
        przyciskSymuluj.addActionListener(e -> startSymulacja());
        przyciskLosowa.addActionListener(e -> generujLosowaSekwencje());

        // Timer – animacja co 1 sekunda
        timer = new Timer(1000, e -> animujKrok());
    }

    private void generujLosowaSekwencje() {
        Random rand = new Random();
        int dlugosc = 10 + rand.nextInt(11); // 10-20 elementów
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < dlugosc; i++) {
            sb.append(rand.nextInt(10) + 1).append(",");
        }
        poleSekwencja.setText(sb.substring(0, sb.length() - 1));
    }

    private void startSymulacja() {
        try {
            int rozmiarCache = Integer.parseInt(poleRozmiarCache.getText().trim());
            if (rozmiarCache <= 0) {
                JOptionPane.showMessageDialog(this, "Rozmiar cache musi być > 0!");
                return;
            }

            symulator = new SymulatorLFU(rozmiarCache);

            String tekst = poleSekwencja.getText().trim();
            if (tekst.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Wpisz sekwencję stron!");
                return;
            }

            String[] czesci = tekst.split(",");
            sekwencja = new ArrayList<>();
            for (String czesc : czesci) {
                sekwencja.add(Integer.parseInt(czesc.trim()));
            }

            poleLog.setText("Start symulacji...\n\n");
            aktualnyKrok = 0;
            timer.start();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Błąd: Wpisz tylko liczby oddzielone przecinkami!");
        }
    }

    private void animujKrok() {
        if (aktualnyKrok >= sekwencja.size()) {
            timer.stop();
            poleLog.append("\n=== Symulacja zakończona ===\n");
            return;
        }

        int strona = sekwencja.get(aktualnyKrok);
        symulator.odwolanieDoStrony(strona);

        poleLog.append("Krok " + (aktualnyKrok + 1) + ": Strona " + strona + "\n");
        panelWizualizacji.repaint();
        aktualnyKrok++;
    }

    // Panel rysujący zawartość cache
    class PanelWizualizacji extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setFont(new Font("Arial", Font.BOLD, 16));

            // Pobieramy aktualne strony z symulatora
            Collection<Strona> strony = (symulator != null) ? symulator.pobierzStronyWCache() : null;

            if (strony == null || strony.isEmpty()) {
                g.setColor(Color.RED);
                g.drawString("Cache jest pusty – zacznij symulację", 50, 100);
                return;
            }

            int rozmiarBoxa = 120;
            int margines = 30;
            int x = 50;
            int y = 50;

            for (Strona s : strony) {
                // Tło ramki
                g.setColor(new Color(173, 216, 230)); // jasnoniebieski
                g.fillRoundRect(x, y, rozmiarBoxa, rozmiarBoxa + 30, 20, 20);

                // Obramowanie
                g.setColor(Color.BLACK);
                g.drawRoundRect(x, y, rozmiarBoxa, rozmiarBoxa + 30, 20, 20);

                // Tekst
                g.drawString("Strona: " + s.numerStrony, x + 15, y + 40);
                g.drawString("Użycia: " + s.czestotliwosc, x + 15, y + 70);

                x += rozmiarBoxa + margines;

                // Jeśli za dużo ramek – przenieś do nowego wiersza
                if (x + rozmiarBoxa > getWidth()) {
                    x = 50;
                    y += rozmiarBoxa + 60;
                }
            }
        }
    }

}