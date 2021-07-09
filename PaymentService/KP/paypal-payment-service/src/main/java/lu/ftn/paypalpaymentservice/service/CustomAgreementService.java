package lu.ftn.paypalpaymentservice.service;

import lu.ftn.paypalpaymentservice.model.CustomAgreement;
import lu.ftn.paypalpaymentservice.repository.CustomAgreementRepository;
import org.springframework.stereotype.Service;

@Service
public class CustomAgreementService {

    private final CustomAgreementRepository customAgreementRepository;

    public CustomAgreementService(CustomAgreementRepository customAgreementRepository) {
        this.customAgreementRepository = customAgreementRepository;
    }

    public CustomAgreement save(CustomAgreement customAgreement){
       return customAgreementRepository.save(customAgreement);
    }

    public CustomAgreement getOne(Long id){
        return customAgreementRepository.getOne(id);
    }
    public CustomAgreement findByToken(String token){
        return customAgreementRepository.findByToken(token);
    }
}
