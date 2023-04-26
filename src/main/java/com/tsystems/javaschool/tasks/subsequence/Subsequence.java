package com.tsystems.javaschool.tasks.subsequence;

import java.util.ArrayList; 
import java.util.List;
import java.util.Collections;

public class Subsequence {

    /**
     * Checks if it is possible to get a sequence which is equal to the first
     * one by removing some elements from the second one.
     *
     * @param x first sequence
     * @param y second sequence
     * @return <code>true</code> if possible, otherwise <code>false</code>
     */
    @SuppressWarnings("rawtypes")
    public boolean find(List x, List y) {
		
		boolean fine = true;
		boolean terminate = false;
		int i,j;
		ArrayList<ArrayList<Integer>> history = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> aux_list, best_combination = new ArrayList<Integer>();
			
			//starting with some trivial conditions
			if(x == null || y == null){throw new IllegalArgumentException();}
			if (x.size() > y.size()){fine=false; terminate=true;}
			else if ((x.isEmpty() && y.isEmpty())){fine=true; terminate=true;}
			else if ((x.isEmpty() && !y.isEmpty())){fine=true; terminate=true;}
			else if(fine && !terminate){ 	
				for (i=0; i<x.size(); i++){
					
					aux_list = new ArrayList<Integer>();
					for (j=0; j<y.size(); j++){
						
						if(x.get(i) == y.get(j))
						{
							aux_list.add(j);
						}
						
					}
					history.add(aux_list);
				}
				
				//now if there's any empty list on history ==> we straigthforward output false
				for (i=0; i<history.size(); i++){
					if (history.get(i).isEmpty())
					{
						fine = false;
					}				
				}
				
				if(fine)
				{
					//in history we have in the i-th position, a list of the positions in which x(i) appears in y, 
					//now we just have to analyze this list to determine if x can be built from y
					
					//we could answer definetively a YES to the answer if we could choose certain locations of history for each sublist and sort them
					//ascending. For that to be more probable, we could choose the smallest position for the first sublist of history and the highest for the latest:
					
					//first element
					best_combination.add(history.get(0).get(0));
					
					//elements in-between
					for (i=1; i<history.size()-1; i++){
						
						if (!fine){break;}
						for (j=0; j<history.get(i).size(); j++){
							
							if(history.get(i).get(j) < best_combination.get(i-1))
							{fine = false;}
							else{
								fine = true;
								best_combination.add(history.get(i).get(j));
								break;
							}			
						}			
					}
					
					if (fine){				
					//last element
					best_combination.add(history.get(history.size()-1).get(history.get(history.size()-1).size()-1));
					
					aux_list = best_combination;
					Collections.sort(aux_list);
					//if the list is not ordered by adding this final element then it return false
					if(!best_combination.equals(aux_list)) {fine = false;}			
					}
				}
			}
		
					
		return fine;	
	}
			     
}
