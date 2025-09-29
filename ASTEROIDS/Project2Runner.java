public class Project2Runner {
    
    /*
     * Name: Adrian Nur
     * Student ID: 501227509
     * 
     ******** Project Description ********
     * 
     * Describe in plain English the overall program/program in a paragraph or 2.
     * 
     * I've always been captivated by classic arcade games like Galaga or Asteroids.
     * Unfortunately I'm broke and can't continuously spend quarters at the machines
     * So I decided to make my own version of Asteroids for this project!
     * 
     * Due to time constraints, I unfortunately wasn't able to polish much of the game,
     * as seen in the main menu being very basic and the asteroids all being simple
     * spheres. I also intended to code in UFO's like the arcade game that would shoot
     * back at the Player, although I didn't have time to figure it out. Still, I hope
     * this version is enjoyable!
     * 
     ******** Swing Requirement ********
     * 
     * Describe in 1 paragraph how your program satisfies the requirement that
     * there is at least 3 unique components. Be clear to identify in what
     * files and the lines number (just the starting line is fine) that the
     * components are defined on.
     * 
     * All 3 unique swing components can all be found in the MainMenu.java file.
     * The first component is a couple labels, which are used to display the title,
     * the high score, and the instructions. The second component is the Jbutton, 2
     * are used to start the game or quit the game respectively. The final component
     * is a JRadiobutton, which 2 of them are in a group that handles the game's
     * difficulty, Normal and Challenge mode respectively. There is also a label in
     * the main GamePanel.java file that actively shows the Player's current score
     * during the game.
     * 
     * The lines for some example components (In MainMenu.java) are:
     * Title Label: Declared in line 9. Properly start to set up in line 31.
     * Start Game Button: Declared in line 10. Properly start to set up in line 57.
     * Difficulty Radio Button: Declared in lines 12,13.
     * Properly start to set up in line 39.
     * 
     ******** 2D Graphics Requirement ********
     *
     * Describe in 1 paragraph how your program satisfies the requirement that
     * there is at least 1 JPanel used for drawing something. Be clear to
     * identify in what files and the line numbers that this panel is defined on.
     * 
     * There are 2 main panels for the game, the first is declared within MainMenu.java
     * in line 8. This panel is used to display the game's menu, and is used to draw
     * all the buttons and labels on it. The second panel is the GamePanel.java file.
     * This class extends the JPanel and is used to draw the main game loop in, and 
     * actually supports animation unlike MainMenu.java. The Jpanel is created when
     * a new GamePanel object is constructed.
     * 
     ******** Event Listener Requirement ********
     *
     * Describe in 1 paragraph how your program satisfies the requirement that
     * there is at least one ActionListener, and there is additionally at least
     * one MouseListener or ActionListener. Be clear to identify in what file
     * and the line numbers that these listeners are defined in.
     * 
     * For an actionlistener/actionperformed example, I set a timer in the Bullet.java
     * class which ensures bullets only last for a second before getting erased
     * automatically. This timer can be found in line 30 of the Bullet.java class.
     * As for some Keyboardlisteners, they can all be found in GamePanel.java in
     * line 147 and line 170. These are press and release functions respectively, that
     * are used to control the player's movement and bullet shooting.
     * 
     */

    public static void main(String[] args) {
        MainMenu startmenu = new MainMenu();
    }
}
