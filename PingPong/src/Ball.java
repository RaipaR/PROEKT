
import java.awt.geom.Ellipse2D;

/**
 * Класс описывает мяч, наследуется от класса Ellipse2D.Float
 */
class Ball extends Ellipse2D.Float {
	
	public int x_speed, y_speed; // скорость движения по x и y
	private int d; // диаметр мяча
	
	// Конструктор класса
	public Ball(int diameter)
	{
		super(0, 150, diameter, diameter); // начальное положение и диаметр мяча
		this.d = diameter;
		
		this.x_speed = 5; //(int)(Math.random() * 5 + 3); // скорость движения мяча вдоль направления x
		this.y_speed = 3; //(int)(Math.random() * 5 + 5); // скорость движения мяча вдоль направления y
	}
	
	// Метод описывает закон движения мяча
	public void move()
	{
		// если координата x < 0 или x > ширины окна-диаметр, то меняем скорость по x на противоположную
		if (super.x < 0 || super.x > Canvas.width - d)
			x_speed = -x_speed;
		// если координата y < 0 или y > высоты окна-диаметр, то меняем скорость по y на противоположную
		if (super.y < 0 || super.y > Canvas.surfHeight - d)
				y_speed = -y_speed;
		super.x += x_speed;
		super.y += y_speed;
	}
}
