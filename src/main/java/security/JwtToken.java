package security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

public class JwtToken implements TokenService {

    @Override
    public String generateToken(String id) {
        Algorithm algorithm = Algorithm.HMAC256("secret");
        return JWT.create()
                .withIssuer("auth0")
                .withClaim("id", id)
                .sign(algorithm);
    }
}
