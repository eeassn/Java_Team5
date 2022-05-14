package skku_project;

public class GameObject{
	private int hp;
	private int dmg;
	private int speed;
	private int x,y;
	
	private int width, height;
	
	public int getHp() {return hp;}
	public void setHp(int hp) {	this.hp = hp;}

	public int getDmg() {return dmg;}
	public void setDmg(int dmg) {this.dmg= dmg;}
	
	public int getSpeed() {return speed;}
	public void setSpeed(int speed) {this.speed= speed;}
	
	public void setX(int x) {
		this.x=x;
	}
	public void setY(int y) {
		this.y=y;
	}
	public int getX() {
		return this.x;
	}
	public int getY() {
		return this.y;
	}
	
	public void setWidth(int width) {
		this.width=width;
	}
	public void setHeight(int height) {
		this.height=height;
	}
	public int getWidth() {
		return this.width;
	}
	public int getHeight() {
		return this.height;
	}
}

