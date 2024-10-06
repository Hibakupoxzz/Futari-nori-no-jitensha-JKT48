import javax.swing.*;
import java.awt.*;

public class Teleport extends JPanel {

    // Array lirik lagu
    private String[] lyrics = {
            "Ah, mungkin bagi dirimu",
            "Hanya teman sekelas saja",
            "Yang jalan pulangnya searah",
            "Keberadaan yang seperti angin!!",
            "Ah, yang selalu bercanda",
            "Padahal kita s'lalu saling bicara",
            "Mengapa hari ini",
            "Cinta tak abadi yang berputar jauh"
    };

    // Delay untuk setiap baris lirik
    private int[] delays = {
            2000, 2000, 1000, 2000, 2000, 2000, 2000, 5000
    };

    // Variabel status untuk mengatur alur tampilan
    private int currentIndex = 0;
    private String currentLine = "";
    private int currentCharIndex = 0;
    private ImageIcon backgroundGif;

    // Konstruktor panel
    public Teleport() {
        setPreferredSize(new Dimension(800, 600));
        // Memuat gambar latar belakang (GIF)
        backgroundGif = new ImageIcon("farm.gif");
        
        // Memulai thread untuk menampilkan lirik satu per satu
        new Thread(() -> {
            try {
                while (currentIndex < lyrics.length) {
                    if (currentCharIndex < lyrics[currentIndex].length()) {
                        // Menambahkan satu karakter ke currentLine
                        currentLine += lyrics[currentIndex].charAt(currentCharIndex);
                        currentCharIndex++;
                        repaint(); // Memperbarui tampilan
                        Thread.sleep(130); // Waktu jeda antar karakter
                    } else {
                        Thread.sleep(delays[currentIndex]); // Jeda setelah satu baris selesai
                        currentIndex++; // Pindah ke baris berikutnya
                        currentLine = ""; // Mengatur ulang baris saat ini
                        currentCharIndex = 0; // Mengatur ulang indeks karakter
                        repaint(); // Memperbarui tampilan
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    // Metode untuk menggambar komponen
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Menggambar gambar latar belakang jika tersedia
        if (backgroundGif != null) {
            g.drawImage(backgroundGif.getImage(), 0, 0, getWidth(), getHeight(), this);
        }

        // Mengatur font dan warna teks
        g.setFont(new Font("Serif", Font.PLAIN, 24));
        
        // Mengatur posisi Y untuk teks agar berada di tengah secara vertikal
        int y = (getHeight() - lyrics.length * 30) / 2;

        // Mengatur posisi X untuk teks agar berada di tengah secara horizontal
        int stringWidth = g.getFontMetrics().stringWidth(currentLine);
        int x = (getWidth() - stringWidth) / 2;
        
        // Menggambar teks utama dengan warna hitam
        g.setColor(Color.BLACK);
        g.drawString(currentLine, x, y + currentIndex * 30);
    }

    // Metode main untuk menjalankan program
    public static void main(String[] args) {
        JFrame frame = new JFrame("Lirik Lagu");
        Teleport panel = new Teleport();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null); // Menempatkan jendela di tengah layar
        frame.setVisible(true); // Menampilkan jendela
    }
}
