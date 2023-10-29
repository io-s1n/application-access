package io.s1n.token;

import static java.nio.charset.StandardCharsets.UTF_8;

import java.io.InputStream;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Base64;

final class PrivateKeyReader {

  private static final PrivateKey PRIVATE_KEY = readPrivateKey();

  public static PrivateKey getPrivateKey() {
    return PRIVATE_KEY;
  }

  private PrivateKeyReader() {
  }

  private static PrivateKey readPrivateKey() {
    try (InputStream contentIS = PrivateKeyReader.class.getResourceAsStream("/privatekey.pem")) {
      byte[] tmp = new byte[4096];
      assert contentIS != null;
      return decodePrivateKey(new String(tmp, 0, contentIS.read(tmp), UTF_8));
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  private static PrivateKey decodePrivateKey(final String pemEncoded) throws Exception {
    byte[] encodedBytes = Base64.getDecoder().decode(removeBeginEnd(pemEncoded));
    PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encodedBytes);

    return KeyFactory.getInstance("RSA").generatePrivate(keySpec);
  }

  private static String removeBeginEnd(String pem) {
    return pem.replaceAll("-----BEGIN (.*)-----", "")
        .replaceAll("-----END (.*)----", "")
        .replaceAll("\r\n", "")
        .replaceAll("\n", "")
        .trim();
  }
}
