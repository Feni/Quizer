// Feni Varughese
// Started 4-22-08
////////////////////////////////////////////////////////////////////////////////
// A quiz program that quizes you according to data it finds in a dat file. 
// Capabilities to add in new elements through the command line. 
// A timer counts the amount of time it takes you to answer the question. 
// That data is used to calculate how good you know the answer. 
// The program will be displayed as a multiple choice program where the other
// options are taken from the answers to the other questions
// When the program is started, the options are picked randomly. 
// When the user gets an answer wrong, the wrong answer they pick will be taged
// as related so that it's displayed as an option the next time also. 
// A calculate method will analyze the times the question had been asked and then
// uses that to calculate the order the questions will be asked. 
/////////////////////////////////////////////////////////////////////////////////

import java.io.*;
import java.util.*;
import java.util.Random;

public class quizer{
	
	public static void main(String args[])throws IOException{
		System.out.println("??????Quizzer??????");
		System.out.println("Version 1.0");
		System.out.println("Author: Feni Varughese");
		System.out.println("Quizzer: A simple quiz program ");
		System.out.println("Instructions:"+"\n"+"The program will display a word");
		System.out.println("Press enter when you are ready to see the meaning");
		System.out.println("Provide the program with feedback on how hard you thought the word was.\n");
		
		FileReader inFile = new FileReader("dataset.txt");	
		BufferedReader inStream = new BufferedReader(inFile);
		String temp = "";
		ArrayList questions = new ArrayList<Question>();
		// If file has content and content is not a comment
		int counter = 0;
		while((temp = inStream.readLine()) != null){
			counter++;
		
			// Anything following the * is a comment
			if((temp.charAt(0) != '*')){
				String question = "";
				String answer = "";
				String priority = "";
				int pointer = 0;
				for(int k = 0; k < temp.length(); k++){
					if(temp.charAt(k) != '.'){
						if(pointer == 0){
							question+=temp.charAt(k);
						}
						else if(pointer == 1){
							answer+=temp.charAt(k);
						}
						else if(pointer == 2){
							priority+=temp.charAt(k);
						}
					}
					else{
						pointer++;
					}
				}
				Question q = new Question(question,answer,(int)pi(priority));
				questions.add(q);
//				System.out.print(counter);		  // Debugger statement
//				System.out.println(q.toString2());// Also a debugger
			}
		}
		
		Scanner input = new Scanner(System.in);	 
		// Loops through the questions infinetly
		while(true){
			Random rnd = new Random();
			boolean valid = false;
			int count = 0;
			Question current = new Question("No Questions Found","No Answers Found",9);
			while(!valid){
				int loc = rnd.nextInt(questions.size());
				current = (Question) questions.get(loc);
				if(current.getPriority() > 0){	valid = true;	}
				else{	questions.remove(loc);	}
				count++;
				
				// Just escapes because it's taking too long to find a question
				// A shortcut to avoid infinite looping if there are no priority
				// 0+ Questions
				if(count > 25){
					valid = true;
				}
			}
			// Print out the word
			System.out.print("\n"+current+"?   ");
			input.nextLine();
			System.out.print("Answer: "+current.getAnswer()+"    ");
			String str = input.nextLine();
			if(!str.equals("") && isValidNum(str)){
				int priority = Integer.parseInt(str);
				current.setPriority(priority);
			}
			System.out.println();
		}
	}

	public static int pi(String s)	{	return Integer.parseInt(s);	}
	
	public static boolean isValidNum(String str){
		boolean b = true;
		for(int k = 0; k < str.length(); k++){
			char c = str.charAt(k);
			if(c == '0' || c== '1' || c== '2' || c== '3' || c== '4' 
			|| c== '5' || c== '6' || c== '7' || c== '8' || c== '9'){
			}
			else{	b = false;	}
		}
		// Too long. Has to be from 0-9
		if(str.length() > 1){	b = false;	}
		return b;
	}
}
class Question{
	String question = "";
	String answer = "";
	int priority = 1;
	public Question(String q, String a, int p){
		question = q;
		answer = a;
		priority = p;
	}
	public String toString(){
		return ""+priority+". "+question;
	}
	public String toString2(){
		return ""+question;
	}
	public String getAnswer(){
		return answer;
	}
	public int getPriority(){
		return priority;
	}
	public void setPriority(int p){
		priority = (priority+p)/2;
	}
}