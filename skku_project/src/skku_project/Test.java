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
		/*�������Դϴ�.*/
	static JButton rank=new JButton("RANK");
	static JButton history=new JButton("HISTORY");
	private boolean isMainScreen, isLoadingScreen, isGameScreen;
    
		static JPanel main=new JPanel() {
			Image background=new ImageIcon ("src/image/main.jpg").getImage();
			public void paint(Graphics g) {//�׸��� �Լ�
					g.drawImage(background, 0, 0, null);//background�� �׷���		
			}
		};
		static JPanel loading=new JPanel() {
			Image background=new ImageIcon ("src/image/loading.jpeg").getImage();
			public void paint(Graphics g) {//�׸��� �Լ�
					g.drawImage(background, 0, 0, null);//background�� �׷���		
			}
		};
		static JPanel ingame=new JPanel() {
			Image background=new ImageIcon ("src/image/game.jpeg").getImage();
			public void paint(Graphics g) {//�׸��� �Լ�
					g.drawImage(background, 0, 0, null);//background�� �׷���		
			}
		};
		
		public Test() {
			setTitle("1");
			setSize(runGame.SCREEN_WIDTH+18, runGame.SCREEN_HEIGHT+50);
			setResizable(true);//â�� ũ�⸦ �������� ���ϰ�
			setLocationRelativeTo(null);//â�� ��� ������
			setLayout(null);
			setVisible(true);//â�� ���̰�	
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
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//JFrame�� ���������� ����ǰ�
		}
		public void mainSetting()
		{
		
			//��ư1�� ��ġ ��
			//page1.add(rank);
			;
		}
		public void loadingframe()
		{
			isMainScreen = false;
			isLoadingScreen = true;
			setTitle("2");
			setSize(runGame.SCREEN_WIDTH+18, runGame.SCREEN_HEIGHT+50);
			setResizable(true);//â�� ũ�⸦ �������� ���ϰ�
			setLocationRelativeTo(null);//â�� ��� ������
			setLayout(null);
			setVisible(true);//â�� ���̰�	
			loading.setLayout(null);
			loading.setBounds(0, 0, runGame.SCREEN_WIDTH+20, runGame.SCREEN_HEIGHT+20);
			add(loading);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//JFrame�� ���������� ����ǰ�
		}
		class KeyTest implements KeyListener {
			
			@Override
			public void keyTyped(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub
				System.out.println("���ȴ�");
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
