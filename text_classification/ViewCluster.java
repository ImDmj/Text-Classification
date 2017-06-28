package cluster;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.awt.Font;
public class ViewCluster extends JFrame{
	JTable tab;
	DefaultTableModel dtm;
	JScrollPane jsp;
	ArrayList<String> cls;
public ViewCluster(ArrayList<String> cls){
	super("View Cluster Details");
	this.cls = cls;
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
	dtm.addColumn("Cluster ID");
	dtm.addColumn("Nodes in Cluster");
	getContentPane().add(jsp);
	result();
}
public void result(){
	for(int i=0;i<cls.size();i++){
		Object arr[] = {Integer.toString(i+1),cls.get(i)};
		dtm.addRow(arr);
	}
}
}