import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class GymApp extends JFrame {
    private JTextField txtId, txtName;
    private JComboBox<String> cmbType;
    private JCheckBox chkTrainer;
    private JTable table;
    private DefaultTableModel tableModel;

    public GymApp() {
        setTitle("StayFit Gym Management System");
        setSize(720, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel pnlForm = new JPanel(new GridLayout(6, 2, 5, 10));
        pnlForm.setBorder(BorderFactory.createTitledBorder("Add New Member"));

        pnlForm.add(new JLabel(" Member ID:"));
        txtId = new JTextField();
        pnlForm.add(txtId);

        pnlForm.add(new JLabel(" Full Name:"));
        txtName = new JTextField();
        pnlForm.add(txtName);

        pnlForm.add(new JLabel(" Membership Type:"));
        cmbType = new JComboBox<>(new String[]{"Regular", "Premium"});
        pnlForm.add(cmbType);

        pnlForm.add(new JLabel(" Personal Trainer?"));
        chkTrainer = new JCheckBox("Yes (+Rs.500)");
        chkTrainer.setEnabled(false);
        pnlForm.add(chkTrainer);

        cmbType.addActionListener(e -> chkTrainer.setEnabled(cmbType.getSelectedItem().equals("Premium")));

        JButton btnAdd = new JButton("Save Member");
        pnlForm.add(btnAdd);
        
        add(pnlForm, BorderLayout.WEST);

        tableModel = new DefaultTableModel(new String[]{"ID", "Name", "Type", "Monthly Fee (Rs)"}, 0);
        table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveMemberToDatabase();
            }
        });

        loadTableData();
    }

    private void saveMemberToDatabase() {
        String id = txtId.getText().trim();
        String name = txtName.getText().trim();
        String type = (String) cmbType.getSelectedItem();
        
        if (id.isEmpty() || name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all empty fields!", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Member member;
        if (type.equals("Premium")) {
            member = new PremiumMember(id, name, chkTrainer.isSelected());
        } else {
            member = new Member(id, name);
        }

        double calculatedFee = member.calculateFee();
        String finalType = member.getType();

        String sql = "INSERT INTO members (id, name, type, fee) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, member.getId());
            pstmt.setString(2, member.getName());
            pstmt.setString(3, finalType);
            pstmt.setDouble(4, calculatedFee);
            
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(this, "Member records pushed cleanly to local database storage!");
            
            tableModel.addRow(new Object[]{member.getId(), member.getName(), finalType, calculatedFee});
            txtId.setText("");
            txtName.setText("");
            chkTrainer.setSelected(false);
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Database Execution Exception: " + ex.getMessage(), "Runtime Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadTableData() {
        tableModel.setRowCount(0);
        String sql = "SELECT * FROM members";
        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                tableModel.addRow(new Object[]{
                    rs.getString("id"),
                    rs.getString("name"),
                    rs.getString("type"),
                    rs.getDouble("fee")
                });
            }
        } catch (Exception ex) {
            System.out.println("Initial data load skipped: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GymApp().setVisible(true));
    }
}