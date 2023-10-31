package forktex.SoccerManagerBELite.repositories.entities;

import lombok.Data;
import lombok.experimental.Accessors;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

@Data
@Accessors(chain = true)
@Entity
@Table(name = "market")
public class Market {
    private static final String TABLE_SEQUENCE = "market_id_seq";
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = TABLE_SEQUENCE)
    @SequenceGenerator(name = TABLE_SEQUENCE, sequenceName = TABLE_SEQUENCE, allocationSize = 1)
    private Long id;

    @Column(name = "player_id")
    private Long playerId;

    @Column(name = "requested_price")
    private Double requestedPrice;

    @Column(name = "finalized")
    private Boolean finalized;

    @Column(name = "canceled")
    private Boolean canceled;

    @Column(name = "selling_team_id")
    private Long sellingTeamId;

    @Column(name = "buying_team_id")
    private Long buyingTeamId;

    @Column(name = "created_date")
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createdDate;

    @Column(name = "updated_date")
    @Temporal(TemporalType.TIMESTAMP)
    @UpdateTimestamp
    private Date updatedDate;
}

