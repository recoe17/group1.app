package sms;

import javax.swing.SwingUtilities;

public class GuiMain {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            SchoolManagementGui gui = new SchoolManagementGui();
            gui.setVisible(true);
        });
    }
}
