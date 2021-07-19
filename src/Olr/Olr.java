package Olr;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import java.util.ArrayList;  
import java.util.Arrays;  

public class Olr {
	/*
	 * Global variable declarations
	 */
	public static String character = System.getProperty("user.dir")+"/char.png";
	
	static void simplify_image() {
		/* local variables for function. Values will be modified
		 * in runtime.
		 */
		int r = 0;
		int g = 0;
		int b = 0;
		/*
		 * We will save file as array of white and black, after simplification.
		 */
		String[] pixel_col = {""};
		ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(pixel_col)); 
		try {
			File file = new File(character);
			BufferedImage img = ImageIO.read(file);
			for (int y = 0; y < img.getHeight(); y++) {
				for (int x = 0; x < img.getWidth(); x++) {
					/*
					 * For each pixel in the image, we 
					 * will average its RGB values, and
					 * check whether it's closer to 255 (white)
					 * or 0 (black). 
					 */
					int pixel = img.getRGB(x, y);
					Color color = new Color(pixel, true);
					r = color.getRed();
					g = color.getGreen();
					b = color.getBlue();
					/* 
					 * average the RGB as shown below.
					 */
					int avg = (r+g+b)/3;
					
					if ((avg <= 255) & (avg > 127)) {
						r = 255;
						g = 255;
						b = 255;
						arrayList.add("white");
					}else if (avg <= 127) {
						r = 0;
						g = 0;
						b = 0;
						arrayList.add("black");
					}
					/*
					 * based on the determination explained above,
					 *  we will change that pixel color.
					 */
					color = new Color(r, g, b);
					img.setRGB(x, y, color.getRGB());
					
				}
			}
			pixel_col = arrayList.toArray(pixel_col);  
			file = new File(character);
		    ImageIO.write(img, "png", file);
		} catch(Exception e) {
			/* if input file was not img, or some other error*/
			System.err.println("Error: Image values cannot be read.");
		}
		compare_image_files(pixel_col);
	}
	
	static void compare_image_files(String[] arr) {
		/* following method lists all files in given dir
		 * https://stackoverflow.com/a/4917359
		 */
		File path = new File(System.getProperty("user.dir")+"/fontface");
		File [] files = path.listFiles();
		String filenames[] = Arrays.stream(files).map(File::getAbsolutePath)
		        .toArray(String[]::new);
		float max = 0;
		String dir = "";
		ArrayList<Float> percents = new ArrayList<Float>();
	    for (String temp : filenames) {
			float num = comparison_calc_main(temp, arr);
			percents.add(num);
	    }
	    for (int i = 0; i < percents.size(); i++) {
	    	if (percents.get(i) > max) {
	    		max = percents.get(i);
	    		dir = filenames[i];
	    	}
	    }
	    System.out.println("Prediction: "+dir.replaceAll(System.getProperty("user.dir")+"/fontface/", "").replaceAll(".png", ""));
	    System.out.println("At location "+dir);
	    System.out.println("With a certainty of: "+max*100+"%");
		return;
	}
	
	static float comparison_calc_main(String path, String[] arr) {
		Float confidence[] = {};
		ArrayList<Float> conf_percentage = new ArrayList<Float>(Arrays.asList(confidence)); 
		
		int r = 0;
		int g = 0;
		int b = 0;
		int same = 0;
		int diff = 0;
		int counter = 0;
		String[] pixel_col = {""};
		ArrayList<String> arrayList = new ArrayList<String>(Arrays.asList(pixel_col));
		try {
    		same = 0;
    		diff = 0;
    		File file = new File(path);
    		BufferedImage img = ImageIO.read(file);
    		for (int y = 0; y < img.getHeight(); y++) {
				for (int x = 0; x < img.getWidth(); x++) {
					/*
					 * For each pixel in the image, we 
					 * will average its RGB values, and
					 * check whether it's closer to 255 (white)
					 * or 0 (black). 
					 */
					int pixel = img.getRGB(x, y);
					Color color = new Color(pixel, true);
					r = color.getRed();
					g = color.getGreen();
					b = color.getBlue();
					/* 
					 * average the RGB as shown below.
					 */
					int avg = (r+g+b)/3;
					
					if ((avg <= 255) & (avg > 127)) {
						r = 255;
						g = 255;
						b = 255;
						arrayList.add("white");
						color = new Color(255, 255, 255);
						img.setRGB(x, y, color.getRGB());
					}else if (avg <= 127) {
						r = 0;
						g = 0;
						b = 0;
						arrayList.add("black");
						color = new Color(0, 0, 0);
						img.setRGB(x, y, color.getRGB());
					}
					
					/*
					 * When we detect every pixel has been tested, we can
					 * now compare the pixel values as arrays.
					 */
					if (counter == (img.getWidth()*img.getHeight())-1) {
						Object[] pixels = arrayList.toArray();
						for (int i = 0; i < pixels.length; i++) {
							if (arr[i] == pixels[i]) {
								same = same + 1;
							}else {
								diff = diff + 1;
							}
						}
						float total = same+diff;
						float maths = (float)same/total;
						conf_percentage.add(maths);
					}
					counter = counter + 1;
				}
			}
    		file = new File(path);
    	    ImageIO.write(img, "png", file);

    	} catch(Exception e) {
    		e.printStackTrace();
    	}
		return conf_percentage.get(0);
	}
	
	static void output_results(String[] names, Float[] percent){
		for (int i = 0; i < percent.length; i++) {
			System.out.println(percent.length);
		}
	}
	
	public static void main(String[] args) {
		simplify_image();
	}
}
