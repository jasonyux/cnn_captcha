import com.github.cage.*;
import java.io.*;
import java.util.*;
import java.lang.Math;
import static java.lang.System.nanoTime;

public class Main{
    public static final String outPath = "images/";
    public static final int captchaLength = 4;
    public static final int numImages = 20000;
    

    public static void main(String[] args) throws IOException {
        int i = 0;
        while(i++ < numImages){
            Cage cage = getRandomCage();

            String text = getAlphaNumericString(captchaLength);
            long serialization = nanoTime();
            String file = outPath + text + "_"+ serialization + ".png";
            OutputStream os = new FileOutputStream(file, false);
            try {
                cage.draw(text, os);
            } finally {
                os.close();
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
}