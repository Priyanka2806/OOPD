import java.util.ArrayList;
import java.util.Scanner;
import java.util.Iterator;

public class SmartHealth {
	
	//Three lists maintained to store Users, Admin, Moderators.
	static ArrayList<User> userList = new ArrayList<User>();
	static ArrayList<Moderator> modList = new ArrayList<Moderator>();
	static ArrayList<Admin> adminList = new ArrayList<Admin>();
	static ArrayList<Person> deletedPersonList = new ArrayList<Person>(); //List to keep record of the users who have deleted their profile.
	public static void main(String args[])
	{
		//To set usertype for Users, according to date of registration.
		Scanner in = new Scanner(System.in);
		long currentTime = System.currentTimeMillis();
		Iterator<User> iterator1 = userList.iterator();
		int denominator = 1000*60*60*24;
		 while(iterator1.hasNext()) {
			 Person userTime = iterator1.next();
			 long result=(currentTime - userTime.regTime)/denominator;
			 if(result > 30 && result < 366)
			 {
				 userTime.userType = "middle";
			 }
			 else if(result >= 365)
			 {
				 userTime.userType = "old";
			 }
		 }

		System.out.println("New user? Enter 1");
		System.out.println("Registered user? Enter 2");
		System.out.println("Enter 3 to exit");
		int opt1=in.nextInt();
		in.nextLine();
		switch(opt1)
		{
			case 1: createProfile();
					break;
					
			case 2: userLogin();
					break;
					
			case 3: System.out.println("Thank you!!");
					break;
		}
		in.close();
	}
	
	//User Login Page - Checks email id and password entered by the user and directs to the corresponding user's profile.
	private static void userLogin() {
		int flag = 0;
		Scanner in = new Scanner(System.in);

		System.out.println("Enter Credentials to Login. Enter 1 to go back to previous Menu."); //Press 'Enter' if you want to enter credentials.
		String choice = in.nextLine();
		if(choice == "1")
			main(null);
		else{
			System.out.println("Email Id: ");
			String loginId = in.nextLine();
			System.out.println("Password: ");
			String password = in.nextLine();
			Iterator<User> iterator2 = userList.iterator();
			while(iterator2.hasNext()) {
				User prsn = iterator2.next();
				if(prsn.primaryEmail.equals(loginId)){
					if(prsn.password.equals(password)){
						flag = 1;
						userProfilePage(prsn.username, "U");
						break;
					}	 
				} 
			}
			if(flag == 0)
			{
				Iterator<Moderator> iterator3 = modList.iterator();
				while(iterator3.hasNext()) {
					Moderator prsn = iterator3.next();
					if(prsn.primaryEmail.equals(loginId)){
						if(prsn.password.equals(password)){
							flag = 1;
							userProfilePage(prsn.username, "M");
							break;
						}	 
					} 
				}
			}
			
			if(flag == 0){
				Iterator<Admin> iterator4 = adminList.iterator();
				while(iterator4.hasNext()) {
					Admin prsn = iterator4.next();
					if(prsn.primaryEmail.equals(loginId)){
						if(prsn.password.equals(password)){
							flag = 1;
							userProfilePage(prsn.username, "A");
							break;
						}
					}	 
				} 
			}
			if(flag==0){
				System.out.println("Invalid Login ID or Password!!");
				userLogin();
			}
		}
		in.close();
	}
	
	//User Profile Page - Displays users details, and options for deleting or updating user profile. Arguement 'string' defines whether the person is a User, Moderator or Admin.
	private static void userProfilePage(String username, String string) {
		Scanner in = new Scanner(System.in);
		System.out.printf("Welcome "+ username);
		System.out.println();
		System.out.println("Update Details? Enter 1");
		System.out.println("Delete Profile? Enter 2");
		System.out.println("Enter 3 to Logout");
		
		Person loggedInUser = new Person();
		if(string.equals("U")){
			loggedInUser = new User();
			Iterator<User> iterator4 = userList.iterator();
			while(iterator4.hasNext()) {
				User person = iterator4.next();
				if(person.username.equals(username))
				{
					loggedInUser = person;
					break;
				}
			}
		}
		else if(string.equals("M"))
		{
			loggedInUser = new Moderator();
			Iterator<Moderator> iterator4 = modList.iterator();
			while(iterator4.hasNext()) {
				Moderator person = iterator4.next();
				if(person.username.equals(username))
				{
					loggedInUser = person;
					break;
				}
			}
			
		}
		
		else if(string.equals("A"))
		{
			loggedInUser = new Admin();
			Iterator<Admin> iterator4 = adminList.iterator();
			while(iterator4.hasNext()) {
				Admin person = iterator4.next();
				if(person.username.equals(username))
				{
					loggedInUser = person;
					break;
				}
			}
			
		}
		
		int opt2=in.nextInt();
		in.nextLine();
		switch(opt2)
		{
			case 1: updateProfile(loggedInUser, string);
					break;
					
			case 2: deleteProfile(loggedInUser, string);
					break;
						
			case 3: main(null);
					break;
		}
		in.close();
	}
	
	//User's profile details are deleted from the list of registered users, and added to list of deleted  users, for record purpose.
	private static void deleteProfile(Person userToBeDeleted, String string) {
		Scanner in = new Scanner(System.in);
		System.out.println("Are you sure you want to delete your Profile? [y/n]");
		String ans = in.nextLine();
		if(ans.equals("y") || ans.equals("Y")) //Confirms whether users wants to delete profile or not.
		{
			if(string.equals("A"))
				adminList.remove(userToBeDeleted);
			else if(string.equals("M"))
				modList.remove(userToBeDeleted);
			else 
				userList.remove(userToBeDeleted);
			
				deletedPersonList.add(userToBeDeleted);
				System.out.println("User deleted successfully!!");
				main(null);
		}
		else
			userProfilePage(userToBeDeleted.username, string);
		
		in.close();	
	}

	//Displays various details of the logged in user, and provides option to update those details.
	private static void updateProfile(Person loggedInUser, String string) {
		Scanner in = new Scanner(System.in);
		System.out.println("Your Profile Details are:-");
		System.out.print("\nUsername - " + loggedInUser.username + "  ----(Press 1 to update)");
		System.out.print("\nPrimary Email ID - " + loggedInUser.primaryEmail + "  ----(Press 2 to update)");
		System.out.print("\nSecondary Email ID - " + loggedInUser.secondaryEmail + "  ----(Press 3 to update)");
		System.out.print("\nPassword - " + loggedInUser.password + "  ----(Press 4 to update)");
		System.out.print("\nFirst Name - " + loggedInUser.firstName + "  ----(Press 5 to update)");
		System.out.print("\nLast Name - " + loggedInUser.lastName + "  ----(Press 6 to update)");
		System.out.print("\nAddress - " + loggedInUser.address + "  ----(Press 7 to update)");
		System.out.print("\nAbout Me - " + loggedInUser.aboutMe + "  ----(Press 8 to update)");
		
		int selection = in.nextInt();
		in.nextLine();
		switch(selection)
		{
			case 1: System.out.println("Enter new Username: ");
					loggedInUser.username = in.nextLine();
					break;
					
			case 2: System.out.println("Enter new Primary Email ID: ");
					loggedInUser.primaryEmail = in.nextLine();
					break;
			
			case 3: System.out.println("Enter new Secondary Email ID: ");
					loggedInUser.secondaryEmail = in.nextLine();
					break;
			
			case 4: System.out.println("Enter new Password: ");
					loggedInUser.password = in.nextLine();
					break;
					
			case 5: System.out.println("Enter new First Name: ");
					loggedInUser.firstName = in.nextLine();
					break;
			
			case 6: System.out.println("Enter new Last Name: ");
					loggedInUser.lastName = in.nextLine();
					break;
			
			case 7: System.out.println("Enter new Postal Address: ");
					loggedInUser.address = in.nextLine();
					break;
			
			case 8: System.out.println("Update About Me: ");
					loggedInUser.aboutMe = in.nextLine();
					break;
			
		}
		//personList.add(loggedInUser);
		//Yahan prob aari hai.. cuz Idon't know how to update in particular list----
		
		
		System.out.println("More Updations?? Enter 1"); //Press 'Enter' if no more updations.
		String ch = in.nextLine();
		if(ch == "1")
		{
			updateProfile(loggedInUser, string);
		}
		else
		{
			System.out.println("All updations made successfully!!");
			userProfilePage(loggedInUser.username, string);
		}
		in.close();
	}

	//For registering a Person
	public static void createProfile()
	{
		Scanner in = new Scanner(System.in);
		
		int temp = 0;
		String c;
		
		System.out.println("Enter the following information.");
		System.out.println("User?? Enter 'U'");
		System.out.println("Moderator?? Enter 'M'");
		System.out.println("Admin?? Enter 'A'");
		c = in.nextLine();
		
		
		if(c.equals("U")){
			User user = new User();
			System.out.println("Username: ");
			user.username = in.nextLine();
			while(user.username.length() > 20){
				System.out.println("Username cannot be longer than 20 characters. Re-enter Username.");
				user.username = in.nextLine();
			} //To check that Username is not greater than 20 characters.
		
			while(temp == 1)
			{
				Iterator<User> iterator3 = userList.iterator();
				while(iterator3.hasNext()){
					User registeredUsers = iterator3.next();
					if(registeredUsers.username.equals(user.username)){
						temp = 1;
						System.out.println("This username is already taken. Try some other Username.");
						user.username = in.nextLine();
						break;
					}
				}
			}//To check that Username is unique.
		 
			System.out.println("Primary Email id: ");
			user.primaryEmail = in.nextLine();
			while(!(user.primaryEmail.contains("@")) || !(user.primaryEmail.contains(".com")))
			{
				System.out.println("Enter a valid Email Id");
				user.primaryEmail = in.nextLine();
			}//To check format of the email id.
			System.out.println("Secondary Email id: ");
			user.secondaryEmail = in.nextLine();
			while(!(user.secondaryEmail.contains("@")) || !(user.secondaryEmail.contains(".com")))
			{
				System.out.println("Enter a valid Email Id");
				user.secondaryEmail = in.nextLine();
			}//To check format of the email id.
			System.out.println("Password: ");
			user.password = in.nextLine();
			System.out.println("Postal address: ");
			user.address = in.nextLine();
			System.out.println("About Me: ");
			user.aboutMe = in.nextLine();
			user.regTime = System.currentTimeMillis();
			for(int i=0;i<3;i++)
			{
				System.out.println("Enter URL for photo " + (i+1) + ": ");
				user.photoURL[i] = in.nextLine();
			}
			user.userType = "new";
			user.karma = 0;
			userList.add(user);
			
			System.out.println("User Added Successfully!!");
		}
		
		else if(c.equals("M")){
			Moderator mod = new Moderator();
			System.out.println("Username: ");
			mod.username = in.nextLine();
			while(mod.username.length() > 20){
				System.out.println("Username cannot be longer than 20 characters. Re-enter Username.");
				mod.username = in.nextLine();
			}
		
			while(temp == 1)
			{
				Iterator<Moderator> iterator3 = modList.iterator();
				while(iterator3.hasNext()){
					Moderator registeredUsers = iterator3.next();
					if(registeredUsers.username.equals(mod.username)){
						temp = 1;
						System.out.println("This username is already taken. Try some other Username.");
						mod.username = in.nextLine();
						break;
					}
				}
			}
		 
			System.out.println("Primary Email id: ");
			mod.primaryEmail = in.nextLine();
			while(!(mod.primaryEmail.contains("@")) || !(mod.primaryEmail.contains(".com")))
			{
				System.out.println("Enter a valid Email Id");
				mod.primaryEmail = in.nextLine();
			}
			System.out.println("Secondary Email id: ");
			mod.secondaryEmail = in.nextLine();
			while(!(mod.secondaryEmail.contains("@")) || !(mod.secondaryEmail.contains(".com")))
			{
				System.out.println("Enter a valid Email Id");
				mod.secondaryEmail = in.nextLine();
			}
			System.out.println("Password: ");
			mod.password = in.nextLine();
			System.out.println("Postal address: ");
			mod.address = in.nextLine();
			System.out.println("About Me: ");
			mod.aboutMe = in.nextLine();
			mod.regTime = System.currentTimeMillis();
			for(int i=0;i<3;i++)
			{
				System.out.println("Enter URL for photo " + (i+1) + ": ");
				mod.photoURL[i] = in.nextLine();
			}
			mod.userType = "mod";
			System.out.println("Enter the contact No.: ");
			mod.contactNo = in.nextLine();
			System.out.println("Enter the qualification details: ");
			mod.qualification = in.nextLine();
			modList.add(mod);
			
			System.out.println("Moderator Added Successfully!!");
		}
		
		else if(c.equals("A")){
			Admin admin = new Admin();
			System.out.println("Username: ");
			admin.username = in.nextLine();
			while(admin.username.length() > 20){
				System.out.println("Username cannot be longer than 20 characters. Re-enter Username.");
				admin.username = in.nextLine();
			}
		
			while(temp == 1)
			{
				Iterator<Admin> iterator3 = adminList.iterator();
				while(iterator3.hasNext()){
					Admin registeredUsers = iterator3.next();
					if(registeredUsers.username.equals(admin.username)){
						temp = 1;
						System.out.println("This username is already taken. Try some other Username.");
						admin.username = in.nextLine();
						break;
					}
				}
			}
		 
			System.out.println("Primary Email id: ");
			admin.primaryEmail = in.nextLine();
			while(!(admin.primaryEmail.contains("@")) || !(admin.primaryEmail.contains(".com")))
			{
				System.out.println("Enter a valid Email Id");
				admin.primaryEmail = in.nextLine();
			}
			System.out.println("Secondary Email id: ");
			admin.secondaryEmail = in.nextLine();
			while(!(admin.secondaryEmail.contains("@")) || !(admin.secondaryEmail.contains(".com")))
			{
				System.out.println("Enter a valid Email Id");
				admin.secondaryEmail = in.nextLine();
			}
			System.out.println("Password: ");
			admin.password = in.nextLine();
			System.out.println("Postal address: ");
			admin.address = in.nextLine();
			System.out.println("About Me: ");
			admin.aboutMe = in.nextLine();
			admin.regTime = System.currentTimeMillis();
			for(int i=0;i<3;i++)
			{
				System.out.println("Enter URL for photo " + (i+1) + ": ");
				admin.photoURL[i] = in.nextLine();
			}
			admin.userType = "admin";
			System.out.println("Enter Contact No.: ");
			admin.contactNo = in.nextLine();
			adminList.add(admin);
			System.out.println("Admin Added Successfully!!");
		}
		
		
		System.out.println("Enter 1 to Login");
		System.out.println("Enter 2 to go back to Menu");
		int op = in.nextInt();
		if(op == 1)
			userLogin();
		else if(op == 2)
			main(null);
		in.close();
	}
}
