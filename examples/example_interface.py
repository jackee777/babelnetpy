# interface for babelnet
class babelnet:
    def __init__(self, API_PATH, lang):
        self.bn = BabelNet(None)
        self.bn.API_PATH = API_PATH
        self.lang = lang
    
    def get_synsets_num(self, word):
        """
        get the number of babelnet ids
        """
        return len(self.bn.getSynset_Ids(word, self.lang))


    def get_word_bn(self, word):
        """
        get babelnet ids
        """
        synsetids = self.bn.getSynset_Ids(word, self.lang)
        babelnet_ids = [synsetid.id for synsetid in synsetids]
        bnid2pos = {synset.id:synset.pos for synset in synsetids}
        return babelnet_ids, bnid2pos


    def get_word_synsetids(self, word):
        """
        get babelnet ids(inclued pos)
        """
        synsetids = self.bn.getSynset_Ids(word, self.lang)
        return [synsetid for synsetid in synsetids]


    def get_bn_lemmas(self, synsetid):
        """
        get babelnet lemmas
        """    
        lemmas = [sense.simpleLemma for synset in self.bn.getSynsets(synsetid) 
                  for sense in synset.senses]
        return lemmas


    def get_bn_main_sense(self, synsetid):
        """
        get main sense
        """ 
        return self.bn.getSynsets(synsetid)[0].mainSense


    def get_hypernyms(self, synsetid):
        """
        get babelnet ids of hypernyms
        """
        hypernyms = [edge.target for edge in self.bn.getOutgoingEdges(synsetid)
                    if edge.language == self.lang and edge.pointer.name == "Hypernym"]
        return hypernyms
    
    def get_hyponyms(self, synsetid):
        """
        get babelnet ids of hypernyms
        """
        hypnyms = [edge.target for edge in self.bn.getOutgoingEdges(synsetid)
                    if edge.language == self.lang and edge.pointer.name == "Hyponym"]
        return hyponyms
    
    def get_wordnet_id(self, babelnetid):
        wnids = self.bn.getWordNetId(babelnetid)
        if len(wnids) >= 1:
            if len(wnids) >= 2:
                pass
                #print("Caution!! one babelnet id, {}, has multiple wordnet ids".format(babelnetid))
            # need 'from nltk.corpus import wordnet as wn'
            for wnid in wnids:
                if "3.0" in wnid["versionMapping"]:
                    return wn.of2ss(wnid["id"][3:])
            
        return None
