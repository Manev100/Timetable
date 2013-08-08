package marc.main;

import javax.swing.text.*;


public final class TimeValidationDocument extends PlainDocument {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TimeValidationDocument() {
	}
	
	public void insertString(final int offset, final String textToInsert, final AttributeSet attributes) throws BadLocationException{
		if((getLength() + textToInsert.length()) <= 5){
			super.insertString(offset, textToInsert, attributes);
		}
		
	}

}
