package sms;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;

public class SchoolManagementGui extends JFrame {
    private final Map<String, Student> students = new LinkedHashMap<>();
    private final Map<String, Teacher> teachers = new LinkedHashMap<>();

    private final JLabel totalStudentsLabel = new JLabel("Total Students: 0");
    private final JLabel totalTeachersLabel = new JLabel("Total Teachers: 0");
    private final JLabel attendanceRecordsLabel = new JLabel("Attendance Records: 0");
    private final JLabel resultsRecordsLabel = new JLabel("Result Records: 0");

    private final DefaultTableModel studentTableModel = new DefaultTableModel(
            new String[]{"ID", "Name", "Gender", "Class", "Fees Due", "Paid", "Balance"}, 0);
    private final DefaultTableModel teacherTableModel = new DefaultTableModel(
            new String[]{"ID", "Name", "Gender", "Specialization", "Assigned Classes"}, 0);

    private final JComboBox<String> attendanceStudentCombo = new JComboBox<>();
    private final JComboBox<String> resultStudentCombo = new JComboBox<>();
    private final JComboBox<String> feeStudentCombo = new JComboBox<>();
    private final JComboBox<String> attendanceStatusCombo = new JComboBox<>(new String[]{"PRESENT", "ABSENT", "LATE"});

    private final JTextArea recordsLogArea = new JTextArea(10, 60);
    private final JTextArea reportsArea = new JTextArea(18, 80);

    public SchoolManagementGui() {
        setTitle("School Management System - GUI");
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Dashboard", buildDashboardPanel());
        tabs.addTab("Students", buildStudentsPanel());
        tabs.addTab("Teachers", buildTeachersPanel());
        tabs.addTab("Records", buildRecordsPanel());
        tabs.addTab("Reports", buildReportsPanel());
        add(tabs);
    }

    private JPanel buildDashboardPanel() {
        JPanel panel = new JPanel(new GridLayout(2, 2, 12, 12));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.add(createCard(totalStudentsLabel));
        panel.add(createCard(totalTeachersLabel));
        panel.add(createCard(attendanceRecordsLabel));
        panel.add(createCard(resultsRecordsLabel));
        return panel;
    }

    private JPanel createCard(JLabel label) {
        JPanel card = new JPanel(new BorderLayout());
        label.setHorizontalAlignment(SwingConstants.CENTER);
        card.add(label, BorderLayout.CENTER);
        card.setBorder(BorderFactory.createEtchedBorder());
        return card;
    }

    private JPanel buildStudentsPanel() {
        JPanel panel = new JPanel(new BorderLayout(8, 8));
        panel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        JPanel form = new JPanel(new GridLayout(0, 2, 8, 8));
        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField genderField = new JTextField();
        JTextField phoneField = new JTextField();
        JTextField addressField = new JTextField();
        JTextField classField = new JTextField();
        JTextField feesDueField = new JTextField();

        form.add(new JLabel("Student ID"));
        form.add(idField);
        form.add(new JLabel("Name"));
        form.add(nameField);
        form.add(new JLabel("Gender"));
        form.add(genderField);
        form.add(new JLabel("Phone"));
        form.add(phoneField);
        form.add(new JLabel("Address"));
        form.add(addressField);
        form.add(new JLabel("Class"));
        form.add(classField);
        form.add(new JLabel("Total Fees Due"));
        form.add(feesDueField);

        JButton addButton = new JButton("Add Student");
        addButton.addActionListener(e -> {
            try {
                String id = required(idField.getText(), "Student ID");
                if (students.containsKey(id)) {
                    showError("Student ID already exists.");
                    return;
                }
                Student student = new Student(
                        id,
                        required(nameField.getText(), "Name"),
                        required(genderField.getText(), "Gender"),
                        required(phoneField.getText(), "Phone"),
                        required(addressField.getText(), "Address"),
                        required(classField.getText(), "Class"),
                        parseDouble(feesDueField.getText(), "Fees Due")
                );
                students.put(student.getId(), student);
                refreshAllViews();
                clearFields(idField, nameField, genderField, phoneField, addressField, classField, feesDueField);
                showInfo("Student added successfully.");
            } catch (IllegalArgumentException ex) {
                showError(ex.getMessage());
            }
        });

        JPanel top = new JPanel(new BorderLayout());
        top.add(form, BorderLayout.CENTER);
        top.add(addButton, BorderLayout.SOUTH);

        JTable table = new JTable(studentTableModel);
        JButton removeStudentBtn = new JButton("Remove Selected");
        removeStudentBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) {
                showError("Select a student row to remove.");
                return;
            }
            String id = (String) studentTableModel.getValueAt(row, 0);
            students.remove(id);
            refreshAllViews();
            showInfo("Student removed.");
        });
        JPanel studentSouth = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        studentSouth.add(removeStudentBtn);

        panel.add(top, BorderLayout.NORTH);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        panel.add(studentSouth, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel buildTeachersPanel() {
        JPanel panel = new JPanel(new BorderLayout(8, 8));
        panel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        JPanel form = new JPanel(new GridLayout(0, 2, 8, 8));
        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField genderField = new JTextField();
        JTextField phoneField = new JTextField();
        JTextField addressField = new JTextField();
        JTextField specializationField = new JTextField();
        JTextField classesField = new JTextField();

        form.add(new JLabel("Teacher ID"));
        form.add(idField);
        form.add(new JLabel("Name"));
        form.add(nameField);
        form.add(new JLabel("Gender"));
        form.add(genderField);
        form.add(new JLabel("Phone"));
        form.add(phoneField);
        form.add(new JLabel("Address"));
        form.add(addressField);
        form.add(new JLabel("Specialization"));
        form.add(specializationField);
        form.add(new JLabel("Assigned Classes (comma separated)"));
        form.add(classesField);

        JButton addButton = new JButton("Add Teacher");
        addButton.addActionListener(e -> {
            try {
                String id = required(idField.getText(), "Teacher ID");
                if (teachers.containsKey(id)) {
                    showError("Teacher ID already exists.");
                    return;
                }
                Teacher teacher = new Teacher(
                        id,
                        required(nameField.getText(), "Name"),
                        required(genderField.getText(), "Gender"),
                        required(phoneField.getText(), "Phone"),
                        required(addressField.getText(), "Address"),
                        required(specializationField.getText(), "Specialization")
                );
                String[] classes = classesField.getText().split(",");
                for (String c : classes) {
                    if (!c.isBlank()) {
                        teacher.assignClass(c.trim());
                    }
                }
                teachers.put(teacher.getId(), teacher);
                refreshAllViews();
                clearFields(idField, nameField, genderField, phoneField, addressField, specializationField, classesField);
                showInfo("Teacher added successfully.");
            } catch (IllegalArgumentException ex) {
                showError(ex.getMessage());
            }
        });

        JPanel top = new JPanel(new BorderLayout());
        top.add(form, BorderLayout.CENTER);
        top.add(addButton, BorderLayout.SOUTH);

        JTable table = new JTable(teacherTableModel);
        JButton removeTeacherBtn = new JButton("Remove Selected");
        removeTeacherBtn.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row < 0) {
                showError("Select a teacher row to remove.");
                return;
            }
            String id = (String) teacherTableModel.getValueAt(row, 0);
            teachers.remove(id);
            refreshAllViews();
            showInfo("Teacher removed.");
        });
        JPanel teacherSouth = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        teacherSouth.add(removeTeacherBtn);

        panel.add(top, BorderLayout.NORTH);
        panel.add(new JScrollPane(table), BorderLayout.CENTER);
        panel.add(teacherSouth, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel buildRecordsPanel() {
        JPanel panel = new JPanel(new BorderLayout(8, 8));
        panel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));

        JPanel actions = new JPanel(new GridLayout(3, 1, 8, 8));
        actions.add(buildAttendancePanel());
        actions.add(buildResultsPanel());
        actions.add(buildFeesPanel());

        recordsLogArea.setEditable(false);
        panel.add(actions, BorderLayout.NORTH);
        panel.add(new JScrollPane(recordsLogArea), BorderLayout.CENTER);
        return panel;
    }

    private JPanel buildAttendancePanel() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 8, 8));
        panel.setBorder(BorderFactory.createTitledBorder("Record Attendance"));
        JButton saveButton = new JButton("Save Attendance");
        saveButton.addActionListener(e -> {
            Student student = selectedStudent(attendanceStudentCombo);
            if (student == null) return;
            AttendanceStatus status = AttendanceStatus.valueOf((String) attendanceStatusCombo.getSelectedItem());
            student.addAttendanceRecord(new AttendanceRecord(LocalDate.now(), status));
            appendLog("Attendance saved for " + student.getName() + ": " + status);
            refreshAllViews();
        });
        panel.add(new JLabel("Student"));
        panel.add(attendanceStudentCombo);
        panel.add(new JLabel("Status"));
        panel.add(attendanceStatusCombo);
        panel.add(new JLabel(""));
        panel.add(saveButton);
        return panel;
    }

    private JPanel buildResultsPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 8, 8));
        panel.setBorder(BorderFactory.createTitledBorder("Record Result"));
        JTextField subjectField = new JTextField();
        JTextField markField = new JTextField();
        JTextField commentField = new JTextField();
        JButton saveButton = new JButton("Save Result");
        saveButton.addActionListener(e -> {
            Student student = selectedStudent(resultStudentCombo);
            if (student == null) return;
            try {
                ResultRecord result = new ResultRecord(
                        required(subjectField.getText(), "Subject"),
                        parseDouble(markField.getText(), "Mark"),
                        commentField.getText()
                );
                student.addResultRecord(result);
                appendLog("Result saved for " + student.getName() + ": " + result.getSubject() +
                        " " + result.getMark() + " (" + result.getGrade() + ")");
                clearFields(subjectField, markField, commentField);
                refreshAllViews();
            } catch (IllegalArgumentException ex) {
                showError(ex.getMessage());
            }
        });
        panel.add(new JLabel("Student"));
        panel.add(resultStudentCombo);
        panel.add(new JLabel("Subject"));
        panel.add(subjectField);
        panel.add(new JLabel("Mark"));
        panel.add(markField);
        panel.add(new JLabel("Comment"));
        panel.add(commentField);
        panel.add(new JLabel(""));
        panel.add(saveButton);
        return panel;
    }

    private JPanel buildFeesPanel() {
        JPanel panel = new JPanel(new GridLayout(0, 2, 8, 8));
        panel.setBorder(BorderFactory.createTitledBorder("Record Fee Payment"));
        JTextField amountField = new JTextField();
        JTextField methodField = new JTextField();
        JTextField referenceField = new JTextField();
        JButton saveButton = new JButton("Save Payment");
        saveButton.addActionListener(e -> {
            Student student = selectedStudent(feeStudentCombo);
            if (student == null) return;
            try {
                FeePayment payment = new FeePayment(
                        LocalDate.now(),
                        parseDouble(amountField.getText(), "Amount"),
                        required(methodField.getText(), "Method"),
                        referenceField.getText()
                );
                student.addPayment(payment);
                appendLog("Payment saved for " + student.getName() +
                        ": " + payment.getAmount() + ", new balance " + String.format("%.2f", student.getFeesBalance()));
                clearFields(amountField, methodField, referenceField);
                refreshAllViews();
            } catch (IllegalArgumentException ex) {
                showError(ex.getMessage());
            }
        });
        panel.add(new JLabel("Student"));
        panel.add(feeStudentCombo);
        panel.add(new JLabel("Amount"));
        panel.add(amountField);
        panel.add(new JLabel("Method"));
        panel.add(methodField);
        panel.add(new JLabel("Reference"));
        panel.add(referenceField);
        panel.add(new JLabel(""));
        panel.add(saveButton);
        return panel;
    }

    private JPanel buildReportsPanel() {
        JPanel panel = new JPanel(new BorderLayout(8, 8));
        panel.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));

        JButton studentsBtn = new JButton("Student List");
        studentsBtn.addActionListener(e -> reportsArea.setText(buildStudentListReport()));
        JButton feesBtn = new JButton("Fee Balances");
        feesBtn.addActionListener(e -> reportsArea.setText(buildFeesReport()));
        JButton feeStatementsBtn = new JButton("Fee Statements");
        feeStatementsBtn.addActionListener(e -> reportsArea.setText(buildFeeStatementsReport()));
        JButton attendanceBtn = new JButton("Attendance Summary");
        attendanceBtn.addActionListener(e -> reportsArea.setText(buildAttendanceReport()));
        JButton academicBtn = new JButton("Academic Summary");
        academicBtn.addActionListener(e -> reportsArea.setText(buildAcademicReport()));

        buttons.add(studentsBtn);
        buttons.add(feesBtn);
        buttons.add(feeStatementsBtn);
        buttons.add(attendanceBtn);
        buttons.add(academicBtn);

        reportsArea.setEditable(false);
        panel.add(buttons, BorderLayout.NORTH);
        panel.add(new JScrollPane(reportsArea), BorderLayout.CENTER);
        return panel;
    }

    private void refreshAllViews() {
        refreshStudentTable();
        refreshTeacherTable();
        refreshStudentCombos();
        refreshDashboard();
    }

    private void refreshStudentTable() {
        studentTableModel.setRowCount(0);
        for (Student s : students.values()) {
            studentTableModel.addRow(new Object[]{
                    s.getId(), s.getName(), s.getGender(), s.getClassName(),
                    String.format("%.2f", s.getTotalFeesDue()),
                    String.format("%.2f", s.getTotalPaid()),
                    String.format("%.2f", s.getFeesBalance())
            });
        }
    }

    private void refreshTeacherTable() {
        teacherTableModel.setRowCount(0);
        for (Teacher t : teachers.values()) {
            teacherTableModel.addRow(new Object[]{
                    t.getId(), t.getName(), t.getGender(), t.getSpecialization(),
                    String.join(", ", t.getAssignedClasses())
            });
        }
    }

    private void refreshStudentCombos() {
        String[] ids = students.keySet().toArray(new String[0]);
        setComboValues(attendanceStudentCombo, ids);
        setComboValues(resultStudentCombo, ids);
        setComboValues(feeStudentCombo, ids);
    }

    private void setComboValues(JComboBox<String> combo, String[] values) {
        combo.removeAllItems();
        for (String id : values) {
            combo.addItem(id);
        }
    }

    private void refreshDashboard() {
        totalStudentsLabel.setText("Total Students: " + students.size());
        totalTeachersLabel.setText("Total Teachers: " + teachers.size());

        int attendanceCount = 0;
        int resultCount = 0;
        for (Student s : students.values()) {
            attendanceCount += s.getAttendanceRecords().size();
            resultCount += s.getResultRecords().size();
        }
        attendanceRecordsLabel.setText("Attendance Records: " + attendanceCount);
        resultsRecordsLabel.setText("Result Records: " + resultCount);
    }

    private Student selectedStudent(JComboBox<String> combo) {
        String id = (String) combo.getSelectedItem();
        if (id == null || id.isBlank()) {
            showError("No student available. Please add students first.");
            return null;
        }
        return students.get(id);
    }

    private String buildStudentListReport() {
        StringBuilder sb = new StringBuilder("=== Student List ===\n");
        if (students.isEmpty()) {
            return sb.append("No students registered.\n").toString();
        }
        for (Student s : students.values()) {
            sb.append(s.getId()).append(" | ")
                    .append(s.getName()).append(" | ")
                    .append(s.getClassName()).append(" | Balance: ")
                    .append(String.format("%.2f", s.getFeesBalance())).append('\n');
        }
        return sb.toString();
    }

    private String buildFeesReport() {
        StringBuilder sb = new StringBuilder("=== Fee Balances ===\n");
        if (students.isEmpty()) {
            return sb.append("No students registered.\n").toString();
        }
        for (Student s : students.values()) {
            sb.append(s.getId()).append(" | ")
                    .append(s.getName()).append(" | Due: ")
                    .append(String.format("%.2f", s.getTotalFeesDue()))
                    .append(" | Paid: ").append(String.format("%.2f", s.getTotalPaid()))
                    .append(" | Balance: ").append(String.format("%.2f", s.getFeesBalance()))
                    .append('\n');
        }
        return sb.toString();
    }

    private String buildFeeStatementsReport() {
        StringBuilder sb = new StringBuilder("=== Fee Statements ===\n");
        if (students.isEmpty()) {
            return sb.append("No students registered.\n").toString();
        }
        for (Student s : students.values()) {
            sb.append("--- ").append(s.getName()).append(" (").append(s.getId()).append(") ---\n");
            sb.append(String.format("Total due: %.2f | Paid: %.2f | Balance: %.2f%n",
                    s.getTotalFeesDue(), s.getTotalPaid(), s.getFeesBalance()));
            if (s.getFeePayments().isEmpty()) {
                sb.append("No payments recorded.\n");
            } else {
                sb.append("Payment history:\n");
                for (FeePayment p : s.getFeePayments()) {
                    sb.append(String.format("  %s | %.2f | %s | %s%n",
                            p.getDate(), p.getAmount(), p.getMethod(), p.getReference()));
                }
            }
            sb.append('\n');
        }
        return sb.toString();
    }

    private String buildAttendanceReport() {
        StringBuilder sb = new StringBuilder("=== Attendance Summary ===\n");
        if (students.isEmpty()) {
            return sb.append("No students registered.\n").toString();
        }
        for (Student s : students.values()) {
            long present = s.getAttendanceRecords().stream().filter(a -> a.getStatus() == AttendanceStatus.PRESENT).count();
            long absent = s.getAttendanceRecords().stream().filter(a -> a.getStatus() == AttendanceStatus.ABSENT).count();
            long late = s.getAttendanceRecords().stream().filter(a -> a.getStatus() == AttendanceStatus.LATE).count();
            sb.append(s.getId()).append(" | ").append(s.getName())
                    .append(" | Present: ").append(present)
                    .append(" | Absent: ").append(absent)
                    .append(" | Late: ").append(late)
                    .append('\n');
        }
        return sb.toString();
    }

    private String buildAcademicReport() {
        StringBuilder sb = new StringBuilder("=== Academic Summary ===\n");
        if (students.isEmpty()) {
            return sb.append("No students registered.\n").toString();
        }
        for (Student s : students.values()) {
            double avg = s.getResultRecords().stream().mapToDouble(ResultRecord::getMark).average().orElse(0.0);
            sb.append(s.getId()).append(" | ").append(s.getName())
                    .append(" | Results: ").append(s.getResultRecords().size())
                    .append(" | Average: ").append(String.format("%.2f", avg))
                    .append('\n');
        }
        return sb.toString();
    }

    private void appendLog(String message) {
        recordsLogArea.append(LocalDate.now() + " - " + message + "\n");
    }

    private String required(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " cannot be blank.");
        }
        return value.trim();
    }

    private double parseDouble(String value, String fieldName) {
        try {
            double number = Double.parseDouble(value.trim());
            if (number < 0) {
                throw new IllegalArgumentException(fieldName + " cannot be negative.");
            }
            return number;
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException("Enter a valid number for " + fieldName + ".");
        }
    }

    private void clearFields(JTextField... fields) {
        for (JTextField field : fields) {
            field.setText("");
        }
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void showInfo(String message) {
        JOptionPane.showMessageDialog(this, message, "Success", JOptionPane.INFORMATION_MESSAGE);
    }
}
