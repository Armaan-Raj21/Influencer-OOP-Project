package core;

public class Contract {
    private String contractId;
    private String brandId;
    private String influencerId;
    private String campaignId;
    private String terms;
    private String status;

    public Contract(String contractId, String brandId, String influencerId, String campaignId, String terms, String status) {
        this.contractId = contractId;
        this.brandId = brandId;
        this.influencerId = influencerId;
        this.campaignId = campaignId;
        this.terms = terms;
        this.status = status;
    }

    public Contract(String... details) {
        if (details.length >= 6) {
            this.contractId = details[0];
            this.brandId = details[1];
            this.influencerId = details[2];
            this.campaignId = details[3];
            this.terms = details[4];
            this.status = details[5];
        }
    }

    public String getContractId() {
        return contractId;
    }

    public String getBrandId() {
        return brandId;
    }

    public String getInfluencerId() {
        return influencerId;
    }

    public String getCampaignId() {
        return campaignId;
    }

    public String getTerms() {
        return terms;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void updateTermsAndStatus(String... updates) {
        if (updates.length >= 2) {
            this.terms = updates[0];
            this.status = updates[1];
        }
    }

    public void updateFields(String... fields) {
        if (fields.length >= 1) this.contractId = fields[0];
        if (fields.length >= 2) this.brandId = fields[1];
        if (fields.length >= 3) this.influencerId = fields[2];
        if (fields.length >= 4) this.campaignId = fields[3];
        if (fields.length >= 5) this.terms = fields[4];
        if (fields.length >= 6) this.status = fields[5];
    }

    @Override
    public String toString() {
        return contractId + "," + brandId + "," + influencerId + "," + campaignId + "," + terms + "," + status;
    }
}
