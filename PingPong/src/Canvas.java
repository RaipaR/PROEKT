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
 * ����� ������, �� �������� ��������� ���
 * ����������� �� ������ JFrame
 */
public class Canvas extends JFrame implements PaintSurfaceListener {
	
	static int width = 600; // ������ ����
	static int height = 400; // ������ ����
	static int surfHeight = 315; // ������ ����������� ���������
	// ������ �� ����� �������� ��� ��������� 10 ����������� �������
	static int[] results = new int[10]; // ��� �������� ����� 0
	
	private ThreadAnimation ta; // ��������� ������ ��� ����������� ���� ��� ��������
	private PaintSurface ps; // �����������, �� ������� �������� ��� � �������
	private JPanel cp; // ������ � ������� � ������� � ���������� ����� � ��������� �������
	private JLabel ballsLabel; // ����� ��� ����������� ���������� ���������� �����
	private TimerLabel timeLabel; // ����� ��� ����������� ���������� �������
	private JButton startButton; // ������ ��� ������� ����� ����
	private Preferences userPrefs;
	public JFrame th = null;
	
	// ����������� ������
	public Canvas() {
		th = this;
		setSize(width,height); // ��������� �������� ����
		ps = new PaintSurface(); // �������� ���������� ����������� ���������
		super.add(ps, BorderLayout.CENTER); // ���������� ����������� �� ����� � ������ ��������� ����������
		ps.addListener(this); // ������������� �� ������������ ������� ����������� ���������
		// �������� ������ ��� ������������ ���������
		ps.setCursor(ps.getToolkit().createCustomCursor(new BufferedImage(3, 3, BufferedImage.TYPE_INT_ARGB), new Point(0, 0), "null"));
		
		cp = new JPanel();
		cp.setPreferredSize(new Dimension(width, 50)); // ���������� ���������������� ������ ������
		cp.setBackground(new Color(/*190,205,220*/210,210,210));
		super.add(cp, BorderLayout.SOUTH);
		cp.setLayout(new BoxLayout(cp, BoxLayout.X_AXIS));
		
		Font font = new Font("Verdana", Font.BOLD, 12);
		
		cp.add(Box.createHorizontalStrut(25));
		
		// �������� ����� ���������� ����� (�������������� html-������: ������������ �� ������, ����� � 2 ������)
		ballsLabel = new JLabel("<html><center>�������� �����<p>5</center></html>", SwingConstants.CENTER);
		ballsLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1)); // ��������� ������� ����� ����� ������ ����� �������� 1px
		ballsLabel.setVerticalAlignment(SwingConstants.CENTER); // ��������� �� ��������� �� ������
		ballsLabel.setHorizontalAlignment(SwingConstants.CENTER); // ��������� �� ����������� �� ������
		ballsLabel.setFont(font); // ���������� ����� �����
		cp.add(ballsLabel); // �������� ����� �� ������
		cp.add(Box.createHorizontalStrut(25));
		
		timeLabel = new TimerLabel(); // ("<html><center>������ �������<p>00:00:00</center></html>", SwingConstants.CENTER);
		timeLabel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
		timeLabel.setVerticalAlignment(SwingConstants.CENTER);
		timeLabel.setHorizontalAlignment(SwingConstants.CENTER);
		timeLabel.setFont(font);
		cp.add(timeLabel);
		cp.add(Box.createHorizontalStrut(25));
		
		startButton = new JButton("����� ����");
		startButton.setFont(font);
		cp.add(startButton);
		cp.add(Box.createHorizontalStrut(25));
		
		startButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	// ��������� ������� �� ������ "����� ����"
            	if(timeLabel.isTimerRunning()) {
            		timeLabel.stopTimer();
            		ta.stopAnimation();
            	}
            	ps.init();
        		ta = new ThreadAnimation(th); // �������� ���������� ������ �����������
        		ta.start(); // ������ �������� ��������
        		ta.startAnimation();
            	timeLabel.startTimer();
            }
        });
		
		// ������� ������ ��� ���������� 10 ����������� � ������ �PingPong�
		// � Windows ��� ����� HKEY_CURRENT_USER\Software\JavaSoft\Prefs\PingPong
        userPrefs = Preferences.userRoot().node("pingpong");
        String res = Arrays.toString(results); // �������������� ������� �� 10 ����� ����� � ������ ���� "[1, 2, 3, 4, 5, 6, 7, 8, 9, 10]"
        String savedRes = userPrefs.get("lastresults", res); // ��������� ������ � ���������� 10 ������������. ���� �������� ���, �� �� ��������� ����� ������ "[0, 0, 0, 0, 0, 0, 0, 0, 0, 0]"
        savedRes = savedRes.substring(1, savedRes.length()-1);
        String[] temp = savedRes.split(","); // �������� ������ �� �����-�������� �����������
        for (int i=0; i<temp.length; i++) {
        	temp[i] = (temp[i]).trim();
        	results[i] = Integer.valueOf(temp[i]);
        }
		
		setLocationRelativeTo(null); // ���������� ���� ��������� �� ����� ������
		setTitle("���� Ping-Pong"); // ��������� ���� ���������
		setVisible(true); // ������� ���� ��������� �������
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // ���������� ��������� ��� �������� ����
		setResizable(false); // ������� ���� ����������� �������
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter() {
		    public void windowClosing(WindowEvent evt) {
		        // ��� ������������ ������� �� ������ �������� ����
		    	// ����� ������� ��������� ��������� ����������
		        String res = Arrays.toString(results);
		        userPrefs.put("lastresults", res);
		        System.exit(0);
		    }
		});
	}
	
	// ����� ����������� ����� ���������� ���������� �����
	public void onBallsChanged(int count) {
		if(count < 1) {
			// ��� 5 ����� ������������ - ����� ����
			stopGame();
			ballsLabel.setText("<html><center>�������� �����<p>0</center></html>");
		}
		else ballsLabel.setText("<html><center>�������� �����<p>" + Integer.toString(count) + "</center></html>");
    }
	
	private void stopGame() {
		timeLabel.stopTimer();
		ta.stopAnimation();
		int lastTime = (int) timeLabel.getTime(); // ����������� � ������� ����� ��������� ����
		for(int i=1; i<results.length; i++)
			results[i-1] = results[i]; // �������� ��������� 9 �������� �������
		results[results.length-1] = lastTime; // ���������� ��������� �����
		String res = Arrays.toString(results);
        userPrefs.put("lastresults", res);
        // ����� ���� � ������������ �������
        SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Results r = new Results(th, true, results);
			}
        });
	}
	
	// ����� ������� ���������� - ����� ����� � ���������
	public static void main(String args[]) {
		new Canvas(); // �������� ������
	}
}
