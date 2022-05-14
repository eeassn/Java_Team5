package skku_project;

import java.awt.Image;

import javax.swing.ImageIcon;

public class BossEnemy extends GameObject{

	Image img = new ImageIcon("src/image/winaldum.jpg").getImage();

	public BossEnemy(int x, int y,int hp,int speed) {
		this.setX(x);
		this.setY(y);
		this.setWidth(img.getWidth(null));
		this.setHeight(img.getHeight(null));
		this.setHp(hp);
		this.setSpeed(speed);
	}

	public void move() {
		this.setY( this.getY() + this.getSpeed() );
	}

}
