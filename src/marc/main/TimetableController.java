package marc.main;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


public class TimetableController {
	
	private TimetableModel model;
	private TimetableView view;
	
	
	public TimetableController(){
		model = new TimetableModel();
		view = new TimetableView(model);
		model.addObserver(view);
	}
	
	
	
	
	public void show(){
		view.show();		
	}
	
	
	
	/*private void setLookAndFeel(String lookAndFeel) {
	    try {
            // Set System L&F
	    	UIManager.setLookAndFeel(lookAndFeel);
		} 
	    catch (ClassNotFoundException e) {
            System.err.println("Couldn't find class for specified look and feel:"
                               + lookAndFeel);
            System.err.println("Did you include the L&F library in the class path?");
            System.err.println("Using the default look and feel.");
        } 
        
        catch (UnsupportedLookAndFeelException e) {
            System.err.println("Can't use the specified look and feel ("
                               + lookAndFeel
                               + ") on this platform.");
            System.err.println("Using the default look and feel.");
        } 
        
        catch (Exception e) {
            System.err.println("Couldn't get specified look and feel ("
                               + lookAndFeel
                               + "), for some reason.");
            System.err.println("Using the default look and feel.");
            e.printStackTrace();
        }
		
	}
	*/
}
