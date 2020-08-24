package com.amp.common.api.vision.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Set;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

/**
 * A DTO for the {@link com.acceps.receiptsvc.domain.Receipt} entity.
 */
public class ReceiptDTO implements Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long id;

    @NotNull
    private String userId;

    private String storeReceiptId;

    private Instant purchaseDate;

    @DecimalMin(value = "0")
    private BigDecimal subtotal;

    @DecimalMin(value = "0")
    private BigDecimal salesTaxRate;

    @DecimalMin(value = "0")
    private BigDecimal taxAmount;

    @NotNull
    @DecimalMin(value = "0")
    private BigDecimal total;


    private StoreDTO store;
    
    private Set<ReceiptItemDTO> receiptItems;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStoreReceiptId() {
        return storeReceiptId;
    }

    public void setStoreReceiptId(String storeReceiptId) {
        this.storeReceiptId = storeReceiptId;
    }

    public Instant getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Instant purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public BigDecimal getSalesTaxRate() {
        return salesTaxRate;
    }

    public void setSalesTaxRate(BigDecimal salesTaxRate) {
        this.salesTaxRate = salesTaxRate;
    }

    public BigDecimal getTaxAmount() {
        return taxAmount;
    }

    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public StoreDTO getStore() {
        return store;
    }

    public void setStore(StoreDTO store) {
        this.store = store;
    }

    public Set<ReceiptItemDTO> getReceiptItems() {
    	return receiptItems;
    }
    
    public void setReceiptItems(Set<ReceiptItemDTO> receiptItems) {
    	this.receiptItems = receiptItems;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ReceiptDTO)) {
            return false;
        }

        return id != null && id.equals(((ReceiptDTO) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ReceiptDTO{" +
            "id=" + getId() +
            ", userId='" + getUserId() + "'" +
            ", storeReceiptId='" + getStoreReceiptId() + "'" +
            ", purchaseDate='" + getPurchaseDate() + "'" +
            ", subtotal=" + getSubtotal() +
            ", salesTaxRate=" + getSalesTaxRate() +
            ", taxAmount=" + getTaxAmount() +
            ", total=" + getTotal() +
            ", store=" + this.store.toString() +
            ", receiptItems=" + this.receiptItems.isEmpty() +
            "}";
    }

}
