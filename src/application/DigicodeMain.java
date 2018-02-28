package application;

import java.sql.SQLException;

import util.DBUtil;
import view.DigiCodeWindow;

public class DigicodeMain {

	public static void main(String [] args){
		
		String numBadge="12345123451234512345";
		
		int essai = 0;
		boolean correct=false;

		DigiCodeWindow digiCodeWindow = new DigiCodeWindow(numBadge);
		
		do {

			digiCodeWindow.setVisible(true);
			digiCodeWindow.setPswField("");
			if (digiCodeWindow.isCorrect()) {
				correct=true;
				System.out.println("--> green LED state should be : OFF and security video taken");
			} else {
				essai++;
				System.out.println("essai ++");
			}

		} while (essai < 3 && !correct);
		
		if(!correct){
			System.out.println("--> red LED state should be: ON");
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		digiCodeWindow.dispose();
		System.out.println("end of main");
		
	}
	
	
}
