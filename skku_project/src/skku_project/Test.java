package skku_project;

import javax.swing.*;

import skku_project.ShootingGame.KeyTest;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Timer;
import java.util.TimerTask;

	
public class Test extends JFrame{
		/*생성자입니다.*/
	static JButton rank=new JButton("RANK");
	static JButton history=new JButton("HISTORY");
	private boolean isMainScreen, isLoadingScreen, isGameScreen;
    
		static JPanel main=new JPanel() {
			Image background=new ImageIcon ("src/image/main.jpg").getImage();
			public void paint(Graphics g) {//그리는 함수
					g.drawImage(background, 0, 0, null);//background를 그려줌		
			}
		};
		static JPanel loading=new JPanel() {
			Image background=new ImageIcon ("src/image/loading.jpeg").getImage();
			public void paint(Graphics g) {//그리는 함수
					g.drawImage(background, 0, 0, null);//background를 그려줌		
			}
		};
		static JPanel ingame=new JPanel() {
			Image background=new ImageIcon ("src/image/game.jpeg").getImage();
			public void paint(Graphics g) {//그리는 함수
					g.drawImage(background, 0, 0, null);//background를 그려줌		
			}
		};
		
		public Test() {
			setTitle("1");
			setSize(runGame.SCREEN_WIDTH+18, runGame.SCREEN_HEIGHT+50);
			setResizable(true);//창의 크기를 변경하지 못하게
			setLocationRelativeTo(null);//창이 가운데 나오게
			setLayout(null);
			setVisible(true);//창이 보이게	
			isMainScreen = true;
			mainFrame();
		}	
		public void mainFrame() {
			
			rank.setBounds(50, 800, 200, 100);
			main.add(rank);
			
			history.setBounds(500, 200, 200, 100);			
			main.add(history);
			main.setLayout(null);
			main.setBounds(0, 0, runGame.SCREEN_WIDTH+20, runGame.SCREEN_HEIGHT+20);
			add(main);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//JFrame이 정상적으로 종료되게
		}
		public void mainSetting()
		{
		
			//버튼1의 위치 설
			//page1.add(rank);
			;
		}
		public void loadingframe()
		{
			isMainScreen = false;
			isLoadingScreen = true;
			setTitle("2");
			setSize(runGame.SCREEN_WIDTH+18, runGame.SCREEN_HEIGHT+50);
			setResizable(true);//창의 크기를 변경하지 못하게
			setLocationRelativeTo(null);//창이 가운데 나오게
			setLayout(null);
			setVisible(true);//창이 보이게	
			loading.setLayout(null);
			loading.setBounds(0, 0, runGame.SCREEN_WIDTH+20, runGame.SCREEN_HEIGHT+20);
			add(loading);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//JFrame이 정상적으로 종료되게
		}
		class KeyTest implements KeyListener {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				System.out.println("눌렸다");
				switch(e.getKeyCode()) {
					case KeyEvent.VK_ESCAPE:
						System.exit(0);
						break;
					case KeyEvent.VK_ENTER:
						if(isMainScreen)
						{
							main.setVisible(false);
							loadingframe();
							loading.setVisible(true);
						}
							
						else
							System.exit(0);
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
		}
	}
