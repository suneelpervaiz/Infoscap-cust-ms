package tiers.app.user.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
public class Country extends AuditingMetaData{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String isoAlpha2CountryCode;
    private String isoAlpha3CountryCode;
    private String countryName;
    @ManyToMany
    @JoinTable(
            name = "countryLanguage",
            joinColumns = @JoinColumn(name = "countryId"),
            inverseJoinColumns = @JoinColumn(name = "languageId")
    )
    private Set<Language> countryLanguages = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "country")
    private Set<Customer> customers = new HashSet<>();

}
