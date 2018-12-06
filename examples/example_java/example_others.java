package babelnet;

import static java.lang.System.out;

import java.util.Arrays;
import java.util.List;

import com.babelscape.util.UniversalPOS;

import it.uniroma1.lcl.babelnet.BabelNet;
import it.uniroma1.lcl.babelnet.BabelNetQuery;
import it.uniroma1.lcl.babelnet.BabelSynset;
import it.uniroma1.lcl.babelnet.BabelSynsetRelation;
import it.uniroma1.lcl.jlt.util.Language;

public class example_others {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		BabelNet bn = BabelNet.getInstance();
		out.println(bn);
		out.println("\n");
		
		BabelNetQuery query = new BabelNetQuery.Builder("BabelNet")
				.from(Language.EN)
				.build();
		List<BabelSynset> synsets = bn.getSynsets(query);
		out.println("synsets");
		out.println(synsets);
		out.println(synsets.size());
		out.println("\n");
		
		BabelSynset synset = synsets.get(0);
		out.println("synset");
		out.println(synset.getID());
		out.println(synset.getPOS());
		out.println(synset.getSenseSources());
		out.println("\n");
		
		List<BabelSynsetRelation> edges = synset.getOutgoingEdges();
		out.println("edges");
		out.println(edges);
		out.println("\n");
		
		BabelSynsetRelation edge = edges.get(0);
		out.println("edge");
		out.println(edge.getLanguage());
		out.println(edge.getPointer());
		out.println(edge.getTarget());
		out.println(edge.getBabelSynsetIDTarget());
		out.println(edge.getWeight()); // unable
		out.println(edge.getNormalizedWeight()); // unable
		out.println("\n");
		
		out.println("finish");

	}

}
