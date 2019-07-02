package com.revature.project1.reimbursement;

import java.sql.Timestamp;

public class Request {
	
	private int ID;
	private int EMP_ID;
	private Timestamp time;
	private float amount;
	private String reason;
	private Status status;
	
	

	@Override
	public String toString() {
		return "Request [ID=" + ID + ", EMP_ID=" + EMP_ID + ", time=" + time + ", amount=" + amount + ", reason="
				+ reason + ", status=" + status + "]";
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + EMP_ID;
		result = prime * result + ID;
		result = prime * result + Float.floatToIntBits(amount);
		result = prime * result + ((reason == null) ? 0 : reason.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
		result = prime * result + ((time == null) ? 0 : time.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Request other = (Request) obj;
		if (EMP_ID != other.EMP_ID)
			return false;
		if (ID != other.ID)
			return false;
		if (Float.floatToIntBits(amount) != Float.floatToIntBits(other.amount))
			return false;
		if (reason == null) {
			if (other.reason != null)
				return false;
		} else if (!reason.equals(other.reason))
			return false;
		if (status != other.status)
			return false;
		if (time == null) {
			if (other.time != null)
				return false;
		} else if (!time.equals(other.time))
			return false;
		return true;
	}



	public int getID() {
		return ID;
	}



	public void setID(int iD) {
		ID = iD;
	}



	public int getEMP_ID() {
		return EMP_ID;
	}



	public void setEMP_ID(int eMP_ID) {
		EMP_ID = eMP_ID;
	}



	public Timestamp getTime() {
		return time;
	}



	public void setTime(Timestamp time) {
		this.time = time;
	}



	public float getAmount() {
		return amount;
	}



	public void setAmount(float amount) {
		this.amount = amount;
	}



	public String getReason() {
		return reason;
	}



	public void setReason(String reason) {
		this.reason = reason;
	}



	public Status getStatus() {
		return status;
	}



	public void setStatus(Status status) {
		this.status = status;
	}

	
	/**
	 * Converts a string into a status
	 * @param status	a String that relates to a status
	 * @return 			a status based on that string or returns null if not associated
	 * @throws Exception 
	 */
	public static Status getStatusFromString(String status) throws Exception {
		if(status.equals("PENDING")) {
			return Status.PENDING;
		} else if (status.equals("DENIED")) {
			return Status.DENIED;
		} else if (status.equals("APPROVED")) {
			return Status.APPROVED;
		} else {
			throw new Exception("The String could nt be matched to a status");
		}
	}
	
	
	/**
	 * Takes a status and returns a string related to that status
	 * @param status	a status provided
	 * @return 			a string of that status 
	 */
	public static String statusToString(Status status) {
		if(status == Status.PENDING) {
			return "PENDING";
		} else if (status == Status.DENIED) {
			return "DENIED";
		} else if (status == Status.APPROVED) {
			return "APPROVED";
		} else {
			return null;
		}
	}



	public Request(int iD, int eMP_ID, Timestamp time, float amount, String reason, Status status) {
		super();
		ID = iD;
		EMP_ID = eMP_ID;
		this.time = time;
		this.amount = amount;
		this.reason = reason;
		this.status = status;
	}


}
