package lu.ftn.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class KeyWord {

    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private String keyWord;

    public KeyWord() {
    }

    public KeyWord(Long id, String keyWord) {
        this.id = id;
        this.keyWord = keyWord;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }
}
