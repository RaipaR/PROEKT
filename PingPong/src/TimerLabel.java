import javax.swing.JLabel;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * ����� ����� �������, ����������� �� JLabel
 */
public class TimerLabel extends JLabel {
	
	private Timer timer;
    private long startTime;
    private long time;
    
    // ����������� ������
	public TimerLabel() {
		super();
        resetText();
    }
	
	private final Timer getTimer() {
        if (timer == null) {
            int delay = 1000; // �������� � 1000 �����������
            ActionListener taskPerformer = new ActionListener() {
                public void actionPerformed(ActionEvent evt) {
                    taskPerformed();
                }
            };
            timer = new Timer(delay, taskPerformer);
            timer.setInitialDelay(0);
        }
        return timer;
    }
	
	// ����� �������� ���������� �������
	private final void taskPerformed() {
        time = System.currentTimeMillis() - startTime; // ��������� ����� � �������������
        time = time / 1000; // ������� ������� � �������
        String res = String.format("%02d:%02d:%02d", (time/3600)%60, (time/60)%60, time%60); // �������������� ������ � ����, ������ � ������� � ������� ��:��:��
        setText("<html><center>������ �������<p>" + res + "</center></html>");
    }
	
	// ��������� �������� ������ �� ���������
	public final void resetText() {
        setText("<html><center>������ �������<p>00:00:00</center></html>");
    }
	
	// ������ �������
	public final synchronized void startTimer() {
        startTime = System.currentTimeMillis();
        getTimer().start();
    }
	
	// ��������� �������
	public final synchronized void stopTimer() {
        getTimer().stop();
    }
	
	// �������� ������� �� ������
	public final boolean isTimerRunning() {
        return getTimer().isRunning();
    }
	
	// ����� ����������� �������� ������� � ��������
	public final long getTime() {
		return time;
	}
}
