package com.example.diary.domain.member.util;

import com.example.diary.domain.member.infrastructure.entity.MemberRole;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import io.micrometer.common.util.StringUtils;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Arrays;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Component
public class JwtUtil { // TODO Util class 로 만드는 것은 어떨까?
    private static final String AUTHORIZATION_HEADER_KEY = "Authorization";
    private static final String AUTHORIZATION_KEY = "Auth";
    private static final String BEARER_PREFIX = "Bearer ";
    private static final Long TOKEN_EXPIRED_TIME = 60 * 60 * 1000L;
    private static final SignatureAlgorithm SIGNATURE_ALGORITHM = SignatureAlgorithm.HS256;

    @Value("${jwt.secret_key}")
    private String secretKey;

    private Key key;

    @PostConstruct
    private void init() {
        byte[] bytes = Base64.getDecoder().decode(secretKey);
        key = Keys.hmacShaKeyFor(bytes);
    }

    public String generateToken(String email, MemberRole role) {
        Date now = new Date();
        return BEARER_PREFIX +
                Jwts.builder()
                        .setSubject(email)
                        .claim(AUTHORIZATION_KEY, role)
                        .setExpiration(new Date(now.getTime() + TOKEN_EXPIRED_TIME))
                        .setIssuedAt(now)
                        .signWith(key, SIGNATURE_ALGORITHM)
                        .compact();
    }

    public void addTokenToCookie(String token, HttpServletResponse response) {
        token = URLEncoder.encode(token, StandardCharsets.UTF_8)
                .replaceAll("\\+", "%20");
        Cookie cookie = new Cookie(AUTHORIZATION_HEADER_KEY, token);
        cookie.setPath("/");

        response.addCookie(cookie);
    }

    public String substringToken(String tokenValue) {
        int lengthOfBearerPrefix = 7;
        if (StringUtils.isBlank(tokenValue) || tokenValue.startsWith(BEARER_PREFIX)) {
            return tokenValue.substring(lengthOfBearerPrefix);
        }

        // TODO: refactoring exception
        log.error("[Token is null]");
        throw new NullPointerException("Token is null");
    }

    public Claims getMemberInfoFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String getTokenFromRequest(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        String invalidToken = "INVALID";

        if (cookies == null) {
            return invalidToken; // TODO: refactoring 단지 문자열 값을 반환하는 것이 맏을까?
        }

        Cookie findCookie = Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals(AUTHORIZATION_HEADER_KEY))
                .findFirst()
                .orElseThrow();// TODO: 어떤 예외를 처히하는게 맞을까?

        return URLDecoder.decode(findCookie.getValue(), StandardCharsets.UTF_8);
    }

    public boolean validate(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException |
                 SignatureException e) {
            log.error("[Invalid JWT signature]", e);
        } catch (ExpiredJwtException e) {
            log.error("[Expired JWT token]", e);
        } catch (UnsupportedJwtException e) {
            log.error("[Unsupported JWT token]", e);
        } catch (IllegalArgumentException e) {
            log.error("[JWT claims is empty]", e);
        }

        return false;
    }

    public void expiredToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals(AUTHORIZATION_HEADER_KEY))
                .findFirst().ifPresent(cookie -> cookie.setMaxAge(-1));
    }
}