import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.event.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;


public class SeriesDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonCancel;
    private JTextField nInput;
    private JRadioButton linerRadioButton;
    private JRadioButton exponentialRadioButton;
    private JTextField dQInput;
    private JTextField a1b1Input;
    private JTextArea output;
    private JButton saveButton;
    private Series series;
    private ButtonGroup radioButtonGroup;
    private String prevN, prevFirst, prevDelta;

    class InitData {
        int n;
        double first;
        double diff;
        static final String paramErrorMessage = "Check your params.\nN >= 0, numeric \nA1,B1 must be numeric \nD,Q should be numeric";;

        InitData(String nStr, String firstStr, String diffStr) throws IllegalArgumentException {
            this.n = 0;
            this.first = 1;
            this.diff = 1;

            String exceptionString = "";
            if (nStr != null && !nStr.trim().isEmpty() && isPositiveInt(nStr)) {
                n = Integer.parseInt(nStr);
            } else {
                if (!isPositiveInt(nStr))
                    throw new IllegalArgumentException(paramErrorMessage);

            }
            if (firstStr != null && !firstStr.trim().isEmpty() && isDouble(firstStr)) {
                first = Double.parseDouble(firstStr);
            } else {
                if (!isDouble(firstStr))
                    throw new IllegalArgumentException(paramErrorMessage);
            }
            if (diffStr != null && !diffStr.trim().isEmpty() && isDouble(diffStr)) {
                diff = Double.parseDouble(diffStr);
            } else {
                if (!isDouble(diffStr))
                    throw new IllegalArgumentException(paramErrorMessage);
            }

            if (!exceptionString.equals(""))
                throw new IllegalArgumentException(exceptionString);
        }

        private boolean isPositiveInt(String number) {
            int n = -1;
            try {
                n = Integer.parseInt(number);
            } catch (IllegalArgumentException e) {
                return false;
            }
            return n >= 0;
        }

        private boolean isDouble(String number) {
            try {
                Double.parseDouble(number);
            } catch (IllegalArgumentException e) {
                return false;
            }
            return true;
        }
    }


    public SeriesDialog() {
        setContentPane(contentPane);
        setModal(true);

        radioButtonGroup = new ButtonGroup();
        radioButtonGroup.add(linerRadioButton);
        radioButtonGroup.add(exponentialRadioButton);

        buttonCancel.addActionListener(e -> onCancel());

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(e -> onCancel(), KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        ActionListener listener = e -> {
            JRadioButton toHandle = (JRadioButton) e.getSource();
            if (toHandle.getName().equals("liner"))
                onLiner();
            else if (toHandle.getName().equals("exp"))
                onExp();
        };
        nInput.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                reactParamsChange();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                reactParamsChange();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                reactParamsChange();
            }
        });
        a1b1Input.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                reactParamsChange();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                reactParamsChange();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                reactParamsChange();
            }
        });
        dQInput.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                reactParamsChange();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                reactParamsChange();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                reactParamsChange();
            }
        });

        linerRadioButton.setName("liner");
        exponentialRadioButton.setName("exp");
        exponentialRadioButton.setActionCommand("exp");
        linerRadioButton.setActionCommand("liner");
        linerRadioButton.addActionListener(listener);
        exponentialRadioButton.addActionListener(listener);

        saveButton.addActionListener(e -> {

            JFileChooser fileChooser = new JFileChooser();
            int ret = fileChooser.showSaveDialog(SeriesDialog.this);
            if (ret == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                String absolutePath = file.getAbsolutePath();
                if (!absolutePath.endsWith(".txt"))
                    file = new File(absolutePath + ".txt");

                if (!file.exists())
                    try {
                        file.createNewFile();
                        JOptionPane.showMessageDialog(null, "No file exists in directory. Created");
                    } catch (IOException e1) {
                        JOptionPane.showMessageDialog(null, e1.getMessage());
                        return;
                    }

                try {
                    series.saveToFile(file.getAbsolutePath());
                    JOptionPane.showMessageDialog(null, "Saved");
                } catch (IOException e1) {
                    JOptionPane.showMessageDialog(null, e1.getMessage());
                }
            }
        });
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }

    private void onExp() {
        try {
            InitData initData = new InitData(nInput.getText(), a1b1Input.getText(), dQInput.getText());
            series = new Exponential(initData.first, initData.diff, initData.n);
            updateOutputState();
        } catch (IllegalArgumentException e) {
            if (output.getText().equals("") || output.getText() == null || Character.isDigit(output.getText().charAt(0)))
                output.setText(e.getMessage() + "\n" + output.getText());
        }
    }

    private void onLiner() {
        try {
            InitData initData = new InitData(nInput.getText(), a1b1Input.getText(), dQInput.getText());
            series = new Liner(initData.first, initData.diff, initData.n);
            updateOutputState();
        } catch (IllegalArgumentException e) {
            if (output.getText().equals("") || output.getText() == null || Character.isDigit(output.getText().charAt(0)))
                output.setText(e.getMessage() + "\n" + output.getText());
        }
    }

    private void updateOutputState() {
        output.setText(series.toString());
    }

    private void reactParamsChange() {
        for (Enumeration<AbstractButton> buttons = radioButtonGroup.getElements(); buttons.hasMoreElements(); ) {
            AbstractButton currentButton = buttons.nextElement();
            if (currentButton.isSelected()) {
                switch (currentButton.getActionCommand()) {
                    case "liner":
                        onLiner();
                        break;
                    case "exp":
                        onExp();
                        break;
                }
            }
        }
    }

    public static void main(String[] args) {
        SeriesDialog dialog = new SeriesDialog();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
