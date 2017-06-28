package cluster;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import java.util.ArrayList; 
import java.awt.event.WindowEvent;
public class AUCChart extends ApplicationFrame{
	ArrayList<String> list;
public AUCChart(String title,ArrayList<String> list){
	super(title);
	this.list = list;
	JFreeChart lineChart = ChartFactory.createLineChart(title,"Technique Name","Accuracy Value",createDataset(),PlotOrientation.VERTICAL,true,true,false);
	ChartPanel chartPanel = new ChartPanel( lineChart );
    chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
    setContentPane( chartPanel );
}
public void windowClosing(WindowEvent we){
	this.setVisible(false);
}
private DefaultCategoryDataset createDataset(){
	DefaultCategoryDataset dataset = new DefaultCategoryDataset( );
    for(int i=0;i<list.size();i++){
		String arr[] = list.get(i).split(",");
		dataset.addValue(Double.parseDouble(arr[0]),"AUC",arr[1]);
	}
    return dataset;
}
public static void main(String args[]){
	ArrayList<String> list = new ArrayList<String>();
	list.add("75,SMTP Accuracy");
	list.add("63,ED Accuracy");
	list.add("50,Kmeans Cosine Accuracy");
	list.add("50,Kmeans SMTP Accuracy");
	AUCChart chart = new AUCChart("Accuracy Chart",list);
	chart.pack();
	RefineryUtilities.centerFrameOnScreen(chart);
	chart.setVisible(true);
}
}