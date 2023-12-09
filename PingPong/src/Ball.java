
import java.awt.geom.Ellipse2D;

/**
 * ����� ��������� ���, ����������� �� ������ Ellipse2D.Float
 */
class Ball extends Ellipse2D.Float {
	
	public int x_speed, y_speed; // �������� �������� �� x � y
	private int d; // ������� ����
	
	// ����������� ������
	public Ball(int diameter)
	{
		super(0, 150, diameter, diameter); // ��������� ��������� � ������� ����
		this.d = diameter;
		
		this.x_speed = 5; //(int)(Math.random() * 5 + 3); // �������� �������� ���� ����� ����������� x
		this.y_speed = 3; //(int)(Math.random() * 5 + 5); // �������� �������� ���� ����� ����������� y
	}
	
	// ����� ��������� ����� �������� ����
	public void move()
	{
		// ���� ���������� x < 0 ��� x > ������ ����-�������, �� ������ �������� �� x �� ���������������
		if (super.x < 0 || super.x > Canvas.width - d)
			x_speed = -x_speed;
		// ���� ���������� y < 0 ��� y > ������ ����-�������, �� ������ �������� �� y �� ���������������
		if (super.y < 0 || super.y > Canvas.surfHeight - d)
				y_speed = -y_speed;
		super.x += x_speed;
		super.y += y_speed;
	}
}
