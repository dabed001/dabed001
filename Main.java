import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import java.util.Scanner;

public class Main {

    // Declares an array called squads that has a length of 32.
    public static Squad[] squads = new Squad[32];

    // Declares ArrayList's called listPlayers and listManagers that will be used for storing, and listing players and managers.
    public static ArrayList<Player> listPlayers = new ArrayList<>();
    public static ArrayList<Manager> listManagers = new ArrayList<>();

    // The code below loads in the 2 data files (Players.csv and Managers.csv) and populates squads based on the data.
    // I have also written code so that any errors are caught with an exception for debugging.
    public static void main(String[] args) {
        try {

            playersCSV("Players.csv");
            managersCSV("Managers.csv");
            populateSquads();
        }
        catch (Exception error)
        {
            error.printStackTrace();
        }
    }

    public static Team getTeam(Squad s) {
        Team t = new Team(s.getTeamName(), s.getManager());

        // This code gets the manager's preferred formation that they would like to implement for their team.
        String managersPreference = s.getManager().getFavouredFormation();

        // The codes below will be used for extraction on the number of defenders, midfielders, and forwards from the manager's preferred formation.
        int defendingPlayer = Character.getNumericValue(managersPreference.charAt(0));
        int midfieldingPlayer = Character.getNumericValue(managersPreference.charAt(2));
        int forwardPlayer = Character.getNumericValue(managersPreference.charAt(4));

        // This code will be used to select the best player based on a collection of average attributes
        ArrayList<Player> chosenPlayers = new ArrayList<>();

        // This code sorts the players based on their average attributes using a Comparator
        listPlayers.sort(Comparator.comparingDouble(players ->
        {
            double playerAttribute = (Double.parseDouble(String.valueOf(players.getFitness())) +
                    Double.parseDouble(String.valueOf(players.getPassingAccuracy())) +
                    Double.parseDouble(String.valueOf(players.getShotAccuracy())) +
                    Double.parseDouble(String.valueOf(players.getDefensiveness())) +
                    Double.parseDouble(String.valueOf(players.getAggression())) +
                    Double.parseDouble(String.valueOf(players.getPositioning())) +
                    Double.parseDouble(String.valueOf(players.getDribbling())) +
                    Double.parseDouble(String.valueOf(players.getChanceCreation())) +
                    Double.parseDouble(String.valueOf(players.getOffsideAdherence()))) / 9.0;

            return playerAttribute * -1;
        }));

        // These codes will be used to select the top players based on the formation chosen by the manager.
        int defendingPlayerCount = 0;
        int midfieldingPlayerCount = 0;
        int forwardPlayerCount = 0;

        // This code selects players based on their position and fulfills the number of players required for each position.
        // The players are then added to the chosenPlayers list.
        for (Player players : listPlayers)
        {
            if (defendingPlayerCount < defendingPlayer && players.getPosition().equals("Defender"))
            {
                chosenPlayers.add(players);
                defendingPlayerCount++;
            }
            else if (midfieldingPlayerCount < midfieldingPlayer && players.getPosition().equals("Midfielder"))
            {
                chosenPlayers.add(players);
                midfieldingPlayerCount++;
            }
            else if (forwardPlayerCount < forwardPlayer && players.getPosition().equals("Forward"))
            {
                chosenPlayers.add(players);
                forwardPlayerCount++;
            }
            if (defendingPlayerCount == defendingPlayer && midfieldingPlayerCount == midfieldingPlayer && forwardPlayerCount == forwardPlayer)
            {
                break; // This stops selecting players when the requirement for each position is fulfilled.
            }
        }

        // This code adds the selected players to the team.
        t.setPlayers(chosenPlayers);

        return t;
    }

    public static void runTournament() {

        // This code shuffles the squads to produce a randomised group stage
        ArrayList<Squad> shuffleTeams = new ArrayList<>(Arrays.asList(squads));
        Collections.shuffle(shuffleTeams);

        // This code establishes the group stage for the tournament.
        for (int i = 0; i < shuffleTeams.size(); i += 4) {
            ArrayList<Squad> teamGroups = new ArrayList<>(shuffleTeams.subList(i, i + 4));
            // Simulate matches in the group
            for (int j = 0; j < teamGroups.size(); j += 2)
            {
                Squad teamA = teamGroups.get(j);
                Squad teamB = teamGroups.get(j + 1);
                matchSimulation(teamA, teamB);
            }
        }

        // This code establishes the knockout stage for the tournament.
        ArrayList <Squad> knockoutStage = new ArrayList<>();

        // This code adds the winning two teams from each group to the knockout stage
        for (int i = 0; i < shuffleTeams.size(); i += 2)
        {
            knockoutStage.add(shuffleTeams.get(i));
            knockoutStage.add(shuffleTeams.get(i + 1));
        }

        // This code simulates the knockout fixtures to determine a winner.
        while (knockoutStage.size() > 1)
        {
            ArrayList<Squad> matchWinner = new ArrayList<>();
            for (int i = 0; i < knockoutStage.size(); i += 2)
            {
                Squad teamA = knockoutStage.get(i);
                Squad teamB = knockoutStage.get(i + 1);
                matchWinner.add(matchSimulation(teamA, teamB));
            }
            knockoutStage = matchWinner;
        }

        // The codes below retrieve the winner of the tournament along with the manager that led their team to victory.
        Squad winnerOfTournament = knockoutStage.get(0);
        Manager winningManager = winnerOfTournament.getManager();

        // The codes below output who the winning team with victorious manager is.
        System.out.println("The Winning Team of the Tournament is: " + winnerOfTournament.getTeamName());
        System.out.println("The Manager That Led Their Team to Victory is: " + winningManager.getFirstName() + " " + winningManager.getSurname());
}

    private static void playersCSV(String csvFile) {
        try {

            Scanner playerScanner = new Scanner(new File(csvFile)); // This code uses a Scanner to read the Players.csv file
            playerScanner.nextLine(); // This code discards the column names in the Player.csv and reads the actual data.

            while (playerScanner.hasNextLine())
            {
                String playerLine = playerScanner.nextLine(); // As I implemented code to discard column names in the file, this code will read the actual data.
                String[] playersData = playerLine.split(","); // As data is divided with commas, this code allows the reading of the data to be easier.

                // The codes below extract player data from the playersData array.
                String firstName = playersData[0];
                String surname = playersData[1];
                String team = playersData[2];
                String position = playersData[3];
                double fitness = Double.parseDouble(playersData[4]);
                double passingAccuracy = Double.parseDouble(playersData[5]);
                double shotAccuracy = Double.parseDouble(playersData[6]);
                double shotFrequency = Double.parseDouble(playersData[7]);
                double defensiveness = Double.parseDouble(playersData[8]);
                double aggression = Double.parseDouble(playersData[9]);
                double positioning = Double.parseDouble(playersData[10]);
                double dribbling = Double.parseDouble(playersData[11]);
                double chanceCreation = Double.parseDouble(playersData[12]);
                double offsideAdherence = Double.parseDouble(playersData[13]);

                // This code creates a player object and adds it to the list of players.
                Player players = new Player(firstName, surname, team, position, fitness, passingAccuracy, shotAccuracy, shotFrequency, defensiveness, aggression, positioning, dribbling, chanceCreation, offsideAdherence);
                listPlayers.add(players);
            }
            playerScanner.close();
        }
        // This code allows the identification of any errors to be found easier as it is printed.
        catch (FileNotFoundException error)
        {
            error.printStackTrace();
        }
    }

    private static void managersCSV(String csvFile) {
        try {

            Scanner managerScanner = new Scanner(new File(csvFile)); // This code uses a Scanner to read the Managers.csv file
            managerScanner.nextLine(); // This code discards the column names in the Managers.csv and reads the actual data.

            while (managerScanner.hasNextLine())
            {
                String managerLine = managerScanner.nextLine(); // As I implemented code to discard column names in the file, this will read the actual data.
                String[] managersData = managerLine.split(","); // As data is divided with commas, this code allows the reading of the data to be easier.

                // The codes below extract player data from the managersData array.
                String firstName = managersData[0];
                String surname = managersData[1];
                String team = managersData[2];
                String favouredFormation = managersData[3];
                double respect = Double.parseDouble(managersData[4]);
                double ability = Double.parseDouble(managersData[5]);
                double knowledge = Double.parseDouble(managersData[6]);
                double belief = Double.parseDouble(managersData[7]);

                // This code creates a manager object and adds it to the list of managers.
                Manager managers = new Manager(firstName, surname, team, favouredFormation, respect, ability, knowledge, belief);
                listManagers.add(managers);
            }
            managerScanner.close();
        }
        // This code allows the identification of any errors to be found easier as it is printed.
        catch (FileNotFoundException error)
        {
            error.printStackTrace();
        }
    }

    // This code creates and populates squads for 32 teams. Each team has their number of players fixed to 11.
    private static void populateSquads()
    {
        for (int i = 0; i < 32; i++)
        {
            int totalTeamPlayers = 11;
            squads[i] = new Squad();
            squads[i].setManager(listManagers.get(i));
            squads[i].setPlayers(new ArrayList<>(listPlayers.subList(i * totalTeamPlayers, (i + 1) * totalTeamPlayers)));
        }
    }

    // This code simulates matches to determine a winner.
    private static Squad matchSimulation(Squad teamA, Squad teamB) {
        Random randomise = new Random(); // This code will be used for randomising teams
        int winnerOfMatch = randomise.nextInt(2); // This code randomises two outcomes (who the winner in the match is)

        // This code determines and outputs who the winner of the match is.
        if (winnerOfMatch == 0)
        {
            System.out.println(teamA.getTeamName() + " won their match against " + teamB.getTeamName());
            return teamA;
        }
        else {
            System.out.println(teamB.getTeamName() + " won their match against " + teamA.getTeamName());
            return teamB;
        }
    }
}
