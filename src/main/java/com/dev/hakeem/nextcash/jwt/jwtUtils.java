package com.dev.hakeem.nextcash.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class jwtUtils {

    public static  final String JWT_BEARER = "Bearer";

    public static final String JWT_AUTHORIZATION = "Authorization";

    public static final  String SECRET_KEY = "123456789-23456789-123456789";

    public static final long EXPIRE_DAY = 0;

    public static final long EXPIRE_HOURES = 0;

    public static final  long EXPIRE_MINUTES = 2;
    private static final Logger log = LoggerFactory.getLogger(jwtUtils.class);

    private  jwtUtils (){

    }
    private static Key genaratekey(){
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

     private static Date toExpireDate(Date start){
         LocalDateTime localDate = start.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
         LocalDateTime end = localDate.plusDays(EXPIRE_DAY).minusHours(EXPIRE_HOURES).plusMinutes(EXPIRE_MINUTES);
         return  Date.from(end.atZone(ZoneId.systemDefault()).toInstant());
     }

     public  static  jwtToken  createToken(String email, String role ){
        Date issueAt  = new Date();
        Date limit = toExpireDate(issueAt);

         String token = Jwts.builder()
                 .setHeaderParam("typ","JWT")
                 .setSubject(email)
                 .setIssuedAt(issueAt)
                 .setExpiration(limit)
                 .signWith(genaratekey(), SignatureAlgorithm.ES256)
                 .claim("role",role)
                 .compact();

         return new jwtToken(token);



     }

     private  static Claims getClaimsFromToken(String token){
        try{
            return Jwts.parser()
                    .setSigningKey(genaratekey()).build()
                    .parseClaimsJws(refactorToken(token)).getBody();
        }catch (JwtException e){
            log.error(String.format("Token invalido %s",e.getMessage()) );
        }

        return null;

     }

    public static String getUEmailFromToken(String token){
        return getClaimsFromToken(token).getSubject();
    }

     public static boolean istokenvalid(String token){
         try{
              Jwts.parser()
                     .setSigningKey(genaratekey()).build()
                     .parseClaimsJws(refactorToken(token));
             return true;
         }catch (JwtException e){
             log.error(String.format("Token invalido %s",e.getMessage()) );
         }

         return false;
     }

     private  static  String refactorToken(String token){
        if (token.contains(JWT_BEARER)){
            return token.substring(JWT_BEARER.length());
        }

        return token;
     }






}


