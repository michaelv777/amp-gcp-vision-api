package com.amp.common.api.vision.jpa;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the vendor_m database table.
 * 
 */
@Entity
@Table(name="vendor_m")
@NamedQuery(name="VendorM.findAll", query="SELECT v FROM VendorM v")
public class VendorM implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(unique=true, nullable=false)
	private String vendorid;

	@Column(nullable=false, length=1000)
	private String description;

	@Column(nullable=false, length=100)
	private String name;

	//bi-directional many-to-one association to ReceiptConfigurationM
	@OneToMany(mappedBy="vendorM")
	private List<ReceiptConfigurationM> receiptConfigurationMs;

	public VendorM() {
	}

	public String getVendorid() {
		return this.vendorid;
	}

	public void setVendorid(String vendorid) {
		this.vendorid = vendorid;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ReceiptConfigurationM> getReceiptConfigurationMs() {
		return this.receiptConfigurationMs;
	}

	public void setReceiptConfigurationMs(List<ReceiptConfigurationM> receiptConfigurationMs) {
		this.receiptConfigurationMs = receiptConfigurationMs;
	}

	public ReceiptConfigurationM addReceiptConfigurationM(ReceiptConfigurationM receiptConfigurationM) {
		getReceiptConfigurationMs().add(receiptConfigurationM);
		receiptConfigurationM.setVendorM(this);

		return receiptConfigurationM;
	}

	public ReceiptConfigurationM removeReceiptConfigurationM(ReceiptConfigurationM receiptConfigurationM) {
		getReceiptConfigurationMs().remove(receiptConfigurationM);
		receiptConfigurationM.setVendorM(null);

		return receiptConfigurationM;
	}

}