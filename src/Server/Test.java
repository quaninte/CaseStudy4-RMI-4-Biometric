/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Server;

import Biometrics.*;
import java.awt.image.*;
import java.io.*;
import javax.imageio.*;

/**
 *
 * @author quanmt
 */
public class Test {
    
    
    public static void main(String[] args) throws Exception {
        
        File file = new File("");
        String localPath = file.getAbsolutePath();
        
        
        CFingerPrint m_finger1 = new CFingerPrint();
        CFingerPrint m_finger2 = new CFingerPrint();
        double finger1[] = new double[m_finger1.FP_TEMPLATE_MAX_SIZE];
        double finger2[] = new double[m_finger2.FP_TEMPLATE_MAX_SIZE];
        
        double result = 0.0;
        
        BufferedImage trustedImage = null;
        BufferedImage image = null;
        
        trustedImage = ImageIO.read(new File(localPath + "/1.png"));
        image = ImageIO.read(new File(localPath + "/2.png"));
        
        // set trusted image
        m_finger1.setFingerPrintImage(trustedImage) ;
        m_finger2.setFingerPrintImage(image) ;
        
        finger1 = m_finger1.getFingerPrintTemplate();
        finger2 = m_finger2.getFingerPrintTemplate();
        
        try {
            result = m_finger1.Match(finger1, finger2, 65, false);
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
        
        System.out.println(result);
    }
    
}
