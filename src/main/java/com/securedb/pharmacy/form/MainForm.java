package com.securedb.pharmacy.form;

import com.securedb.pharmacy.entity.impl.Order;
import com.securedb.pharmacy.repository.impl.ClientRepositoryImpl;
import com.securedb.pharmacy.repository.impl.DrugRepositoryImpl;
import com.securedb.pharmacy.repository.impl.OrderRepositoryImpl;
import com.securedb.pharmacy.entity.SqlEntity;
import com.securedb.pharmacy.repository.ClientRepository;
import com.securedb.pharmacy.repository.DrugRepository;
import com.securedb.pharmacy.repository.OrderRepository;
import com.securedb.pharmacy.utils.GlobalConstants;
import org.hibernate.Session;

import javax.imageio.ImageIO;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.*;
import javax.swing.text.html.HTMLDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class MainForm implements GlobalConstants {
    private JPanel rootPanel;
    private JTextField searchField;
    private JButton searchButton;
    private JList officeList;
    private JButton editOfficeButton;
    private JButton deleteOfficeButton;
    private JButton addNewOfficeButton;
    private JButton clientsButton;
    private JTextPane details;
    private JButton drugsButton;

    @PersistenceContext
    private static EntityManager ENTITY_MANAGER;

    private static ClientRepository CLIENT_REPO;
    private static OrderRepository ORDER_REPO;
    private static DrugRepository DRUG_REPO;

    private MainForm() {
        DefaultListModel<SqlEntity> model = new DefaultListModel<>();
        for (Order order : getOrderRepo().getAll()) {
            model.addElement(order);
        }
        details.setEditable(false);
        StyledDocument doc = (StyledDocument) details.getDocument();
        Style style = doc.addStyle("StyleName", null);
        StyleConstants.setFontSize(style, 18);
        HTMLDocument html = (HTMLDocument) doc;
        //details.setContentType("text/html");
        //details.setFont(details.getFont().deriveFont(12f));

        officeList.setLayoutOrientation(JList.VERTICAL);
        officeList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        officeList.setFixedCellHeight(100);
        officeList.setModel(model);

        officeList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                Order entity = (Order) officeList.getSelectedValue();
                if (entity != null) {
                    try {
                        html.setInnerHTML(getBodyElement(html), entity.getInfo());
                    } catch (BadLocationException ex) {
                        ex.printStackTrace();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        clientsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CRUDForm("Клиенты", true);
            }
        });

        drugsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new CRUDForm("Лекарства", false);
            }
        });

        editOfficeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final Order order = (Order) officeList.getSelectedValue();
                if (order == null) {
                    JOptionPane.showMessageDialog(rootPanel, GlobalConstants.ERR_ORDER_NOT_SELECT);
                } else {
                    new OrderForm(order, true, null, null);
                }
            }
        });

        deleteOfficeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final Order order = (Order) officeList.getSelectedValue();
                if (order == null) {
                    JOptionPane.showMessageDialog(rootPanel, GlobalConstants.ERR_ORDER_NOT_SELECT);
                } else {
                    try {
                        html.setInnerHTML(getBodyElement(html), "<br>");
                    } catch (BadLocationException | IOException ex) {
                        ex.printStackTrace();
                    }
                    officeList.clearSelection();
                    model.removeElement(order);
                    MainForm.getOrderRepo().deleteOrder(order);
                }
            }
        });

        addNewOfficeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new OrderForm(null, false, model, officeList);
            }
        });

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final String clientName = searchField.getText();
                List<Order> searching;
                model.clear();
                if (clientName == null || clientName.isEmpty()) {
                    searching = ORDER_REPO.getAll();
                } else {
                    searching = ORDER_REPO.getByClientName(clientName);
                }
                for (Order entity : searching) {
                    model.addElement(entity);
                }
            }
        });
    }

    private static Element getBodyElement(final HTMLDocument document) {
        final Element body = findBodyElement(document
                .getDefaultRootElement());
        if (body == null) {
            throw new IllegalArgumentException(
                    "Not found <body> tag in given document.");
        }

        return body;
    }

    private static Element findBodyElement(final Element element) {
        if (element.getName().equals("body")) {
            return element.getElement(0);
        }

        for (int i = 0; i < element.getElementCount(); i++) {
            final Element child = findBodyElement(element.getElement(i));
            if (child != null) {
                return child;
            }
        }

        return null;
    }

    static void frameSettings(MainForm form) throws IOException {
        JFrame frame = new JFrame(GlobalConstants.MAIN_FRAME_TITTLE);
        Dimension windowSize = new Dimension(800, 500);

        frame.setIconImage(ImageIO.read(
                Objects.requireNonNull(MainForm.class.getClassLoader().getResourceAsStream(GlobalConstants.ICON_RESOURCE))));
        frame.setContentPane(form.rootPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(windowSize);
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        try {
            EntityManagerFactory factory = Persistence
                    .createEntityManagerFactory("pharmacy-persistence-unit");
            ENTITY_MANAGER = factory.createEntityManager();
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            MainForm form = new MainForm();
            frameSettings(form);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, GlobalConstants.ERR_DB_NOT_CONNECT);
        }
    }

    private static Session getSession() {
        if (ENTITY_MANAGER == null) {
            EntityManagerFactory factory = Persistence
                    .createEntityManagerFactory("pharmacy-persistence-unit");
            ENTITY_MANAGER = factory.createEntityManager();
        }
        return ENTITY_MANAGER.unwrap(Session.class);
    }

    public static ClientRepository getClientRepo() {
        if (CLIENT_REPO == null) {
            CLIENT_REPO = new ClientRepositoryImpl(getSession());
        }
        return CLIENT_REPO;
    }

    public static OrderRepository getOrderRepo() {
        if (ORDER_REPO == null) {
            ORDER_REPO = new OrderRepositoryImpl(getSession());
        }
        return ORDER_REPO;
    }

    public static DrugRepository getDrugRepo() {
        if (DRUG_REPO == null) {
            DRUG_REPO = new DrugRepositoryImpl(getSession());
        }
        return DRUG_REPO;
    }

}
