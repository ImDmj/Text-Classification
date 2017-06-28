package cluster;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.awt.Font;
public class ClassifyResult extends JFrame{
	JTable tab;
	DefaultTableModel dtm;
	JScrollPane jsp;
	ArrayList<String> result;
public ClassifyResult(ArrayList<String> result,String title){
	super(title);
	this.result = result;
	dtm = new DefaultTableModel(){
		public boolean isCellEditable(int row_no,int column_no){
			return false;
		}
	};
	tab = new JTable(dtm);
	tab.getTableHeader().setFont(new Font("Courier New",Font.BOLD,14));
	tab.setFont(new Font("Courier New",Font.BOLD,13));
	tab.setRowHeight(30);
	jsp = new JScrollPane(tab);
	dtm.addColumn("Classification");
	dtm.addColumn("Distance");
	getContentPane().add(jsp);
	result();
}
public void result(){
	for(int i=0;i<result.size();i++){
		String arr[] = result.get(i).split(",");
		Object row[] = {arr[1]+" classified as "+arr[0],arr[2]};
		dtm.addRow(row);
	}
}
}