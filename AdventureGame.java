package p5adventure;

import student.adventure.*;

/**
 * AdventureGame: European Football Stadium Hunt
 *
 * HOW TO PLAY:
 * You are a football memorabilia hunter tasked with collecting one unique collectible
 * from each of Europe's most iconic stadiums. Travel between 10 famous grounds, pick up
 * the stadium's signature item, and make your way to UEFA Headquarters in Nyon,
 * Switzerland to claim the legendary Golden Boot trophy.
 *
 * Use directional commands (north, south, east, west, up, down or n/s/e/w/u/d) to move
 * between locations. Use 'take [item]' to collect memorabilia, 'look' to survey your
 * surroundings, 'inventory' or 'i' to check your bag, 'examine [item]' to inspect
 * something, 'score' to check your progress, 'back' to return to the previous room,
 * 'drop [item]' to leave an item, 'help' for a command list, and 'quit' to exit.
 *
 * HOW THE GAME ENDS (WIN):
 * Collect all 10 stadium collectibles and travel to UEFA Headquarters, then type 'claim'.
 * The win detection logic is in ClaimCommand.execute(Player) — see ClaimCommand.java lines 47–66.
 * The supporting isGameOver() check is also available in Adventurer.java.
 *
 * STADIUMS (10 required + UEFA HQ finish line):
 *   1. Wanda Metropolitano    (Atletico Madrid)  — Red & White Scarf
 *   2. Emirates Stadium       (Arsenal)          — Cannon Badge
 *   3. Mestalla               (Valencia)         — Bat Pennant
 *   4. Camp Nou               (Barcelona)        — Match Programme
 *   5. Allianz Arena          (Bayern Munich)    — Oktoberfest Stein
 *   6. San Siro               (Inter/AC Milan)   — Vintage Jersey
 *   7. Estadio Bernabeu       (Real Madrid)      — Golden Whistle
 *   8. Anfield                (Liverpool)        — This Is Anfield Sign (replica)
 *   9. Parc des Princes       (PSG)              — Gold Star Patch
 *  10. Johan Cruyff Arena     (Ajax)             — Ajax Scarf
 *      UEFA Headquarters      (Nyon, Switzerland)— claim the Golden Boot here
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
        return
            "╔══════════════════════════════════════════════════════════════════╗\n" +
            "║        ⚽  EUROPEAN FOOTBALL STADIUM HUNT  ⚽                     ║\n" +
            "╚══════════════════════════════════════════════════════════════════╝\n\n" +
            "You are a legendary football memorabilia hunter.\n" +
            "Your mission: travel to 10 iconic European stadiums, collect\n" +
            "one unique item from each, then reach UEFA Headquarters in Nyon\n" +
            "and type 'claim' to win the Golden Boot.\n\n" +
            "Carry limit: 30 kg. Collect wisely.\n" +
            "Type 'help' for a list of commands. Good luck, hunter!\n";
    }

    /**
     * Registers all command words with their associated Command objects.
     * Includes the 3 library commands and 9 user-defined commands:
     *   go, help, quit (library)
     *   north/n, south/s, east/e, west/w, up/u, down/d (all one DirectionCommand class)
     *   take, drop, inventory/i, back, look, examine, score, claim
     */
    @Override
    public void createCommands() {
        CommandWords commands = parser().commandWords();

        // Library commands
        commands.addCommand("go",   new GoCommand());
        commands.addCommand("help", new HelpCommand(commands));
        commands.addCommand("quit", new QuitCommand());

        // Direction commands — ONE class, 12 words
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
     * Builds the full game map — 10 stadiums plus UEFA Headquarters (11 locations total).
     * All rooms are reachable from the starting location (Wanda Metropolitano).
     *
     * Map layout (simplified):
     *
     *                        [Anfield]
     *                            |  north/south
     *             [Emirates] ---east/west--- [Parc des Princes]
     *                  |                           |
     *              north/south                 north/south
     *                  |                           |
     *            [Mestalla] ---east/west--- [Johan Cruyff Arena]
     *                  |
     *              north/south
     *                  |
     *   [Bernabeu] ---east/west--- [Wanda Met] ---east/west--- [Camp Nou]
     *                                   |
     *                               north/south
     *                                   |
     *                            [Allianz Arena]
     *                                   |
     *                               north/south
     *                                   |
     *                             [San Siro] ---east/west--- [UEFA HQ]
     */
    @Override
    public void createRooms() {

        // ── Create Locations ─────────────────────────────────────────────────

        Location wanda = new Location("at Wanda Metropolitano, home of Atletico Madrid");
        Location emirates = new Location("at Emirates Stadium, home of Arsenal FC");
        Location mestalla = new Location("at Mestalla, the fortress of Valencia CF");
        Location campNou = new Location("at Camp Nou, the cathedral of FC Barcelona");
        Location allianz = new Location("at Allianz Arena, the glowing home of Bayern Munich");
        Location sanSiro = new Location("at San Siro, the legendary temple of Milan football");
        Location bernabeu = new Location("at Estadio Santiago Bernabeu, home of Real Madrid");
        Location anfield = new Location("at Anfield, the iconic home of Liverpool FC");
        Location parcDesPrinces = new Location("at Parc des Princes, home of Paris Saint-Germain");
        Location cruyffArena = new Location("at the Johan Cruyff Arena, home of AFC Ajax");
        Location uefaHQ = new Location("at UEFA Headquarters in Nyon, Switzerland");

        // ── Place Collectible Items ───────────────────────────────────────────

        // Wanda Metropolitano — Atletico Madrid Red & White Scarf (weight 2)
        wanda.addItem(new Item("scarf",
            "A red and white Atletico Madrid scarf. 'Con el Atleti hasta la muerte!'", 2));
        // Fixed landmark
        wanda.addItem(new Item("pitch",
            "The immaculate Wanda Metropolitano turf, shimmering under stadium lights.", 50, false));

        // Emirates — Arsenal Cannon Badge (weight 1)
        emirates.addItem(new Item("badge",
            "A brass Arsenal cannon badge, polished to a gleam. The Gunners' proud crest.", 1));
        emirates.addItem(new Item("marble-halls",
            "The famous marble halls at Highbury, commemorated in a plaque on the wall.", 80, false));

        // Mestalla — Valencia Bat Pennant (weight 1)
        mestalla.addItem(new Item("pennant",
            "A Valencia CF pennant bearing the club's bat crest — inspired by the bat of King James I.", 1));
        mestalla.addItem(new Item("south-stand",
            "The steep South Stand of Mestalla, packed with the world's noisiest ultras.", 100, false));

        // Camp Nou — Barcelona Match Programme (weight 1)
        campNou.addItem(new Item("programme",
            "A vintage Camp Nou match programme from the 1999 Champions League final. Priceless.", 1));
        campNou.addItem(new Item("mosaic",
            "A giant tifo mosaic covering the entire lower tier — over 90,000 coloured cards.", 200, false));

        // Allianz Arena — Bayern Munich Oktoberfest Stein (weight 4)
        allianz.addItem(new Item("stein",
            "A Bayern Munich branded Oktoberfest beer stein. Mia san Mia! Heavy but worth it.", 4));
        allianz.addItem(new Item("exterior",
            "The iconic illuminated exterior of Allianz Arena, glowing red tonight.", 999, false));

        // San Siro — Vintage AC Milan / Inter Jersey (weight 2)
        sanSiro.addItem(new Item("jersey",
            "A rare vintage jersey from San Siro's glory days — half rossonero, half nerazzurri.", 2));
        sanSiro.addItem(new Item("spiral-ramps",
            "The famous external spiral ramps of San Siro, an architectural marvel.", 500, false));

        // Bernabeu — Golden Whistle Replica (weight 3)
        bernabeu.addItem(new Item("whistle",
            "A golden referee's whistle, replica of the one used in the 2002 Champions League final.", 3));
        bernabeu.addItem(new Item("trophy-room",
            "Real Madrid's trophy room: 14 Champions League trophies gleam behind reinforced glass.", 999, false));

        // Anfield — 'This Is Anfield' Sign Replica (weight 5)
        anfield.addItem(new Item("sign",
            "A replica of the iconic 'This Is Anfield' sign. Every player touches it for luck.", 5));
        anfield.addItem(new Item("Kop",
            "The legendary Spion Kop stand, towering and passionate. You can feel the history.", 999, false));

        // Parc des Princes — PSG Gold Star Patch (weight 1)
        parcDesPrinces.addItem(new Item("patch",
            "A gold star embroidered patch from PSG's Ligue 1 champion kit. Ici c'est Paris!", 1));
        parcDesPrinces.addItem(new Item("fountain",
            "An ornate fountain outside the Parc des Princes, surrounded by fans' scarves.", 300, false));

        // Johan Cruyff Arena — Ajax Scarf (weight 2)
        cruyffArena.addItem(new Item("ajax-scarf",
            "A black and white Ajax scarf. Worn by a fan who saw Johan Cruyff himself play.", 2));
        cruyffArena.addItem(new Item("retractable-roof",
            "The Johan Cruyff Arena's retractable roof — the first fully covered stadium in the Netherlands.", 400, false));

        // UEFA HQ — no collectible; player must type 'claim' here to win
        uefaHQ.addItem(new Item("golden-boot",
            "The legendary Golden Boot trophy, encased in glass. Type 'claim' to take it!", 10, false));

        // ── Wire Up Exits ─────────────────────────────────────────────────────

        // Wanda <-> Bernabeu (west/east)
        wanda.setExit("west", bernabeu);
        bernabeu.setExit("east", wanda);

        // Wanda <-> Camp Nou (east/west)
        wanda.setExit("east", campNou);
        campNou.setExit("west", wanda);

        // Wanda <-> Allianz Arena (south/north)
        wanda.setExit("south", allianz);
        allianz.setExit("north", wanda);

        // Allianz Arena <-> San Siro (south/north)
        allianz.setExit("south", sanSiro);
        sanSiro.setExit("north", allianz);

        // San Siro <-> UEFA HQ (east/west)
        sanSiro.setExit("east", uefaHQ);
        uefaHQ.setExit("west", sanSiro);

        // Bernabeu <-> Mestalla (north/south)  [via Spanish corridor]
        bernabeu.setExit("north", mestalla);
        mestalla.setExit("south", bernabeu);

        // Mestalla <-> Emirates (west/east)
        mestalla.setExit("west", emirates);
        emirates.setExit("east", mestalla);

        // Mestalla <-> Johan Cruyff Arena (east/west)
        mestalla.setExit("east", cruyffArena);
        cruyffArena.setExit("west", mestalla);

        // Emirates <-> Anfield (north/south)
        emirates.setExit("north", anfield);
        anfield.setExit("south", emirates);

        // Parc des Princes <-> Johan Cruyff Arena (south/north)
        parcDesPrinces.setExit("south", cruyffArena);
        cruyffArena.setExit("north", parcDesPrinces);

        // Anfield <-> Parc des Princes (east/west)  [cross-channel link]
        anfield.setExit("east", parcDesPrinces);
        parcDesPrinces.setExit("west", anfield);

        // ── Player Start ──────────────────────────────────────────────────────
        player().setCurrentRoom(wanda);
    }
}
