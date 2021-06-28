package com.eduard240300.TarockWebSite.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class CookieService {
    public static Cookie getCookie(HttpServletRequest request, String name)
    {
        Cookie[] cookies = request.getCookies();
        Cookie cookie;
        if( cookies != null ) {
            for (int i = 0; i < cookies.length; i++) {
                cookie = cookies[i];
                if (cookie.getName().equals(name))
                    return cookie;
            }
        }
        return null;
    }
}
