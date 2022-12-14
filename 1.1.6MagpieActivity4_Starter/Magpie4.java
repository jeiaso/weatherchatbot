/**
 * A program to carry on conversations with a human user.
 * This version:
 *<ul><li>
 * 		Uses advanced search for keywords 
 *</li><li>
 * 		Will transform statements as well as react to keywords
 *</li></ul>
 * @author Laurie White
 * @version April 2012
 *
 */
import java.util.Scanner;

public class Magpie4
{
	/**
	 * Get a default greeting 	
	 * @return a greeting
	 */	
	public String getGreeting()
	{
		return "Hello, how are you?";
	}
	
	/**
	 * Gives a response to a user statement
	 * 
	 * @param statement
	 *            the user statement
	 * @return a response based on the rules given
	 */
	public String getResponse(String statement)
	{
		String response = "";
		String lastChar = statement.substring(statement.length() - 1);
		//char lastChar = statement.charAt(statement.length() - 1);
		//char questionMark = '?';
		if (statement.length() == 0)
		{
			response = "Say something, please.";
		}

		else if (findKeyword(statement, "no") >= 0)
		{
			response = "Why so negative?";
		}

		// topic specific dialogue 
		else if (findKeyword(statement, "I'm") >= 0)
		{
			response = "That's good! How was the weather today?";
		}
		else if (findKeyword(statement, "weather was") >= 0)
		{
			response = "Nice! The weather the week of halloween was really cold; do you prefer hot or cold weather?";
		}

		// choice
		else if (findKeyword(statement, "cold weather") >= 0)
		{
			response = "Really? Me too! I'm glad it's finally winter. Why?";
		}
		else if (findKeyword(statement, "hot weather") >= 0)
		{
			response = "Really? I prefer the cold! Why do you like hot weather?";
		}

		else if (findKeyword(statement, "rhyme") >= 0)
		{
			response = "Weather rhyme time! When it's cloudy I get pouty!";
		}

		else if (findKeyword(statement, "rain") >= 0)
		{
			response = "A lot of people have different thoughts about the rain! Some think it's refreshing while others think it's sad.";
		}

		else if (findKeyword(statement, "your") >= 0)
		{
			response = "I would rather learn about you than talk about me!";
		}

		else if (lastChar.equals("?"))
		{
			response = getClarification();
		}

		else if (findKeyword(statement, "mother") >= 0
				|| findKeyword(statement, "father") >= 0
				|| findKeyword(statement, "sister") >= 0
				|| findKeyword(statement, "brother") >= 0)
		{
			response = "Tell me more about your family.";
		}

		// Responses which require transformations
		else if (findKeyword(statement, "I want to", 0) >= 0)
		{
			response = transformIWantToStatement(statement);
		}

		else if (findKeyword(statement, "hurricane") >= 0){
			response = "Hmmm. That's interesting! Did you know two hurricanes can merge together and become one!?";
		}
		else if (findKeyword(statement, "rain") >= 0){
			response = "Rain is one of my favorites! Something I think is cool is that raindrops are shaped more like hamburger buns. As a rain drop falls, it becomes less spherical in shape and becomes more flattened on the bottom like a hamburger bun.";
		}
		else if (findKeyword(statement, "sun") >= 0){
			response = "Woah! That's fascinating! By the way, did you know it would take 1.3 million earths to fill up the sun!? ";
		}
		else if (findKeyword(statement, "warm") >= 0){
			response = "Woah! That's fascinating! By the way, did you know it would take 1.3 million earths to fill up the sun!? ";
		}
		else if (findKeyword(statement, "hot") >= 0){
			response = "Woah! That's fascinating! By the way, did you know it would take 1.3 million earths to fill up the sun!? ";
		}
		else if (findKeyword(statement, "snow") >= 0){
			response = "The cold? Interesting! Something I think that's interesing is it takes about an hour for snowflakes to fall to the ground!";
		}
		else if (findKeyword(statement, "cold") >= 0){
			response = "The cold? Interesting! Something I think that's interesing is it takes about an hour for snowflakes to fall to the ground!";
		}
		else if (findKeyword(statement, "tornado") >= 0){
			response = "Ooo tornadoes? That's very cool! In the whole world, the US gets the most tornadoes but what I find is interesting is that England is number three on that list!";
		}

		else
		{
			// Look for a two word (you <something> me)
			// pattern
			int psn = findKeyword(statement, "you", 0);

			if (psn >= 0
					&& findKeyword(statement, "me", psn) >= 0)
			{
				response = transformYouMeStatement(statement);
			}
			else
			{
				final int randRandom = 2;
				double r = Math.random();
				int question = (int)(r * randRandom);
				if (question == 0){
					response = getRandomResponse();
				}
				else{
					response = getRandomQuestion();
				}
			}
		}

		return response;
	}
	
	/**
	 * Take a statement with "I want to <something>." and transform it into 
	 * "What would it mean to <something>?"
	 * @param statement the user statement, assumed to contain "I want to"
	 * @return the transformed statement
	 */
	private String transformIWantToStatement(String statement)
	{
		//  Remove the final period, if there is one
		statement = statement.trim();
		String lastChar = statement.substring(statement
				.length() - 1);
		if (lastChar.equals("."))
		{
			statement = statement.substring(0, statement
					.length() - 1);
		}
		int psn = findKeyword (statement, "I want to", 0);
		String restOfStatement = statement.substring(psn + 9).trim();
		return "What would it mean to " + restOfStatement + "?";
	}

	
	
	/**
	 * Take a statement with "you <something> me" and transform it into 
	 * "What makes you think that I <something> you?"
	 * @param statement the user statement, assumed to contain "you" followed by "me"
	 * @return the transformed statement
	 */
	private String transformYouMeStatement(String statement)
	{
		//  Remove the final period, if there is one
		statement = statement.trim();
		String lastChar = statement.substring(statement
				.length() - 1);
		if (lastChar.equals("."))
		{
			statement = statement.substring(0, statement
					.length() - 1);
		}
		
		int psnOfYou = findKeyword (statement, "you", 0);
		int psnOfMe = findKeyword (statement, "me", psnOfYou + 3);
		
		String restOfStatement = statement.substring(psnOfYou + 3, psnOfMe).trim();
		return "What makes you think that I " + restOfStatement + " you?";
	}
	
	

	
	
	/**
	 * Search for one word in phrase.  The search is not case sensitive.
	 * This method will check that the given goal is not a substring of a longer string
	 * (so, for example, "I know" does not contain "no").  
	 * @param statement the string to search
	 * @param goal the string to search for
	 * @param startPos the character of the string to begin the search at
	 * @return the index of the first occurrence of goal in statement or -1 if it's not found
	 */
	private int findKeyword(String statement, String goal, int startPos)
	{
		String phrase = statement.trim();
		//  The only change to incorporate the startPos is in the line below
		int psn = phrase.toLowerCase().indexOf(goal.toLowerCase(), startPos);
		
		//  Refinement--make sure the goal isn't part of a word 
		while (psn >= 0) 
		{
			//  Find the string of length 1 before and after the word
			String before = " ", after = " "; 
			if (psn > 0)
			{
				before = phrase.substring (psn - 1, psn).toLowerCase();
			}
			if (psn + goal.length() < phrase.length())
			{
				after = phrase.substring(psn + goal.length(), psn + goal.length() + 1).toLowerCase();
			}
			
			//  If before and after aren't letters, we've found the word
			if (((before.compareTo ("a") < 0 ) || (before.compareTo("z") > 0))  //  before is not a letter
					&& ((after.compareTo ("a") < 0 ) || (after.compareTo("z") > 0)))
			{
				return psn;
			}
			
			//  The last position didn't work, so let's find the next, if there is one.
			psn = phrase.indexOf(goal.toLowerCase(), psn + 1);
			
		}
		
		return -1;
	}
	
	/**
	 * Search for one word in phrase.  The search is not case sensitive.
	 * This method will check that the given goal is not a substring of a longer string
	 * (so, for example, "I know" does not contain "no").  The search begins at the beginning of the string.  
	 * @param statement the string to search
	 * @param goal the string to search for
	 * @return the index of the first occurrence of goal in statement or -1 if it's not found
	 */
	private int findKeyword(String statement, String goal)
	{
		return findKeyword (statement, goal, 0);
	}
	


	/**
	 * Pick a default response to use if nothing else fits.
	 * @return a non-committal string
	 */
	private String getRandomResponse()
	{
		final int NUMBER_OF_RESPONSES = 6;
		double r = Math.random();
		int whichResponse = (int)(r * NUMBER_OF_RESPONSES);
		String response = "";
		
		if (whichResponse == 0)
		{
			response = "Interesting, tell me more.";
		}
		else if (whichResponse == 1)
		{
			response = "Hmmm.";
		}
		else if (whichResponse == 2)
		{
			response = "Do you really think so?";
		}
		else if (whichResponse == 3)
		{
			response = "You don't say.";
		}
		else if (whichResponse == 4)
		{
			response = "No way!";
		}
		else if (whichResponse == 5)
		{
			response = "Wow! How was the weather today?";
		}

		return response;
	}

private String getClarification()
{
	String response = "I'm sorry, could you repeat that?";
	return response;
}

private String getFact(String statement)
{
	String response = "";
	if (findKeyword(statement, "hurricane") >= 0){
		response = "Hmmm. That's interesting! Did you know two hurricanes can merge together and become one!?";
	}
	else if (findKeyword(statement, "rain") >= 0){
		response = "Rain is one of my favorites! Something I think is cool is that raindrops are shaped more like hamburger buns. As a rain drop falls, it becomes less spherical in shape and becomes more flattened on the bottom like a hamburger bun.";
	}
	else if (findKeyword(statement, "sun") >= 0){
		response = "Woah! That's fascinating! By the way, did you know it would take 1.3 million earths to fill up the sun!? ";
	}
	else if (findKeyword(statement, "warm") >= 0){
		response = "Woah! That's fascinating! By the way, did you know it would take 1.3 million earths to fill up the sun!? ";
	}
	else if (findKeyword(statement, "hot") >= 0){
		response = "Woah! That's fascinating! By the way, did you know it would take 1.3 million earths to fill up the sun!? ";
	}
	else if (findKeyword(statement, "snow") >= 0){
		response = "The cold? Interesting! Something I think that's interesing is it takes about an hour for snowflakes to fall to the ground!";
	}
	else if (findKeyword(statement, "cold") >= 0){
		response = "The cold? Interesting! Something I think that's interesing is it takes about an hour for snowflakes to fall to the ground!";
	}
	else if (findKeyword(statement, "tornado") >= 0){
		response = "Ooo tornadoes? That's very cool! In the whole world, the US gets the most tornadoes but what I find is interesting is that England is number three on that list!";
	}
	return response; 
}

private String getRandomQuestion()
{
	final int NUMBER_OF_RESPONSES = 6;
	double r = Math.random();
	int question = (int)(r * NUMBER_OF_RESPONSES);
	String response = "";
	
	if (question == 0)
	{
		response = "How is the weather looking next week?";
	}
	else if (question == 1)
	{
		response = "Tell me a weather rhyme! My friend told me that in the sun we have fun, and in the rain we feel pain LOL";
	}
	else if (question == 2)
	{
		response = "What is your favorite or least favorite season?";
	}
	else if (question == 3)
	{
		response = "Do you like the rain?";
	}
	else if (question == 4)
	{
		response = "What is the best food to eat during winter?";
	}
	else if (question == 5)
	{
		response = "What do you eat to cool down in the summer?";
	}

	return response;
}

}
