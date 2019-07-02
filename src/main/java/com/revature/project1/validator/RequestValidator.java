package com.revature.project1.validator;

import com.revature.project1.reimbursement.Request;

public class RequestValidator {

	public RequestValidator() {
		
	}
	
	public static void validateRequest(Request r) {
		if (r.getAmount() <= 0 || r.getAmount() > 9999.99){
			throw new IllegalArgumentException("Invalid Ammount");
		} else if (r.getReason() == null || r.getReason() == "") {
			throw new IllegalArgumentException("You Must have a reason attached to this request");
		} else if (r.getStatus() == null){
			throw new IllegalArgumentException("This should never appear.");
		}
	}

}
