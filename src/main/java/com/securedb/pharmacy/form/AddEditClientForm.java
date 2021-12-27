package com.securedb.pharmacy.form;

import com.securedb.pharmacy.entity.SqlEntity;
import com.securedb.pharmacy.entity.impl.Client;
import com.securedb.pharmacy.utils.GlobalConstants;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Objects;

public class AddEditClientForm implements GlobalConstants {
    private JTextField nameField;
    private JTextField surnameField;
    private JTextField phoneField;
    private JButton btnSubmit;
    private JPanel addEditPanel;
    private JFrame frame;

    public AddEditClientForm(String tittle, Client client, DefaultListModel<SqlEntity> model) {
        frame = new JFrame(tittle);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setContentPane(this.addEditPanel);
        Dimension windowSize = new Dimension(300, 300);
        frame.setMinimumSize(windowSize);
        frame.setResizable(false);

        try {
            frame.setIconImage(ImageIO.read(Objects.requireNonNull(
                    MainForm.class.getClassLoader().getResourceAsStream(GlobalConstants.ICON_RESOURCE))));
        } catch (IOException e) {
            e.printStackTrace();
        }

        frame.pack();
        frame.setVisible(true);

        if (client != null) {
            nameField.setText(client.getName());
            surnameField.setText(client.getSurname());
            phoneField.setText(client.getPhoneNumber());
        }

        btnSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (client == null) {
                    Client client1 = new Client(nameField.getText(), surnameField.getText(), phoneField.getText());

                    MainForm.getClientRepo().saveClient(client1);
                    model.addElement(client1);
                } else {
                    client.setName(nameField.getText());
                    client.setSurname(surnameField.getText());
                    client.setPhoneNumber(phoneField.getText());
                    MainForm.getClientRepo().saveClient(client);
                }
                frame.dispose();
            }
        });
    }

}
