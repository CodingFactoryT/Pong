import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.util.Random;
import java.awt.BasicStroke;


public class MainPanel extends JPanel implements ActionListener, KeyListener{
	private static final long serialVersionUID = -2324407767446391362L;
	
	File paddleSoundFile = new File("resources\\paddle.wav");
	File scoreSoundFile = new File("resources\\score.wav");
	
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
	
	Clip[] audioClips = new Clip[2];
	
	MainPanel(){	
        audioClips = initSound();
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
	
	private Clip[] initSound() {
		Clip[] audioClips = new Clip[2];
		
		try {
			AudioInputStream paddleAudioStream = AudioSystem.getAudioInputStream(paddleSoundFile);
			AudioInputStream scoreAudioStream = AudioSystem.getAudioInputStream(scoreSoundFile);
			
			Clip paddleSoundClip = AudioSystem.getClip();
			Clip scoreSoundClip = AudioSystem.getClip();
			
			paddleSoundClip.open(paddleAudioStream);
			scoreSoundClip.open(scoreAudioStream);
			
			audioClips[0] = paddleSoundClip;
			audioClips[1] = scoreSoundClip;
		} catch(Exception e) {
				System.out.println("Something went wrong with the sound!");
		}
		
		return audioClips;
	}
	
	private int getRandomSpeed() {
		Random random = new Random();
		if(random.nextBoolean() == true) return speed;
		return -speed;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(isRunning) {

			player1UpperY += player1Velocity;
			player2UpperY += player2Velocity;
	
			player1LowerY = player1UpperY + playerHeight;
			player2LowerY = player2UpperY + playerHeight;
			
			if(ballX < playerWidth && ballY > player1UpperY && ballY < player1LowerY) {
				currentBallXSpeed = speed;
				audioClips[0].start();
				audioClips[0].setMicrosecondPosition(0);
			}
			if(ballX > Frame.frameWidth - playerWidth -40 && ballY > player2UpperY && ballY < player2LowerY) {
				currentBallXSpeed = -speed;
				audioClips[0].start();
				audioClips[0].setMicrosecondPosition(0);
			}
			
			if(player1UpperY < 0) player1UpperY = 0;
			if(player1UpperY > 611) player1UpperY = 611;
			
			if(player2UpperY < 0) player2UpperY = 0;
			if(player2UpperY > 611) player2UpperY = 611;
		
			ballX += currentBallXSpeed;
			ballY += currentBallYSpeed;
			
			if(ballX < 0) {
				player2Score++;
				PlayerScore.updateGUI();
				resetAfterScore();
				audioClips[1].start();
				audioClips[1].setMicrosecondPosition(0);
			}
			if(ballX > Frame.frameWidth) {
				player1Score++;
				PlayerScore.updateGUI();
				resetAfterScore();
				audioClips[1].start();
				audioClips[1].setMicrosecondPosition(0);
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
		ballX = Frame.frameWidth /2 -10;
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

	float dash[] = {20.0f};
	BasicStroke dashed = new BasicStroke(10,BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 100.0f, dash, 0f);
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.setColor(Color.blue);
		g.fillRect(0, player1UpperY, playerWidth, playerHeight);
		g.setColor(Color.red);
		g.fillRect(Frame.frameWidth - playerWidth -16, player2UpperY, playerWidth, playerHeight);
		
		g.setColor(Color.white);
		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(dashed);
		g2.drawLine(Frame.frameWidth/2, 0, Frame.frameWidth/2, Frame.frameHeight);
		
		g.setColor(Color.yellow);
		g.fillOval(ballX,ballY,ballSize,ballSize);	
	}

}
