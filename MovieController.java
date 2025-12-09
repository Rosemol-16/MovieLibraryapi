package com.example.movielib;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/movies")
public class MovieController {
    @Autowired MovieService service;

    @GetMapping
    public Page<Movie> list(
        @RequestParam(required=false) String search,
        @RequestParam(defaultValue="0") int page,
        @RequestParam(defaultValue="10") int size,
        @RequestParam(defaultValue="id") String sortBy,
        @RequestParam(defaultValue="ASC") String dir
    ) {
        return service.list(search, page, size, sortBy, dir);
    }

    @PostMapping
    public Movie create(@RequestBody Movie m) { return service.save(m); }

    @GetMapping("/{id}")
    public Movie get(@PathVariable Long id) { return service.findById(id); }

    @PutMapping("/{id}")
    public Movie update(@PathVariable Long id, @RequestBody Movie m) {
        Movie ex = service.findById(id);
        // copy fields (simple)
        ex.setTitle(m.getTitle());
        ex.setDirector(m.getDirector());
        ex.setGenre(m.getGenre());
        ex.setReleaseDate(m.getReleaseDate());
        ex.setRating(m.getRating());
        return service.save(ex);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { service.delete(id); }
}

