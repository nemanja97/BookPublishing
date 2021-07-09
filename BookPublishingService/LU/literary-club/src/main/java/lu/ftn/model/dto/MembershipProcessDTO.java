package lu.ftn.model.dto;

import lu.ftn.model.entity.MembershipDecision;
import lu.ftn.model.entity.MembershipProcess;

import java.io.Serializable;

public class MembershipProcessDTO implements Serializable {

    private String id;

    private MembershipDecision decision;

    public MembershipProcessDTO() {
    }

    public MembershipProcessDTO(String id, MembershipDecision decision) {
        this.id = id;
        this.decision = decision;
    }

    public MembershipProcessDTO(MembershipProcess process) {
        this.id = process.getId();
        this.decision = process.getDecision();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public MembershipDecision getDecision() {
        return decision;
    }

    public void setDecision(MembershipDecision decision) {
        this.decision = decision;
    }
}
