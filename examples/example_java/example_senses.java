package babelnet; // your package name

import static java.lang.System.out; // to write shorter like python

import java.util.Arrays;
import java.util.List;

import com.babelscape.util.UniversalPOS;

import it.uniroma1.lcl.babelnet.BabelNet;
import it.uniroma1.lcl.babelnet.BabelNetQuery;
import it.uniroma1.lcl.babelnet.BabelSense;
import it.uniroma1.lcl.babelnet.BabelSynset;
import it.uniroma1.lcl.babelnet.BabelSynsetID;
import it.uniroma1.lcl.babelnet.data.BabelSenseSource;
import it.uniroma1.lcl.jlt.util.Language;

public class example_senses {

	public static void main(String[] args) {
		BabelNet bn = BabelNet.getInstance();
		out.println(bn);
		out.println("\n");
		
		BabelSynset synset = bn.getSynset(new BabelSynsetID("bn:03083790n"));
		out.println(synset.getSenses());
		out.println("\n");

		List<BabelSense> senses = synset.getSenses();
		out.println(senses);
		out.println(senses.size());
		out.println("\n");

		BabelSense sense = senses.get(0);
		
		out.println("sense");
		out.println(sense);
		out.println(sense.getFullLemma());
		out.println(sense.getSimpleLemma());
		out.println(sense.getSource());
		out.println(sense.getSensekey());
		out.println(sense.getFrequency()); // unable
		out.println(sense.getLanguage());
		out.println(sense.getPOS());
		out.println(sense.getSynsetID());
		
		
		BabelNetQuery query = new BabelNetQuery.Builder("BabelNet")
				.from(Language.EN)
				.POS(UniversalPOS.NOUN)
				.sources(Arrays.asList(BabelSenseSource.WIKI))
				.build();
		List<BabelSynset> synsets = bn.getSynsets(query);
		out.println("synsets");
		out.println(synsets);
		out.println(synsets.size());
		out.println("\n");
		
		synset = synsets.get(0);
		out.println("synset");
		out.println(synset.getID());
		out.println(synset.getPOS());
		out.println(synset.getSenseSources());
		out.println("\n");

		out.println("finish");

	}

}
