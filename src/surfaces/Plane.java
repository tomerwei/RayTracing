package surfaces;

import RayTracing.Ray;
import utils.Vector;

public class Plane extends GeneralObject {
	
	private Vector normal;
	private double offset;
	private int matIndex;	
	
	/**
	 * 
	 * @param x the x component of the plane normal.
	 * @param y the y component of the plane normal.
	 * @param z the z component of the plane normal.
	 * @param offset the point that is used to define the plane.
	 */
	public Plane(Material material, Vector normal, double offset, int matIndex) {
		this.setMaterial(material);
		this.normal = normal;
		this.offset = offset;
		this.matIndex = matIndex;
	}
	
	public Vector findIntersectionPoint(Ray ray) {
		double d=this.offset;
		double VN=Vector.dotProduct(ray.getDirection(), this.normal);
		double P0N=Vector.dotProduct(ray.getSource(), this.normal);
		double t;
		
		if (VN==0)
			return null;
		t=(-P0N+d)/VN;
		if (t<=0) 
			return null;
		Vector tv = Vector.scalarMult(ray.getDirection(), t);
		return Vector.vecAdd(ray.getSource(), tv);
	}
	
	//gets a point on the plane and returns the normal direction in that point
	public Vector findNormalVector(Vector p) {
		Vector returnedNormal = new Vector(this.normal.X(),this.normal.Y(),this.normal.Z());
		return returnedNormal;
	}
	
}