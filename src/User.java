
import java.util.Scanner;


public class User extends Person {
	int karma;

	public User() {
		super();
	}

	public User(String username, String primaryEmail, String secondaryEmail,
			String password, String firstName, String lastName, String address,
			String aboutMe, String[] photoURL, long regTime, String userType,
			int karma) {
		super(username, primaryEmail, secondaryEmail, password, firstName,
				lastName, address, aboutMe, photoURL, regTime, userType);
		this.karma = karma;
	}

	@Override
	public void displayDetails()
	{
		System.out.print("\nUsername - " + this.username + "  ----(Press 1 to update)");
		System.out.print("\nPrimary Email ID - " + this.primaryEmail + "  ----(Press 2 to update)");
		System.out.print("\nSecondary Email ID - " + this.secondaryEmail + "  ----(Press 3 to update)");
		System.out.print("\nPassword - " + this.password + "  ----(Press 4 to update)");
		System.out.print("\nFirst Name - " + this.firstName + "  ----(Press 5 to update)");
		System.out.print("\nLast Name - " + this.lastName + "  ----(Press 6 to update)");
		System.out.print("\nAddress - " + this.address + "  ----(Press 7 to update)");
		System.out.print("\nAbout Me - " + this.aboutMe + "  ----(Press 8 to update)");
		System.out.print("\nKarma Score - " + this.karma + "  ----(Cannot be updated)");
		//System.out.print("Enter 9 to go back to your profile.");
	}
	
	@Override
	public void updateDetails(int selection)
	{
		Scanner in = new Scanner(System.in);
		switch(selection)
		{
			case 1: System.out.println("Enter new Username: ");
					this.username = in.nextLine();
					break;
					
			case 2: System.out.println("Enter new Primary Email ID: ");
					this.primaryEmail = in.nextLine();
					break;
			
			case 3: System.out.println("Enter new Secondary Email ID: ");
					this.secondaryEmail = in.nextLine();
					break;
			
			case 4: System.out.println("Enter new Password: ");
					this.password = in.nextLine();
					break;
					
			case 5: System.out.println("Enter new First Name: ");
					this.firstName = in.nextLine();
					break;
			
			case 6: System.out.println("Enter new Last Name: ");
					this.lastName = in.nextLine();
					break;
			
			case 7: System.out.println("Enter new Postal Address: ");
					this.address = in.nextLine();
					break;
			
			case 8: System.out.println("Update About Me: ");
					this.aboutMe = in.nextLine();
					break;
					
			//case 9: userProfilePage(this.username, "user");
			
		}
		//in.close();
	}
	

}
