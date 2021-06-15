/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package creditcoin;

import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.Vector;

public class MultiReceiver extends Thread {

    public String node, sys, port, xVal, yVal, signal;
    Observer o = new Observer();
    UserController carc;
    RsuController rcc;
    AttackerController atc;
    AuthorityController acc;
    public static HashMap allcarsysnum = new HashMap();
    public static HashMap allcarportnum = new HashMap();
    public static HashMap allcarsignal = new HashMap();
    public static HashMap allrsusysnum = new HashMap();
    public static HashMap allrsuportnum = new HashMap();
    public static HashMap allHashValue = new HashMap();
//    public static HashMap allauthoritysysnum = new HashMap();
//    public static HashMap allauthorityportnum = new HashMap();

    public MultiReceiver(String nodename, String portnumber, String sysnumber, String signa, UserController uc) {
        this.node = nodename;
        this.port = portnumber;
        this.sys = sysnumber;
        this.signal = signa;

        o = uc.getObserver();
        carc = uc;
        start();
    }

    public MultiReceiver(String nodename, String portnumber, String sysnumber, RsuController rc) {
        this.node = nodename;
        this.port = portnumber;
        this.sys = sysnumber;
        this.rcc = rc;

        o = rc.getObserver();
        rcc = rc;
        start();
    }

    public MultiReceiver(String nodename, String portnumber, String sysnumber, AttackerController at) {
        this.node = nodename;
        this.port = portnumber;
        this.sys = sysnumber;

        o = at.getObserver();
        atc = at;
        start();
    }

    public MultiReceiver(String name, String portnumber, String sysnumber, AuthorityController ac) {

        this.node = name;
        this.port = portnumber;
        this.sys = sysnumber;
        this.acc = ac;

        o = ac.getObserver();
        acc = ac;
        start();
    }

    @Override
    public void run() {
        try {
            InetAddress in = InetAddress.getByName("225.89.67.48");
            MulticastSocket ms = new MulticastSocket(4567);
            ms.joinGroup(in);

            while (true) {
                byte[] b = new byte[1024];
                DatagramPacket dp = new DatagramPacket(b, b.length);
                ms.receive(dp);
                String data1 = new String(dp.getData()).trim();
                StringTokenizer str1 = new StringTokenizer(data1, "$");
                String str2 = str1.nextToken().toString();

                if (str2.equals("CARINFO")) {
                    String nodenum = str1.nextToken();
                    String sysnum = str1.nextToken();
                    String portnum = str1.nextToken();
                    String xVal = str1.nextToken();
                    String yVal = str1.nextToken();
                    String signal = str1.nextToken();

                    allcarsignal.put(nodenum, signal);
                    allcarsysnum.put(nodenum, sysnum);
                    allcarportnum.put(nodenum, portnum);
                } else if (str2.equals("RSUINFO")) {
                    String rsuname = str1.nextToken();
                    String portnum = str1.nextToken();
                    String sysnum = str1.nextToken();

                    allrsusysnum.put(rsuname, sysnum);
                    allrsuportnum.put(rsuname, portnum);

                } else if (str2.equals("ATTACKINFO")) {
                    String nodenum = str1.nextToken();
                    String sysnum = str1.nextToken();
                    String portnum = str1.nextToken();

                    allcarsysnum.put(nodenum, sysnum);
                    allcarportnum.put(nodenum, portnum);

                } else if (str2.equals("AUTHORITYINFO")) {
                    String name = str1.nextToken();
                    String portnum = str1.nextToken();
                    String sysnum = str1.nextToken();

                    allrsusysnum.put(name, sysnum);
                    allrsuportnum.put(name, portnum);

                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void applyBreak(String partialBreakNode, int xPos, int yPos, int speed, String data) {
        try {
            String sysno = allcarsysnum.get(partialBreakNode).toString().trim();
            String portno = allcarportnum.get(partialBreakNode).toString().trim();

            Socket s = new Socket(sysno, Integer.parseInt(portno));
            ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
            oos.writeObject("Break");
            oos.writeObject(partialBreakNode);
            oos.writeObject(xPos);
            oos.writeObject(yPos);
            oos.writeObject(speed);
            oos.writeObject(data);
            oos.close();
            s.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void criticalNeigh(String neighName, String nodeName, int speed) {
        try {
            String sysno = allcarsysnum.get(neighName).toString().trim();
            String portno = allcarportnum.get(neighName).toString().trim();

            Socket s = new Socket(sysno, Integer.parseInt(portno));
            ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
            oos.writeObject("Critical");
            oos.writeObject(neighName);
            oos.writeObject(nodeName);
            oos.writeObject(speed);
            oos.close();
            s.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void applyBreak(String partialBreakNode, int xPos, int yPos, int speed, Vector<String> neighbour, String data, String encvalue) {
        for (String neighNode : neighbour) {
            try {
                String sysno = allcarsysnum.get(neighNode).toString().trim();
                String portno = allcarportnum.get(neighNode).toString().trim();

                Socket s = new Socket(sysno, Integer.parseInt(portno));
                ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
                oos.writeObject("Break");
                oos.writeObject(partialBreakNode);
                oos.writeObject(xPos);
                oos.writeObject(yPos);
                oos.writeObject(speed);
                oos.writeObject(data);
                oos.writeObject(encvalue);
                oos.close();
                s.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public void applyBreak1(String partialBreakNode, int xPos, int yPos, int speed, Vector Back_neighbour, String receiveInfo, String encvalue) {

        try {
            String sysno = allcarsysnum.get(partialBreakNode).toString().trim();
            String portno = allcarportnum.get(partialBreakNode).toString().trim();
            Socket s = new Socket(sysno, Integer.parseInt(portno));
            ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
            oos.writeObject("Break1");
            oos.writeObject(partialBreakNode);
            oos.writeObject(xPos);
            oos.writeObject(yPos);
            oos.writeObject(speed);
            oos.writeObject(receiveInfo);
            oos.writeObject(encvalue);

            oos.close();
            s.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
