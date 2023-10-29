package io.s1n;

import static io.s1n.token.TokenGenerator.*;

import io.s1n.token.TokenGenerator;
import io.smallrye.mutiny.Uni;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.concurrent.Executors;
import org.jboss.resteasy.reactive.NoCache;

@ApplicationScoped
@Path("/token")
public class TokenResource {

  private static final TokenGenerator GENERATOR = DEFAULT_GENERATOR();

  @GET
  @NoCache
  @Produces(MediaType.TEXT_PLAIN)
  public Uni<Response> generateToken() {
    return Uni.createFrom().item(GENERATOR.generateAccessToken())
        .map(token -> Response.ok()
            .entity(token)
            .build()
        )
        .runSubscriptionOn(Executors.newVirtualThreadPerTaskExecutor());
  }
}
