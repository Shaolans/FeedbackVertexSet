package algorithms;

import java.awt.Point;
import java.util.ArrayList;

public class DefaultTeam {
  Evaluation evaluator = new Evaluation();
  public ArrayList<Point> calculFVS(ArrayList<Point> points) {
	  
    ArrayList<Point> fvs = greedyAlgorithm(points);
    fvs = localSearchFSV(points, fvs);
    ArrayList<Point> tmp;
    boolean change = true;

    while(change) {
    	change = false;
    	tmp = localSearchFSV(points, fvs);
    	if(tmp.size() < fvs.size()) {
    		fvs = tmp;
    		change = true;
    	}
    	System.out.println("LA");
    	
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
  
  public ArrayList<Point> localSearchFSV(ArrayList<Point> points, ArrayList<Point> fsv){
	  for(int i = 0; i < fsv.size(); i++) {
		  for(int j = 0; j < fsv.size(); j++) {
			  if(i == j) continue;
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
 