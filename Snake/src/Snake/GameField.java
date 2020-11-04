package Snake;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GameField extends JPanel implements ActionListener{
	private final int SIZE = 320;
	private final int DOT_SIZE = 16;
	private final int ALL_DOTS = 400;
	private Image dot;
	private Image apple;
	private int appleX;
	private int appleY;
	private int [] x = new int[ALL_DOTS];
	private int [] y = new int[ALL_DOTS];
	private int dots;
	private Timer timer;
	private boolean left = false;
	private boolean right = true;
	private boolean up = false;
	private boolean down = false;
	private boolean inGame = true;
	
	public GameField() {
		setBackground(Color.yellow);
		loadImages();
		initGame();
		addKeyListener(new FieldKeyListener());
		setFocusable(true);
	}
	public void initGame() {
		inGame = true;
		dots = 3;
		for (int i = 0; i<dots; i++) {
			x[i] = 48 - i*DOT_SIZE;
			y[i] = 48;
		}
		timer = new Timer(250, this);
		timer.start();
		createApple();
		
	}
	
	public void createApple() {		
			boolean freeApple = false;
			int apX = 0;
			int apY = 0;
			int recurs = 0;
			while (freeApple == false) {
				apX = new Random().nextInt(19)*DOT_SIZE;
				apY = new Random().nextInt(19)*DOT_SIZE;
				freeApple = checkFreeApple (apX, apY);
				recurs ++;
				//if (recurs == 100) {break;}
				if (freeApple == true) {break;}
			}
			appleX = apX;
			appleY = apY;
			
	}
	public boolean checkFreeApple(int apX, int apY) {
		boolean freeApple = true;
		for (int i = 0; i < dots; i++) {
			if ((apX == x[i]) && (apY == y[i])) {
				freeApple = false;
			}
		}
		return freeApple;
	}
	
	public void loadImages() {
		
		ImageIcon iid = new ImageIcon("dot.png");
		dot = iid.getImage();
		ImageIcon iia = new ImageIcon("apple.png");
		apple = iia.getImage();
	}
	
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(inGame) {
			g.drawImage(apple, appleX, appleY, this);
			for (int i = 0; i<dots; i++) {
				g.drawImage(dot, x[i], y[i],this);
			}
		}
		else {
			String str = "Game over :( Press Enter to retry.";
			//Font f = new Font("Arial", 14, Font.BOLD);
			g.setColor(Color.red);
			//g.setFont(f);
			g.drawString(str, 57, SIZE/2);
		}
	}
	
	public void snakeMove() {
		for (int i = dots; i>0; i--) {
			x[i] = x[i-1];
			y[i] = y[i-1];
		}
		if (left) {
			if (x[0] == 0) {
				x[0]=SIZE-(DOT_SIZE*2);
			}
			else {
				x[0]-=DOT_SIZE;
			}
		}
		if (right) {
			if (x[0] == SIZE -(DOT_SIZE*2)) {
				x[0] = 0;
			}
			else {
				x[0]+=DOT_SIZE;
			}
		}
		if (up) {
			if(y[0] == 0) {
				y[0]=SIZE-(DOT_SIZE*2);
			}
			else {
				y[0]-=DOT_SIZE;
			}
		}
		if (down) {
			if(y[0] == SIZE-(DOT_SIZE*2)) {
				y[0] = 0;
			}
			else {
				y[0]+=DOT_SIZE;
			}
			
		}
		
	}
	
	public void checkApple() {
		
		if ((x[0] == appleX) && (y[0] == appleY)){
			dots++;
			createApple();
			
		}
	}
	
	public void checkCollisions() {
		for (int i = dots; i > 0; i--) {//i<4
			if ( (x[0] == x[i]) && (y[0] == y[i])) {
				inGame = false;
			}
			if (x[i] > SIZE-DOT_SIZE) {
				inGame = false;
				//x[i] = 0;
			}
			if (x[i] < 0) {
				inGame = false;
				//x[i] = SIZE;
			}
			if (y[i] > SIZE-DOT_SIZE) {
				inGame = false;
				//y[i] = 0;
			}
			if (y[i] < 0) {
				inGame = false;
				//y[i] = SIZE;
			}
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		if (inGame) {
			snakeMove();
			checkApple();
			checkCollisions();
		}
		repaint();
	}
	class FieldKeyListener extends KeyAdapter{
		public void keyPressed(KeyEvent e) {
			super.keyPressed(e);
			int key = e.getKeyCode();
			if ((key == KeyEvent.VK_LEFT) && (y[0] != y[1])) {
				left = true;
				up = false;
				down = false;
			}
			if (key == KeyEvent.VK_RIGHT && (y[0] != y[1])) {
				right = true;
				up = false;
				down = false;
			}
			if (key == KeyEvent.VK_UP && (x[0] != x[1])) {
				up = true;
				left = false;
				right = false;
			}
			if (key == KeyEvent.VK_DOWN && (x[0] != x[1])) {
				down = true;
				left = false;
				right = false;
			}
			if (key == KeyEvent.VK_ENTER) {
				inGame = true;
				dots = 3;
				for (int i = 0; i<dots; i++) {
					x[i] = 48 - i*DOT_SIZE;
					y[i] = 48;
				}
				down = false;
				up = false;
				left = false;
				right = true;
				createApple();
				
			}
		}
	}

}
