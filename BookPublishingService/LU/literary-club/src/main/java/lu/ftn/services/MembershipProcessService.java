package lu.ftn.services;

import lu.ftn.model.entity.MembershipDecision;
import lu.ftn.model.entity.MembershipProcess;
import lu.ftn.model.entity.MembershipVote;

import java.util.List;

public interface MembershipProcessService {

    MembershipProcess save(MembershipProcess membershipProcess);

    MembershipProcess recordNewMembershipProcess(MembershipProcess process);

    MembershipProcess makeFinalDecision(MembershipProcess process, MembershipDecision decision);

    MembershipProcess findById(String id);

    MembershipProcess findByUserId(String userId);

    List<MembershipProcess> findAllMembershipProcessesForEditorToVoteOn(String editorId);

    MembershipProcess recordVote(MembershipVote vote, String editorId, String membershipProcessId);

    void deleteById(String id);
}
