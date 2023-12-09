import javax.swing.JFrame;
/**
 * Класс для перерисовки окна при анимации движения
 * Наследуется от класса Thread для перерисовки в отдельном потоке приложения.
 */
public class ThreadAnimation extends Thread  {
	JFrame f; // окно приложения
	private boolean stopped = false;
	
	// Конструктор класса
	ThreadAnimation(JFrame f) {
		this.f = f; // получить окно приложения	
	}

	// Запуск выполнения потока
	public void run() {

		// Пока работает поток перерисовываем окно
		while(!stopped){
			f.repaint();
			try {
				Thread.sleep(20); // приостановить выполнение потока на 20 миллисекунд
				// благодаря задержке получим частоту перерисовки окна 50 раз в секунду.
			} catch (InterruptedException e) {
				// если возникло исключение, то печатать трассировку стека 
				// в выходной поток сообщений об ошибках.
				e.printStackTrace();
			}
		}
	}
	
	public void startAnimation() {
		stopped = false;
	}
	
	public void stopAnimation() {
		stopped = true;
	}
}
