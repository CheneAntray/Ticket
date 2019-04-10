package com.base.utils;

import java.util.Random;

/**
 * 生成随机颜色
 * 
 * @author liyang
 *
 */
public class RandomColor {

	public RandomColor() {
	}

	/**
	 * 获得一个16进制颜色 票务分析项目，可以用这个方法来生成图形统计颜色
	 * 
	 * @return String color
	 */
	public static String getRandomColor() {
		// 红色
		String red;
		// 绿色
		String green;
		// 蓝色
		String blue;
		// 生成随机对象
		Random random = new Random();
		// 生成红色颜色代码
		red = Integer.toHexString(random.nextInt(256)).toUpperCase();
		// 生成绿色颜色代码
		green = Integer.toHexString(random.nextInt(256)).toUpperCase();
		// 生成蓝色颜色代码
		blue = Integer.toHexString(random.nextInt(256)).toUpperCase();
		// 判断红色代码的位数
		red = red.length() == 1 ? "0" + red : red;
		// 判断绿色代码的位数
		green = green.length() == 1 ? "0" + green : green;
		// 判断蓝色代码的位数
		blue = blue.length() == 1 ? "0" + blue : blue;
		// 生成十六进制颜色值
		return "#" + red + green + blue;
	}

	private static Random random = new Random();
	private float k = random.nextFloat() * 360;

	public static void main(String[] args) {
		RandomColor d = new RandomColor();
		Color c = d.randomColor();
		StringBuffer sb = new StringBuffer();
		sb.append("rgba(");
		sb.append(((int) c.r));
		sb.append(",");
		sb.append(((int) c.g));
		sb.append(",");
		sb.append(((int) c.b));
		sb.append(",");
		System.out.println(sb);
	}

	public static String getRandomRGBColor() {
		RandomColor d = new RandomColor();
		Color c = d.randomColor();
		StringBuffer sb = new StringBuffer();
		sb.append("rgba(");
		sb.append(((int) c.r));
		sb.append(",");
		sb.append(((int) c.g));
		sb.append(",");
		sb.append(((int) c.b));
		sb.append(",");
		return sb.toString();
	}

	public Color randomColor() {
		return HSVtoRGB(randomH(), 0.8F, 0.8F);
	}

	private float randomH() {
		k += (1 - 0.618f) * 360;
		if (k > 360)
			k -= 360;
		return k;
	}

	private Color HSVtoRGB(float h, float s, float v) {
		float f, p, q, t;
		if (s == 0) {
			return makeColor(v, v, v);
		}

		h /= 60;
		int i = (int) Math.floor(h);
		f = h - i;
		p = v * (1 - s);
		q = v * (1 - s * f);
		t = v * (1 - s * (1 - f));
		switch (i) {
		case 0:
			return makeColor(v, t, p);
		case 1:
			return makeColor(q, v, p);
		case 2:
			return makeColor(p, v, t);
		case 3:
			return makeColor(p, q, v);
		case 4:
			return makeColor(t, p, v);
		default:
			return makeColor(v, p, q);
		}
	}

	private Color makeColor(float r, float g, float b) {
		return new Color(r, g, b);
	}

	class Color {
		float r, g, b;

		public Color(float r, float g, float b) {
			super();
			this.r = r * 256;
			this.g = g * 256;
			this.b = b * 256;
		}

		public Color() {
			super();
		}

		public float getR() {
			return r;
		}

		public void setR(float r) {
			this.r = r;
		}

		public float getG() {
			return g;
		}

		public void setG(float g) {
			this.g = g;
		}

		public float getB() {
			return b;
		}

		public void setB(float b) {
			this.b = b;
		}

	}
}
