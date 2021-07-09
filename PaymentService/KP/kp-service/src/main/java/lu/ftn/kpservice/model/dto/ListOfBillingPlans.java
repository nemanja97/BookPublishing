package lu.ftn.kpservice.model.dto;

import java.util.List;

public class ListOfBillingPlans {
    private List<PaypalSubscriptionPlanDTO> listOfPlans;

    public ListOfBillingPlans(List<PaypalSubscriptionPlanDTO> listOfPlans) {
        this.listOfPlans = listOfPlans;
    }

    public ListOfBillingPlans() {
    }

    public List<PaypalSubscriptionPlanDTO> getListOfPlans() {
        return listOfPlans;
    }

    public void setListOfPlans(List<PaypalSubscriptionPlanDTO> listOfPlans) {
        this.listOfPlans = listOfPlans;
    }
}
