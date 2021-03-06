package surfaces;

import RayTracing.Ray;
import utils.Vector;

public class Triangle extends GeneralObject{
	private Vector v0;
	private Vector v1;
	private Vector v2;
	
	public Triangle(Material material, Vector u, Vector v, Vector w, int matIndex) {
		this.setMaterial(material);
		this.v0 = u;
		this.v1 = v;
		this.v2 = w;
	}
	
	
	public Vector findIntersectionPoint(Ray ray) {
		
		Vector normal = findNormalVector(null);
		double offset = (-1) * Vector.dotProduct(normal, this.v0);
		
		/////////// Step1: finding P ///////////

		Vector p = trianglePlaneIntersection(ray, normal, offset);
		if(p == null) return null;
		
		////// Step 2: inside-outside test /////
		
		Vector c; // Vector perpendicular to triangle's plane
		
		// edge 0
		Vector edge0 = Vector.vecSubtract(this.v1, this.v0);
		Vector v0p = Vector.vecSubtract(p, this.v0);
		c = edge0.crossProduct(v0p);
		if(normal.dotProduct(c) < 0) return null; // p is on the right side
		
		// edge 1
		Vector edge1 = Vector.vecSubtract(this.v2, this.v1);
		Vector v1p = Vector.vecSubtract(p, this.v1);
		c = edge1.crossProduct(v1p);
		if(normal.dotProduct(c) < 0) return null; // p is on the right side
		
		// edge 2
		Vector edge2 = Vector.vecSubtract(this.v0, this.v2);
		Vector v2p = Vector.vecSubtract(p, this.v2);
		c = edge2.crossProduct(v2p);
		if(normal.dotProduct(c) < 0) return null; // p is on the right side
		
		return p;

	}
	
	//gets a point on the triangle and returns the normal direction in that point
	protected Vector findNormalVector(Vector p) {
		Vector v0v1 = Vector.vecSubtract(this.v1, this.v0);
		Vector v0v2 = Vector.vecSubtract(this.v2, this.v0);
		return v0v1.crossProduct(v0v2);
	}
	
	
	private Vector trianglePlaneIntersection(Ray ray, Vector normal, double offset) {
		double d = offset;
		double VN=Vector.dotProduct(ray.getDirection(), normal);
		double P0N=Vector.dotProduct(ray.getSource(), normal);
		double t;
		
		if (VN == 0)
			return null;
		
		t = -(P0N+d)/VN;
		if (t<=0) 
			return null;
		Vector tv = Vector.scalarMult(ray.getDirection(), t);
		return Vector.vecAdd(ray.getSource(), tv);
	}
}
