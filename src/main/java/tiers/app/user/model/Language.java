package tiers.app.user.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
public class Language extends AuditingMetaData{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String isoLocaleLanguageCode;
    private String languageName;
    @ManyToMany(mappedBy = "countryLanguages")
    private Set<Country> countries = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "language")
    private Set<Customer> customers = new HashSet<>();

}
