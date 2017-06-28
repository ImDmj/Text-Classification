package cluster;
public class KNNData{
	String file;
	double vector[];
public void setFile(String file){
	this.file = file;
}
public String getFile(){
	return file;
}
public void setVector(double vector[]){
	this.vector = vector;
}
public double[] getVector(){
	return vector;
}
}