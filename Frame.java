package main.java;
import javax.swing.JFrame;

public class Frame extends JFrame {
	private static final long serialVersionUID = 5111686254023266177L;
	
	public static int frameWidth = 1500;
	public static int frameHeight = 800;
	Frame(){
		this.setTitle("Pong!");
		this.setLayout(null);
		this.setSize(frameWidth, frameHeight);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.add(new MainPanel());
		this.setVisible(true);
	}
}
