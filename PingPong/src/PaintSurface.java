import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JComponent;

/**
 *  ласс дл€ отрисовки поверхности, на которой рисуютс€ м€ч и ракетка
 * Ќаследуетс€ от класса JComponent
 */
public class PaintSurface extends JComponent {

	int paddle_x = 500; // координата x ракетки
	int paddle_y = 125; // координата y ракетки
	int balls = 5; // счЄтчик
	float a = 1.0f; // ускорение м€ча
	Ball ball; // м€ч

	private ArrayList<PaintSurfaceListener> listeners = new ArrayList<PaintSurfaceListener>();
	
	//  онструктор класса
	public PaintSurface(){

		// ƒобавление слушател€ событий движени€ курсора мыши
		addMouseMotionListener(new MouseMotionAdapter()
		{
			// ћетод вызываетс€, когда курсор мыши был перемещен
			public void mouseMoved(MouseEvent e) // e - это событие с информацией о движении курсора мыши
			{
				if (e.getY()  - paddle_y > 35)
					a = 1.1f;
				else if (e.getY() - 35 - paddle_y < -35)
					a = -1.1f;
				else
					a = 1.0f;
				paddle_y = e.getY() - 35;
			}
		} );
		ball = new Ball(20); // создаЄм м€ч диаметром 20 пикселей.
	}

	public void init() {
		paddle_x = 500;
		paddle_y = 125;
		a = 1.0f;
		balls = 5;
		ball = new Ball(20);
		fireListeners(balls);
	}
	
	// ћетод рисовани€ компонента
	public void paint(Graphics g){

		Graphics2D g2 = (Graphics2D)g; //содание экземпл€ра класса дл€ рисовани€
		// Ќастройка алгоритмов отрисовки
		g2.setRenderingHint(
				RenderingHints.KEY_ANTIALIASING, // пытатьс€ снизить артефакты путем сглаживани€ по кра€м контуров
				RenderingHints.VALUE_ANTIALIAS_ON); // включить сглаживание

		// —оздание ракетки на основе пр€моугольника
		Shape paddle = new Rectangle2D.Float(paddle_x, paddle_y, 10, 70); // установка координат x, y, ширины и высоты каретки
		//g2.setColor(color[colorIndex]); 
		g2.setColor(Color.BLUE);
		if (ball.intersects(paddle_x, paddle_y, 10, 70)) // проверка пересечени€ каретки и шара
		{
			ball.y_speed = (int) (ball.y_speed * a);
			ball.x_speed = -ball.x_speed;
		}
		if (ball.getX() + ball.getWidth() >= Canvas.width) // если не поймали м€ч, он за пределами справа
		{
			balls -= 1;
			fireListeners(balls);
			ball = new Ball(20); // создаЄм новый м€ч и увеличиваем скорость
			ball.x_speed += (int)(Math.random()*3 + 3);
			ball.y_speed += (int)(Math.random()*2 + 2);
		}
		ball.move(); // вызов перемещени€ м€ча
		g2.fill(ball); // заполн€ем контур окружности м€ча установленным выше цветом
		g2.setColor(Color.DARK_GRAY); // устанавливаем новый цвет (дл€ ракетки)
		g2.fill(paddle); // заполн€ем контур ракетки установленным цветом
	}
	
	public void addListener(PaintSurfaceListener listener) {
        listeners.add(listener);
    }

    public void removeListener(PaintSurfaceListener listener) {
        listeners.remove(listener);
    }

    private void fireListeners(int count) {
        for(PaintSurfaceListener listener : listeners) {
            listener.onBallsChanged(count);
        }
    }
}
