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

public class Quizer2{
	
	// TODO: Time propogation of error. You think you know it, but you're bound to forget it over time. 
	
	public static void main(String args[])throws IOException{
		System.out.println("??????Quizzer??????");
		System.out.println("Version 1.1");
		System.out.println("Author: Feni Varughese");
		System.out.println("Quizzer: A simple quiz program ");
		System.out.println("Instructions:"+"\n"+"The program will display a word");
		System.out.println("Press enter when you are ready to see the meaning");
		System.out.println("Provide the program with feedback on how hard you thought the word was.\n");
		
		
		Scanner input = new Scanner(new File("dataset.txt"));
		ArrayList questions = new ArrayList<Question>();
		String temp = "";
		
		int counter = 0;
		while(input.hasNext()){
			temp = input.nextLine();
			counter++;
			if(!temp.startsWith("*")){ // Make sure it's not a comment..
				StringTokenizer st = new StringTokenizer(temp,".");
				String question = st.nextToken();
				String answer = st.nextToken();
				String priority = st.nextToken();
				Question q = new Question(question,answer,(int)pi(priority.trim()));
				questions.add(q);
			}
		}
		
		System.out.println("Done parsing the file successfully...");
		input = new Scanner(System.in);	 
		// Loops through the questions infinetly
		Random rand = new Random();
		while(questions.size() > 0){
			boolean valid = false;
			int count = 0;
			Question current = new Question("No Questions Found","No Answers Found",9);
			while(!valid){
				int loc = rand.nextInt(questions.size());
				
				System.out.print("Question ("+loc+") : " );
				
				current = (Question) questions.get(loc);
				if(current.getPriority() > 0){	valid = true;	}
				else{	questions.remove(loc);	}
				count++;
				if(count > 25){ // Taking too long to find a valid question... Exit
					valid = true;
				}
			}
			// Print out the word
			System.out.print(current+"?   ");
			input.nextLine();
			System.out.print("Answer: "+current.getAnswer()+"    ");
			String str = input.nextLine();
			if(!str.equals("") && isValidNum(str)){
				int priority = Integer.parseInt(str);
				current.setPriority(priority);
			}
			System.out.println();
		}
		
		// TODO: make a save method that saves the state of the questions with their respective
		// priorities...
	}

	public static int pi(String s)	{	return Integer.parseInt(s);	}
	
	public static boolean isValidNum(String str){
		if(str.length() == 1)
			return Character.isDigit(str.charAt(0));
		return false;
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
	public String toString(){	return ""+priority+". "+question;	}
	public String toString2(){	return ""+question;	}
	public String getAnswer(){	return answer;	}
	public int getPriority(){	return priority;	}
	public void setPriority(int p){	priority = (priority+p)/2;	}
}