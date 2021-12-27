package com.securedb.pharmacy.form;

import com.securedb.pharmacy.entity.SqlEntity;
import com.securedb.pharmacy.entity.Type;
import com.securedb.pharmacy.entity.impl.Drug;
import com.securedb.pharmacy.utils.GlobalConstants;
import com.securedb.pharmacy.entity.Desease;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Objects;

public class AddEditDrugFrom {

    private static final DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

    private JPanel addEditDrugPanel;
    private JTextField name;
    private JTextField price;
    private JSpinner quantity;
    private JTextField startDate;
    private JTextField expireDate;
    private JComboBox<Type> type;
    private JComboBox<Desease> desease;
    private JButton submit;
    private JFrame frame;

    public AddEditDrugFrom(String tittle, Drug drug, DefaultListModel<SqlEntity> model) {
        frame = new JFrame(tittle);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setContentPane(this.addEditDrugPanel);
        frame.setResizable(false);
        Dimension windowSize = new Dimension(300, 300);
        frame.setMinimumSize(windowSize);
        try {
            frame.setIconImage(ImageIO.read(Objects.requireNonNull(
                    MainForm.class.getClassLoader().getResourceAsStream(GlobalConstants.ICON_RESOURCE))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        frame.pack();
        frame.setVisible(true);

        for (Type typeValue : Type.values()) {
            type.addItem(typeValue);
        }

        for (Desease deseaseValue : Desease.values()) {
            desease.addItem(deseaseValue);
        }

        if (drug != null) {
            name.setText(drug.getName());
            price.setText(drug.getPrice() + "");
            quantity.setValue(drug.getQuantity());
            startDate.setText(drug.getInceptDate().toString());
            expireDate.setText(drug.getExpireDate().toString());
            type.setSelectedItem(drug.getType());
            desease.setSelectedItem(drug.getDesease());
        }


        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (drug == null) {
                    Drug drug1 = null;
                    try {
                        drug1 = new Drug(
                                name.getText(),
                                Double.parseDouble(price.getText()),
                                (Integer) quantity.getValue(),
                                formatter.parse(startDate.getText()),
                                formatter.parse(expireDate.getText()),
                                (Type) type.getSelectedItem(),
                                (Desease) desease.getSelectedItem()
                        );
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(addEditDrugPanel, GlobalConstants.ERR_CANNOT_PARSE_DRUG);
                    }
                    if (drug1 != null) {
                        MainForm.getDrugRepo().saveDrug(drug1);
                        model.addElement(drug1);
                        frame.dispose();
                    }
                } else {
                    try {
                        drug.setName(name.getText());
                        drug.setPrice(Double.parseDouble(price.getText()));
                        drug.setQuantity((Integer) quantity.getValue());
                        drug.setInceptDate(formatter.parse(startDate.getText()));
                        drug.setExpireDate(formatter.parse(expireDate.getText()));
                        drug.setType((Type) type.getSelectedItem());
                        drug.setDesease((Desease) desease.getSelectedItem());
                        MainForm.getDrugRepo().saveDrug(drug);
                        frame.dispose();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                        JOptionPane.showMessageDialog(addEditDrugPanel, GlobalConstants.ERR_CANNOT_PARSE_DRUG);
                    }
                }
            }
        });
    }

}
