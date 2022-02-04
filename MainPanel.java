import javax.swing.JPanel;
import javax.swing.Timer;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;


public class MainPanel extends JPanel implements ActionListener, KeyListener{
	private static final long serialVersionUID = -2324407767446391362L;
	
	final int playerWidth = 20;
	final int playerHeight = 150;
	int player1UpperY = Frame.frameHeight /2 - playerHeight /2;
	int player2UpperY = Frame.frameHeight /2 - playerHeight /2;
	int player1Velocity = 0;
	int player2Velocity = 0;
	int player1LowerY = player1UpperY + playerHeight;
	int player2LowerY = player2UpperY + playerHeight;
	
	final int ballSize = 20;
	int ballX = Frame.frameWidth /2;
	int ballY = Frame.frameWidth /2;
	int currentBallXSpeed = 0;
	int currentBallYSpeed = 0;
	final int speed = 12;
	
	Timer timer = new Timer(1, this);
	
	boolean isRunning = false;
	public static int player1Score = 0;
	public static int player2Score = 0;
	
	MainPanel(){		
		this.setBounds(0,0,Frame.frameWidth, Frame.frameHeight);
		this.setBackground(Color.black);
		this.setLayout(null);
		
		new PlayerScore();
		this.add(PlayerScore.player1Score);
		
		new PlayerScore();
		this.add(PlayerScore.player2Score);
		
		currentBallXSpeed = getRandomSpeed();
		currentBallYSpeed = getRandomSpeed();
		
		timer.start();
        this.addKeyListener(this);
        this.setFocusable(true);
        this.setFocusTraversalKeysEnabled(false);
        
        resetAfterScore();
	}
	
	private int getRandomSpeed() {
		Random random = new Random();
		if(random.nextBoolean() == true) return speed;
		return -speed;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		player1UpperY += player1Velocity;
		player2UpperY += player2Velocity;

		player1LowerY = player1UpperY + playerHeight;
		player2LowerY = player2UpperY + playerHeight;
		
		if(ballX < playerWidth && ballY > player1UpperY && ballY < player1LowerY) currentBallXSpeed = speed;
		if(ballX > Frame.frameWidth - playerWidth -40 && ballY > player2UpperY && ballY < player2LowerY) currentBallXSpeed = -speed;
		
		if(player1UpperY < 0) player1UpperY = 0;
		if(player1UpperY > 611) player1UpperY = 611;
		
		if(player2UpperY < 0) player2UpperY = 0;
		if(player2UpperY > 611) player2UpperY = 611;
		
		if(isRunning) {
			ballX += currentBallXSpeed;
			ballY += currentBallYSpeed;
			
			if(ballX < 0) {
				player2Score++;
				PlayerScore.updateGUI();
				resetAfterScore();
			}
			if(ballX > Frame.frameWidth) {
				player1Score++;
				PlayerScore.updateGUI();
				resetAfterScore();
			}
			
			if(ballY < 0) currentBallYSpeed = speed;
			if(ballY > Frame.frameHeight - 65) currentBallYSpeed = -speed;
			
		}
		repaint();
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();

		if(keyCode == KeyEvent.VK_W) player1Velocity = -speed;	
		if(keyCode == KeyEvent.VK_S) player1Velocity = speed;
		
		if(keyCode == KeyEvent.VK_UP) player2Velocity = -speed;	
		if(keyCode == KeyEvent.VK_DOWN) player2Velocity = speed;
		if(keyCode == KeyEvent.VK_SPACE) isRunning = !isRunning;
		
	}
	
	public void resetAfterScore() {
		isRunning = false;
		ballX = Frame.frameWidth /2;
		ballY = Frame.frameHeight /2;
		currentBallXSpeed = getRandomSpeed();
		currentBallYSpeed = getRandomSpeed();
	}
	
	@Override
	public void keyTyped(KeyEvent e) {}	
	@Override
	public void keyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();

		if(keyCode == KeyEvent.VK_UP || keyCode == KeyEvent.VK_DOWN) player2Velocity = 0;
		if(keyCode == KeyEvent.VK_W || keyCode == KeyEvent.VK_S) player1Velocity = 0;

	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.white);
		g.fillRect(0, player1UpperY, playerWidth, playerHeight);
		g.fillRect(Frame.frameWidth - playerWidth -16, player2UpperY, playerWidth, playerHeight);
		g.fillOval(ballX,ballY,ballSize,ballSize);
	}

}
