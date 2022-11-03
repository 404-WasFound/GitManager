package main;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class Frame extends JFrame {

    private JButton setPathButton;
    private JTextField pathTextField;

    private JButton initButton;
    private JTextField initTextField;

    private JButton addButton;

    private JButton commitButton;
    private JButton pushButton;
    private JTextField commitTextField;

    private JButton pullButton;
    private JButton fetchButton;

    private String path;
    private String url;
    private boolean gotPath = false;

    Frame() {

        int spacing = 0;
        int length = 300;
        List<JButton> buttons = new ArrayList<JButton>() {};

        setPathButton = new JButton();
        setPathButton.setBounds(0, 30, length, 30);
        setPathButton.addActionListener(e -> setPathButtonAction());
        setPathButton.setText("Set Path");
        buttons.add(setPathButton);

        pathTextField = new JTextField(80);
        pathTextField.setBounds(0, 0, length, 30);
        pathTextField.setText("Enter Path");
        pathTextField.setOpaque(true);
        pathTextField.setBackground(new Color(0x010409));
        pathTextField.setForeground(new Color(0x8b949e));

        spacing += 90;

        initButton = new JButton();
        initButton.setBounds(0, spacing + 30, length, 30);
        initButton.addActionListener(e -> initButtonAction());
        initButton.setText("Init git repo");
        buttons.add(initButton);

        initTextField = new JTextField(80);
        initTextField.setBounds(0, spacing + 0, length, 30);
        initTextField.setText("Enter Git Repo Link");
        initTextField.setOpaque(true);
        initTextField.setBackground(new Color(0x010409));
        initTextField.setForeground(new Color(0x8b949e));

        spacing += 90;

        addButton = new JButton();
        addButton.setBounds(0, spacing + 30, length / 3, 30);
        addButton.addActionListener(e -> addButtonAction());
        addButton.setText("Add");
        buttons.add(addButton);

        commitButton = new JButton();
        commitButton.setBounds(length / 3, spacing + 30, length / 3, 30);
        commitButton.addActionListener(e -> commitButtonAction());
        commitButton.setText("Commit");
        buttons.add(commitButton);

        pushButton = new JButton();
        pushButton.setBounds((length / 3) * 2, spacing + 30, length / 3, 30);
        pushButton.addActionListener(e -> pushButtonAction());
        pushButton.setText("Push");
        buttons.add(pushButton);

        commitTextField = new JTextField(80);
        commitTextField.setBounds(0, spacing + 0, length, 30);
        commitTextField.setText("Enter Commit Message");
        commitTextField.setOpaque(true);
        commitTextField.setBackground(new Color(0x010409));
        commitTextField.setForeground(new Color(0x8b949e));

        spacing += 90;

        pullButton = new JButton();
        pullButton.setBounds(0, spacing + 0, length, 30);
        pullButton.addActionListener(e -> pullButtonAction());
        pullButton.setText("Pull");
        buttons.add(pullButton);

        fetchButton = new JButton();
        fetchButton.setBounds(0, spacing + 30, length, 30);
        fetchButton.addActionListener(e -> fetchButtonAction());
        fetchButton.setText("Fetch");
        buttons.add(fetchButton);


        for (JButton b : buttons) {

            b.setOpaque(true);
            b.setBackground(new Color(0x1f6feb));
            b.setForeground(new Color(0xc9d1d9));

        }


        this.setTitle("Git Manager");
        this.setSize(length + 14, 500);
        this.getContentPane().setBackground(new Color(0x0d1117));
        this.setLayout(null);
        this.setFocusable(true);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        this.add(setPathButton);
        this.add(pathTextField);
        this.add(initButton);
        this.add(initTextField);
        this.add(addButton);
        this.add(commitButton);
        this.add(pushButton);
        this.add(commitTextField);
        this.add(pullButton);
        this.add(fetchButton);

        JFrame frame = this;

        this.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent windowEvent) {

                int close = JOptionPane.showConfirmDialog(frame, 
                "Are you sure you want to close this window?", "Close Window?", 
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

                if (close == JOptionPane.YES_OPTION) {

                    System.exit(0);

                }

            }

        });

    }



    private void initButtonAction() {
        
        if (initTextField.getText() != "") {

            runCommand("git init && git remote add origin " + initTextField.getText());

        }
        
    }



    private void commitButtonAction() {
        
        if (commitTextField.getText() != "") {

            runCommand("git commit -m \"" + commitTextField.getText() + "\"");
            return;

        }

        runCommand("git commit -m \"Empty commit message\"");
        
    }



    private void fetchButtonAction() {
        
        runCommand("git fetch origin main");
        
    }



    private void pullButtonAction() {
        
        runCommand("git pull origin main");

    }



    private void addButtonAction() {
        
        runCommand("git add .");

    }



    private void setPathButtonAction() {

        path = pathTextField.getText().toString().replaceAll("\\\\", "/");
        this.setTitle(path);
        gotPath = true;

    }



    private void pushButtonAction() {

        runCommand("git push -u -f origin main");

    }



    private void runCommand(String command) {

        ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", "cd \"" + path + "\" && " + command);

        try {

            builder.start();

        } catch (IOException e) {
            
            e.printStackTrace();

        }

    }

}

class RoundedBorder implements Border {

    private int radius;

    RoundedBorder(int radius) {

        this.radius = radius;

    }



    public boolean isBorderOpaque() {

        return true;

    }



    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {

        g.drawRoundRect(x, y, width-1, height-1, radius, radius);
        
    }



    @Override
    public Insets getBorderInsets(Component c) {

        return new Insets(this.radius+1, this.radius+1, this.radius+2, this.radius);
        
    }

}