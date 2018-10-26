package solengine.configuration;

public class QESystemConfiguration {
	
	public boolean HierarchieAnalogy = false;
	public boolean DifferenceInversion = false;
	public boolean SameAsSurprisingObservation = false;
	public boolean SeeAlsoSurprisingObservation = false;
	public boolean AssociationSurprisingObservation = false;
	public boolean InfluenceAnalogy = false;
	
	public QESystemConfiguration(int configuration){
		switch(configuration) {		
		case 0:
			this.InfluenceAnalogy = true;
			break;
		case 1:
			this.HierarchieAnalogy = true;
			break;
		case 2:
			this.SeeAlsoSurprisingObservation = true;
			break;
		case 3:
			this.AssociationSurprisingObservation = true;
			break;
		case 4:
			this.SameAsSurprisingObservation = true;
			break;
		case 5:
			this.DifferenceInversion = true;
			break;
		default:
			this.HierarchieAnalogy = true;
			this.DifferenceInversion = true;
			this.SameAsSurprisingObservation = true;
			this.SeeAlsoSurprisingObservation = true;
			this.AssociationSurprisingObservation = true;
			this.InfluenceAnalogy = true;
			break;
		}
	}

}
