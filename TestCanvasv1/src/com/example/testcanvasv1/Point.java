package com.example.testcanvasv1;

public class Point {

	public double longitude = 0f;
	public double latitude = 0f;
	public String description;
	public float x, y;
	
	public Point(double lat, double lon, String desc) {
		this.latitude = lat;
		this.longitude = lon;
		this.description = desc;
	}
	
}
