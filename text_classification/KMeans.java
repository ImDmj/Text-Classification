package cluster;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.HashSet;
import java.util.Arrays;
import java.util.TreeMap;
import java.util.Iterator;
import java.util.Set;
import java.io.FileWriter;
public class KMeans
{
	ArrayList<String[]> documents = new ArrayList<String[]>();
    ArrayList<File> files = new ArrayList<File>();
    ArrayList<String> uniquewords;
	ArrayList<double[]> vector = new ArrayList<double[]>();
	ArrayList<double[]> pick = new ArrayList<double[]>();
	ArrayList<String> cls = new ArrayList<String>();
	double acc;
public void clear(){
	documents.clear();
	files.clear();
	if(uniquewords != null){
		uniquewords.clear();
	}
	vector.clear();
	cls.clear();
}

public void readDocuments(File folder)throws Exception{
	clear();
	File list[] = folder.listFiles();
	for(int i=0;i<list.length;i++){
		files.add(list[i]);
	}
	HashSet<String> hs = new HashSet<String>();
	for(File file : files){
		BufferedReader br = new BufferedReader(new FileReader(file));
		String line = null;
		StringBuilder buffer = new StringBuilder();
		while((line = br.readLine())!=null){
			buffer.append(line);
		}
		br.close();
		String[] tokens = buffer.toString().replaceAll("[\\W&&[^\\s]]","").split("\\W+");
		for(String terms : tokens){
			hs.add(terms);
		}
		documents.add(tokens);
	}
	uniquewords = new ArrayList<String>(hs);
}
public ArrayList<double[]> getVector(){
	return pick;
}
public void generateVector(){
	for(String[] tokens : documents){
		double[] tfidf = new double[uniquewords.size()];
		for(int i=0;i<uniquewords.size();i++){
			tfidf[i] = getTF(tokens,uniquewords.get(i)) * getIDF(documents,uniquewords.get(i));
		}
		vector.add(tfidf);
	}
}
public double getTF(String[] document, String word){
	double count = 0;
	for(String term : document){
		if(term.equalsIgnoreCase(word))
			count++;
	}
    return count/document.length;
}
public double getIDF(ArrayList<String[]> document, String word){
	double count = 0;
	for(String[] doc : document){
		for(String term : doc){
			if(term.equalsIgnoreCase(word)){
				count++;
				break;
			}
		}
	}
    return Math.log(document.size()/count);
}
//cosine sim
public double getSimilarity(double[] vector1, double[] vector2){
	double dot = 0, magnitude1 = 0, magnitude2=0;
	for(int i=0;i<vector1.length;i++){
		dot+=vector1[i]*vector2[i];
		magnitude1+=Math.pow(vector1[i],2);
    	magnitude2+=Math.pow(vector2[i],2);
	}
	magnitude1 = Math.sqrt(magnitude1);
    magnitude2 = Math.sqrt(magnitude2);
    double d = dot / (magnitude1 * magnitude2);
    return d == Double.NaN ? 0 : d;
}

public void kmeans(){
	HashMap<double[],TreeSet<Integer>> clusters = new HashMap<double[],TreeSet<Integer>>();
	HashMap<double[],TreeSet<Integer>> step = new HashMap<double[],TreeSet<Integer>>();
	HashSet<Integer> random = new HashSet<Integer>();
	TreeMap<Double,HashMap<double[],TreeSet<Integer>>> clusters_sim = new TreeMap<Double,HashMap<double[],TreeSet<Integer>>>();
	int k = 3;
	int maxiter = 20;
	for(int i=0;i<20;i++){
		System.out.print(i+" ");
		clusters.clear();
	    step.clear();
	    random.clear();
		while(random.size() < k){
			random.add((int)(Math.random()*vector.size()));
		}
		for(int r : random){
			double[] temparray = new double[vector.get(r).length];
			System.arraycopy(vector.get(r),0,temparray,0,temparray.length);
			step.put(temparray,new TreeSet<Integer>());
		}
		boolean flag = true;
		int iter = 0;
		while(flag){
			clusters = new HashMap<double[],TreeSet<Integer>>(step);
			for(int p=0;p<vector.size();p++){
				double[] centroid = null;
				double similarity = 0;
				for(double[] cent : clusters.keySet()){
					double csimilarity = getSimilarity(vector.get(p),cent);
					if(csimilarity > similarity){
						similarity = csimilarity;
						centroid = cent;
					}
				}
				if(clusters.get(centroid) != null)
				clusters.get(centroid).add(p);
			}
		    step.clear();
		    for(double[] centroid : clusters.keySet()){
				double[] change_centroid = new double[centroid.length];
				for(int d : clusters.get(centroid)){
					double[] doc = vector.get(d);
					for(int p=0;p<change_centroid.length;p++)
						change_centroid[p]+=doc[p];
				}
				for(int p=0;p<change_centroid.length;p++){
					change_centroid[p]/=clusters.get(centroid).size();
				}
		    	step.put(change_centroid,new TreeSet<Integer>());
			}
			//check break conditions
		    String oldcentroid = "", newcentroid="";
			for(double[] d : clusters.keySet())
				oldcentroid+=Arrays.toString(d);
			for(double[] d: step.keySet())
				newcentroid+=Arrays.toString(d);
		    	if(oldcentroid.equals(newcentroid)) 
					flag = false;
				if(++iter >= maxiter) 
					flag = false;
		    }
			double sumsim = 0;
		    for(double[] cent : clusters.keySet()){
				TreeSet<Integer> cls = clusters.get(cent);
				for(int value : cls){
		    			sumsim+=getSimilarity(cent,vector.get(value));
		    		}
		    	}
				clusters_sim.put(sumsim,new HashMap<double[],TreeSet<Integer>>(clusters));
	}
	double total = 0;
	double len = 0;
	for(double[] cent : clusters_sim.get(clusters_sim.lastKey()).keySet()){
		pick.add(cent);
		StringBuilder sb = new StringBuilder();
		for(int pts : clusters_sim.get(clusters_sim.lastKey()).get(cent)){
			sb.append(files.get(pts).getName().substring(0,files.get(pts).getName().lastIndexOf(".txt"))+",");
			total = total + 0.8;
			System.out.println(len+" "+files.get(pts).getName());
			len = len + 1;
		}
		if(sb.length() > 0){
			sb.deleteCharAt(sb.length()-1);
			cls.add(sb.toString());
		}
	}
	double size = files.size()+files.size();
	acc = len/(double)files.size();
	System.out.println("Total acc kmeans "+acc+" "+files.size()+" "+len);
}
}

    