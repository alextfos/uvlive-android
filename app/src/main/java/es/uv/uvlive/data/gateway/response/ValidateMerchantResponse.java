package es.uv.uvlive.data.gateway.response;

public class ValidateMerchantResponse extends BaseResponse {

    private boolean existingUsername;

    /**
     * Constructor
     */
    public ValidateMerchantResponse() {
    }

    /**
     * Constructor
     * @param existingUsername
     */
    public ValidateMerchantResponse(boolean existingUsername) {
        this.existingUsername = existingUsername;
    }

    /**
     * isExistingUsername
     * @return usernameExists
     */
    public boolean isExistingUsername() {
        return existingUsername;
    }

    /**
     * Sets existingUsername
     * @param existingUsername
     */
    public void setExistingUsername(boolean existingUsername) {
        this.existingUsername = existingUsername;
    }
}
