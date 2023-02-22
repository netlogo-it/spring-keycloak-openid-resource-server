package it.example.beans;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Album {
	
	private String id;
	private String title;
	private String cover;
	private List<Photo> photos;
}
