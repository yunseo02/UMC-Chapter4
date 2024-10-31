package umc.umc_crud.controller;

import lombok.Getter;
import lombok.Setter;
@Getter @Setter
public class PostForm {



    @Getter
    private String title;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    private String author;

    private String context;

    public void setTitle(String title) {
        this.title = title;
    }
}
