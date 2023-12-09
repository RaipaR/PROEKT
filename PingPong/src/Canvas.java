import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.prefs.Preferences;

/**
 * Класс холста, по которому двигается мяч
 * Унаследован от класса JFrame
 */
public class Canvas extends JFrame implements PaintSurfaceListener {
	
	static int width = 600; // ширина окна
	static int height = 400; // высота окна
	static int surfHeight = 315; // высота поверхности рисования
	// массив из целых значений для последних 10 результатов времени
	static int[] results = new int[10]; // все значения равны 0
	
	private ThreadAnimation ta; // экземпляр класса для перерисовки окна при анимации
	private PaintSurface ps; // поверхность, на которой рисуются мяч и ракетка
	private JPanel cp; // панель с кнопкой и метками о количестве мячей и прошедшем времени
	private JLabel ballsLabel; // метка для отображения количества оставшихся мячей
	private TimerLabel timeLabel; // метка для отображения прошедшего времени
	private JButton startButton; // кнопка для запуска новой игры
	private Preferences userPrefs;
	public JFrame th = null;
	
	// Конструктор класса
	public Canvas() {
		th = this;
		setSize(width,height); // установка размеров окна
		ps = new PaintSurface(); // создание экземпляра поверхности рисования
		super.add(ps, BorderLayout.CENTER); // добавление поверхности на форму в центре менеджера компоновки
		ps.addListener(this); // подписываемся на отслеживание событий поверхности рисования
		// скрываем курсор над поверхностью рисования
		ps.setCursor(ps.getToolkit().createCustomCursor(new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB), new Point(0, 0), "null"));
		
		cp = new JPanel();
		cp.setPreferredSize(new Dimension(width, 50)); // установить предпочтительный размер панели
		cp.setBackground(new Color(/*190,205,220*/210,210,210));
		super.add(cp, BorderLayout.SOUTH);
		cp.setLayout(new BoxLayout(cp, BoxLayout.X_AXIS));
		
		Font font = new Font("Verdana", Font.BOLD, 12);
		
		cp.add(Box.createHorizontalStrut(25));
		
		// создание метки количества мячей (форматирование html-тегами: выравнивание по центру, текст в 2 строки)
		ballsLabel = new JLabel("<html><center>Осталось мячей<p>5</center></html>", SwingConstants.CENTER);
		ballsLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1)); // установка границы метки линии серого цвета толщиной 1px
		ballsLabel.setVerticalAlignment(SwingConstants.CENTER); // выровнять по вертикали по центру
		ballsLabel.setHorizontalAlignment(SwingConstants.CENTER); // выровнять по горизонтали по центру
		ballsLabel.setFont(font); // установить шрифт метки
		cp.add(ballsLabel); // добавить метку на панель
		cp.add(Box.createHorizontalStrut(25));
		
		timeLabel = new TimerLabel(); // ("<html><center>Прошло времени<p>00:00:00</center></html>", SwingConstants.CENTER);
		timeLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		timeLabel.setVerticalAlignment(SwingConstants.CENTER);
		timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		timeLabel.setFont(font);
		cp.add(timeLabel);
		cp.add(Box.createHorizontalStrut(25));
		
		startButton = new JButton("Новая игра");
		startButton.setFont(font);
		cp.add(startButton);
		cp.add(Box.createHorizontalStrut(25));
		
		startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	// Обработка нажатия на кнопку "Новая игра"
            	if(timeLabel.isTimerRunning()) {
            		timeLabel.stopTimer();
            		ta.stopAnimation();
            	}
            	ps.init();
        		ta = new ThreadAnimation(th); // создание экземпляра класса перерисовки
        		ta.start(); // запуск анимации движения
        		ta.startAnimation();
            	timeLabel.startTimer();
            }
        });
		
		// Создаем объект для сохранения 10 результатов с именем “PingPong”
		// В Windows это будет HKEY_CURRENT_USER\Software\JavaSoft\Prefs\PingPong
        userPrefs = Preferences.userRoot().node("pingpong");
        String res = Arrays.toString(results); // преобразование массива из 10 целых чисел в строку вида "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]"
        String savedRes = userPrefs.get("lastresults", res); // считываем строку с последними 10 результатами. если настроек нет, то по умолчанию будет строка "[0, 0, 0, 0, 0, 0, 0, 0, 0, 0]"
        savedRes = savedRes.substring(1, savedRes.length()-1);
        String[] temp = savedRes.split(","); // получаем массив из строк-значений результатов
        for (int i=0; i<temp.length; i++) {
        	temp[i] = (temp[i]).trim();
        	results[i] = Integer.valueOf(temp[i]);
        }
		
		setLocationRelativeTo(null); // разместить окно программы по центу экрана
		setTitle("Игра Ping-Pong"); // заголовок окна программы
		setVisible(true); // сделать окно программы видимым
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // завершение программы при закрытии окна
		setResizable(false); // сделать окно постоянного размера
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
		    public void windowClosing(WindowEvent evt) {
		        // тут обрабатываем нажатие на кнопку закрытия окна
		    	// перед выходом сохраняем последние результаты
		        String res = Arrays.toString(results);
		        userPrefs.put("lastresults", res);
		        System.exit(0);
		    }
		});
	}
	
	// Метод выполняется когда изменяется количество шаров
	public void onBallsChanged(int count) {
		if(count < 1) {
			// все 5 шаров использованы - конец игры
			stopGame();
			ballsLabel.setText("<html><center>Осталось мячей<p>0</center></html>");
		}
		else ballsLabel.setText("<html><center>Осталось мячей<p>" + Integer.toString(count) + "</center></html>");
    }
	
	private void stopGame() {
		timeLabel.stopTimer();
		ta.stopAnimation();
		int lastTime = (int) timeLabel.getTime(); // запрашиваем у таймера время последней игры
		for(int i=1; i<results.length; i++)
			results[i-1] = results[i]; // сдвигаем последние 9 значений времени
		results[results.length-1] = lastTime; // запоминаем последнее время
		String res = Arrays.toString(results);
        userPrefs.put("lastresults", res);
        // Вывод окна с результатами времени
        SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Results r = new Results(th, true, results);
			}
        });
	}
	
	// Метод запуска приложения - точка входа в программу
	public static void main(String args[]) {
		new Canvas(); // создание холста
	}
}
