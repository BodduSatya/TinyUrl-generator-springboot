package com.javaminds.TinyURL.controllers;

import com.google.common.hash.Hashing;

import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import javax.validation.constraints.NotNull;

import com.javaminds.TinyURL.exception.ResourceNotFoundException;
import com.javaminds.TinyURL.models.ErrorResponse;
import com.javaminds.TinyURL.models.Url;
import com.javaminds.TinyURL.repository.TinyURLRepository;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
public class UrlShortenerController {

    @Value("${global.url}")
    private String globalURL;

    @Autowired
    private TinyURLRepository urlRepo;
    /**
     * Navigate to full URL by accessing short URL.
     */
    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity getUrl(@PathVariable String id) {
        Url url =  urlRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Short URL is not valid" ));
        url.setClicks(url.getClicks()+1);
        urlRepo.save(url);
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(url.getUrl())).build();
    }

    /**
     * Description : Generate a short URL from get request
     */
    @PostMapping("/v1/shorten")
    @ResponseBody
    public ResponseEntity postUrl(@RequestBody @NotNull Url url,@RequestHeader String api_key) {
        UrlValidator validator = new UrlValidator( new String[]{"http", "https"} );
        // if invalid url, return error
        if (!validator.isValid(url.getUrl())) {
            ErrorResponse error = new ErrorResponse("url", url.getUrl(), "Invalid URL, URL should prefixed with http/https.");
            return ResponseEntity.badRequest().body(error);
        }

        String id = Hashing.murmur3_32().hashString(url.getUrl(), StandardCharsets.UTF_8).toString();
        url.setId(id);
        url.setShortUrl(globalURL+id);
        url.setCreated(LocalDateTime.now());
        urlRepo.save(url);
        return ResponseEntity.ok(url);
    }

    /**
     * Description : Generate a short URL from post request
     */
    @GetMapping("/v1/shorten")
    @ResponseBody
    public ResponseEntity postUrl(@RequestParam @NotNull String url, @RequestParam String api_key) {
        url = URLDecoder.decode(url,StandardCharsets.UTF_8);
        Url urlObj = new Url(url);
        UrlValidator validator = new UrlValidator( new String[]{"http", "https"} );

        // if invalid url, return error
        if (!validator.isValid(urlObj.getUrl())) {
            ErrorResponse error = new ErrorResponse("url", urlObj.getUrl(), "Invalid URL, URL should prefixed with http/https.");
            return ResponseEntity.badRequest().body(error);
        }

        String id = Hashing.murmur3_32().hashString(urlObj.getUrl(), StandardCharsets.UTF_8).toString();
        urlObj.setId(id);
        urlObj.setShortUrl(globalURL+id);
        urlObj.setCreated(LocalDateTime.now());
        urlRepo.save(urlObj);
        return ResponseEntity.ok(urlObj);
    }

    /*
    * Short Link Home Page
    * */
    @GetMapping("/")
    @ResponseBody
    public ResponseEntity welcome(){
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(globalURL+"/index.html")).build();
    }

}

