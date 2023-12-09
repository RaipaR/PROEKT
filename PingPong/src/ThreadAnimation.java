import javax.swing.JFrame;
/**
 * ����� ��� ����������� ���� ��� �������� ��������
 * ����������� �� ������ Thread ��� ����������� � ��������� ������ ����������.
 */
public class ThreadAnimation extends Thread  {
	JFrame f; // ���� ����������
	private boolean stopped = false;
	
	// ����������� ������
	ThreadAnimation(JFrame f) {
		this.f = f; // �������� ���� ����������	
	}

	// ������ ���������� ������
	public void run() {

		// ���� �������� ����� �������������� ����
		while(!stopped){
			f.repaint();
			try {
				Thread.sleep(20); // ������������� ���������� ������ �� 20 �����������
				// ��������� �������� ������� ������� ����������� ���� 50 ��� � �������.
			} catch (InterruptedException e) {
				// ���� �������� ����������, �� �������� ����������� ����� 
				// � �������� ����� ��������� �� �������.
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
