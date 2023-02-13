package batch3;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Console;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

//THIS IS THE CLIENT
public final class App {
    private App() {
    }

    public static void main(String[] args) throws UnknownHostException, IOException {
        
        //variable to store keyboard input and socket return value
        String keyInput = "";
        String msgRecv = "";

        //using console to receive input from keyboard
        Console console = System.console();


        //opening a socket to connect to the server on port xxxx
        Socket socket = new Socket("localhost", 1234);
        
        try(OutputStream os = socket.getOutputStream()){
            BufferedOutputStream bos = new BufferedOutputStream(os);
            DataOutputStream dos = new DataOutputStream(bos);

            //receive data from inputstream
            try(InputStream is = socket.getInputStream()){
                BufferedInputStream bis = new BufferedInputStream(is);
                DataInputStream dis = new DataInputStream(bis);

                while(!keyInput.equalsIgnoreCase("quit")){
                    keyInput = console.readLine("Enter guess XX, XX refers to number to guess: ");
                    //send input over
                    dos.writeUTF(keyInput);
                    dos.flush(); //must flush to make sure it is sent over

                    msgRecv = dis.readUTF();
                    System.out.println("From server: " + msgRecv);
                }

                bos.close();
                dos.close();
                socket.close();

            } catch (EOFException ex){
                ex.printStackTrace();
                socket.close();
            }        
        } catch (EOFException ex){
            ex.printStackTrace();
            socket.close();
        }

    }
}
