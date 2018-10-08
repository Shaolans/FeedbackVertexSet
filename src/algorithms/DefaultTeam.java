package algorithms;

import java.awt.Point;
import java.util.ArrayList;

public class DefaultTeam {
  Evaluation evaluator = new Evaluation();
  public ArrayList<Point> calculFVS(ArrayList<Point> points) {
	  
    //ArrayList<Point> fvs = greedyAlgorithm(points);
	ArrayList<Point> fvs, tmp;
	fvs = greedyAlgorithmAlea(points);
	for(int i = 0; i < 10000; i++) {
		tmp = greedyAlgorithmAlea(points);
		if(tmp.size() < fvs.size()) {
			fvs = tmp;
		}
	}
    fvs = localSearchFSV1(points, fvs);

    boolean change = true;

    while(change) {
    	change = false;
    	tmp = localSearchFSV1(points, fvs);
    	if(tmp.size() < fvs.size()) {
    		fvs = tmp;
    		change = true;
    	}
    	
    }
    
    
    fvs = localSearchFSV2(points, fvs);
    change = true;

    while(change) {
    	change = false;
    	tmp = localSearchFSV2(points, fvs);
    	if(tmp.size() < fvs.size()) {
    		fvs = tmp;
    		change = true;
    	}
    	
    }
    return fvs;
  }
  
  
  public ArrayList<Point> greedyAlgorithm(ArrayList<Point> points){
	  ArrayList<Point> set = new ArrayList<Point>();
	  ArrayList<Point> result = new ArrayList<Point>(points);
	  while(!evaluator.isValide(points, set)) {
		  set.add(result.remove(maxNeighbor(result)));
	  }
	  return set;
  }
  
  
  
  public ArrayList<Point> greedyAlgorithmAlea(ArrayList<Point> points){
	  ArrayList<Point> set = new ArrayList<Point>();
	  ArrayList<Point> result = new ArrayList<Point>(points);
	  while(!evaluator.isValide(points, set)) {
		  set.add(result.remove(randomSelect(result)));
	  }
	  return set;
  }
  
  public int randomSelect(ArrayList<Point> points) {
	  int max =  maxNeighbor(points);
	  if(Math.random() < 0.9) {
		  return max;
	  }
	  ArrayList<Point> copy = new ArrayList<>(points);
	  copy.remove(max);
	  return maxNeighbor(copy);
  }
  
  public int maxNeighbor(ArrayList<Point> points) {
	  int max = 0;
	  int maxneighbor = 0;
	  int tmpneighbor;
	  for(int i = 0; i < points.size(); i++) {
		  tmpneighbor = evaluator.neighbor(points.get(i), points).size();
		  if(tmpneighbor > maxneighbor) {
			  max = i;
			  maxneighbor = tmpneighbor;
		  }
	  }
	  return max;
  }
  
  public ArrayList<Point> localSearchFSV1(ArrayList<Point> points, ArrayList<Point> fsv){
	  for(int i = 0; i < fsv.size(); i++) {
		  ArrayList<Point> tmpfsv = new ArrayList<>(fsv);
		  tmpfsv.remove(fsv.get(i));
		  if(evaluator.isValide(points, tmpfsv)) return tmpfsv;
	  }
	  return fsv;
  }
  
  
  
  public ArrayList<Point> localSearchFSV2(ArrayList<Point> points, ArrayList<Point> fsv){
	  for(int i = 0; i < fsv.size(); i++) {
		  for(int j = i+1; j < fsv.size(); j++) {
			  for(int k = 0; k < points.size(); k++) {
				  if(points.get(k) == fsv.get(j) || points.get(k) == fsv.get(i)) continue;
				  ArrayList<Point> tmpfsv = new ArrayList<>(fsv);
				  tmpfsv.remove(fsv.get(i));
				  tmpfsv.remove(fsv.get(j));
				  tmpfsv.add(points.get(k));
				  if(evaluator.isValide(points, tmpfsv)) return tmpfsv;
				  
			  }
		  }
	  }
	  return fsv;
  }
  
  public Point detectLoop(ArrayList<Point> origPoints){
	    ArrayList<Point> vertices = new ArrayList<Point>();

	    //Looking for loops in subgraph induced by origPoint \setminus fvs
	    while (!vertices.isEmpty()){
	      ArrayList<Point> green = new ArrayList<Point>();
	      green.add((Point)vertices.get(0).clone());
	      ArrayList<Point> black = new ArrayList<Point>();

	      while (!green.isEmpty()){
	        for (Point p:evaluator.neighbor(green.get(0),vertices)){
	          if (green.get(0).equals(p)) continue;
	          if (evaluator.isMember(black,p)) return p;
	          if (evaluator.isMember(green,p)) return p;
	          green.add((Point)p.clone());
	        }
	        black.add((Point)green.get(0).clone());
	        vertices.remove(green.get(0));
	        green.remove(0);
	      }
	    }

	    return null;
	  }
  
}
 