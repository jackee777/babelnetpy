package babelnet;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.babelscape.util.UniversalPOS;
import com.google.common.collect.Multimap;

import it.uniroma1.lcl.babelnet.BabelNet;
import it.uniroma1.lcl.babelnet.BabelNetQuery;
import it.uniroma1.lcl.babelnet.BabelNetUtils;
import it.uniroma1.lcl.babelnet.BabelSense;
import it.uniroma1.lcl.babelnet.BabelSenseComparator;
import it.uniroma1.lcl.babelnet.BabelSynset;
import it.uniroma1.lcl.babelnet.BabelSynsetComparator;
import it.uniroma1.lcl.babelnet.BabelSynsetID;
import it.uniroma1.lcl.babelnet.BabelSynsetRelation;
import it.uniroma1.lcl.babelnet.InvalidSynsetIDException;
import it.uniroma1.lcl.babelnet.data.BabelGloss;
import it.uniroma1.lcl.babelnet.data.BabelImage;
import it.uniroma1.lcl.babelnet.data.BabelSenseSource;
import it.uniroma1.lcl.jlt.util.Language;
import it.uniroma1.lcl.jlt.util.ScoredItem;

/**
 * A demo class to test {@link BabelNet}'s various features.
 *
 * @author cecconi, navigli, vannella
 * @see it.uniroma1.lcl.babelnet.test.BabelNetTest
 * @see it.uniroma1.lcl.babelnet.test.BabelSenseTest
 * @see it.uniroma1.lcl.babelnet.test.BabelSynsetTest
 */
public class demo
{
	/**
	 * A demo to see the senses of a word.
	 *
	 * @param lemma the input lemma
	 * @param languageToSearch the language to search
	 * @param languagesToPrint the languages used for printing
	 */
	public static void testDictionary(String lemma, Language languageToSearch,
			  						  Language... languagesToPrint)
	{
		BabelNet bn = BabelNet.getInstance();
		System.out.println("SENSES FOR \"" + lemma + "\"");
		List<BabelSense> senses =
			bn.getSensesFrom(lemma, languageToSearch, UniversalPOS.NOUN);
		Collections.sort(senses, new BabelSenseComparator());
		for (BabelSense sense : senses)
			System.out.println("\t=>"+sense.getSenseString());
		System.out.println();
		System.out.println("SYNSETS WITH \"" + lemma + "\"");
		List<BabelSynset> synsets =
			bn.getSynsets(lemma, languageToSearch, UniversalPOS.NOUN);
		Collections.sort(synsets, new BabelSynsetComparator(lemma));
		for (BabelSynset synset : synsets)
			System.out.println(
					"\t=>(" +synset.getID() +
					") TYPE: " + synset.getType() +
					"; WN SYNSET: " + synset.getWordNetOffsets() +
					"; MAIN SENSE: " + synset.getMainSense(Language.EN) +
					"; SENSES: "+ synset.toString(languagesToPrint));
		System.out.println();
	}

	/**
	 * A demo to see the senses of a word.
	 *
	 * @param lemma the input lemma
	 * @param languageToSearch the language to search with
	 * @param allowedSources the sources of the senses
	 */
	public static void testDictionary(String lemma, Language languageToSearch,
			  						  BabelSenseSource... allowedSources)
	{
		BabelNet bn = BabelNet.getInstance();
		System.out.println("SENSES FOR \"" + lemma + "\"");
		List<BabelSense> senses =
			bn.getSensesFrom(new BabelNetQuery.Builder(lemma)
											.from(languageToSearch)
											.POS(UniversalPOS.NOUN)
											.sources(allowedSources)
												.build());
		Collections.sort(senses, new BabelSenseComparator());
		for (BabelSense sense : senses)
			System.out.println("\t=>"+sense.getSenseString());
		System.out.println();
		System.out.println("SYNSETS WITH \"" + lemma + "\"");
		List<BabelSynset> synsets =
			bn.getSynsets(new BabelNetQuery.Builder(lemma)
											.from(languageToSearch)
											.POS(UniversalPOS.NOUN)
											.sources(allowedSources)
												.build());
		Collections.sort(synsets, new BabelSynsetComparator(lemma));
		for (BabelSynset synset : synsets)
			System.out.println(
					"\t=>(" +synset.getID() +
					") TYPE: " + synset.getType() +
					"; WN SYNSET: " + synset.getWordNetOffsets() +
					"; MAIN SENSE: " + synset.getMainSense(Language.EN) +
					"; SENSES: "+ synset.toString());
		System.out.println();
	}

	/**
	 * A demo to explore the BabelNet graph.
	 *
	 * @param id the id of the synset to test
	 * @throws InvalidSynsetIDException thrown when the id is invalid
	 */
	public static void testGraph(String id) throws InvalidSynsetIDException
	{
		testGraph(new BabelSynsetID(id));
	}

	/**
	 * A demo to explore the BabelNet graph.
	 *
	 * @param lemma the lemma to work on
	 * @param language the lemma language
	 */
	public static void testGraph(String lemma, Language language)
	{
		BabelNet bn = BabelNet.getInstance();
		List<BabelSynset> synsets = bn.getSynsets(lemma, language);
		Collections.sort(synsets, new BabelSynsetComparator(lemma));

		for (BabelSynset synset : synsets) testGraph(synset.getID());
	}

	/**
	 * A demo to explore the BabelNet graph.
	 *
	 * @param synsetId the synset ID to test
	 */
	public static void testGraph(BabelSynsetID synsetId)
	{
		List<BabelSynsetRelation> successorsEdges = synsetId.getOutgoingEdges();

		System.out.println("SYNSET ID:" + synsetId);
		System.out.println("# OUTGOING EDGES: " + successorsEdges.size());

		for (BabelSynsetRelation edge : successorsEdges)
		{
			System.out.println("\tEDGE " + edge);
			System.out.println("\t" +  edge.getBabelSynsetIDTarget().toSynset().toString(Language.EN));
			System.out.println();
		}
	}

	/**
	 * A demo to see the translations of a word.
	 *
	 * @param lemma the lemma to work on
	 * @param languageToSearch the search language
	 * @param languagesToPrint the languages to use for printing
	 */
	public static void testTranslations(String lemma, Language languageToSearch,
			  							Language... languagesToPrint)
	{
		List<Language> allowedLanguages = Arrays.asList(languagesToPrint);
		Multimap<Language, ScoredItem<String>> translations =
			BabelNetUtils.getTranslations(languageToSearch, lemma);

		System.out.println("TRANSLATIONS FOR " + lemma);
		for (Language language : translations.keySet())
		{
			if (allowedLanguages.contains(language))
				System.out.println("\t"+language+"=>"+translations.get(language));
		}
	}

	/**
	 * A demo to see the glosses of a {@link BabelSynset} given its id.
	 *
	 * @param id the id of the synset to test
	 * @throws InvalidSynsetIDException thrown when the id is invalid
	 */
	public static void testGloss(String id) throws InvalidSynsetIDException
	{
		BabelSynset synset = new BabelSynsetID(id).toSynset();
		testGloss(synset);
	}

	/**
	 *
	 * A demo to see the glosses of a word in a certain language
	 *
	 * @param lemma the lemma to work on
	 * @param language the lemma language
	 */
	public static void testGloss(String lemma, Language language)
	{
		BabelNet bn = BabelNet.getInstance();
		List<BabelSynset> synsets = bn.getSynsets(lemma, language);
		for (BabelSynset synset : synsets) testGloss(synset);
	}

	/**
	 * A demo to see the glosses of a {@link BabelSynset}
	 *
	 * @param synset the synset to test
	 */
	public static void testGloss(BabelSynset synset)
	{
		BabelSynsetID id = synset.getID();
		List<BabelGloss> glosses = synset.getGlosses();

		System.out.println("GLOSSES FOR SYNSET " + synset + " -- ID: " + id);
		for (BabelGloss gloss : glosses)
		{
			System.out.println(" * "+gloss.getLanguage()+" "+gloss.getSource()+" "+
							        gloss.getSourceSense()+"\n\t"+gloss.getGloss());
		}
		System.out.println();
	}

	/**
	 * A demo to see the images of a {@link BabelSynset}
	 *
	 * @param lemma the lemma to work on
	 * @param language the lemma language
	 */
	public static void testImages(String lemma, Language language)
	{
		BabelNet bn = BabelNet.getInstance();
		System.out.println("SYNSETS WITH word: \""+ lemma + "\"");
		List<BabelSynset> synsets = bn.getSynsets(lemma, language);
		for (BabelSynset synset : synsets)
		{
			System.out.println("  =>(" + synset.getID() + ")" +
							 "  MAIN LEMMA: " + synset.getMainSense(language));
			for (BabelImage img : synset.getImages())
			{
				System.out.println("\tIMAGE URL:" + img.getURL());
				System.out.println("\tIMAGE VALIDATED URL:" + img.getURL());
				System.out.println("\t==");
			}
			System.out.println("  -----");
		}
	}

	public static void mainTest()
	{
		BabelNet bn = BabelNet.getInstance();
		String word = "bank";
		System.out.println("SYNSETS WITH English word: \""+word+"\"");
		List<BabelSynset> synsets = bn.getSynsets(word, Language.EN);
		Collections.sort(synsets, new BabelSynsetComparator(word));
		for (BabelSynset synset : synsets)
		{
			System.out.print("  =>(" + synset.getID() + 
							 "; TYPE: " + synset.getType() +
							 "; WN SYNSET: " + synset.getWordNetOffsets() + ";\n" +
							 "  MAIN LEMMA: " + synset.getMainSense(Language.EN) +
							 ";\n  IMAGES: " + synset.getImages() +
							 ";\n  CATEGORIES: " + synset.getCategories() +
							 ";\n  SENSES (Italian): { ");
			for (BabelSense sense : synset.getSenses(Language.IT))
				System.out.print(sense.toString()+" "+sense.getPronunciations()+" ");
			System.out.println("}\n  -----");
		}
	}

	/**
	 * Just for testing
	 *
	 * @param args the demo arguments
	 *
	 */
	static public void main(String[] args)
	{
		try
		{

		   BabelNet bn = BabelNet.getInstance();

			System.out.println("=== DEMO ===");
			BabelSynset  synset = bn.getSynset(new BabelSynsetID("bn:03083790n"));
			System.out.println("Welcome on "+synset.getMainSense(Language.EN).get().getFullLemma().replace("_", " "));
			System.out.println(synset.getMainGloss(Language.EN).get().getGloss());

			mainTest();

//			testImages("balloon", Language.EN);
//
//			System.out.println("===============TESTING BABELNET DICT===============\n");
//			for (String test : new String[] {
//					"bank",
//					"house",
//					"car",
//					"account"
//			}) testDictionary(test, Language.EN, Language.IT);
//			System.out.println("=====================DONE=====================");
//
//			System.out.println("===============TESTING BABELNET GRAPH===============\n");
//			testGraph("bank", Language.EN);
//			testGraph("bn:00000010n");
//			System.out.println("=====================DONE=====================");
//
//			System.out.println("===============TESTING BABELNET TRANSLATIONS===============\n");
//			List<Language> languages = BabelNetConfiguration.getInstance().getBabelLanguages();
//			languages.add(Language.EN);
//			testTranslations("apple", Language.EN, languages.toArray(new Language[]{}));
//			System.out.println("=====================DONE=====================");
//
//			System.out.println("===============TESTING BABELNET GLOSSES===============\n");
//			testGloss("play", Language.EN);
//			testGloss("bn:00000010n");
//			System.out.println("=====================DONE=====================");

		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
