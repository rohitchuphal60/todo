import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class TodoApp extends JFrame {
    private DefaultListModel<TodoItem> listModel;
    private JList<TodoItem> todoList;
    private JTextField taskField;
    private JButton addButton, editButton, deleteButton, completeButton;
    private List<TodoItem> todos;

    public TodoApp() {
        todos = new ArrayList<>();
        initializeComponents();
        setupLayout();
        setupEventListeners();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("TODO Application");
        setSize(500, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void initializeComponents() {
        // Initialize list model and JList
        listModel = new DefaultListModel<>();
        todoList = new JList<>(listModel);
        todoList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        todoList.setCellRenderer(new TodoCellRenderer());
        
        // Initialize input components
        taskField = new JTextField(20);
        addButton = new JButton("Add Task");
        editButton = new JButton("Edit Task");
        deleteButton = new JButton("Delete Task");
        completeButton = new JButton("Toggle Complete");
        
        // Set button colors
        addButton.setBackground(new Color(46, 125, 50));
        addButton.setForeground(Color.WHITE);
        editButton.setBackground(new Color(33, 150, 243));
        editButton.setForeground(Color.WHITE);
        deleteButton.setBackground(new Color(244, 67, 54));
        deleteButton.setForeground(Color.WHITE);
        completeButton.setBackground(new Color(255, 152, 0));
        completeButton.setForeground(Color.WHITE);
    }

    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Title panel
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(new Color(63, 81, 181));
        JLabel titleLabel = new JLabel("TODO Application");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        titlePanel.add(titleLabel);
        titlePanel.setBorder(new EmptyBorder(20, 20, 20, 20));
        
        // Input panel
        JPanel inputPanel = new JPanel(new FlowLayout());
        inputPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        inputPanel.add(new JLabel("New Task:"));
        inputPanel.add(taskField);
        inputPanel.add(addButton);
        
        // List panel
        JScrollPane scrollPane = new JScrollPane(todoList);
        scrollPane.setPreferredSize(new Dimension(450, 300));
        scrollPane.setBorder(BorderFactory.createTitledBorder("Tasks"));
        
        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(completeButton);
        
        // Add components to frame
        add(titlePanel, BorderLayout.NORTH);
        add(inputPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);
        
        // Create bottom panel for buttons
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(buttonPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
        
        // Reorganize layout
        remove(inputPanel);
        remove(scrollPane);
        remove(bottomPanel);
        
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(inputPanel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        centerPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        add(centerPanel, BorderLayout.CENTER);
    }

    private void setupEventListeners() {
        // Add task button
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addTask();
            }
        });
        
        // Enter key in text field
        taskField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addTask();
            }
        });
        
        // Edit task button
        editButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editTask();
            }
        });
        
        // Delete task button
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteTask();
            }
        });
        
        // Complete task button
        completeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggleComplete();
            }
        });
    }

    private void addTask() {
        String taskText = taskField.getText().trim();
        if (!taskText.isEmpty()) {
            TodoItem newTodo = new TodoItem(taskText);
            todos.add(newTodo);
            listModel.addElement(newTodo);
            taskField.setText("");
            taskField.requestFocus();
        } else {
            JOptionPane.showMessageDialog(this, "Please enter a task!", "Empty Task", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void editTask() {
        int selectedIndex = todoList.getSelectedIndex();
        if (selectedIndex != -1) {
            TodoItem selectedTodo = listModel.getElementAt(selectedIndex);
            String newText = JOptionPane.showInputDialog(this, "Edit task:", selectedTodo.getText());
            if (newText != null && !newText.trim().isEmpty()) {
                selectedTodo.setText(newText.trim());
                listModel.setElementAt(selectedTodo, selectedIndex);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a task to edit!", "No Selection", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void deleteTask() {
        int selectedIndex = todoList.getSelectedIndex();
        if (selectedIndex != -1) {
            int result = JOptionPane.showConfirmDialog(this, 
                "Are you sure you want to delete this task?", 
                "Confirm Delete", 
                JOptionPane.YES_NO_OPTION);
            if (result == JOptionPane.YES_OPTION) {
                todos.remove(selectedIndex);
                listModel.removeElementAt(selectedIndex);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a task to delete!", "No Selection", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void toggleComplete() {
        int selectedIndex = todoList.getSelectedIndex();
        if (selectedIndex != -1) {
            TodoItem selectedTodo = listModel.getElementAt(selectedIndex);
            selectedTodo.setCompleted(!selectedTodo.isCompleted());
            listModel.setElementAt(selectedTodo, selectedIndex);
            todoList.repaint();
        } else {
            JOptionPane.showMessageDialog(this, "Please select a task to toggle!", "No Selection", JOptionPane.WARNING_MESSAGE);
        }
    }

    // TodoItem class to represent individual tasks
    class TodoItem {
        private String text;
        private boolean completed;

        public TodoItem(String text) {
            this.text = text;
            this.completed = false;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public boolean isCompleted() {
            return completed;
        }

        public void setCompleted(boolean completed) {
            this.completed = completed;
        }

        @Override
        public String toString() {
            return (completed ? "✓ " : "○ ") + text;
        }
    }

    // Custom cell renderer for better visual representation
    class TodoCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                                                    boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            
            if (value instanceof TodoItem) {
                TodoItem todo = (TodoItem) value;
                setText(todo.toString());
                
                if (todo.isCompleted()) {
                    setFont(getFont().deriveFont(Font.ITALIC));
                    setForeground(isSelected ? Color.WHITE : Color.GRAY);
                } else {
                    setFont(getFont().deriveFont(Font.PLAIN));
                    setForeground(isSelected ? Color.WHITE : Color.BLACK);
                }
            }
            
            return this;
        }
    }

    public static void main(String[] args) {
        // Set look and feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeel());
        } catch (ClassNotFoundException | InstantiationException | 
                 IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new TodoApp();
            }
        });
    }
}