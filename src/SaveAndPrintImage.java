import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

public class SaveAndPrintImage implements Printable {
	BufferedImage img;

	SaveAndPrintImage(JComponent myComponent, Dimension size) {
		BufferedImage img = new BufferedImage(size.width, size.height,
				BufferedImage.TYPE_INT_RGB);

		Graphics2D g2 = img.createGraphics();

		myComponent.paint(g2);
	}

	public void saveImage(File outputfile) {
		try {
			ImageIO.write(img, "png", outputfile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int print(Graphics g, PageFormat pf, int page)
			throws PrinterException {

		if (page > 0) { /* We have only one page, and 'page' is zero-based */
			return NO_SUCH_PAGE;
		}

		/*
		 * User (0,0) is typically outside the imageable area, so we must
		 * translate by the X and Y values in the PageFormat to avoid clipping
		 */
		Graphics2D g2d = (Graphics2D) g;
		g2d.translate(pf.getImageableX(), pf.getImageableY());

		/* Now we perform our rendering */
		g2d.drawImage(img, null, 0, 0);

		/* tell the caller that this page is part of the printed document */
		return PAGE_EXISTS;
	}

	public void actionPerformed(ActionEvent e) {
		PrinterJob job = PrinterJob.getPrinterJob();
		job.setPrintable(this);
		boolean ok = job.printDialog();
		if (ok) {
			try {
				job.print();
			} catch (PrinterException ex) {
				/* The job did not successfully complete */
			}
		}
	}

}
