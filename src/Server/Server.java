/*
 * windows: start rmiregistry
 * unix: rmiregistry &
 */
package Server;

import Biometrics.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.rmi.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.*;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

/**
 *
 * @author quanmt
 */
public class Server extends UnicastRemoteObject implements FingerprintRecognition {

    String localPath;
    
    private CFingerPrint m_finger1 = new CFingerPrint();
    private CFingerPrint m_finger2 = new CFingerPrint();
    private double finger1[] = new double[m_finger1.FP_TEMPLATE_MAX_SIZE];
    private double finger2[] = new double[m_finger2.FP_TEMPLATE_MAX_SIZE];
    
    public Server() throws RemoteException {
        super();
        File file = new File("");
        localPath = file.getAbsolutePath();
    }

    @Override
    public void save(byte[] bytes) throws RemoteException {
        try {
            File file = new File(localPath + "/trusted.png");
            BufferedImage image = ImageIO.read(new ByteArrayInputStream(bytes));
            ImageIO.write(image, "png", file);
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public Double compareFingerprint(byte[] bytes) throws RemoteException {
        double result = 0.0;
        BufferedImage trustedImage = null;
        BufferedImage image = null;
        try {
            trustedImage = ImageIO.read(new File(localPath + "/trusted.png"));
            image = ImageIO.read(new ByteArrayInputStream(bytes));
        } catch (IOException ex) {
            Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
        }
        
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
        
        return result;
    }
    
    public static void main(String args[]) throws Exception {
	
        Server server = new Server();

        LocateRegistry.createRegistry(12345);
        Naming.rebind("rmi://localhost:12345/fpr", server);
        
        System.out.print("Save image server is running ...");
        
    }
    
}
