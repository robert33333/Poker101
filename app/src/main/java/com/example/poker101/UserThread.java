package com.example.poker101;

import android.widget.Toast;

import com.example.poker101.date.Comanda;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.List;

import static com.example.poker101.User.goToWaitScreen;
import static com.example.poker101.User.ois;


class UserThread extends Thread {

//    public static boolean messageReady = false;
//    private static Object message = null;

//    public UserThread() {}
//
//    public void run() {
//
//        while (true) {
//            try {
//
//
//                Object object = ois.readObject();
//
//                if (object instanceof Comanda) {
//                    Comanda comanda = (Comanda) object;
//                    switch (comanda.getOptiune()) {
//                        case "inviteFriend":
//                            User.currentOpponent = (String) comanda.getObj();
//                            goToWaitScreen();
//                            //Toast.makeText(User.context,"test",Toast.LENGTH_LONG).show();
//                            break;
//                        case "declineInvite":
//                            User.currentOpponent = null;
//                            User.goToMenu();
//                            break;
////                        case "getFriends":
////                            UserThread.message = comanda.getObj();
////                            messageReady = true;
////                            break;
////                        case "getFriendsOnline":
////                            UserThread.message = comanda.getObj();
////                            messageReady = true;
////                            break;
//                    }
//                }
//                else {
//                    UserThread.message = object;
//                    messageReady = true;
//                }
//
//
//            }
//            catch (EOFException e) {
//                return;
//            }
//            catch (IOException | ClassNotFoundException  e ) {
//                e.printStackTrace();
//                return;
//            }
//
//        }
//
//
//    }

//    public static void resetMessage() {
//        message = null;
//        messageReady = false;
//    }

    public static Object readMessage() {
//        Object result = message;
//        resetMessage();
//        return result;
        try {
            return ((Comanda)ois.readObject()).getObj();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

//    public static void waitMessage() {
//        while (!messageReady);
//        System.out.println("Exit!");
//    }
}