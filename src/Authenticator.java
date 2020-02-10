import java.util.ArrayList;
import java.util.Scanner;
import java.security.SecureRandom;

/**
 * 
 */

/**
 * @author Andhy Gomez
 *
 */
public class Authenticator 
{
	static // Create each array list object
	ArrayList<User> credentials = new ArrayList<User>();
	
	/**
	 * @param args
	 */
	public static void main(String[] args) 
	{	
		// Initialize variables
		String choice;
		String username;
		String password;
		String pwSalt;
		String pwHash;
		
		int userAdded;
				
		// Create a new scanner object
		Scanner keyboard = new Scanner(System.in);
				
		do
		{
			System.out.println("Would you like to");
			System.out.println("1) Add a user \n");
			System.out.println("2) Remove a user \n");
			System.out.println("3) Sign in \n");
			System.out.println("4) Exit the program \n");
				
			choice = keyboard.nextLine();
					
			switch(choice)
			{
				case "1": // Add a user
					// Obtain information
					System.out.print("Enter username: ");
					username = keyboard.nextLine();
					System.out.print("Enter password: ");
					password = keyboard.nextLine();
					
					// Generate salt & hash
					pwSalt = "";
					pwHash = generateHash(password, pwSalt);
							
					// Create new user instance with information entered
					User user = new User(username, pwSalt, pwHash);
							
					// Add user
					userAdded = addUser(user);
						
					break;
				case "2": // Remove a user
					break;
				case "3": // Sign in
						
					break;
				case "4":
					System.out.println("Exiting program. Goodbye.");
					break;
				default:
					System.out.println("Invalid input, please try again.");
					}
					
				}while(!choice.equals("4"));
	}
	
	public static int addUser(User user)
	{
		// Add user
		credentials.add(user);
		
		return 1;	
	}
	
	public static User removeUser(String user)
	{
		return null;
	}
	
	public boolean isUserValid(String username, String password)
	{
		return false;	
	}
	
	public static String generateSalt(String password);
	{
		final int SALTSIZE = 16;
		
		String passwordSalt;	
		
		byte[] arraySalt = new byte[SALTSIZE];
		
		SecureRandom salt = new SecureRandom();
		
		salt.nextBytes(arraySalt);
		
		
		
		return passwordSalt;
	}
	
	public static String generateHash(String password, String passwordSalt)
	{
		String pwConcat;
		char hashToChar;
		int ascii;
		
		pwConcat = password.concat(passwordSalt);
		
        // Create a new int array
        int[] asciiHash = new int[pwConcat.length()];
        
        // Converts string to char array
        char[] stringAsChars = pwConcat.toCharArray();
        
        // Converts hash to chars
        char[] hashAsChars = new char[pwConcat.length()];
        
        for(int charAt = 0; charAt < stringAsChars.length; charAt++)
		{
        	ascii = stringAsChars[charAt];
        	
        	asciiHash[charAt] = ascii + 1;
		}
        
        //System.out.print("Hash: ");
        
        for (int i = 0; i < asciiHash.length; i++) 
        {
        	hashToChar = (char) asciiHash[i];
        	
        	hashAsChars[i] = hashToChar;
        }
        
        String passwordHash = new String(hashAsChars);
        
        return passwordHash;
	}

}
