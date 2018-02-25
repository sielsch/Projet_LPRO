/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package src;

import java.io.*;
import java.net.*;

/**
 *
 * @author Enzo
 */
public class Client_Vigile {
    

    public static void main(String[] args) {
        int port = 1234;
        int tailleBuffer = 2048;
        ServerSocket sockConn = null;
        Socket SocketCommu = null;
        DataInputStream dis = null;

        try {
            //Creates a server socket, bound to the specified port.
            sockConn = new ServerSocket(port);
            SocketCommu = sockConn.accept();
            System.out.println("nouvelle connexion acceptée");
            dis = new DataInputStream(new BufferedInputStream(SocketCommu.getInputStream()));
            while (true) {
                // Listens for a connection to be made to this socket and accepts it.                
               
                String nomfichier = dis.readUTF();
                
                long SizeFichier = dis.readLong();
                
                
                
                File filetowrite = new File(nomfichier);
                FileOutputStream  fos = new FileOutputStream (filetowrite);

                
                byte temp[] = new byte[tailleBuffer];

                int TailleSegm, count = 0, max = 1, min = (int) Double.POSITIVE_INFINITY,tailleTransmis=0;

                while (SizeFichier > 0 && (TailleSegm = dis.read(temp, 0, (int)Math.min(temp.length, SizeFichier))) != -1) {
                    tailleTransmis+=TailleSegm;
                    SizeFichier-=TailleSegm;
                    count++;

                    fos.write(temp, 0, TailleSegm);
                   

                    if (TailleSegm > max) {
                        max = TailleSegm;
                    }
                    if (TailleSegm < min) {
                        min = TailleSegm;
                    }
                }
                System.out.println("\nEnvoyé en " + count + " fois, Max : " + max + ", Min : " + min+"\nTaille total : "+tailleTransmis);
                
                fos.close();                
                System.out.println("fin reception fichier : "+nomfichier);
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Erreur IO1");
        } finally {
            SocketCommu=null;
            try {
                if(dis != null){
                    dis.close();
                }
                if (SocketCommu != null) {
                    SocketCommu.close();
                }
                if (sockConn != null) {
                    sockConn.close();
                }
            } catch (IOException e) {
                System.out.println("Erreur IO2");
            }
        }

    }
}
