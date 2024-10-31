package umc.umc_crud.repository;

import umc.umc_crud.domain.Post;

import java.util.List;
import java.util.Optional;

public interface PostRepository {
    Post save(Post post);
    Optional<Post> findById(Long id);
    Optional<Post> findByTitle(String title);
    List<Post> findAll();

    void deleteById(Long id);
}
