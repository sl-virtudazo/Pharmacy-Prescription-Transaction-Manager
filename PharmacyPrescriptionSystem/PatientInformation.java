package PharmacyPrescriptionSystem;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PatientInformation extends JFrame {
    private final JTextField nameField, ageField, searchField, algoRuntimeField;
    private final JComboBox<String> genderCombo, doctorCombo;
    private final DefaultTableModel tableModel;
    private List<Medicine> currentMedicines;

    // Changed to store multiple medicines with quantities
    private final Map<Medicine, Integer> selectedMedicinesWithQuantities;

    // Autocomplete components
    private JList<String> suggestionList;
    private JWindow suggestionWindow;
    private javax.swing.Timer debounceTimer;

    // Instance of PharmacyAlgorithm (non-static)
    private final PharmacyAlgorithm pharmacy;

    public PatientInformation() {
        // Initialize pharmacy algorithm
        pharmacy = new PharmacyAlgorithm();
        selectedMedicinesWithQuantities = new HashMap<>();

        setTitle("Pharmacy Prescription Transaction Manager");
        setSize(950, 445);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setUndecorated(true);
        setLayout(new BorderLayout());

        // Main container with border and background
        JPanel mainContainer = new JPanel(new BorderLayout());
        mainContainer.setBackground(new Color(203, 226, 250));
        mainContainer.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        // Custom title bar
        JPanel titleBar = new JPanel(new BorderLayout());
        titleBar.setBackground(new Color(0xD25D5D));
        titleBar.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
        titleBar.setPreferredSize(new Dimension(960, 30));

        // Title text
        JLabel titleLabel = new JLabel("Pharmacy Prescription Transaction Manager");
        titleLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
        titleLabel.setForeground(Color.BLACK);

        // Combine logo + title in a small left-aligned panel
        JPanel titleLeftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 5));
        titleLeftPanel.setBackground(new Color(0xD25D5D));
        titleLeftPanel.add(titleLabel);

        // Add to the main title bar
        titleBar.add(titleLeftPanel, BorderLayout.WEST);

        JButton closeBtn = new JButton("x");
        closeBtn.setFont(new Font("Tahoma", Font.BOLD, 15));
        closeBtn.setFocusPainted(false);
        closeBtn.setBorderPainted(false);
        closeBtn.setContentAreaFilled(false);
        closeBtn.addActionListener(e -> System.exit(0));
        titleBar.add(closeBtn, BorderLayout.EAST);

        JButton minimizeBtn = new JButton("—");
        minimizeBtn.setFont(new Font("Tahoma", Font.BOLD, 15));
        minimizeBtn.setFocusPainted(false);
        minimizeBtn.setBorderPainted(false);
        minimizeBtn.setContentAreaFilled(false);
        minimizeBtn.addActionListener(e -> setState(JFrame.ICONIFIED));

        JPanel windowButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        windowButtons.setBackground(new Color(0xD25D5D));
        windowButtons.add(minimizeBtn);
        windowButtons.add(closeBtn);

        titleBar.add(windowButtons, BorderLayout.EAST);

        mainContainer.add(titleBar, BorderLayout.NORTH);

        // Content panel
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(new Color(203, 226, 250));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        // Top right - Algo Runtime
        JPanel topRightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        topRightPanel.setBackground(new Color(203, 226, 250));
        JLabel algoLabel = new JLabel("Runtime:");
        algoLabel.setFont(new Font("SanSerif", Font.BOLD, 12));
        algoRuntimeField = new JTextField(10);
        algoRuntimeField.setEditable(false);
        algoRuntimeField.setPreferredSize(new Dimension(100, 25));
        topRightPanel.add(algoLabel);
        topRightPanel.add(algoRuntimeField);
        contentPanel.add(topRightPanel, BorderLayout.NORTH);

        // Center - Main content area
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(new Color(203, 226, 250));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(10, 10, 10, 10);

        // LEFT PANEL - Patient Information Form
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(new Color(203, 226, 250));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 20));

        JLabel formTitle = new JLabel("Patient Information Form:");
        formTitle.setFont(new Font("Tahoma", Font.BOLD, 18));
        formTitle.setAlignmentX(Component.LEFT_ALIGNMENT);
        leftPanel.add(formTitle);
        leftPanel.add(Box.createVerticalStrut(20));

        // Full Name
        JPanel namePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        namePanel.setBackground(new Color(203, 226, 250));
        JLabel nameLabel = new JLabel("Full Name:");
        nameLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
        nameLabel.setPreferredSize(new Dimension(100, 25));
        nameField = new JTextField();
        nameField.setPreferredSize(new Dimension(200, 25));
        namePanel.add(nameLabel);
        namePanel.add(nameField);
        namePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        leftPanel.add(namePanel);
        leftPanel.add(Box.createVerticalStrut(10));

        // Age
        JPanel agePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        agePanel.setBackground(new Color(203, 226, 250));
        JLabel ageLabel = new JLabel("Age:");
        ageLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
        ageLabel.setPreferredSize(new Dimension(100, 25));
        ageField = new JTextField();
        ageField.setPreferredSize(new Dimension(200, 25));
        agePanel.add(ageLabel);
        agePanel.add(ageField);
        agePanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        leftPanel.add(agePanel);
        leftPanel.add(Box.createVerticalStrut(10));

        // Gender
        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        genderPanel.setBackground(new Color(203, 226, 250));
        JLabel genderLabel = new JLabel("Gender:");
        genderLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
        genderLabel.setPreferredSize(new Dimension(100, 25));
        genderCombo = new JComboBox<>(new String[]{"Select", "Male", "Female", "Other"});
        genderCombo.setPreferredSize(new Dimension(200, 25));
        genderPanel.add(genderLabel);
        genderPanel.add(genderCombo);
        genderPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        leftPanel.add(genderPanel);
        leftPanel.add(Box.createVerticalStrut(10));

        // Doctor
        JPanel doctorPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        doctorPanel.setBackground(new Color(203, 226, 250));
        JLabel doctorLabel = new JLabel("Doctor:");
        doctorLabel.setFont(new Font("Tahoma", Font.PLAIN, 12));
        doctorLabel.setPreferredSize(new Dimension(100, 25));
        doctorCombo = new JComboBox<>(new String[]{"Select","Dr. Lyka Jane Cabillan",
                "Dr. Jasmine Cadelina", "Dr. Samantha Virtudaazo", "Dr. Jester Oton", "Dr. Red Buenafe",
                "Dr. Juan Cruz", "Dr. Ana Reyes", "Dr. Carlos Domingo", "Dr. Sofia Lim","Dr. Miguel Fernandez"});
        doctorCombo.setPreferredSize(new Dimension(200, 25));
        doctorPanel.add(doctorLabel);
        doctorPanel.add(doctorCombo);
        doctorPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        leftPanel.add(doctorPanel);

        // RIGHT PANEL - Medical Condition
        JPanel rightPanel = new JPanel(new BorderLayout(0, 15));
        rightPanel.setBackground(new Color(203, 226, 250));

        // Top section
        JPanel rightTopPanel = new JPanel();
        rightTopPanel.setLayout(new BoxLayout(rightTopPanel, BoxLayout.Y_AXIS));
        rightTopPanel.setBackground(new Color(203, 226, 250));

        JLabel conditionLabel = new JLabel("Medical Condition:");
        conditionLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
        conditionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        rightTopPanel.add(conditionLabel);
        rightTopPanel.add(Box.createVerticalStrut(8));

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 5, 0));
        searchPanel.setBackground(new Color(203, 226, 250));
        searchField = new JTextField();
        searchField.setPreferredSize(new Dimension(180, 25));
        searchField.setText("Search");
        searchField.setForeground(Color.GRAY);
        searchField.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent e) {
                if (searchField.getText().equals("Search")) {
                    searchField.setText("");
                    searchField.setForeground(Color.BLACK);
                }
            }
            public void focusLost(java.awt.event.FocusEvent e) {
                if (searchField.getText().isEmpty()) {
                    searchField.setText("Search");
                    searchField.setForeground(Color.GRAY);
                }
            }
        });
        JButton searchButton = new JButton("Search");
        searchButton.setPreferredSize(new Dimension(80, 25));
        searchButton.setFont(new Font("SansSerif", Font.BOLD, 13));
        searchButton.setBackground(Color.WHITE);
        searchButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        searchButton.setFocusPainted(false);

        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        searchPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        rightTopPanel.add(searchPanel);

        rightPanel.add(rightTopPanel, BorderLayout.NORTH);

        // Initialize autocomplete feature
        setupAutocomplete(searchButton);

        // Table
        String[] columns = {"Select", "Generic Name", "Brand Name", "Stock", "Expiration Date", "Manufacturer"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public Class<?> getColumnClass(int column) {
                return column == 0 ? Boolean.class : String.class;
            }
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 0;
            }
        };
        JTable medicineTable = new JTable(tableModel);
        medicineTable.setRowHeight(22);
        medicineTable.setBackground(Color.WHITE);
        medicineTable.getTableHeader().setBackground(new Color(220, 220, 220));
        medicineTable.getTableHeader().setFont(new Font("SansSerif", Font.PLAIN, 11));
        medicineTable.setFont(new Font("SansSerif", Font.PLAIN, 10));
        medicineTable.setShowGrid(true);
        medicineTable.setGridColor(Color.LIGHT_GRAY);

        JScrollPane scrollPane = new JScrollPane(medicineTable);
        scrollPane.setPreferredSize(new Dimension(550, 200));
        rightPanel.add(scrollPane, BorderLayout.CENTER);

        // Add panels to center
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.35;
        gbc.weighty = 1;
        centerPanel.add(leftPanel, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.65;
        centerPanel.add(rightPanel, gbc);

        contentPanel.add(centerPanel, BorderLayout.CENTER);

        // Bottom right - Continue button
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        bottomPanel.setBackground(new Color(203, 226, 250));
        JButton continueButton = new JButton("Continue");
        continueButton.setPreferredSize(new Dimension(90, 30));
        continueButton.setFont(new Font("SansSerif", Font.BOLD, 13));
        continueButton.setBackground(Color.WHITE);
        continueButton.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        continueButton.setFocusPainted(false);
        bottomPanel.add(continueButton);
        contentPanel.add(bottomPanel, BorderLayout.SOUTH);

        mainContainer.add(contentPanel, BorderLayout.CENTER);
        add(mainContainer);

        // SEARCH BUTTON ACTION
        searchButton.addActionListener(e -> {
            String condition = searchField.getText().trim();
            if (condition.isEmpty() || condition.equals("Search")) {
                JOptionPane.showMessageDialog(this, "Please enter a medical condition");
                return;
            }
            long startTime = System.nanoTime();
            currentMedicines = pharmacy.findMedicineByCondition(condition);
            long endTime = System.nanoTime();

            double runtime = (endTime - startTime) / 1_000_000.0;
            algoRuntimeField.setText(String.format("%.4f ms", runtime));
            System.out.println("\n--- PERFORMANCE REPORT ---\n");
            System.out.println("Algorithm: HashMap with Hashing");
            System.out.println("Time Complexity:");
            System.out.println("\tBest Case: O(1)");
            System.out.println("\tAverage Case: O(1)");
            System.out.println("\tWorst Case: O(n)");
            System.out.println("Runtime Search Complexity: " + String.format("%.4f ms", runtime));

            // Hide suggestions after search
            suggestionWindow.setVisible(false);

            if (!currentMedicines.isEmpty()) {
                tableModel.setRowCount(0);
                for (Medicine med : currentMedicines) {
                    tableModel.addRow(new Object[]{
                            false,
                            med.getGenericName(),
                            med.getBrandName(),
                            String.valueOf(med.getStock()),
                            med.getExpirationDate(),
                            med.getManufacturer()
                    });
                }
            } else {
                JOptionPane.showMessageDialog(this,
                        "No medicines found for condition: " + condition,
                        "Search result",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        });
        // CONTINUE BUTTON ACTION - Updated for multiple selections
        continueButton.addActionListener(e -> handleMultipleSelection());

        setLocationRelativeTo(null);
        setVisible(true);
    }
    // AUTOCOMPLETE SETUP
    private void setupAutocomplete(JButton searchButton) {
        suggestionList = new JList<>();
        suggestionList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        suggestionList.setFont(new Font("SansSerif", Font.PLAIN, 12));

        JScrollPane suggestionScrollPane = new JScrollPane(suggestionList);
        suggestionWindow = new JWindow(this);
        suggestionWindow.add(suggestionScrollPane);

        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateSuggestions();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                updateSuggestions();
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                updateSuggestions();
            }
        });
        suggestionList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 1) {
                    String selected = suggestionList.getSelectedValue();
                    if (selected != null) {
                        searchField.setText(selected);
                        suggestionWindow.setVisible(false);
                        searchButton.doClick(); // Auto-search selected condition
                    }
                }
            }
        });
        searchField.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER && suggestionList.getSelectedValue() != null) {
                    searchField.setText(suggestionList.getSelectedValue());
                    suggestionWindow.setVisible(false);
                    searchButton.doClick();
                } else if (e.getKeyCode() == KeyEvent.VK_DOWN && suggestionWindow.isVisible()) {
                    suggestionList.requestFocus();
                    suggestionList.setSelectedIndex(0);
                }
            }
        });
        // Add key listener to a suggestion list for navigation
        suggestionList.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    String selected = suggestionList.getSelectedValue();
                    if (selected != null) {
                        searchField.setText(selected);
                        suggestionWindow.setVisible(false);
                        searchField.requestFocus();
                        searchButton.doClick();
                    }
                }
            }
        });
    }
    private void updateSuggestions() {
        String input = searchField.getText().trim();
        if (input.isEmpty() || input.equals("Search") || searchField.getForeground().equals(Color.GRAY)) {
            suggestionWindow.setVisible(false);
            return;
        }
        if (debounceTimer != null && debounceTimer.isRunning()) {
            debounceTimer.restart();
            return;
        }
        debounceTimer = new javax.swing.Timer(150, evt -> {
            List<String> allConditions = pharmacy.getSortedConditions();
            List<String> filtered = new ArrayList<>();
            for (String condition : allConditions) {
                if (condition.toLowerCase().startsWith(input.toLowerCase())) {
                    filtered.add(condition);
                }
            }
            if (filtered.isEmpty()) {
                suggestionWindow.setVisible(false);
            } else {
                suggestionList.setListData(filtered.toArray(new String[0]));
                Point location = searchField.getLocationOnScreen();
                suggestionWindow.setLocation(location.x, location.y + searchField.getHeight());
                suggestionWindow.setSize(searchField.getWidth(), Math.min(150, filtered.size() * 22));
                suggestionWindow.setVisible(true);
            }
        });
        debounceTimer.setRepeats(false);
        debounceTimer.start();
    }
    // HANDLE MULTIPLE MEDICINE SELECTION
    private void handleMultipleSelection() {
        // Validate patient information
        if (nameField.getText().trim().isEmpty() || ageField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill-in all patient information.");
            return;
        }
        if (genderCombo.getSelectedIndex() == 0 || doctorCombo.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(this, "Please select gender and doctor.");
            return;
        }
        // Get all selected rows
        List<Integer> selectedRows = new ArrayList<>();
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            if ((Boolean) tableModel.getValueAt(i, 0)) {
                selectedRows.add(i);
            }
        }
        if (selectedRows.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select at least one medicine by checking the box.");
            return;
        }
        // Clear previous selections
        selectedMedicinesWithQuantities.clear();

        // Process each selected medicine
        boolean allValid = true;
        for (int row : selectedRows) {
            Medicine medicine = currentMedicines.get(row);

            String quantityStr = JOptionPane.showInputDialog(this,
                    "Enter quantity for: " + medicine.getGenericName() + " (" + medicine.getBrandName() + ")", "Quantity Entry", JOptionPane.QUESTION_MESSAGE);
            if (quantityStr == null) {
                allValid = false;
                break; // User cancelled
            }
            try {
                int quantity = Integer.parseInt(quantityStr);
                if (quantity <= 0) {
                    JOptionPane.showMessageDialog(this, "Quantity must be greater than 0 for " + medicine.getGenericName());
                    allValid = false;
                    break;
                }
                if (quantity > medicine.getStock()) {
                    JOptionPane.showMessageDialog(this, "Insufficient stock for " + medicine.getGenericName() + "!\nRequested: " + quantity + "\nAvailable: " + medicine.getStock(),
                            "Stock error!", JOptionPane.ERROR_MESSAGE);
                    allValid = false;
                    break;
                }
                // Update stock
                int newStock = medicine.getStock() - quantity;
                medicine.setStock(newStock);
                tableModel.setValueAt(String.valueOf(newStock), row, 3);

                // Store medicine with quantity
                selectedMedicinesWithQuantities.put(medicine, quantity);

            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid quantity entered for " + medicine.getGenericName());
                allValid = false;
                break;
            }
        }
        if (allValid) {
            // Get patient information
            String patientName = nameField.getText();
            String patientAge = ageField.getText();
            String patientGender = (String) genderCombo.getSelectedItem();
            String patientDoctor = (String) doctorCombo.getSelectedItem();

            // Open confirmation with multiple medicines
            new Confirmation(patientName, patientAge, patientGender, patientDoctor, selectedMedicinesWithQuantities);
            dispose();
        }
    }
}