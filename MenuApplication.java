import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class MenuApplication extends JFrame {
    private JTextArea textArea;
    private float hue;

    public MenuApplication() {
        super("Menu Application");

        textArea = new JTextArea();
        textArea.setEditable(false);

        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");

        JMenuItem showDateTimeItem = new JMenuItem("Show Date/Time");
        JMenuItem writeToFileItem = new JMenuItem("Write to File");
        JMenuItem changeBackgroundColorItem = new JMenuItem("Change Background Color");
        JMenuItem exitItem = new JMenuItem("Exit");

        showDateTimeItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showDateTime();
            }
        });

        writeToFileItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                writeToLogFile();
            }
        });

        changeBackgroundColorItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeBackgroundColor();
            }
        });

        exitItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        menu.add(showDateTimeItem);
        menu.add(writeToFileItem);
        menu.add(changeBackgroundColorItem);
        menu.add(exitItem);
        menuBar.add(menu);
        setJMenuBar(menuBar);

        setLayout(new BorderLayout());
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        changeBackgroundColor(); // Initialize background color
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void showDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateTime = dateFormat.format(new Date());
        textArea.setText(dateTime);
    }

    private void writeToLogFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("log.txt"))) {
            writer.write(textArea.getText());
            JOptionPane.showMessageDialog(this, "Contents written to log.txt");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void changeBackgroundColor() {
        hue = getRandomGreenHue();
        Color bgColor = Color.getHSBColor(hue, 0.8f, 0.8f);
        getContentPane().setBackground(bgColor);
        textArea.setBackground(bgColor);
        updateMenuText();
    }

    private float getRandomGreenHue() {
        Random random = new Random();
        return random.nextFloat() * 0.15f + 0.25f;
    }

    private void updateMenuText() {
        JMenuBar menuBar = getJMenuBar();
        JMenu menu = menuBar.getMenu(0);
        JMenuItem menuItem = menu.getItem(2);
        menuItem.setText("Change Background Color (Hue: " + String.format("%.2f", hue) + ")");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MenuApplication();
            }
        });
    }
}
