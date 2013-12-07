package marc.main;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextField;
import javax.swing.text.*;

import marc.ItemStuff.Time;

public class TimeField extends JTextField {
	private int editNumber;
	private String time;
	
	public TimeField(){
		super(new TimeValidationDocument(),"__:__",3);
		editNumber = 4;
		time = "__:__";
		
		this.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				int key = e.getKeyChar();
				if(key<=57 && key>=48){
					int number = extractNumberTyped(key);
					switch(editNumber){
					case 1:
						time = "" + time.charAt(1) + time.charAt(3) + ":" + time.charAt(4) + number;
						editNumber = 4;
						setText(time);
						break;
					case 2:
						if(number > 5){
							setText(time);
							break;
						}
						time = "_" + time.charAt(3)+ ":" + time.charAt(4) + number;	
						editNumber = 1;
						setText(time);
						break;
					case 3:
						if(Integer.parseInt("" + time.charAt(4)) == 2 && number > 3){
							setText(time);
							break;
						}
						time = "__:"  + time.charAt(4) + number;
						editNumber = 2;
						setText(time);
						break;
					case 4:
						if(number > 2){
							setText(time);
							break;		
						}
						time = "__:_" + number;
						editNumber = 3;
						setText(time);
						break;
					}
					
				}else{
					setText(time);
				}
			}
			
			private int extractNumberTyped(int key) {
				switch(key){
					case 48:
						return 0;
					case 49:
						return 1;
					case 50:
						return 2;
					case 51:
						return 3;
					case 52:
						return 4;
					case 53:
						return 5;
					case 54:
						return 6;
					case 55:
						return 7;
					case 56:
						return 8;
					case 57:
						return 9;
					default:
						throw new IllegalArgumentException("key must be between 48 and 57, ascii-code of 0,...,9");
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				
				
			}
		});
		
	}
	
	// -1 if time not filled in correctly
	public int timeInInteger(){
		String t = getTime();
		try{
			return Integer.parseInt(t);	
		}catch(NumberFormatException e){
			return -1;
		}
	}
	
	public String getTime(){
		String t = "" + time.substring(0, 2) + time.substring(3,5);
		return t;
	}
	
	public void setTime(Time t){
		time = t.getFormattedTime();
		setText(time);
		editNumber = 4;
	}
	
	public void reset(){
		time = "__:__";
		setText(time);
		editNumber = 4;
	}
}
