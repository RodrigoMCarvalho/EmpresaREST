package com.empresaRest.validation;

import java.time.LocalDate;

public class GenericErrorDetails extends ErrorDetails {

	public static final class Builder {
		private String title;
		private int status;
		private String detail;
		private LocalDate timestamp;
		private String developerMessage;

		private Builder() {
		}

		public static Builder newBuilder() {
			return new Builder();
		}

		public Builder title(String title) {
			this.title = title;
			return this;
		}

		public Builder status(int status) {
			this.status = status;
			return this;
		}

		public Builder detail(String detail) {
			this.detail = detail;
			return this;
		}

		public Builder timestamp(LocalDate timestamp) {
			this.timestamp = timestamp;
			return this;
		}

		public Builder developerMessage(String developerMessage) {
			this.developerMessage = developerMessage;
			return this;
		}

		public GenericErrorDetails build() {
			GenericErrorDetails genericErrorDetails = new GenericErrorDetails();
			genericErrorDetails.setDeveloperMessage(developerMessage);
			genericErrorDetails.setTitle(title);
			genericErrorDetails.setDetail(detail);
			genericErrorDetails.setTimestamp(timestamp);
			genericErrorDetails.setStatus(status);
			return genericErrorDetails;
		}
	}
}
