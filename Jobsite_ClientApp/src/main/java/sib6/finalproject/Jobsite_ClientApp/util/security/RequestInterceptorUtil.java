package sib6.finalproject.Jobsite_ClientApp.util.security;

import java.io.IOException;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.core.Authentication;

public class RequestInterceptorUtil implements ClientHttpRequestInterceptor {

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
            throws IOException {

        // get principle
        Authentication auth = AuthSessionUtil.getAuthentication();

        // create principle on header from path other login
        if (!request.getURI().getPath().equals("/api/auth/login")) {
            String token = BasicHeaderUtil.createBasicToken(
                    auth.getName(),
                    auth.getCredentials().toString());

            request
                    .getHeaders()
                    .add(
                            "Authorization",
                            "Basic " + token);
        }

        ClientHttpResponse response = execution.execute(request, body);
        return response;
    }

}
