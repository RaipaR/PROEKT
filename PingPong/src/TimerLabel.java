import javax.swing.JLabel;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Класс метки таймера, наследуется от JLabel
 */
public class TimerLabel extends JLabel {
	
	private Timer timer;
    private long startTime;
    private long time;
    
    // Конструктор класса
	public TimerLabel() {
		super();
        resetText();
    }
	
	private final Timer getTimer() {
        if (timer == null) {
            int delay = 1000; // задержка в 1000 миллисекунд
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
	
	// Метод подсчёта прошедшего времени
	private final void taskPerformed() {
        time = System.currentTimeMillis() - startTime; // прошедшее время в миллисекундах
        time = time / 1000; // перевод времени в секунды
        String res = String.format("%02d:%02d:%02d", (time/3600)%60, (time/60)%60, time%60); // преобразование секунд в часы, минуты и секунды в формате чч:мм:сс
        setText("<html><center>Прошло времени<p>" + res + "</center></html>");
    }
	
	// Установка значения текста по умолчанию
	public final void resetText() {
        setText("<html><center>Прошло времени<p>00:00:00</center></html>");
    }
	
	// Запуск таймера
	public final synchronized void startTimer() {
        startTime = System.currentTimeMillis();
        getTimer().start();
    }
	
	// Остановка таймера
	public final synchronized void stopTimer() {
        getTimer().stop();
    }
	
	// Проверка запущен ли таймер
	public final boolean isTimerRunning() {
        return getTimer().isRunning();
    }
	
	// Метод возвращения значения времени в секундах
	public final long getTime() {
		return time;
	}
}
