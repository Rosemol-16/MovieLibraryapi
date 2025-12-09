package com.example.movielib;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
public class MovieService {
    @Autowired
    private MovieRepository repo;

    public Page<Movie> list(String search, int page, int size, String sortBy, String dir) {
        Sort sort = Sort.by(Sort.Direction.fromString(dir), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        if (search == null || search.isBlank()) {
            return repo.findAll(pageable);
        }
        return repo.findByTitleContainingIgnoreCase(search, pageable);
    }

    public Movie save(Movie m) { return repo.save(m); }
    public Movie findById(Long id) { return repo.findById(id).orElseThrow(() -> new RuntimeException("Not found")); }
    public void delete(Long id) { repo.deleteById(id); }
}

