package babelnet;

import static java.lang.System.*;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;

import it.uniroma1.lcl.babelnet.BabelNet;
import it.uniroma1.lcl.babelnet.BabelNetQuery;
import it.uniroma1.lcl.babelnet.BabelSense;
import it.uniroma1.lcl.babelnet.BabelSynset;
import it.uniroma1.lcl.babelnet.BabelSynsetID;
import it.uniroma1.lcl.babelnet.BabelSynsetRelation;
import it.uniroma1.lcl.jlt.util.Language;

public class Make_word_tree {

	public static List<List<String>> get_simple_lemmas(BabelSynsetID synset_id) {
		BabelNet bn = BabelNet.getInstance();
		BabelSynset synset = bn.getSynset(synset_id);
		List<BabelSense> senses = synset.getSenses(Language.EN);
		List<List<String>> lemma_lists = new ArrayList<List<String>>();

		for (BabelSense sense : senses) {
			List<String> lemma_list = new ArrayList<String>();
//			if (sense.getSource() == BabelSenseSource.WN) {
//				lemma_list.add("WN");
//				lemma_list.add(sense.getSimpleLemma());
//			} else if (sense.getSource() == BabelSenseSource.WIKI) {
//				lemma_list.add("WIKI");
//				lemma_list.add(sense.getSimpleLemma());
//			} else if (sense.getSource() == BabelSenseSource.WIKIDATA) {
//				lemma_list.add("WIKIDATA");
//				lemma_list.add(sense.getSimpleLemma());
//			} else if (sense.getSource() == BabelSenseSource.WIKT) {
//				lemma_list.add("WIKT");
//				lemma_list.add(sense.getSimpleLemma());
//			} else if (sense.getSource() == BabelSenseSource.WIKIRED) {
//				lemma_list.add("WIKIRED");
//				lemma_list.add(sense.getSimpleLemma());
//			} else if (sense.getSource() == BabelSenseSource.OMWN) {
//				lemma_list.add("OMWN");
//				lemma_list.add(sense.getSimpleLemma());
//			} else if (sense.getSource() == BabelSenseSource.OMWIKI) {
//				lemma_list.add("OMWIKI");
//				lemma_list.add(sense.getSimpleLemma());
//			} else {
//				out.println(sense.getSource());
//				lemma_list.add("OTHERS");
//				lemma_list.add(sense.getSimpleLemma());
//			}
			lemma_list.add(sense.getSource().toString());
			lemma_list.add(sense.getSimpleLemma());
			if(lemma_list.size() != 0) {
				lemma_lists.add(lemma_list);
			}
		}

		return lemma_lists;
	}

	private static HashMap<String, List<String>> get_tree(String word) {
		BabelNet bn = BabelNet.getInstance();

		BabelNetQuery query = new BabelNetQuery.Builder(word).from(Language.EN).build();
		List<BabelSynset> synsets = bn.getSynsets(query);
		HashMap<String, List<String>> id_tree = new HashMap<String, List<String>>();
		HashMap<String, List<List<String>>> lemma_map = new HashMap<String, List<List<String>>>();
		for (BabelSynset synset : synsets) {
			//out.println(synset.getID().getID());
			lemma_map.put(synset.getID().getID(), get_simple_lemmas(synset.getID()));
			List<BabelSynsetRelation> edges = synset.getOutgoingEdges();
			List<String> hypernyms = new ArrayList<String>();
			for (BabelSynsetRelation edge : edges) {
				if (edge.getLanguage() == Language.EN && edge.getPointer().isHypernym()) {
					BabelSynsetID synset_id = edge.getBabelSynsetIDTarget();
					List<List<String>> simple_lemmas = get_simple_lemmas(synset_id);
					//out.println("size"+ simple_lemmas.size());
					//if(simple_lemmas.size() != 0) {
					hypernyms.add(synset_id.getID());
					//out.println(hypernyms);
					lemma_map.put(synset_id.getID(), simple_lemmas);
					//}
				}
			}
			//out.println("hypernym"+ hypernyms);
			//out.println(lemma_map.get(synset.getID().getID()).size());
			if(hypernyms.size() != 0) {
				//out.println(synset.getID().getID());
				id_tree.put(synset.getID().getID().replace(":", "_"), hypernyms);
			}
		}
		//out.println(lemma_map);
		//write_lemmas(lemma_map);
		//out.println(id_tree);
		return id_tree;
	}

	public void getSynsetIds(String lemma, String searchLang) {
		BabelNet bn = BabelNet.getInstance();
		Language sLang = Language.EN;
		if(searchLang != null) {
			if(searchLang.equals("IT")) {
				sLang = Language.IT;
			}
		}

		BabelNetQuery query = new BabelNetQuery.Builder(lemma).from(sLang).build();
		List<BabelSynset> synsets = bn.getSynsets(query);
		HashMap<String, List<String>> id_tree = new HashMap<String, List<String>>();

		File file = new File("files/getSynsetIds");
		try{
			file.createNewFile();
			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			List<SynsetId> SynsetIds = new ArrayList<SynsetId>();
			for (BabelSynset synset : synsets) {
				List<BabelSynsetRelation> edges = synset.getOutgoingEdges();
				for (BabelSynsetRelation edge : edges) {
					if (edge.getLanguage() == Language.EN && edge.getPointer().isHypernym()) {
						BabelSynsetID synset_id = edge.getBabelSynsetIDTarget();
						List<List<String>> simple_lemmas = get_simple_lemmas(synset_id);
						SynsetId synsetid = new SynsetId();
						synsetid.id = synset_id.getID();
						synsetid.pos = synset_id.getPOS().toString();
						synsetid.source = synset_id.getSource().getSourceName();
						SynsetIds.add(synsetid);
					}
				}
			}
			bw.write(new Gson().toJson(SynsetIds));
	        bw.close();
		}catch(IOException e){
		    System.out.println(e);
		}

	}


	public static void main(String[] args) {
		String word = args[0];
		HashMap<String, List<String>> id_tree = get_tree(word);
		//out.println(id_tree);
		//for(int i = 0; i < 100; i++) out.print("");
		out.println("finish");
		//out.println("check");
	}

}
