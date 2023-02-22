package it.example.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import it.example.beans.Album;
import it.example.beans.Photo;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@CrossOrigin
@RequestMapping(value = "/app")
public class AppController {
	
	private static final String IMAGE_PREFIX = "http://localhost:8081";
	private static final String PHOTOS1	= IMAGE_PREFIX + "/images/photo1";
	private static final String PHOTOS2	= IMAGE_PREFIX + "/images/photo2";

	private List<Album> listAlbums = new ArrayList<>(); 

	@PostConstruct 
	void init() {
		
		this.listAlbums.add(
			new Album(
				"1", 
				"Photo Album 1",
				PHOTOS1 + "/1.png",
				Arrays.asList(
					new Photo(PHOTOS1 + "/1.png"),
					new Photo(PHOTOS1 + "/2.png"),
					new Photo(PHOTOS1 + "/3.png"),
					new Photo(PHOTOS1 + "/4.png"),
					new Photo(PHOTOS1 + "/5.png")
				)
			)
		);
		
		this.listAlbums.add(
			new Album(
				"2", 
				"Photo Album 2",
				PHOTOS2 + "/1.png",
				Arrays.asList(
						new Photo(PHOTOS2 + "/1.png"),
						new Photo(PHOTOS2 + "/2.png"),
						new Photo(PHOTOS2 + "/3.png"),
						new Photo(PHOTOS2 + "/4.png"),
						new Photo(PHOTOS2 + "/5.png")
				)
			)
		);
	}
	
	@GetMapping("/albums")
	public Map<String, List<Album>> albums(JwtAuthenticationToken authn) {
		Jwt jwt=(Jwt)authn.getPrincipal();
		Map<String,Object> claims=jwt.getClaims();
		log.info("authn={}",authn);
		log.info("claims={}",claims);

		var albumsMap = new HashMap<String, List<Album>>();
		albumsMap.put("albums", this.listAlbums);
		return albumsMap;
	}
	
    @PostMapping("/photos")
    public Map<String, List<Photo>> photos(@RequestBody Map fields) {
    	
    	String id = (String)fields.get("albumid");
    	log.info("albumid="+id);
    	List<Photo> photos = this.listAlbums.stream()
							    		.filter(a -> a.getId().equalsIgnoreCase(id))
							    		.findFirst()
							    		.orElseThrow()
							    		.getPhotos();
    	
		var photosMap = new HashMap<String, List<Photo>>();
		photosMap.put("photos", photos);
		return photosMap;
    }
}
