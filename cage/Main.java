import com.github.cage.*;
import java.io.*;
import java.util.*;
import java.lang.Math;
import static java.lang.System.nanoTime;
import javax.imageio.*;
import java.awt.image.*;
import java.awt.Image;

public class Main{
    public static final String OUT_PATH = "images/";
    public static final String IMAGE_FORMAT = "png";
    public static final int CAPTCHA_LENGTH = 4;
    public static final int NUM_IMAGES = 20000;
    public static final int IMAGE_WIDTH = 150;
    public static final int IMAGE_HEIGHT = 60;
    

    public static void main(String[] args) throws IOException {
        int i = 0;
        while(i++ < NUM_IMAGES){
            Cage cage = getRandomCage();

            String text = getAlphaNumericString(CAPTCHA_LENGTH);
            long serialization = nanoTime();
            String file = OUT_PATH + text + "_"+ serialization + "." + IMAGE_FORMAT;
            OutputStream os = new FileOutputStream(file, false);
            try {
                cage.draw(text, os);
            } finally {
                os.close();
            }
            try {
                resizeImage(file, IMAGE_WIDTH, IMAGE_HEIGHT);
            } catch(Exception e) {
                System.out.println("resize "+ file +"failed");
            }
        }
    }

    public static Cage getRandomCage()
    {
        Cage[] cages = {new YCage(), new GCage()};
        int index = (int)(cages.length * Math.random());
        return cages[index];
    }

    /* https://www.geeksforgeeks.org/generate-random-string-of-given-size-in-java/ */
    public static String getAlphaNumericString(int n)
    {
        // chose a Character random from this String
        String AlphaNumericString = "0123456789"
                                    + "abcdefghijklmnopqrstuvxyz";
  
        // create StringBuffer size of AlphaNumericString
        StringBuilder sb = new StringBuilder(n);
  
        for (int i = 0; i < n; i++) {
            // generate a random number between
            // 0 to AlphaNumericString variable length
            int index = (int)(AlphaNumericString.length() * Math.random());
  
            // add Character one by one in end of sb
            sb.append(AlphaNumericString.charAt(index));
        }
  
        return sb.toString();
    }

    public static BufferedImage resizeImage(String inputImagePath, int targetWidth, int targetHeight) throws IOException {
        File inputFile = new File(inputImagePath);
        BufferedImage inputImage = ImageIO.read(inputFile);
        BufferedImage outputImage = _resizeImage(inputImage, targetWidth, targetHeight);
        ImageIO.write(outputImage, IMAGE_FORMAT, new File(inputImagePath));
        return outputImage;
    }

    private static BufferedImage _resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) throws IOException {
        Image resultingImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_DEFAULT);
        BufferedImage outputImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        outputImage.getGraphics().drawImage(resultingImage, 0, 0, null);
        return outputImage;
    }
}