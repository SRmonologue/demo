package com.ohosure.smart.zigbeegate.protocol;

public abstract class HReceive {

	private boolean isAnalyzed;

	public void analyze() {

		if (!isAnalyzed) {
			parse();
			isAnalyzed = true;
		}

	}

	protected abstract void parse();

}
