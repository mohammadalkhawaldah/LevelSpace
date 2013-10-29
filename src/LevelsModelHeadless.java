import java.awt.image.BufferedImage;
import java.io.IOException;

import org.nlogo.api.CompilerException;
import org.nlogo.api.LogoException;
import org.nlogo.headless.HeadlessWorkspace;

public class LevelsModelHeadless extends LevelsModelAbstract {
	
	HeadlessWorkspace myWS;
	ImageFrame frame;
	String name;
	String path;
	double levelsSpaceNumber;
	
	public LevelsModelHeadless(String url, double levelsSpaceNumber)
	{
		// make a new headless workspace
		myWS = HeadlessWorkspace.newInstance();
		// load the model inthe headless workspace
		try {
			myWS.open(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (CompilerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LogoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		path = url;
		
		this.levelsSpaceNumber = levelsSpaceNumber;
		
        // find the name of the model - it is the bit past the last 
        // dash
        int lastDashPosition = url.lastIndexOf("/") + 1;
        name = url.substring(lastDashPosition);
		
	}

	public void createImageFrame(){
		// if there already is a frame, we don't do anything.
		if (frame != null) {return;}
		// get an image from the model so we know how big it is
		BufferedImage bi = myWS.exportView();
		
		// create a new image frame to show what's going on in the model
		// send it the image to set the size correctly
		String aTitle = name.concat(" (LevelsSpace Model No. ").concat(Double.toString(levelsSpaceNumber)).concat(")");
		frame = new ImageFrame(bi, aTitle);
	}
	

	public void updateView()
	{
		if (frame != null){
			// get the image from the workspace
			BufferedImage bi = myWS.exportView();
			// update the frame
			
			frame.updateImage(bi);
		}

	}
	
	public void removeImageFrame(){
		frame.dispose();
		frame = null;
	}
	
	public void command (String command)
	{
		// run the command
		try {
			myWS.command(command);
		} catch (CompilerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LogoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void kill()
	{
		// kill the headless workspace
		try {
			myWS.dispose();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// kill the image frame if there is one
		if (frame != null){
			frame.dispose();

		}
	}
	
	public Object report (String varName)
	{
		Object reportedValue = null;
		try {
			reportedValue = myWS.report(varName);
		} catch (CompilerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (LogoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return reportedValue;
	}

	public String getName()
	{
		return name;
	}

	public String getPath() {
		// TODO Auto-generated method stub
		return path;
	}

	@Override
	void breathe() {
		myWS.breathe();
		
	}
	
	
	
}