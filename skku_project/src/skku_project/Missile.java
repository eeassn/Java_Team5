package skku_project;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Missile extends GameObject{
	Image img = new ImageIcon("src/image/missile.jpeg").getImage();
	

	public Missile(int x, int y,int dmg) {
		/* add your code, you can add parameter, too */
		this.setX(x);
		this.setY(y);
		this.setHp(0);
		this.setWidth(img.getWidth(null));
		this.setHeight(img.getHeight(null));
		this.setSpeed(10);
		this.setDmg(dmg);
	}
	public void fire() {
        this.setY( this.getY()-this.getSpeed() );
    }
}
