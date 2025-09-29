import javax.swing.SwingUtilities;

public class MainGame {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainMenu());
    }
}