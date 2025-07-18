  package core;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

import exceptions.PaymentProcessingException;

public class PaymentProcessor {
      private String paymentId;
      private String brandId;
      private String influencerId;
      private String campaignId;
      private double amount;
      private String status;
      
      public PaymentProcessor(String paymentId, String brandId, String influencerId, String campaignId, double amount, String status) {
          this.paymentId = paymentId;
          this.brandId = brandId;
          this.influencerId = influencerId;
          this.campaignId = campaignId;
          this.amount = amount;
          this.status = status;
      }
      

   public boolean processPayment(String brandId, String influencerId, double amount) throws PaymentProcessingException {
       try {
		return processPayment(brandId, influencerId, "", amount);
	} catch (PaymentProcessingException e) {
	        throw new PaymentProcessingException("Failed to process payment: " + e.getMessage());
	}
   }

   public boolean processPayment(String brandId, String influencerId, String campaignId, double amount) throws PaymentProcessingException {
       String record = String.format("%s,%s,%s,%.2f\n", brandId, influencerId, campaignId, amount);
	   writeToPaymentLog(record);
	   return true;
   }

   private void writeToPaymentLog(String record) throws PaymentProcessingException {
	   try (java.io.BufferedWriter writer = new java.io.BufferedWriter(new java.io.FileWriter("src/data/payments.txt", true))) {
	        writer.write(record);
	        writer.newLine();
	    } catch (IOException e) {
	        throw new PaymentProcessingException("Failed to write payment record: " + e.getMessage());
	    }
	
}


	public String getPaymentId() {
    	  return paymentId;
      }
      public String getBrandId(){
    	  return brandId;
      }
      public String getInfluencerId() {
    	  return influencerId;
      }
      public String getCampaignId() { 
    	  return campaignId;
      }
      public double getAmount() { 
    	  return amount;
      }
      public String getStatus() {
    	  return status;
      }

      public void setStatus(String status) {
    	  this.status = status;
      }

      @Override
      public String toString() {
          return paymentId + "," + brandId + "," + influencerId + "," + campaignId + "," + amount + "," + status;
      }
  }
