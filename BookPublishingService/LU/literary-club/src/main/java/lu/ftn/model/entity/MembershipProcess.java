package lu.ftn.model.entity;


import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class MembershipProcess {

    @Id
    private String id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private Writer writer;

    @OneToMany(mappedBy = "membershipProcess", cascade = CascadeType.ALL)
    private Set<MembershipProcessVote> votes = new HashSet<>();

    @Column(name = "decision", nullable = false)
    @Enumerated(EnumType.STRING)
    private MembershipDecision decision = MembershipDecision.UNDECIDED;

    @ElementCollection
    @CollectionTable(name = "membership_works", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "works")
    private List<String> works;

    public MembershipProcess() {
    }

    public MembershipProcess(String id, Writer writer, Set<MembershipProcessVote> votes, MembershipDecision decision, List<String> works) {
        this.id = id;
        this.writer = writer;
        this.votes = votes;
        this.decision = decision;
        this.works = works;
    }

    public MembershipProcess(String id, Writer writer, List<String> works) {
        this.id = id;
        this.writer = writer;
        this.votes = new HashSet<>();
        this.decision = MembershipDecision.UNDECIDED;
        this.works = works;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Writer getWriter() {
        return writer;
    }

    public void setWriter(Writer writer) {
        this.writer = writer;
    }

    public Set<MembershipProcessVote> getVotes() {
        return votes;
    }

    public void setVotes(Set<MembershipProcessVote> votes) {
        this.votes = votes;
    }

    public MembershipDecision getDecision() {
        return decision;
    }

    public void setDecision(MembershipDecision decision) {
        this.decision = decision;
    }

    public List<String> getWorks() {
        return works;
    }

    public void setWorks(List<String> works) {
        this.works = works;
    }
}
