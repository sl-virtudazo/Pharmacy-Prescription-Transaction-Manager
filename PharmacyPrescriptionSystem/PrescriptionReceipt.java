package PharmacyPrescriptionSystem;

import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Random;

public class PrescriptionReceipt extends JFrame {
    private String receiptContent;
    private String transactionId;

    public PrescriptionReceipt(String name, String age, String gender, String doctor, Map<Medicine, Integer> medicinesWithQuantities) {

        JTextArea receiptArea = new JTextArea();
        receiptArea.setEditable(false);
        receiptArea.setFont(new Font("Arial", Font.PLAIN, 12));
        receiptArea.setMargin(new Insets(10, 10, 10, 10));

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        transactionId = "RX" + now.format(DateTimeFormatter.ofPattern("yyyyMMdd")) +
                "-" + String.format("%03d", new Random().nextInt(1000));

        double grandTotal = 0.0;

        StringBuilder receipt = new StringBuilder();
        receipt.append("=========================================================\n");
        receipt.append("           PHARMACY PRESCRIPTION RECEIPT\n");
        receipt.append("=========================================================\n");
        receipt.append("Transaction ID: ").append(transactionId).append("\n");
        receipt.append("Date & Time: ").append(now.format(formatter)).append("\n\n");
        receipt.append("═════════════════════════════════════════════════════════\n");
        receipt.append("                  PATIENT INFORMATION\n");
        receipt.append("═════════════════════════════════════════════════════════\n");
        receipt.append("Name: ").append(name).append("\n");
        receipt.append("Age: ").append(age).append("\n");
        receipt.append("Gender: ").append(gender).append("\n");
        receipt.append("Prescribing Doctor: ").append(doctor).append("\n\n");
        receipt.append("═════════════════════════════════════════════════════════\n");
        receipt.append("                  DISPENSED MEDICATIONS\n");
        receipt.append("═════════════════════════════════════════════════════════\n\n");

        int medicineNumber = 1;
        for (Map.Entry<Medicine, Integer> entry : medicinesWithQuantities.entrySet()) {
            Medicine medicine = entry.getKey();
            int quantity = entry.getValue();
            double subtotal = medicine.getPrice() * quantity;
            grandTotal += subtotal;

            receipt.append("Medicine #").append(medicineNumber).append(":\n");
            receipt.append("---------------------------------------------------------\n");
            receipt.append("Generic Name: ").append(medicine.getGenericName()).append("\n");
            receipt.append("Brand Name: ").append(medicine.getBrandName()).append("\n");
            receipt.append("Manufacturer: ").append(medicine.getManufacturer()).append("\n");
            receipt.append("Expiration Date: ").append(medicine.getExpirationDate()).append("\n");
            receipt.append(String.format("Unit Price: ₱%.2f\n", medicine.getPrice()));
            receipt.append(String.format("Quantity: %d\n", quantity));
            receipt.append(String.format("Subtotal: ₱%.2f\n\n", subtotal));

            medicineNumber++;
        }

        receipt.append("═════════════════════════════════════════════════════════\n");
        receipt.append("                  BILLING SUMMARY\n");
        receipt.append("═════════════════════════════════════════════════════════\n");
        receipt.append(String.format("Total Items: %d\n", medicinesWithQuantities.size()));
        receipt.append(String.format("GRAND TOTAL: ₱%.2f\n\n", grandTotal));
        receipt.append("═════════════════════════════════════════════════════════\n");
        receipt.append("                PHARMACIST INFORMATION\n");
        receipt.append("═════════════════════════════════════════════════════════\n");
        receipt.append("Name: Pharmacist John Dela Cruz, RPh\n");
        receipt.append("License No: PRC-0123456\n");
        receipt.append("Workplace: MedCare Pharmacy\n");
        receipt.append("Contact: +63 917 123 4567\n");
        receipt.append("Email: pharmacist@medcare.ph\n\n");
        receipt.append("=========================================================\n");
        receipt.append("       Thank you for trusting CCE105 Pharmacy!\n");
        receipt.append("=========================================================\n");

        receiptContent = receipt.toString();
        receiptArea.setText(receiptContent);

        JScrollPane scrollPane = new JScrollPane(receiptArea);
        add(scrollPane, BorderLayout.CENTER);

        // Button Panel with Save and Close
        JPanel buttonPanel = new JPanel(new FlowLayout());

        JButton saveButton = new JButton("Save to File");
        saveButton.addActionListener(e -> saveReceiptToFile());
        buttonPanel.add(saveButton);

        JButton closeButton = new JButton("Close");
        closeButton.addActionListener(e -> System.exit(0));
        buttonPanel.add(closeButton);

        add(buttonPanel, BorderLayout.SOUTH);

        setTitle("Prescription Information");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(570,750);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void saveReceiptToFile() {
        String filename = "All_Receipts.txt";

        try (FileWriter writer = new FileWriter(filename, true)) { // true = append mode
            writer.write(receiptContent);
            writer.write("\n\n\n"); // Add spacing between receipts
            JOptionPane.showMessageDialog(this,
                    "Receipt saved successfully to:\n\n" + filename,
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this,
                    "Error saving receipt: " + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}