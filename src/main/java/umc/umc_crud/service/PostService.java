package umc.umc_crud.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.umc_crud.domain.Post;
import umc.umc_crud.repository.JpaPostRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class PostService {

    private final JpaPostRepository postRepository;
    @Autowired
    public PostService(JpaPostRepository postRepository) {
        this.postRepository = postRepository;
    }

    //글생성
    public Long save(Post post){
        postRepository.save(post);
        return post.getId();
    }

    //글 하나 조회
    public Optional<Post> findOnePost(Long postId){
        return postRepository.findById(postId);
    }

    //글 목록
    public List<Post> findPosts(){
        return postRepository.findAll();
    }

    //글삭제
    public void deletePost(Long id){
        postRepository.deleteById(id);
    }


}
