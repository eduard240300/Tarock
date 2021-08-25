package com.Tarock.Server.ConnectionManager;

import com.Tarock.Common.Domain.Game;
import com.Tarock.Common.Domain.PHPResponse;
import com.Tarock.Common.Domain.Session;
import com.Tarock.Common.Domain.User;
import com.Tarock.Common.Exception.PHPException;
import com.Tarock.Common.Service.DataManipulationService;
import com.Tarock.Server.GUI.StatusForm;
import com.google.gson.Gson;
import lombok.SneakyThrows;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PHPConnection {
    private static String ipAddress;
    private static final Gson gson = new Gson();

    public static void initPHPConnection(){
        InputStream inputStream = DataManipulationService.getInputStream("websiteIP.conf");
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        try {
            ipAddress = reader.readLine();
        } catch (Exception ignored) {
        }
    }

    @SneakyThrows
    public static String read(String requestBodyJSON){
        CloseableHttpClient client = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost("http://" + ipAddress + "/tarock/controllerHelper.php");

        StringEntity entity = new StringEntity(requestBodyJSON);
        httpPost.setEntity(entity);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");

        CloseableHttpResponse response = client.execute(httpPost);
        String result = EntityUtils.toString(response.getEntity());
        result = result.replace('*', ',');
        result = DataManipulationService.removeCharFromString('\\', result);
        return result;
    }

    public static User getUser(String username) {
        User user;
        Map<String, String> inputMap = new HashMap<>();
        inputMap.put("functionName", "getUser");
        inputMap.put("username", username);
        String jsonStr = gson.toJson(inputMap);
        String responseJSON = read(jsonStr);
        PHPResponse response = gson.fromJson(responseJSON, PHPResponse.class);
        if (response.getError() != null){
            throw new PHPException(response.getError());
        }
        else if (response.getException() != null) {
            throw new PHPException(response.getException());
        }
        else{
            user = gson.fromJson(responseJSON, User.class);
        }
        StatusForm.addToStatusTextArea("Received from server: " + user.toString());
        return user;
    }

    public static Session getSession(int sessionID) {
        Session session;
        Map<String, String> inputMap = new HashMap<>();
        inputMap.put("functionName", "getSession");
        inputMap.put("sessionID", String.valueOf(sessionID));
        String jsonStr = gson.toJson(inputMap);

        String responseJSON = read(jsonStr);
        PHPResponse response = gson.fromJson(responseJSON, PHPResponse.class);
        if (response.getError() != null){
            throw new PHPException(response.getError());
        }
        else if (response.getException() != null) {
            throw new PHPException(response.getException());
        }
        else{
            session = gson.fromJson(responseJSON, Session.class);
        }
        StatusForm.addToStatusTextArea("Received from server: " + session.toString());
        return session;
    }

    public static void addGame(String username, Game game) {
        Map<String, String> inputMap = game.getMap();
        inputMap.put("functionName", "addGame");
        inputMap.put("username", username);
        String jsonStr = gson.toJson(inputMap);

        String receivedJSON = read(jsonStr);
        PHPResponse response = gson.fromJson(receivedJSON, PHPResponse.class);
        if (response.getError() != null){
            throw new PHPException(response.getError());
        }
        else if (response.getException() != null) {
            throw new PHPException(response.getException());
        }
        StatusForm.addToStatusTextArea("Added game successfully");
    }

    public static List<Game> getGames(String username, int sessionID) {
        List<Game> games = new ArrayList<>();
        Map<String, String> inputMap = new HashMap<>();
        inputMap.put("functionName", "getGamesSize");
        inputMap.put("username", username);
        inputMap.put("sessionID", String.valueOf(sessionID));
        String jsonStr = gson.toJson(inputMap);

        String responseJSON = read(jsonStr);
        PHPResponse response = gson.fromJson(responseJSON, PHPResponse.class);
        if (response.getError() != null){
            throw new PHPException(response.getError());
        }
        else if (response.getException() != null) {
            throw new PHPException(response.getException());
        }
        else{
            int gamesSize = Integer.parseInt(response.getResult());
            for(int i=0;i<gamesSize;i++){
                Game game;
                inputMap = new HashMap<>();
                inputMap.put("functionName", "getGame");
                inputMap.put("gameRow", String.valueOf(i));
                inputMap.put("sessionID", String.valueOf(sessionID));
                jsonStr = gson.toJson(inputMap);

                responseJSON = read(jsonStr);
                response = gson.fromJson(responseJSON, PHPResponse.class);
                if (response.getError() != null){
                    throw new PHPException(response.getError());
                }
                else if (response.getException() != null) {
                    throw new PHPException(response.getException());
                }
                else{
                    game = gson.fromJson(responseJSON, Game.class);
                }
                games.add(game);
            }
        }
        StatusForm.addToStatusTextArea("Received from server: ");
        for (Game game : games) {
            StatusForm.addToStatusTextArea(game.toString());
        }
        return games;
    }
}
