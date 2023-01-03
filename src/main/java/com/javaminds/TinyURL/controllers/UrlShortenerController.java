package com.javaminds.TinyURL.controllers;

import com.google.common.hash.Hashing;

import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
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

   public static HashMap<String,Object> data = new LinkedHashMap();

    @Autowired
    private TinyURLRepository urlRepo;
    /**
     * Get a short URL.
     */
    @GetMapping("/{id}")
    @ResponseBody
    public ResponseEntity getUrl(@PathVariable String id) {
//        Url url = (Url)data.get(id);
//        if (url == null) {
//            ErrorResponse error = new ErrorResponse("id", id, "Short URL is not valid.");
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
//        }

        Url url =  urlRepo.findById(id).orElseThrow(() -> new ResourceNotFoundException("Short URL is not valid" ));
//        return ResponseEntity.ok(employee);

//        return ResponseEntity.ok(url);
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(url.getUrl())).build();
    }

    /**
     * Generate a short URL.
     */
    @PostMapping("/")
    @ResponseBody
    public ResponseEntity postUrl(@RequestBody @NotNull Url url) {
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
//        data.put(url.getId(), url);
        urlRepo.save(url);
        return ResponseEntity.ok(url);
    }

    @GetMapping("/")
    @ResponseBody
    public ResponseEntity welcome(){
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(globalURL+"/index.html")).build();
    }

}

