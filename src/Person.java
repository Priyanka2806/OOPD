
public class Person {
	String username;
	String primaryEmail;
	String secondaryEmail;
	String password;
	String firstName;
	String lastName;
	String address;
	String aboutMe;
	String[] photoURL = new String[3];
	long regTime;
	String userType;
	
	
	
	public Person() {
		super();
	}

	public Person(String username, String primaryEmail, String secondaryEmail,
			String password, String firstName, String lastName, String address,
			String aboutMe, String[] photoURL, long regTime, String userType) {
		super();
		this.username = username;
		this.primaryEmail = primaryEmail;
		this.secondaryEmail = secondaryEmail;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		this.aboutMe = aboutMe;
		this.photoURL = photoURL;
		this.regTime = regTime;
		this.userType = userType;
	}

	public void displayDetails()
	{
		System.out.println("This method displays the various details of the user.");
	}
	
	public void updateDetails(int selection)
	{
		System.out.println("This method updates the details of the user.");
		
	}
}