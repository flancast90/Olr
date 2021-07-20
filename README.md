Olr
=================================================

Java Optical Letter Recognition using pure maths, and no Neural Network!

[![License](https://img.shields.io/badge/License-Apache%202.0-lightgray.svg?style=flat-square)](https://opensource.org/licenses/MIT)
[![Latest release](https://img.shields.io/badge/Release-Latest-orange.svg?style=flat-square)](https://github.com/flancast90/Olr/releases)


Table of contents
-----------------

* [Introduction](#introduction)
* [Installation](#installation)
* [Algorithm Basics](#Algorithm)
* [Getting help](#getting-help)
* [Contributing](#contributing)
* [License](#license)
* [Acknowledgments](#acknowledgments)


Introduction
------------

Olr is a Java take on the well-known OCR, or optical character recognition. Unlike these other libraries, however, Olr separates itself by its ease-of-use, completely native dependencies (no external .jars), and lack of Neural Network. Instead, Olr uses a purely meth-driven approach to letter-recognition, keeping CPU-usage at a minimum while also not sacrificing quality. More information about the code, as well as a pseudocode approach to Olr, can be [found below](#Algorithm).


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



1. To get started with Pythune, first make sure Python is downloaded as per https://www.python.org/downloads/, and then download PIP [here](https://pip.pypa.io/en/stable/cli/pip_download/)
2. Next, make sure that the necessary libraries are installed using Pip
```bash
pip install pyaudio
pip install scipy
pip install numpy
pip install tqdm
```
3. Once these libraries are downloaded, start Pythune as follows:
```bash
cd path/to/Pythune-main
cd src
python pythune.py
```
4. That's it! Refer to the [Usage Section](#usage) for the quick-start guide


Usage
-----

### Getting Started

Once Pythune starts, you should see an output similar to the following:
[![Pythune Start Img](https://i.imgur.com/645StsQ.png)]

Click any key to continue when on that screen!

Next, you should see a nifty progress bar; This means Pythune is recording sound, and waiting for the sound of a piano key!
[![Pythune Recording](https://i.imgur.com/pYXkodm.png)]

Once this progress bar reaches 100% (progress bar courtesy of TQDM), you will get a brief message of the file status, its frequency, as well as the distance needed for the key to be tuned to reach perfect pitch! **Remember that Pythune is not perfect. If its output does not seem correct, try restarting the program, there is no limit on how many recordings can be done!**

### Listening to Past-Pythune Recordings

For ease-of-use, Pythune stores all past audio files in its ```cache``` folder. To get there, just navigate to the ``src`` folder and then to the ``cache`` folder within it. The numbers of the file name follow the Pythune convention of the current hour-minute-millisecond, so finding the correct files is pretty easy. If you are A: a mega-hacker, or B: Lacking a GUI, you can also go to the cache folder via a simple ``cd`` command as shown below:
```
cd path/to/src/folder
cd src
```
The contents can then be listed as follows:
```
dir
```

Getting help
------------

Hopefully you don't need this section, but in case something goes wrong, feel free to drop me an email at ```flancast90@gmail.com```, or [open a new issue on this GitHub Repo](https://github.com/flancast90/Pythune/issues/new). I will do my best to respond ASAP to these problems!


Contributing
------------

Contributions to this file can be done as a [Pull Request](https://github.com/flancast90/Pythune/compare), or by shooting an email to ```flancast90@gmail.com```. If any Python or Music-Savvy person would like to be added as a Collaborator to this repo, please send an email to the same address given above. 


License
-------

This README file is distributed under the terms of the [MIT License](https://opensource.org/licenses/MIT). The license applies to this file and other files in the [GitHub repository](http://github.com/flancast90/Pythune) hosting this file.


Acknowledgments
---------------

Thanks to everyone in the list below! Each of them helped me on my journey to create Pythune, and their knowledge and expertise in the subject of music, music-theory, or programming, respectively, taught me something new that is now implemented in Pythune. You all are awesome!

* https://github.com/badges/shields
* https://github.com/mhucka/readmine/
