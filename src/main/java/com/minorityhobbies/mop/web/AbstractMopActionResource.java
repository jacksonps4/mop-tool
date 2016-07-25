package com.minorityhobbies.mop.web;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.BadRequestException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;

abstract class AbstractMopActionResource {
    private static final String USERNAME_COOKIE = "username";
    private static final String MOP_ID_COOKIE = "mopId";

    @Context
    HttpServletRequest request;

    @Context
    HttpServletResponse response;

    @Context
    UriInfo uriInfo;

    Long getMopId() {
        Long mopId = (Long) request.getSession(true).getAttribute("mopId");
        if (mopId != null) {
            return mopId;
        }

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (MOP_ID_COOKIE.equals(cookie.getName())) {
                    return Long.valueOf(cookie.getValue());
                }
            }
        }

        throw new BadRequestException("No mop id: either create or join a MOP");
    }

    void setMopId(long mopId) {
        Cookie mopIdCookie = new Cookie(MOP_ID_COOKIE, String.valueOf(mopId));
        mopIdCookie.setPath("/mop/api");
        mopIdCookie.setMaxAge(60 * 60 * 12);
        response.addCookie(mopIdCookie);

        request.getSession().setAttribute("mopId", mopId);
    }

    String getUsername() {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (USERNAME_COOKIE.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

    void setUsername(String username) {
        Cookie usernameCookie = new Cookie(USERNAME_COOKIE, username);
        usernameCookie.setMaxAge(60 * 60 * 24 * 365);
        usernameCookie.setPath("/mop/api");
        response.addCookie(usernameCookie);
    }
}
