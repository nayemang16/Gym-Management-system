import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * GymGUI - A graphical user interface for managing gym members
 * This class handles the UI and operations for the Fitness Center Management System
 * 
 * @author (Nayem Raj Angdembay)
 * @version (May 14, 2025)
 */
/**
 * GymGUI - A graphical user interface for managing gym members.
 * This class handles the UI and operations for the Fitness Center Management System.
 * It allows users to add, activate, deactivate, mark attendance, and manage memberships.
 * 
 * @author Nayem Raj Angdembay
 * @version May 14, 2025
 */
public class GymGUI implements ActionListener {
    // Constants for UI components
    private static final int BUTTON_WIDTH = 200;
    private static final int BUTTON_HEIGHT = 35;
    private static final Color BUTTON_COLOR = new Color(205, 133, 63);
    private static final Color BACKGROUND_COLOR = new Color(255, 240, 215);
    private static final int TEXT_FIELD_WIDTH = 200;
    private static final int TEXT_FIELD_HEIGHT = 25;

    // Main components
    private JFrame frame; // Main window frame
    private JPanel currentPanel; // Current panel being displayed

    // Text fields for member details
    private JTextField txtID, txtName, txtLocation, txtPhone, txtEmail, txtTrainer, txtReferral, txtPaidAmount;
    private JTextField txtAttendanceID, txtActivateID, txtUpgradeID, txtDeactivateID, txtRevertID;
    private JTextField txtRegularPlanPrice, txtPremiumPlanCharge, txtDiscountAmount;

    // UI Components
    private JRadioButton Male, Female; // Gender selection
    private JComboBox<String> ComboDay, ComboMonth, ComboYear, ComboStartDay, ComboStartMonth, ComboStartYear, ComboPlan;

    // Buttons for actions
    private JButton btnAddMember, btnChangeMemberType, btnActivate, btnDeactivate, btnMarkAttendance, btncalcdiscount;
    private JButton btnClearForm, btnSaveToFile, btnDisplayAll, btnRevert, btnUpgrade, btnPayDue, btnReadFile;

    // User data
    private ButtonGroup genderGroup; // Group for gender radio buttons
    private ArrayList<GymMember> members = new ArrayList<>(); // List to store gym members - only one ArrayList as per requirement

    /**
     * Main method to start the application
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GymGUI().initializeRegularMemberUI());
    }

    /**
     * Initialize the UI for Regular Member management
     */
    private void initializeRegularMemberUI() {
        createBaseUI("Regular Member Management"); // Create base UI components
        setupCommonFields(); // Set up common fields for both regular and premium members
        setupRegularSpecificFields(); // Set up fields specific to regular members
        setupRegularButtons(); // Set up buttons for regular member management
        frame.setVisible(true);
    }

    /**
     * Initialize the UI for Premium Member management
     */
    private void initializePremiumMemberUI() {
        createBaseUI("Premium Member Management"); // Create base UI components
        setupCommonFields(); // Set up common fields for both regular and premium members
        setupPremiumSpecificFields(); // Set up fields specific to premium members
        setupPremiumButtons(); // Set up buttons for premium member management
        frame.setVisible(true);
    }

    /**
     * Create the base UI components
     * @param title The title of the window
     */
    private void createBaseUI(String title) {
        if (frame != null) {
            frame.dispose(); // Dispose of the current frame if it exists
        }

        frame = new JFrame(title);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(950, 750); // Slightly taller to accommodate new layout
        frame.setLocationRelativeTo(null); // Center the frame on screen

        currentPanel = new JPanel();
        currentPanel.setLayout(null);
        currentPanel.setBounds(10, 10, 920, 700);
        currentPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(),
            "Fitness Center Management System",
            TitledBorder.LEADING, TitledBorder.TOP, 
            new Font("Calibri", Font.BOLD, 18), new Color(70, 130, 180))); 
        currentPanel.setBackground(BACKGROUND_COLOR); // Cream color

        frame.setContentPane(currentPanel);
    }

    /**
     * Set up the fields common to both Regular and Premium member UIs
     */
    private void setupCommonFields() {
        // Left column - Member basic info
        addLabelAndField("ID:", 30, 40, txtID = new JTextField(), 170, 40, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);
        addLabelAndField("Name:", 30, 80, txtName = new JTextField(), 170, 80, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);
        addLabelAndField("Location:", 30, 120, txtLocation = new JTextField(), 170, 120, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);
        addLabelAndField("Phone:", 30, 160, txtPhone = new JTextField(), 170, 160, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);
        addLabelAndField("Email:", 30, 200, txtEmail = new JTextField(), 170, 200, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);
        addLabelAndField("Referral Source:", 30, 240, txtReferral = new JTextField(), 170, 240, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);

        // Right column - Additional info
        addLabelAndField("Gender:", 450, 40, null, 0, 0, 0, 0);
        Male = new JRadioButton("Male");
        Male.setBounds(550, 40, 80, 25);
        Male.setBackground(BACKGROUND_COLOR);
        Male.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1)); // Add border to radio button

        Female = new JRadioButton("Female");
        Female.setBounds(640, 40, 80, 25);
        Female.setBackground(BACKGROUND_COLOR);
        Female.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1)); // Add border to radio button

        genderGroup = new ButtonGroup();
        genderGroup.add(Male);
        genderGroup.add(Female);
        currentPanel.add(Male);
        currentPanel.add(Female);

        // Date of Birth - Right column
        addLabelAndField("DOB:", 450, 80, null, 0, 0, 0, 0);
        setupDateFields(550, 80);

        // Membership Start - Right column with CORRECTED SPACING
        JLabel lblMembershipStart = new JLabel("Membership Start:");
        lblMembershipStart.setBounds(450, 120, 120, 25);
        currentPanel.add(lblMembershipStart);

        // Fixed placement for start date fields
        ComboStartDay = new JComboBox<>();
        for (int i = 1; i <= 31; i++) ComboStartDay.addItem(String.format("%02d", i));
        ComboStartDay.setBounds(575, 120, 60, 25);
        ComboStartDay.setBackground(new Color(255, 255, 255)); // Set background color
        ComboStartDay.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1)); // Add border to combo box

        ComboStartMonth = new JComboBox<>(new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", 
                                                "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"});
        ComboStartMonth.setBounds(645, 120, 80, 25);
        ComboStartMonth.setBackground(new Color(255, 255, 255)); // Set background color
        ComboStartMonth.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1)); // Add border to combo box

        ComboStartYear = new JComboBox<>();
        for (int i = 2000; i <= 2035; i++) ComboStartYear.addItem(String.valueOf(i));
        ComboStartYear.setBounds(735, 120, 80, 25);
        ComboStartYear.setBackground(new Color(255, 255, 255)); // Set background color
        ComboStartYear.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1)); // Add border to combo box

        currentPanel.add(ComboStartDay);
        currentPanel.add(ComboStartMonth);
        currentPanel.add(ComboStartYear);

        // Initialize Trainer and Paid Amount fields without adding to UI
        // These will be properly added in the specific setup methods
        txtTrainer = new JTextField();
        txtPaidAmount = new JTextField();

        // Plan and pricing information - Panel
        JPanel pricingPanel = new JPanel();
        pricingPanel.setLayout(null);
        pricingPanel.setBounds(30, 280, 880, 80);
        pricingPanel.setBorder(BorderFactory.createTitledBorder("Plan & Pricing"));
        pricingPanel.setBackground(BACKGROUND_COLOR);

        // Plan pricing fields
        JLabel lblRegularPrice = new JLabel("Regular Plan Price:");
        lblRegularPrice.setBounds(20, 30, 130, 25);
        pricingPanel.add(lblRegularPrice);

        txtRegularPlanPrice = new JTextField("6500");
        txtRegularPlanPrice.setBounds(150, 30, 100, TEXT_FIELD_HEIGHT);
        txtRegularPlanPrice.setEditable(false);
        pricingPanel.add(txtRegularPlanPrice);

        JLabel lblPremiumPlan = new JLabel("Premium Plan:");
        lblPremiumPlan.setBounds(320, 30, 100, 25);
        pricingPanel.add(lblPremiumPlan);

        txtPremiumPlanCharge = new JTextField("50000");
        txtPremiumPlanCharge.setBounds(420, 30, 100, TEXT_FIELD_HEIGHT);
        txtPremiumPlanCharge.setEditable(false);
        pricingPanel.add(txtPremiumPlanCharge);

        JLabel lblDiscountAmount = new JLabel("Discount Amount:");
        lblDiscountAmount.setBounds(590, 30, 130, 25);
        pricingPanel.add(lblDiscountAmount);

        txtDiscountAmount = new JTextField("0");
        txtDiscountAmount.setBounds(720, 30, 100, TEXT_FIELD_HEIGHT);
        txtDiscountAmount.setEditable(false);
        pricingPanel.add(txtDiscountAmount);

        currentPanel.add(pricingPanel);

        // Operations panel
        JPanel operationsPanel = new JPanel();
        operationsPanel.setLayout(null);
        operationsPanel.setBounds(30, 370, 880, 130);
        operationsPanel.setBorder(BorderFactory.createTitledBorder("Member Operations"));
        operationsPanel.setBackground(BACKGROUND_COLOR);

        // Member operations fields
        addLabelAndField("Attendance (ID):", 20, 30, txtAttendanceID = new JTextField(), 150, 30, 180, TEXT_FIELD_HEIGHT, operationsPanel);
        addLabelAndField("Activate (ID):", 20, 70, txtActivateID = new JTextField(), 150, 70, 180, TEXT_FIELD_HEIGHT, operationsPanel);
        addLabelAndField("Deactivate (ID):", 20, 110, txtDeactivateID = new JTextField(), 150, 110, 180, TEXT_FIELD_HEIGHT, operationsPanel);

        // Initialize Revert to Regular field
        txtRevertID = new JTextField();

        currentPanel.add(operationsPanel);
    }

    /**
     * Add a label and text field to the panel
     */
    private void addLabelAndField(String labelText, int lblX, int lblY, JTextField field, 
                            int fieldX, int fieldY, int fieldW, int fieldH) {
        JLabel label = new JLabel(labelText);
        label.setBounds(lblX, lblY, 120, 25);
        currentPanel.add(label);

        if (field != null) {
            field.setBounds(fieldX, fieldY, fieldW, fieldH);
            currentPanel.add(field);
        }
    }

    /**
     * Add a label and text field to a specific panel
     */
    private void addLabelAndField(String labelText, int lblX, int lblY, JTextField field, 
                            int fieldX, int fieldY, int fieldW, int fieldH, JPanel panel) {
        JLabel label = new JLabel(labelText);
        label.setBounds(lblX, lblY, 120, 25);
        panel.add(label);

        if (field != null) {
            field.setBounds(fieldX, fieldY, fieldW, fieldH);
            panel.add(field);
        }
    }

    /**
     * Set up date of birth fields (day, month, year)
     * @param x Base X position
     * @param y Y position
     */
    private void setupDateFields(int x, int y) {
        ComboDay = new JComboBox<>();
        for (int i = 1; i <= 31; i++) ComboDay.addItem(String.format("%02d", i));
        ComboDay.setBounds(x, y, 60, 25);
        ComboDay.setBackground(new Color(255, 255, 255)); // Set background color
        ComboDay.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1)); // Add border to combo box

        ComboMonth = new JComboBox<>(new String[]{"Jan", "Feb", "Mar", "Apr", "May", "Jun", 
                                            "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"});
        ComboMonth.setBounds(x + 70, y, 80, 25);
        ComboMonth.setBackground(new Color(255, 255, 255)); // Set background color
        ComboMonth.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1)); // Add border to combo box

        ComboYear = new JComboBox<>();
        for (int i = 1990; i <= 2035; i++) ComboYear.addItem(String.valueOf(i));
        ComboYear.setBounds(x + 160, y, 80, 25);
        ComboYear.setBackground(new Color(255, 255, 255)); // Set background color
        ComboYear.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1)); // Add border to combo box

        currentPanel.add(ComboDay);
        currentPanel.add(ComboMonth);
        currentPanel.add(ComboYear);
    }

    /**
     * Set up fields specific to Regular Member UI
     */
    private void setupRegularSpecificFields() {
        // Right side of main panel - Add plan selection for regular members
        addLabelAndField("Plan:", 450, 240, null, 0, 0, 0, 0);
        ComboPlan = new JComboBox<>(new String[]{"Basic", "Standard", "Deluxe"});
        ComboPlan.setBounds(550, 240, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);
        ComboPlan.setBackground(new Color(255, 255, 255)); // Set background color
        ComboPlan.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1)); // Add border to combo box
        currentPanel.add(ComboPlan);

        // Operations panel - get reference to existing panel
        Component[] components = currentPanel.getComponents();
        JPanel operationsPanel = null;
        for (Component comp : components) {
            if (comp instanceof JPanel && ((JPanel)comp).getBorder() != null && 
                ((JPanel)comp).getBorder().toString().contains("Member Operations")) {
                operationsPanel = (JPanel)comp;
                break;
            }
        }

        if (operationsPanel != null) {
            // Right side operations for regular members
            addLabelAndField("Upgrade to Premium:", 400, 30, txtUpgradeID = new JTextField(), 530, 30, 180, TEXT_FIELD_HEIGHT, operationsPanel);

            // This field is common to both types but placed differently
            addLabelAndField("Revert to Regular (ID):", 400, 70, txtRevertID = new JTextField(), 530, 70, 180, TEXT_FIELD_HEIGHT, operationsPanel);
            txtRevertID.setEnabled(false); // Disable in Regular member view
        }
    }

    private void setupPremiumSpecificFields() {
        // Add premium-specific fields (trainer, paid amount)
        addLabelAndField("Trainer's Name:", 450, 200, txtTrainer, 550, 200, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);
        addLabelAndField("Paid Amount:", 450, 160, txtPaidAmount, 550, 160, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);

        // Set up premium plan selection
        addLabelAndField("Plan:", 450, 240, null, 0, 0, 0, 0);
        ComboPlan = new JComboBox<>(new String[]{"Premium"});
        ComboPlan.setBounds(550, 240, TEXT_FIELD_WIDTH, TEXT_FIELD_HEIGHT);
        ComboPlan.setEnabled(false);
        ComboPlan.setBackground(new Color(255, 255, 255)); // Set background color
        ComboPlan.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1)); // Add border to combo box
        currentPanel.add(ComboPlan);

        // Modify Member Operations panel
        JPanel operationsPanel = getMemberOperationsPanel();
        if (operationsPanel != null) {
            // Add Revert Member ID field to existing operations
            addLabelAndField("Revert Member (ID):", 400, 70, txtRevertID, 530, 70, 180, TEXT_FIELD_HEIGHT, operationsPanel);
            txtRevertID.setEnabled(true);
        }
    }

    private JPanel getMemberOperationsPanel() {
        for (Component comp : currentPanel.getComponents()) {
            if (comp instanceof JPanel && ((JPanel)comp).getBorder() != null && 
                ((TitledBorder)((JPanel)comp).getBorder()).getTitle().contains("Member Operations")) {
                return (JPanel)comp;
            }
        }
        return null;
    }

    private JPanel getOperationsPanel() {
        for (Component comp : currentPanel.getComponents()) {
            if (comp instanceof JPanel && ((JPanel)comp).getBorder() != null && 
                ((JPanel)comp).getBorder().toString().contains("Member Operations")) {
                return (JPanel)comp;
            }
        }
        return null;
    }

    private void clearRightSideOperations(JPanel operationsPanel) {
        Component[] opsComponents = operationsPanel.getComponents();
        for (Component opComp : opsComponents) {
            if (opComp.getX() > 400) {
                operationsPanel.remove(opComp);
            }
        }
    }

    /**
     * Set up buttons for Regular Member UI
     */
    private void setupRegularButtons() {
        // Reset premium-specific buttons
        btnRevert = null;
        btncalcdiscount = null;
        btnPayDue = null;

        // Create a button panel at the bottom
        JPanel buttonPanel = new JPanel(new GridLayout(2, 4, 15, 10));
        buttonPanel.setBounds(30, 540, 880, 95);
        buttonPanel.setBackground(BACKGROUND_COLOR);

        // Create all buttons
        btnAddMember = createButton("Add Regular Member", 0, 0);
        btnChangeMemberType = createButton("Change To Premium", 0, 0);
        btnActivate = createButton("Activate Membership", 0, 0);
        btnDeactivate = createButton("Deactivate Membership", 0, 0);
        btnMarkAttendance = createButton("Mark Attendance", 0, 0);
        btnUpgrade = createButton("Upgrade Plan", 0, 0);
        btnClearForm = createButton("Clear Form", 0, 0);
        btnSaveToFile = createButton("Save to File", 0, 0);
        btnDisplayAll = createButton("Display Members", 0, 0);
        btnReadFile = createButton("Read From File", 0, 0);

        // Add buttons to the grid layout
        buttonPanel.add(btnAddMember);
        buttonPanel.add(btnChangeMemberType);
        buttonPanel.add(btnActivate);
        buttonPanel.add(btnMarkAttendance);
        buttonPanel.add(btnDeactivate);
        buttonPanel.add(btnUpgrade);
        buttonPanel.add(btnSaveToFile);
        buttonPanel.add(btnReadFile);

        // Additional buttons panel
        JPanel additionalButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        additionalButtonPanel.setBounds(30, 645, 880, 45);
        additionalButtonPanel.setBackground(BACKGROUND_COLOR);
        additionalButtonPanel.add(btnClearForm);
        additionalButtonPanel.add(btnDisplayAll);

        currentPanel.add(buttonPanel);
        currentPanel.add(additionalButtonPanel);
    }

    /**
     * Set up buttons for Premium Member UI
     */
    private void setupPremiumButtons() {
        // Reset regular-specific buttons
        btnUpgrade = null;

        // Create a button panel at the bottom
        JPanel buttonPanel = new JPanel(new GridLayout(2, 4, 15, 10));
        buttonPanel.setBounds(30, 540, 880, 95);
        buttonPanel.setBackground(BACKGROUND_COLOR);

        // Create all buttons
        btnAddMember = createButton("Add Premium Member", 0, 0);
        btnChangeMemberType = createButton("Change To Regular", 0, 0);
        btnActivate = createButton("Activate Membership", 0, 0);
        btncalcdiscount = createButton("Calculate Discount", 0, 0);
        btnDeactivate = createButton("Deactivate Membership", 0, 0);
        btnMarkAttendance = createButton("Mark Attendance", 0, 0);
        btnRevert = createButton("Revert to Regular", 0, 0);
        btnPayDue = createButton("Pay Due Amount", 0, 0);
        btnClearForm = createButton("Clear Form", 0, 0);
        btnSaveToFile = createButton("Save to File", 0, 0);
        btnDisplayAll = createButton("Display Members", 0, 0);
        btnReadFile = createButton("Read From File", 0, 0);

        // Add buttons to the grid layout
        buttonPanel.add(btnAddMember);
        buttonPanel.add(btnChangeMemberType);
        buttonPanel.add(btnActivate);
        buttonPanel.add(btncalcdiscount);
        buttonPanel.add(btnDeactivate);
        buttonPanel.add(btnMarkAttendance);
        buttonPanel.add(btnRevert);
        buttonPanel.add(btnPayDue);

        // Additional buttons panel
        JPanel additionalButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 5));
        additionalButtonPanel.setBounds(30, 645, 880, 45);
        additionalButtonPanel.setBackground(BACKGROUND_COLOR);
        additionalButtonPanel.add(btnClearForm);
        additionalButtonPanel.add(btnSaveToFile);
        additionalButtonPanel.add(btnDisplayAll);
        additionalButtonPanel.add(btnReadFile);

        currentPanel.add(buttonPanel);
        currentPanel.add(additionalButtonPanel);
    }

    /**
     * Helper method to create a standard button
     * @param text Button text
     * @param x X position (not used in grid layout)
     * @param y Y position (not used in grid layout)
     * @return Configured JButton
     */
    private JButton createButton(String text, int x, int y) {
        JButton button = new JButton(text);
        button.addActionListener(this);
        button.setBackground(BUTTON_COLOR);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
        return button;
    }

    /**
     * Remove existing buttons from the panel
     */
    private void removeExistingButtons() {
        Component[] components = currentPanel.getComponents();
        for (int i = components.length - 1; i >= 0; i--) {
            Component component = components[i];
            if (component instanceof JButton) {
                currentPanel.remove(component);
            } else if (component instanceof JPanel && 
                      (((JPanel)component).getLayout() instanceof GridLayout ||
                       ((JPanel)component).getLayout() instanceof FlowLayout)) {
                currentPanel.remove(component);
            }
        }
        // Force repaint
        currentPanel.revalidate();
        currentPanel.repaint();
    }

    /**
     * Handle button click events
     * @param e The ActionEvent that triggered this method
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnChangeMemberType) {
            if (btnChangeMemberType.getText().contains("Premium")) {
                initializePremiumMemberUI(); // Switch to premium member UI
            } else {
                initializeRegularMemberUI(); // Switch to regular member UI
            }
        } else if (e.getSource() == btnAddMember) {
            if (btnAddMember.getText().contains("Premium")) {
                addPremiumMember(); // Add a premium member
            } else {
                addRegularMember(); // Add a regular member
            }
        } else if (e.getSource() == btnActivate) {
            activateMembership(); // Activate a membership
        } else if (e.getSource() == btnDeactivate) {
            deactivateMembership(); // Deactivate a membership
        } else if (e.getSource() == btnMarkAttendance) {
            markAttendance(); // Mark attendance for a member
        } else if (e.getSource() == btnRevert) {
            revertToRegular(); // Revert a premium member to regular
        } else if (e.getSource() == btnUpgrade) {
            upgradePlan(); // Upgrade a regular member's plan
        } else if (e.getSource() == btnClearForm) {
            clearForm(); // Clear the form fields
        } else if (e.getSource() == btnSaveToFile) {
            saveToFile(); // Save members to a file
        } else if (e.getSource() == btnDisplayAll) {
            displayAllMembers(); // Display all members
        } else if (e.getSource() == btncalcdiscount) {
            calculateDiscount(); // Calculate discount for premium member
        } else if (e.getSource() == btnPayDue) {
            payDueAmount(); // Pay due amount for premium member
        } else if (e.getSource() == btnReadFile) {
            readFromFile(); // Read members from a file
        }
    }

    /**
     * Add a regular member to the system
     */
    private void addRegularMember() {
        try {
            // Validate input fields
            if (!validateInputFields()) {
                return;
            }

            int id = Integer.parseInt(txtID.getText());
            String name = txtName.getText();
            String location = txtLocation.getText();
            String phone = txtPhone.getText();
            String email = txtEmail.getText();
            String gender = Male.isSelected() ? "Male" : "Female";
            String dob = String.format("%s/%s/%s", 
                ComboDay.getSelectedItem(), 
                ComboMonth.getSelectedItem(), 
                ComboYear.getSelectedItem());
            String startDate = String.format("%s/%s/%s", 
                ComboStartDay.getSelectedItem(), 
                ComboStartMonth.getSelectedItem(), 
                ComboStartYear.getSelectedItem());
            String referral = txtReferral.getText();

            if (isDuplicateId(id)) {
                JOptionPane.showMessageDialog(frame, "ID already exists. Each member must have a unique ID."); 
                return;
            }

            RegularMember newMember = new RegularMember(id, name, location, phone, email,
                                                    gender, dob, startDate, referral);
            members.add(newMember); // Add member to the list
            JOptionPane.showMessageDialog(frame, "Regular Member added successfully.");
            clearForm(); // Clear the form after adding
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Invalid ID format. Please enter a positive number.");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
        }
    }

    /**
     * Add a premium member to the system
     */
    private void addPremiumMember() {
        try {
            // Validate input fields
            if (!validateInputFields()) {
                return;
            }

            if (txtTrainer.getText().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please enter trainer's name for Premium Member.");
                return;
            }

            int id = Integer.parseInt(txtID.getText());
            String name = txtName.getText();
            String location = txtLocation.getText();
            String phone = txtPhone.getText();
            String email = txtEmail.getText();
            String gender = Male.isSelected() ? "Male" : "Female";
            String dob = String.format("%s/%s/%s", 
                ComboDay.getSelectedItem(), 
                ComboMonth.getSelectedItem(), 
                ComboYear.getSelectedItem());
            String startDate = String.format("%s/%s/%s", 
                ComboStartDay.getSelectedItem(), 
                ComboStartMonth.getSelectedItem(), 
                ComboStartYear.getSelectedItem());
            String trainer = txtTrainer.getText();
            double paidAmount = 0.0;

            if (!txtPaidAmount.getText().isEmpty()) {
                paidAmount = Double.parseDouble(txtPaidAmount.getText());
                if (paidAmount < 0) {
                    JOptionPane.showMessageDialog(frame, "Paid amount cannot be negative.");
                    return;
                }
            }

            if (isDuplicateId(id)) {
                JOptionPane.showMessageDialog(frame, "ID already exists. Each member must have a unique ID."); 
                return;
            }

            PremiumMember newMember = new PremiumMember(id, name, location, phone, email, 
                                                gender, dob, startDate, trainer);

            // If paid amount is provided, process payment
            if (paidAmount > 0) {
                newMember.payDueAmount(paidAmount);
            }

            members.add(newMember); // Add member to the list
            JOptionPane.showMessageDialog(frame, "Premium Member added successfully.");
            clearForm(); // Clear the form after adding
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "Invalid input format. Please check your data.");
        } catch (Exception ex) {
           JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
        }
    }

    /**
     * Validate common input fields
     * @return true if all fields are valid, false otherwise
     */
    private boolean validateInputFields() {
        if (txtID.getText().isEmpty() || txtName.getText().isEmpty() || 
            txtLocation.getText().isEmpty() || txtPhone.getText().isEmpty() || 
            txtEmail.getText().isEmpty() || (!Male.isSelected() && !Female.isSelected())) {
            JOptionPane.showMessageDialog(frame, "Please fill all required fields.");
            return false;
        }

        // Validate ID (must be a positive number)
        try {
            int id = Integer.parseInt(txtID.getText());
            if (id <= 0) {
                JOptionPane.showMessageDialog(frame, "ID must be a positive number.");
                return false;
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(frame, "ID must be a valid number.");
            return false;
        }

        // Validate phone (digits only)
        if (!txtPhone.getText().matches("\\d+")) {
            JOptionPane.showMessageDialog(frame, "Phone number should contain only digits.");
            return false;
        }

        // Validate email format (optional)
        if (!isValidEmail(txtEmail.getText())) {
            JOptionPane.showMessageDialog(frame, "Please enter a valid email address.");
            return false;
        }

        return true;
    }

    /**
     * Validate email format using a simple regex pattern
     * @param email The email to validate
     * @return true if the email is valid, false otherwise
     */
    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
       return email.matches(emailRegex);
   }

   /**
    * Check if a member ID already exists
    * @param id The ID to check
    * @return true if the ID exists, false otherwise
    */
   private boolean isDuplicateId(int id) {
       return members.stream().anyMatch(m -> m.getid() == id); // Check if the ID already exists
   }

   /**
    * Activate a member's membership
    */
   private void activateMembership() {
       try {
           if (txtActivateID.getText().isEmpty()){
               JOptionPane.showMessageDialog(frame, "Please enter a member ID.");
               return;
           }

           int id = validateIdInput(txtActivateID.getText());
           if (id == -1) return;

           GymMember member = findMemberById(id);

           if (member != null) {
               member.activateMember(); // Activate the member's membership
               JOptionPane.showMessageDialog(frame, "Membership activated successfully.");
           } else {
               JOptionPane.showMessageDialog(frame, "Member not found.");
           }
       } catch (Exception ex) {
           JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
       }
   }

   /**
    * Deactivate a member's membership
    */
   private void deactivateMembership() {
       try {
           if (txtDeactivateID.getText().isEmpty()) {
               JOptionPane.showMessageDialog(frame, "Please enter a member ID.");
               return;
           }

           int id = validateIdInput(txtDeactivateID.getText());
           if (id == -1) return;

           GymMember member = findMemberById(id);

           if (member != null) {
               member.deactivateMember(); // Deactivate the member's membership
               JOptionPane.showMessageDialog(frame, "Membership deactivated successfully.");
           } else {
               JOptionPane.showMessageDialog(frame, "Member not found.");
           }
       } catch (Exception ex) {
           JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
       }
   }

   /**
    * Mark attendance for a member
    */
   private void markAttendance() {
       try {
           if (txtAttendanceID.getText().isEmpty()) {
               JOptionPane.showMessageDialog(frame, "Please enter a member ID.");
               return;
           }

           int id = validateIdInput(txtAttendanceID.getText());
           if (id == -1) return;

           GymMember member = findMemberById(id);

           if (member != null) {
               if (member.activeStatus()) {
                   member.markAttendance(); // Mark attendance for the member
                   JOptionPane.showMessageDialog(frame, "Attendance marked successfully.");
               } else {
                   JOptionPane.showMessageDialog(frame, "Cannot mark attendance - member is inactive.");
               }
           } else {
               JOptionPane.showMessageDialog(frame, "Member not found.");
           }
       } catch (Exception ex) {
           JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
       }
   }

   /**
    * Validate member ID input
    * @param idText The ID text to validate
    * @return The valid ID or -1 if invalid
    */
   private int validateIdInput(String idText) {
       try {
           int id = Integer.parseInt(idText);
           if (id <= 0) {
               JOptionPane.showMessageDialog(frame, "ID must be a positive number.");
               return -1;
           }
           return id;
       } catch (NumberFormatException ex) {
           JOptionPane.showMessageDialog(frame, "Invalid ID format. Please enter a positive number.");
           return -1;
       }
   }

   /**
    * Upgrade a regular member's plan
    */
   private void upgradePlan() {
       try {
           if (txtUpgradeID.getText().isEmpty()) {
               JOptionPane.showMessageDialog(frame, "Please enter a member ID.");
               return;
           }

           int id = validateIdInput(txtUpgradeID.getText());
           if (id == -1) return;

           String selectedPlan = (String) ComboPlan.getSelectedItem();
           GymMember member = findMemberById(id);

           if (member != null) {
               if (member instanceof RegularMember) {
                   RegularMember regularMember = (RegularMember) member;
                   if (member.activeStatus()) {
                       String result = regularMember.upgradePlan(selectedPlan.toLowerCase()); // Upgrade the member's plan
                       JOptionPane.showMessageDialog(frame, result);
                   } else {
                       JOptionPane.showMessageDialog(frame, "Cannot upgrade - member is inactive.");
                   }
               } else {
                   JOptionPane.showMessageDialog(frame, "Only regular members can upgrade plans.");
               }
           } else {
               JOptionPane.showMessageDialog(frame, "Member not found.");
           }
       } catch (Exception ex) {
           JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
       }
   }

   /**
    * Revert a premium member to regular
    */
   private void revertToRegular() {
       try {
           if (txtRevertID.getText().isEmpty()) {
               JOptionPane.showMessageDialog(frame, "Please enter a member ID.");
               return;
           }

           int id = validateIdInput(txtRevertID.getText());
           if (id == -1) return;

           GymMember member = findMemberById(id);

           if (member != null && member instanceof PremiumMember) {
               PremiumMember premiumMember = (PremiumMember) member;
               premiumMember.revertPremiumMember();

               RegularMember regularMember = convertToRegularMember(premiumMember); // Convert premium member to regular member

               members.set(members.indexOf(member), regularMember);
               JOptionPane.showMessageDialog(frame, "Premium Member reverted to Regular Member.");
           } else {
               JOptionPane.showMessageDialog(frame, "Premium Member not found or member is already a Regular Member.");
           }
       } catch (Exception ex) {
           JOptionPane.showMessageDialog(frame, "Error: " + ex.getMessage());
       }
   }

   /**
 * Calculate discount for a premium member
 */
private void calculateDiscount() {
    try {
        if (txtAttendanceID.getText().isEmpty()) {
            JOptionPane.showMessageDialog(frame, "Please enter a member ID.");
            return;
        }

        int id = validateIdInput(txtAttendanceID.getText());
        if (id == -1) return;

        GymMember member = findMemberById(id);

        if (member == null) {
            JOptionPane.showMessageDialog(frame, "Member not found.");
            return;
        }

        if (!(member instanceof PremiumMember)) {
            JOptionPane.showMessageDialog(frame, 
                "Discount calculation is only available for Premium members.",
                "Member Type Error",
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        PremiumMember premiumMember = (PremiumMember) member;

        // Calculate discount based on attendance
        double discount = premiumMember.calculateDiscount();

        // Update the discount amount field
        txtDiscountAmount.setText(String.format("%.2f", discount));

        // Format the message based on discount eligibility
        StringBuilder message = new StringBuilder();
        message.append("Member: ").append(member.getname()).append("\n");
        message.append("Attendance: ").append(member.getattendance()).append("\n");

        if (discount > 0) {
            message.append("Discount Amount: ").append(String.format("%.2f", discount)).append("\n");
            message.append("Eligible for 15% discount on premium charge.");
        } else {
            message.append("Not eligible for discount yet.\n");
            message.append("Minimum 30 attendances required for discount eligibility."); // Changed from 10 to 30
        }

        JOptionPane.showMessageDialog(frame, 
            message.toString(),
            "Discount Calculation",
            JOptionPane.INFORMATION_MESSAGE);

    } catch (Exception ex) {
        JOptionPane.showMessageDialog(frame, 
            "Error calculating discount: " + ex.getMessage(),
            "Processing Error",
            JOptionPane.ERROR_MESSAGE);
    }
}

   /**
    * Process payment for a premium member
    */
   private void payDueAmount() {
       try {
           // Validate input fields
           if (txtAttendanceID.getText().isEmpty() || txtPaidAmount.getText().isEmpty()) {
               JOptionPane.showMessageDialog(frame, "Please enter member ID and payment amount.");
               return;
           }

           int id = validateIdInput(txtAttendanceID.getText());
           if (id == -1) return;

           double amount = 0;
           try {
               amount = Double.parseDouble(txtPaidAmount.getText());
           } catch (NumberFormatException ex) {
               JOptionPane.showMessageDialog(frame, "Payment amount must be a valid number.");
               return;
           }

           // Check for negative or zero amount
           if (amount <= 0) {
               JOptionPane.showMessageDialog(frame, 
                   "Payment amount must be greater than zero.", 
                   "Invalid Amount", 
                   JOptionPane.ERROR_MESSAGE);
               return;
           }

           GymMember member = findMemberById(id);

           if (member != null && member instanceof PremiumMember) {
               PremiumMember premiumMember = (PremiumMember) member;
               String result = premiumMember.payDueAmount(amount);
               JOptionPane.showMessageDialog(frame, result);
           } else {
               JOptionPane.showMessageDialog(frame, "Premium Member not found.");
           }
       } catch (Exception ex) {
           JOptionPane.showMessageDialog(frame, 
               "Error: " + ex.getMessage(),
               "Processing Error",
               JOptionPane.ERROR_MESSAGE);
       }
   }

   /**
    * Convert a premium member to a regular member
    * @param premiumMember The premium member to convert
    * @return A new regular member with the same basic info
    */
   private RegularMember convertToRegularMember(PremiumMember premiumMember) {
       return new RegularMember(
           premiumMember.getid(), 
           premiumMember.getname(), 
           premiumMember.getlocation(), 
           premiumMember.getphone(), 
           premiumMember.getemail(), 
           premiumMember.getgender(), 
           premiumMember.getDOB(), 
           premiumMember.getmembershipStartDate(),
           "Converted from Premium"
       );
   }

   /**
    * Find a member by ID
    * @param id The ID to search for
    * @return The member with the given ID, or null if not found
    */
   private GymMember findMemberById(int id) {
       return members.stream()
                    .filter(m -> m.getid() == id)
                    .findFirst()
                    .orElse(null); // Find a member by ID
   }

   /**
    * Save all members to a file
    */
   private void saveToFile() {
       try {
           // Check if there are any members to save
           if (members.isEmpty()) {
               JOptionPane.showMessageDialog(frame, 
                   "No members to save. Please add members before saving to file.",
                   "No Data",
                   JOptionPane.INFORMATION_MESSAGE);
               return;
           }

           // Create directory if it doesn't exist
           File directory = new File("C:\\24046417 Nayem Raj Angdembay");
           if (!directory.exists()) {
               directory.mkdirs();
           }

           // Set the complete file path
           String filePath = "C:\\24046417 Nayem Raj Angdembay\\members.txt";

           try (FileWriter writer = new FileWriter(filePath)) {
               // Write header line
               writer.write(String.format("%-5s %-15s %-15s %-15s %-25s %-20s %-10s %-10s %-10s %-15s %-10s %-15s %-15s %-15s\n", 
                   "ID", "Name", "Location", "Phone", "Email", "Membership Start Date", "Plan", "Price", "Attendance", 
                   "Loyalty Points", "Active Status", "Full Payment", "Discount Amount", "Net Amount Paid"));

               // Write member data
               for (GymMember member : members) {
                   String plan = "";
                   String price = "";
                   String fullPayment = "N/A";
                   String discountAmount = "N/A";
                   String netAmountPaid = "N/A";

                   if (member instanceof RegularMember) {
                       RegularMember rm = (RegularMember) member;
                       plan = rm.get_plan();
                       price = String.valueOf(rm.get_price());
                   } else if (member instanceof PremiumMember) {
                       PremiumMember pm = (PremiumMember) member;
                       plan = "Premium";
                       price = String.valueOf(pm.getPremiumCharge());
                       fullPayment = pm.isFullPayment() ? "Yes" : "No";
                       discountAmount = String.valueOf(pm.getDiscountAmount());
                       netAmountPaid = String.valueOf(pm.getPaidAmount());
                   }

                   writer.write(String.format("%-5d %-15s %-15s %-15s %-25s %-20s %-10s %-10s %-10d %-15d %-10s %-15s %-15s %-15s\n", 
                       member.getid(), 
                       member.getname(), 
                       member.getlocation(), 
                       member.getphone(), 
                       member.getemail(), 
                       member.getmembershipStartDate(), 
                       plan, 
                       price, 
                       member.getattendance(), 
                       member.getLoyaltyPoints(), 
                       member.activeStatus() ? "Active" : "Inactive", 
                       fullPayment, 
                       discountAmount, 
                       netAmountPaid));
               }

               JOptionPane.showMessageDialog(frame, "Members saved to file successfully at: " + filePath);
           }
       } catch (IOException ex) {
           JOptionPane.showMessageDialog(frame, 
               "Error saving to file: " + ex.getMessage(),
               "File Error",
               JOptionPane.ERROR_MESSAGE);
       }
   }

   /**
    * Read member data from a file and display it
    */
   private void readFromFile() {
       try {
           // Set the complete file path
           String filePath = "C:\\24046417 Nayem Raj Angdembay\\members.txt";
           File file = new File(filePath);

           if (!file.exists()) {
               JOptionPane.showMessageDialog(frame, 
                   "File not found at: " + filePath, 
                   "File Error", 
                   JOptionPane.ERROR_MESSAGE);
               return;
           }

           StringBuilder content = new StringBuilder();
           try (Scanner scanner = new Scanner(file)) {
               // Read all lines from file
               while (scanner.hasNextLine()) {
                   content.append(scanner.nextLine()).append("\n");
               }
           }

           // Display content in a scrollable text area
           JTextArea textArea = new JTextArea(content.toString());
           textArea.setEditable(false);
           textArea.setFont(new Font("Monospaced", Font.PLAIN, 12)); // Use monospaced font for better alignment
           JScrollPane scrollPane = new JScrollPane(textArea);
           scrollPane.setPreferredSize(new Dimension(800, 400));

           JOptionPane.showMessageDialog(frame, scrollPane, "Member Details from File", JOptionPane.INFORMATION_MESSAGE);
       } catch (IOException ex) {
           JOptionPane.showMessageDialog(frame, 
               "Error reading from file: " + ex.getMessage(),
               "File Error",
               JOptionPane.ERROR_MESSAGE);
       }
   }

   /**
    * Display information about all members
    */
   private void displayAllMembers() {
       if (members.isEmpty()) {
           JOptionPane.showMessageDialog(frame, "No members to display."); // Check if there are no members
           return;
       }

       StringBuilder displayText = new StringBuilder();

       for (GymMember member: members) {
           displayText.append("ID: ").append(member.getid()).append("\n");
           displayText.append("Name: ").append(member.getname()).append("\n");
           displayText.append("Location: ").append(member.getlocation()).append("\n");
           displayText.append("Phone: ").append(member.getphone()).append("\n");
           displayText.append("Email: ").append(member.getemail()).append("\n");
           displayText.append("Gender: ").append(member.getgender()).append("\n");
           displayText.append("DOB: ").append(member.getDOB()).append("\n");
           displayText.append("Membership Start: ").append(member.getmembershipStartDate()).append("\n");
           displayText.append("Attendance: ").append(member.getattendance()).append("\n");
           displayText.append("Loyalty Points: ").append(member.getLoyaltyPoints()).append("\n");
           displayText.append("Status: ").append(member.activeStatus() ? "Active" : "Inactive").append("\n");

           if (member instanceof RegularMember) {
               RegularMember rm = (RegularMember) member;
               displayText.append("Type: Regular Member\n");
               displayText.append("Plan: ").append(rm.get_plan()).append("\n");
               displayText.append("Price: ").append(rm.get_price()).append("\n");
               displayText.append("Referral: ").append(rm.get_referralSource()).append("\n");
               displayText.append("Eligible for Upgrade: ").append(rm.get_isEligibleForUpgrade() ? "Yes" : "No").append("\n");
           } else if (member instanceof PremiumMember) {
               PremiumMember pm = (PremiumMember) member;
               displayText.append("Type: Premium Member\n");
               displayText.append("Trainer: ").append(pm.getPersonalTrainer()).append("\n");
               displayText.append("Premium Charge: ").append(pm.getPremiumCharge()).append("\n");
               displayText.append("Paid Amount: ").append(pm.getPaidAmount()).append("\n");
               displayText.append("Full Payment: ").append(pm.isFullPayment() ? "Yes" : "No").append("\n");
               displayText.append("Discount Amount: ").append(pm.getDiscountAmount()).append("\n");
           }

           displayText.append("\n-------------------------------\n\n");
       }

       JTextArea textArea = new JTextArea(displayText.toString());
       textArea.setEditable(false);
       JScrollPane scrollPane = new JScrollPane(textArea);
       scrollPane.setPreferredSize(new Dimension(600, 400));

       JOptionPane.showMessageDialog(frame, scrollPane, "All Members", JOptionPane.INFORMATION_MESSAGE);
   }

   /**
    * Clear all form fields
    */
   private void clearForm() {
       // Clear text fields
       Component[] components = currentPanel.getComponents();
       for (Component component : components) {
           if (component instanceof JTextField) {
               JTextField field = (JTextField) component;
               // Don't clear non-editable fields with default values
               if (field != txtRegularPlanPrice && field != txtPremiumPlanCharge && field != txtDiscountAmount) {
                   field.setText("");
               }
           } else if (component instanceof JPanel) {
               // Also check for text fields in nested panels
               Component[] panelComponents = ((JPanel) component).getComponents();
               for (Component panelComp : panelComponents) {
                   if (panelComp instanceof JTextField) {
                       JTextField field = (JTextField) panelComp;
                       // Skip default fields
                       if (!field.getText().equals("6500") && !field.getText().equals("50000") && !field.getText().equals("0")) {
                           field.setText("");
                       }
                   }
               }
           }
       }

       // Set default values for non-editable fields
       txtRegularPlanPrice.setText("6500");
       txtPremiumPlanCharge.setText("50000");
       txtDiscountAmount.setText("0");

       // Reset radio buttons
       genderGroup.clearSelection();

       // Reset combo boxes
       ComboDay.setSelectedIndex(0);
       ComboMonth.setSelectedIndex(0);
       ComboYear.setSelectedIndex(0);
       ComboStartDay.setSelectedIndex(0);
       ComboStartMonth.setSelectedIndex(0);
       ComboStartYear.setSelectedIndex(0);
       if (ComboPlan != null) {
           ComboPlan.setSelectedIndex(0);
       }
   }

}