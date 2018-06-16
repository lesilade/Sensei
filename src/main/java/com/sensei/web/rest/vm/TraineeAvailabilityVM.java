package com.sensei.web.rest.vm;

public class TraineeAvailabilityVM {

	    private Long coachRequestId;
		private String day;
	    private String timeofday;
	    private String username;
	    		
	        
	    public TraineeAvailabilityVM() {
		}
	    
		public TraineeAvailabilityVM(Long coachRequestId, String day, String timeofday, String username) {
			this.day = day;
			this.timeofday = timeofday;
			this.username = username;
			this.coachRequestId = coachRequestId;
		}
		
		
		public Long getCoachRequestId() {
			return coachRequestId;
		}

		public void setCoachRequestId(Long coachRequestId) {
			this.coachRequestId = coachRequestId;
		}

		public String getDay() {
			return day;
		}
		public void setDay(String day) {
			this.day = day;
		}
		public String getTimeofday() {
			return timeofday;
		}
		public void setTimeofday(String timeofday) {
			this.timeofday = timeofday;
		}
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}

		@Override
		public String toString() {
			return "TraineeAvailabilityVM [day=" + day + ", timeofday=" + timeofday + ", username=" + username + "]";
		}
		
}
