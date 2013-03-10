package forestry.api.genetics;

import java.util.List;

import forestry.api.core.INBTTagable;

/**
 * Implementations other than Forestry's default one are not supported!
 */
public interface IIndividual extends INBTTagable {

	boolean analyze();

	boolean isAnalyzed();

	String getDisplayName();

	void addTooltip(List<String> list);

	boolean hasEffect();

	boolean isSecret();

	IGenome getGenome();

	String getIdent();

	boolean isGeneticEqual(IIndividual other);
		
	IIndividual copy();

}
