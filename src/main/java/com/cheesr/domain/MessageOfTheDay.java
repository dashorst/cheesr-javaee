package com.cheesr.domain;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class MessageOfTheDay
{
	public String getMessage()
	{
		return "What happens to the hole when the cheese is gone?";
	}
}
