package cluster;
import java.util.ArrayList;
public class KNN{
	static double tp,fn,tn,fp,precision,recall;
	static double precision1,recall1;
	static int s1,s2;
public static void classifySMTP(Vector vector,ArrayList<KNNData> test){
	ArrayList<String> classify = new ArrayList<String>();
	String file = "";
	double vec1[] = new double[vector.unique_att.size()];
	for(int i=0;i<test.size();i++){
		KNNData obj = test.get(i);
		double distance = 0;
		for(int j=0;j<vector.vector.size();j++){
			VectorModel vm1 = vector.vector.get(j);
			for(int k=0;k<vector.unique_att.size();k++){
				vec1[k] = vector.matrix[j][k];
			}
			double sim = smtp(vec1,obj.getVector());
			if(sim > distance){
				distance = sim;
				file = vm1.filename;
			}
		}
		if(distance > 0){
			classify.add(file+","+obj.getFile()+","+distance);
		}
	}
	s1 = classify.size();
	double tsize = (double)test.size();
	double csize = (double)classify.size();
	double vsize = (double)vector.vector.size();
	tp = csize/tsize;
	fn = (tsize - csize)/tsize;
	tn = (vsize - csize)/vsize;
	fp = csize/vsize;
	precision = tp/(tp+fp);
	recall = tp/(tp+fn);
	System.out.println("smtp "+tp+" "+fp+" "+fn+" "+precision+" "+recall);
	ClassifyResult cr = new ClassifyResult(classify,"SMTP Classification Result");
	cr.setVisible(true);
	cr.setSize(600,400);
}

public static void classifyEd(Vector vector,ArrayList<KNNData> test){
	ArrayList<String> classify = new ArrayList<String>();
	String file = "";
	double vec1[] = new double[vector.unique_att.size()];
	for(int i=0;i<test.size();i++){
		KNNData obj = test.get(i);
		double distance = 0;
		for(int j=0;j<vector.vector.size();j++){
			VectorModel vm1 = vector.vector.get(j);
			for(int k=0;k<vector.unique_att.size();k++){
				vec1[k] = vector.matrix[j][k];
			}
			double sim = EuclideanDistance(vec1,obj.getVector());
			if(sim > distance){
				distance = sim;
				file = vm1.filename;
			}
		}
		if(distance > 0){
			classify.add(file+","+obj.getFile()+","+distance);
		}
	}
	s2 = classify.size();
	double tsize = (double)test.size();
	double csize = (double)classify.size();
	double vsize = (double)vector.vector.size();
	tp = csize/tsize;
	fn = (tsize - csize)/tsize;
	tn = (vsize - csize)/vsize;
	fp = csize/vsize;
	precision1 = tp/(tp+fp);
	recall1 = tp/(tp+fn);
	System.out.println("ed "+tp+" "+fp+" "+fn+" "+precision1+" "+recall1);
	ClassifyResult cr = new ClassifyResult(classify,"ED Classification Result");
	cr.setVisible(true);
	cr.setSize(600,400);
}
public static boolean isMatrixContains(double d1, double d2){
	if(d1 > 0.4 && d2 > 0.4)
		return true;
	else
		return false;
}
public static double smtp(double[] vector1,double[] vector2){
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
public static double EuclideanDistance(double[] vector1, double[] vector2) {
	double distance = 0;
	for(int i=0;i<vector1.length;i++){
		if(isMatrixContains(vector2[i],vector1[i])){
			double diff = vector2[i] - vector1[i];
			distance += diff * diff;
		}
	}
    return distance;
}
}