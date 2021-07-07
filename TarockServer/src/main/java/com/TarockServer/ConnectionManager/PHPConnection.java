package com.TarockServer.ConnectionManager;

import java.io.*;
import java.net.*;
import java.util.List;

import com.TarockServer.Exception.*;

import com.TarockServer.Domain.Pair;
import com.TarockServer.Domain.Session;
import com.TarockServer.Domain.User;
import com.TarockServer.Service.DataManipulationService;
import at.favre.lib.crypto.bcrypt.BCrypt;
import at.favre.lib.bytes.Bytes;

public class PHPConnection {
    public static String read(String inputString){
        try {
            byte[] post = inputString.getBytes();

            URL u = new URL("http://localhost/TarockWebsite/controllerHelper.php");
            HttpURLConnection con = (HttpURLConnection) u.openConnection();
            con.setRequestMethod("POST");
            con.setDoOutput(true);
            OutputStream out = con.getOutputStream();
            out.write(post);
            out.close();
            if (con.getResponseCode() != 200) {
                throw new Exception("Server returned bad response code: " + con.getResponseCode() + " " + con.getResponseMessage());
            }
            InputStream in = con.getInputStream();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buff = new byte[8192];
            int cur = 0;
            while ((cur = in.read(buff)) > 0) {
                baos.write(buff, 0, cur);
            }
            in.close();
            System.out.println(new String(baos.toByteArray()));
            return new String(baos.toByteArray());
        }
        catch (Exception e) { e.printStackTrace();}
        return null;
    }

    public static User getUser(String username) {
        User user = new User();
        String post = "functionName=getUser&username=" + username;
        List<Pair<String, String>> userJSON = DataManipulationService.JSONtoList(PHPConnection.read(post));
        if (userJSON.get(0).getKey().equals("exception"))
        {
            throw new PHPException(userJSON.get(0).getValue());
        }
        else {
            for(int i=0;i<userJSON.size();i++)
            {
                if (userJSON.get(i).getKey().equals("name"))
                {
                    user.setName(userJSON.get(i).getValue());
                }
                else if (userJSON.get(i).getKey().equals("username"))
                {
                    user.setUsername(userJSON.get(i).getValue());
                }
                else if (userJSON.get(i).getKey().equals("password"))
                {
                    user.setPassword(userJSON.get(i).getValue());
                }
                else if (userJSON.get(i).getKey().equals("email"))
                {
                    user.setEmail(userJSON.get(i).getValue());
                }
            }
            return user;
        }
    }

    public static Session getSession(int sessionID) {
        Session session = new Session();
        String post = "functionName=getSession&sessionID=" + sessionID;
        List<Pair<String, String>> userJSON = DataManipulationService.JSONtoList(PHPConnection.read(post));
        if (userJSON.get(0).getKey().equals("exception"))
        {
            throw new PHPException(userJSON.get(0).getValue());
        }
        else
        {
            for(int i=0;i<userJSON.size();i++)
            {
                if (userJSON.get(i).getKey().equals("sessionID"))
                {
                    session.setSessionID(Integer.parseInt(userJSON.get(i).getValue()));
                }
                else if (userJSON.get(i).getKey().equals("creator"))
                {
                    session.setCreator(userJSON.get(i).getValue());
                }
                else if (userJSON.get(i).getKey().equals("dateCreated"))
                {
                    session.setDateCreated(DataManipulationService.getName(userJSON.get(i).getValue()));
                }
                else if (userJSON.get(i).getKey().equals("dateClosed"))
                {
                    session.setDateClosed(DataManipulationService.getName(userJSON.get(i).getValue()));
                }
                else if (userJSON.get(i).getKey().equals("player1"))
                {
                    session.setPlayer1(userJSON.get(i).getValue());
                }
                else if (userJSON.get(i).getKey().equals("player2"))
                {
                    session.setPlayer2(userJSON.get(i).getValue());
                }
                else if (userJSON.get(i).getKey().equals("player3"))
                {
                    session.setPlayer3(userJSON.get(i).getValue());
                }
                else if (userJSON.get(i).getKey().equals("player4"))
                {
                    session.setPlayer4(userJSON.get(i).getValue());
                }
            }
            return session;
        }
    }

    public static boolean verifyPassword(String username, String password) {
        User user = getUser(username);
        return (BCrypt.verifyer(BCrypt.Version.VERSION_2Y).verify(Bytes.from(password).array(), Bytes.from(user.getPassword()).array()).verified);
    }
}
