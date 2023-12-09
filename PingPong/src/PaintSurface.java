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
 * ����� ��� ��������� �����������, �� ������� �������� ��� � �������
 * ����������� �� ������ JComponent
 */
public class PaintSurface extends JComponent {

	int paddle_x = 500; // ���������� x �������
	int paddle_y = 125; // ���������� y �������
	int balls = 5; // �������
	float a = 1.0f; // ��������� ����
	Ball ball; // ���

	private ArrayList<PaintSurfaceListener> listeners = new ArrayList<PaintSurfaceListener>();
	
	// ����������� ������
	public PaintSurface(){

		// ���������� ��������� ������� �������� ������� ����
		addMouseMotionListener(new MouseMotionAdapter()
		{
			// ����� ����������, ����� ������ ���� ��� ���������
			public void mouseMoved(MouseEvent e) // e - ��� ������� � ����������� � �������� ������� ����
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
		ball = new Ball(20); // ������ ��� ��������� 20 ��������.
	}

	public void init() {
		paddle_x = 500;
		paddle_y = 125;
		a = 1.0f;
		balls = 5;
		ball = new Ball(20);
		fireListeners(balls);
	}
	
	// ����� ��������� ����������
	public void paint(Graphics g){

		Graphics2D g2 = (Graphics2D)g; //������� ���������� ������ ��� ���������
		// ��������� ���������� ���������
		g2.setRenderingHint(
				RenderingHints.KEY_ANTIALIASING, // �������� ������� ��������� ����� ����������� �� ����� ��������
				RenderingHints.VALUE_ANTIALIAS_ON); // �������� �����������

		// �������� ������� �� ������ ��������������
		Shape paddle = new Rectangle2D.Float(paddle_x, paddle_y, 10, 70); // ��������� ��������� x, y, ������ � ������ �������
		//g2.setColor(color[colorIndex]); 
		g2.setColor(Color.BLUE);
		if (ball.intersects(paddle_x, paddle_y, 10, 70)) // �������� ����������� ������� � ����
		{
			ball.y_speed = (int) (ball.y_speed * a);
			ball.x_speed = -ball.x_speed;
		}
		if (ball.getX() + ball.getWidth() >= Canvas.width) // ���� �� ������� ���, �� �� ��������� ������
		{
			balls -= 1;
			fireListeners(balls);
			ball = new Ball(20); // ������ ����� ��� � ����������� ��������
			ball.x_speed += (int)(Math.random()*3 + 3);
			ball.y_speed += (int)(Math.random()*2 + 2);
		}
		ball.move(); // ����� ����������� ����
		g2.fill(ball); // ��������� ������ ���������� ���� ������������� ���� ������
		g2.setColor(Color.DARK_GRAY); // ������������� ����� ���� (��� �������)
		g2.fill(paddle); // ��������� ������ ������� ������������� ������
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
