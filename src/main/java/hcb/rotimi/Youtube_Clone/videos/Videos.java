package hcb.rotimi.Youtube_Clone.videos;

import hcb.rotimi.Youtube_Clone.users.Users;
import hcb.rotimi.Youtube_Clone.video_type.VideoType;
import java.time.OffsetDateTime;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
public class Videos {

    @Id
    @Column(nullable = false, updatable = false)

    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String fileuri;

    @Column(nullable = false)
    private long size;

    @Column(nullable = false)
    private String contenttype;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id")
    private Users usersid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "typeid_id")
    private VideoType typeid;

    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @Column(nullable = false)
    private OffsetDateTime lastUpdated;

    @PrePersist
    public void prePersist() {
        dateCreated = OffsetDateTime.now();
        lastUpdated = dateCreated;
    }

    @PreUpdate
    public void preUpdate() {
        lastUpdated = OffsetDateTime.now();
    }

}
