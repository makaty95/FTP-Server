package Run;

import Client.Client;

import java.io.IOException;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Scanner;


public class ClientR {

    public static boolean debug = true;

    public static void main(String[] args) throws IOException {


        // check 
        if(args[0].equals("true")) debug = true;
        else if(args[0].equals("false")) debug = false;
        else {System.out.println("Invalid debug argument!"); return;}


        if(debug) {
            Client client = new Client(

                // Server address info
                new InetSocketAddress("127.12.12.12", 2121).getAddress(),
                2121,

                // Client address info
                new InetSocketAddress("127.23.12.9", 7878).getAddress(),
                7878
            );

            try{
                client.connect();
            } catch(ConnectException e) {
                System.err.println("Connection refused - check the server state");
            } catch (Exception e) {
                System.err.println("Unhandled  Exception in ClientR.java - 2839");
                
            }
            
        } else {

            Scanner scanner = new Scanner(System.in);
            String in;
            Client client = null;
            String message =
                    "[1] Enter custom IP and Port\n[2] Continue with my machine private network";
            do {
                System.out.println(message);
                System.out.print(">>");
                in = scanner.nextLine();
            }while(in.isEmpty() || in.isBlank() || (!in.equals("1") && !in.equals("2")));

            String SIP, SPort, IP, Port;

            if(in.equals("1")) {
                boolean retry;
                do {
                    retry = false;
                    // get user IP
                    do{
                        System.out.print("Enter your IP: ");
                        IP = scanner.nextLine();
                    }while(IP.isEmpty() || IP.isBlank());

                    // get user Port
                    do{
                        System.out.print("Enter your Port: ");
                        Port = scanner.nextLine();
                    }while(Port.isEmpty() || Port.isBlank());

                    // get server IP
                    do{
                        System.out.print("Enter Server IP: ");
                        SIP = scanner.nextLine();
                    }while(SIP.isEmpty() || SIP.isBlank());

                    // get server Port
                    do{
                        System.out.print("Enter Server Port: ");
                        SPort = scanner.nextLine();
                    }while(SPort.isEmpty() || SPort.isBlank());

                    // initialize
                    try{
                        client = new Client(

                                // Server address info
                                new InetSocketAddress(SIP, Integer.parseInt(SPort)).getAddress(),
                                Integer.parseInt(SPort),

                                // Client address info
                                new InetSocketAddress(IP, Integer.parseInt(Port)).getAddress(),
                                Integer.parseInt(Port)                                                               // client port
                        );
                    }catch(NumberFormatException e) {
                        System.out.println("The port is incorrect");
                        retry = true;
                    } catch(IllegalArgumentException | SecurityException e) {
                        System.out.println("The given IP and port are illegal");
                        retry = true;
                    }
                }while(retry);

            } else {
                // the server will have the same address as the machine, if the machine have private network
                // address = 192.168.1.12 the server will have that.

                // get server IP
                do{
                    System.out.print("Enter Server IP: ");
                    SIP = scanner.nextLine();
                }while(SIP.isEmpty() || SIP.isBlank());

                // get server Port
                do{
                    System.out.print("Enter Server Port: ");
                    SPort = scanner.nextLine();
                }while(SPort.isEmpty() || SPort.isBlank());

                client = new Client(

                        // Server address info
                        new InetSocketAddress(SIP, Integer.parseInt(SPort)).getAddress(),
                        Integer.parseInt(SPort),

                        // Client address info
                        InetAddress.getLocalHost(),
                        7878
                );
            }

            client.connect();
            scanner.close();
        }



    }
}
