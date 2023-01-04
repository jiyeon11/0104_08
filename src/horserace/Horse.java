package horserace;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Horse extends JFrame {
	JLabel cookie_label[] = new JLabel[3];
	int ImageNum;
	int winner[] = new int[3];
	int index = 0;
	int bettingIndex;
	JButton betting_button = new JButton("베팅");
	JButton start_button = new JButton("게임 시작");
	String[] comboStr = {"1번 용감한맛 쿠키","2번 명랑한 쿠키","3번 블랙베리맛 쿠키"};
	JComboBox<String> combobox = new JComboBox<String>(comboStr);
	
	public Horse() {
		JPanel panel = new JPanel();
		JPanel panel_north = new JPanel();
		ImageIcon line_icon = new ImageIcon("image/line.png");
		JLabel line = new JLabel(line_icon);  //선
		line.setBounds(720, 0, 5, 600);  //선 위치 사이즈
		
		for(int i = 0; i<cookie_label.length; i++) {  //멈춤 이미지 라벨에 넣기~
			ImageIcon icon = new ImageIcon("image/stopcookie"+(i+1)+".png");
			cookie_label[i] = new JLabel(icon);
			cookie_label[i].setLocation(0,i*150);
			cookie_label[i].setSize(120,120);
			panel.add(cookie_label[i]);
		}//for

		JButton start_button = new JButton("게임 시작");
		betting_button.addActionListener(buttonListener);
		start_button.addActionListener(buttonListener);
		
		panel_north.add(combobox);
		panel_north.add(betting_button);
		panel_north.add(start_button);
		panel.add(line);
		panel.setLayout(null);
		add(panel_north,"North");
		add(panel, "Center");
		setTitle("쿠키 달리기 대회");
		setSize(800, 600);
		setLocation(300, 150);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
	}//생성자 끝
	
	class HorseRunning extends Thread{
		JLabel cookie_label = new JLabel();
		int ImageNum;
		int cookie_index;
		public HorseRunning(JLabel cookie_label, int ImageNum ) {
			this.cookie_label = cookie_label;
			this.ImageNum = ImageNum;
		}
		
		@Override
		public void run() {
			//쿠키.gif 추가
			ImageIcon run_icon;
			run_icon = new ImageIcon("image/cookie"+ImageNum+".gif");
			cookie_label.setIcon(run_icon);
			
			while(true){ 
				cookie_label.setLocation(cookie_label.getX()+10, cookie_label.getY()); //앞으로 전진
				if(cookie_label.getX() == 670) {  //선 넘으면 멈춤
					winner[index++] = ImageNum;
					cookie_label.setLocation(0, cookie_label.getY());//처음으로 돌아가기
					if(winner[0] == ImageNum) {//우승창
						JOptionPane.showMessageDialog(Horse.this, ImageNum + "번 쿠키가 우승하였습니다~");
						if(bettingIndex+1 == winner[0]) {//베팅창
							JOptionPane.showMessageDialog(Horse.this, "베팅에 성공하셨습니다! 좋으시겠어요~");
						}else {
							JOptionPane.showMessageDialog(Horse.this, "베팅에 실패하셨습니다! 다음을 노리세요~");
						}
						index = 0;
					}
					break;
				}//큰 if
				
				try {
					Random random = new Random();
					sleep(20*random.nextInt(10));
				} catch (InterruptedException e) {
					e.printStackTrace();
				}//try
			}//while
		}//run()
	}//HorseRunning 스레드
	ActionListener buttonListener = new ActionListener() {
		
		@Override
		public void actionPerformed(ActionEvent e) {
			switch(e.getActionCommand()) {
			case "게임 시작" : 
				HorseRunning[] t = new HorseRunning[3];
				for(int i = 0; i<cookie_label.length; i++) {
					ImageNum = i+1;
					t[i]= new HorseRunning(cookie_label[i], ImageNum);
					t[i].start();
				}
				break;
			case "베팅" : 
				bettingIndex = combobox.getSelectedIndex();
				System.out.println(bettingIndex);
				break;
			}
			
		}
	};
	public static void main(String args[]) {
		new Horse();
	}
}
