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
public class ClassifyChart extends ApplicationFrame{
	int smtp,ed;
public ClassifyChart(String title,int s,int e){
	super(title);
	smtp = s;
	ed = e;
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
    dataset.addValue(smtp,"AUC","smtp classify");
	dataset.addValue(ed,"AUC","Ed classify");
	return dataset;
}
}