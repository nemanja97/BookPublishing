package lu.ftn.model.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity
public class MembershipProcessVote {

    @Id
    @GeneratedValue(generator="uuid")
    @GenericGenerator(name="uuid", strategy = "uuid")
    private String id;

    @ManyToOne(fetch = FetchType.EAGER)
    private User councilMember;

    @Column(name = "vote", nullable = false)
    @Enumerated(EnumType.STRING)
    private MembershipVote vote;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "membershipProcess_id")
    private MembershipProcess membershipProcess;

    public MembershipProcessVote() {
    }

    public MembershipProcessVote(String id, User councilMember, MembershipVote vote, MembershipProcess membershipProcess) {
        this.id = id;
        this.councilMember = councilMember;
        this.vote = vote;
        this.membershipProcess = membershipProcess;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getCouncilMember() {
        return councilMember;
    }

    public void setCouncilMember(User councilMember) {
        this.councilMember = councilMember;
    }

    public MembershipVote getVote() {
        return vote;
    }

    public void setVote(MembershipVote vote) {
        this.vote = vote;
    }

    public MembershipProcess getMembershipProcess() {
        return membershipProcess;
    }

    public void setMembershipProcess(MembershipProcess membershipProcess) {
        this.membershipProcess = membershipProcess;
    }
}
