package msm_backend.domain;

import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.*;

@Entity
public class Config {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int configid;

    @Column(name="configkey")
    private String key;

    @Column(name="configvalue")
    private String value;


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
