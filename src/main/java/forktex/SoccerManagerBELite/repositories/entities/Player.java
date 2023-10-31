package forktex.SoccerManagerBELite.repositories.entities;

import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import forktex.SoccerManagerBELite.utils.NumberUtils;

import javax.persistence.*;
import java.util.Date;

@Data
@Accessors(chain = true)
@Entity
@Table(name = "players")
public class Player {
    private static final String TABLE_SEQUENCE = "players_id_seq";
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = TABLE_SEQUENCE)
    @SequenceGenerator(name = TABLE_SEQUENCE, sequenceName = TABLE_SEQUENCE, allocationSize = 1)
    private Long id;

    @Column(name = "team_id")
    private Long teamId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "country")
    private String country;

    @Column(name = "age")
    private Integer age;

    @Column(name = "type")
    private String type;

    @Column(name = "market_value")
    private Double marketValue;

    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createdDate;

    @Column(name = "updated_date")
    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Date updatedDate;

    public void increaseMarketValue() {
        this.marketValue *= NumberUtils.getMarketIncreaseMultiplier();
    }
}
