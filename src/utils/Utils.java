package utils;

import java.awt.FlowLayout;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Utils {

    public static String contactsPath = "/home/santi/NetBeansProjects/POO_ACTIVITY_6/src/files/contacts.txt";

    public static String temporalPath = "/home/santi/NetBeansProjects/POO_ACTIVITY_6/src/files/temporal.txt";

    public static void setContactsInPanel(JPanel panel, File file) throws IOException {
        panel.removeAll();
        RandomAccessFile raf = new RandomAccessFile(file, "rw");
        while (raf.getFilePointer() < raf.length()) {
            String nameNumberString = raf.readLine();
            String[] lineSplit = nameNumberString.split("!");
            String name = lineSplit[0];
            long number = Long.parseLong(lineSplit[1]);
            panel.add(new JLabel("Friend Name: " + name + ", number: " + number + "\n"));
        }
        panel.setBorder(BorderFactory.createEtchedBorder());
        panel.setLayout(new BoxLayout(panel,BoxLayout.Y_AXIS));
        panel.revalidate();
        panel.repaint();
    }

    public static File createOrGetFile(String path) throws IOException {
        File file = new File(path);
        if (!file.exists()) {
            file.createNewFile();
        }
        return file;
    }

    public static void writeInFile(File file, String contactName, long contactNumber) throws IOException {
        RandomAccessFile raf = new RandomAccessFile(file, "rw");
        if (Utils.isInFile(raf, contactName, contactNumber)) {
            JOptionPane.showMessageDialog(null, "¡El contacto que deseas agregar ya existe!");
            raf.close();
            return;
        }
        raf.writeBytes(contactName + "!" + contactNumber);
        raf.writeBytes(System.lineSeparator());
        raf.close();
        JOptionPane.showMessageDialog(null, "¡El contacto " + contactName + " se ha agregado con exito!");
    }

    public static void deleteInFile(File file, String contactName) throws IOException {
        RandomAccessFile raf = new RandomAccessFile(file, "rw");
        if (!Utils.isInFile(raf, contactName)) {
            JOptionPane.showMessageDialog(null, "¡El contacto " + contactName + " no existe!");
            raf.close();
            return;
        }
        File tmpFile = new File(Utils.temporalPath);
        RandomAccessFile tmpraf = new RandomAccessFile(tmpFile, "rw");
        raf.seek(0);
        while (raf.getFilePointer() < raf.length()) {
            String nameNumberString = raf.readLine();
            int index = nameNumberString.indexOf('!');
            String name = nameNumberString.substring(0, index);
            if (name.equals(contactName)) {
                continue;
            }
            tmpraf.writeBytes(nameNumberString);
            tmpraf.writeBytes(System.lineSeparator());
        }
        raf.seek(0);
        tmpraf.seek(0);
        while (tmpraf.getFilePointer() < tmpraf.length()) {
            raf.writeBytes(tmpraf.readLine());
            raf.writeBytes(System.lineSeparator());
        }
        raf.setLength(tmpraf.length());
        tmpraf.close();
        raf.close();
        tmpFile.delete();
        JOptionPane.showMessageDialog(null, "¡El contacto " + contactName + " se ha eliminado con exito!");
    }

    public static void updateInFile(File file, String contactName, long contactNumber) throws IOException {
        RandomAccessFile raf = new RandomAccessFile(file, "rw");
        if (!Utils.isInFile(raf, contactName)) {
            JOptionPane.showMessageDialog(null, "¡El contacto " + contactName + " no existe!");
            raf.close();
            return;
        }
        File tmpFile = new File(Utils.temporalPath);
        RandomAccessFile tmpraf = new RandomAccessFile(tmpFile, "rw");
        raf.seek(0);
        while (raf.getFilePointer() < raf.length()) {
            String nameNumberString = raf.readLine();
            int index = nameNumberString.indexOf('!');
            String name = nameNumberString.substring(0, index);
            if (name.equals(contactName)) {
                nameNumberString = name + "!" + String.valueOf(contactNumber);
            }
            tmpraf.writeBytes(nameNumberString);
            tmpraf.writeBytes(System.lineSeparator());
        }
        raf.seek(0);
        tmpraf.seek(0);
        while (tmpraf.getFilePointer() < tmpraf.length()) {
            raf.writeBytes(tmpraf.readLine());
            raf.writeBytes(System.lineSeparator());
        }
        raf.setLength(tmpraf.length());
        tmpraf.close();
        raf.close();
        tmpFile.delete();
        JOptionPane.showMessageDialog(null, "¡El contacto " + contactName + " se ha actualizado con exito!");
    }

    public static boolean isInFile(RandomAccessFile raf, String contactName, long contactNumber) throws IOException {
        boolean contactFound = false;
        while (raf.getFilePointer() < raf.length()) {
            String nameNumberString = raf.readLine();
            String[] lineSplit = nameNumberString.split("!");
            String name = lineSplit[0];
            long number = Long.parseLong(lineSplit[1]);
            if (name.equals(contactName) || number == contactNumber) {
                contactFound = true;
            }
        }
        return contactFound;
    }

    public static boolean isInFile(RandomAccessFile raf, String contactName) throws IOException {
        boolean contactFound = false;
        while (raf.getFilePointer() < raf.length()) {
            String nameNumberString = raf.readLine();
            String[] lineSplit = nameNumberString.split("!");
            String name = lineSplit[0];
            if (name.equals(contactName)) {
                contactFound = true;
            }
        }
        return contactFound;
    }

    public static boolean isInFile(File file, String contactName, long contactNumber) throws IOException {
        RandomAccessFile raf = new RandomAccessFile(file, "rw");
        boolean found = Utils.isInFile(raf, contactName, contactNumber);
        raf.close();
        return found;
    }
}
