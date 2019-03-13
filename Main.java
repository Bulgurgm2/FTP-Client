import javax.sound.midi.Soundbank;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {

        Scanner ind = new Scanner(System.in);

        byte []b = new byte[11111];
        Socket dataPort = null;

        Boolean password = false;
        int i = 3;
        String host, answerPassword;

        System.out.println("Enter password");
        while (!password){
            if (ind.nextLine().equals("123")){
                password =true;
            } else { i--; System.out.printf("Wrong password! You have %d before your computer shuts down\n", i); }
            if (i == 0) {
//                Runtime runtime = Runtime.getRuntime();
//                Process proc = runtime.exec("shutdown -s -t 0");
//                System.exit(0);
            }
        }
//        System.out.println("password lykkedes\nwrite host.");
//        host = ind.nextLine();

        Socket s = new Socket("ftp.cs.brown.edu",21);

        // Reads and writes to the server
        PrintWriter outData = new PrintWriter(s.getOutputStream());
        BufferedReader bf = new BufferedReader(new InputStreamReader(s.getInputStream()));





//        // Læser fra terminal
//        BufferedReader bfConcol = new BufferedReader(new InputStreamReader(System.in));







        while(true) {


            answerPassword = getAnswer(bf);

            if (answerPassword.equals(221)){
                System.out.println("Service closing control connection."); break; }
//            else if (answerPassword.equals("220")){
//                write(outData, "user"); getAnswer(bf);}
//            else if (answerPassword.equals("150")) {
//                System.out.println("File status okay; about to open data connection.");
////              få data
////              få answer
//                dataPort = null;
//            }
//            if (dataPort == null){
//                dataPort = createDataPort(s, bf, outData);
//            }
//            hvis ingen dataport
//              lav data port
//            få bruger answer
//            s.send(cmd + '\r\n')



            System.out.print(">");
            write(outData, ind.nextLine());
        }
    }





    public static String getAnswer(BufferedReader bf) throws IOException {

        String answer = bf.readLine();

        while (bf.ready()) {
            System.out.println(answer);
            answer = bf.readLine();
        }

        return answer.substring(0,3);
    }


//    public static
//
//
//
//    conn, host = r.accept()
//    print '(data connection accepted)'
//            while 1:
//    data = conn.recv(BUFSIZE)
//            if not data: break
//            sys.stdout.write(data)
//    print '(end of data connection)'
//


    public static Socket createDataPort(Socket s, BufferedReader bf, PrintWriter outData) throws IOException {

        write(outData, "PASV");
        String answer = bf.readLine();
        System.out.println(answer);
//        int result1 = Integer.parseInt(answer.substring(42, 45));
//        int result2 = Integer.parseInt(answer.substring(46, 48));
//
//        System.out.println(result1 + result2);

//        Socket dataPort = new Socket("ftp.cs.brown.edu", );
//        r.listen(1)
//        sendportcmd(s, f, port)
        return null;
    }


    public static void write(PrintWriter outData, String ind ){
        outData.println(ind + "\r\n");
        outData.flush();
    }




}
