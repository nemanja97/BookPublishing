package lu.ftn.services.implementation;

import lu.ftn.model.entity.*;
import lu.ftn.repository.MembershipProcessRepository;
import lu.ftn.services.MembershipProcessService;
import lu.ftn.services.UserService;
import org.camunda.bpm.engine.delegate.BpmnError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class MembershipProcessServiceImpl implements MembershipProcessService {

    @Autowired
    MembershipProcessRepository repository;

    @Autowired
    UserService userService;

    @Override
    public MembershipProcess save(MembershipProcess membershipProcess) {
        return repository.save(membershipProcess);
    }

    @Override
    public MembershipProcess recordNewMembershipProcess(MembershipProcess process) {
        return save(process);
    }

    @Override
    public MembershipProcess makeFinalDecision(MembershipProcess process, MembershipDecision decision) {
        process.setDecision(decision);
        process.setVotes(new HashSet<>());
        return save(process);
    }

    @Override
    public MembershipProcess findById(String id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public MembershipProcess findByUserId(String userId) {
        Writer writer;
        try {
            writer = (Writer) userService.findOne(userId);
            if (writer == null || !writer.getRole().equals(Role.ROLE_WRITER))
                throw new EntityNotFoundException("No writer exists under that ID");
        } catch (Exception ex) {
            throw ex;
        }

        return repository.findByWriter(writer);
    }

    @Override
    public List<MembershipProcess> findAllMembershipProcessesForEditorToVoteOn(String editorId) {
        return null;
    }

    @Override
    public MembershipProcess recordVote(MembershipVote vote, String editorId, String membershipProcessId) {
        MembershipProcess process = findById(membershipProcessId);
        if (process == null)
            throw new BpmnError("No membership process exists under that ID");

        User editor = userService.findOne(editorId);
        if (editor == null || !(editor.getRole().equals(Role.ROLE_EDITOR) || editor.getRole().equals(Role.ROLE_HEAD_EDITOR)))
            throw new BpmnError("No editor exists under that ID");

        if (process.getVotes() == null) {
            Set<MembershipProcessVote> votes = new HashSet<>();
            votes.add(new MembershipProcessVote("", editor, vote, process));
            process.setVotes(votes);
        } else {
            if (process.getVotes().stream().anyMatch(mpv -> mpv.getCouncilMember().getId().equals(editorId))) {
                throw new BpmnError("Vote already cast");
            }
            process.getVotes().add(new MembershipProcessVote("", editor, vote, process));
        }

        return save(process);
    }

    @Override
    public void deleteById(String id) {
        repository.deleteById(id);
    }
}
