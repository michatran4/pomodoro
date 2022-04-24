import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TimerGUI {
    public TimerGUI() {
        addTimer();
    }

    public static void main(String[] args) {
        new TimerGUI();
    }

    private void setProperties(JComponent component, Font font) { // mostly for JLabels
        component.setForeground(Color.WHITE);
        component.setBackground(new Color(35, 35, 35));
        component.setFont(font);
        if (component instanceof JLabel) {
            ((JLabel) component).setHorizontalAlignment(SwingConstants.CENTER);
        }
    }

    private void setProperties(JButton button, Font font, Dimension size) { // for JButtons
        setProperties(button, font);
        button.setPreferredSize(size);
        button.setFocusPainted(false);
    }

    private void setProperties(JFrame frame, int[] dimensions, LayoutManager layoutManager, boolean visible) { // for JFrames
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setBackground(new Color(35, 35, 35));
        frame.setSize(dimensions[0], dimensions[1]);
        frame.setLayout(layoutManager);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setVisible(visible);
    }

    /*
     * Adds the timer page.
     */
    private void addTimer() {
        JFrame timerPage = new JFrame("Timer");
        setProperties(timerPage, new int[]{500, 350}, new FlowLayout(), true);
        JLabel time = new JLabel("30:00");
        setProperties(time, new Font("Serif", Font.PLAIN, 150));
        timerPage.add(time);

        JPanel buttons = new JPanel();
        JButton toggle = new JButton("Start");
        setProperties(toggle, new Font("Serif", Font.PLAIN, 20), new Dimension(100, 100));
        JButton reset = new JButton("Reset");
        setProperties(reset, new Font("Serif", Font.PLAIN, 20), new Dimension(100, 100));
        buttons.add(toggle);
        buttons.add(reset);
        timerPage.add(buttons);

        Pomodoro pomodoro = new Pomodoro();
        time.setText(pomodoro.toString());
        toggle.addActionListener((ActionEvent a) -> { // start/stop the timer
            pomodoro.toggleRunning();
            if (pomodoro.isRunning()) { // only update text while it's running
                toggle.setText("Pause");
                Timer timer = new Timer(1000, null); // create a new timer each time so no conflicts
                // updating at the same rate as the timer ticking down shouldn't matter too much
                ActionListener taskPerformer = actionEvent -> { // text updater
                    if (pomodoro.isRunning()) {
                        time.setForeground(pomodoro.isWorking() ? Color.WHITE : Color.GREEN);
                        time.setText(pomodoro.toString());
                    }
                    else {
                        timer.stop(); // kill the timer
                    }
                };
                timer.addActionListener(taskPerformer);
                timer.start();
            }
            else {
                toggle.setText("Start");
            }
        });
        reset.addActionListener((ActionEvent a) -> { // resets the timer, to work mode
            pomodoro.reset();
            time.setForeground(Color.WHITE);
            time.setText(pomodoro.toString());
            toggle.setText("Start");
        });
    }
}
