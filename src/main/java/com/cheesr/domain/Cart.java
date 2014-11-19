package com.cheesr.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ConversationScoped;

import com.cheesr.entities.Cheese;

@ConversationScoped
public class Cart implements Serializable {
	private static final long serialVersionUID = 1L;

	private List<Cheese> items = new ArrayList<>();

	public Cart() {
	}

	public void setItems(List<Cheese> items) {
		this.items = items;
	}

	public List<Cheese> getItems() {
		return items;
	}

	public double getTotal() {
		return items.stream().mapToDouble(Cheese::getPrice).sum();
	}
}
