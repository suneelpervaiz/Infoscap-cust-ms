package tiers.app.customer.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
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
