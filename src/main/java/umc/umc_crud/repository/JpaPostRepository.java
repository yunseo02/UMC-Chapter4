package umc.umc_crud.repository;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import umc.umc_crud.domain.Post;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public class JpaPostRepository implements PostRepository{

    private final EntityManager em;

    public JpaPostRepository(EntityManager em) {
        this.em = em;
    }

    @Override
    public Post save(Post post) {
        em.persist(post);
        return post;
    }

    @Override
    public Optional<Post> findById(Long id) {
        Post post = em.find(Post.class, id);
        return Optional.ofNullable(post);
    }//Post.class의 이유: 엔티티 매니저가 어떤 엔티티 타입의 객체를 찾을지 명확히 지정하기 위해서
     //em.find 메서드는 두 가지 인자를 받는다 1. 엔티티 클래스 타임 2. 찾고자 하는 엔티티의 식별자 값

    @Override
    public Optional<Post> findByTitle(String title) {
        List<Post> result = em.createQuery("select p from Post p where p.title = :title", Post.class)
                .setParameter("title", title)//쿼리에서 사용되는 :title 파라메터 정해준다
                .getResultList();//Jpa에서는 쿼리 결과가 몇 개일지 미리 알 수 없기 때문에, 쿼리 실행 결과를 일반적으로 리스트 형태로 반환한다.//결과가 하나일 때: getSingleResult()
        return result.stream().findAny();//findAny()는 리스트에서 하나의 결과를 Oprional<Post>형태로 감싸서 반환함
    }
    //pk기반이 아니라 나머지 find 기능은 query 작성이 필요함

    @Override
    public List<Post> findAll() {
        return em.createQuery("select p from Post p", Post.class)
                .getResultList();
    }

    @Override
    public void deleteById(Long id) {
        Post post = em.find(Post.class, id);
        if (post != null) {
            em.remove(post);
        }
    }
}
//??굳이 findByTitle을 했을 때 리스트로 받는 이유를 모르겠음
