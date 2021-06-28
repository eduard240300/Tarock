package com.eduard240300.TarockWebSite.Controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * Created by forest on 5/17/2018.
 */

public class AssetsController extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        /*if ((action != null) && action.equals("update")) {
            // We update an asset
            Book book = new Book(Integer.parseInt(request.getParameter("id")),
                    Integer.parseInt(request.getParameter("userid")),
                    request.getParameter("description"),
                    Integer.parseInt(request.getParameter("value")));
            DBManager dbmanager = new DBManager();
            Boolean result = dbmanager.updateAsset(book);
            PrintWriter out = new PrintWriter(response.getOutputStream());
            if (result == true) {
                out.println("Update asset succesfully.");
            } else {
                out.println("Error updating asset!");
            }
            out.flush();
        } else if ((action != null) && action.equals("getAll")) {
            int userid = Integer.parseInt(request.getParameter("userid"));

            response.setContentType("application/json");
            DBManager dbmanager = new DBManager();
            ArrayList<Book> books = dbmanager.getUserAssets(userid);
            JSONArray jsonAssets = new JSONArray();
            for (int i = 0; i < books.size(); i++) {
                JSONObject jObj = new JSONObject();
                jObj.put("id", books.get(i).getId());
                jObj.put("userid", books.get(i).getUserid());
                jObj.put("description", books.get(i).getDescription());
                jObj.put("value", books.get(i).getValue());
                jsonAssets.add(jObj);
            }
            PrintWriter out = new PrintWriter(response.getOutputStream());
            out.println(jsonAssets.toJSONString());
            out.flush();
        }*/
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
