package vdsale.configurations;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.credentials.ClientCredentials;
import com.wrapper.spotify.requests.authorization.client_credentials.ClientCredentialsRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@Slf4j
@Configuration
public class SpotifyConfiguration {

    @Value("${spotify.client.id}")
    private String accessKey;

    @Value("${spotify.client.secret}")
    private String secretKey;

    @Bean
    public SpotifyApi spotifyApi(){
        SpotifyApi spotifyApi = new SpotifyApi.Builder()
                .setClientId(accessKey)
                .setClientSecret(secretKey)
                .build();
        return spotifyApi;
    }

    @Bean
    public String spotifyApiToken_Sync() throws IOException, SpotifyWebApiException {
        try {
            final ClientCredentialsRequest clientCredentialsRequest = spotifyApi().clientCredentials().build();
            final ClientCredentials clientCredentials = clientCredentialsRequest.execute();

            // Set access token for further "spotifyApi" object usage
            String token = clientCredentials.getAccessToken();
            spotifyApi().setAccessToken(token);

            log.info("M=spotifyApiToken_Sync, token={}, message=Expires in {} ", token, clientCredentials.getExpiresIn());
            return token;
        } catch (IOException | SpotifyWebApiException e) {
            log.error("M=spotifyApiToken_Sync, message=Error to get token sync from spotify ", e);
            throw e;
        }
    }

    //@Bean
    public String spotifyApiToken_Async() throws ExecutionException, InterruptedException {

        try {
            final ClientCredentialsRequest clientCredentialsRequest = spotifyApi().clientCredentials().build();
            final Future<ClientCredentials> clientCredentialsFuture = clientCredentialsRequest.executeAsync();

            final ClientCredentials clientCredentials = clientCredentialsFuture.get();

            // Set access token for further "spotifyApi" object usage
            String token = clientCredentials.getAccessToken();
            spotifyApi().setAccessToken(token);

            log.info("M=spotifyApiToken_Async, token={}, message=Expires in {} ", token, clientCredentials.getExpiresIn());
            return token;
        } catch (InterruptedException | ExecutionException e) {
            log.error("M=spotifyApiToken_Async, message=Error to get token sync from spotify ", e);
            throw e;
        }

    }

}
