package com.securedb.pharmacy.form;

import com.securedb.pharmacy.entity.impl.Client;
import com.securedb.pharmacy.entity.SqlEntity;
import com.securedb.pharmacy.entity.impl.Drug;
import com.securedb.pharmacy.entity.impl.Order;
import com.securedb.pharmacy.entity.impl.OrderNumber;
import com.securedb.pharmacy.utils.GlobalConstants;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Objects;

public class OrderForm {
    private JTextField orderNumber;
    private JComboBox clientSelect;
    private JList drugList;
    private JComboBox selectDrugs;
    private JButton addButton;
    private JButton submit;
    private JButton deleteButton;
    private JPanel orderPanel;
    private JFrame frame;

    private DefaultListModel<SqlEntity> orderDrugs;

    public OrderForm(Order order, boolean isEdit, DefaultListModel<SqlEntity> rootOrderList, JList list) {
        final String tittle;
        if (isEdit) {
            tittle = "Изменить Заказ";
        } else {
            tittle = "Добавить Заказ";
        }
        final Order editableOrder = this.setupFrame(order, tittle, isEdit);

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final Drug drug = (Drug) drugList.getSelectedValue();
                if (drug == null) {
                    JOptionPane.showMessageDialog(orderPanel, GlobalConstants.ERR_ORDER_NOT_SELECT);
                } else {
                    editableOrder.getDrugs().remove(drug);
                    drugList.clearSelection();
                    orderDrugs.removeElement(drug);
                }
            }
        });

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final Drug drug = (Drug) selectDrugs.getSelectedItem();
                if (drug != null) {
                    editableOrder.getDrugs().add(drug);
                    orderDrugs.addElement(drug);
                }
            }
        });

        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final Client client = (Client) clientSelect.getSelectedItem();
                if (client != null && !editableOrder.getClient().equals(client)) {
                    editableOrder.setClient(client);
                }
                if (!isEdit) {
                    editableOrder.setOrderNumber(new OrderNumber());
                }
                MainForm.getOrderRepo().saveOrder(editableOrder);
                if (!isEdit && rootOrderList != null) {
                    rootOrderList.addElement(editableOrder);
                }
                //JOptionPane.showMessageDialog(orderPanel, GlobalConstants.INFO_SAVED);
                frame.dispose();
            }
        });
    }

    private Order setupFrame(Order order, String tittle, boolean isEdit) {
        for (Client client : MainForm.getClientRepo().getAll()) {
            clientSelect.addItem(client);
            if (isEdit) {
                clientSelect.setSelectedItem(order.getClient());
            }
        }
        for (Drug drug : MainForm.getDrugRepo().getAll()) {
            selectDrugs.addItem(drug);
        }
        this.orderDrugs = new DefaultListModel<>();
        if (isEdit) {
            for (Drug drug : order.getDrugs()) {
                this.orderDrugs.addElement(drug);
            }
            orderNumber.setText(order.getOrderNumber() + "");
        } else {
            orderNumber.setText("Создается при сохранении");
            order = new Order((Client) clientSelect.getSelectedItem());
        }
        orderNumber.setEditable(false);

        drugList.setLayoutOrientation(JList.VERTICAL);
        drugList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        drugList.setFixedCellHeight(80);
        drugList.setModel(orderDrugs);

        frame = new JFrame(tittle);
        Dimension windowSize = new Dimension(500, 500);
        frame.setMinimumSize(windowSize);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setContentPane(this.orderPanel);
        frame.setResizable(false);
        try {
            frame.setIconImage(ImageIO.read(Objects.requireNonNull(
                    MainForm.class.getClassLoader().getResourceAsStream(GlobalConstants.ICON_RESOURCE))));
        } catch (IOException e) {
            e.printStackTrace();
        }
        frame.pack();
        frame.setVisible(true);
        return order;
    }

}
