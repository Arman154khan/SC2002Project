package entity;

//javadoc 

/**
 * represents the user entity. 
 */

public abstract class User 
{
    protected String name;
    protected String nric; 
    protected int age;
    protected String maritalStatus;
    protected String password;


 public User(String name, String nric, int age, String maritalStatus, String password, String userType)
 {
    this.name = name;
    this.nric = nric;
    this.age = age;
    this.maritalStatus = maritalStatus;
    this.password = "password"; 
 }


public String getNric()
{
    return nric;
}

public String getName()
{
    return name;
}

public int getAge()
{
    return age;
}

public String getMaritalStatus()
{
    return maritalStatus;
}

public String getPassword() {
    return this.password;
}

public boolean checkPassword(String input)
{
    return password.equals(input); 
}

public void changePassword(String newPassword)
{
    this.password = newPassword; 
}

public abstract String getRole();  

// public String toString()
// {
//     return nric + " ," + password + " ," + age + " ," + maritalStatus + " ," + getRole();
// }

}