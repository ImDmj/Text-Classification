package cluster;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.awt.Font;
public class SimResult extends JFrame{
	JTable tab;
	DefaultTableModel dtm;
	JScrollPane jsp;
	ArrayList<Similarity> result;
public SimResult(ArrayList<Similarity> result){
	super("Document Similarity Result");
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
	dtm.addColumn("Training Document");
	dtm.addColumn("Test Document");
	dtm.addColumn("Similarity Score");
	getContentPane().add(jsp);
	result();
}
public void result(){
	for(int i=result.size()-1;i>=0;i--){
		Similarity sim = result.get(i);
		Object arr[] = {sim.source,sim.target,sim.sim};
		dtm.addRow(arr);
	}
}
}