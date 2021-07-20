Olr
=================================================

<img align="center" src="https://i.imgur.com/G61nvII.png" width="100%"/>

Java Optical Letter Recognition using pure maths, and no Neural Network!

[![License](https://img.shields.io/badge/License-Apache%202.0-lightgray.svg?style=flat-square)](https://opensource.org/licenses/MIT)
[![Latest release](https://img.shields.io/badge/Release-Latest-orange.svg?style=flat-square)](https://github.com/flancast90/Olr/releases)


Table of contents
-----------------

* [Introduction](#introduction)
* [Screenshots](#screenshots)
* [Installation](#installation)
* [Algorithm Basics](#Algorithm)
* [Getting help](#getting-help)
* [Contributing](#contributing)
* [License](#license)
* [Acknowledgments](#acknowledgments)


Introduction
------------

Olr is a Java take on the well-known OCR, or optical character recognition. Unlike these other libraries, however, Olr separates itself by its ease-of-use, completely native dependencies (no external .jars), and lack of Neural Network. Instead, Olr uses a purely meth-driven approach to letter-recognition, keeping CPU-usage at a minimum while also not sacrificing quality. More information about the code, as well as a pseudocode approach to Olr, can be [found below](#Algorithm).


Screenshots
-----------
![Olr Running](https://i.imgur.com/rkoguIN.png)


Algorithm
---------

In pseudocode, the basic steps to recognize each letter, as well as derive the confidence of the image similarity (as percent), are listed as follows.
```pseudocode
1. Change the colors of the sample image (char.png), to black-and-white.
2. Using a pixel-by-pixel approach, initialise an array storing the bytes "0" (white), and "1" (black) for the sample image.
2. For each image in the fontface directory (stores the letter and number images for comparison), also change these to black-and-white.
3. For each image in the fontface directory, initialise an array storing the black and white vals, as was done for the sample image.
4. Compare the sample-image array to the arrays of each of the other files in the fontface directory. For a shared pixel value at the same position, add one to the value of same.
5. For each value of same, add it to a new Java arrayList, and determine which is the highest number within it. Output this number*100 (to make percent), to the user, as well as the name of the file.
6. Replace the path of the file, leaving only the name, which, because of the naming-convention used, is also the value of the letter it represents. Output this to the user.
7. EXIT
```

And some of these steps in Java (only partial):
```Java
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
```
```Java
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
```


Installation
------------

Olr has no non-native dependencies. This makes it super-easy to set-up. Hooray! 🎉

1. To get started with Olr, first ensure that you have a Java IDE installed. One popular such IDE is [Eclipse](https://www.eclipse.org/eclipse/).
2. Inside your IDE, there should be an ``import`` tab, or something comparable. You should be able to select the ``Olr-main`` folder after a few more steps, and choose this to do so. Below, this step is shown as done in [Eclipse](https://www.eclipse.org/eclipse/).
![Import File Image](https://i.imgur.com/xKYBQK5.png)
3. Assuming you imported the file correctly, you should be able to run the ``olr.java`` file!
4. That's it!


Getting help
------------

Hopefully you don't need this section, but in case something goes wrong, feel free to drop me an email at ```flancast90@gmail.com```, or [open a new issue on this GitHub Repo](https://github.com/flancast90/Olr/issues/new). I will do my best to respond ASAP to these problems!


Contributing
------------

Contributions to this file can be done as a [Pull Request](https://github.com/flancast90/Olr/compare), or by shooting an email to ```flancast90@gmail.com```. If anyone would like to be added as a Collaborator to this repo, please send an email to the same address given above. 


License
-------

This README file is distributed under the terms of the [Apache 2.0 License](https://opensource.org/licenses/Apache-2.0). The license applies to this file and other files in the [GitHub repository](http://github.com/flancast90/Olr) hosting this file.


Acknowledgments
---------------

Thanks to everyone in the list below! Each of them helped me on my journey to create Pythune, and their knowledge and expertise in the subject of music, music-theory, or programming, respectively, taught me something new that is now implemented in Pythune. You all are awesome!

Sorry, I'm a JavaScript developer, which (obviously), has no similarity to Java.

* https://github.com/badges/shields
* https://github.com/mhucka/readmine/
* https://onlinetexttools.com/convert-text-to-image
* https://www.javaprogramto.com/2020/04/java-arraylist-maximum-value.html#:~:text=Finding%20the%20max%20value%20from,natural%20ordering%20of%20its%20elements.
* https://www.java67.com/2016/07/how-to-find-length-size-of-arraylist-in-java.html#:~:text=You%20can%20use%20the%20size,present%20in%20the%20array%20list.
* https://stackoverflow.com/questions/15776644/java-how-do-i-initialize-an-array-size-if-its-unknown/15776723
* https://www.geeksforgeeks.org/java-program-for-program-to-find-largest-element-in-an-array/
* https://stackoverflow.com/questions/179427/how-to-resolve-a-java-rounding-double-issue
* https://www.geeksforgeeks.org/how-to-add-an-element-to-an-array-in-java/
* https://stackoverflow.com/questions/4917326/how-to-iterate-over-the-files-of-a-certain-directory-in-java
