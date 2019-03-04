import javax.sound.midi.Soundbank;
import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {

        Scanner ind = new Scanner(System.in);

        byte []b = new byte[11111];
        Socket dataPort = null;

        Boolean kode = false;
        int i = 3;
        String host, svarKode;

        System.out.println("Skriv kode");
        while (!kode){
            if (ind.nextLine().equals("123")){
                kode =true;
            } else { i--; System.out.printf("Forkert kode! Du har %d før din computer lukker ned\n", i); }
            if (i == 0) {
//                Runtime runtime = Runtime.getRuntime();
//                Process proc = runtime.exec("shutdown -s -t 0");
//                System.exit(0);
            }
        }
//        System.out.println("Kode lykkedes\nSkriv host.");
//        host = ind.nextLine();

        Socket s = new Socket("ftp.cs.brown.edu",21);

        // Læser og skriver til serveren
        PrintWriter outData = new PrintWriter(s.getOutputStream());
        BufferedReader bf = new BufferedReader(new InputStreamReader(s.getInputStream()));





//        // Læser fra terminal
//        BufferedReader bfConcol = new BufferedReader(new InputStreamReader(System.in));







        while(true) {


            svarKode = fåSvar(bf);

            if (svarKode.equals(221)){
                System.out.println("Service closing control connection."); break; }
//            else if (svarKode.equals("220")){
//                skriv(outData, "user"); fåSvar(bf);}
//            else if (svarKode.equals("150")) {
//                System.out.println("File status okay; about to open data connection.");
////              få data
////              få svar
//                dataPort = null;
//            }
//            if (dataPort == null){
//                dataPort = lavDataPort(s, bf, outData);
//            }
//            hvis ingen dataport
//              lav data port
//            få bruger svar
//            s.send(cmd + '\r\n')



            System.out.print(">");
            skriv(outData, ind.nextLine());
        }
    }





    public static String fåSvar(BufferedReader bf) throws IOException {

        String svar = bf.readLine();

        while (bf.ready()) {
            System.out.println(svar);
            svar = bf.readLine();
        }

        return svar.substring(0,3);
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


    public static Socket lavDataPort(Socket s, BufferedReader bf, PrintWriter outData) throws IOException {

        skriv(outData, "PASV");
        String svar = bf.readLine();
        System.out.println(svar);
//        int result1 = Integer.parseInt(svar.substring(42, 45));
//        int result2 = Integer.parseInt(svar.substring(46, 48));
//
//        System.out.println(result1 + result2);

//        Socket dataPort = new Socket("ftp.cs.brown.edu", );
//        r.listen(1)
//        sendportcmd(s, f, port)
        return null;
    }


    public static void skriv(PrintWriter outData, String ind ){
        outData.println(ind + "\r\n");
        outData.flush();
    }




}
