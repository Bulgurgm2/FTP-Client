import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {

        Scanner ind = new Scanner(System.in);

        byte[] b = new byte[11111];
        Socket dataPort = null;

        Boolean kode = false, ingenSvar = false, udensvar = false;
        int i = 3;
        String host, svarKode = null, comando;

        System.out.println("Skriv kode");
        while (!kode) {
            if (ind.nextLine().equals("123")) {
                kode = true;
            } else {
                i--;
                System.out.printf("Forkert kode! Du har %d før din computer lukker ned\n", i);
            }
            if (i == 0) {
//                Runtime runtime = Runtime.getRuntime();
//                Process proc = runtime.exec("shutdown -s -t 0");
//                System.exit(0);
            }
        }
//        System.out.println("Kode lykkedes\nSkriv host.");
//        host = ind.nextLine();

        Socket s = new Socket("ftp.cs.brown.edu", 21);

        // Læser og skriver til serveren
        DataOutputStream outData = new DataOutputStream(s.getOutputStream());
        BufferedReader bf = new BufferedReader(new InputStreamReader(s.getInputStream()));


//        // Læser fra terminal
//        BufferedReader bfConcol = new BufferedReader(new InputStreamReader(System.in));



        fåSvar(bf);
        skriv(outData, "user");
        System.out.println("\nDette er en FTP Client.\nDu kan benytte den med de normale FTP commands\nhttps://en.wikipedia.org/wiki/List_of_FTP_commands?fbclid=IwAR2kd7_gIs4UfIqvxdWLrkFilYyvCNpG7EOrChTpHI3qQIU9TnZ4QWmK2j8\n\nClienten kommer med få prædifinerede comandoer\nSkriv \"hjælp\" for at få dem frem\n");

        while (true) {

            if (ingenSvar){
                ingenSvar = false;
            } else if (udensvar) {
                svarKode = fåSvarUdenSvar(bf);
                udensvar = false;
            }else { svarKode = fåSvar(bf); }

            if (svarKode.equals("221")) {
                System.out.println("Lukker forbindelse.");
                break;
            }
//            else if(svarKode.equals("150")){
//                seData(dataPort);
//                dataPort = null;
//            }

            if (dataPort == null) {
                dataPort = lavDataSocket(s, bf, outData);
            }


            System.out.print(">");
            comando = ind.nextLine();


            if (comando.equals("1")) {
                System.out.println("Skriv fil navn");
                comando = ind.nextLine();
                skriv(outData,("retr " + comando));

                svarKode = fåSvar(bf);
                if (svarKode.equals("150")) {
                    fåData(dataPort, comando);
                }

                ingenSvar = true;
                dataPort = null;

            } else if (comando.equals("hjælp")){
                System.out.println("1       Download fil\n" +
                                    "2       Se mappen du er i\n" +
                                    "3       gå til en anden mappe\n" +
                                    "quit    Hvad tror du?");
                ingenSvar = true;
            } else if (comando.equals("2")){
                skriv(outData, "list");
                seData(dataPort);
                dataPort = null;
                ingenSvar = true;

            } else if (comando.equals("3")){
                System.out.print("skriv mappenavn\n>");
                skriv(outData, "cwd /" + ind.nextLine());
            } else { skriv(outData, comando); }
        }
    }




    public static String fåSvar(BufferedReader bf) throws IOException {

        String svar = null;

        do {
            svar = bf.readLine();


            if (svar.equals("230 Anonymous user logged in")) {
                System.out.println("Du er logget ind som Anonym bruger");
            } else { System.out.println(svar); }
        } while (bf.ready());


        return svar.substring(0,3);
    }

    public static String fåSvarUdenSvar(BufferedReader bf) throws IOException {
        String svar = null;
        do {
            svar = bf.readLine();
        } while (bf.ready());

        return svar.substring(0,3);
    }


    public static void fåData(Socket dataPort, String stig) throws IOException{

        InputStreamReader dataReader = null;
        FileOutputStream fos = null;
        try {
            dataReader = new InputStreamReader(dataPort.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        File fil = new File(stig);


        try {
            fos = new FileOutputStream(fil, true);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        do {
            int skrivInd = dataReader.read();
            fos.write(skrivInd);
        } while (dataReader.ready());
        fos.close();
    }

    public static void seData(Socket dataPort){

        try {
            BufferedReader dataReader = new BufferedReader(new InputStreamReader(dataPort.getInputStream()));
            fåSvar(dataReader);
        } catch (IOException e) {
            e.printStackTrace();
        }





    }




    public static Socket lavDataSocket(Socket s, BufferedReader bf, DataOutputStream outData) throws IOException {

        skriv(outData, "PASV");


        String svar = bf.readLine();
        System.out.println("dasds" + svar);
        while(!svar.substring(0,3).equals("227")){
            svar = bf.readLine();
        }
        //bf.readLine();

        String[] svararray = svar.substring(27,svar.length()-1).split(",");

        int result1 = Integer.parseInt(svararray[svararray.length-2]);
        int result2 = Integer.parseInt(svararray[svararray.length-1]);
        int port = result1 * 256 + result2;

        Socket dataSocket = new Socket("ftp.cs.brown.edu", port);
        return dataSocket;
    }


    public static void skriv(DataOutputStream outData, String ind ){
        try {
            outData.writeBytes(ind + "\r\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
