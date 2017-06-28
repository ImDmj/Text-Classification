package cluster;
import java.util.Comparator;
public class Similarity implements Comparator<Similarity> {
	String source,target;//file anme
	double sim;// similarity value
Similarity(){} // default constructor for sorting
Similarity(String source,String target,double sim){ //user values
	this.source = source;
	this.target = target;
	this.sim = sim;
}
//compare similarity
public int compare(Similarity s1,Similarity s2){
	double sm1 = s1.sim;
    double sm2 = s2.sim;
	if (sm1 == sm2)
		return 0;
    else if (sm1 > sm2)
    	return 1;
    else
		return -1;
}
}