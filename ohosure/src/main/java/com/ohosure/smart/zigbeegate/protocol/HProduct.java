package com.ohosure.smart.zigbeegate.protocol;



public class HProduct {

	long productId;
	int productType;
	int productState;

	public HProduct(long productId, int productType,
			int productState) {
		this.productId = productId;
		this.productType = productType;
		this.productState = productState;

	}

	public long getProductId() {
		return productId;
	}

	public void setProductId(long productId) {
		this.productId = productId;
	}

	public int getProductType() {
		return productType;
	}

	public void setProductType(int productType) {
		this.productType = productType;
	}

	public int getProductState() {
		return productState;
	}

	public void setProductState(int productState) {
		this.productState = productState;
	}

	 
	 

}
