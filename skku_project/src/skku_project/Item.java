package skku_project;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Item extends GameObject{
	Image img = new ImageIcon("src/image/item.png").getImage();
	

	public Item(int x, int y) {
		/* add your code, you can add parameter, too */
		this.setX(x);
		this.setY(y);
		this.setHp(1);
		this.setWidth(img.getWidth(null));
		this.setHeight(img.getHeight(null));
		this.setSpeed(7);
	}
	public void fire() {
        this.setY( this.getY()+this.getSpeed() );
    }
}
