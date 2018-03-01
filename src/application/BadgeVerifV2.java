// START SNIPPET: serial-snippet
package application;

import com.pi4j.io.gpio.*;
import com.pi4j.io.serial.*;
import com.pi4j.util.CommandArgumentParser;
import com.pi4j.util.Console;
import java.sql.*;
import util.DBUtil;
import view.DigiCodeWindow;

import java.io.*;
import java.net.*;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;
import java.text.SimpleDateFormat;

public class BadgeVerifV2 {

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
    private static final int PORT_PC_VIGILE = 1234;
    private static final String IP_PC_VIGILE = "192.168.0.19";
    private static DataOutputStream dos = null;
    private static final int tailleBuffer = 2048;
    //communication socket for video sending
    private static  Socket SocketCommu = null;       
    //streams for video sending
    private static OutputStream os;
            


    public static void main(String args[]) throws InterruptedException, IOException {
       
        initConnexion();
    
        

        // create gpio controller
        final GpioController gpio = GpioFactory.getInstance();

        // provision gpio pin #03 as an output pin and turn on LED VERTE
        final GpioPinDigitalOutput greenLED = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_03, "greenLED", PinState.LOW);

        // provision gpio pin #00 as an output pin and turn on LED ROUGE
        //final GpioPinDigitalOutput redLED = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_00, "redLED", PinState.LOW);
		
		 // provision gpio pin #02 as an input pin with its internal pull down resistor enabled
        final GpioPinDigitalInput pushButton = gpio.provisionDigitalInputPin(RaspiPin.GPIO_02, PinPullResistance.PULL_DOWN);

        // set shutdown state for this input pin
        pushButton.setShutdownOptions(true);

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

				// NOTE! - It is extremely important to read the data received
				// from the
				// serial port. If it does not get read from the receive buffer,
				// the
				// buffer will continue to grow and consume memory.
				// print out the data received to the console
				try {
					Thread.sleep(1000); // pour eviter que le badge soit lu
										// plusieurs fois
					String idBadge = event.getHexByteString();
					idBadge = idBadge.substring(3, 32);
					idBadge = idBadge.replaceAll(",", "");
					console.println("[ID] = " + idBadge);
					String statement = "SELECT * FROM AutorisationAcces WHERE numBadge=\"" + idBadge + "\" AND idZone="
							+ IDZONE + ";";
					List<String[]> values = (ArrayList<String[]>) DBUtil.dbExecuteQueryRasp(statement);

					if (values.size() == 1) {
						String[] value = values.get(0);
						if (value[0].equals(idBadge) && value[1].equals(IDZONE.toString())) {

							int essai = 0;
							boolean correct=false;

							DigiCodeWindow digiCodeWindow = new DigiCodeWindow(idBadge);
							
							do {

								digiCodeWindow.setVisible(true);
								digiCodeWindow.setPswField("");
								if (digiCodeWindow.isCorrect()) {
									correct=true;
									 console.box("ACCESS GRANTED");
						                            DBUtil.insertHistoriqueNow(idBadge,IDZONE);
						                            greenLED.toggle();
						                            Thread.sleep(5000); 
						                            greenLED.toggle();//led off after taking video
                            						    System.out.println("--> green LED state should be : OFF");
								} else {
									essai++;
								}

							} while (essai < 3 && !correct);
							
							if(!correct){
								//redLED.toggle();
								console.box("ACCESS DENIED");
								System.out.println("--> red LED state should be: ON");
								Thread.sleep(2000);
								//redLED.toggle();
							}
							
							digiCodeWindow.dispose();

							// console.box("ACCESS GRANTED");
							// DBUtil.insertHistoriqueNow(idBadge,IDZONE);
							// Runtime rt = Runtime.getRuntime();
							// String videoName = "../video_" +
							// getCurrentTimeStamp()+".h264";
							// Process snap = rt.exec("raspivid --timeout 7000
							// --output "+ videoName);
							// greenLED.toggle();
							// snap.waitFor();
							// greenLED.toggle();//led off after taking video
							// System.out.println("--> green LED state should be
							// : OFF and security video taken");
							// sendVideo(videoName);

						} else {
							//redLED.toggle();
							console.box("ACCESS DENIED weird");
							System.out.println("--> red LED state should be: ON");
							Thread.sleep(2000);
							//redLED.toggle();
						}

					} else {
						//redLED.toggle();
						console.box("ACCESS DENIED");
						System.out.println("--> red LED state should be: ON");
						Thread.sleep(2000);
						//redLED.toggle();
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
        
        
        // create and register gpio pin listener
        pushButton.addListener(new GpioPinListenerDigital() {
            @Override
            public void handleGpioPinDigitalStateChangeEvent(GpioPinDigitalStateChangeEvent event) {
                if(event.getState().isHigh()){
                try{
                    System.out.println("taking video");            
                    Runtime rt = Runtime.getRuntime();
                    String videoName = "../video_" + getCurrentTimeStamp()+".h264";
                    Process snap = rt.exec("raspivid -n  --timeout 7000 --output "+ videoName);            
                    snap.waitFor();
                    sendVideo(videoName);
                    } catch (IOException e) {
                    e.printStackTrace();
                    } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                }
            }

        });

        try {
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

public static void sendVideo(String videoName){
    
    try{
                            File filetoread = new File(videoName);                            

                            dos.writeUTF(videoName); // on envoie le nom du fichier à écrire
                            dos.flush(); // on vide le tampon

                            dos.writeLong(filetoread.length());
                            dos.flush();
                            
                            FileInputStream fis = new FileInputStream(filetoread);                            
                            
                            byte[] temp = new byte[tailleBuffer];

                            int taille;

                            while ((taille = fis.read(temp)) != -1) {
                                dos.write(temp, 0, taille);

                                dos.flush();
                            }

                            System.out.println("Video sent to post vigil");

                            fis.close();
    } catch (IOException e) {
                System.out.println("Erreur IO video not sent");
    }catch (NullPointerException e) {
                System.out.println("Erreur IO video won't be sent");
                initConnexion();
            }
            
}

public static void initConnexion(){
        
            try{        
            //init connection
            InetSocketAddress sockaddrServ = new InetSocketAddress(IP_PC_VIGILE , PORT_PC_VIGILE );

            SocketCommu = new Socket();
            SocketCommu.connect(sockaddrServ);

             dos = new DataOutputStream(new BufferedOutputStream(SocketCommu.getOutputStream()));
               } catch (IOException e) {
                System.out.println("Erreur IO can't reach vigil client");

            }
           
}

}

// END SNIPPET: serial-snippet
