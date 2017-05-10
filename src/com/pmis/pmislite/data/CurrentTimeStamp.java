package com.pmis.pmislite.data;

import java.sql.Timestamp;

public class CurrentTimeStamp {

	public static Timestamp getCurrentTimestamp()
    {
	 java.util.Date date= new java.util.Date();
	 return new Timestamp(date.getTime());
    }
}
