package com.newtonk.app.service.impl;


import com.newtonk.app.service.Axe;

public class SteelAxe implements Axe {

	@Override
	public String chop() {
		return "钢斧好多了";
	}

	public String getName() {
		return "不错~";
	}

}
