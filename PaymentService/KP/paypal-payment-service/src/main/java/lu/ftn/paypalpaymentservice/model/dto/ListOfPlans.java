package lu.ftn.paypalpaymentservice.model.dto;

import java.util.List;

public class ListOfPlans {
    private List<SubscriptionPlanDTO> listOfPlans;

    public ListOfPlans(List<SubscriptionPlanDTO> listOfPlans) {
        this.listOfPlans = listOfPlans;
    }

    public ListOfPlans() {
    }

    public List<SubscriptionPlanDTO> getListOfPlans() {
        return listOfPlans;
    }

    public void setListOfPlans(List<SubscriptionPlanDTO> listOfPlans) {
        this.listOfPlans = listOfPlans;
    }
}
