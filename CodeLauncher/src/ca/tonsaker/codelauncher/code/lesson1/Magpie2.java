package ca.tonsaker.codelauncher.code.lesson1;

/*
 * ANSWERS TO ACTIVITY 1 <--------------------------------------------------------------------------------------------
 * 
 * My bot: iEinstein
 * 
 * EXPLORATION
 * 1.  The mind of my creator.
 * 2.  According to the big bang theory, the universe began by expanding from an infinitesimal volume with extremely high density and temperature. The universe was initially significantly smaller than even a pore on your skin. With the big bang, the fabric of space itself began expanding like the surface of an inflating balloon ? matter simply rode along the stretching space like dust on the balloon's surface. The big bang is not like an explosion of matter in otherwise empty space; rather, space itself began with the big bang and carried matter with it as it expanded. Physicists think that even time began with the big bang. Today, just about every scientist believes in the big bang model. The evidence is overwhelming enough that in 1951, the Catholic Church officially pronounced the big bang model to be in accordance with the Bible.
 * 3.  Maybe you could try Ask.com - CLICK HERE!
 * 4.  Did you mean to say that?
 * 
 * QUESTIONS
 * 1. The chatbot takes "we" as a keyword.
 * 2. Dog, Cat, Mother, Father, Sister, Cousin, Brother, Aunt, Uncle
 * 
 */

/*
 * ANSWERS TO ACTIVITY 2 <--------------------------------------------------------------------------------------------
 * 
 * START
 * 1.  Tell me more about your family.
 * 2.  Don't be so negative.
 * 3.  Interesting, tell me more.
 * 4.  Tell me more about your family.
 * 
 * EXERCISES
 * 1. Keywords at the top of of the if-else strain are prioritized before others.
 * 
 */

/**
 * A program to carry on conversations with a human user.
 * This is the initial version that:  
 * <ul><li>
 *       Uses indexOf to find strings
 * </li><li>
 * 		    Handles responding to simple words and phrases 
 * </li></ul>
 * This version uses a nested if to handle default responses.
 * @author Laurie White
 * @version April 2012
 */
public class Magpie2{
	/**
	 * Get a default greeting 	
	 * @return a greeting
	 */
	public String getGreeting(){
		return "Hello, let's talk.";
	}
	
	/**
	 * Gives a response to a user statement
	 * 
	 * @param statement
	 *            the user statement
	 * @return a response based on the rules given
	 */
	public String getResponse(String statement){
		
		statement = statement.toLowerCase();
		
		String response = "";
		if (statement.indexOf("no") >= 0){
			response = "Why so negative?";
		}else if(statement.indexOf("mother") >= 0
				|| statement.indexOf("father") >= 0
				|| statement.indexOf("sister") >= 0
				|| statement.indexOf("brother") >= 0){
			response = "Tell me more about your family.";
		}else if(statement.trim().isEmpty()){
			response = "Say something, please.";
		}else if(statement.contains("dog") || statement.contains("cat")){
			response = "Tell me more about your pets.";
		}else if(statement.contains("murtha")){
			response = "He seems like a good teacher.";
		}else if(statement.contains("programming")){
			response = "Tell me more about programming.";
		}else if(statement.contains("java")){
			response = "I hear java is like the best programming language.";
		}else if(statement.contains("you")){
			response = "Why me?";
		}else{
			response = getRandomResponse();
		}
		return response;
	}

	/**
	 * Pick a default response to use if nothing else fits.
	 * @return a non-committal string
	 */
	private String getRandomResponse(){
		final int NUMBER_OF_RESPONSES = 6;
		double r = Math.random();
		int whichResponse = (int)(r * NUMBER_OF_RESPONSES);
		String response = "";
		
		if (whichResponse == 0){
			response = "Interesting, tell me more.";
		}else if (whichResponse == 1){
			response = "Hmmm.";
		}else if (whichResponse == 2){
			response = "Do you really think so?";
		}else if (whichResponse == 3){
			response = "You don't say.";
		}else if (whichResponse == 4){
			response = "I didn't know that.";
		}else if (whichResponse == 5){
			response = "Cool.";
		}

		return response;
	}
}
