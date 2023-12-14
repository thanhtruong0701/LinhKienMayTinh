package com.datn.service;

import javax.mail.MessagingException;

import com.datn.bean.MailInfo;
import com.datn.model.Account;

public interface MailerService {
	/**
	 * Gửi email
	 * 
	 * @param mail thông tin email
	 * @throws MessagingException lỗi gửi email
	 */
	void send(MailInfo mail) throws MessagingException;

	/**
	 * Gửi email đơn giản
	 * 
	 * @param to      email người nhận
	 * @param subject tiêu đề email
	 * @param body    nội dung email
	 * @throws MessagingException lỗi gửi email
	 */
	void send(String to, String subject, String body) throws MessagingException;

	/**
	 * Gửi email feedback - góp ý
	 * 
	 * @param from email + tên người gửi
	 * @param to người nhận mặc định taitvdps20669@fpt.edu.vn
	 * @param subject tiêu đề email
	 * @param body nội dung email
	 * @throws MessagingException lỗi gửi email
	 */
	void sendFeedback(String from, String to, String subject, String body) throws MessagingException;

	/**
	 * Gửi email đơn giản
	 * 
	 * @param to      email người nhận
	 * @param subject tiêu đề email
	 * @param body    nội dung email
	 * @throws MessagingException lỗi gửi email
	 */
	void sendEmail(Account recipient, String type) throws MessagingException;

	/**
	 * Xếp mail vào hàng đợi
	 * 
	 * @param mail thông tin email
	 */
	void queue(MailInfo mail);

	/**
	 * Tạo MailInfo và xếp vào hàng đợi
	 * 
	 * @param to      email người nhận
	 * @param subject tiêu đề email
	 * @param body    nội dung email
	 */
	void queue(String to, String subject, String body);
}
