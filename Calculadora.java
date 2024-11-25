import java.awt.*;
import java.awt.event.*;
import javax.swing.JOptionPane;

public class Calculadora extends Frame implements ActionListener {
    TextField display;
    Panel buttonPanel;
    Button[] buttons;
    String[] buttonLabels = {
        "MC", "MR", "MS", "M+", "M-",
        "<-", "CE", "C", "+", "-",
        "7", "8", "9", "*", "/",
        "4", "5", "6", "1/x", "=",
        "1", "2", "3", "0", "."
    };

    double num1 = 0, num2 = 0, result = 0;
    char operator;

    public Calculadora() {
        setTitle("Calculadora");
        setSize(400, 500);
        setLayout(new BorderLayout());
        setResizable(false);

        display = new TextField();
        display.setFont(new Font("Arial", Font.BOLD, 25));
        display.setEditable(false);
        add(display, BorderLayout.NORTH);

        buttons = new Button[buttonLabels.length];
        buttonPanel = new Panel();
        buttonPanel.setLayout(new GridLayout(5, 5, 5, 5));
        for (int i = 0; i < buttonLabels.length; i++) {
            buttons[i] = new Button(buttonLabels[i]);
            buttons[i].setFont(new Font("Arial", Font.PLAIN, 18));
            buttons[i].addActionListener(this);
            buttonPanel.add(buttons[i]);
        }
        add(buttonPanel, BorderLayout.CENTER);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        if (command.charAt(0) >= '0' && command.charAt(0) <= '9') {
            display.setText(display.getText() + command);
        } else if (command.equals("C")) {
            display.setText("");
            num1 = num2 = result = 0;
            JOptionPane.showMessageDialog(this, "Display limpo!", "Info", JOptionPane.INFORMATION_MESSAGE);
        } else if (command.equals("=")) {
            try {
                num2 = Double.parseDouble(display.getText());
                switch (operator) {
                    case '+':
                        result = num1 + num2;
                        break;
                    case '-':
                        result = num1 - num2;
                        break;
                    case '*':
                        result = num1 * num2;
                        break;
                    case '/':
                        if (num2 == 0) {
                            JOptionPane.showMessageDialog(this, "Erro: Divisão por zero!", "Erro", JOptionPane.ERROR_MESSAGE);
                            return;
                        }
                        result = num1 / num2;
                        break;
                }
                display.setText(String.valueOf(result));
                JOptionPane.showMessageDialog(this, "Resultado: " + result, "Resultado", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                display.setText("Erro");
                JOptionPane.showMessageDialog(this, "Entrada inválida!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } else if (command.equals("+") || command.equals("-") || command.equals("*") || command.equals("/")) {
            try {
                num1 = Double.parseDouble(display.getText());
                operator = command.charAt(0);
                display.setText("");
            } catch (Exception ex) {
                display.setText("Erro");
                JOptionPane.showMessageDialog(this, "Entrada inválida!", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        } else if (command.equals("<-")) {
            String currentText = display.getText();
            if (currentText.length() > 0) {
                display.setText(currentText.substring(0, currentText.length() - 1));
            }
        } else if (command.equals(".")) {
            if (!display.getText().contains(".")) {
                display.setText(display.getText() + ".");
            }
        }
    }

    public static void main(String[] args) {
        Calculadora calc = new Calculadora();
        calc.setVisible(true);
    }
}
