package skku_project;

import java.awt.Image;

import javax.swing.ImageIcon;

public class Enemy extends GameObject{
	Image img = new ImageIcon("src/image/enemy.jpg").getImage();

    public Enemy(int x, int y,int hp,int speed) {
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