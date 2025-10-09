package PharmacyPrescriptionSystem;

import javax.swing.*;
import java.awt.*;
import java.util.Map;

public class Confirmation extends JFrame {
    public Confirmation(String name, String age, String gender, String doctor, Map<Medicine, Integer> medicinesWithQuantities) {

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("PATIENT INFORMATION");
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        panel.add(titleLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;
        panel.add(new JLabel("Patient Name:"), gbc);
        gbc.gridx = 1;
        panel.add(new JLabel(name), gbc);

        gbc.gridx = 0; gbc.gridy++;
        panel.add(new JLabel("Age:"), gbc);
        gbc.gridx = 1;
        panel.add(new JLabel(age), gbc);

        gbc.gridx = 0; gbc.gridy++;
        panel.add(new JLabel("Gender:"), gbc);
        gbc.gridx = 1;
        panel.add(new JLabel(gender), gbc);

        gbc.gridx = 0; gbc.gridy++;
        panel.add(new JLabel("Doctor:"), gbc);
        gbc.gridx = 1;
        panel.add(new JLabel(doctor), gbc);

        gbc.gridx = 0; gbc.gridy++;
        gbc.gridwidth = 2;
        panel.add(new JSeparator(), gbc);

        gbc.gridy++;
        JLabel medLabel = new JLabel("SELECTED MEDICATIONS (" + medicinesWithQuantities.size() + ")");
        medLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
        panel.add(medLabel, gbc);

        // Display each medicine with its details
        int medicineIndex = 1;
        for (Map.Entry<Medicine, Integer> entry : medicinesWithQuantities.entrySet()) {
            Medicine medicine = entry.getKey();
            int quantity = entry.getValue();

            gbc.gridy++;
            gbc.gridwidth = 2;
            JLabel medNumberLabel = new JLabel("Medicine " + medicineIndex + ":");
            medNumberLabel.setFont(new Font("Tahoma", Font.BOLD, 12));
            panel.add(medNumberLabel, gbc);

            gbc.gridwidth = 1;
            gbc.gridx = 0; gbc.gridy++;
            panel.add(new JLabel("  Generic Name:"), gbc);
            gbc.gridx = 1;
            panel.add(new JLabel(medicine.getGenericName()), gbc);

            gbc.gridx = 0; gbc.gridy++;
            panel.add(new JLabel("  Brand Name:"), gbc);
            gbc.gridx = 1;
            panel.add(new JLabel(medicine.getBrandName()), gbc);

            gbc.gridx = 0; gbc.gridy++;
            panel.add(new JLabel("  Quantity:"), gbc);
            gbc.gridx = 1;
            panel.add(new JLabel(String.valueOf(quantity)), gbc);

            gbc.gridx = 0; gbc.gridy++;
            panel.add(new JLabel("  Updated Stock:"), gbc);
            gbc.gridx = 1;
            panel.add(new JLabel(String.valueOf(medicine.getStock())), gbc);

            // Add spacing between medicines
            if (medicineIndex < medicinesWithQuantities.size()) {
                gbc.gridx = 0; gbc.gridy++;
                gbc.gridwidth = 2;
                panel.add(Box.createVerticalStrut(10), gbc);
            }
            medicineIndex++;
        }
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane, BorderLayout.CENTER);

        JButton proceedButton = new JButton("Generate Receipt");
        proceedButton.addActionListener(e -> {
            new PrescriptionReceipt(name, age, gender, doctor, medicinesWithQuantities);
            dispose();
        });
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(proceedButton);
        add(buttonPanel, BorderLayout.SOUTH);

        setTitle("Confirmation");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        pack();
        setSize(370,470);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}