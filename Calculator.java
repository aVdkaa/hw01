import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JOptionPane;

class IntegerTextField {
  private Panel numberPanel = new Panel();
  private TextField field = new TextField(10);

  public IntegerTextField(Font font, int num) {
    Label label = new Label("Number " + num + ":");
    label.setFont(font);
    this.field.setFont(font);

    this.numberPanel.setLayout(new FlowLayout());
    this.numberPanel.add(label);
    this.numberPanel.add(this.field);
  }

  public Panel getPanel() {
    return this.numberPanel;
  }

  public int getFieldValue() {
    try {
      return Integer.parseInt(this.field.getText());
    } catch (NumberFormatException nfe) {
      return 0;
    }
  }
}

public class Calculator extends Frame {
  private Panel TextFieldsPanel = new Panel();
  private Panel ButtonsPanel = new Panel();
  private Font font = new Font("Arial", Font.PLAIN, 13);

  private String operation;
  private IntegerTextField[] TextFields = new IntegerTextField[3];

  private int result = 0;

  public static void main(String[] args) {
    Calculator mainWindow = new Calculator();
    mainWindow.init();
  }

  public void init() {
    setLayout(new BorderLayout());
    TextFieldsPanel.setLayout(new GridLayout(3, 1));
    ButtonsPanel.setLayout(new GridLayout(1, 4));
    this.renderButtons();
    Button resultButton = new Button("Calculate");

    TextField resultField = new TextField(20);
    resultField.setEditable(false);
    resultField.setFont(font);

    resultButton = new Button("Calculate");
    resultButton.setBackground(Color.orange);
    resultButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        result = TextFields[0].getFieldValue();
        for (int i = 1; i < TextFields.length; i++) {
          IntegerTextField field = TextFields[i];
          if (operation == "+") {
            result += field.getFieldValue();
          } else if (operation == "-") {
            result -= field.getFieldValue();
          } else if (operation == "*") {
            result *= field.getFieldValue();
          }

          resultField.setText(result + "");
        }
      }
    });

    ButtonsPanel.add(resultButton);

    for (int i = 1; i < 4; i++) {
      IntegerTextField field = new IntegerTextField(this.font, i);
      this.TextFields[i - 1] = field;
      TextFieldsPanel.add(field.getPanel());
    }

    add(TextFieldsPanel, BorderLayout.PAGE_START);
    add(ButtonsPanel, BorderLayout.CENTER);
    add(resultField, BorderLayout.PAGE_END);

    setTitle("Calculator HW-1");
    setSize(300, 300);
    setVisible(true);

    addWindowListener(new WindowAdapter() {
      public void windowClosing(WindowEvent e) {
        confirmExit();
      }
    });
  }

  private void renderButtons() {
    String[] operations = { "+", "-", "*" };

    for (String operation : operations) {
      Button button = new Button(operation);
      button.setBackground(Color.RED);
      button.addActionListener(new ActionListener() {
        public void actionPerformed(ActionEvent e) {
          Calculator.this.operation = operation;
        }
      });
      this.ButtonsPanel.add(button);
    }
  }

  private void confirmExit() {
    int isConfirmed = JOptionPane.showConfirmDialog(this, "Exit?", "Exit Window", JOptionPane.YES_NO_OPTION);
    if (isConfirmed == JOptionPane.YES_OPTION) {
      this.dispose();
    }
  }
}
