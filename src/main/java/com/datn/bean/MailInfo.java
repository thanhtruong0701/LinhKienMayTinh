package com.datn.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MailInfo {
	String from;
	String to;
	String[] cc;
	String[] bcc;
	String subject;
	String body;
	String[] attachments;
	public MailInfo(String to, String subject, String body) {
		super();
		this.from = "TÀI STORE <taitvdps20669@fpt.edu.vn>";
		this.to = to;
		this.subject = subject;
		this.body = body;
	}
	
	public MailInfo(String from, String to, String subject, String body) {
		super();
		this.from = from;
		this.to = to;
		this.subject = subject;
		this.body = body;
	}
}
