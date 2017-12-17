# voogasalad

* Archana Ahlawat, Nik Bramblett, Dara Buggay, Ryan Chung, Samarth Desai, Ian Eldridge-Allegra, Aaron Paskin, Sam Slack, Owen Smith, David Tran
* Started on October 31st, ended on December 15th, estimated 2,500 hours total amongst all group members spent on the project
* Each person's role in the project: 
    * Archana:
    * Nik:
    * Dara:
    * Ryan:
    * Samarth:
    * Ian:
    * Aaron:
    * Sam:
    * Owen: responsible for managing authoring actions and conditions, loading and saving information about them to spriteObjects, and passing and receiving that information from the data stored in XML files for games.
    * David: 
* Resources: Java documentation on list change listeners, lambda expressions, and layout components like HBoxes, VBoxes, TreeViews, and tabs, Program Creek for examples on list change listeners, StackOverflow for researching specific, small algorithms that were useful, and newthinktank.com for an example on MVC.
* Files used to start the project: Scenecontroller, which creates the welcome screen from which the user can either play a pre-existing game, build their own game, or view the settings for the game. 
* Files used to test the project: 
* Errors our program can handle without crashing: incorrectly filing in information about actions and conditions or leaving fields blank in the tree, incorrectly pairing a variable name to a sprite object to assign for a condition or action, and failing to pair at least one condition with every action.
* Data or resource files required by the project: in the TextResources package, there are several properties containing titles and labels used in the conditions and actions tab. 
* Information about using the program: to play a game, select the play game button in the menu and click on a previously created game. To build a game, select create. Create sprite objects, which can be viewed under default sprite objects, and place them on the scene wherever. Their images can be created by loading an image file or drawing them with the drawing tool. Ultimately they are saved by clicking the create button. Edit their properties by clicking on them and editing information about their proprties like actions and conditions, parameters, and dialogue. When editing a sprite object, it is important to click the apply button to save any changes. The game can be saved by going to file and clicking save, and then can be re-run and edited by going to play and selecting the game the user saved. 
* Decisions, assumptions, or simplifications made: in making our role playing game, the genre isn't exactly defined, so we took that to mean make a game building application that can support as many games as possible. In the end, we had functionality for genres like RPG, platformer, puzzle, and arcade shooting games. 
* Known bugs, crashes, or problems with the project's functionality: regarding actions and conditions, cannot copy actions and conditions from one sprite object onto another, cannot completely load the treeview of actions and conditions of each sprite object after being saved, and cannot create sprite actions and conditions from the sprite creator (only in the map editor). 
* Extra features included: our game engine has a game data producer and viewer, which allows the user to view information about any object at any point in the game like its position and parameters. 
* Impressions of assignment: voogasalad was a great final project for the semester because it allowed for the most design freedom and creativity, which definitely expanded given the experience gained from previous projects. It was definitely difficult to manage roles within a 10 person team, but our team skills definitely improved as a result. Of course, our coding skills really improved since we had to program so much more for this project and think in a more modular way. We also learned JavaFX features like serialization and CSS styling. 