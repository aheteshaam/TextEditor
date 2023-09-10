import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import javax.swing.JFileChooser;

public class TextEditor implements ActionListener {
    private static int windowCount = 0;
    private JFrame frame;
    private JMenuBar menubar;
    private JMenu file, edit;
    private JMenuItem newFile, openFile, saveFile, newWindow; // Added newWindow JMenuItem
    private JMenuItem cut, copy, paste, selectAll, close;
    private JTextArea textArea;

    TextEditor() {
        windowCount++;
        frame = new JFrame("Text Editor - " + windowCount);
        menubar = new JMenuBar();
        file = new JMenu("FILE");
        edit = new JMenu("EDIT");

        newWindow = new JMenuItem("NEW WINDOW"); // Added newWindow menu item
        openFile = new JMenuItem("OPEN FILE");
        saveFile = new JMenuItem("SAVE FILE");


        newWindow.addActionListener(this); // Add action listener for newWindow
        openFile.addActionListener(this);
        saveFile.addActionListener(this);


        cut = new JMenuItem("CUT");
        copy = new JMenuItem("COPY");
        paste = new JMenuItem("PASTE");
        selectAll = new JMenuItem("SELECT ALL");
        close = new JMenuItem("CLOSE");

        cut.addActionListener(this);
        copy.addActionListener(this);
        paste.addActionListener(this);
        selectAll.addActionListener(this);
        close.addActionListener(this);

        file.add(newWindow); // Add newWindow to the FILE menu
        file.add(openFile);
        file.add(saveFile);


        edit.add(cut);
        edit.add(copy);
        edit.add(paste);
        edit.add(selectAll);
        edit.add(close);

        menubar.add(file);
        menubar.add(edit);

        frame.setJMenuBar(menubar);

        // Create a JScrollPane and set the JTextArea as its viewport
        JScrollPane scrollPane = new JScrollPane();
        textArea = new JTextArea();
        scrollPane.setViewportView(textArea);

        // Add a border to the JTextArea
        Border border = BorderFactory.createLineBorder(Color.BLACK);
        textArea.setBorder(BorderFactory.createCompoundBorder(border,
                BorderFactory.createEmptyBorder(5, 5, 5, 5))); // Add padding

        frame.add(scrollPane, BorderLayout.CENTER); // Use BorderLayout for the content

        frame.setBounds(150, 150, 500, 500);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == cut) {
            textArea.cut();
        }
        if (ae.getSource() == copy) {
            textArea.copy();
        }
        if (ae.getSource() == paste) {
            textArea.paste();
        }
        if (ae.getSource() == selectAll) {
            textArea.selectAll();
        }
        if (ae.getSource() == close) {
            frame.dispose();
            windowCount--;
        }
        if (ae.getSource() == openFile) {
            JFileChooser fileChooser = new JFileChooser("C:");
            int chooseOption = fileChooser.showOpenDialog(null);
            if (chooseOption == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                String filePath = file.getPath();
                try {
                    FileReader fileReader = new FileReader(filePath);
                    BufferedReader bufferedReader = new BufferedReader(fileReader);
                    String intermediate;
                    StringBuilder output = new StringBuilder();
                    while ((intermediate = bufferedReader.readLine()) != null) {
                        output.append(intermediate).append("\n");
                    }
                    textArea.setText(output.toString());
                } catch (IOException fileNotFoundException) {
                    JOptionPane.showMessageDialog(frame, "Error reading the file.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        if (ae.getSource() == saveFile) {
            JFileChooser fileChooser = new JFileChooser("C:");
            int chooseOption = fileChooser.showSaveDialog(null);
            if (chooseOption == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                String filePath = file.getPath();
                try {
                    FileWriter fileWriter = new FileWriter(filePath);
                    fileWriter.write(textArea.getText());
                    fileWriter.close();
                } catch (IOException fileNotFoundException) {
                    JOptionPane.showMessageDialog(frame, "Error saving the file.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
        if (ae.getSource() == newWindow) {
            new TextEditor(); // Open a new instance of the text editor
        }
    }

    public static void main(String[] args) {
        new TextEditor(); // Open the first instance of the text editor
    }
}
