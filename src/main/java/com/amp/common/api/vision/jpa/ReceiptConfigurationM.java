package com.amp.common.api.vision.jpa;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the receipt_configuration_m database table.
 * 
 */
@Entity
@Table(name="receipt_configuration_m")
@NamedQuery(name="ReceiptConfigurationM.findAll", query="SELECT r FROM ReceiptConfigurationM r")
@NamedNativeQueries(value = {
	@NamedNativeQuery(
			name="ReceiptConfigurationM.findByVendor",
			query="SELECT * FROM receipt_configuration_m r where r.RECEIPTCONFIGURATION2VENDOR=(select v.VENDORID from vendor_m v where lower(NAME) = lower(:vendorName))", 
			resultClass = ReceiptConfigurationM.class)
})
public class ReceiptConfigurationM implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false)
	private String receiptconfigurationid;
	
	@Column(name = "RECEIPTCONFIGURATION", columnDefinition = "json", nullable=false)
	private String receiptconfiguration;

	//bi-directional many-to-one association to VendorM
	@ManyToOne
	@JoinColumn(name="RECEIPTCONFIGURATION2VENDOR", nullable=false)
	private VendorM vendorM;

	public ReceiptConfigurationM() {
	}

	public String getReceiptconfigurationid() {
		return this.receiptconfigurationid;
	}

	public void setReceiptconfigurationid(String receiptconfigurationid) {
		this.receiptconfigurationid = receiptconfigurationid;
	}

	public String getReceiptconfiguration() {
		return receiptconfiguration;
	}

	public void setReceiptconfiguration(String receiptconfiguration) {
		this.receiptconfiguration = receiptconfiguration;
	}

	public VendorM getVendorM() {
		return this.vendorM;
	}

	public void setVendorM(VendorM vendorM) {
		this.vendorM = vendorM;
	}

}