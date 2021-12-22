package com.securedb.pharmacy.form;

import com.securedb.pharmacy.entity.SqlEntity;
import com.securedb.pharmacy.entity.impl.Client;
import com.securedb.pharmacy.entity.impl.Drug;
import com.securedb.pharmacy.utils.GlobalConstants;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Objects;

public class CRUDForm {
    private JList itemList;
    private JButton changeButton;
    private JButton deleteButton;
    private JLabel formLabel;
    private JPanel crudPanel;
    private JButton newButton;
    private JFrame frame;

    public CRUDForm(String tittle, boolean isClients) {
        DefaultListModel<SqlEntity> model = new DefaultListModel<>();
        if (isClients) {
            for (Client client : MainForm.getClientRepo().getAll()) {
                model.addElement(client);
            }
        } else {
            for (Drug drug : MainForm.getDrugRepo().getAll()) {
                model.addElement(drug);
            }
        }

        this.setupFrame(tittle, model);

        changeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        newButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object item = itemList.getSelectedValue();
                if (item != null) {
                    if (isClients) {
                        final Client client = (Client) item;
                        if (!MainForm.getOrderRepo().getByClient(client).isEmpty()) {
                            JOptionPane.showMessageDialog(frame, GlobalConstants.ERR_CLIENT_IN_USE);
                        } else {
                            MainForm.getClientRepo().deleteClient(client);
                            itemList.clearSelection();
                            model.removeElement(item);
                        }
                    } else {
                        final Drug drug = (Drug) item;
                        if (!MainForm.getOrderRepo().getByDrug(drug).isEmpty()) {
                            JOptionPane.showMessageDialog(frame, GlobalConstants.ERR_DRUG_IN_USE);
                        } else {
                            MainForm.getDrugRepo().deleteDrug(drug);
                            itemList.clearSelection();
                            model.removeElement(item);
                        }
                    }
                }
            }
        });

        changeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Object item = itemList.getSelectedValue();
                if (item != null) {
                    if (isClients) {
                        final Client client = (Client) item;
                        new AddEditClientForm("Редектировать Клиента", client, model);
                    } else {
                        final Drug drug = (Drug) item;
                        new AddEditDrugFrom("Редектировать Лекарство", drug, model);
                    }
                }
            }
        });

        newButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isClients) {
                    new AddEditClientForm("Добавить Клиента", null, model);
                } else {
                    new AddEditDrugFrom("Добавить Лекарство", null, model);
                }
            }
        });
    }

    private void setupFrame(String tittle, DefaultListModel<SqlEntity> model) {
        itemList.setLayoutOrientation(JList.VERTICAL);
        itemList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        itemList.setFixedCellHeight(100);
        itemList.setModel(model);

        formLabel.setText(tittle);
        frame = new JFrame(tittle);
        Dimension windowSize = new Dimension(500, 500);
        frame.setMinimumSize(windowSize);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setContentPane(this.crudPanel);
        frame.setResizable(false);
        try {
            frame.setIconImage(ImageIO.read(Objects.requireNonNull(
                    MainForm.class.getClassLoader().getResourceAsStream(GlobalConstants.ICON_RESOURCE))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        frame.pack();
        frame.setVisible(true);
    }

}
