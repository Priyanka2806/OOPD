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

		System.out.println("Press 'Enter' to Login. Enter 1 to go back to previous Menu."); //Press 'Enter' if you want to enter credentials.
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
		loggedInUser.displayDetails();
		int selection = in.nextInt();
		in.nextLine();
		loggedInUser.updateDetails(selection);
		
		System.out.println("More Updations?? Enter 1");
		System.out.println("Else Enter 2");//Press 'Enter' if no more updations.
		int option1 = in.nextInt();
		System.out.println(option1);
		if(option1 == 1)
		{
			updateProfile(loggedInUser, string);
		}
		else if(option1 == 2)
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
		
		System.out.println("Username: ");
		String uName = in.nextLine();
		while(uName.length() > 20){
			System.out.println("Username cannot be longer than 20 characters. Re-enter Username.");
			uName = in.nextLine();
		} //To check that Username is not greater than 20 characters.
	
		while(temp == 1)
		{
			Iterator<User> iterator3 = userList.iterator();
			while(iterator3.hasNext()){
				User registeredUsers = iterator3.next();
				if(registeredUsers.username.equals(uName)){
					temp = 1;
					System.out.println("This username is already taken. Try some other Username.");
					uName = in.nextLine();
					break;
				}
			}
		}//To check that Username is unique.
	 
		System.out.println("Primary Email id: ");
		String pEmail = in.nextLine();
		while(!(pEmail.contains("@")) || !(pEmail.contains(".com")))
		{
			System.out.println("Enter a valid Email Id");
			pEmail = in.nextLine();
		}//To check format of the email id.
		System.out.println("Secondary Email id: ");
		String sEmail = in.nextLine();
		while(!(sEmail.contains("@")) || !(sEmail.contains(".com")))
		{
			System.out.println("Enter a valid Email Id");
			sEmail = in.nextLine();
		}//To check format of the email id.
		System.out.println("Password: ");
		String password = in.nextLine();
		System.out.println("First Name: ");
		String fName = in.nextLine();
		System.out.println("Last Name: ");
		String lName = in.nextLine();
		System.out.println("Postal address: ");
		String address = in.nextLine();
		System.out.println("About Me: ");
		String aboutMe = in.nextLine();
		long regTime = System.currentTimeMillis();
		String[] photo = new String[3];
		for(int i=0;i<3;i++)
		{
			System.out.println("Enter URL for photo " + (i+1) + ": ");
			photo[i] = in.nextLine();
		}

		if(c.equals("U") || c.equals("u")){
			String usrType = "new";
			int karma = 0;
			User user = new User(uName, pEmail, sEmail, password, fName, lName, address, aboutMe, photo, regTime, usrType, karma);
			userList.add(user);
			System.out.println("User Added Successfully!!");
		}
		
		else if(c.equals("M") || c.equals("m")){
			String usrType = "mod";
			System.out.println("Enter the contact No.: ");
			String contactNo = in.nextLine();
			System.out.println("Enter the qualification details: ");
			String qualificatn = in.nextLine();
			Moderator mod = new Moderator(uName, pEmail, sEmail, password, fName, lName, address, aboutMe, photo, regTime, usrType, contactNo, qualificatn);
			modList.add(mod);
			System.out.println("Moderator Added Successfully!!");
		}
		
		else if(c.equals("A") || c.equals("a")){
			String usrType = "admin";
			System.out.println("Enter Contact No.: ");
			String contactNo = in.nextLine();
			Admin admin = new Admin(uName, pEmail, sEmail, password, fName, lName, address, aboutMe, photo, regTime, usrType, contactNo);
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
