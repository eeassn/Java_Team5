package skku_project;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Player extends GameObject{
	Image playerImg = new ImageIcon("src/image/nose.jpeg").getImage();
	public Player(int x, int y ) {
		/* add your code, you can add parameter, too */
		
		this.setX(x);
		this.setY(y);
		this.setWidth(playerImg.getWidth(null));
		this.setHeight(playerImg.getHeight(null));
		this.setHp(3);
	}	
	
	public void move(int y,int x) {
		/* add your code, you can add parameter, too */
		
		this.setY(y);
		this.setX(x);
	}
}
