package com.tsystems.javaschool.tasks.calculator;
import java.util.ArrayList; 
import java.lang.Math;

public class Calculator {

    /*
		The code is divided into 4 functions: the main function and 3 complementaries.
		*step1 refers to the first step we have to make to solve a mathematical statement: solve parenthesis
		*step2 solves then multiplications and divisions from left to right
		*step3 finally solves sums and substractions from left to right
		*the main function looks for quick bad formatting and invokes the rest
    */
	
	public Float step3(String str) {
		
		ArrayList<Float> numbers = new ArrayList<Float>();
		ArrayList<String[]> operations = new ArrayList<>();
		String[] op_aux;
		String str_aux;
		int aux_pos = 0;
		int i;
		float result;
		
		if(str != null)
		{
	
			for (i=1; i<str.length(); i++){		
				if (str.charAt(i) == '+' || str.charAt(i) == '-'){ // + and - signs are char type
						
						//inserting into operations a new array: the operation itself and its position
						op_aux = new String[] {String.valueOf(str.charAt(i)), Integer.toString(i)};
						operations.add(op_aux);
				}
			}
			
			for (i=0; i<operations.size(); i++){
				
					//Inserting each number into numbers
					numbers.add(Float.parseFloat(str.substring(aux_pos, Integer.parseInt(operations.get(i)[1]))));
					aux_pos = Integer.parseInt(operations.get(i)[1])+1;
			}		
			//appending the last number
			numbers.add(Float.parseFloat(str.substring(aux_pos, str.length())));
			
			result = numbers.get(0);
			for (i=0; i<operations.size(); i++){
				if(operations.get(i)[0].equals("+")){ // + sign is a string
					result += numbers.get(i+1);
				} else {
					result -= numbers.get(i+1);
				}	
			}
			
			return Math.round(result*10000)/10000f;

		}else{return null;}
	}
	
	public Float step2(String str) {
		
		int op_pos_left=0, op_pos_right=0;
		int step_l, step_r;
		int i;
		float result_i;
		String str_in = str;
		boolean fine = true;
		
		
		for (i=0; i<str_in.length(); i++){
			
			step_l = 1; 
			if (str_in.length()-1-i == 1){step_r = 1;}else{step_r=2;}
			result_i = 0f;
			
			//looking for any multiplications or divisions
			if (str_in.charAt(i) == '*' || str_in.charAt(i) == '/'){
				
				//while looping to find the closest operation
				while( (str_in.charAt(i-step_l) != '+') && (str_in.charAt(i-step_l) != '-')) {
					if(i-step_l == 0) {break;} else {step_l += 1;}
				}
				
				while( (str_in.charAt(i+step_r) != '+') && (str_in.charAt(i+step_r) != '-') && (str_in.charAt(i+step_r) != '*') && (str_in.charAt(i+step_r) != '/')) {
					if(i+step_r == str_in.length()-1) {break;} else {step_r += 1;}
				}
				
				//storing positions to get the numbers later
				if (i-step_l != 0) {op_pos_left = i-step_l+1;}else{op_pos_left=0;}
				if (i+step_r != str_in.length()-1) {op_pos_right = i+step_r;} else {op_pos_right =  str_in.length();}
				
				
				//effectuate the operation
				if (str_in.charAt(i) == '*'){
					result_i = Float.parseFloat(str_in.substring(op_pos_left, i))*Float.parseFloat(str_in.substring(i+1, op_pos_right));					
				} else {
					
					//if this is a division by 0, the loop is terminated
					if (Float.parseFloat(str_in.substring(i+1, op_pos_right)) == 0 || Float.parseFloat(str_in.substring(i+1, op_pos_right)) == 0.0f)
					{
						fine = false;
						break;
					}	
					else{result_i = Float.parseFloat(str_in.substring(op_pos_left, i))/Float.parseFloat(str_in.substring(i+1, op_pos_right));}
				}
				
				//updating the string
				str_in  = str_in.substring(0, op_pos_left)+String.valueOf(result_i)+str_in.substring(op_pos_right);
			}
		}
		
		if (fine) {return step3(str_in);}
		else {return null;}
	}
	
	public Float step1(String str) {
		
		int i,j;
		int pos_open, pos_close;
		int n_childs, child_close_count;
		String aux_str, sub_str_i;
		String str_in = str;
		float result_i;
			
		
		for (i=0; i<str_in.length(); i++){			
			if (str_in.charAt(i) == '(') {
				
				aux_str = str_in.substring(i+1);
				pos_open = i;
				pos_close = str_in.length();
				child_close_count = 0;
				n_childs = 0;
				
				for (j=0; j<aux_str.length(); j++){ //looping now over a substring which does not contain the initial parenthesis, the goal is to find the closing parenthesis
					
					//store the number of child parenthesises
					if (aux_str.charAt(j) == '(') {
						n_childs+=1;
						continue;
						}
					
					//looking for closes, whenever the main close parenthesis is found, break the loop
					if (aux_str.charAt(j) == ')' && child_close_count < n_childs) {
						child_close_count += 1;
						continue;
					}
					if (aux_str.charAt(j) == ')' && child_close_count == n_childs) {
						pos_close = i+j+1;
						break;
					}
				}
				
				//just the content of this parenthesis
				sub_str_i = str_in.substring(pos_open+1, pos_close);
				
				if (n_childs == 0){
					result_i = step2(sub_str_i); //if no parenthesis childs, the content goes to step 2, then step 1 and we get its nummeric value
				} else {
					result_i = step1(sub_str_i); //by recurrence of this same function we get its value 
				}
				
				//now update the main string by replacing the whole parenthesis with the result
				str_in = str_in.replace(str_in.substring(pos_open, pos_close+1), String.valueOf(result_i));
				
			}						
		}
		
		return step2(str_in);
	}
	
	 
    public String evaluate(String statement) {
        
		int j;
		int open_count=0, close_count=0;
		Float aux_result;
		boolean fine = true;
		Calculator calculator = new Calculator();
		
		//check if there is any doubled symbol, commas, null or empty string
		if (statement != null && statement != ""){
			if(statement != statement.replace("++", "") || statement != statement.replace("--", "") || statement != statement.replace("**", "") || statement != statement.replace("//", "") || statement != statement.replace("..", "") || statement != statement.replace(",", ""))
			{
				fine = false;
			}
		}else{fine=false;}
		
		
		if(fine){
			//check if there's any extra parenthesis
			for (j=0; j<statement.length(); j++){	
				if(statement.charAt(j) == '('){open_count += 1;}
				if(statement.charAt(j) == ')'){close_count += 1;}	
			}
			if(open_count != close_count){fine = false;}
		}
		
		//if this quick comprobations were successful, the code runs
		if (fine){
			
			aux_result = calculator.step1(statement);
			if (aux_result == null){return null;}
			else{		
				j = aux_result.intValue();
				if (j == aux_result) {
					return String.valueOf(j);
				} else {
					return String.valueOf(aux_result);
				}
			}
		}
		else{return null;}
    }

}
