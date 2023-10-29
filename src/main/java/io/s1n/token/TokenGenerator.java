package io.s1n.token;

@FunctionalInterface
public interface TokenGenerator {
  String generateAccessToken();

  static TokenGenerator DEFAULT_GENERATOR(){
    return new TokenGeneratorImpl();
  }
}
