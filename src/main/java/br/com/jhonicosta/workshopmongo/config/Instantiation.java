package br.com.jhonicosta.workshopmongo.config;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import br.com.jhonicosta.workshopmongo.domain.Post;
import br.com.jhonicosta.workshopmongo.domain.User;
import br.com.jhonicosta.workshopmongo.dto.AuthorDTO;
import br.com.jhonicosta.workshopmongo.dto.CommentDTO;
import br.com.jhonicosta.workshopmongo.repository.PostRepository;
import br.com.jhonicosta.workshopmongo.repository.UserRepository;

@Configuration
public class Instantiation implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PostRepository postRepository;
	
	@Override
	public void run(String... args) throws Exception {
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
		
		userRepository.deleteAll();
		postRepository.deleteAll();
		
		User maria = new User(null, "Maria Brown", "maria@gmail.com");
		User alex = new User(null, "Alex Green", "alex@gmail.com");
		User bob = new User(null, "Bob Grey", "bob@gmail.com");
		
		userRepository.saveAll(Arrays.asList(maria, alex, bob));
		
		Post post1 = new Post(null, sdf.parse("21/08/2018"), "Partiu viagem", "Vou viajar para São Paulo. Abraços!", new AuthorDTO(maria));
		Post post2 = new Post(null, sdf.parse("31/08/2018"), "Bom Dia", "Acordei feliz hoje!", new AuthorDTO(maria));
		
		CommentDTO c1 = new CommentDTO("Boa viajem mano", sdf.parse("31/08/2018"), new AuthorDTO(alex));
		CommentDTO c2 = new CommentDTO("Aproveite", sdf.parse("22/08/2018"), new AuthorDTO(bob));
		CommentDTO c3 = new CommentDTO("Tenha um ótimo dia!", sdf.parse("23/08/2018"), new AuthorDTO(alex));
		
		post1.setComments(Arrays.asList(c1,c2));
		post2.setComments(Arrays.asList(c3));
		
		postRepository.saveAll(Arrays.asList(post1, post2));
		
		maria.setPosts(Arrays.asList(post1, post2));
		userRepository.save(maria);
	}

}
