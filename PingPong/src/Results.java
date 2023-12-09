import java.awt.Color;
import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;

/**
 * ����� ��������� ���� � ������������ �������, ����������� �� JDialog
 */
public class Results extends JDialog {
	public boolean isRecord = true;
	// ����������� ������
	public Results(JFrame parent, boolean isModal, int[] res) {
		super(parent, isModal);
		setSize(270, 220);
		setLocationRelativeTo(parent); // ���������� ���� ��������� �� ����� ������������� ����
		setTitle("������ ��������� �����������"); // ��������� ���� ���������
		
		// ������ ������ - ��� ���������� �����
		DefaultListModel listModel = new DefaultListModel();
		JList list = new JList(listModel);
		
		// �������� �������� �� ��������� ��������� ��������
		for(int i=8; i>=0; i--)
			if(res[i] >= res[9]) {
				isRecord = false; // ���� ���� ���� ���������� �������� ������ ��� �����, �� �� ������
				break;
			}
		
		// ����� ��������� � �������
		if(res[9] > 0 && isRecord) listModel.addElement("����� ������ " + " : " + res[9] + " ���.");
		else listModel.addElement(res[9] + " ���.");
		for(int j=8; j>=0; j--)
			if(res[j] > 0) listModel.addElement(res[j] + " ���.");
		// ������������ ������ � �������
		list.setCellRenderer(new DefaultListCellRenderer() {
		       public int getHorizontalAlignment() {
		    	   return CENTER;
		       }
		       public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		    	   super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		    	   // ���� ��������� ��������� - ������, �� �������� ������ ������
		    	   if(isRecord && index == 0)
		    		   setBackground(Color.GREEN);
		    	   else 
		    		   setBackground(Color.WHITE);
		    	   return this;
		       }
		});
		
		this.add(list); // ���������� ������ �� ���������� ����
		
		setVisible(true); // ������� ���� ��������� �������
	}
}
