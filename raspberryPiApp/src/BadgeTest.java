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

public class BadgeTest {

    /**
     * This example program supports the following optional command arguments/options:
     *   "--device (device-path)"                   [DEFAULT: /dev/ttyAMA0]
     *   "--baud (baud-rate)"                       [DEFAULT: 38400]
     *   "--data-bits (5|6|7|8)"                    [DEFAULT: 8]
     *   "--parity (none|odd|even)"                 [DEFAULT: none]
     *   "--stop-bits (1|2)"                        [DEFAULT: 1]
     *   "--flow-control (none|hardware|software)"  [DEFAULT: none]
     *
     * @param args
     * @throws InterruptedException
     * @throws IOException
     */
	//ID VIDEO
	private static int IDVideo;
	private static final Integer IDZONE = 1; 
    public static void main(String args[]) throws InterruptedException, IOException {

	// create gpio controller
        final GpioController gpio = GpioFactory.getInstance();

        // provision gpio pin #03 as an output pin and turn on LED VERTE
        final GpioPinDigitalOutput greenLED = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_03,"greenLED", PinState.LOW);

        // provision gpio pin #00 as an output pin and turn on LED ROUGE
        final GpioPinDigitalOutput redLED = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_00,"redLED", PinState.LOW);

        // create Pi4J console wrapper/helper
        // (This is a utility class to abstract some of the boilerplate code)
        final Console console = new Console();

        // print program title/header
        console.title("<-- Projet annuel LPRO Raspberry Pi Portiques-->", "Test Badges");

        // allow for user to exit program using CTRL-C
        console.promptForExit();

        // create an instance of the serial communications class
        final Serial serial = SerialFactory.createInstance();

        IDVideo = 0;
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
			String ID = event.getHexByteString();
			ID = ID.substring(3,32);
			ID=ID.replaceAll(",","");
			console.println("[ID] = " + ID);
			String statement = "SELECT * FROM AutorisationAcces WHERE numBadge=\""+ID+"\" AND idZone="+IDZONE+";";
		        console.println(statement);
			List<String> values = (ArrayList<String>) DBUtil.dbExecuteQuery(statement);
			if(values.size()>0){
			        if(values.get(0).equals(ID) && values.get(1).equals(IDZONE.toString())){
			                console.box("accès autorisé");
				        Runtime rt = Runtime.getRuntime();
              				Process snap = rt.exec("raspivid --timeout 10000 --output ../video"+IDVideo+".h264");      
				        IDVideo++;
				        greenLED.toggle();
				        snap.waitFor();
				        greenLED.toggle();//led off after taking video
				        System.out.println("--> green LED state should be : OFF and security video taken");			        
			        }else{
			                redLED.toggle();
			                console.box("accès refusé");
				        System.out.println("--> red LED state should be: ON");
				        Thread.sleep(2000);
				        redLED.toggle();
			        }
			
			}else{
			                redLED.toggle();
			                console.box("accès refusé");
				        System.out.println("--> red LED state should be: ON");
				        Thread.sleep(2000);
				        redLED.toggle();
			        }
			/*if ( result.first() ){
			greenLED.toggle();//led off after taking video
			System.out.println("--> green LED state should be : ON");
			Thread.sleep(2000);
			greenLED.toggle();
			}*/
/*
			if( ID.equals("30,37,30,30,42,43,45,45,41,36")) {
				redLED.toggle();
				System.out.println("--> red LED state should be: ON");
				Thread.sleep(2000);
				redLED.toggle();
			}
			if( ID.equals("37,32,30,30,37,37,37,43,42,46")) {
				console.box("accès autorisé");
				Runtime rt = Runtime.getRuntime();
      				Process snap = rt.exec("raspivid --timeout 10000 --output ../video"+IDVideo+".h264");      
				IDVideo++;
				greenLED.toggle();
				snap.waitFor();
				greenLED.toggle();//led off after taking video
				System.out.println("--> green LED state should be : OFF and security video taken");
			}*/
		}catch(SQLException e){
		    e.printStackTrace();
                } catch(ClassNotFoundException e){
		    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }catch(InterruptedException e){
                    e.printStackTrace();		   
		}catch(StringIndexOutOfBoundsException e){
		    		
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
            if(args.length > 0){
                config = CommandArgumentParser.getSerialConfig(config, args);
            }

            // display connection details
            console.box(" Connecting to: " + config.toString(),
				  " Data received on serial port will be displayed below.");


            // open the default serial device/port with the configuration settings
            serial.open(config);
		console.box("connected");
            // continuous loop to keep the program running until the user terminates the program
            while(console.isRunning()) {
                try {
                  // write the date in console
                }
                catch(IllegalStateException ex){
                    ex.printStackTrace();
                }

            }

        }
        catch(IOException ex) {
            console.println(" ==>> SERIAL SETUP FAILED : " + ex.getMessage());
            return;
        }
    }
	
}

// END SNIPPET: serial-snippet
