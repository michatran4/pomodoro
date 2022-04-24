import javax.swing.*;
import java.awt.event.ActionListener;

public class Pomodoro {
    public final int WORK = 1800; // 1800 seconds divided by 60 is 30 minutes working
    public final int BREAK = 300; // 300 seconds divided by 60 is 5 minutes break
    private int timeLeft;
    private boolean working;
    private boolean running;
    private Timer timer;

    /**
     * Instantiates a new Pomodoro timer.
     */
    public Pomodoro() {
        working = true;
        running = false;
        timeLeft = WORK;
    }

    /**
     * Toggle the timer.
     * The timer switches between working and break after the player's prompt.
     */
    public void toggleRunning() {
        running = !running;
        if (running) {
            ActionListener taskPerformer = actionEvent -> {
                if (running) { // safety check, margin of 1 second
                    timeLeft--; // decrement time left every second
                    if (timeLeft == 0) { // time should stop at 0, not less than 0, because that adds an extra second
                        JOptionPane.showConfirmDialog(null, "Time's up.", "Pomodoro", JOptionPane.OK_CANCEL_OPTION);
                        working = !working; // switch modes
                        timeLeft = working ? WORK + 1 : BREAK + 1; // display in the toString properly when modes are switched
                    }
                }
            };
            timer = new Timer(1000, taskPerformer); // loop every second
            timer.start();
        }
        else {
            timer.stop();
        }
    }

    /**
     * @return if the timer is running
     */
    public boolean isRunning() {
        return running;
    }

    /**
     * @return if the timer is in the working mode
     */
    public boolean isWorking() {
        return working;
    }

    /**
     * Reset the timer, returning to work mode.
     */
    public void reset() {
        timeLeft = WORK;
        working = true;
        running = false;
        timer.stop();
    }

    /**
     * Format a number to make it double digits.
     *
     * @param num the number to format, either minutes or seconds
     * @return double digit format for a clock
     */
    private String addZeros(int num) {
        return ((num < 10 ? "0" : "") + num);
    }

    /**
     * @return the time left in MM:SS format.
     */
    public String toString() {
        if (timeLeft < 0) return "00:00";
        int minutes = timeLeft / 60;
        int seconds = timeLeft % 60;
        return addZeros(minutes) + ":" + addZeros(seconds);
    }
}
