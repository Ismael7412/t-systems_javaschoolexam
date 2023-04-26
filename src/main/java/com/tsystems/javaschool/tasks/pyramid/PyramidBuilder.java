package com.tsystems.javaschool.tasks.pyramid;

import java.util.ArrayList; 
import java.util.List;
import java.util.Collections;

public class PyramidBuilder {

    /**
		*We need to take into account that a pyramid will be possible to be built if the length of the given list belongs to this succession: 
		{1,1+2,1+2+3,...} i.e length=x_n=\sum_{i=1}^n(i) for a given n
		
		*The condition above is actually the only one since we take for granted that only lists of integers will be inputed. No mixed classes 
		nor nulls can be included in a List<Integer> so we don't exclude these cases.
			 
     */
	 
    public int[][] buildPyramid(List<Integer> inputNumbers) {
       
	   int i,j;
	   int current_pos=1, center_pos=0;
	   int length = inputNumbers.size(), rows=0, row_elements=0;
	   int aux = length;
	   boolean fine = true;
	   List<Integer> sorted_input = inputNumbers;
	   ArrayList<Integer> aux_list = new ArrayList<Integer>();
	   ArrayList<ArrayList<Integer>> output = new ArrayList<ArrayList<Integer>>();
	   	   
	   //checking first if the list has a suitable size
	   for (i=1; i<length; i++)
	   {
		  aux-=i;
		  if(aux==0) {rows=i; break;}
		  else if(aux<0) {fine=false; break;}
	   }
	   
	   //checking if all elements are integers
	   for (i=0; i<inputNumbers.size(); i++){
			if (!(inputNumbers.get(i) instanceof Integer)) {
				fine = false;
				break;
			}
        }
	   
	   //if we have a proper input we start bulding the pyramid
	   if (fine){
		   
		   //each sublist will have this elements
		   row_elements = rows + rows-1;
		   
		   //the center position in each row will be
		   center_pos = (row_elements-1)/2;
		   
		   //fill all the sublists with zeros
		   for (i=0; i<rows; i++){
				aux_list = new ArrayList<Integer>();
				for (j=0; j<row_elements; j++){
					aux_list.add(0);
				}
				output.add(aux_list);
			}
		   
		   //ordering the input list ascending
		   Collections.sort(sorted_input);
		   
		   //initializing with the first row
		   output.get(0).set(center_pos, sorted_input.get(0));
		   
		   //filling the pyramid
		   for (i=2; i<=rows; i++){
					for (j=-i+1; j<=i-1; j+=2){
						output.get(i-1).set(center_pos+j, sorted_input.get(current_pos));
						current_pos+=1;
					}			   
		   }
		   
		   //we need to return an int[][] object so we transform it
			int[][] toReturn = new int[rows][row_elements];
			for (i=0; i<rows; i++){
				for(j=0; j<row_elements; j++){
						toReturn[i][j] = output.get(i).get(j);
				}
			}
		   
		   return toReturn;
		   
	    }
		else{
			throw new CannotBuildPyramidException();
			}

    }


}
