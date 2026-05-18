package p5adventure;

import student.adventure.*;

/**
 * AdventureGame: European Football Stadium Hunt.
 *
 * HOW TO PLAY:
 * You are a football memorabilia hunter. Collect one item from each
 * of 10 iconic European stadiums, then travel to UEFA Headquarters
 * in Nyon and type 'claim' to win the Golden Boot.
 *
 * COMMANDS: north/n, south/s, east/e, west/w, up/u, down/d, take,
 * drop, inventory/i, back, look, examine, score, claim, help, quit.
 *
 * HOW THE GAME ENDS:
 * Collect all 10 collectibles then type 'claim' at UEFA Headquarters.
 * Win logic: ClaimCommand.execute(Player) and Adventurer.isGameOver().
 *
 * @author Tihnan Ahmed (tihnan2024)
 * @version 1.0
 */
public class AdventureGame extends Game {

    /**
     * Creates a new AdventureGame with an Adventurer and a Parser.
     */
    public AdventureGame() {
        super(new Adventurer(), new Parser());
    }

    /**
     * Returns the welcome message shown at game start.
     *
     * @return the welcome message string
     */
    @Override
    public String welcomeMessage() {
        return "Welcome to the European Football Stadium Hunt!\n"
            + "Collect one item from each of 10 iconic stadiums,\n"
            + "then head to UEFA Headquarters and type 'claim'.\n"
            + "Type 'help' for commands. Good luck!";
    }

    /**
     * Registers all command words with their Command objects.
     */
    @Override
    public void createCommands() {
        CommandWords commands = parser().commandWords();

        // Library commands
        commands.addCommand("go",   new GoCommand());
        commands.addCommand("help", new HelpCommand(commands));
        commands.addCommand("quit", new QuitCommand());

        // Direction commands - ONE class, 12 words
        commands.addCommand("north", new DirectionCommand("north"));
        commands.addCommand("south", new DirectionCommand("south"));
        commands.addCommand("east",  new DirectionCommand("east"));
        commands.addCommand("west",  new DirectionCommand("west"));
        commands.addCommand("up",    new DirectionCommand("up"));
        commands.addCommand("down",  new DirectionCommand("down"));
        commands.addCommand("n",     new DirectionCommand("n"));
        commands.addCommand("s",     new DirectionCommand("s"));
        commands.addCommand("e",     new DirectionCommand("e"));
        commands.addCommand("w",     new DirectionCommand("w"));
        commands.addCommand("u",     new DirectionCommand("u"));
        commands.addCommand("d",     new DirectionCommand("d"));

        // Item commands
        commands.addCommand("take",      new TakeCommand());
        commands.addCommand("drop",      new DropCommand());
        commands.addCommand("inventory", new InventoryCommand());
        commands.addCommand("i",         new InventoryCommand());

        // Movement
        commands.addCommand("back",    new BackCommand());

        // Custom commands
        commands.addCommand("look",    new LookCommand());
        commands.addCommand("examine", new ExamineCommand());
        commands.addCommand("score",   new ScoreCommand());
        commands.addCommand("claim",   new ClaimCommand());
    }

    /**
     * Builds the full game map with 11 locations.
     * All rooms reachable from Wanda Metropolitano (start).
     */
    @Override
    public void createRooms() {

        Location wanda = new Location(
            "at Wanda Metropolitano, home of Atletico Madrid");
        Location emirates = new Location(
            "at Emirates Stadium, home of Arsenal FC");
        Location mestalla = new Location(
            "at Mestalla, the fortress of Valencia CF");
        Location campNou = new Location(
            "at Camp Nou, the cathedral of FC Barcelona");
        Location allianz = new Location(
            "at Allianz Arena, the glowing home of Bayern Munich");
        Location sanSiro = new Location(
            "at San Siro, the legendary temple of Milan football");
        Location bernabeu = new Location(
            "at Estadio Santiago Bernabeu, home of Real Madrid");
        Location anfield = new Location(
            "at Anfield, the iconic home of Liverpool FC");
        Location parcDesPrinces = new Location(
            "at Parc des Princes, home of Paris Saint-Germain");
        Location cruyffArena = new Location(
            "at the Johan Cruyff Arena, home of AFC Ajax");
        Location uefaHQ = new Location(
            "at UEFA Headquarters in Nyon, Switzerland");

        // Collectible items - one per stadium, all removable
        wanda.addItem(new Item("scarf",
            "A red and white Atletico Madrid scarf.", 2));
        emirates.addItem(new Item("badge",
            "A brass Arsenal cannon badge.", 1));
        mestalla.addItem(new Item("pennant",
            "A Valencia CF pennant with the bat crest.", 1));
        campNou.addItem(new Item("programme",
            "A vintage 1999 Champions League final programme.", 1));
        allianz.addItem(new Item("stein",
            "A Bayern Munich Oktoberfest beer stein.", 4));
        sanSiro.addItem(new Item("jersey",
            "A vintage jersey, half rossonero, half nerazzurri.", 2));
        bernabeu.addItem(new Item("whistle",
            "A golden whistle replica from the 2002 CL final.", 3));
        anfield.addItem(new Item("sign",
            "A replica of the This Is Anfield sign.", 5));
        parcDesPrinces.addItem(new Item("patch",
            "A gold star patch from PSG's Ligue 1 champion kit.", 1));
        cruyffArena.addItem(new Item("ajax-scarf",
            "A black and white Ajax scarf.", 2));

        // UEFA HQ - not collectible; player types 'claim' to win
        uefaHQ.addItem(new Item("golden-boot",
            "The Golden Boot trophy. Type 'claim' to win!", 10, false));

        // Wire up exits
        wanda.setExit("west", bernabeu);
        bernabeu.setExit("east", wanda);

        wanda.setExit("east", campNou);
        campNou.setExit("west", wanda);

        wanda.setExit("south", allianz);
        allianz.setExit("north", wanda);

        allianz.setExit("south", sanSiro);
        sanSiro.setExit("north", allianz);

        sanSiro.setExit("east", uefaHQ);
        uefaHQ.setExit("west", sanSiro);

        bernabeu.setExit("north", mestalla);
        mestalla.setExit("south", bernabeu);

        mestalla.setExit("west", emirates);
        emirates.setExit("east", mestalla);

        mestalla.setExit("east", cruyffArena);
        cruyffArena.setExit("west", mestalla);

        emirates.setExit("north", anfield);
        anfield.setExit("south", emirates);

        parcDesPrinces.setExit("south", cruyffArena);
        cruyffArena.setExit("north", parcDesPrinces);

        anfield.setExit("east", parcDesPrinces);
        parcDesPrinces.setExit("west", anfield);

        // Player starts at Wanda Metropolitano
        player().setCurrentRoom(wanda);
    }
}
