import java.awt.Color;
import java.awt.Component;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;

/**
 * Класс реализует окно с результатами времени, наследуется от JDialog
 */
public class Results extends JDialog {
	public boolean isRecord = true;
	// Конструктор класса
	public Results(JFrame parent, boolean isModal, int[] res) {
		super(parent, isModal);
		setSize(270, 220);
		setLocationRelativeTo(parent); // разместить окно программы по центу родительского окна
		setTitle("Список последних результатов"); // заголовок окна программы
		
		// Модель списка - для добавления строк
		DefaultListModel listModel = new DefaultListModel();
		JList list = new JList(listModel);
		
		// Проверка является ли последний результат рекордом
		for(int i=8; i>=0; i--)
			if(res[i] >= res[9]) {
				isRecord = false; // если хоть одно предыдущее значение больше или равно, то не рекорд
				break;
			}
		
		// Вывод сообщения о рекорде
		if(res[9] > 0 && isRecord) listModel.addElement("Новый рекорд " + " : " + res[9] + " сек.");
		else listModel.addElement(res[9] + " сек.");
		for(int j=8; j>=0; j--)
			if(res[j] > 0) listModel.addElement(res[j] + " сек.");
		// Выравнивание текста в ячейках
		list.setCellRenderer(new DefaultListCellRenderer() {
		       public int getHorizontalAlignment() {
		    	   return CENTER;
		       }
		       public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
		    	   super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
		    	   // если последний результат - рекорд, то выделяем строку цветом
		    	   if(isRecord && index == 0)
		    		   setBackground(Color.GREEN);
		    	   else 
		    		   setBackground(Color.WHITE);
		    	   return this;
		       }
		});
		
		this.add(list); // добавление списка на диалоговое окно
		
		setVisible(true); // сделать окно программы видимым
	}
}
