import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;


/**
 * @author Andhy Gomez
 * St. Thomas University
 * 
 * Description: Program that will allow a user to either Add a user,
 * remove a user, or sign in.
 */
public class Authenticator 
{
	static // Create array list object
	ArrayList<User> credentials = new ArrayList<User>();
	
	private static final String SALTBANK = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789!@#$~%^&*?.,";
	
	/**
	 * Description: Entry point of program with main menu
	 * 
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
		String userToRemove;
		
		int userAdded;
		
		boolean credentialsValid = false;
				
		// Create a new scanner object
		Scanner keyboard = new Scanner(System.in);
				
		do
		{
			System.out.println("Would you like to");
			System.out.print("1) Add a user \n");
			System.out.print("2) Remove a user \n");
			System.out.print("3) Sign in \n");
			System.out.print("4) Exit the program \n");
				
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
					pwSalt = generateSalt();
					pwHash = generateHash(password, pwSalt);
							
					// Create new user instance with information entered
					User user = new User(username, pwSalt, pwHash);
							
					// Add user
					userAdded = addUser(user);				
					break;
				case "2": // Remove a user
					System.out.println("Enter the username of the user you would like to remove.");
					userToRemove = keyboard.nextLine();
					
					for(User index: credentials)
					{
						String currentUser = index.username;
						
						if(currentUser.equals(userToRemove))
						{
							removeUser(userToRemove);
						}
					}
					break;
				case "3": // Sign in
					// Obtain information
					System.out.print("Enter username: ");
					username = keyboard.nextLine();
					System.out.print("Enter password: ");
					password = keyboard.nextLine();
					
					credentialsValid = isUserValid(username, password);
					
					if(credentialsValid)
					{
						System.out.println("You have successfully signed in.");
					}
					else
					{
						System.out.println("Your credentials were invalid.");
					}
					break;
				case "4":
					System.out.println("Exiting program. Goodbye.");
					break;
				default:
					System.out.println("Invalid input, please try again.");
			}
					
		}while(!choice.equals("4"));
	}
	
	/**
	 * Description: adds a user to arraylist
	 * 
	 * @param user User instance containing username, salt, and hash
	 * @return 1 or 0 if user was added or not
	 */	
	public static int addUser(User user)
	{
		// Add user
		credentials.add(user);
		
		return 1;	
	}
	
	/**
	 * Description: Method removes a desired user
	 * 
	 * @param user
	 * @return null
	 */
	public static User removeUser(String username)
	{
		String userToSearch = username;
		
		for(User index: credentials)
		{
			if(userToSearch.equals(index.username))
			{
				System.out.println("User: " + index.username + ", has been removed.");
				
				credentials.remove(index);
			}
		}
		
		return null;
	}
	
	/**
	 * Description: Checks if user credentials are valid
	 * 
	 * @param username 
	 * @param password
	 * @return true if credentials are valid, false otherwise
	 */	
	public static boolean isUserValid(String username, String password)
	{
		String userSalt;
		String userPw;
		String hash;
		
		boolean isUserValid = false;
		
		for(User index: credentials)
		{
			if(username.equals(index.username))
			{
				userSalt = index.salt;
				userPw = password;
				
				hash = generateHash(userPw, userSalt);
				
				if(hash.equals(index.passwordHash))
				{
					isUserValid = true;
				}
			}
		}
		
		return isUserValid;		
	}
	
	/**
	 * Description: Generate salt to aid password security
	 * 
	 * @return generated salt
	 */	
	public static String generateSalt();
	{
		final int SALTSIZE = 5;
		
		String passwordSalt;
		
		StringBuilder generatedSalt = new StringBuilder();
	
		for(int i = 0; i <  SALTSIZE; i++)
		{
			int character = (int)(Math.random()*SALTBANK.length());
			generatedSalt.append(SALTBANK.charAt(character));
		}
		
		passwordSalt = generatedSalt.toString();
		
		return passwordSalt;
	}
	
	/**
	 * Description: Generates a hashcode of a concatenation of the users entered
	 * password and the password salt
	 * 
	 * @param password user entered password as a string
	 * @param passwordSalt randomly generated salt as a string
	 * @return hashed password
	 */
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
        
    
        
        	for (int i = 0; i < asciiHash.length; i++) 
        	{
        		hashToChar = (char) asciiHash[i];
        	
        		hashAsChars[i] = hashToChar;
       		}
        
        	String passwordHash = new String(hashAsChars);
        
        	return passwordHash;
	}

}
