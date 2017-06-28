package cluster;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.JFileChooser;
import java.awt.Cursor;
import com.jd.swing.custom.component.panel.HeadingPanel;
import com.jd.swing.util.PanelType;
import com.jd.swing.util.Theme;
import org.jfree.ui.RefineryUtilities;
import java.awt.Dimension;
import java.text.DecimalFormat;
import java.io.BufferedReader;
import java.io.FileReader;
import javax.swing.UIManager;
public class UploadDocument extends JFrame{
	JLabel l1;
	JPanel p1,p2,p3;
	Font f1;
	JScrollPane jsp;
	JButton b1,b2,b3,b4,b5,b6,b7,b8,b9,b10,b11,b12,b13,b14,b15;
	JFileChooser chooser;
	MyTableModel dtm;
	JTable table;
	File file,temp;
	DecimalFormat format = new DecimalFormat("#.###");
	ArrayList<Similarity> list = new ArrayList<Similarity>();
	Vector vector = new Vector();
	double smtp_acc,ed_acc;
	long kstart,kend;
	long sstart,send;
	KMeans kalgo;
	KMeansSMTP kalgosmtp;
	ArrayList<KNNData> knn_test = new ArrayList<KNNData>();
	Parallel screen;
public UploadDocument(){
	super("Text Classification");
	
	p1 = new HeadingPanel("Project Title",Theme.GLOSSY_OLIVEGREEN_THEME);
	p1.setPreferredSize(new Dimension(600,50));
	l1 = new JLabel("<html><body><center>TEXT CLASSIFICATION AND CLUSTERING USING SIMILARITY MEASURES</center></body></html>");
	l1.setFont(new Font("Courier New",Font.BOLD,18));
	l1.setForeground(Color.white);
	p1.add(l1);
	getContentPane().add(p1,BorderLayout.NORTH);

	f1 = new Font("Courier New",Font.BOLD,14);

	p2 = new JPanel();
	p2.setLayout(new BorderLayout());
	dtm = new MyTableModel(){
		public boolean isCellEditable(int r,int c){
			return false;
		}
	};
	table = new JTable(dtm);
	table.setRowHeight(30);
	table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
	table.setFont(f1);
	table.getTableHeader().setFont(f1);
	jsp = new JScrollPane(table);
	
	
	p3 = new HeadingPanel("",Theme.GLOSSY_OLIVEGREEN_THEME);
	p3.setPreferredSize(new Dimension(150,100));
	chooser = new JFileChooser(new File("."));
	chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
	b1 = new JButton("Upload Text Documents");
	b1.setFont(f1);
	p3.add(b1);
	b1.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			int option = chooser.showOpenDialog(UploadDocument.this);
			if(option == chooser.APPROVE_OPTION){
				file = chooser.getSelectedFile();
				clearTable();
				JOptionPane.showMessageDialog(UploadDocument.this,"Dataset Loaded");
			}
		}
	});

	b2 = new JButton("Text Similarity Clustering");
	b2.setFont(f1);
	p3.add(b2);
	b2.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
			setCursor(hourglassCursor);
			sstart = System.currentTimeMillis();
			vector.buildVector(dtm,file);
			send = System.currentTimeMillis();
			dtm.addColumn("File Name");
			for(int i=0;i<vector.unique_att.size();i++){
				dtm.addColumn(vector.unique_att.get(i));
			}
			for(int i=0;i<dtm.getColumnCount();i++){
				table.getColumnModel().getColumn(i).setPreferredWidth(100);
			}
			for(int i=0;i<vector.vector.size();i++){
				VectorModel vm = vector.vector.get(i);
				String row[] = new String[dtm.getColumnCount()];
				row[0] = vm.filename;
				for(int j=1;j<dtm.getColumnCount();j++){
					if(vm.vector.get(dtm.getColumnName(j)) != null){
						int value = vm.vector.get(dtm.getColumnName(j));
						row[j] = Integer.toString(value);
					}else{
						row[j] = "0";
					}
				}
				dtm.addRow(row);
			}
			Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
			setCursor(normalCursor);
		}
	});

	b3 = new JButton("Text Classification SMTP");
	b3.setFont(f1);
	p3.add(b3);
	b3.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			list.clear();
			int option = chooser.showOpenDialog(UploadDocument.this);
			if(option == chooser.APPROVE_OPTION){
				File test_file = chooser.getSelectedFile();
				temp = test_file;
				knn_test.clear();
				Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
				setCursor(hourglassCursor);
				list.clear();
				textclassify(test_file);
				if(list.size() > 0){
					SimResult sr = new SimResult(list);
					sr.setSize(600,400);
					sr.setVisible(true);
				}
				Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
				setCursor(normalCursor);
			}
		}
	});

	b6 = new JButton("Text Classification ED");
	b6.setFont(f1);
	p3.add(b6);
	b6.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			list.clear();
			int option = chooser.showOpenDialog(UploadDocument.this);
			if(option == chooser.APPROVE_OPTION){
				File test_file = chooser.getSelectedFile();
				temp = test_file;
				Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
				setCursor(hourglassCursor);
				list.clear();
				textclassify1(test_file);
				if(list.size() > 0){
					SimResult sr = new SimResult(list);
					sr.setSize(600,400);
					sr.setVisible(true);
				}
				Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
				setCursor(normalCursor);
			}
		}
	});

	b8 = new JButton("KMeans Clustering");
	b8.setFont(f1);
	p3.add(b8);
	b8.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
			setCursor(hourglassCursor);
			try{
				kstart = System.currentTimeMillis();
				kalgo = new KMeans();
				kalgo.clear();
				kalgo.readDocuments(file);
				System.out.println("file read");
				kalgo.generateVector();
				System.out.println("vector");
				kalgo.kmeans();
				kend = System.currentTimeMillis();
				ViewCluster vc = new ViewCluster(kalgo.cls);
				vc.setSize(600,400);
				vc.setVisible(true);
			}catch(Exception e){
				e.printStackTrace();
			}
			Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
			setCursor(normalCursor);
		}
	});

	b11 = new JButton("KMeans SMTP Clustering");
	b11.setFont(f1);
	p3.add(b11);
	b11.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
			setCursor(hourglassCursor);
			try{
				kalgosmtp = new KMeansSMTP();
				kalgosmtp.clear();
				kalgosmtp.readDocuments(file);
				System.out.println("file read1");
				kalgosmtp.generateVector();
				System.out.println("vector");
				kalgosmtp.kmeans();
				ViewCluster vc = new ViewCluster(kalgosmtp.cls);
				vc.setSize(600,400);
				vc.setVisible(true);
			}catch(Exception e){
				e.printStackTrace();
			}
			Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
			setCursor(normalCursor);
		}
	});

	b4 = new JButton("Classification Chart");
	b4.setFont(f1);
	p3.add(b4);
	b4.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			File file_list[] = temp.listFiles();
			Chart chart1 = new Chart("Classification Chart",file_list.length,list.size());
			chart1.pack();
			RefineryUtilities.centerFrameOnScreen(chart1);
			chart1.setVisible(true);
		}
	});

	b7 = new JButton("SMTP & ED Chart");
	b7.setFont(f1);
	p3.add(b7);
	b7.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			EDSMTPAccuracy chart1 = new EDSMTPAccuracy("SMTP & ED Chart",smtp_acc,ed_acc);
			chart1.pack();
			RefineryUtilities.centerFrameOnScreen(chart1);
			chart1.setVisible(true);
		}
	});


	b9 = new JButton("SMTP & Kmeans Processing Time Chart");
	b9.setFont(f1);
	//p3.add(b9);
	b9.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			
		}
	});
	b10 = new JButton("Accuracy Chart");
	b10.setFont(f1);
	p3.add(b10);
	b10.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			ArrayList<String> list = new ArrayList<String>();
			ArrayList<String> list1 = new ArrayList<String>();
			//smtp_acc = smtp_acc * 10;
			//ed_acc = ed_acc * 10;
			//list.add((smtp_acc)+",SMTP Accuracy");
			//list.add((ed_acc)+",ED Accuracy");
			list1.add((kalgo.acc*100)+",Kmeans ED Accuracy");
			list1.add((kalgosmtp.acc*100)+",Kmeans SMTP Accuracy");
			//System.out.println(list);
			//System.out.println(list1);
			//AUCChart chart = new AUCChart("Accuracy Chart",list);
			//chart.pack();
			//RefineryUtilities.centerFrameOnScreen(chart);
			//chart.setVisible(true);

			AUCChart chart1 = new AUCChart("Accuracy Chart",list1);
			chart1.pack();
			RefineryUtilities.centerFrameOnScreen(chart1);
			chart1.setVisible(true);
			//auc.clear();
		}
	});
	
	b12 = new JButton("KNN SMTP");
	b12.setFont(f1);
	p3.add(b12);
	b12.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			KNN.classifySMTP(vector,knn_test);
		}
	});

	b13 = new JButton("KNN ED");
	b13.setFont(f1);
	p3.add(b13);
	b13.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			KNN.classifyEd(vector,knn_test);
		}
	});

	b14 = new JButton("KNN SMTP & ED Accuracy Chart");
	b14.setFont(f1);
	p3.add(b14);
	b14.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			TimeChart chart1 = new TimeChart("KNN SMTP & ED Accuracy Chart");
			chart1.pack();
			RefineryUtilities.centerFrameOnScreen(chart1);
			chart1.setVisible(true);

			ClassifyChart chart2 = new ClassifyChart("Classification Chart",KNN.s1,KNN.s2);
			chart2.pack();
			RefineryUtilities.centerFrameOnScreen(chart2);
			chart2.setVisible(true);
		}
	});

	b5 = new JButton("Parallel Processing KMeans");
	b5.setFont(f1);
	//p3.add(b5);
	b5.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			Cursor hourglassCursor = new Cursor(Cursor.WAIT_CURSOR);
			setCursor(hourglassCursor);
			new Thread(new Runnable(){
				public void run(){
					pt.runtime.ParaTask.init();
					screen = new Parallel(file);
					screen.startRunning();
					System.out.println("kmeans "+(kend-kstart));
				}
			}).start();
			Cursor normalCursor = new Cursor(Cursor.DEFAULT_CURSOR);
			setCursor(normalCursor);
		}
	});

	b15 = new JButton("Parallel Processing Chart");
	b15.setFont(f1);
	//p3.add(b15);
	b15.addActionListener(new ActionListener(){
		public void actionPerformed(ActionEvent ae){
			TimeChart1 chart1 = new TimeChart1("Parallel & Normal Processing Chart",(kend-kstart),(screen.end-screen.start));
			chart1.pack();
			RefineryUtilities.centerFrameOnScreen(chart1);
			chart1.setVisible(true);
		}
	});


	getContentPane().add(jsp,BorderLayout.CENTER);
	getContentPane().add(p3,BorderLayout.SOUTH);
}
public void clearTable(){
	for(int i=table.getRowCount()-1;i>=0;i--){
		dtm.removeRow(i);
	}
	for(int i=table.getColumnCount()-1;i>=0;i--){
		dtm.removeColumn(i);
	}
}
public void textclassify(File test_file){
	File[] list = test_file.listFiles();
	for(File subfile : list){
		if(subfile.isFile()){
			readFile(subfile);
		}else if(subfile.isDirectory()){
			textclassify(subfile);
        }
    }
}
public void textclassify1(File test_file){
	File[] list = test_file.listFiles();
	for(File subfile : list){
		if(subfile.isFile()){
			readFile1(subfile);
		}else if(subfile.isDirectory()){
			textclassify1(subfile);
        }
    }
}
public void readFile(File file){
	try{
		BufferedReader file_reader = new BufferedReader(new FileReader(file));
		String reader = null;
		StringBuilder tokens = new StringBuilder();
		while((reader=file_reader.readLine())!=null){
			reader = reader.toLowerCase().replaceAll("[^a-zA-Z\\s+]", " ");
			tokens.append(reader.trim()+" ");
		}
		String array[] = tokens.toString().trim().toLowerCase().split("\\s+");
		textclassify(array,file.getName());
	}catch(Exception fnfe){
		System.out.println("Exception in readFile");
	}
}
public void readFile1(File file){
	try{
		BufferedReader file_reader = new BufferedReader(new FileReader(file));
		String reader = null;
		StringBuilder tokens = new StringBuilder();
		while((reader=file_reader.readLine())!=null){
			reader = reader.toLowerCase().replaceAll("[^a-zA-Z\\s+]", " ");
			tokens.append(reader.trim()+" ");
		}
		String array[] = tokens.toString().trim().toLowerCase().split("\\s+");
		textclassify1(array,file.getName());
	}catch(Exception fnfe){
		System.out.println("Exception in readFile");
	}
}
public double getQueryFrequency(String arr[],String qry){
	double count = 0;
	for(String str : arr){
		if(str.equals(qry))
			count = count + 1;
	}
	return count;
}
public void textclassify(String arr[],String test_file){
	double vec1[] = new double[vector.unique_att.size()];
	double vec2[] = new double[vector.unique_att.size()];
	for(int i=0;i<vector.unique_att.size();i++){
		double value = getQueryFrequency(arr,vector.unique_att.get(i));
		if(value == 0)
			vec1[i] = 0;
		else{
			double af = vector.addTerm(i);
			vec1[i] = (value/af)*vector.matrix[vector.currentrow][i];
		}
	}
	KNNData obj = new KNNData();
	obj.setFile(test_file);
	obj.setVector(vec1);
	knn_test.add(obj);
	for(int i=0;i<vector.vector.size();i++){
		VectorModel vm1 = vector.vector.get(i);
		for(int k=0;k<vector.unique_att.size();k++){
			vec2[k] = vector.matrix[i][k];
		}
		try{
			double sim = Double.parseDouble(format.format(smtp(vec1,vec2)));
			if(sim > 0){
				list.add(new Similarity(vm1.filename,test_file,sim));
				//smtp_acc = (double)list.size()/(double)vector.unique_att.size();
			}
		}catch(NumberFormatException nfor){}
	}
	java.util.Collections.sort(list,new Similarity());
	smtp_acc = (double)list.size()/(double)vector.vector.size();
	//System.out.println(smtp_acc+" acc test"+list.size()+" "+vector.unique_att.size());
}
public void textclassify1(String arr[],String test_file){
	double vec1[] = new double[vector.unique_att.size()];
	double vec2[] = new double[vector.unique_att.size()];
	for(int i=0;i<vector.unique_att.size();i++){
		double value = getQueryFrequency(arr,vector.unique_att.get(i));
		if(value == 0)
			vec1[i] = 0;
		else{
			double af = vector.addTerm(i);
			vec1[i] = (value/af)*vector.matrix[vector.currentrow][i];
		}
	}
	for(int i=0;i<vector.vector.size();i++){
		VectorModel vm1 = vector.vector.get(i);
		for(int k=0;k<vector.unique_att.size();k++){
			vec2[k] = vector.matrix[i][k];
		}
		try{
			double sim = Double.parseDouble(format.format(EuclideanDistance(vec1,vec2)));
			if(sim > 0){
				list.add(new Similarity(vm1.filename,test_file,sim));
			}
		}catch(NumberFormatException nfor){}
	}
	java.util.Collections.sort(list,new Similarity());
	ed_acc = (double)list.size()/(double)vector.vector.size();
}
public boolean isMatrixContains(double d1, double d2){
	if(d1 > 0.4 && d2 > 0.4)
		return true;
	else
		return false;
}
public double smtp(double[] vector1,double[] vector2){
	double d1 = 0.0;
    double d2 = 0.0;
	double n1 = 0;
	double u1 = 0;
	double total = 0;
    for(int i=0;i<vector1.length;i++){
		d1 = vector1[i];
		d2 = vector2[i];
		if(d1 > 0 && d2 > 0){
			double m1 = Math.abs((d1 - d2)/(double)vector1.length);
			m1 = Math.sqrt(m1);
			double exp = 1 + Math.exp(m1);
			n1 = 0.5 * exp;
			double test = n1/1.0;
			if(!Double.isNaN(test))
				total = total + test;
		}
	}
	return total;
}
public double EuclideanDistance(double[] vector1, double[] vector2) {
	double distance = 0;
	for(int i=0;i<vector1.length;i++){
		if(isMatrixContains(vector2[i],vector1[i])){
			double diff = vector2[i] - vector1[i];
			distance += diff * diff;
		}
	}
    return distance;
}
public static void main(String a[])throws Exception{
	UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	UploadDocument ud = new UploadDocument();
	ud.setVisible(true);
	ud.setExtendedState(JFrame.MAXIMIZED_BOTH);
	//pt.compiler.ParaTaskParser.parse(new File("Parallel.ptjava"));
}
}
class MyTableModel extends DefaultTableModel {
    public void removeColumn(int column){
		columnIdentifiers.remove(column);
		for(Object row: dataVector){
			((java.util.Vector) row).remove(column);
		}
		fireTableStructureChanged();
    }	
}