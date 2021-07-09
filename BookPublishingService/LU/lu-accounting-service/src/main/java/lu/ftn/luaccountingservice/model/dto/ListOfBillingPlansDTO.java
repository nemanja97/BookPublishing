package lu.ftn.luaccountingservice.model.dto;

import java.util.List;

public class ListOfBillingPlansDTO {
    private List<PaypalSubscriptionPlanDTO> listOfPlans;

    public ListOfBillingPlansDTO(List<PaypalSubscriptionPlanDTO> listOfPlans) {
        this.listOfPlans = listOfPlans;
    }

    public ListOfBillingPlansDTO() {
    }

    public List<PaypalSubscriptionPlanDTO> getListOfPlans() {
        return listOfPlans;
    }

    public void setListOfPlans(List<PaypalSubscriptionPlanDTO> listOfPlans) {
        this.listOfPlans = listOfPlans;
    }
}
