package io.s1n.token;

import io.smallrye.jwt.build.Jwt;
import io.smallrye.jwt.build.JwtClaimsBuilder;
import java.security.PrivateKey;
import java.time.Instant;

class TokenGeneratorImpl implements TokenGenerator {
  private static final PrivateKey PK = PrivateKeyReader.getPrivateKey();
  @Override
  public String generateAccessToken() {
    String privateKeyLocation = "/privatekey.pem";

    JwtClaimsBuilder claimsBuilder = Jwt.claims();
    long currentTimeInSecs = Instant.now().getEpochSecond();
    claimsBuilder.issuedAt(currentTimeInSecs);
    claimsBuilder.expiresAt(currentTimeInSecs + 300);

    return claimsBuilder.jws()
        .keyId(privateKeyLocation)
        .sign(PK);
  }
}
