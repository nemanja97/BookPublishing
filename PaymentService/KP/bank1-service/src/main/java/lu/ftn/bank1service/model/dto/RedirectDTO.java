package lu.ftn.bank1service.model.dto;

public class RedirectDTO {

    String redirectUrl;

    public RedirectDTO() {
    }

    public RedirectDTO(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }
}
