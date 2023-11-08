package view;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class WelcomeWindow {
    public static void showWelcomeWindow(JLabel labelToUpdate) {
        JFrame newFrame = new JFrame("LightsOut");
        JPanel panelVentanaNueva = new JPanel();
        panelVentanaNueva.setLayout(new BoxLayout(panelVentanaNueva, BoxLayout.Y_AXIS));

        newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        newFrame.setSize(300, 200);

        JLabel LabelBienvenida = new JLabel("Bienvenido porfavor ingrese su nombre: ");
        LabelBienvenida.setHorizontalAlignment(JLabel.CENTER);
        panelVentanaNueva.add(LabelBienvenida);

        JTextField textField = new JTextField(20);
        panelVentanaNueva.add(textField);

        JButton botonContinuar = new JButton("Continuar: ");
        panelVentanaNueva.add(botonContinuar);

        newFrame.setLocationRelativeTo(null);
        newFrame.add(panelVentanaNueva);

        newFrame.setVisible(true);

        botonContinuar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String text = textField.getText();
                labelToUpdate.setText(text); // Update the external JLabel
                newFrame.dispose(); // Close the window
                // You can perform other actions with the entered name here
            }
        });
    }
}
