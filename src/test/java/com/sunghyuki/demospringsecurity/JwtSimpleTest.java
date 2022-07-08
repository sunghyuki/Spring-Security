package com.sunghyuki.demospringsecurity;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.Test;

import java.util.Base64;
import java.util.Map;

public class JwtSimpleTest {

    @Test
    public void testForjjwt() {
        String token = Jwts.builder().addClaims(
                Map.of("name", "sunghyuk", "price", 1000)
                ).signWith(SignatureAlgorithm.HS256, "sunghyuk")
                .compact();

        System.out.println(token);
        printToken(token);

        Jws<Claims> jwtInfo = Jwts.parser().setSigningKey("sunghyuk").parseClaimsJws(token);
        System.out.println(jwtInfo);
    }

    @Test
    public void testForjava_jwt() {
        String token = JWT.create().withClaim("name", "sunghyuk").withClaim("price", 1000)
                         .sign(Algorithm.HMAC256("sunghyuk"));
        System.out.println(token);
        printToken(token);
        DecodedJWT verified = JWT.require(Algorithm.HMAC256("sunghyuk")).build().verify(token);
        System.out.println(verified.getClaims());


    }

    private void printToken(String token) {
        String[] tokens = token.split("\\.");
        System.out.println("header : " + new String(Base64.getDecoder().decode(tokens[0])));
        System.out.println("payload : " + new String(Base64.getDecoder().decode(tokens[1])));
    }
}
