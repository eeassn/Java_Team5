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
	// 버튼을 panel을 통해서 추가하면 esc, enter가 안먹음 해결하거나 or
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
		Date += Integer.toString(hour) + "시";
		Date += Integer.toString(minute) + "분";
		Date += Integer.toString(second)+"초";


		list.add(Score);
		list.add(Date);
		rank.put(input, list);

		csvWrite(input, Score, Date);
		JOptionPane.showMessageDialog(null,"성공적으로 등재되었습니다.");
		JOptionPane.showMessageDialog(null,"Enter를 누르시면 종료됩니다.");
		System.exit(0);
	}
	class RankFrame extends JDialog{
		JLabel jlb = new JLabel("");
		public RankFrame(){
			jlb.setText("rank".toString());
			this.setSize(200,100);
			this.setModal(true);
			this.setVisible(true);


			// file io 구현
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


			// file io 구현
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
            while ((line = br.readLine()) != null) { // readLine()은 파일에서 개행된 한 줄의 데이터를 읽어온다.
                String[] lineArr = line.split(","); // 파일의 한 줄을 ,로 나누어 배열에 저장 후 리스트로 변환한다.
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
                    br.close(); // 사용 후 BufferedReader를 닫아준다.
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
			// csv파일의 기존 값에 이어쓰려면 위처럼 true를 지정하고, 기존 값을 덮어쓰려면 true를 삭제한다
			
			String aData = name + "," + score + "," + date ;
			// 한 줄에 넣을 각 데이터 사이에 ,를 넣는다
			bw.write(aData);
			// 작성한 데이터를 파일에 넣는다
			bw.newLine(); // 개행
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {	
			try {
				if (bw != null) {
					bw.flush(); // 남아있는 데이터까지 보내 준다
					bw.close(); // 사용한 BufferedWriter를 닫아 준다
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}