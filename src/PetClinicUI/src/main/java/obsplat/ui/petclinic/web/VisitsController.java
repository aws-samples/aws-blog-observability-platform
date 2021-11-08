// Original Copyright 2002-2017 the original author or authors. Licensed under the Apache 2.0 License.
// Modifications Copyright Amazon.com, Inc. or its affiliates. All Rights Reserved. 

package obsplat.ui.petclinic.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Enumeration;

@RestController
@RequiredArgsConstructor
@Slf4j
public class VisitsController {

    @Value("${visits-service-id://visits-service}")
    private String hostname;

    private final RestTemplate restTemplate;

    @RequestMapping("/api/visit/**")
    @ResponseBody
    public ResponseEntity forward(@RequestBody(required = false) String body,
                                     HttpMethod method, HttpServletRequest request, HttpServletResponse response)
            throws URISyntaxException {
        String requestUrl = request.getRequestURI().replace("/petclinic-ui/api/visit", "/visits-service");
        log.info("Forward request with path " + request.getRequestURI() + " to visits service with path " + requestUrl);
        return getResponseEntity(body, method, request, requestUrl, hostname);
    }

    static ResponseEntity getResponseEntity(@RequestBody(required = false) String body, HttpMethod method, HttpServletRequest request, String requestUrl, String hostname) throws URISyntaxException {
        URI uri = new URI("http", null, new URI(hostname).getHost(), 80, null, null, null);

        uri = UriComponentsBuilder.fromUri(uri)
                .path(requestUrl)
                .query(request.getQueryString())
                .build(true).toUri();

        HttpHeaders headers = new HttpHeaders();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            if(
                    headerName.equalsIgnoreCase("Content-Type") ||
                            headerName.equalsIgnoreCase("Content-Length")
            ) {
                headers.set(headerName, request.getHeader(headerName));
            }
        }

        HttpEntity<String> httpEntity = new HttpEntity<>(body, headers);
        RestTemplate restTemplate = new RestTemplate();
        try {
            return restTemplate.exchange(uri, method, httpEntity, String.class);
        } catch(HttpStatusCodeException e) {
            return ResponseEntity.status(e.getRawStatusCode())
                    .headers(e.getResponseHeaders())
                    .body(e.getResponseBodyAsString());
        }
    }
}
