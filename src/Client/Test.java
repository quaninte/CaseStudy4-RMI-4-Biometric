/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Client;

import Server.FingerprintRecognition;
import java.awt.image.*;
import java.io.*;
import java.rmi.*;
import javax.imageio.*;

/**
 *
 * @author quanmt
 */
public class Test {
    
    public static void main(String[] args) throws Exception {
        File file = new File("");
        String localPath = file.getAbsolutePath();
        file = new File(localPath + "/src/SampleImages/1-mod.png");
        System.out.println(file.getAbsolutePath());
        
        BufferedImage image = ImageIO.read(file);
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        javax.imageio.ImageIO.write(image, "png", baos);
        
        FingerprintRecognition service = (FingerprintRecognition) Naming.lookup("rmi://localhost:12345/saveImage");
        Double percentage = service.compareFingerprint(baos.toByteArray());
        
        System.out.println(percentage);
    }
    
}
