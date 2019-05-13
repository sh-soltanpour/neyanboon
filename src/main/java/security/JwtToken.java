package security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import utils.DateUtils;

import java.util.Date;

public class JwtToken implements TokenService {
    private Algorithm algorithm = Algorithm.HMAC256("69D7a43t#V#KTkTEL$XMFRfGT#2bz+P*");
    @Override
    public String generateToken(String id) {

        return JWT.create()
                .withIssuer("neyanboon")
                .withExpiresAt(DateUtils.addHours(new Date(), 1))
                .withClaim("id", id)
                .sign(algorithm);
    }

    @Override
    public String parseToken(String token) {
        //TODO: check token expiration
        DecodedJWT jwt = JWT.decode(token);
        return jwt.getClaim("id").asString();
    }
}
