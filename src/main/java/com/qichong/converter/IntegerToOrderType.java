package com.qichong.converter;

import org.springframework.core.convert.converter.Converter;

import com.qichong.enums.OrderType;

public class IntegerToOrderType implements Converter<Integer, OrderType> {

	@Override
	public OrderType convert(Integer source) {
		if (source == null) {
			return null;
		}
		return OrderType.get(source.byteValue());
	}

}
