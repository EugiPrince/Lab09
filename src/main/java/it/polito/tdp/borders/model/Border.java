package it.polito.tdp.borders.model;

public class Border {

	private Integer c1;
	private Integer c2;
	
	/**
	 * @param c1
	 * @param c2
	 */
	public Border(Integer c1, Integer c2) {
		super();
		this.c1 = c1;
		this.c2 = c2;
	}

	public Integer getC1() {
		return c1;
	}

	public void setC1(Integer c1) {
		this.c1 = c1;
	}

	public Integer getC2() {
		return c2;
	}

	public void setC2(Integer c2) {
		this.c2 = c2;
	}
	
}
