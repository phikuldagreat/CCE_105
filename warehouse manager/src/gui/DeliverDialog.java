package gui;

import Storage.StorageManager;
import models.Item;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeliverDialog extends JDialog {
    private StorageManager storageManager;

    public DeliverDialog(JFrame parent, StorageManager storageManager) {
        super(parent, "Deliver Item", true);
        this.storageManager = storageManager;

        setLayout(new GridLayout(3, 2, 10, 10));

        JLabel codeLabel = new JLabel("Item Code:");
        JTextField codeField = new JTextField();

        JLabel qtyLabel = new JLabel("Quantity to Deliver:");
        JTextField qtyField = new JTextField();

        JButton deliverBtn = new JButton("Deliver");
        JButton cancelBtn = new JButton("Cancel");

        add(codeLabel);
        add(codeField);
        add(qtyLabel);
        add(qtyField);
        add(deliverBtn);
        add(cancelBtn);

        deliverBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String code = codeField.getText().trim();
                String qtyText = qtyField.getText().trim();

                if (code.isEmpty() || qtyText.isEmpty()) {
                    JOptionPane.showMessageDialog(DeliverDialog.this, "Please fill in all fields.");
                    return;
                }

                try {
                    int qty = Integer.parseInt(qtyText);
                    storageManager.removeQuantity(code, qty);
                    JOptionPane.showMessageDialog(DeliverDialog.this, "Delivered successfully!");
                    dispose();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(DeliverDialog.this, "Quantity must be a number.");
                }
            }
        });

        cancelBtn.addActionListener(e -> dispose());

        setSize(350, 180);
        setLocationRelativeTo(parent);
    }
}
