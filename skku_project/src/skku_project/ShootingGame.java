package skku_project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;


public class ShootingGame extends JFrame{
	// ��ư�� panel�� ���ؼ� �߰��ϸ� esc, enter�� �ȸ��� �ذ��ϰų� or
	// 

	HashMap<String, ArrayList<String>> rank = new HashMap<String, ArrayList<String>>();
	ArrayList<String> list = new ArrayList<String>();

	private Image bufferImage;
	private Graphics screenGraphic;
	private Audio backgroundMusic;

	private Image mainScreen = new ImageIcon ("src/image/main.jpg").getImage();
	private Image loadingScreen = new ImageIcon ("src/image/loading.jpeg").getImage();
	private Image gameScreen = new ImageIcon ("src/image/game.jpeg").getImage();
	private boolean isMainScreen, isLoadingScreen, isGameScreen;
	private Board game = new Board();


	public ShootingGame() {
		setTitle("JAVA PRACTICE TEAM 5");
		setSize(runGame.SCREEN_WIDTH, runGame.SCREEN_HEIGHT);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		setLayout(null);

		init();
	}
	private void loading() {
		isMainScreen = false;
		isLoadingScreen = true;
		isGameScreen = false;
	}
	private void gameStart() {
		isMainScreen = false;
		isLoadingScreen = false;
		isGameScreen = true;

		game.start();
	}
	public void paint(Graphics g) {
		bufferImage = createImage(runGame.SCREEN_WIDTH, runGame.SCREEN_HEIGHT);
		screenGraphic = bufferImage.getGraphics();
		screenDraw(screenGraphic);
		g.drawImage(bufferImage, 0 , 0 , null);
	}

	public void screenDraw(Graphics g) {
		if (isMainScreen) {
			g.drawImage(mainScreen, 0, 0, null);
		}
		if (isLoadingScreen) {
			g.drawImage(loadingScreen, 0, 0, null);
		}
		if (isGameScreen) {
			g.drawImage(gameScreen, 0, 0, null);
			game.gameDraw(g);
		}
		this.repaint();
	}

	private void init() {
		isMainScreen = true;
		isLoadingScreen = false;
		isGameScreen = false;
		backgroundMusic = new Audio("src/audio/TangoDada.wav", true);
		backgroundMusic.start();
		addKeyListener(new KeyTest());
	}

	class KeyTest implements KeyListener {

		@Override
		public void keyTyped(KeyEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void keyPressed(KeyEvent e) {
			// TODO Auto-generated method stub
			switch(e.getKeyCode()) {
			case KeyEvent.VK_UP:
				game.setUp(true);
				break;
			case KeyEvent.VK_DOWN:
				game.setDown(true);
				break;
			case KeyEvent.VK_LEFT:
				game.setLeft(true);
				break;
			case KeyEvent.VK_RIGHT:
				game.setRight(true);
				break;
			case KeyEvent.VK_1:
			{
				if(isLoadingScreen)
				{
					game.setStage(0); // 35
					game.setDelay(35);
					JOptionPane.showMessageDialog(null,"easy");
				}

				break;
			}
			case KeyEvent.VK_2:
			{
				if(isLoadingScreen)
				{
					game.setStage(1); //25
					JOptionPane.showMessageDialog(null,"normal");
				}
				break;
			}
			case KeyEvent.VK_3:
			{
				if(isLoadingScreen)
				{
					game.setStage(2); //16
					JOptionPane.showMessageDialog(null,"hard");
				}
				break;
			}
			case KeyEvent.VK_ESCAPE:
				backgroundMusic.stop();
				System.exit(0);
				break;
			case KeyEvent.VK_ENTER:
			{
				if(isMainScreen)
					loading();
				else if(isLoadingScreen)
					gameStart();
				else if(game.getIsOver()) System.exit(0);
				break;
			}
			case KeyEvent.VK_J:
			{
				backgroundMusic.stop();
				break;
			}
			case KeyEvent.VK_K:
			{
				backgroundMusic.start();
				break;
			}
			case KeyEvent.VK_Q:
			{
				inputRanking();
				break;
			}
			case KeyEvent.VK_H:
			{
				if(isMainScreen)
				{
					csvRead();
					rankTablePrint();
				}
					
				break;
			}
			case KeyEvent.VK_SPACE:
				game.setShooting(true);
				break;
			}
		}

		@Override
		public void keyReleased(KeyEvent e) {
			switch (e.getKeyCode()) {
			case KeyEvent.VK_UP:
				game.setUp(false);
				break;
			case KeyEvent.VK_DOWN:
				game.setDown(false);
				break;
			case KeyEvent.VK_LEFT:
				game.setLeft(false);
				break;
			case KeyEvent.VK_RIGHT:
				game.setRight(false);
				break;
			case KeyEvent.VK_SPACE:
				game.setShooting(false);
				break;
			}
		}
	}
	public void inputRanking() {
		String input = JOptionPane.showInputDialog("Your Initial.");
		String Score = Integer.toString(game.getScore());
		LocalDate now = LocalDate.now();
		String Date = "";
		int year = now.getYear();
		Date += Integer.toString(year) + "/";
		int monthValue = now.getMonthValue();
		Date += Integer.toString(monthValue) + "/";
		int dayOfMonth = now.getDayOfMonth();
		Date += Integer.toString(dayOfMonth) + "/";
		LocalTime nowTime = LocalTime.now();
		int hour = nowTime.getHour();
		int minute = nowTime.getMinute();
		int second = nowTime.getSecond();
		Date += Integer.toString(hour) + "��";
		Date += Integer.toString(minute) + "��";
		Date += Integer.toString(second)+"��";


		list.add(Score);
		list.add(Date);
		rank.put(input, list);

		csvWrite(input, Score, Date);
		JOptionPane.showMessageDialog(null,"���������� ����Ǿ����ϴ�.");
		JOptionPane.showMessageDialog(null,"Enter�� �����ø� ����˴ϴ�.");
		System.exit(0);
	}
	class RankFrame extends JDialog{
		JLabel jlb = new JLabel("");
		public RankFrame(){
			jlb.setText("rank".toString());
			this.setSize(200,100);
			this.setModal(true);
			this.setVisible(true);


			// file io ����
		}
	}
	class HistoryFrame extends JDialog{
		JLabel jlb = new JLabel("");
		public HistoryFrame(){
			getContentPane().add(jlb);
			jlb.setText("history".toString());
			this.setSize(200,100);
			this.setModal(true);
			this.setVisible(true);


			// file io ����
		}
	}
	
	public void rankTablePrint() {
		for (Entry<String, ArrayList<String>> entry : rank.entrySet()) {
			String name = entry.getKey();
			
			System.out.print(name + " : ");
			System.out.print(entry.getValue().get(0) + " ");
			System.out.println(entry.getValue().get(1));
		}
	}
	public void csvRead()
	{
		File csv = new File("src/result.csv");
        BufferedReader br = null;
        String line = "";

        try {
            br = new BufferedReader(new FileReader(csv));
            while ((line = br.readLine()) != null) { // readLine()�� ���Ͽ��� ����� �� ���� �����͸� �о�´�.
                String[] lineArr = line.split(","); // ������ �� ���� ,�� ������ �迭�� ���� �� ����Ʈ�� ��ȯ�Ѵ�.
                ArrayList<String> temp = new ArrayList<String>();
                temp.add(lineArr[1]);
                temp.add(lineArr[2]);
                //System.out.println( lineArr[1] + " " +lineArr[2] );
                rank.put(lineArr[0],temp);
                //temp.clear();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) { 
                    br.close(); // ��� �� BufferedReader�� �ݾ��ش�.
                }
            } catch(IOException e) {
                e.printStackTrace();
            }
        }
	}

	public void csvWrite(String name, String score, String date) {
		File csv = new File("src/result.csv");
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(csv, true));
			// csv������ ���� ���� �̾���� ��ó�� true�� �����ϰ�, ���� ���� ������� true�� �����Ѵ�
			
			String aData = name + "," + score + "," + date ;
			// �� �ٿ� ���� �� ������ ���̿� ,�� �ִ´�
			bw.write(aData);
			// �ۼ��� �����͸� ���Ͽ� �ִ´�
			bw.newLine(); // ����
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {	
			try {
				if (bw != null) {
					bw.flush(); // �����ִ� �����ͱ��� ���� �ش�
					bw.close(); // ����� BufferedWriter�� �ݾ� �ش�
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}