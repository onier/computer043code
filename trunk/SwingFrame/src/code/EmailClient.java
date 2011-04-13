/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package code;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.mail.*;
import javax.mail.internet.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.AbstractTableModel;

// The E-mail Client.
public class EmailClient extends JFrame {

    // Message table's data model.
    private MessagesTableModel tableModel;
    // Table listing messages.
    private JTable table;
    // This the text area for displaying messages.
    private JTextArea messageTextArea;
    /* This is the split panel that holds the messages
    table and the message view panel. */
    private JSplitPane splitPane;
    // These are the buttons for managing the selected message.
    private JButton replyButton, forwardButton, deleteButton;
    // Currently selected message in table.
    private Message selectedMessage;
    // Flag for whether or not a message is being deleted.
    private boolean deleting;
    // This is the JavaMail session.
    private Session session;

    // Constructor for E-mail Client.
    public EmailClient() {
        // Set application title.
        setTitle("E-mail Client");

        // Set window size.
        setSize(640, 480);

        // Handle window closing events.
        addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {
                actionExit();
            }
        });

        // Setup file menu.
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        JMenuItem fileExitMenuItem = new JMenuItem("Exit",
                KeyEvent.VK_X);
        fileExitMenuItem.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                actionExit();
            }
        });
        fileMenu.add(fileExitMenuItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

        // Setup buttons panel.
        JPanel buttonPanel = new JPanel();
        JButton newButton = new JButton("New Message");
        newButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                actionNew();
            }
        });
        buttonPanel.add(newButton);

        // Setup messages table.
        tableModel = new MessagesTableModel();
        table = new JTable(tableModel);
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                tableSelectionChanged();
            }
        });
        // Allow only one row at a time to be selected.
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Setup E-mails panel.
        JPanel emailsPanel = new JPanel();
        emailsPanel.setBorder(
                BorderFactory.createTitledBorder("E-mails"));
        messageTextArea = new JTextArea();
        messageTextArea.setEditable(false);
        splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT,
                new JScrollPane(table), new JScrollPane(messageTextArea));
        emailsPanel.setLayout(new BorderLayout());
        emailsPanel.add(splitPane, BorderLayout.CENTER);

        // Setup buttons panel 2.
        JPanel buttonPanel2 = new JPanel();
        replyButton = new JButton("Reply");
        replyButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                actionReply();
            }
        });
        replyButton.setEnabled(false);
        buttonPanel2.add(replyButton);
        forwardButton = new JButton("Forward");
        forwardButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                actionForward();
            }
        });
        forwardButton.setEnabled(false);
        buttonPanel2.add(forwardButton);
        deleteButton = new JButton("Delete");
        deleteButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                actionDelete();
            }
        });
        deleteButton.setEnabled(false);
        buttonPanel2.add(deleteButton);

        // Add panels to display.
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(buttonPanel, BorderLayout.NORTH);
        getContentPane().add(emailsPanel, BorderLayout.CENTER);
        getContentPane().add(buttonPanel2, BorderLayout.SOUTH);
    }

    // Exit this program.
    private void actionExit() {
        System.exit(0);
    }

    // Create a new message.
    private void actionNew() {
        sendMessage(MessageDialog.NEW, null);
    }

    // Called when table row selection changes.
    private void tableSelectionChanged() {
        /* If not in the middle of deleting a message, set
        the selected message and display it. */
        if (!deleting) {
            selectedMessage =
                    tableModel.getMessage(table.getSelectedRow());
            showSelectedMessage();
            updateButtons();
        }
    }

    // Reply to a message.
    private void actionReply() {
        sendMessage(MessageDialog.REPLY, selectedMessage);
    }

    // Forward a message.
    private void actionForward() {
        sendMessage(MessageDialog.FORWARD, selectedMessage);
    }

    // Delete the selected message.
    private void actionDelete() {
        deleting = true;

        try {
            // Delete message from server.
            selectedMessage.setFlag(Flags.Flag.DELETED, true);
            Folder folder = selectedMessage.getFolder();
            folder.close(true);
            folder.open(Folder.READ_WRITE);
        } catch (Exception e) {
            showError("Unable to delete message.", false);
        }

        // Delete message from table.
        tableModel.deleteMessage(table.getSelectedRow());

        // Update GUI.
        messageTextArea.setText("");
        deleting = false;
        selectedMessage = null;
        updateButtons();
    }

    // Send the specified message.
    private void sendMessage(int type, Message message) {
        // Display message dialog to get message values.
        MessageDialog dialog;
        try {
            dialog = new MessageDialog(this, type, message);
            if (!dialog.display()) {
                // Return if dialog was cancelled.
                return;
            }
        } catch (Exception e) {
            showError("Unable to send message.", false);
            return;
        }

        try {
            // Create a new message with values from dialog.
            Message newMessage = new MimeMessage(session);
            newMessage.setFrom(new InternetAddress(dialog.getFrom()));
            newMessage.setRecipient(Message.RecipientType.TO,
                    new InternetAddress(dialog.getTo()));
            newMessage.setSubject(dialog.getSubject());
            newMessage.setSentDate(new Date());
            newMessage.setText(dialog.getContent());

            // Send new message.
            Transport.send(newMessage);
        } catch (Exception e) {
            showError("Unable to send message.", false);
        }
    }

    // Show the selected message in the content panel.
    private void showSelectedMessage() {
        // Show hour glass cursor while message is loaded.
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        try {
            messageTextArea.setText(
                    getMessageContent(selectedMessage));
            messageTextArea.setCaretPosition(0);
        } catch (Exception e) {
            showError("Unabled to load message.", false);
        } finally {
            // Return to default cursor.
            setCursor(Cursor.getDefaultCursor());
        }
    }

    /* Update each button's state based off of whether or not
    there is a message currently selected in the table. */
    private void updateButtons() {
        if (selectedMessage != null) {
            replyButton.setEnabled(true);
            forwardButton.setEnabled(true);
            deleteButton.setEnabled(true);
        } else {
            replyButton.setEnabled(false);
            forwardButton.setEnabled(false);
            deleteButton.setEnabled(false);
        }
    }

    // Show the application window on the screen.
    public void show() {
        super.show();

        // Update the split panel to be divided 50/50.
        splitPane.setDividerLocation(.5);
    }

    // Connect to e-mail server.
    public void connect() {
        // Display connect dialog.
        ConnectDialog dialog = new ConnectDialog(this);
        dialog.show();

        // Build connection URL from connect dialog settings.
        StringBuffer connectionUrl = new StringBuffer();
        connectionUrl.append(dialog.getType() + "://");
        connectionUrl.append(dialog.getUsername() + ":");
        connectionUrl.append(dialog.getPassword() + "@");
        connectionUrl.append(dialog.getServer() + "/");

        /* Display dialog stating that messages are
        currently being downloaded from server. */
        final DownloadingDialog downloadingDialog =
                new DownloadingDialog(this);
        SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                downloadingDialog.show();
            }
        });

        // Establish JavaMail session and connect to server.
        Store store = null;
        try {
            // Initialize JavaMail session with SMTP server.
            Properties props = new Properties();
            props.put("mail.smtp.host", dialog.getSmtpServer());
            session = Session.getDefaultInstance(props, null);

            // Connect to e-mail server.
            URLName urln = new URLName(connectionUrl.toString());
            store = session.getStore(urln);
            store.connect();
        } catch (Exception e) {
            // Close the downloading dialog.
            downloadingDialog.dispose();

            // Show error dialog.
            showError("Unable to connect.", true);
        }

        // Download message headers from server.
        try {
            // Open main "INBOX" folder.
            Folder folder = store.getFolder("INBOX");
            folder.open(Folder.READ_WRITE);

            // Get folder's list of messages.
            Message[] messages = folder.getMessages();

            // Retrieve message headers for each message in folder.
            FetchProfile profile = new FetchProfile();
            profile.add(FetchProfile.Item.ENVELOPE);
            folder.fetch(messages, profile);

            // Put messages in table.
            tableModel.setMessages(messages);
        } catch (Exception e) {
            // Close the downloading dialog.
            downloadingDialog.dispose();

            // Show error dialog.
            showError("Unable to download messages.", true);
        }

        // Close the downloading dialog.
        downloadingDialog.dispose();
    }

    // Show error dialog and exit afterwards if necessary.
    private void showError(String message, boolean exit) {
        JOptionPane.showMessageDialog(this, message, "Error",
                JOptionPane.ERROR_MESSAGE);
        if (exit) {
            System.exit(0);
        }
    }

    // Get a message's content.
    public static String getMessageContent(Message message)
            throws Exception {
        Object content = message.getContent();
        if (content instanceof Multipart) {
            StringBuffer messageContent = new StringBuffer();
            Multipart multipart = (Multipart) content;
            for (int i = 0; i < multipart.getCount(); i++) {
                Part part = (Part) multipart.getBodyPart(i);
                if (part.isMimeType("text/plain")) {
                    messageContent.append(part.getContent().toString());
                }
            }
            return messageContent.toString();
        } else {
            return content.toString();
        }
    }

    // Run the E-mail Client.
    public static void main(String[] args) {
        EmailClient client = new EmailClient();
        client.show();

        // Display connect dialog.
        client.connect();
    }
}

/* This class displays a dialog for entering e-mail
server connection settings. */
class ConnectDialog extends JDialog {

    // These are the e-mail server types.
    private static final String[] TYPES = {"pop3", "imap"};
    // Combo box for e-mail server types.
    private JComboBox typeComboBox;
    // Server, username and SMTP server text fields.
    private JTextField serverTextField, usernameTextField;
    private JTextField smtpServerTextField;
    // Password text field.
    private JPasswordField passwordField;

    // Constructor for dialog.
    public ConnectDialog(Frame parent) {
        // Call super constructor, specifying that dialog is modal.
        super(parent, true);

        // Set dialog title.
        setTitle("Connect");

        // Handle closing events.
        addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {
                actionCancel();
            }
        });

        // Setup settings panel.
        JPanel settingsPanel = new JPanel();
        settingsPanel.setBorder(
                BorderFactory.createTitledBorder("Connection Settings"));
        GridBagConstraints constraints;
        GridBagLayout layout = new GridBagLayout();
        settingsPanel.setLayout(layout);
        JLabel typeLabel = new JLabel("Type:");
        constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.EAST;
        constraints.insets = new Insets(5, 5, 0, 0);
        layout.setConstraints(typeLabel, constraints);
        settingsPanel.add(typeLabel);
        typeComboBox = new JComboBox(TYPES);
        constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.insets = new Insets(5, 5, 0, 5);
        constraints.weightx = 1.0D;
        layout.setConstraints(typeComboBox, constraints);
        settingsPanel.add(typeComboBox);
        JLabel serverLabel = new JLabel("Server:");
        constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.EAST;
        constraints.insets = new Insets(5, 5, 0, 0);
        layout.setConstraints(serverLabel, constraints);
        settingsPanel.add(serverLabel);
        serverTextField = new JTextField(25);
        constraints = new GridBagConstraints();
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.insets = new Insets(5, 5, 0, 5);
        constraints.weightx = 1.0D;
        layout.setConstraints(serverTextField, constraints);
        settingsPanel.add(serverTextField);
        JLabel usernameLabel = new JLabel("Username:");
        constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.EAST;
        constraints.insets = new Insets(5, 5, 0, 0);
        layout.setConstraints(usernameLabel, constraints);
        settingsPanel.add(usernameLabel);
        usernameTextField = new JTextField();
        constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.insets = new Insets(5, 5, 0, 5);
        constraints.weightx = 1.0D;
        layout.setConstraints(usernameTextField, constraints);
        settingsPanel.add(usernameTextField);
        JLabel passwordLabel = new JLabel("Password:");
        constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.EAST;
        constraints.insets = new Insets(5, 5, 5, 0);
        layout.setConstraints(passwordLabel, constraints);
        settingsPanel.add(passwordLabel);
        passwordField = new JPasswordField();
        constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.weightx = 1.0D;
        layout.setConstraints(passwordField, constraints);
        settingsPanel.add(passwordField);
        JLabel smtpServerLabel = new JLabel("SMTP Server:");
        constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.EAST;
        constraints.insets = new Insets(5, 5, 5, 0);
        layout.setConstraints(smtpServerLabel, constraints);
        settingsPanel.add(smtpServerLabel);
        smtpServerTextField = new JTextField(25);
        constraints = new GridBagConstraints();
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.insets = new Insets(5, 5, 5, 5);
        constraints.weightx = 1.0D;
        layout.setConstraints(smtpServerTextField, constraints);
        settingsPanel.add(smtpServerTextField);

        // Setup buttons panel.
        JPanel buttonsPanel = new JPanel();
        JButton connectButton = new JButton("Connect");
        connectButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                actionConnect();
            }
        });
        buttonsPanel.add(connectButton);
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                actionCancel();
            }
        });
        buttonsPanel.add(cancelButton);

        // Add panels to display.
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(settingsPanel, BorderLayout.CENTER);
        getContentPane().add(buttonsPanel, BorderLayout.SOUTH);

        // Size dialog to components.
        pack();

        // Center dialog over application.
        setLocationRelativeTo(parent);
    }

    // Validate connection settings and close dialog.
    private void actionConnect() {
        if (serverTextField.getText().trim().length() < 1
                || usernameTextField.getText().trim().length() < 1
                || passwordField.getPassword().length < 1
                || smtpServerTextField.getText().trim().length() < 1) {
            JOptionPane.showMessageDialog(this,
                    "One or more settings is missing.",
                    "Missing Setting(s)", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Close dialog.
        dispose();
    }

    // Cancel connecting and exit program.
    private void actionCancel() {
        System.exit(0);
    }

    // Get e-mail server type.
    public String getType() {
        return (String) typeComboBox.getSelectedItem();
    }

    // Get e-mail server.
    public String getServer() {
        return serverTextField.getText();
    }

    // Get e-mail username.
    public String getUsername() {
        return usernameTextField.getText();
    }

    // Get e-mail password.
    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    // Get e-mail SMTP server.
    public String getSmtpServer() {
        return smtpServerTextField.getText();
    }
}

// This class manages the e-mail table's data.
class MessagesTableModel extends AbstractTableModel {

    // These are the names for the table's columns.
    private static final String[] columnNames = {"Sender",
        "Subject", "Date"};
    // The table's list of messages.
    private ArrayList messageList = new ArrayList();

    // Sets the table's list of messages.
    public void setMessages(Message[] messages) {
        for (int i = messages.length - 1; i >= 0; i--) {
            messageList.add(messages[i]);
        }

        // Fire table data change notification to table.
        fireTableDataChanged();
    }

    // Get a message for the specified row.
    public Message getMessage(int row) {
        return (Message) messageList.get(row);
    }

    // Remove a message from the list.
    public void deleteMessage(int row) {
        messageList.remove(row);

        // Fire table row deletion notification to table.
        fireTableRowsDeleted(row, row);
    }

    // Get table's column count.
    public int getColumnCount() {
        return columnNames.length;
    }

    // Get a column's name.
    public String getColumnName(int col) {
        return columnNames[col];
    }

    // Get table's row count.
    public int getRowCount() {
        return messageList.size();
    }

    // Get value for a specific row and column combination.
    public Object getValueAt(int row, int col) {
        try {
            Message message = (Message) messageList.get(row);
            switch (col) {
                case 0: // Sender
                    Address[] senders = message.getFrom();
                    if (senders != null || senders.length > 0) {
                        return senders[0].toString();
                    } else {
                        return "[none]";
                    }
                case 1: // Subject
                    String subject = message.getSubject();
                    if (subject != null && subject.length() > 0) {
                        return subject;
                    } else {
                        return "[none]";
                    }
                case 2: // Date
                    Date date = message.getSentDate();
                    if (date != null) {
                        return date.toString();
                    } else {
                        return "[none]";
                    }
            }
        } catch (Exception e) {
            // Fail silently.
            return "";
        }

        return "";
    }
}

// This class displays the dialog used for creating messages.
class MessageDialog extends JDialog {

    // Dialog message identifiers.
    public static final int NEW = 0;
    public static final int REPLY = 1;
    public static final int FORWARD = 2;
    // Message from, to and subject text fields.
    private JTextField fromTextField, toTextField;
    private JTextField subjectTextField;
    // Message content text area.
    private JTextArea contentTextArea;
    // Flag specifying whether or not dialog was cancelled.
    private boolean cancelled;

    // Constructor for dialog.
    public MessageDialog(Frame parent, int type, Message message)
            throws Exception {
        // Call super constructor, specifying that dialog is modal.
        super(parent, true);

        /* Set dialog title and get message's "to", "subject"
        and "content" values based on message type. */
        String to = "", subject = "", content = "";
        switch (type) {
            // Reply message.
            case REPLY:
                setTitle("Reply To Message");

                // Get message "to" value
                Address[] senders = message.getFrom();
                if (senders != null || senders.length > 0) {
                    to = senders[0].toString();
                }
                to = message.getFrom()[0].toString();

                // Get message subject.
                subject = message.getSubject();
                if (subject != null && subject.length() > 0) {
                    subject = "RE: " + subject;
                } else {
                    subject = "RE:";
                }

                // Get message content and add "REPLIED TO" notation.
                content = "\n----------------- "
                        + "REPLIED TO MESSAGE"
                        + " -----------------\n"
                        + EmailClient.getMessageContent(message);
                break;

            // Forward message.
            case FORWARD:
                setTitle("Forward Message");

                // Get message subject.
                subject = message.getSubject();
                if (subject != null && subject.length() > 0) {
                    subject = "FWD: " + subject;
                } else {
                    subject = "FWD:";
                }

                // Get message content and add "FORWARDED" notation.
                content = "\n----------------- "
                        + "FORWARDED MESSAGE"
                        + " -----------------\n"
                        + EmailClient.getMessageContent(message);
                break;

            // New message.
            default:
                setTitle("New Message");
        }

        // Handle closing events.
        addWindowListener(new WindowAdapter() {

            public void windowClosing(WindowEvent e) {
                actionCancel();
            }
        });

        // Setup fields panel.
        JPanel fieldsPanel = new JPanel();
        GridBagConstraints constraints;
        GridBagLayout layout = new GridBagLayout();
        fieldsPanel.setLayout(layout);
        JLabel fromLabel = new JLabel("From:");
        constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.EAST;
        constraints.insets = new Insets(5, 5, 0, 0);
        layout.setConstraints(fromLabel, constraints);
        fieldsPanel.add(fromLabel);
        fromTextField = new JTextField();
        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.insets = new Insets(5, 5, 0, 0);
        layout.setConstraints(fromTextField, constraints);
        fieldsPanel.add(fromTextField);
        JLabel toLabel = new JLabel("To:");
        constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.EAST;
        constraints.insets = new Insets(5, 5, 0, 0);
        layout.setConstraints(toLabel, constraints);
        fieldsPanel.add(toLabel);
        toTextField = new JTextField(to);
        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.insets = new Insets(5, 5, 0, 0);
        constraints.weightx = 1.0D;
        layout.setConstraints(toTextField, constraints);
        fieldsPanel.add(toTextField);
        JLabel subjectLabel = new JLabel("Subject:");
        constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 0);
        layout.setConstraints(subjectLabel, constraints);
        fieldsPanel.add(subjectLabel);
        subjectTextField = new JTextField(subject);
        constraints = new GridBagConstraints();
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridwidth = GridBagConstraints.REMAINDER;
        constraints.insets = new Insets(5, 5, 5, 0);
        layout.setConstraints(subjectTextField, constraints);
        fieldsPanel.add(subjectTextField);

        // Setup content panel.
        JScrollPane contentPanel = new JScrollPane();
        contentTextArea = new JTextArea(content, 10, 50);
        contentPanel.setViewportView(contentTextArea);

        // Setup buttons panel.
        JPanel buttonsPanel = new JPanel();
        JButton sendButton = new JButton("Send");
        sendButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                actionSend();
            }
        });
        buttonsPanel.add(sendButton);
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                actionCancel();
            }
        });
        buttonsPanel.add(cancelButton);

        // Add panels to display.
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(fieldsPanel, BorderLayout.NORTH);
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        getContentPane().add(buttonsPanel, BorderLayout.SOUTH);

        // Size dialog to components.
        pack();

        // Center dialog over application.
        setLocationRelativeTo(parent);
    }

    // Validate message fields and close dialog.
    private void actionSend() {
        if (fromTextField.getText().trim().length() < 1
                || toTextField.getText().trim().length() < 1
                || subjectTextField.getText().trim().length() < 1
                || contentTextArea.getText().trim().length() < 1) {
            JOptionPane.showMessageDialog(this,
                    "One or more fields is missing.",
                    "Missing Field(s)", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Close dialog.
        dispose();
    }

    // Cancel creating this message and close dialog.
    private void actionCancel() {
        cancelled = true;

        // Close dialog.
        dispose();
    }

    // Show dialog.
    public boolean display() {
        show();

        // Return whether or not display was successful.
        return !cancelled;
    }

    // Get message's "From" field value.
    public String getFrom() {
        return fromTextField.getText();
    }

    // Get message's "To" field value.
    public String getTo() {
        return toTextField.getText();
    }

    // Get message's "Subject" field value.
    public String getSubject() {
        return subjectTextField.getText();
    }

    // Get message's "content" field value.
    public String getContent() {
        return contentTextArea.getText();
    }
}



/* This class displays a simple dialog instructing the user
   that messages are being downloaded. */
 class DownloadingDialog extends JDialog {

    // Constructor for dialog.
    public DownloadingDialog(Frame parent) {
        // Call super constructor, specifying that dialog is modal.
        super(parent, true);

        // Set dialog title.
        setTitle("E-mail Client");

        // Instruct window not to close when the "X" is clicked.
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        // Put a message with a nice border in this dialog.
        JPanel contentPane = new JPanel();
        contentPane.setBorder(
                BorderFactory.createEmptyBorder(5, 5, 5, 5));
        contentPane.add(new JLabel("Downloading messages..."));
        setContentPane(contentPane);

        // Size dialog to components.
        pack();

        // Center dialog over application.
        setLocationRelativeTo(parent);
    }
}

