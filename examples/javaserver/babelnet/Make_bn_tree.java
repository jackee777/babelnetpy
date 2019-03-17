package babelnet;

import static java.lang.System.out;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import it.uniroma1.lcl.babelnet.BabelNet;
import it.uniroma1.lcl.babelnet.BabelNetQuery;
import it.uniroma1.lcl.babelnet.BabelSense;
import it.uniroma1.lcl.babelnet.BabelSynset;
import it.uniroma1.lcl.babelnet.BabelSynsetID;
import it.uniroma1.lcl.babelnet.BabelSynsetRelation;
import it.uniroma1.lcl.jlt.util.Language;

public class Make_bn_tree {
	
	public static void write_lemmas(HashMap<String, List<List<String>>> lemma_map) {		
		for (String key : lemma_map.keySet()) {
			File file = new File("lemmas/"+key);
			try{
				file.createNewFile();
				BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			    for(List<String> lemma_info: lemma_map.get(key)) {
			    	bw.write(lemma_info.get(0)+" "+lemma_info.get(1));
			        bw.newLine();
			    }
		        bw.close();
			}catch(IOException e){
			    System.out.println(e);
			}
			//System.out.println(key + " => " + lemma_map.get(key));
		}

	}
	
	public static void write_tree(HashMap<String, List<String>> id_tree) {
		for (String key : id_tree.keySet()) {
			File file = new File("relations/"+key);
			try{
				file.createNewFile();
				BufferedWriter bw = new BufferedWriter(new FileWriter(file));
				if(id_tree.get(key).size() != 0) {
					bw.write(id_tree.get(key).get(0));
				}
			    for(int i = 1; i < id_tree.get(key).size(); i++){
			    	bw.write(" "+id_tree.get(key).get(i));
			    }
		        bw.close();
			}catch(IOException e){
			    System.out.println(e);
			}
		}
	}

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

	private static List<String> get_hypernym(String id) {
		BabelNet bn = BabelNet.getInstance();

		BabelSynset synset = bn.getSynset(new BabelSynsetID(id));
		HashMap<String, List<List<String>>> lemma_map = new HashMap<String, List<List<String>>>();
		List<String> hypernyms = new ArrayList<String>();
		
		lemma_map.put(synset.getID().getID(), get_simple_lemmas(synset.getID()));
		List<BabelSynsetRelation> edges = synset.getOutgoingEdges();
		for (BabelSynsetRelation edge : edges) {
			if (edge.getLanguage() == Language.EN && edge.getPointer().isHypernym()) {
				out.println(edge.getPointer());
				BabelSynsetID synset_id = edge.getBabelSynsetIDTarget();
				List<List<String>> simple_lemmas = get_simple_lemmas(synset_id);
				out.println("id " + synset_id.getID());
				if(simple_lemmas.size() != 0) {
					lemma_map.put(synset_id.getID(), simple_lemmas);
					hypernyms.add(synset_id.getID());
				}
			}
		}
		write_lemmas(lemma_map);

		return hypernyms;
	}

	public static void main(String[] args) {
		String id = args[0];
		
		HashMap<String, List<String>> id_tree = new HashMap<String, List<String>>();
		id_tree.put(id, get_hypernym(id));
		out.println(id_tree);
		write_tree(id_tree);

		out.println("finish");

	}

}
