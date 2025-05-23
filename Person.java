public class Person {

    // The codes below define instance variables for the Person class.
    private String firstName;
    private String surname;
    private String team;

    // The codes below creates a constructor that hold the Person class instance variables for initialisation.
    public Person(String firstName, String surname, String team)
    {
        this.firstName = firstName;
        this.surname = surname;
        this.team = team;
    }

    // The codes below are getters and setters that can be used for access or modification of the Person attributes.
    public String getFirstName()
    {
        return firstName;
    }
    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getSurname()
    {
        return surname;
    }
    public void setSurname(String surname)
    {
        this.surname = surname;
    }

    public String getTeam()
    {
        return team;
    }
    public void setTeam(String team)
    {
        this.team = team;
    }
}