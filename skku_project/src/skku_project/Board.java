package skku_project;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;


import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class Board extends Thread{
	Image heart = new ImageIcon("src/image/heart.jpeg").getImage();
	private Audio attackSound;
	private Audio hittedSound;

	Player player = new Player( 320 ,750 );
	Missile missile;

	private int stage=0; // easy 0 : normal 1 : hard 2
	///// 난이도 변수
	private int enemyAppear = 70; // easy 80 : normal 60 : hard 40
	private int delay= 25; // easy 36 : normal 25 : hard 16
	private int mujeockTime = 1500;  // easy 2000 : normal 1500 : hard 1000
	private int enemyHp = 10; // easy 5 : normal 10 : hard 20
	private int scorediv = 150; // easy 150 : normal 100 : hard 70
	private int scoreAdd = 500; // easy 500 : normal 1000 : hard 2000
	private int itemAppear = 2000; // easy 2000 : normal 3000 : hard 4000
	private int playeSpeed = 10; // easy 10 : normal 12 : hard 15
	private int missileDmg = 6;
	private int enemyDropSpeed = 10;  // easy 10 : normal 12 : hard 15
	private int EnemyDropSpeedOrigin;
	private int bossAppear = 1000000; //easy 1000000 : normal 2000000 : hard 3000000
	private int bossEnemyHp = 100;  //easy 100 : normal 200 : hard 300
	private int bossDropSpeed = 1;  //easy 1 : normal 2 : hard 3
	/////
	private int cnt; // delay 간극조절

	private long pretime;
	private int missileDelay = 15;
	private int score = 2999999;
	private boolean up,down,left,right,shooting;
	private boolean isOver=false;

	private ArrayList<Missile> playerAttackList = new ArrayList<Missile>();
	private Missile playerAttack;

	private ArrayList<Enemy> enemyList = new ArrayList<Enemy>();
	private Enemy enemy;
	
	private ArrayList<BossEnemy> bossEnemyList = new ArrayList<BossEnemy>();
	private BossEnemy boss;
	
	private ArrayList<Item> itemList = new ArrayList<Item>();
	private Item itemElements;
	ConcurrentHashMap<String, Long> itemEffect = new ConcurrentHashMap<String,Long>();
	
	Board(){
		;
	}

	private long playerLifeCooltime;
	private boolean playerHitted = false;
	
	@Override
	public void run() {

		
		Nanido();

		player.setSpeed(playeSpeed);
		player.setDmg(missileDmg);
		attackSound = new Audio("src/audio/attacksound.wav", false);
		hittedSound = new Audio("src/audio/hitted.wav", false);
		
		while(true) {
			while(!isOver)
			{
				score += cnt/scorediv;
				pretime = System.currentTimeMillis();
				if (System.currentTimeMillis() - pretime < delay) {
					try {
						Thread.sleep(delay - System.currentTimeMillis() + pretime);
						keyProcess();
						playerAttackProcess();
						
						switch(stage) {
						case 2: enemyAppearProcess();
						case 1: enemyAppearProcess();
						case 0: enemyAppearProcess();
						default: enemyAppearProcess();
						break;
						}
						enemyMoveProcess();
						
						itemAppearProcess();
						itemMoveProcess();
						itemEffects();
						itemRemove();
						bossAppearProcess();
						bossMoveProcess();
						if(playerHitted == false)
						{
							playerHitted();
							playerLifeCooltime =System.currentTimeMillis();
						}
						if(System.currentTimeMillis() - playerLifeCooltime > mujeockTime)
							playerHitted=false;
						cnt++;
						
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
		}

	}
	public void Nanido() {
		if( this.stage ==0 )
		{
			this.delay = 36;
			this.enemyAppear = 80;
			this.mujeockTime = 2000;  // easy 2000 : normal 1500 : hard 1000
			this.enemyHp = 6; // easy 5 : normal 10 : hard 20
			this.scorediv = 150; // easy 150 : normal 100 : hard 70
			this.scoreAdd = 500; // easy 500 : normal 1000 : hard 2000
			this.itemAppear = 300; // easy 2000 : normal 3000 : hard 4000
			this.playeSpeed = 10; // easy 10 : normal 12 : hard 15
			this.missileDmg = 6;// easy 6 : normal 5 : hard 4
			this.enemyDropSpeed = 10;  // easy 10 : normal 12 : hard 15
			EnemyDropSpeedOrigin = enemyDropSpeed;
			this.bossAppear = 1000000; //easy 1000000 : normal 2000000 : hard 3000000
			this.bossEnemyHp = 100;  //easy 100 : normal 200 : hard 300
			this.bossDropSpeed = 1;  //easy 3 : normal 4 : hard 5
		}
		else if(this.stage ==1)
		{
			this.delay =25;
			this.enemyAppear = 60;
			this.mujeockTime = 1500;
			this.enemyHp = 10;
			this.scorediv = 100;
			this.scoreAdd = 1000;
			this.itemAppear = 200;
			this.playeSpeed = 12;
			this.missileDmg = 7;
			this.enemyDropSpeed = 12;
			EnemyDropSpeedOrigin = enemyDropSpeed;
			this.bossAppear = 2000000;
			this.bossEnemyHp = 200;
			this.bossDropSpeed = 2;
		}
		else if(this.stage ==2 )
		{
			this.delay= 16;
			this.enemyAppear = 40;
			this.mujeockTime = 1000;
			this.enemyHp = 25;
			this.scorediv = 70;
			this.scoreAdd = 2000;
			this.itemAppear = 50;
			this.playeSpeed = 15; 
			this.missileDmg = 8;
			this.enemyDropSpeed = 15;
			EnemyDropSpeedOrigin = enemyDropSpeed;
			this.bossAppear = 3000000;
			this.bossEnemyHp = 300;
			this.bossDropSpeed = 2;
		}
	}
	private void keyProcess() {
		if (up && player.getY() - player.getSpeed()  > 0) player.setY(player.getY()-player.getSpeed() );
		if (down && player.getY() + player.getHeight() + player.getSpeed() < runGame.SCREEN_HEIGHT) player.setY(player.getY()+player.getSpeed() );
		if (left && player.getX() - player.getSpeed() > 0) player.setX(player.getX()-player.getSpeed() );
		if (right && player.getX() + player.getWidth() + player.getSpeed() < runGame.SCREEN_WIDTH) player.setX(player.getX()+player.getSpeed() );
		if (shooting && cnt % missileDelay == 0) {
			playerAttack = new Missile(player.getX()+ player.getWidth()/4 , player.getY()-50,player.getDmg());
			playerAttackList.add(playerAttack);
		}
	}
	private boolean playerHitted()
	{
		for (int j = 0; j < enemyList.size(); j++) {
			enemy = enemyList.get(j);

			if ( ( player.getX() > enemy.getX() || player.getX() + player.getWidth() > enemy.getX())
					&& (player.getX() < enemy.getX() + enemy.getWidth() ||  player.getX() + player.getWidth() < enemy.getX() +  enemy.getWidth()   )
					&& player.getY() + enemy.getHeight() > enemy.getY() && player.getY() < enemy.getY()  ) {
				player.setHp(player.getHp()-1);
				playerHitted = true;
				hittedSound.start();
				enemyList.remove(enemy);
				if(player.getHp()<=0)
					isOver= true;
				return true;
			}

		}
		for (int j = 0; j < bossEnemyList.size(); j++) {
			boss = bossEnemyList.get(j);

			if ( ( player.getX() > boss.getX() || player.getX() + player.getWidth() > boss.getX())
					&& (player.getX() < boss.getX() + boss.getWidth() ||  player.getX() + player.getWidth() < boss.getX() +  boss.getWidth()   )
					&& player.getY() + boss.getHeight() > boss.getY() && player.getY() < boss.getY()  ) {
				player.setHp(player.getHp()-2);
				playerHitted = true;
				hittedSound.start();
				enemyList.remove(enemy);
				if(player.getHp()<=0)
					isOver= true;
				return true;
			}

		}
		return false;
	}
	private void playerAttackProcess() {
		for (int i = 0; i < playerAttackList.size(); i++) {
			playerAttack = playerAttackList.get(i);
			playerAttack.fire();

			for (int j = 0; j < enemyList.size(); j++) {
				enemy = enemyList.get(j);
				if ( (playerAttack.getX() > enemy.getX()  || playerAttack.getX() + playerAttack.getWidth() > enemy.getX() )
						&& ( playerAttack.getX() < enemy.getX() + enemy.getWidth() || playerAttack.getX() + playerAttack.getWidth()< enemy.getX() + enemy.getWidth() )
						&& playerAttack.getY() + enemy.getHeight() > enemy.getY() && playerAttack.getY() < enemy.getY()  ) {
					enemy.setHp( enemy.getHp() - playerAttack.getDmg());
					playerAttackList.remove(playerAttack);
				}
				if (enemy.getHp() <= 0) {
					attackSound.start();
					enemyList.remove(enemy);
					score += scoreAdd;
				}
			}
			for (int j = 0; j < itemList.size(); j++) {
				itemElements = itemList.get(j);
				if ( (playerAttack.getX() > itemElements.getX()  || playerAttack.getX() + playerAttack.getWidth() > itemElements.getX() )
						&& ( playerAttack.getX() < itemElements.getX() + itemElements.getWidth() || playerAttack.getX() + playerAttack.getWidth()< itemElements.getX() + itemElements.getWidth() )
						&& playerAttack.getY() + itemElements.getHeight() > itemElements.getY() && playerAttack.getY() < itemElements.getY()  ) {
					itemElements.setHp( itemElements.getHp() - playerAttack.getDmg());
					playerAttackList.remove(playerAttack);
				}
				if (itemElements.getHp() <= 0) {
					attackSound.start();
					itemList.remove(itemElements);
					score += scoreAdd;
					itemEvent();
				}
			}
			for (int j = 0; j < bossEnemyList.size(); j++) {
				boss = bossEnemyList.get(j);
				if ( (playerAttack.getX() > boss.getX()  || playerAttack.getX() + playerAttack.getWidth() > boss.getX() )
						&& ( playerAttack.getX() < boss.getX() + boss.getWidth() || playerAttack.getX() + playerAttack.getWidth()< boss.getX() + boss.getWidth() )
						&& playerAttack.getY() + boss.getHeight() > boss.getY() && playerAttack.getY() < boss.getY()  ) {
					boss.setHp( boss.getHp() - playerAttack.getDmg());
					playerAttackList.remove(playerAttack);
				}
				if (boss.getHp() <= 0) {
					attackSound.start();
					bossEnemyList.remove(boss);
					score += scoreAdd*20;
					this.missileDmg++;
					this.player.setHp(this.player.getHp()+1);
				}
			}
		}

	}

	private void enemyAppearProcess() {
		if (cnt % enemyAppear == 0) {
			enemy = new Enemy( (int)(Math.random()*runGame.SCREEN_WIDTH - 49), 0, enemyHp,enemyDropSpeed);
			enemyList.add(enemy);
		}
	}

	private void enemyMoveProcess() {
		for (int i = 0; i< enemyList.size(); i++) {
			enemy = enemyList.get(i);
			enemy.move();
		}
	}
	private void bossAppearProcess() {
		if ( this.cnt%1000 == 0) {
			boss = new BossEnemy( (int)(Math.random()*200 - 49), -400, bossEnemyHp,bossDropSpeed);
			bossEnemyList.add(boss);
		}
	}

	private void bossMoveProcess() {
		for (int i = 0; i< bossEnemyList.size(); i++) {
			boss = bossEnemyList.get(i);
			boss.move();
		}
	}
	private void itemAppearProcess() {
		if (cnt % itemAppear == 0) {
			itemElements = new Item( (int)(Math.random()*runGame.SCREEN_WIDTH - 49), 0);
			itemList.add(itemElements);
		}
	}

	private void itemMoveProcess() {
		for (int i = 0; i< itemList.size(); i++) {
			itemElements = itemList.get(i);
			itemElements.fire();
		}
	}
	private void itemEffects() {
		String itemName;
		for (Entry<String, Long> entry : itemEffect.entrySet()) {
			itemName = entry.getKey();
			if(System.currentTimeMillis() -entry.getValue() < 4000 )
				switch(itemName) {
				case "Speed":
					player.setSpeed(20);
					break;
				case "Damage":
					player.setDmg(20);
					break;
				case "Delay":
					this.missileDelay=8;
					break;
				case "DropRate":
					this.enemyDropSpeed=6;
					break;
				case "invincible":
					this.playerHitted =true;
					break;
				}
		}
	}
	private void itemRemove() {
		String itemName;
		Iterator<Entry<String, Long>> entries = itemEffect.entrySet().iterator();
		while(entries.hasNext()){
		    Map.Entry<String, Long> entry = entries.next();
		    itemName = entry.getKey();
		    if(System.currentTimeMillis() -entry.getValue() > 4000 ) {
				switch(itemName) {
					case "Speed":
						player.setSpeed(this.playeSpeed);
						break;
					case "Damage":
						player.setDmg(this.missileDmg);
						break;
					case "Delay":
						this.missileDelay=15;
						break;
					case "DropRate":
						this.enemyDropSpeed=EnemyDropSpeedOrigin;
						break;
					case "invincible":
						this.playerHitted =false;
						break;
				}
				itemEffect.remove(itemName);
			}
		}
	}
	private void itemEvent()
	{
		int index = (int) (Math.random()*6);
		switch(index) {
		case 0:
			if(player.getHp()<10)
				this.player.setHp(player.getHp()+1);
			break;
		case 1:
			System.out.println("speed");
			itemEffect.put("Speed", System.currentTimeMillis());
			break;
		case 2:
			System.out.println("Dmg");
			itemEffect.put("Damage", System.currentTimeMillis());
			break;
		case 3:			
			System.out.println(" 무적");
			itemEffect.put("invincible", System.currentTimeMillis());
			break;
		case 4:
			System.out.println("노딜");
			itemEffect.put("Delay", System.currentTimeMillis());
			break;
		case 5:
			System.out.println("드랍속도");
			itemEffect.put("DropRate", System.currentTimeMillis());
			break;
		}
	}
	public void gameDraw(Graphics g) {
		playerDraw(g);
		enemyDraw(g);
		infoDraw(g);
		itemDraw(g);
		bossDraw(g);
	}
	public void playerDraw(Graphics g) {
		int tempHeart=player.getHp();
		if(tempHeart>0 && tempHeart < 10)
		{
			for(int q=0;q<tempHeart;q++)
				g.drawImage(heart, 10 + q*60, 110, null);
		}
		else if(tempHeart <= 0)
		{
			JOptionPane.showMessageDialog(null,"you died");
			this.isOver = true;
		}
		g.drawImage(player.playerImg, this.player.getX(), this.player.getY(), null);
		g.setColor(Color.GREEN);
		for (int i = 0; i < playerAttackList.size(); i++) {
			playerAttack = playerAttackList.get(i);
			g.drawImage(playerAttack.img, playerAttack.getX(), playerAttack.getY(), null);
		}
	}
	public void enemyDraw(Graphics g) {
		for (int i = 0; i< enemyList.size(); i++) {
			enemy = enemyList.get(i);
			g.drawImage(enemy.img, enemy.getX(), enemy.getY(), null);
			g.setColor(Color.GREEN);
			if( enemy.getHp() *10 > 100)
			{
				g.setColor(Color.red);
				g.fillRect(enemy.getX() + 1, enemy.getY() -10, (int)((enemy.getHp()-10) *7.5) , 15);
			}
			else
			{
				g.fillRect(enemy.getX() + 1, enemy.getY() -10,  (int)( (enemy.getHp()) *7.5), 15);
			}

		}
	}
	public void bossDraw(Graphics g) {
		for (int i = 0; i< bossEnemyList.size(); i++) {
			boss = bossEnemyList.get(i);
			g.drawImage(boss.img, boss.getX(), boss.getY(), null);
			g.setColor(Color.GREEN);
			if( boss.getHp()  > 200)
			{
				g.setColor(Color.blue);
				g.fillRect(boss.getX() + 1, boss.getY() -10, (boss.getHp()-200)*4 , 15);
			}
			else if( boss.getHp()  > 100)
			{
				g.setColor(Color.red);
				g.fillRect(boss.getX() + 1, boss.getY() -10, (boss.getHp()-100)*4 , 15);
			}
			else
			{
				g.fillRect(boss.getX() + 1, boss.getY() -10, ((boss.getHp()) )*4, 15);
			}

		}
	}
	public void itemDraw(Graphics g) {
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.BOLD, 20));
		for (int i = 0; i< itemList.size(); i++) {
			itemElements = itemList.get(i);
			g.drawImage(itemElements.img, itemElements.getX(), itemElements.getY(), null);
		}
		if(  itemEffect.containsKey("Speed") )
			g.drawString("Speed", 30, 185);
		if( itemEffect.containsKey("Damage"))
			g.drawString("Damage Up", 30, 210);
		if( itemEffect.containsKey("Delayㅈ"))
			g.drawString("No delay", 30, 235);
		if( itemEffect.containsKey("DropRate"))
			g.drawString("Slow Drop", 30, 260);
		if( itemEffect.containsKey("invincible"))
			g.drawString("Invincible", 30, 285);
	}
	public void infoDraw(Graphics g) {
		g.setColor(Color.WHITE);
		g.setFont(new Font("Arial", Font.BOLD, 40));
		g.drawString("SCORE : " + score, 300, 80);
		if (isOver) {
			g.setColor(Color.RED);
			g.setFont(new Font("Arial", Font.BOLD, 80));
			g.drawString("Press Q", 100, 300);
			g.drawString("to Record", 100, 380);
			g.drawString("History", 100, 470);
			
			g.drawString("OR ENTER", 200, 600);
			g.drawString("TO EXIT", 200, 690);
		}
	}
	
	
	public int getScore() {return this.score;}
	public boolean getIsOver() { return this.isOver;}
	public void setStage(int stage) { this.stage = stage;}
	public void setMissileDelay(int delay) {this.missileDelay = delay;}
	public void setDelay(int delay) { this.delay = delay; }
	public void setUp(boolean up) { this.up = up;  }
	public void setDown(boolean down) { this.down = down;  }
	public void setLeft(boolean left) { this.left = left;  }
	public void setRight(boolean right) { this.right = right;  }
	public void setShooting(boolean shooting) { this.shooting = shooting; }
}
