public class Manager extends Person {

    // The codes below define instance variables for the Manager class.
    private String favouredFormation;
    private double respect;
    private double ability;
    private double knowledge;
    private double belief;

    // The codes below creates a constructor that hold the Manager class instance variables for initialisation.
    // A superclass is also called so that other variables from the Person class can be inherited through their initialisation.
    public Manager(String firstName, String surname, String team,
                   String favouredFormation, double respect, double ability, double knowledge, double belief)
    {
        super(firstName, surname, team);

        this.favouredFormation = favouredFormation;
        this.respect = respect;
        this.ability = ability;
        this.knowledge = knowledge;
        this.belief = belief;
    }

    // The codes below are getters and setters that can be used for access or modification of the Manager attributes.
    public String getFavouredFormation()
    {
        return favouredFormation;
    }
    public void setFavouredFormation(String favouredFormation)
    {
        this.favouredFormation = favouredFormation;
    }

    public double getRespect()
    {
        return respect;
    }
    public void setRespect(double respect)
    {
        this.respect = respect;
    }

    public double getAbility()
    {
        return ability;
    }
    public void setAbility(double ability)
    {
        this.ability = ability;
    }

    public double getKnowledge()
    {
        return knowledge;
    }
    public void setKnowledge(double knowledge)
    {
        this.knowledge = knowledge;
    }

    public double getBelief()
    {
        return belief;
    }
    public void setBelief(double belief)
    {
        this.belief = belief;
    }
}