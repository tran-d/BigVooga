package authoring_UI;

import java.util.List;
import javafx.scene.layout.VBox;

public class PairList extends VBox implements PairListI {
	private List<VarValPair> varValPairs;
	
	public PairList(List<VarValPair> variableValuePairs) {
		varValPairs = variableValuePairs;
	}

	@Override
	public void addVarValPair(VarValPair varValPair) {
		varValPairs.add(varValPair);
		getChildren().add(varValPair);
	}

	@Override
	public void removeVarValPair(VarValPair varValPair) {
		varValPairs.remove(varValPair);
		getChildren().remove(varValPair);
	}
	
}
