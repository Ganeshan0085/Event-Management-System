import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class EventManagementApp extends JFrame {
    JTextField nameField, dateField, venueField;
    JTable eventTable;
    DefaultTableModel model;

    public EventManagementApp() {
        setTitle("Event Management System");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        JLabel title = new JLabel("Mini Event Management System", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 18));
        title.setBounds(100, 10, 400, 30);
        add(title);

        JLabel nameLbl = new JLabel("Event Name:");
        nameLbl.setBounds(50, 60, 100, 25);
        add(nameLbl);

        nameField = new JTextField();
        nameField.setBounds(150, 60, 150, 25);
        add(nameField);

        JLabel dateLbl = new JLabel("Date:");
        dateLbl.setBounds(320, 60, 50, 25);
        add(dateLbl);

        dateField = new JTextField();
        dateField.setBounds(370, 60, 150, 25);
        add(dateField);

        JLabel venueLbl = new JLabel("Venue:");
        venueLbl.setBounds(50, 100, 100, 25);
        add(venueLbl);

        venueField = new JTextField();
        venueField.setBounds(150, 100, 370, 25);
        add(venueField);

        JButton addBtn = new JButton("Add Event");
        addBtn.setBounds(230, 140, 120, 30);
        add(addBtn);

        model = new DefaultTableModel(new String[]{"ID", "Name", "Date", "Venue"}, 0);
        eventTable = new JTable(model);
        JScrollPane sp = new JScrollPane(eventTable);
        sp.setBounds(50, 190, 500, 150);
        add(sp);

        JButton refreshBtn = new JButton("Refresh");
        refreshBtn.setBounds(230, 350, 120, 25);
        add(refreshBtn);

        addBtn.addActionListener(e -> addEvent());
        refreshBtn.addActionListener(e -> loadEvents());

        loadEvents();
        setVisible(true);
    }

    private void addEvent() {
        String name = nameField.getText();
        String date = dateField.getText();
        String venue = venueField.getText();

        if (name.isEmpty() || date.isEmpty() || venue.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill all fields");
            return;
        }

        try {
            Connection con = DBConnection.getConnection();
            PreparedStatement ps = con.prepareStatement("INSERT INTO events(name, date, venue) VALUES (?, ?, ?)");
            ps.setString(1, name);
            ps.setString(2, date);
            ps.setString(3, venue);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Event Added Successfully!");
            nameField.setText("");
            dateField.setText("");
            venueField.setText("");
            loadEvents();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void loadEvents() {
        try {
            model.setRowCount(0);
            Connection con = DBConnection.getConnection();
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM events");
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getString("date"),
                    rs.getString("venue")
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new EventManagementApp();
    }
}
