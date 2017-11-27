package engine;

/**
 * @author Nikolas Bramblett
 */
public class DialogueBox extends GameObject {

	
	private String dialogue;
	public DialogueBox() {
		// TODO Auto-generated constructor stub
		super();
		addTag("Dialogue Box");
	}

	public DialogueBox(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	
	public void addDialogue(String words)
	{
		dialogue = words;
	}
	
	public String getDialogue()
	{
		return dialogue;
	}

}
