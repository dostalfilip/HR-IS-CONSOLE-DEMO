package com.dostal.demo;
import java.security.SecureRandom;

/*
 * Class support utility methods
 */
class UtilityClass {
	
	/*
	 * input : String and wanted total lenght of string
	 * add spaces to string
	 * return string with spaces
	 */
	static String checkLenght(String input, int totalLenght){
		StringBuilder sb = new StringBuilder();
		sb.append(input);
		while(sb.length() < totalLenght){
			sb.append(" ");
		}	
		return sb.toString();
	}

	 //generate random names
    static String  generateString(){
    	final String AB = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    	SecureRandom rnd = new SecureRandom();
    	int size = 16;

    	StringBuilder sb = new StringBuilder( size );
    	for( int i = 0; i < size ; i++ ) 
    		sb.append( AB.charAt( rnd.nextInt(AB.length())) 
    		);
    	return sb.toString();
    	}
      
    // generate number in interval
    static int generateInt(int downlimit, int uplimit){
    	int randomNumber = (int) (downlimit + (Math.random()*(uplimit - downlimit)));
    	return randomNumber;
    }
}
