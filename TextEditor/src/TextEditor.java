import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class TextEditor extends JFrame implements ActionListener {

    private JTextArea textArea;
    private JMenuBar menuBar;
    private JMenu fileMenu, editMenu, helpMenu;
    private JMenuItem newMenuItem, openMenuItem, saveMenuItem, saveAsMenuItem, exitMenuItem;
    private JMenuItem cutMenuItem, copyMenuItem, pasteMenuItem, selectAllMenuItem;
    private JMenuItem aboutMenuItem;
    private File currentFile;

    public TextEditor() {

        setTitle("Notepad by Kuunda Simuchimba");
        setSize(800, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        textArea = new JTextArea();
        JScrollPane scrollPane = new JScrollPane(textArea);
        add(scrollPane);
        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        newMenuItem = new JMenuItem("New");
        openMenuItem = new JMenuItem("Open");
        saveMenuItem = new JMenuItem("Save");
        saveAsMenuItem = new JMenuItem("Save As");
        exitMenuItem = new JMenuItem("Exit");

        fileMenu.add(newMenuItem);
        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.add(saveAsMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(exitMenuItem);

        editMenu = new JMenu("Edit");
        cutMenuItem = new JMenuItem("Cut");
        copyMenuItem = new JMenuItem("Copy");
        pasteMenuItem = new JMenuItem("Paste");
        selectAllMenuItem = new JMenuItem("Select All");

        editMenu.add(cutMenuItem);
        editMenu.add(copyMenuItem);
        editMenu.add(pasteMenuItem);
        editMenu.addSeparator();
        editMenu.add(selectAllMenuItem);

        helpMenu = new JMenu("Help");
        aboutMenuItem = new JMenuItem("About");

        helpMenu.add(aboutMenuItem);

        menuBar.add(fileMenu);
        menuBar.add(editMenu);
        menuBar.add(helpMenu);

        setJMenuBar(menuBar);

        newMenuItem.addActionListener(this);
        openMenuItem.addActionListener(this);
        saveMenuItem.addActionListener(this);
        saveAsMenuItem.addActionListener(this);
        exitMenuItem.addActionListener(this);
        cutMenuItem.addActionListener(this);
        copyMenuItem.addActionListener(this);
        pasteMenuItem.addActionListener(this);
        selectAllMenuItem.addActionListener(this);
        aboutMenuItem.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == newMenuItem) {
            newFile();
        } else if (e.getSource() == openMenuItem) {
            openFile();
        } else if (e.getSource() == saveMenuItem) {
            saveFile();
        } else if (e.getSource() == saveAsMenuItem) {
            saveFileAs();
        } else if (e.getSource() == exitMenuItem) {
            System.exit(0);
        } else if (e.getSource() == cutMenuItem) {
            textArea.cut();
        } else if (e.getSource() == copyMenuItem) {
            textArea.copy();
        } else if (e.getSource() == pasteMenuItem) {
            textArea.paste();
        } else if (e.getSource() == selectAllMenuItem) {
            textArea.selectAll();
        } else if (e.getSource() == aboutMenuItem) {
            JOptionPane.showMessageDialog(this, "Notepad Application designed by Kuunda Simuchimba", "About", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void newFile() {

        currentFile = null;
        textArea.setText("");
        setTitle("Notepad");
    }

    private void openFile() {

        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            currentFile = fileChooser.getSelectedFile();
            try {
                BufferedReader reader = new BufferedReader(new FileReader(currentFile));
                textArea.read(reader, null);
                reader.close();
                setTitle("Notepad - " + currentFile.getName());
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error opening file", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void saveFile() {

        if (currentFile != null) {
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(currentFile));
                textArea.write(writer);
                writer.close();
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error saving file", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            saveFileAs();
        }
    }

    private void saveFileAs() {

        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            currentFile = fileChooser.getSelectedFile();
            saveFile();
            setTitle("Notepad - " + currentFile.getName());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TextEditor notepad = new TextEditor();

            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            int x = (screenSize.width - notepad.getWidth()) / 2;
            int y = (screenSize.height - notepad.getHeight()) / 2;
            notepad.setLocation(x, y);

            notepad.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            notepad.setResizable(false);
            notepad.setVisible(true);
        });
    }
}