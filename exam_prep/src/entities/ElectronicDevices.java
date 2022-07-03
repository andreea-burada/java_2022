package entities;

import java.io.Serializable;
import java.util.Collection;

public interface ElectronicDevices extends Comparable<ElectronicDevices>, Serializable {
	public abstract String infoDevice();	// abstract modifier not required
}
