
import java.io.IOException;
import javax.swing.JOptionPane;
import utils.Utils;

public class Main {

    public static void main(String[] args) {
        try {
            Utils.createOrGetFile(Utils.contactsPath);
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "No se pudo obtener el archivo de contactos.");
        }
    }
}
