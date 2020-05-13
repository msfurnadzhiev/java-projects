import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CalculatorForm extends JFrame {


    public CalculatorForm() {
        JFrame frame = new JFrame("Calculator");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(580,450);

        JPanel panel = new JPanel(null);

        JLabel history = new JLabel();
        history.setBounds(20,20,540,160);
        history.setFont(new Font(null, Font.PLAIN, 30));
        Border border = BorderFactory.createLineBorder(Color.decode("#4e9843"), 1);
        Border margin = new EmptyBorder(10,10,10,10);
        history.setBorder(new CompoundBorder(border, margin));
        panel.add(history);

        JTextField expression = new JTextField();
        expression.setBounds(20,200,540,40);
        expression.setMargin(new Insets(0,5,0,0));
        panel.add(expression);

        //Buttons for digits from "1" to "9"
        JButton[][] buttons = new JButton[3][3];
        int buttonName = 1;
        for(int i=0; i<3; i++) {
            for(int j=0; j<3; j++) {
                addExpressionButton(20+j*100+j*10, 250+i*30+i*10, 100, 30, String.valueOf(buttonName), panel, expression);
                buttonName++;
            }
        }

        addExpressionButton(20,370,100,30, "0", panel, expression);
        addExpressionButton(130,370,100,30, "(", panel, expression);
        addExpressionButton(240,370,100,30, ")", panel, expression);
        addExpressionButton(350,290,100,30, "+", panel, expression);
        addExpressionButton(460,290,100,30, "-", panel, expression);
        addExpressionButton(350,330,100,30, "*", panel, expression);
        addExpressionButton(460,330,100,30, "/", panel, expression);
        addExpressionButton(350,370,100,30, "^", panel, expression);


        addResetButton(460,250,100,30, "C", panel, expression);
        addDeleteLastSymbolButton(350,250,100,30, "CE", panel, expression);
        addSolveButton(460,370,100,30, "=", panel, expression, history);

        frame.add(panel);
        frame.setResizable(false);
        frame.setVisible(true);
    }

    private static void addExpressionButton(int x, int y, int width, int height, String name, JPanel panel,JTextField expression) {
        JButton button = new JButton(name);
        button.setName(name);
        button.setBounds(x, y, width ,height);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                expression.setText(expression.getText() + button.getName());
            }
        });
        panel.add(button);
    }

    private static void addResetButton(int x, int y, int width, int height, String name, JPanel panel, JTextField expression) {
        JButton button = new JButton(name);
        button.setName(name);
        button.setBounds(x, y, width ,height);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                expression.setText("");
            }
        });
        panel.add(button);
    }

    private static void addDeleteLastSymbolButton(int x, int y, int width, int height, String name, JPanel panel, JTextField expression) {
        JButton button = new JButton(name);
        button.setName(name);
        button.setBounds(x, y, width ,height);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                expression.setText(expression.getText().substring(0,expression.getText().length()-1));
            }
        });
        panel.add(button);
    }

    private static void addSolveButton(int x, int y, int width, int height, String name, JPanel panel, JTextField expression, JLabel history) {
        JButton button = new JButton(name);
        button.setName(name);
        button.setBounds(x, y, width ,height);
        button.setBackground(Color.decode("#4e9843"));
        button.setForeground(Color.WHITE);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Solve solve = new Solve(expression.getText());
                if(solve.checkExpression(solve.getExpression())) {
                    history.setForeground(Color.BLACK);
                    solve.solveExpression(solve.getExpression());
                    history.setText(expression.getText() + "=" + solve.getResult());
                }
                else {
                    history.setForeground(Color.RED);
                    history.setText("Wrong expression!");
                }

            }
        });
        panel.add(button);
    }

}
