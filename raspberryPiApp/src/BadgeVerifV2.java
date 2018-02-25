// START SNIPPET: serial-snippet
package src;

import com.pi4j.io.gpio.*;
import com.pi4j.io.serial.*;
import com.pi4j.util.CommandArgumentParser;
import com.pi4j.util.Console;
import java.sql.*;
import util.DBUtil;
import java.io.IOException;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;

public class BadgeVerif {

    /**
     * This example program supports the following optional command
     * arguments/options: "--device (device-path)" [DEFAULT: /dev/ttyAMA0]
     * "--baud (baud-rate)" [DEFAULT: 38400] "--data-bits (5|6|7|8)" [DEFAULT:
     * 8] "--parity (none|odd|even)" [DEFAULT: none] "--stop-bits (1|2)"
     * [DEFAULT: 1] "--flow-control (none|hardware|software)" [DEFAULT: none]
     *
     * @param args
     * @throws InterruptedException
     * @throws IOException
     */
    //ID ZONE 
    private static final Integer IDZONE = 1;
    private static final String PORT_PC_VIGILE = "1234";
    private static final String IP_PC_VIGILE = "localhost";


    public static void main(String args[]) throws InterruptedException, IOException {
        //communication socket for video sending
        Socket SocketCommu = null;       
        //streams for video sending
        OutputStream os;
        private static DataOutputStream dos = null;
        //buffer used for sending video to vigil pc
        int tailleBuffer = 2048;

    
        

        // create gpio controller
        final GpioController gpio = GpioFactory.getInstance();

        // provision gpio pin #03 as an output pin and turn on LED VERTE
        final GpioPinDigitalOutput greenLED = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_03, "greenLED", PinState.LOW);

        // provision gpio pin #00 as an output pin and turn on LED ROUGE
        final GpioPinDigitalOutput redLED = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_00, "redLED", PinState.LOW);

        // create Pi4J console wrapper/helper
        // (This is a utility class to abstract some of the boilerplate code)
        final Console console = new Console();

        // print program title/header
        console.title("<-- Projet annuel LPRO Raspberry Pi Portiques-->", "Test Badges");

        // allow for user to exit program using CTRL-C
        console.promptForExit();

        // create an instance of the serial communications class
        final Serial serial = SerialFactory.createInstance();

// create and register the serial data listener
        serial.addListener(new SerialDataEventListener() {
            @Override
            public void dataReceived(SerialDataEvent event) {

                // NOTE! - It is extremely important to read the data received from the
                // serial port.  If it does not get read from the receive buffer, the
                // buffer will continue to grow and consume memory.
                // print out the data received to the console
                try {
                    Thread.sleep(1000); // pour eviter que le badge soit lu plusieurs fois
                    String idBadge = event.getHexByteString();
                    idBadge = idBadge.substring(3, 32);
                    idBadge = idBadge.replaceAll(",", "");
                    console.println("[ID] = " + idBadge);
                    String statement = "SELECT * FROM AutorisationAcces WHERE numBadge=\"" + idBadge + "\" AND idZone=" + IDZONE + ";";
                    List<String[]> values = (ArrayList<String[]>) DBUtil.dbExecuteQueryRasp(statement);
                   
                    if (values.size() == 1) {
                     String[] value = values.get(0);
                        if (value[0].equals(idBadge) && value[1].equals(IDZONE.toString())) {                        
                            console.box("ACCESS GRANTED");
                            DBUtil.insertHistoriqueNow(idBadge,IDZONE);
                            Runtime rt = Runtime.getRuntime();
                            String videoName = "../video_" + this.getCurrentTimeStamp()+".h264";
                            Process snap = rt.exec("raspivid -n --timeout 7000 --output "+ videoName);
                            greenLED.toggle();
                            snap.waitFor();
                            greenLED.toggle();//led off after taking video
                            System.out.println("--> green LED state should be : OFF and security video taken");
                            this.sendVideo(videoname);

                        } else {
                            redLED.toggle();
                            console.box("ACCESS DENIED weird");
                            System.out.println("--> red LED state should be: ON");
                            Thread.sleep(2000);
                            redLED.toggle();
                        }

                    } else {
                        redLED.toggle();
                        console.box("ACCESS DENIED");
                        System.out.println("--> red LED state should be: ON");
                        Thread.sleep(2000);
                        redLED.toggle();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (StringIndexOutOfBoundsException e) {

                }
            }
        });

        try {
            //init connection
            InetSocketAddress sockaddrServ = new InetSocketAddress(ipServ, portServ);

            SocketCommu = new Socket();
            SocketCommu.connect(sockaddrServ);

             dos = new DataOutputStream(new BufferedOutputStream(SocketCommu.getOutputStream()));



            // create serial config object
            SerialConfig config = new SerialConfig();

            // serial settings (device, baud rate, flow control, etc)
            config.device("/dev/ttyUSB0")
                    .baud(Baud._2400)
                    .dataBits(DataBits._8)
                    .parity(Parity.NONE)
                    .stopBits(StopBits._1);

            // parse optional command argument options to override the default serial settings.
            if (args.length > 0) {
                config = CommandArgumentParser.getSerialConfig(config, args);
            }

            // display connection details
            console.box(" Connecting to: " + config.toString(),
                    " Data received on serial port will be displayed below.");

            // open the default serial device/port with the configuration settings
            serial.open(config);
            console.box("connected");
            // continuous loop to keep the program running until the user terminates the program
            while (console.isRunning()) {
                try {
                    //waiting for event to happen
                } catch (IllegalStateException ex) {
                    ex.printStackTrace();
                }

            }

        } catch (IOException ex) {
            console.println(" ==>> SERIAL SETUP FAILED : " + ex.getMessage());
            return;
        }finally {
            try {
                if (dos != null) {
                    dos.close();
                }
                if (SocketCommu != null) {
                    SocketCommu.close();
                }
            } catch (IOException e) {
                System.out.println("Erreur IO");

            }
        }
    }
    
public static String getCurrentTimeStamp() {
    SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
    Date now = new Date();
    String strDate = sdfDate.format(now);
    return strDate;
}

public static void sendVideo(String videoname){
    
    try{
                            File filetoread = new File(videoName);

                            byte[] temp = new byte[tailleBuffer];

                            dos.writeUTF(t); // on envoie le nom du fichier à écrire
                            dos.flush(); // on vide le tampon

                            dos.writeLong(filetoread.length());
                            dos.flush();

                            int taille, count = 0, max = 1, min = (int) Double.POSITIVE_INFINITY, tailleFichier = 0;

                            while ((taille = fis.read(temp)) != -1) {
                                dos.write(temp, 0, taille);
                                tailleFichier += taille;
                                count++;
                                System.out.print(taille + " ");

                                if (count % 50 == 0) {
                                    System.out.println();
                                }

                                if (taille > max) {
                                    max = taille;
                                }
                                if (taille < min) {
                                    min = taille;
                                }

                                dos.flush();
                            }

                            System.out.println("\nEnvoyé en " + count + " fois, Max : " + max + ", Min : " + min + "\nNombre d'octet total envoyé : " + tailleFichier + " Taille du fichier de base : " + filetoread.length());

                            fis.close();
    } catch (IOException e) {
                System.out.println("Erreur IO");

            }
}

}

// END SNIPPET: serial-snippet
