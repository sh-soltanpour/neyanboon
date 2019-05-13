package security;

public interface TokenService {
    String generateToken(String id);
    String parseToken(String token);
}
