package msm_backend.domain;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int UserId;

    @NotNull
    @Size(max = 20)
    @Column(unique = true)
    private String UserName;

    private String UserEmail;
    private String Password;
    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "user")
    private Set<Plan> Plans = new HashSet<>();

    public User( String name, String email, String password){
        super();
        this.UserName = name;
        this.UserEmail = email;
        this.Password = password;
    }
    public User(){
        super();
    }

    public int getUserId() {
        return UserId;
    }

    public void setUserId(int userId) {
        UserId = userId;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserEmail() {
        return UserEmail;
    }

    public void setUserEmail(String userEmail) {
        UserEmail = userEmail;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public Set<Plan> getPlans() {
        return Plans;
    }

    public void setPlans(Set<Plan> plans) {
        Plans = plans;
    }
}
