import java.util.ArrayList;

public class Squad {

    // The codes below define instance variables for the Squad class.
    private String teamName;
    private ArrayList<Player> players;
    private Manager manager;

    // This constructor is initialised in a format that can be used to set up squads throughout all code.
    Squad(String teamName, Manager manager)
    {
        this.teamName = teamName;
        this.manager = manager;
        players = new ArrayList<>();
    }

    // Constructor for Squad
    public Squad()
    {
        new Squad();
    }

    // This code allows players to be added, presumably to a team.
    public void addPlayer(Player p)
    {
        players.add(p);
    }

    // This code gets a player object by surname
    public Player getPlayer(String surname)
    {
        for(Player p: players)
        {
            if(p.getSurname().equals(surname))
            {
                return p;
            }
        }
        return null;
    }

    // This code gets a player object by index
    public Player getPlayer(int n){
        return players.get(n);
    }

    // The codes below are getters and setters that can be used for access or modification of the Squad attributes.
    public String getTeamName() {
        return teamName;
    }

    public Manager getManager() {
        return manager;
    }

    public void setPlayers(ArrayList<Player> players)
    {
        this.players = players;
    }

    public void setManager(Manager manager)
    {
        this.manager = manager;
    }
}
