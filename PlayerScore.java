import javax.swing.JLabel;
import javax.swing.SwingConstants;

import java.awt.Color;
import java.awt.Font;

public class PlayerScore{
	
	static JLabel player1Score = new JLabel("0", SwingConstants.CENTER);
	static JLabel player2Score = new JLabel("0", SwingConstants.CENTER);
	
	public PlayerScore() {
		player1Score.setBounds(0,0,200,100);
		player1Score.setForeground(Color.white);
		player1Score.setFont(new Font("Verdana", Font.PLAIN, 100));
		
		player2Score.setBounds(Frame.frameWidth -220, 0, 200, 100);
		player2Score.setForeground(Color.white);
		player2Score.setFont(new Font("Verdana", Font.PLAIN, 100));
	}
	
	public static void updateGUI() {
		player1Score.setText(Integer.toString(MainPanel.player1Score));
		player2Score.setText(Integer.toString(MainPanel.player2Score));
	}
}
