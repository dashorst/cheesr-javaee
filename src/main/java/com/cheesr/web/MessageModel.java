package com.cheesr.web;

import javax.inject.Inject;

import org.apache.wicket.cdi.NonContextual;
import org.apache.wicket.model.LoadableDetachableModel;

import com.cheesr.domain.MessageOfTheDay;

public class MessageModel extends LoadableDetachableModel<String> {
	@Inject
	private MessageOfTheDay messages;

	public MessageModel() {
		NonContextual.of(MessageModel.class).inject(this);
	}

	@Override
	protected String load() {
		return messages.getMessage();
	}

	private static final long serialVersionUID = 1L;
}
