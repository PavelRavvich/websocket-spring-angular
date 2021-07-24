package com.grokonez.spring.websocket.model;

public class Order {
	private String data;

	public Order() {
	}

	public Order(String data) {
		this.data = data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String getData() {
		return this.data;
	}
}
