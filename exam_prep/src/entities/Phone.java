package entities;

import java.io.Serializable;

public class Phone implements ElectronicDevices, Serializable, Cloneable {
	// attributes
	protected float weight;
	protected double diagonal;
	protected String producer;
	
	@Override
	public String infoDevice() {
		
		return producer;
	}
	
	@Override
	public boolean equals(Object o)
	{		
		// same address ?
		if (this == o)
			return true;
		if (o == null)
			return false;
		
		// same class ?
		if (o.getClass() != this.getClass())
			return false;
		
		Phone p = (Phone) o;
		// same attributes ?
		return producer.equals(p.producer) 
				&& (weight == p.weight) 
				&& (diagonal == p.diagonal);
//		if (p.weight == this.weight &&
//			p.diagonal == this.diagonal &&
//			p.producer == this.producer)
//			return true;
//		return false;
		
	}
	
	@Override
	public Object clone()
	{
		Phone toReturn =  new Phone();
		toReturn.weight = weight;
		toReturn.diagonal = diagonal;
		toReturn.producer = producer;
		
		return toReturn;
	}
	
	@Override
	public int hashCode()
	{
		int hash = 7;
		hash = hash * 31 + (int)weight;
		hash = hash * 31 + (int)diagonal;
		hash = hash * 31 + producer.hashCode();
		
		return hash;
	}
	
	@Override
	public int compareTo(ElectronicDevices o) {
		Phone other = (Phone)o;
		return producer.compareTo(other.producer);
	}
	
	@Override
	public String toString()
	{
		return "Phone - weight: " + weight + "; diagonal: " + diagonal + "; producer: " + producer;
	}
	
	// constructors
	public Phone()
	{
		weight = 0;
		diagonal = 0;
		producer = "Unknown";
	}
	
	// getters
	public float getWeight() {
		return weight;
	}
	public double getDiagonal() {
		return diagonal;
	}
	public String getProducer() {
		return producer;
	}
	
	// setters
	public void setWeight(float weight) throws Exception {
		if (weight <= 0)
			throw new Exception("Weight invalid");
		this.weight = weight;
	}
	public void setDiagonal(double diagonal) throws Exception {
		if (diagonal <= 0)
			throw new Exception("Invalid diagonal");
		this.diagonal = diagonal;
	}
	public void setProducer(String producer) throws Exception {
		if (producer.length() <= 1 || producer == null)
			throw new Exception("Invalid producer");
		this.producer = producer;
	}

}
